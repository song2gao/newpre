package com.cic.pas.common.bean;

import java.math.BigDecimal;

public class SystemParams {

    private String eulId;

    private String eulName;

    private String eusCode;

    private String eusName;

    public String getEulId() {
        return eulId;
    }

    public void setEulId(String eulId) {
        this.eulId = eulId;
    }

    public String getEulName() {
        return eulName;
    }

    public void setEulName(String eulName) {
        this.eulName = eulName;
    }

    private String paramCode;

    private String paramName;

    private String paramUnit;

    private int paramType;

    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getParamType() {
        return paramType;
    }

    public void setParamType(int paramType) {
        this.paramType = paramType;
    }

    private BigDecimal paramValue=new BigDecimal("0");

    public String getEusCode() {
        return eusCode;
    }

    public void setEusCode(String eusCode) {
        this.eusCode = eusCode;
    }

    public String getEusName() {
        return eusName;
    }

    public void setEusName(String eusName) {
        this.eusName = eusName;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamUnit() {
        return paramUnit;
    }

    public void setParamUnit(String paramUnit) {
        this.paramUnit = paramUnit;
    }

    public BigDecimal getParamValue() {
        return paramValue;
    }

    public void setParamValue(BigDecimal paramValue) {
        this.paramValue = paramValue;
    }

    public String getCalCode() {
        return calCode;
    }

    public void setCalCode(String calCode) {
        this.calCode = calCode;
    }

    public String getMmpCode() {
        return mmpCode;
    }

    public void setMmpCode(String mmpCode) {
        this.mmpCode = mmpCode;
    }

    private String calCode;

    private String mmpCode;
}
