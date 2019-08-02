package com.cic.pas.common.bean;

public class PomsEnergyUsingFacilitiesModel {
    private Integer id;

    private String systemCode;

    private String facilitiesModelCode;

    private String facilitiesModelName;

    private String facilitiesModelBackups;

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

    public String getFacilitiesModelName() {
        return facilitiesModelName;
    }

    public void setFacilitiesModelName(String facilitiesModelName) {
        this.facilitiesModelName = facilitiesModelName == null ? null : facilitiesModelName.trim();
    }

    public String getFacilitiesModelBackups() {
        return facilitiesModelBackups;
    }

    public void setFacilitiesModelBackups(String facilitiesModelBackups) {
        this.facilitiesModelBackups = facilitiesModelBackups == null ? null : facilitiesModelBackups.trim();
    }
}