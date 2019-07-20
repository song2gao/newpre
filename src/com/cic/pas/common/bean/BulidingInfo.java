package com.cic.pas.common.bean;

import java.math.BigDecimal;

public class BulidingInfo {

    private String bulidingId;

    private String bulidingName;

    private String bulidingAddress;

    private int bulidingPerson;

    private BigDecimal buldingAres;

    public String getBulidingName() {
        return bulidingName;
    }

    public void setBulidingName(String bulidingName) {
        this.bulidingName = bulidingName;
    }

    public String getBulidingAddress() {
        return bulidingAddress;
    }

    public void setBulidingAddress(String bulidingAddress) {
        this.bulidingAddress = bulidingAddress;
    }

    public int getBulidingPerson() {
        return bulidingPerson;
    }

    public void setBulidingPerson(int bulidingPerson) {
        this.bulidingPerson = bulidingPerson;
    }

    public BigDecimal getBuldingAres() {
        return buldingAres;
    }

    public void setBuldingAres(BigDecimal buldingAres) {
        this.buldingAres = buldingAres;
    }

    public String getBulidingId() {

        return bulidingId;
    }

    public void setBulidingId(String bulidingId) {
        this.bulidingId = bulidingId;
    }
}
