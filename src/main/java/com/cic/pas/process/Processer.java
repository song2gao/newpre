package com.cic.pas.process;

import java.awt.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.cic.pas.common.bean.*;
import com.cic.pas.common.util.ArrayUtils;
import com.cic.pas.service.ServerContext;
import com.cic.pas.service.UpdateNoCollectValue;
import com.cic.pas.thread.GetDataThread;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.log4j.Logger;

import com.cic.pas.common.net.Message;
import com.cic.pas.common.net.ReturnMessage;
import com.cic.pas.dao.BussinessConfig;

/**
 * 运维处理类
 */
public class Processer {

    public static Logger logger = Logger.getLogger(ReturnMessage.class);

    @SuppressWarnings("static-access")
    public ReturnMessage executeSome(Object object) {
        ReturnMessage rm = new ReturnMessage();
        Message message = (Message) object;
        logger.info("命令是：" + message.getC());
        switch (message.getC()) {
            case PreSystemStatus:
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case PreSystemStatusDetails:
                rm = getSystemDataByCtdCode(message.getMeterCode());
                break;
            case CollectStatus:
                rm = getCollectStatus(message.getConfigMap());
                break;
            case MeterDataByCtdCode:
                rm = getMeterDataByCtdCode(message.getMeterCode());
                break;
            case CollectStatusDetails:
                break;
            case MeterStatus:
                rm = getMeterStatus(message.getConfigMap());
                break;
            case MeterStatusDetails:
                rm = getAndUpdateMeterData(message.getMd());
                break;
            case MeterData:
                rm = getMeterData(message.getMap());
                break;
            case SystemData:
                rm = getSystemData(message.getConfigMap());
                break;
            case Disable:
                break;
            case Normal:
                break;
            case Trial:
                break;
            case Create:
                break;
            case Update:
                rm = updateData(message.getMeterCodes(), message.getPointCode(), message
                        .getValue());
                break;
            case Delete:
                break;
            case HistoryDataByMeter:
                break;
            case ChannelOpen:
                System.out.println("通道命令.....");
                break;
            case MeterDataCurved:
                rm = MeterDataCurved(message.getMeterCode(), message.getPointCode());
                break;
            case UpdatePrePointSet:
                rm = UpdatePrePointSet(message.getConfigMap());
                break;
            case AlarmData:
                rm = getAlarmData();
                break;
            case FanOverViewData:
                rm = getOverViewData(message.getConfigMap());
                break;
        }
        return rm;
    }

    /**
     * 功能描述 得到曲线数据
     *
     * @param
     * @return
     * @author 高嵩
     * @date
     */

    public ReturnMessage MeterDataCurved(String meterCode, String pointCode) {
        ReturnMessage rm = new ReturnMessage();
        String[] eusCodes = meterCode.split(",");
        String[] mmpCodes = pointCode.split(",");
        List<EsmspSumMeasurOrganizationDay> list = new ArrayList<EsmspSumMeasurOrganizationDay>();
        for (String eusCode : eusCodes) {
            for (String mmpCode : mmpCodes) {
                for (EsmspSumMeasurOrganizationDay day : BussinessConfig.config.daylist) {
                    System.out.println(day.getEusCode());
                    if (day.getEusCode().equals(eusCode)) {
                        System.out.println(day.getMmpCode());
                        if (day.getMmpCode().equals(mmpCode)) {
                            list.add(day);
                            break;
                        }
                    }
                }
            }
        }
        JSONArray json = JSONArray.parseArray(JSON.toJSONString(list));
        rm.setObject(json.toString());
        return rm;
    }

    /**
     * 功能描述 得到数据  根据  表计 参数编码
     *
     * @param
     * @return
     * @author 高嵩
     * @date
     */

