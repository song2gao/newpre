package com.cic.pas.thread;

import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.PointDevice;
import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.common.util.Util;
import com.cic.pas.dao.BussinessConfig;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InventedMeterDataThread extends BaseThread {
    private MeterDevice md;
    private String[] ctds;
    public InventedMeterDataThread(MeterDevice md,String[] ctds){
        this.md=md;
        this.ctds=ctds;
    }
    @Override
    public void run(){
        while (!exit){
            String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            List<MeterDevice> list=getMeterList(ctds);
            for(PointDevice pd:md.getPointDevice()){
                BigDecimal value=BigDecimal.ZERO;
                for(MeterDevice m:list){
                    for(PointDevice p:m.getPointDevice()){
                        if(p.getCode().equals(pd.getCode())){
                            value=value.add(p.getValue());
                        }
                    }
                }
                pd.setValue(value);
                pd.setTime(time);
                md.setStatus(1);
                md.setLastCollectDate(time);
            }
            try {
                Thread.sleep(1000*5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * create by: 高嵩
     * description: 得到虚拟表所关联的实表
     * create time: 2020/1/16 23:01
     * @params
     * @return
     */
    private static List<MeterDevice> getMeterList(String[] ctds){
        List<MeterDevice> list=new ArrayList<>();
        int findCount=0;
        for(TerminalDevice td:BussinessConfig.terminalList){
            for(MeterDevice md:td.getMeterList()){
                if(Util.strInStrs(ctds,md.getCode())){
                    list.add(md);
                    findCount++;
                }
                if(findCount>=ctds.length){
                    break;
                }
            }
            if(findCount>=ctds.length){
                break;
            }
        }
        return list;
    }
}
