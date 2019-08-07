package com.cic.pas.common.bean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class PomsEnergyUsingFacilitiesModelPoint {
    private Integer id;

    private String facilitiesModelCode;

    private String mmpCode;

    private String mmpName;

    private String mmpUnit;

    private int mmpOrder;

    private int mmpType;

    private int isBit;

    private int rwType;

    private String formatStr;

    private Map<String,String> formatMap;

    private List<Option> options;

    private String facilityCode;

    private String meterCode;

    private String measurMmpCode;

    private BigDecimal value;

    private BigDecimal upValue;

    private BigDecimal downValue;

    private int isSave;

    private int isAlarm;

    private int isShowIndex;

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

    public int getIsBit() {
        return isBit;
    }

    public void setIsBit(int isBit) {
        this.isBit = isBit;
    }

    public String getMeterCode() {
        return meterCode;
    }

    public void setMeterCode(String meterCode) {
        this.meterCode = meterCode;
    }

    public int getRwType() {
        return rwType;
    }

    public void setRwType(int rwType) {
        this.rwType = rwType;
    }

    public String getFacilityCode() {
        return facilityCode;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }
    public int getIsShowIndex() {
        return isShowIndex;
    }
    public void setIsShowIndex(int isShowIndex) {
        this.isShowIndex = isShowIndex;
    }
    public String getFormatStr() {
        return formatStr;
    }
    public void setFormatStr(String formatStr) {
        this.formatStr = formatStr;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public Map<String, String> getFormatMap() {
        return formatMap;
    }

    public void setFormatMap(Map<String, String> formatMap) {
        this.formatMap = formatMap;
    }
}