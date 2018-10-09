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

@Data
@NoArgsConstructor
public class Reserve implements Serializable {

    public static final String TRANSFERED = "1";

    public static final String NOT_TRANSFERED = "0";

    private static final long serialVersionUID = 1L;

    private String reserveNo;

    private Date reservedDay;

    private int adultCount;

    private int childCount;

    private String transfer;

    private int sumPrice;

    private String remarks;

    private TourInfo tourInfo;

    private Customer customer;

    public Reserve(String reserveNo) {
        this.reserveNo = reserveNo;
    }

}
