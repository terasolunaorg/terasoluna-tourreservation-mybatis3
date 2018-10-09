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
package org.terasoluna.tourreservation.domain.repository.tourinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.tourreservation.domain.model.TourInfo;

public interface TourInfoRepository {

    TourInfo findOneWithDetails(String tourCode);

    TourInfo findOneWithDetailsForUpdate(String tourCode);

    List<TourInfo> findPageBySearchCriteria(
            @Param("criteria") TourInfoSearchCriteria criteria,
            @Param("pageable") Pageable pageable);

    long countBySearchCriteria(
            @Param("criteria") TourInfoSearchCriteria criteria);
}
