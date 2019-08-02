package com.cic.pas.common.bean;

import java.util.List;

public class PomsEnergyUsingSystem {
    private int id;

    private String euiId;

    private String eulId;

    private String eulName;

    private String systemModelCode;

    private String systemModelName;

    private String systemCode;

    private String systemName;

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
}