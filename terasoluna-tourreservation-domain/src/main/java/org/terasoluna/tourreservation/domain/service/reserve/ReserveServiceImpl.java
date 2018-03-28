/*
 * Copyright (C) 2013-2018 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.terasoluna.tourreservation.domain.service.reserve;

import java.util.List;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.dozer.Mapper;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.tourreservation.domain.common.constants.MessageId;
import org.terasoluna.tourreservation.domain.model.Reserve;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.repository.reserve.ReserveRepository;
import org.terasoluna.tourreservation.domain.service.tourinfo.PriceCalculateOutput;
import org.terasoluna.tourreservation.domain.service.tourinfo.PriceCalculateSharedService;
import org.terasoluna.tourreservation.domain.service.tourinfo.TourInfoSharedService;

@Slf4j
@Transactional
@Service
public class ReserveServiceImpl implements ReserveService {

    @Inject
    ReserveRepository reserveRepository;

    @Inject
    AuthorizedReserveSharedService authorizedReserveSharedService;

    @Inject
    TourInfoSharedService tourInfoSharedService;

    @Inject
    PriceCalculateSharedService priceCalculateService;

    @Inject
    JodaTimeDateFactory dateFactory;

    @Inject
    Mapper beanMapper;

    @Override
    public Reserve findOneWithTourInfo(String reserveNo) {
        Reserve reserve = authorizedReserveSharedService.findOne(reserveNo);

        if (reserve != null) {
            TourInfo tourInfo = tourInfoSharedService.findOneWithDetails(reserve
                    .getTourInfo().getTourCode());
            reserve.setTourInfo(tourInfo);
        }

        return reserve;
    }

    @Override
    public List<Reserve> findAllWithTourInfoByCustomer(String customerCode) {
        List<Reserve> reserves = reserveRepository
                .findAllWithTourInfoByCustomer(customerCode);
        return reserves;
    }

    @Override
    public ReserveTourOutput reserve(
            ReserveTourInput input) throws BusinessException {

        TourInfo tourInfo = tourInfoSharedService.findOneWithDetailsForUpdate(
                input.getTourCode());
        DateTime today = dateFactory.newDateTime().withTime(0, 0, 0, 0);

        // * check date
        // error if today is after payment limit
        if (tourInfoSharedService.isOverPaymentLimit(tourInfo)) {
            ResultMessages message = ResultMessages.error().add(
                    MessageId.E_TR_0004);
            throw new BusinessException(message);
        }

        // * check vacancy
        int reserveMember = input.getAdultCount() + input.getChildCount();
        int aveRecMax = tourInfo.getAvaRecMax();
        // retrieve the number of current reservations
        Long sumCount = reserveRepository.countReservedPersonSumByTourInfo(
                tourInfo.getTourCode());
        if (sumCount == null) {
            sumCount = 0L;
        }

        // calculate vacancy
        long vacantCount = aveRecMax - sumCount;
        log.debug("vacantCount({}), reserveMember({})", vacantCount,
                reserveMember);
        // error if reserved number is larger than available vacancy
        if (vacantCount < reserveMember) {
            ResultMessages message = ResultMessages.error().add(
                    MessageId.E_TR_0005);
            throw new BusinessException(message);
        }

        // * reserve
        PriceCalculateOutput priceCalculateOutput = priceCalculateService
                .calculatePrice(tourInfo.getBasePrice(), input.getAdultCount(),
                        input.getChildCount());

        Reserve reserve = beanMapper.map(input, Reserve.class);

        reserve.setTourInfo(tourInfo);
        reserve.setSumPrice(priceCalculateOutput.getSumPrice());
        reserve.setReservedDay(today.toDate());
        reserve.setTransfer(Reserve.NOT_TRANSFERED);

        reserveRepository.insert(reserve);
        log.debug("reserved {}", reserve);

        // * create output
        ReserveTourOutput tourReserveOutput = new ReserveTourOutput();
        tourReserveOutput.setPriceCalculateOutput(priceCalculateOutput);
        tourReserveOutput.setReserve(reserve);
        tourReserveOutput.setTourInfo(tourInfo);
        tourReserveOutput.setPaymentTimeLimit(tourInfo.getPaymentLimit()
                .toDate());
        tourReserveOutput.setCustomer(input.getCustomer());

        return tourReserveOutput;
    }

    @Override
    public void cancel(String reserveNo) throws BusinessException {
        Reserve reserve = findOneWithTourInfo(reserveNo);

        String transfer = reserve.getTransfer();
        if (Reserve.TRANSFERED.equals(transfer)) {
            ResultMessages message = ResultMessages.error().add(
                    MessageId.E_TR_0001);
            throw new BusinessException(message);
        }

        TourInfo info = tourInfoSharedService.findOneWithDetails(reserve
                .getTourInfo().getTourCode());

        // compare system date and payment limit.
        // if the payment limit has been exceeded,
        // navigate to business error screen
        if (tourInfoSharedService.isOverPaymentLimit(info)) {
            ResultMessages message = ResultMessages.error().add(
                    MessageId.E_TR_0002);
            throw new BusinessException(message);
        }

        // cancel the reservation
        // reserve = reserveRepository.findForUpdate(reserveNo); TODO
        reserve = reserveRepository.findOneForUpdate(reserveNo);
        if (reserve != null) {
            reserveRepository.delete(reserveNo);
        } else {
            ResultMessages message = ResultMessages.error().add(
                    MessageId.E_TR_0003);
            throw new BusinessException(message);
        }
        log.debug("canceled reserveNo={}", reserveNo);
    }

    @Override
    public ReservationUpdateOutput update(
            ReservationUpdateInput input) throws BusinessException {
        Reserve reserve = findOneWithTourInfo(input.getReserveNo());

        beanMapper.map(input, reserve, "reserve_map_nonnull");

        TourInfo info = reserve.getTourInfo();
        PriceCalculateOutput price = priceCalculateService.calculatePrice(info
                .getBasePrice(), input.getAdultCount(), input.getChildCount());

        reserve.setSumPrice(price.getSumPrice());
        reserveRepository.update(reserve);

        ReservationUpdateOutput output = new ReservationUpdateOutput();
        output.setReserve(reserve);
        output.setPriceCalculateOutput(price);
        output.setPaymentTimeLimit(info.getPaymentLimit().toDate());

        return output;
    }

}
