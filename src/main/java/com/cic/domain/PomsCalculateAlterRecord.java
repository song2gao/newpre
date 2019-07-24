package com.cic.domain;

import java.math.BigDecimal;

public class PomsCalculateAlterRecord {
    private String ctdCodes;

    private String mmpCodes;

    private BigDecimal alterValue;

    private BigDecimal setValue;

    public BigDecimal getAlterValue() {
        return alterValue;
    }

    public void setAlterValue(BigDecimal alterValue) {
        this.alterValue = alterValue;
    }

    public BigDecimal getSetValue() {
        return setValue;
    }

    public void setSetValue(BigDecimal setValue) {
        this.setValue = setValue;
    }

    private String dateTime;

    private Integer alterLevel;

    private Integer alterType;

    private String ctdName;

    private String mmpName;

    public String getCtdCodes() {
        return ctdCodes;
    }

    public void setCtdCodes(String ctdCodes) {
        this.ctdCodes = ctdCodes == null ? null : ctdCodes.trim();
    }

    public String getMmpCodes() {
        return mmpCodes;
    }

    public void setMmpCodes(String mmpCodes) {
        this.mmpCodes = mmpCodes == null ? null : mmpCodes.trim();
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime == null ? null : dateTime.trim();
    }

    public Integer getAlterLevel() {
        return alterLevel;
    }

    public void setAlterLevel(Integer alterLevel) {
        this.alterLevel = alterLevel;
    }

    public Integer getAlterType() {
        return alterType;
    }

    public void setAlterType(Integer alterType) {
        this.alterType = alterType;
    }

    public String getCtdName() {
        return ctdName;
    }

    public void setCtdName(String ctdName) {
        this.ctdName = ctdName == null ? null : ctdName.trim();
    }

    public String getMmpName() {
        return mmpName;
    }

    public void setMmpName(String mmpName) {
        this.mmpName = mmpName == null ? null : mmpName.trim();
    }
}