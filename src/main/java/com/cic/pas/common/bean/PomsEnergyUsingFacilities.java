package com.cic.pas.common.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PomsEnergyUsingFacilities {
    private Integer id;

    private String systemCode;

    private String facilitiesModelCode;

    private String facilitiesTypeCode;

    private String[] preModelCode;

    private String facilitiesCode;

    private String facilitiesName;

    private Integer facilitiesOffset;

    private List<PomsEnergyUsingFacilitiesModelPoint> pointList;

    private String facilitiesImg;

    private String value;

    private int state=4;

    private String stateFormat;

    private int isFocus;

    private String facilitiesBackups;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode == null ? null : systemCode.trim();
    }

    public String getFacilitiesModelCode() {
        return facilitiesModelCode;
    }

    public void setFacilitiesModelCode(String facilitiesModelCode) {
        this.facilitiesModelCode = facilitiesModelCode == null ? null : facilitiesModelCode.trim();
    }

    public String getFacilitiesCode() {
        return facilitiesCode;
    }

    public void setFacilitiesCode(String facilitiesCode) {
        this.facilitiesCode = facilitiesCode == null ? null : facilitiesCode.trim();
    }

    public String getFacilitiesName() {
        return facilitiesName;
    }

    public void setFacilitiesName(String facilitiesName) {
        this.facilitiesName = facilitiesName == null ? null : facilitiesName.trim();
    }

    public Integer getFacilitiesOffset() {
        return facilitiesOffset;
    }

    public void setFacilitiesOffset(Integer facilitiesOffset) {
        this.facilitiesOffset = facilitiesOffset;
    }

    public String getFacilitiesBackups() {
        return facilitiesBackups;
    }

    public void setFacilitiesBackups(String facilitiesBackups) {
        this.facilitiesBackups = facilitiesBackups == null ? null : facilitiesBackups.trim();
    }

    public List<PomsEnergyUsingFacilitiesModelPoint> getPointList() {
        return pointList;
    }

    public void setPointList(List<PomsEnergyUsingFacilitiesModelPoint> pointList) {
        this.pointList = pointList;
    }

    public String[] getPreModelCode() {
        return preModelCode;
    }

    public void setPreModelCode(String[] preModelCode) {
        this.preModelCode = preModelCode;
    }

    public String getFacilitiesTypeCode() {
        return facilitiesTypeCode;
    }

    public void setFacilitiesTypeCode(String facilitiesTypeCode) {
        this.facilitiesTypeCode = facilitiesTypeCode;
    }

    public String getFacilitiesImg() {
        return facilitiesImg;
    }

    public void setFacilitiesImg(String facilitiesImg) {
        this.facilitiesImg = facilitiesImg;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        if(stateFormat!=null){
            String[] format=stateFormat.split(",");
            Map<String,String> map=new HashMap<>();
            for(String s:format){
                String[] value=s.split("=");
                map.put(value[0],value[1]);
            }
            setValue(map.get(state+""));
        }
    }

    public String getStateFormat() {
        return stateFormat;
    }

    public void setStateFormat(String stateFormat) {
        this.stateFormat = stateFormat;
    }

    public int getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(int isFocus) {
        this.isFocus = isFocus;
    }
}