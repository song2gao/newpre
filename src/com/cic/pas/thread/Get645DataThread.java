package com.cic.pas.thread;

import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.PointDevice;
import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.common.util.CRC16M;
import com.cic.pas.common.util.StringUtils;
import com.cic.pas.common.util.Util;
import com.cic.pas.service.ConnectorContext;
import org.apache.mina.core.session.IoSession;

import java.util.LinkedHashMap;
import java.util.Map;

public class Get645DataThread extends BaseThread {
    private Map<String, String> map = new LinkedHashMap<String, String>();
    private TerminalDevice td;
    public int waitTime=0;
    public Get645DataThread(TerminalDevice td){
        this.td=td;
        for (MeterDevice meter : td.getMeterList()) {
            getSendBuff(meter);
        }
    }
    @Override
    public void run(){
        while (!exit){
            IoSession session=ConnectorContext.clientMap.get(td.getMSA());
            for(String key:map.keySet()){
                int index=key.indexOf(":");
                String ctdAddress=key.substring(0,index);
                MeterDevice md=getMeterByCode(ctdAddress);
                String pointAdd=key.substring(index+1);
                session.setAttribute("ctdAddress",ctdAddress);
                session.setAttribute("address",pointAdd);
                try {
                    session.write(CRC16M.HexString2Buf(map.get(key)));
                    pause();
                    try {
                        synchronized (this) {
                            while (suspended) {
                                wait(100);
                                waitTime+=100;
                                if(waitTime>td.getTimeOut()){
                                    System.out.println("采集器："+td.getName()+"下表计:"+md.getName()+"超时");
                                    waitTime=0;
                                    break;
                                }
                            }
                        }
                    } catch (InterruptedException e) {
                        System.out.println("Thread " + td.getName() + " interrupted.");
                        e.printStackTrace();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    public void getSendBuff(MeterDevice meter) {
        String head="FE FE FE FE";
        String headCmd="68";
        String metearAddress=StringUtils.toSwapStr(StringUtils.leftPad(meter.getAddress()+"",12,'0'));
        String cmd="68 11 04";
        for(PointDevice pd:meter.getPointDevice()){
            String pointAddStr=StringUtils.leftPad(pd.getModAddress()+"",8,'0');
            String pointAddress=StringUtils.toSwapStr(StringUtils.toPlus645(pointAddStr));
            String checkStr=headCmd+metearAddress+cmd+pointAddress;
            String checkSum=StringUtils.checkCs(checkStr.replaceAll(" ",""));
            String fStr=checkStr+checkSum+"16";
            map.put(metearAddress+":"+pointAddress,fStr.replaceAll(" ",""));
        }
    }
    private MeterDevice getMeterByCode(String mdAddress){
        MeterDevice meterDevice=null;
        for(MeterDevice md:td.getMeterList()){
            int ctdAddressLoc = Integer.parseInt(StringUtils.toSwapStr(mdAddress));
            if(md.getAddress()==ctdAddressLoc){
                meterDevice=md;
                break;
            }
        }
        return meterDevice;
    }
    public static void main(String[] args){
        String s="";
        for(int i=1;i<97;i++){
            s+="point"+i+"+";
        }
        System.out.println(s);
    }
}
