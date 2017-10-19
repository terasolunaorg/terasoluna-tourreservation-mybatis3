/*
 * Copyright (C) 2013-2016 NTT DATA Corporation
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

public class Accommodation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accomCode;

    private String accomName;

    private String accomTel;

    public Accommodation() {
    }

    public Accommodation(String accomCode) {
        this.accomCode = accomCode;
    }

    public Accommodation(String accomCode, String accomName, String accomTel) {
        this.accomCode = accomCode;
        this.accomName = accomName;
        this.accomTel = accomTel;
    }

    public String getAccomCode() {
        return accomCode;
    }

    public void setAccomCode(String accomCode) {
        this.accomCode = accomCode;
    }

    public String getAccomName() {
        return accomName;
    }

    public void setAccomName(String accomName) {
        this.accomName = accomName;
    }

    public String getAccomTel() {
        return accomTel;
    }

    public void setAccomTel(String accomTel) {
        this.accomTel = accomTel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accomCode != null ? accomCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are
        // not set
        if (!(object instanceof Accommodation)) {
            return false;
        }
        Accommodation other = (Accommodation) object;
        if ((this.accomCode == null && other.accomCode != null)
                || (this.accomCode != null && !this.accomCode.equals(
                        other.accomCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.terasoluna.tourreservation.domain.model.Accommodation[ accomCode="
                + accomCode + " ]";
    }

}
