package com.cic.pas.service;

import com.cic.pas.application.DBVisitService;
import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.PointDevice;
import com.cic.pas.thread.DataTransmitThread;

import java.math.BigDecimal;

public class UpdateNoCollectValue {
    public static boolean update(MeterDevice md, PointDevice pd,String value){
//        TimingRunStopThread thread=(TimingRunStopThread)ConnectorContext.deviceAuto.get(md.getCode());
//        if(thread==null){
//            return false;
//        }
//        thread.update(pd,value);
        String key=md.getCode()+"-"+pd.getCode();
        DataTransmitThread transmitThread=(DataTransmitThread)ServerContext.transmitThreadMap.get(key);
        if(transmitThread!=null){
            transmitThread.exit=true;
            ServerContext.transmitThreadMap.remove(key);
        }
       if(value.equals("1")){
           DataTransmitThread transmitThreadNew=new DataTransmitThread(pd);
           transmitThreadNew.setName(pd.getName()+"数据转发线程");
           transmitThreadNew.start();
           ServerContext.transmitThreadMap.put(md.getCode()+"-"+pd.getCode(),transmitThreadNew);
           pd.setValue(new BigDecimal(value));
        }else if(value.equals("0")){
           pd.setValue(new BigDecimal(value));
       }

        return DBVisitService.updatePointValue(pd);
    }
}
