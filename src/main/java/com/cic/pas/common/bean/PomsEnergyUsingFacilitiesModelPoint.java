package com.cic.pas.common.bean;

import com.cic.domain.PomsCalculateAlterRecord;
import com.cic.pas.application.DBVisitService;
import com.cic.pas.dao.BussinessConfig;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PomsEnergyUsingFacilitiesModelPoint {
    private Integer id;

    private String facilitiesModelCode;

    private String systemCode;

    private String systemName;

    private String facilityCode;

    private String facilityName;

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

    private String meterCode;

    private String measurMmpCode;

    private BigDecimal value;

    private BigDecimal upValue;

    private BigDecimal downValue;

    private BigDecimal maxValue;

    private String maxDate;

    private BigDecimal minValue;

    private String minDate;

    private BigDecimal avgValue;

    private int isSave;

    private int isAlarm;

    private int isShowIndex;

    private int isShowName;

    /**
     * 报警类型   1 故障（报警） 2 超上限   3 下限
     */
    private int alarmType;
    /**
     * 测点当前报警状态  0 无报警   1 正在报警
     */
    private int mmpAlarmStatus = 0;
    /**
     * 是否为总览数据
     */
    private int isOverViewData;

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
    public void setAlarmData(PomsEnergyUsingSystem system,PomsEnergyUsingFacilities facilities, PomsEnergyUsingFacilitiesModelPoint point) {
        boolean isAlarmFlag = false;
        BigDecimal setAlarmValue = null;
        if (point.getIsAlarm() == 1) {
            if (mmpAlarmStatus == 1) {//正在报警   判断报警是否恢复
                if (alarmType == 1) { // 开关量
                    if (value.intValue() == 0) {// 开关量报警恢复
                        isAlarmFlag = true;
                        setAlarmValue =upValue;
                    }
                } else {
                    if (value.compareTo(upValue) <=0  && value.compareTo(downValue) >=0 ) {
                        isAlarmFlag = true;
                        if (alarmType == 2) {//上限
                            setAlarmValue =upValue;
                        } else {//下限
                            setAlarmValue =downValue;
                        }
                    }
                }
            } else {// 正常状态   判断是否超限
                if (point.getIsAlarm() == 1) {
                    if (isBit == 1) {//开关量
                        if (value.intValue() == 1) {// 开关量报警
                            alarmType = 1;
                            isAlarmFlag = true;
                            setAlarmValue = upValue;
                        }
                    } else {
                        if (value.compareTo(upValue) > 0) {
                            alarmType = 2;
                            isAlarmFlag = true;
                            setAlarmValue =upValue;
                        }
                        if (value.compareTo(downValue) < 0) {
                            alarmType = 3;
                            isAlarmFlag = true;
                            setAlarmValue =downValue;
                        }
                    }
                }
            }
        }else{
            if (mmpAlarmStatus == 1) {//正在报警   关闭报警
                isAlarmFlag = true;
            }
        }
        if (isAlarmFlag) {
            String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            PomsCalculateAlterRecord record = new PomsCalculateAlterRecord();
            record.setSystemCode(facilities.getSystemCode());
            record.setEusCodes(facilities.getFacilitiesCode());
            record.setEusName(facilities.getFacilitiesName());
            record.setMmpCodes(point.getMmpCode());
            record.setMmpName(point.getMmpName());
            mmpAlarmStatus = mmpAlarmStatus == 0 ? 1 : 0;
            record.setAlterLevel(mmpAlarmStatus);
            record.setAlterType(alarmType);
            record.setAlterValue(value);
            record.setSystemName(system.getSystemName());
            record.setSetValue(setAlarmValue);
            record.setDateTime(dateTime);
            String text=system.getSystemName()+"==="+facilities.getFacilitiesName()+"==="+point.getMmpName();
            if(alarmType==1){
                text+=":报警(故障)";
            }else if(alarmType==2){
                text+=":超上限";
            }else{
                text+=":超下限";
            }
            record.setAlarmText(text);
            String key=facilities.getSystemCode()+"-"+facilities.getFacilitiesCode()+"-"+point.getMmpCode();
            if(mmpAlarmStatus==1){//正在报警
                BussinessConfig.alarmMap.put(key,record);
            }else{//报警恢复
                if(BussinessConfig.alarmMap.containsKey(key)){
                    BussinessConfig.alarmMap.remove(key);
                }
            }
            DBVisitService.batchInsertAlarm(record);
            isAlarmFlag = false;
        }
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

    public int getIsShowName() {
        return isShowName;
    }

    public void setIsShowName(int isShowName) {
        this.isShowName = isShowName;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public BigDecimal getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(BigDecimal maxValue) {
        this.maxValue = maxValue;
    }

    public String getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }

    public BigDecimal getMinValue() {
        return minValue;
    }

    public void setMinValue(BigDecimal minValue) {
        this.minValue = minValue;
    }

    public String getMinDate() {
        return minDate;
    }

    public void setMinDate(String minDate) {
        this.minDate = minDate;
    }

    public BigDecimal getAvgValue() {
        return avgValue;
    }

    public void setAvgValue(BigDecimal avgValue) {
        this.avgValue = avgValue;
    }

    public int getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }

    public int getMmpAlarmStatus() {
        return mmpAlarmStatus;
    }

    public void setMmpAlarmStatus(int mmpAlarmStatus) {
        this.mmpAlarmStatus = mmpAlarmStatus;
    }

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

    public int getIsOverViewData() {
        return isOverViewData;
    }

    public void setIsOverViewData(int isOverViewData) {
        this.isOverViewData = isOverViewData;
    }
}