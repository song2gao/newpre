package com.cic.pas.thread;

import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.PointDevice;
import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.common.util.CRC16M;
import com.cic.pas.common.util.StringUtils;
import com.cic.pas.service.ConnectorContext;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import java.util.LinkedHashMap;
import java.util.Map;

public class Get3761DataThread extends BaseThread {
    private Logger logger = Logger.getLogger(Get3761DataThread.class);
    private TerminalDevice td;
    public int waitTime = 0;

    public Get3761DataThread(TerminalDevice td) {
        this.td = td;
    }

    @Override
    public void run() {
        logger.info("========================Get3761Data线程启动=======================================");
        int terminal_id = Integer.parseInt(td.getCode());
        byte[] sendBytes = new byte[20];
        sendBytes[0] = sendBytes[5] = 0x68;
        sendBytes[1] = sendBytes[3] = 0x32;
        sendBytes[2] = sendBytes[4] = 0;
        sendBytes[6] = (byte) Integer.parseInt("4B", 16);
        sendBytes[7] = 0x02;
        sendBytes[8] = 0x37;
        sendBytes[9] = (byte) (terminal_id % 256);
        sendBytes[10] = (byte) (terminal_id / 256);
        sendBytes[11] = (byte) Integer.parseInt("F2", 16);
        sendBytes[12] = (byte) Integer.parseInt("0C", 16);
        while (!exit) {
            IoSession session = ConnectorContext.clientMap.get(terminal_id+"");
            for (MeterDevice md : td.getMeterList()) {
                if(exit){
                    break;
                }
                int seq=Integer.parseInt(session.getAttribute("SEQ").toString());
                seq++;
                if(seq>15){
                    seq=0;
                }
                System.out.println("取出SEQ："+Integer.toHexString(112+seq));
                byte byte13 = (byte) (112 +seq);
                session.setAttribute("SEQ",seq);
                sendBytes[13] = byte13;
                int high=(int)((md.getAddress()-1)/8)+1;
                int ab=md.getAddress()%8;
                if(ab==0){
                    ab=8;
                }
                int low=(int)Math.pow(2,ab-1);
                sendBytes[14] = (byte)low;
                sendBytes[15] = (byte) high;
                sendBytes[16] = 0x01;
                sendBytes[17] = 0x10;
                sendBytes[18]=0;
                for (int i = 0; i < 12; i++) {
                    sendBytes[18] += sendBytes[6 + i];
                }//cs
                sendBytes[19]=0x16;
                logger.info("["+td.getName()+"请求数据帧]:[pn="+md.getAddress()+"]["+CRC16M.getBufHexStr(sendBytes)+"]");
                session.write(sendBytes);
                Get3761DataThread thread=(Get3761DataThread)ConnectorContext.threadMap.get(session.getAttribute("terminal_id")+"");
                if(thread!=null) {
                    synchronized (thread) {
                        try {
                            thread.wait(td.getTimeOut());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            try {
                Thread.sleep(td.getRoundCycle());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private MeterDevice getMeterByCode(String mdAddress) {
        MeterDevice meterDevice = null;
        for (MeterDevice md : td.getMeterList()) {
            int ctdAddressLoc = Integer.parseInt(StringUtils.toSwapStr(mdAddress));
            if (md.getAddress() == ctdAddressLoc) {
                meterDevice = md;
                break;
            }
        }
        return meterDevice;
    }
}
