package com.cic.pas.common.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cic.domain.PomsCalculateAlterRecord;
import com.cic.pas.application.DBVisitService;
import com.cic.pas.common.util.StringUtils;
import com.cic.pas.common.util.Util;

/**
 * @author wz
 * 测量点
 */
public class PointDevice implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -7321598040130269040L;
    /**
     * 主键
     */
    private String id;
    /**
     * 测点名称
     */
    private String name;
    /**
     * 表计编码
     */
    private String ctdCode;
    private String ctdName;
    private String ctmType;
    private String systemCode;

    public Map getFormatMap() {
        return formatMap;
    }

    public void setFormatMap(Map formatMap) {
        this.formatMap = formatMap;
    }

    private Map formatMap;

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getCtmType() {
        return ctmType;
    }

    public void setCtmType(String ctmType) {
        this.ctmType = ctmType;
    }

    public String getCtdName() {
        return ctdName;
    }

    public void setCtdName(String ctdName) {
        this.ctdName = ctdName;
    }

    /**
     * 测点简称
     */
    private String symbols;
    /**
     * 测点单位
     */
    private String units;
    /**
     * 测点公式
     */
    private String formular;
    /**
     * 是否需要存储
     */
    private int issave;//1 是 0否

    /**
     * 是否为采集值
     *
     * @return
     */
    private int isCollect;//1 是  0 否

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public int getIsPlcAddress() {
        return isPlcAddress;
    }

    public void setIsPlcAddress(int isPlcAddress) {
        this.isPlcAddress = isPlcAddress;
    }

    /*是否为PLC地址*/
    private int isPlcAddress;// 1 是  0 否
    /***
     * 寄存器类型
     */
    private int storageType;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getStorageType() {
        return storageType;
    }

    public void setStorageType(int storageType) {
        this.storageType = storageType;
    }

    public boolean isBoor() {
        return boor;
    }

    public void setBoor(boolean boor) {
        this.boor = boor;
    }

    /**
     * 测点读写类型 0 不允许写入  1允许写入
     */
    private int rwType;

    public int getRwType() {
        return rwType;
    }

    public void setRwType(int rwType) {
        this.rwType = rwType;
    }

    public int getIsBit() {
        return isBit;
    }

    public void setIsBit(int isBit) {
        this.isBit = isBit;
    }

    /*是否为位数据  1是   2否  */
    private int isBit;
    /**
     * 测点地址
     */
    private int plcAddress;
    /**
     * modbus 地址
     **/
    private BigDecimal modAddress;

    public BigDecimal getModWAddress() {
        return modWAddress;
    }

    public void setModWAddress(BigDecimal modWAddress) {
        this.modWAddress = modWAddress;
    }

    /**
     * 写入地址
     */
    private BigDecimal modWAddress;
    /**
     * 写入FUNCTION
     */
    private int modWFunction;
    /**
     * 写入长度
     */
    private int modWlength;
    /**
     * 写入数据类型
     */
    private int modWType;

    public int getModWFunction() {
        return modWFunction;
    }

    public void setModWFunction(int modWFunction) {
        this.modWFunction = modWFunction;
    }

    public int getModWlength() {
        return modWlength;
    }

    public void setModWlength(int modWlength) {
        this.modWlength = modWlength;
    }

    public int getModWType() {
        return modWType;
    }

    public void setModWType(int modWType) {
        this.modWType = modWType;
    }

    public String getModWFormular() {
        return modWFormular;
    }

    public void setModWFormular(String modWFormular) {
        this.modWFormular = modWFormular;
    }

    /**
     * 写入数据格式化
     */
    private String modWFormular;

    public BigDecimal getModAddress() {
        return modAddress;
    }

    public void setModAddress(BigDecimal modAddress) {
        this.modAddress = modAddress;
    }

    /**
     * 测点数据字节数
     */
    private int pointLen;
    //是否计算CT
    private int isCt = 0;
    //是否计算PT
    private int isPt = 0;

    public int getIsCt() {
        return isCt;
    }

    public void setIsCt(int isCt) {
        this.isCt = isCt;
    }

    public int getIsPt() {
        return isPt;
    }

    public void setIsPt(int isPt) {
        this.isPt = isPt;
    }

    public int getPlcAddress() {
        return plcAddress;
    }

    public void setPlcAddress(int plcAddress) {
        this.plcAddress = plcAddress;
    }

    public int getPointLen() {
        return pointLen;
    }

    public void setPointLen(int pointLen) {
        this.pointLen = pointLen;
    }

    private int mmpType;//测点类型  0 只读 1可写

    public int getMmpType() {
        return mmpType;
    }

    public void setMmpType(int mmpType) {
        this.mmpType = mmpType;
    }

    /**
     * 存储周期
     */
    private String save_interval;
    /**
     * 测点存储方式
     */
    private String save_style;
    /**
     * 测点存储类型
     */
    private String statistic_type;
    /**
     * 测点上限
     */
    private Double up_line;
    /**
     * 测点上上限
     */
    private Double uper_line;
    /**
     * 测点上上限发生时间
     */
    private String uper_time;
    /**
     * 测点下限
     */
    private Double down_line;
    /**
     * 测点下下限
     */
    private Double downer_line;
    /**
     * 测点下下限发生时间
     */
    private String downer_time;

    /**
     * 测点是否开启报警
     */
    private int mmpIsAlarm;
    /**
     * 报警类型   1 故障（报警） 2 超上限   3 下限
     */
    private int alarmType;
    /**
     * 测点当前报警状态  0 无报警   1 正在报警
     */
    private int mmpAlarmStatus = 0;
    /**
     * 测点标准值
     */
    private Double standard_val = 0.0;
    /**
     * 测点排序
     */
    private int orders;
    /**
     * 测点最大值
     */
    private BigDecimal max_value;
    /**
     * 最大值时间
     */
    private String max_time;
    /**
     * 测点最小值
     */
    private BigDecimal min_value;
    /**
     * 最小值时间
     */
    private String min_time;
    /**
     * 测点增量值
     */
    private BigDecimal increval;
    //测点值
    private BigDecimal value = new BigDecimal(0);
    private String showValue;

    public String getShowValue() {
        return showValue;
    }

    public void setShowValue(String showValue) {
        this.showValue = showValue;
    }

    private Double dayValue;
    private Double monthValue;
    /**
     * 测点编码
     */
    private String code;

    public BigDecimal getLastPointValue() {
        return lastPointValue;
    }

    public void setLastPointValue(BigDecimal lastPointValue) {
        this.lastPointValue = lastPointValue;
    }

    /**
     * 计量表计主键
     */
    private String meterId;//修改字符类型
    //上一个存储值
    private BigDecimal lastPointValue = new BigDecimal(0);
    //上一个测点值
    private BigDecimal previousValue;
    //存储时是否计算差值
    private int isCalculate = 0;

    private int errorNum;

    public int getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(int errorNum) {
        this.errorNum = errorNum;
    }

    public int getIsCalculate() {
        return isCalculate;
    }

    public void setIsCalculate(int isCalculate) {
        this.isCalculate = isCalculate;
    }

    /**
     * 采数时间
     */
    private String time;
    private String dayTime;
    private String monthTime;

    /**
     * 测点变化时间
     */
    private String change_time;

    //private java.sql.Timestamp temp_time;

    //曲线数据
    private List<Object> curveData;
    //曲线点数
    private int dianshu;

    private boolean boor = true;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        if (previousValue == null) {
            try {
                previousValue = value;
                setMax_value(value);
                setMin_value(value);
                setIncreval(value);
                if (time != null) {
                    setMax_time(time);
                    setMin_time(time);
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            this.value = value;

        } else {
            try {
                if (value.compareTo(getMax_value()) == 1 && time != null) {
                    setMax_value(value);
                    setMax_time(time);
                }
                if (value.compareTo(getMin_value()) == -1 && time != null) {
                    setMin_value(value);
                    setMin_time(time);
                }
                setIncreval(value.subtract(previousValue));
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            previousValue = this.value;
            this.value = value;
        }
        boolean isAlarmFlag = false;
        BigDecimal setAlarmValue = null;
        if (mmpIsAlarm == 1) {
            if (mmpAlarmStatus == 1) {//正在报警   判断报警是否恢复
                if (alarmType == 1) { // 开关量
                    if (value.intValue() == 0) {// 开关量报警恢复
                        isAlarmFlag = true;
                        setAlarmValue = new BigDecimal(up_line);
                    }
                } else {
                    if (value.doubleValue() <= up_line && value.doubleValue() >= down_line) {
                        isAlarmFlag = true;
                        if (alarmType == 2) {//上限
                            setAlarmValue = new BigDecimal(up_line);
                        } else {//下限
                            setAlarmValue = new BigDecimal(down_line);
                        }
                    }
                }
            } else {// 正常状态   判断是否超限
                if (mmpIsAlarm == 1) {
                    if (isBit == 1) {//开关量
                        if (value.intValue() == 1) {// 开关量报警
                            alarmType = 1;
                            isAlarmFlag = true;
                            setAlarmValue = new BigDecimal(up_line);
                        }
                    } else {
                        if (value.doubleValue() > up_line) {
                            alarmType = 2;
                            isAlarmFlag = true;
                            setAlarmValue = new BigDecimal(up_line);
                        }
                        if (value.doubleValue() < down_line) {
                            alarmType = 3;
                            isAlarmFlag = true;
                            setAlarmValue = new BigDecimal(down_line);
                        }
                    }
                }
            }
        }
        if (isAlarmFlag) {
            String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            PomsCalculateAlterRecord record = new PomsCalculateAlterRecord();
            record.setCtdCodes(ctdCode);
            record.setCtdName(ctdName);
            record.setMmpCodes(code);
            record.setMmpName(name);
            mmpAlarmStatus = mmpAlarmStatus == 0 ? 1 : 0;
            record.setAlterLevel(mmpAlarmStatus);
            record.setAlterType(alarmType);
            record.setAlterValue(value);
            record.setSetValue(setAlarmValue);
            record.setDateTime(dateTime);
            DBVisitService.batchInsertAlarm(record);
            isAlarmFlag = false;
        }
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(BigDecimal previousValue) {

        this.previousValue = previousValue;
    }

    public String getSymbols() {
        return symbols;
    }

    public void setSymbols(String symbols) {
        this.symbols = symbols;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getFormular() {
        if (formular == null || formular == "") {
            formular = "/1";
        }
        return formular;
    }

    public void setFormular(String formular) {
        if(isBit==1) {
            formatMap=new HashMap();
            if (formular.indexOf(",") > 0) {
                String[] keyValues = formular.split(",");
                for (String keyValue : keyValues) {
                    if (keyValue.indexOf("=") > 0) {
                        String[] keyAndValue = keyValue.split("=");
                        formatMap.put(keyAndValue[0], keyAndValue[1]);
                    }
                }
            }
        }else{
            formatMap=new HashMap();
        }
        this.formular = formular;
    }

    public int getIssave() {
        return issave;
    }

    public void setIssave(int issave) {
        this.issave = issave;
    }

    public String getSave_interval() {
        return save_interval;
    }

    public void setSave_interval(String saveInterval) {
        save_interval = saveInterval;
    }

    public String getSave_style() {
        return save_style;
    }

    public void setSave_style(String saveStyle) {
        save_style = saveStyle;
    }

    public String getStatistic_type() {
        return statistic_type;
    }

    public void setStatistic_type(String statisticType) {
        statistic_type = statisticType;
    }

    public Double getUper_line() {
        return uper_line;
    }

    public void setUper_line(Double uperLine) {
        uper_line = uperLine;
    }

    public Double getDowner_line() {
        return downer_line;
    }

    public void setDowner_line(Double downerLine) {
        downer_line = downerLine;
    }

    public Double getStandard_val() {
        return standard_val;
    }

    public void setStandard_val(Double standardVal) {
        standard_val = standardVal;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public BigDecimal getMax_value() {
        return max_value;
    }

    public void setMax_value(BigDecimal maxValue) {
        max_value = maxValue;
    }

    public String getMax_time() {
        return max_time;
    }

    public void setMax_time(String maxTime) {
        max_time = maxTime;
    }

    public BigDecimal getMin_value() {
        return min_value;
    }

    public void setMin_value(BigDecimal minValue) {
        min_value = minValue;
    }

    public String getMin_time() {
        return min_time;
    }

    public void setMin_time(String minTime) {
        min_time = minTime;
    }

    public BigDecimal getIncreval() {
        return increval;
    }

    public void setIncreval(BigDecimal increval) {
        this.increval = increval;
    }

    public String getUper_time() {
        return uper_time;
    }

    public void setUper_time(String uperTime) {
        uper_time = uperTime;
    }

    public String getDowner_time() {
        return downer_time;
    }

    public void setDowner_time(String downerTime) {
        downer_time = downerTime;
    }

    public Double getUp_line() {
        return up_line;
    }

    public void setUp_line(Double upLine) {
        up_line = upLine;
    }

    public Double getDown_line() {
        return down_line;
    }

    public void setDown_line(Double downLine) {
        down_line = downLine;
    }

    public String getChange_time() {
        return change_time;
    }

    public void setChange_time(String changeTime) {
        change_time = changeTime;
        //swap(changeTime);
    }

    /*private void swap (java.sql.Timestamp changeTime){
        if(temp_time != null && temp_time.equals(changeTime)){
            setChange_time(null);
        }
        temp_time = changeTime;
    }*/
    public List<Object> getCurveData() {
        return curveData;
    }

    public void setCurveData(List<Object> curveData) {
        this.curveData = curveData;
        if (this.dianshu == 0) return;
        if (this.curveData.size() == dianshu) {
            synchronized (this) {
                this.notifyAll();
                this.dianshu = 0;
            }
        }
    }

    public Double getDayValue() {
        return dayValue;
    }

    public void setDayValue(Double dayValue) {
        this.dayValue = dayValue;
    }

    public Double getMonthValue() {
        return monthValue;
    }

    public void setMonthValue(Double monthValue) {
        this.monthValue = monthValue;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    public String getMonthTime() {
        return monthTime;
    }

    public void setMonthTime(String monthTime) {
        this.monthTime = monthTime;
    }

    public int getDianshu() {
        return dianshu;
    }

    public void setDianshu(int dianshu) {
        this.dianshu = dianshu;
    }

    public String getCtdCode() {
        return ctdCode;
    }

    public void setCtdCode(String ctdCode) {
        this.ctdCode = ctdCode;
    }

    public int getMmpIsAlarm() {
        return mmpIsAlarm;
    }

    public void setMmpIsAlarm(int mmpIsAlarm) {
        this.mmpIsAlarm = mmpIsAlarm;
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
}
