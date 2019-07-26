package com.cic.pas.thread;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.PointDevice;
import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.common.util.CRC16M;
import com.cic.pas.common.util.ModBusReadAndWrite;
import com.cic.pas.common.util.ModBusUtil;
import com.cic.pas.procotol.S7ProcotolSend;
import com.cic.pas.service.ConnectorContext;
import com.cic.pas.service.ServerContext;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

public class GetDataThread extends BaseThread {

    private Logger logger = Logger.getLogger(GetDataThread.class);
    private Map<String, byte[]> map = new LinkedHashMap<String, byte[]>();
    private TerminalDevice td;
    public static boolean DEBUG = false;
    IoSession session;
    ModBusReadAndWrite modRW = new ModBusReadAndWrite();
    int i = 0;
    private byte[] firstHand = CRC16M.HexString2Buf("03 00 00 16 11 E0 00 00 00 01 00 C1 02 10 00 C2 02 03 00 C0 01 0A");
    public int pduLength=0;
    public GetDataThread(TerminalDevice td, IoSession session) {
        this.td = td;
        this.session = session;
        if (td.getNoticeAccord().equals("Byte3761")) {
            for (MeterDevice meter : td.getMeterList()) {
                getSendBuff3761(meter);
            }
        } else if (td.getNoticeAccord().equals("ByteS7")) {
            for (MeterDevice meter : td.getMeterList()) {
                getSendBuffS7(meter);
            }
        } else {
            for (MeterDevice meter : td.getMeterList()) {
                getSendBuffModBus(meter);
            }
        }
    }

