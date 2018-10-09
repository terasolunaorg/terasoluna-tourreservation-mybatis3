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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terasoluna.tourreservation.domain.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.joda.time.DateTime;

@Data
@NoArgsConstructor
public class TourInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tourCode;

    private Date plannedDay;

    private String planNo;

    private String tourName;

    private int tourDays;

    private Date depDay;

    private int avaRecMax;

    private int basePrice;

    private String conductor;

    private String tourAbs;

    private Departure departure;

    private Arrival arrival;

    private Accommodation accommodation;

    public TourInfo(String tourCode) {
        this.tourCode = tourCode;
    }

    public DateTime getPaymentLimit() {
        DateTime paymentLimit = new DateTime(this.getDepDay());
        return paymentLimit.minusDays(7);
    }

}
