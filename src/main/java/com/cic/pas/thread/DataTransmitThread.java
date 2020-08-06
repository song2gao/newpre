package com.cic.pas.thread;

import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.PointDevice;
import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.dao.BussinessConfig;
import com.cic.pas.service.ServerContext;
import org.apache.log4j.Logger;

import java.math.BigDecimal;


public class DataTransmitThread extends BaseThread {
    private Logger logger = Logger.getLogger(DataTransmitThread.class);
    PointDevice pd;
    PointDevice readPd;
    MeterDevice writeMd;
    PointDevice writePd;
    BigDecimal lastValue;
    GetDataThread thread;

    public DataTransmitThread(PointDevice pd) {
        this.pd = pd;
        for (TerminalDevice td : BussinessConfig.terminalList) {
            if (Integer.parseInt(td.getCode()) == pd.getStorageType()) {
                for (MeterDevice md : td.getMeterList()) {
                    if (Integer.parseInt(md.getCode()) == pd.getModAddress().intValue()) {
                        for (PointDevice p : md.getPointDevice()) {
                            if (Integer.parseInt(p.getCode()) == pd.getPointLen()) {
                                readPd = p;
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        for (TerminalDevice td : BussinessConfig.terminalList) {
            if (Integer.parseInt(td.getCode()) == pd.getModWFunction()) {
                thread = (GetDataThread) ServerContext.threadMap.get(td.getCode());
                for (MeterDevice md : td.getMeterList()) {
                    if (Integer.parseInt(md.getCode()) == pd.getModWAddress().intValue()) {
                        writeMd = md;
                        for (PointDevice p : md.getPointDevice()) {
                            if (Integer.parseInt(p.getCode()) == pd.getModWlength()) {
                                writePd = p;
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }

    }

    @Override
    public void run() {
        logger.info("[" + this.getName() + "]启动");
        while (!exit) {
            if (thread != null) {
                if (pd.getValue().compareTo(new BigDecimal("1")) == 0) {
                    if(readPd.getValue().compareTo(BigDecimal.ZERO)==0){
                        lastValue=writePd.getValue();
                    }else{
                        lastValue=readPd.getValue();
                    }
                    if ((writePd.getValue().compareTo(readPd.getValue()) != 0)) {
                        if (readPd.getValue().compareTo(BigDecimal.ZERO) != 0) {
                            boolean result = thread.write(writeMd, writePd, readPd.getValue().doubleValue());
                            String info = result == true ? "成功" : "失败";
                            if (result == true) {
                                lastValue = readPd.getValue();
                            }
                            logger.info(writePd.getName() + "自动设置" + info);
                        }
                    }
                }
                try {
                    Thread.sleep(pd.getIsPlcAddress());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
