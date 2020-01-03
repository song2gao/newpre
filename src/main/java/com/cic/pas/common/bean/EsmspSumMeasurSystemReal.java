package com.cic.pas.common.bean;

import java.math.BigDecimal;

/**
 * 版权所有(C)2013-2014 CIC
 * 公司名称：众智盈鑫能源科技（北京）有限公司
 * 版本:V 1.0
 * 文件名：com.cic.pas.procotol @NAME: ByteModBusRtuDecoder
 * 作者: 高嵩
 * 创建时间: 2020/1/3 16:24
 * 修改时间：2020/1/3 16:24
 * 功能描述
 */
public class EsmspSumMeasurSystemReal {
    private String id;

    private String euiCode;

    private int isSave;

    private String systemCode;

    private String facilityCode;

    private String mmpCode;

    private String dateCode;

    private BigDecimal value;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getEuiCode() {
        return euiCode;
    }

    public void setEuiCode(String euiCode) {
        this.euiCode = euiCode == null ? null : euiCode.trim();
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode == null ? null : systemCode.trim();
    }

    public String getMmpCode() {
        return mmpCode;
    }

    public void setMmpCode(String mmpCode) {
        this.mmpCode = mmpCode == null ? null : mmpCode.trim();
    }

    public String getDateCode() {
        return dateCode;
    }

    public void setDateCode(String dateCode) {
        this.dateCode = dateCode == null ? null : dateCode.trim();
    }

    public int getIsSave() {
        return isSave;
    }

    public void setIsSave(int isSave) {
        this.isSave = isSave;
    }

    public String getFacilityCode() {
        return facilityCode;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
