package com.cic.pas.service;

import com.cic.pas.application.DBVisitService;
import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.PointDevice;

public class UpdateNoCollectValue {
    public static boolean update(MeterDevice md, PointDevice pd,String value){
//        TimingRunStopThread thread=(TimingRunStopThread)ConnectorContext.deviceAuto.get(md.getCode());
//        if(thread==null){
//            return false;
//        }
//        thread.update(pd,value);
        return DBVisitService.updatePointValue(pd,value);
    }
}