    public ReturnMessage getMeterData(Map<String, String[]> map) {
        ReturnMessage rm = new ReturnMessage();
        List<PointDevice> pointlist = new ArrayList<PointDevice>();
        for (String meterCode : map.keySet()) {
            boolean isFind = false;
            String[] mmpCodes = map.get(meterCode);
            for (TerminalDevice td : BussinessConfig.terminalList) {
                for (MeterDevice md : td.getMeterList()) {
                    if (md.getCode().equals(meterCode)) {
                        if (mmpCodes != null) {
                            for (String mmpCode : mmpCodes) {
                                for (PointDevice pd : md.getPointDevice()) {
                                    if (pd.getCode().equals(mmpCode)) {
                                        pd.setCtdName(md.getName());
                                        pointlist.add(pd);
                                        break;
                                    }
                                }
                            }
                        } else {
                            for (PointDevice pd : md.getPointDevice()) {
                                pd.setCtdName(md.getName());
                                pointlist.add(pd);
                            }
                        }
                        isFind = true;
                        break;
                    }
                }
                if (isFind) {
                    break;
                }
            }
        }
        JSONArray json = JSONArray.parseArray(JSON.toJSONString(pointlist));
        rm.setObject(json.toString());
        return rm;
    }

    public ReturnMessage getSystemDataByCtdCode(String ctdCode) {
        ReturnMessage rm = new ReturnMessage();
        List<SystemParams> list = new ArrayList<SystemParams>();
        for (SystemParams params : BussinessConfig.systemParams) {
            if (params.getEusCode().equals(ctdCode)) {
                list.add(params);
            }
        }
        JSONArray json = JSONArray.parseArray(JSON.toJSONString(list));
        rm.setObject(json.toString());
        return rm;
    }

    /**
     * 功能描述 根据表计编码得到详细数据
     *
     * @return
     * @author 高嵩
     * @date
     * @param表计编码
     */

    public ReturnMessage getMeterDataByCtdCode(String ctdCode) {
        ReturnMessage rm = new ReturnMessage();
        List<PointDevice> pointlist = new ArrayList<PointDevice>();
        boolean isFind = false;
        for (TerminalDevice td : BussinessConfig.terminalList) {
            for (MeterDevice md : td.getMeterList()) {
                if (md.getCode().equals(ctdCode)) {
                    pointlist = md.getPointDevice();
                    isFind = true;
                    break;
                }
            }
            if (isFind) {
                break;
            }
        }
        String json = JSON.toJSONString(pointlist);
        rm.setObject(json);
        return rm;
    }

