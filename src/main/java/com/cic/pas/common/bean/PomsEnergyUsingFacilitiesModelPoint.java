package com.cic.pas.common.bean;

import java.math.BigDecimal;

public class PomsEnergyUsingFacilitiesModelPoint {
    private Integer id;

    private String facilitiesModelCode;

    private String mmpCode;

    private String mmpName;

    private String mmpUnit;

    private int mmpOrder;

    private int mmpType;

    private String measurModelCode;

    private String measurMmpCode;

    private BigDecimal value;

    private BigDecimal upValue;

    private BigDecimal downValue;

    private int isSave;

    private int isAlarm;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFacilitiesModelCode() {
        return facilitiesModelCode;
    }

    public void setFacilitiesModelCode(String facilitiesModelCode) {
        this.facilitiesModelCode = facilitiesModelCode == null ? null : facilitiesModelCode.trim();
    }

    public String getMmpCode() {
        return mmpCode;
    }

    public void setMmpCode(String mmpCode) {
        this.mmpCode = mmpCode == null ? null : mmpCode.trim();
    }

    public String getMmpName() {
        return mmpName;
    }

    public void setMmpName(String mmpName) {
        this.mmpName = mmpName == null ? null : mmpName.trim();
    }

    public String getMmpUnit() {
        return mmpUnit;
    }

    public void setMmpUnit(String mmpUnit) {
        this.mmpUnit = mmpUnit == null ? null : mmpUnit.trim();
    }

    public int getMmpOrder() {
        return mmpOrder;
    }

    public void setMmpOrder(int mmpOrder) {
        this.mmpOrder = mmpOrder;
    }

    public int getMmpType() {
        return mmpType;
    }

    public void setMmpType(int mmpType) {
        this.mmpType=mmpType;
    }

    public String getMeasurModelCode() {
        return measurModelCode;
    }

    public void setMeasurModelCode(String measurModelCode) {
        this.measurModelCode = measurModelCode == null ? null : measurModelCode.trim();
    }

    public String getMeasurMmpCode() {
        return measurMmpCode;
    }

    public void setMeasurMmpCode(String measurMmpCode) {
        this.measurMmpCode = measurMmpCode == null ? null : measurMmpCode.trim();
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getUpValue() {
        return upValue;
    }

    public void setUpValue(BigDecimal upValue) {
        this.upValue = upValue;
    }

    public BigDecimal getDownValue() {
        return downValue;
    }

    public void setDownValue(BigDecimal downValue) {
        this.downValue = downValue;
    }

    public int getIsSave() {
        return isSave;
    }

    public void setIsSave(int isSave) {
        this.isSave = isSave;
    }

    public int getIsAlarm() {
        return isAlarm;
    }

    public void setIsAlarm(int isAlarm) {
        this.isAlarm = isAlarm;
    }
}