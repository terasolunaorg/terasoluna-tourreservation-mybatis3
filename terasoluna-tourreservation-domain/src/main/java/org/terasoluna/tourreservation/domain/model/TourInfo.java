/*
 * Copyright (C) 2013-2015 terasoluna.org
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

import org.joda.time.DateTime;

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

    public TourInfo() {
    }

    public TourInfo(String tourCode) {
        this.tourCode = tourCode;
    }

    public TourInfo(String tourCode, Date plannedDay, String planNo,
            String tourName, int tourDays, Date depDay, int avaRecMax,
            int basePrice, String conductor) {
        this.tourCode = tourCode;
        this.plannedDay = plannedDay;
        this.planNo = planNo;
        this.tourName = tourName;
        this.tourDays = tourDays;
        this.depDay = depDay;
        this.avaRecMax = avaRecMax;
        this.basePrice = basePrice;
        this.conductor = conductor;
    }

    public String getTourCode() {
        return tourCode;
    }

    public void setTourCode(String tourCode) {
        this.tourCode = tourCode;
    }

    public Date getPlannedDay() {
        return plannedDay;
    }

    public void setPlannedDay(Date plannedDay) {
        this.plannedDay = plannedDay;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public int getTourDays() {
        return tourDays;
    }

    public void setTourDays(int tourDays) {
        this.tourDays = tourDays;
    }

    public Date getDepDay() {
        return depDay;
    }

    public void setDepDay(Date depDay) {
        this.depDay = depDay;
    }

    public int getAvaRecMax() {
        return avaRecMax;
    }

    public void setAvaRecMax(int avaRecMax) {
        this.avaRecMax = avaRecMax;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public String getTourAbs() {
        return tourAbs;
    }

    public void setTourAbs(String tourAbs) {
        this.tourAbs = tourAbs;
    }

    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    public Arrival getArrival() {
        return arrival;
    }

    public void setArrival(Arrival arrival) {
        this.arrival = arrival;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public DateTime getPaymentLimit() {
        DateTime paymentLimit = new DateTime(this.getDepDay());
        return paymentLimit.minusDays(7);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tourCode != null ? tourCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are
        // not set
        if (!(object instanceof TourInfo)) {
            return false;
        }
        TourInfo other = (TourInfo) object;
        if ((this.tourCode == null && other.tourCode != null)
                || (this.tourCode != null && !this.tourCode
                        .equals(other.tourCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.terasoluna.tourreservation.domain.model.TourInfo[ tourCode="
                + tourCode + " ]";
    }

}