    @Override
    public void run() {
        while (!exit) {
            boolean flag = true;
            if (flag) {
                Calendar c = Calendar.getInstance();
                int m = c.get(Calendar.MINUTE);
                for (String key : map.keySet()) {
                    try {
                        synchronized (this) {
                            while (suspended) {
                                wait();
                            }
                        }
                    } catch (InterruptedException e) {
                        System.out.println("Thread " + td.getName() + " interrupted.");
                        e.printStackTrace();
                    }
                    byte[] values = map.get(key);
                    String ctdCode = key.substring(0, key.indexOf(":"));
                    session.setAttribute("ctdCode", ctdCode);
                    isReviced = false;
                    session.write(values);
                    String terminal_id = session.getAttribute("terminal_id").toString();
                    BaseThread thread = ServerContext.threadMap
                            .get(terminal_id);
                    if (thread != null) {
                        synchronized (thread) {
                            try {
                                thread.wait(td.getTimeOut());
                                if (!isReviced) {
                                    if (i > 2) {
                                        setMeterStatus(ctdCode, 0);
                                    } else {
                                        i++;
                                    }
                                } else {
                                    i = 0;
                                    //setMeterStatus(ctdCode, 1);
                                }

                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                Thread.currentThread().interrupt();
                                e.printStackTrace();
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                    try {
                        Thread.sleep(td.getCalculateCycle());
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(td.getRoundCycle());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                exit = true;
            }
        }
    }

    public void setMeterStatus(String ctdCode, int value) {
        for (MeterDevice md : td.getMeterList()) {
            if (md.getCode().equals(ctdCode)) {
                if (value == 0) {
                    logger.info("<<=[采集器:" + td.getName() + "][表计" + md.getName() + "]=>>超时");
                }
                md.setStatus(value);
                break;
            }
        }
    }

    public static void main(String[] args) throws SecurityException,
            NoSuchMethodException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date date1 = sdf.parse("00:00:00");
            Date date2 = sdf.parse("12:01:30");
            Date current = sdf.parse(sdf.format(new Date()));
            if (current.after(date1) && current.before(date2)) {
                System.out.println("OK");
            } else {
                System.out.println("NO");
            }
        } catch (Exception e) {

        }
    }

    public boolean write(MeterDevice md, PointDevice pd, Double writeValue) {
        boolean result = false;
        byte[] writeBytes = modRW.write(md.getAddress(), md.getType(), md.getIncrease(), pd.getModWAddress() + md.getIncrease(), pd.getModWFunction(), pd.getModWType(), pd.getModWlength(), pd.getIsBit(), pd.getModWFormular(), writeValue);
        pause();
        session.setAttribute("writeResult", false);
        session.write(writeBytes);
        BaseThread thread = ServerContext.threadMap
                .get(td.getCode());
        if (thread != null) {
            synchronized (thread) {
                try {
                    thread.wait(td.getTimeOut());
                    result = (Boolean) session.getAttribute("writeResult");
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        readAndDecoder(md.getCode(), pd.getStorageType(), md.getAddress(), pd.getModAddress(), pd.getPointLen());
        goon();
        return result;
    }

    public void readAndDecoder(String ctdCode, int function, int slaveId, int start, int len) {
        try {
            byte[] data = modRW.readData(function, slaveId, start, len);
            session.setAttribute("ctdCode", ctdCode);
            session.setAttribute("slaveId", slaveId);
            session.setAttribute("start", start);
            session.setAttribute("len", len);
            session.write(data);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e);
        }
    }

    public void getSendBuffModBus(MeterDevice meter) {
        int meterAddress = meter.getAddress();
        List<PointDevice> allPints = meter.getPointDevice();// 所有参数 含非采集参数
        List<PointDevice> points = new ArrayList<PointDevice>();// 采集参数
        MeterDevice timingMd = new MeterDevice();
        timingMd.setCode(meter.getCode());
        timingMd.setAddress(meter.getAddress());
        timingMd.setIncrease(meter.getIncrease());
        timingMd.setType(meter.getType());
        timingMd.setPointDevice(new ArrayList<PointDevice>());
        for (PointDevice p : allPints) {
            if (p.getIsCollect() == 1) {
                points.add(p);
                if (p.getCode().equals("311")) {
                    timingMd.getPointDevice().add(p);
                }
            } else {
                timingMd.getPointDevice().add(p);
            }
        }
        if (timingMd.getPointDevice().size() > 2) {
            TimingRunStopThread t = new TimingRunStopThread(timingMd, this);
            t.setName(meter.getName() + "自控");
            t.start();
            if (ConnectorContext.deviceAuto.containsKey(timingMd.getCode())) {
                BaseThread old = ConnectorContext.deviceAuto.get(meter.getCode());
                old.exit = true;
                ConnectorContext.deviceAuto.remove(meter.getCode());
            }
            ConnectorContext.deviceAuto.put(meter.getCode(), t);
        }
        int start = 0;
        int lastAddress = 0;
        int lastLen = 0;
        int len = 0;
        int i = 0;
        String key = "";
        int lastfunction = 3;
        if (points.size() == 1) {
            PointDevice p = points.get(0);
            start = p.getModAddress() + meter.getIncrease();
            key = meter.getCode() + ":" + p.getStorageType() + ":" + start;
            byte[] values = ModBusUtil.getProtocol(meterAddress, start, p.getPointLen(), p.getIsPlcAddress(), p.getStorageType());
            map.put(key, values);
        } else {
            int begin = 0;
            for (PointDevice p : points) {
                int address = p.getModAddress() + meter.getIncrease();
                int curlen = p.getPointLen();
                if (len == 0) {
                    len = curlen;
                    begin = address;
                    key = meter.getCode() + ":" + p.getStorageType() + ":" + address;
                } else {
                    if (address == lastAddress + lastLen && p.getStorageType() == lastfunction) {
                        if (len >= 95) {
                            byte[] values = ModBusUtil.getProtocol(meterAddress, begin, len, p.getIsPlcAddress(), lastfunction);
                            map.put(key, values);
                            key = meter.getCode() + ":" + p.getStorageType() + ":" + address;
                            begin = address;
                            len = curlen;
                        } else {
                            len += curlen;
                            if (i == points.size() - 1) {
                                byte[] values = ModBusUtil.getProtocol(meterAddress, begin, len, p.getIsPlcAddress(), lastfunction);
                                map.put(key, values);
                            }
                        }
                    } else {
                        byte[] values = ModBusUtil.getProtocol(meterAddress, begin, len, p.getIsPlcAddress(), lastfunction);
                        map.put(key, values);
                        if (i == points.size() - 1) {
                            byte[] last = ModBusUtil.getProtocol(meterAddress, address, curlen, p.getIsPlcAddress(), p.getStorageType());
                            key = meter.getCode() + ":" + p.getStorageType() + ":" + address;
                            map.put(key, last);
                        } else {
                            key = meter.getCode() + ":" + p.getStorageType() + ":" + address;
                            begin = address;
                            len = curlen;
                        }
                    }
                }
                lastAddress = address;
                lastLen = curlen;
                lastfunction = p.getStorageType();
                i++;
            }
        }
    }

    public void getSendBuff3761(MeterDevice md) {
        for (PointDevice pd : md.getPointDevice()) {
            byte[] bytes = new byte[2];
            int high = (int) ((md.getAddress() - 1) / 8) + 1;
            int ab = md.getAddress() % 8;
            if (ab == 0) {
                ab = 8;
            }
            int low = (int) Math.pow(2, ab - 1);
            bytes[0] = (byte) low;
            bytes[1] = (byte) high;
            String key = md.getCode() + ":" + pd.getStorageType() + ":" + pd.getModAddress();
            map.put(key, bytes);
        }
    }

    public void getSendBuffS7(MeterDevice md) {
        session.write(firstHand);
        while (pduLength==0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        S7ProcotolSend send=new S7ProcotolSend();
    }
}