    public ReturnMessage getAndUpdateMeterData(MeterDevice m) {
        ReturnMessage rm = new ReturnMessage();
        List<PointDevice> pointlist = new ArrayList<PointDevice>();
        for (TerminalDevice td : BussinessConfig.terminalList) {
            for (MeterDevice md : td.getMeterList()) {
                if (md.getCode().equals(m.getCode())) {
                    for (PointDevice p : m.getPointDevice()) {
                        for (PointDevice pd : md.getPointDevice()) {
                            if (pd.getCode().equals(p.getCode())) {
                                if (pd.getMmpType() == 1) {
                                    pd.setValue(p.getValue());
                                }
                                pd.setCtdName(md.getName());
                                pointlist.add(pd);
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
        JSONArray json = JSONArray.parseArray(JSON.toJSONString(pointlist));
        rm.setObject(json.toString());
        return rm;
    }

    /**
     * 功能描述 写入数据
     *
     * @param
     * @return
     * @author 高嵩
     * @date
     */

    public ReturnMessage updateData(String[] ctdCode, String mmpCode, String value) {
        ReturnMessage rm = new ReturnMessage();
        String result = "写入失败,未找到表计信息（"+ctdCode+"）";
        boolean isFind = false;
        for (TerminalDevice td : BussinessConfig.terminalList) {
            for (MeterDevice md : td.getMeterList()) {
                if (ArrayUtils.inArray(ctdCode,md.getCode())) {
                    result="找到表计("+md.getName()+")，未找到测量点";
                    for (PointDevice pd : md.getPointDevice()) {
                        if (pd.getCode().equals(mmpCode)) {
                            boolean res = false;
                            if (pd.getRwType() != 1) {
                                result = "该参数不允许写入("+td.getName()+"-"+md.getName()+"["+pd.getName()+"]"+")";
                            } else {
                                if (pd.getIsCollect() == 1) {
                                    GetDataThread thread = (GetDataThread) ServerContext.threadMap.get(td.getCode());
                                    res = thread.write(md, pd, Double.parseDouble(value));
                                } else {
                                    res=UpdateNoCollectValue.update(md, pd, value);
                                }
                                if (!res) {
                                    result = "写入失败（"+td.getName()+"-"+md.getName()+"["+pd.getName()+"]）";
                                } else {
                                    result = "写入成功（"+td.getName()+"-"+md.getName()+"["+pd.getName()+"]）";
                                }
                            }
                            isFind = true;
                            break;
                        }
                    }
                }
                if (isFind) {
                    break;
                }
            }
            if (isFind) {
                break;
            }
        }
        rm.setObject(result);
        return rm;
    }

    /**
     * 功能描述 得到采集器状态
     *
     * @param
     * @return
     * @author 高嵩
     * @date
     */

    public ReturnMessage getCollectStatus(Map<String, Object> map) {
        ReturnMessage rm = new ReturnMessage();
        Object status = map.get("status");
        List<TerminalDevice> terminalDevices = new ArrayList<TerminalDevice>();
        for (TerminalDevice t : BussinessConfig.terminalList) {
            TerminalDevice td = new TerminalDevice();
            td.setIsOnline(t.getIsOnline());
            td.setName(t.getName());
            td.setCode(t.getCode());
            td.setMSA(t.getMSA());
            td.setNoticeAccord(t.getNoticeAccord());
            int normalCount = 0;
            int faultCount = 0;
            for (MeterDevice md : t.getMeterList()) {
                if (md.getStatus() == 1) {
                    normalCount++;
                } else {
                    faultCount++;
                }
            }
            td.setNormalMeterCount(normalCount);
            td.setFaultMeterCount(faultCount);
            td.setAsstd_LastDate(t.getAsstd_LastDate());
            td.setProduction(t.getProduction());
            td.setLocation(t.getLocation());
            if (status == null || Integer.parseInt(status.toString()) == 2) {
                terminalDevices.add(td);
            } else {
                int flag = Integer.parseInt(status.toString());
                if (flag == t.getIsOnline()) {
                    terminalDevices.add(td);
                }
            }

        }
        String json = JSON.toJSONString(terminalDevices);
        rm.setObject(json);
        return rm;
    }

    /**
     * 功能描述 更新配置参数
     *
     * @param
     * @return
     * @author 高嵩
     * @date
     */

    public ReturnMessage UpdatePrePointSet(Map<String, Object> map) {
        int result = 0;
        BigDecimal upValue = new BigDecimal(map.get("upValue").toString());
        BigDecimal downValue = new BigDecimal(map.get("downValue").toString());
        Integer mmpIsAlarm = Integer.parseInt(map.get("mmpIsAlarm").toString());
        /**
         * 设备模型编码
         */
        String modelCode = map.get("modelCode").toString();
        /**
         * 设备模型参数编码
         */
        String mmpCode = map.get("mmpCode").toString();
        for (PomsEnergyUsingSystem system : BussinessConfig.systemList) {
            for (PomsEnergyUsingFacilities facilities : system.getFacilitiyList()) {
                for (PomsEnergyUsingFacilitiesModelPoint pd : facilities.getPointList()) {
                    if(pd.getFacilitiesModelCode()!=null) {
                        if (pd.getFacilitiesModelCode().equals(modelCode) && mmpCode.equals(pd.getMmpCode())) {
                            pd.setUpValue(upValue);
                            pd.setDownValue(downValue);
                            pd.setIsAlarm(mmpIsAlarm);
                            result = 1;
                            break;
                        }
                    }
                }
            }
        }
        ReturnMessage rm = new ReturnMessage();
        rm.setObject(result);
        return rm;
    }

    /**
     * 功能描述 根据采集器编码得到表计通讯状态
     *
     * @param
     * @return
     * @author 高嵩
     * @date
     */

    public ReturnMessage getMeterStatus(Map<String, Object> map) {
        String asstdCode = map.get("terminalCode").toString();
        Object status = map.get("status");
        ReturnMessage rm = new ReturnMessage();
        List<MeterDevice> meterDevices = new ArrayList<MeterDevice>();
        try {
            TerminalDevice td = BussinessConfig.getTerminalByCode(asstdCode);
            for (MeterDevice m : td.getMeterList()) {
                MeterDevice md = new MeterDevice();
                md.setStatus(m.getStatus());
                md.setName(m.getName());
                md.setCode(m.getCode());
                md.setAddress(m.getAddress());
                md.setLastCollectDate(m.getLastCollectDate());
                if (status == null) {
                    meterDevices.add(md);
                } else {
                    int flag = Integer.parseInt(status.toString());
                    if (flag == md.getStatus()) {
                        meterDevices.add(md);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String json = JSON.toJSONString(meterDevices);
        rm.setObject(json);
        return rm;
    }

    public static void main(String[] arsg) {
        System.out.println(Integer.parseInt("01000821", 16));
    }

    /**
     * 方法名: getSystemData
     * 描述: 得到用能系统数据
     * 参数: [map]
     * 返回值: com.cic.pas.common.net.ReturnMessage
     * 作者:高嵩
     * 创建时间: 2019/8/3 11:17
     **/
    public ReturnMessage getSystemData(Map<String, Object> map) {
        Object systemCodeObj = map.get("systemCode");
        ReturnMessage rm = new ReturnMessage();
        if (systemCodeObj != null) {
            String systemCode = systemCodeObj.toString();
            for (PomsEnergyUsingSystem system : BussinessConfig.systemList) {
                if (system.getSystemCode().equals(systemCode)) {
                    String json = JSON.toJSONString(system);
                    rm.setObject(json);
                    break;
                }
            }
        }
        return rm;
    }

    /**
     * create by: 高嵩
     * description: 得到正在报警数据
     * create time: 2019/8/14 22:27
     *
     * @return
     * @params
     */
    public ReturnMessage getAlarmData() {
        ReturnMessage rm = new ReturnMessage();
        String json = JSON.toJSONString(BussinessConfig.alarmMap);
        rm.setObject(json);
        return rm;
    }

    /**
     * create by: 高嵩
     * description: 总览数据
     * create time: 2019/8/15 16:50
     *
     * @return
     * @params
     */
    public ReturnMessage getOverViewData(Map<String, Object> configMap) {
        String modelType = configMap.get("modelType").toString();
        List<FanOverView> result = new ArrayList<>();
        for (PomsEnergyUsingSystem system : BussinessConfig.systemList) {
            if (system.getSystemModelCode().equals(modelType)&&system.getEulId().equals("2")) {
                FanOverView fan = new FanOverView();
                fan.setSystemCode(system.getSystemCode());
                fan.setSystemName(system.getSystemName());
                fan.setConnectState(getConnectionState(system.getAsstdStates()));
                fan.setEulCode(system.getEulId());
                fan.setEulName(system.getEulName());
                List<Map<String, Object>> list = new ArrayList<>();
                for (PomsEnergyUsingFacilities facility : system.getFacilitiyList()) {
                    if (fan.getMeterCode() == null) {
                        fan.setMeterCode(facility.getPreModelCode()[0]);
                        fan.setFacilityCode(facility.getFacilitiesCode());
                        fan.setFacilityName(facility.getFacilitiesName());
                    }
                    for (PomsEnergyUsingFacilitiesModelPoint point : facility.getPointList()) {
                        if (point.getIsOverViewData() == 1) {
                            Map<String, Object> map = new LinkedHashMap<>();
                            map.put("mmpCode", point.getMmpCode());
                            map.put("preMmpCode", point.getMeasurMmpCode());
                            map.put("value", point.getValue());
                            list.add(map);
                        }
                    }
                }
                Collections.sort(list, new Comparator<Map<String, Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        int diff = Integer.parseInt(o1.get("preMmpCode").toString()) - Integer.parseInt(o2.get("preMmpCode").toString());
                        if (diff > 0) {
                            return 1;
                        } else if (diff < 0) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                });
                fan.setParams(list);
                result.add(fan);
            }
        }
        ReturnMessage rm = new ReturnMessage();
        String obj = JSON.toJSONString(result);
        rm.setObject(obj);
        return rm;
    }
    /**
     * create by: 高嵩
     * description: 得到通讯状态 有一个通讯故障 均定义为故障
     * create time: 2019/8/29 16:17
     * @params
     * @return
     */
    public int getConnectionState(Map<String,Integer> states){
        for(int value:states.values()){
            if(value==0){
                return 0;
            }
        }
        return 1;
    }
}
