package com.cic.pas.common.bean;

import com.cic.pas.dao.BussinessConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PomsEnergyUsingSystem {
    private int id;

    private String euiId;

    private String eulId;

    private String eulName;

    private String systemModelCode;

    private String systemModelName;

    private String systemCode;

    private String systemName;

    private String[] asstdCodes;

    private Map<String,Integer> asstdStates;

    private List<PomsEnergyUsingFacilities> facilitiyList;

    private String systemBackups;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id=id;
    }

    public String getEuiId() {
        return euiId;
    }

    public void setEuiId(String euiId) {
        this.euiId = euiId == null ? null : euiId.trim();
    }

    public String getEulId() {
        return eulId;
    }

    public void setEulId(String eulId) {
        this.eulId = eulId == null ? null : eulId.trim();
    }

    public String getEulName() {
        return eulName;
    }

    public void setEulName(String eulName) {
        this.eulName = eulName == null ? null : eulName.trim();
    }

    public String getSystemModelCode() {
        return systemModelCode;
    }

    public void setSystemModelCode(String systemModelCode) {
        this.systemModelCode = systemModelCode == null ? null : systemModelCode.trim();
    }

    public String getSystemModelName() {
        return systemModelName;
    }

    public void setSystemModelName(String systemModelName) {
        this.systemModelName = systemModelName == null ? null : systemModelName.trim();
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode == null ? null : systemCode.trim();
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName == null ? null : systemName.trim();
    }

    public String getSystemBackups() {
        return systemBackups;
    }

    public void setSystemBackups(String systemBackups) {
        this.systemBackups = systemBackups == null ? null : systemBackups.trim();
    }

    public List<PomsEnergyUsingFacilities> getFacilitiyList() {
        return facilitiyList;
    }

    public void setFacilitiyList(List<PomsEnergyUsingFacilities> facilitiyList) {
        this.facilitiyList = facilitiyList;
    }

    public String[] getAsstdCodes() {
        return asstdCodes;
    }

    public void setAsstdCodes(String[] asstdCodes) {
        this.asstdCodes = asstdCodes;
        this.asstdStates=new HashMap<>();
        for(String asstdCode:asstdCodes){
            for(TerminalDevice td:BussinessConfig.terminalList){
                if(asstdCode.equals(td.getCode())){
                    asstdStates.put(td.getName(),td.getIsOnline());
                }
            }
        }
    }

    public Map<String, Integer> getAsstdStates() {
        return asstdStates;
    }

    public void setAsstdStates(Map<String, Integer> asstdStates) {
        this.asstdStates = asstdStates;
    }
}