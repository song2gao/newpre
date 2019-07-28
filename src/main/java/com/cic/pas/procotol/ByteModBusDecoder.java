package com.cic.pas.procotol;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cic.pas.common.bean.SystemParams;
import com.cic.pas.common.util.ModBusUtil;
import com.cic.pas.dao.BussinessConfig;
import org.apache.log4j.Logger;

import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.PointDevice;
import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.common.util.Util;

public class ByteModBusDecoder {
    private Logger logger = Logger.getLogger(ByteModBusDecoder.class);

    public void decoder(String terminalCode, String ctdCode, int start, byte[] data, String sendStr, String receivedStr) {
        for (TerminalDevice td : BussinessConfig.terminalList) {
            if (td.getCode().equals(terminalCode)) {
                for (MeterDevice md : td.getMeterList()) {
                    if (md.getCode().equals(ctdCode)) {
//                        md.setStatus(1);
                        int i = 0;
                        int lastlen = 0;
                        for (PointDevice pd : md.getPointDevice()) {
                            start += lastlen;
                            int address = pd.getModAddress().intValue() + md.getIncrease();
                            int pointAddress = ModBusUtil.getProtocolCodeAndAddress(address, pd.getIsPlcAddress());
                            if (pointAddress == start) {
                                int pointlen = pd.getPointLen();
                                if (pd.getMmpType() != 1) {
                                    pointlen = pointlen * 2;
                                }
                                if (i + pointlen > data.length) {
                                    break;
                                }
                                BigDecimal value = new BigDecimal(Util
                                        .bytesToValueRealOffset(data, i,
                                                pd.getMmpType()).toString());
                                if (pd.getIsCt() == 1) {
                                    value = value.multiply(new BigDecimal(md.getCt()));
                                }
                                if (pd.getIsPt() == 1) {
                                    value = value.multiply(new BigDecimal(md.getPt()));
                                }
                                String showValue = value + "";
                                try {
                                    if (pd.getIsBit() == 1) {
                                        try {
                                            if (pd.getFormatMap() != null) {
                                                showValue = pd.getFormatMap().get(value.intValue() + "").toString();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        value = Util.manageData(value, pd
                                                .getFormular());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (checkData(td, pd, value, md.getName(), sendStr, receivedStr)) {
                                    pd.setValue(value);
                                    pd.setShowValue(showValue);
                                    String dateTime = new SimpleDateFormat(
                                            "yyyy-MM-dd HH:mm:ss").format(new Date());
                                    pd.setTime(dateTime);
                                    if(pd.getCode().equals("10000")){//PLC采集表计通讯状态
                                        if(pd.getValue().intValue()>0) {
                                            md.setStatus(0);
                                        }else{
                                            md.setStatus(1);
                                            md.setLastCollectDate(dateTime);
                                        }
                                    }else {
                                        if(getOnlineStatus(md)){
                                            md.setStatus(1);
                                        }
                                        md.setLastCollectDate(dateTime);
                                    }
                                    setSystemParams(ctdCode, pd.getCode(), value);
//                                    System.out.println(md.getName() +"==>" + pd.getName() + ":" + pd.getValue());
                                }
                                lastlen = pd.getPointLen();
                                if (pd.getMmpType() == 1) {
                                    i += pd.getPointLen();
                                } else {
                                    i += pd.getPointLen() * 2;
                                }
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
    }
    public void setSystemParams(String ctdCode, String mmpCode, BigDecimal value) {
        for (SystemParams param : BussinessConfig.systemParams) {
            if (param.getCalCode().equals(ctdCode) && param.getMmpCode().equals(mmpCode)) {
                param.setParamValue(value.setScale(4, BigDecimal.ROUND_HALF_UP));
                param.setTime(new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
        }

    }
    public BigDecimal getPointValue(BigDecimal value, int point) {
        if (point == 0) {
            return value;
        } else {
            BigDecimal result = value;
            try {
                BigDecimal b = new BigDecimal(Math.pow(10, point));
                result = value.divide(b);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("取小数点出错" + "value:" + value + "point:" + point);
            }
            return result;
        }
    }

    private boolean checkData(TerminalDevice td, PointDevice pd, BigDecimal value, String mdName, String sendStr, String receivedStr) {
        if (pd.getValue() == null || pd.getValue().compareTo(BigDecimal.ZERO) == 0) {
            return true;
        } else {
            BigDecimal diff = value.subtract(pd.getValue());
            int upValue = 50000;
            int downValue = -50000;
            int errorMax = 3;
            if (pd.getIsCalculate() == 1) {
                errorMax = 10;
                upValue = 1000;
                downValue = 0;
            }
            if (diff.doubleValue() < downValue || diff.doubleValue() > upValue) {
                boolean result;
                StringBuffer sb = new StringBuffer();
                int errorNum = pd.getErrorNum();
                sb.append("===============================采集器:" + td.getName() + "====>表计:" + mdName + pd.getName() + "校验数据出错======================================\n\r" +
                        "======上次采集值:" + pd.getValue() + "======\r\n" +
                        "======本次采集值:" + value + "======\r\n");
                errorNum++;
                if (errorNum >= errorMax) {
                    pd.setErrorNum(0);
                    result = true;
                    sb.append("检验数次达到最大值：" + errorNum + ",本次保留\r\n");
                } else {
                    pd.setErrorNum(errorNum);
                    result = false;
                    sb.append("检验数次未达到最大值：" + errorMax + ",当前累计：" + errorNum + "本次舍弃\r\n");
                }
                sb.append("======原始报文======\r\n"
                        + sendStr + "\r\n"
                        + receivedStr);
                sb.append("\r\n==================================" + td.getName() + "end=================================================================");
                logger.info(sb.toString());
                return result;
            } else {
                pd.setErrorNum(0);
                return true;
            }
        }
    }

    public static boolean getOnlineStatus(MeterDevice md){
        for(PointDevice pd:md.getPointDevice()){
            if(pd.getCode().equals("10000")){
                if(pd.getValue().intValue()>0){
                    return false;
                }else{
                    return true;
                }
            }
        }
        return true;
    }
}
