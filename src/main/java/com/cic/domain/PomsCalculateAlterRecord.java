package com.cic.domain;

import java.math.BigDecimal;

public class PomsCalculateAlterRecord {
    private int id;

    private String systemCode;

    private String eusCodes;

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

    private String eusName;

    private String mmpName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getEusCodes() {
        return eusCodes;
    }

    public void setEusCodes(String eusCodes) {
        this.eusCodes = eusCodes;
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

    public String getEusName() {
        return eusName;
    }

    public void setEusName(String eusName) {
        this.eusName = eusName;
    }

    public String getMmpName() {
        return mmpName;
    }

    public void setMmpName(String mmpName) {
        this.mmpName = mmpName == null ? null : mmpName.trim();
    }
}