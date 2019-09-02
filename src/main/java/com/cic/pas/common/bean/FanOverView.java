package com.cic.pas.common.bean;

import java.util.List;
import java.util.Map;

public class FanOverView {

    private String eulCode;

    private String eulName;

    private String systemCode;

    private String systemName;

    private int connectState;

    private String facilityCode;

    private String facilityName;

    private String meterCode;

    private String preMmpCode;

    private List<Map<String,Object>> params;

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getMeterCode() {
        return meterCode;
    }

    public void setMeterCode(String meterCode) {
        this.meterCode = meterCode;
    }

    public List<Map<String, Object>> getParams() {
        return params;
    }

    public void setParams(List<Map<String, Object>> params) {
        this.params = params;
    }

    public String getEulCode() {
        return eulCode;
    }

    public void setEulCode(String eulCode) {
        this.eulCode = eulCode;
    }

    public String getEulName() {
        return eulName;
    }

    public void setEulName(String eulName) {
        this.eulName = eulName;
    }

    public String getPreMmpCode() {
        return preMmpCode;
    }

    public void setPreMmpCode(String preMmpCode) {
        this.preMmpCode = preMmpCode;
    }

    public String getFacilityCode() {
        return facilityCode;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public int getConnectState() {
        return connectState;
    }

    public void setConnectState(int connectState) {
        this.connectState = connectState;
    }
}
