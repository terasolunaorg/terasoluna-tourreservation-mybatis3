/*
 * Copyright(c) 2013 NTT DATA Corporation.
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
package org.terasoluna.tourreservation.domain.repository.reserve;

import java.util.List;
import java.util.Optional;

import org.terasoluna.tourreservation.domain.model.Reserve;

public interface ReserveRepository {

    Optional<Reserve> findById(String reserveNo);

    void insert(Reserve reserve);

    void update(Reserve reserve);

    void deleteById(String reserveNo);

    Reserve findOneForUpdate(String reserveNo);

    Reserve findOneWithDetail(String reserveNo);

    Long countReservedPersonSumByTourInfo(String tourCode);

    List<Reserve> findAllWithTourInfoByCustomer(String customerCode);
}
