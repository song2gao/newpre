package com.cic.pas.thread;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.PointDevice;
import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.common.util.CRC16M;
import com.cic.pas.common.util.ModBusReadAndWrite;
import com.cic.pas.common.util.ModBusUtil;
import com.cic.pas.common.util.Util;
import com.cic.pas.procotol.s7.S7ReadRequest;
import com.cic.pas.procotol.s7.S7WriteRequest;
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
    public int pduLength = 0;

    public GetDataThread(TerminalDevice td, IoSession session) {
        this.td = td;
        this.session = session;
//        if (td.getNoticeAccord().equals("Byte3761")) {
//            for (MeterDevice meter : td.getMeterList()) {
//                getSendBuff3761(meter);
//            }
//        } else if (td.getNoticeAccord().equals("ByteS7")) {
//            for (MeterDevice meter : td.getMeterList()) {
//                getSendBuffS7(meter);
//            }
//        } else {
//            for (MeterDevice meter : td.getMeterList()) {
//                getSendBuffModBus(meter);
//            }
//        }
    }

    @Override
    public void run() {
        while (!exit) {
            boolean flag = true;
            if (flag) {
                Calendar c = Calendar.getInstance();
                int m = c.get(Calendar.MINUTE);
                if (map.size() == 0) {
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
                    int firstIndex = key.indexOf(":");
                    int lastIndex = key.lastIndexOf(":");
                    String ctdCode = key.substring(0, firstIndex);
                    String address = key.substring(lastIndex + 1);
                    session.setAttribute("ctdCode", ctdCode);
                    session.setAttribute("readAddress", address);
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

    public static void main(String[] args) throws SecurityException {
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
        byte[] writeBytes = null;
        if (td.getNoticeAccord().equals("ByteS7")) {
            writeBytes = getS7WriteBytes(pd, writeValue);
        } else {
            writeBytes = modRW.write(md.getAddress(), md.getType(), md.getIncrease(), pd.getModWAddress().intValue() + md.getIncrease(), pd.getModWFunction(), pd.getModWType(), pd.getModWlength(), pd.getIsBit(), pd.getModWFormular(), writeValue);
        }
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
                    System.out.println(result==true?"写入成功":"写入失败");
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
        if (td.getNoticeAccord().equals("ByteS7")) {

        } else {
            readAndDecoder(md.getCode(), pd.getStorageType(), md.getAddress(), pd.getModAddress().intValue(), pd.getPointLen());
        }
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
            start = p.getModAddress().intValue() + meter.getIncrease();
            key = meter.getCode() + ":" + p.getStorageType() + ":" + start;
            byte[] values = ModBusUtil.getProtocol(meterAddress, start, p.getPointLen(), p.getIsPlcAddress(), p.getStorageType());
            map.put(key, values);
        } else {
            int begin = 0;
            for (PointDevice p : points) {
                int address = p.getModAddress().intValue() + meter.getIncrease();
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

    public void getSendBuffS7(MeterDevice meter) {
        if(td.getDeviceModel().toLowerCase().equals("s7-200smart")){
            firstHand = CRC16M.HexString2Buf("03 00 00 16 11 E0 00 00 00 01 00 C1 02 10 00 C2 02 03 00 C0 01 0A");
        }else if(td.getDeviceModel().toLowerCase().equals("s7-200")){
            firstHand = CRC16M.HexString2Buf("03 00 00 16 11 e0 00 00 00 01 00 c1 02 4d 57 c2 02 4d 57 c0 01 00");
        }
        session.write(firstHand);
        while (pduLength == 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        List<PointDevice> points = meter.getPointDevice();
        BigDecimal start = new BigDecimal(0);
        BigDecimal lastAddress = new BigDecimal("0");
        int lastLen = 0;
        int len = 0;
        int i = 0;
        String key = "";
        int lastfunction = 3;
        int lastMmpType = 1;
        if (points.size() == 1) {
            PointDevice p = points.get(0);
            start = p.getModAddress().add(new BigDecimal(meter.getIncrease()));
            key = meter.getCode() + ":" + p.getStorageType() + ":" + start;
            byte[] values = getS7Send(start, p.getPointLen(), p.getStorageType(), p.getMmpType());
            map.put(key, values);
        } else {
            BigDecimal begin = new BigDecimal(0);
            for (PointDevice p : points) {
                BigDecimal address = p.getModAddress().add(new BigDecimal(meter.getIncrease()));
                int curlen = p.getPointLen();
                if (len == 0) {
                    len = curlen;
                    begin = address;
                    key = meter.getCode() + ":" + p.getStorageType() + ":" + address;
                } else {
                    if (getDataTypeReadInOneRequest(lastfunction, p.getStorageType(), lastAddress, p.getModAddress(), lastMmpType, p.getMmpType(), lastLen)) {
                        if (len >= pduLength) {
                            byte[] values = getS7Send(begin, len, lastfunction, p.getMmpType());
                            map.put(key, values);
                            key = meter.getCode() + ":" + p.getStorageType() + ":" + address;
                            begin = address;
                            len = curlen;
                        } else {
                            len += curlen;
                            if (i == points.size() - 1) {
                                byte[] values = getS7Send(begin, len, lastfunction, p.getMmpType());
                                map.put(key, values);
                            }
                        }
                    } else {
                        byte[] values = getS7Send(begin, len, lastfunction, lastMmpType);
                        map.put(key, values);
                        if (i == points.size() - 1) {
                            byte[] last = getS7Send(address, curlen, p.getStorageType(), p.getMmpType());
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
                lastMmpType = p.getMmpType();
                i++;
            }
        }
    }

    private byte[] getS7Send(BigDecimal start, int length, int type, int mmpType) {
        S7ReadRequest send = new S7ReadRequest();
        send.setArea((byte) type);
        if (type == 132) {
            send.setDbNumber(new byte[]{0, 1});
        } else {
            send.setDbNumber(new byte[]{0, 0});
        }
        if (mmpType == 1 && length == 1) {// 读取一个bit时使用bit 读取
            send.setTransportSize((byte) 1);
            send.setRequestDataLength(new byte[]{0, 1});
        } else {//多个bit、byte、word、Dword时使用 byte读取
            send.setTransportSize((byte) 2);
            byte[] lengthByte = new byte[2];
            if (mmpType == 1) {// 读取bit时 转为字节长度 重新定义长度
                BigDecimal startPoint = start.subtract(new BigDecimal(start.intValue()));//起始地址小数部分
                if (startPoint.compareTo(BigDecimal.ZERO) == 0) {//起始地址为整数  即为整个byte时
                    length = length / 8 + 1;
                } else {//起始地址不是整数时  重新定义起始地址读取整个字节
                    start = new BigDecimal(start.intValue());
                    length = length + startPoint.multiply(new BigDecimal("10")).intValue();
                    length = length / 8 + 1;
                }

            }
            lengthByte[0] = (byte) (length >> 8 & 0xff);
            lengthByte[1] = (byte) (length & 0xff);
            send.setRequestDataLength(lengthByte);
        }
        int address = 0;
        BigDecimal addressInt = new BigDecimal(start.intValue());
        BigDecimal addressPoint = start.subtract(addressInt);
        address = start.intValue() * 8;
        if (addressPoint.compareTo(BigDecimal.ZERO) != 0) {
            address += addressPoint.multiply(new BigDecimal("10")).intValue();
        }
        byte[] addressBytes = new byte[3];
        addressBytes[0] = (byte) (address >> 16 & 0xff);
        addressBytes[1] = (byte) (address >> 8 & 0xff);
        addressBytes[2] = (byte) (address & 0xff);
        send.setAddress(addressBytes);
        byte[] sendBytes = send.getSendBytes();
        return sendBytes;
    }

    /**
     * 判断能否用一条指令来读取 相邻两个寄存器可以用一同条指令读取  bit和其它类型不能一条指令读取
     *
     * @param lastFunction 上一次寄存器类型
     * @param currFunction 本次寄存器类型
     * @param lastAddress  上次寄存器地址
     * @param currAddress  本次寄存器地址
     * @param lastMmpType  上次数据类型
     * @param currMmpType  本次数据类型
     * @param lastLen      上次寄存器长度
     * @return
     */
    private boolean getDataTypeReadInOneRequest(int lastFunction, int currFunction, BigDecimal lastAddress, BigDecimal currAddress, int lastMmpType, int currMmpType, int lastLen) {
        if (lastFunction != currFunction || !checkTypeInOnFunction(lastMmpType, currMmpType)) {
            return false;
        } else {
            if (currMmpType == 1) {
                BigDecimal temp = new BigDecimal(0);//用来处理0.7与1.0的问题  字节0-7 8进位
                if ((lastAddress + "").indexOf(".7") > 0) {
                    temp = new BigDecimal("0.2");
                }
                BigDecimal newLen = new BigDecimal(lastLen).divide(new BigDecimal(10));
                BigDecimal newAddress = lastAddress.add(newLen).add(temp);
                if (newAddress.compareTo(currAddress) == 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (lastAddress.add(new BigDecimal(lastLen)).compareTo(currAddress) == 0) {
                    return true;
                }
                return false;
            }
        }
    }

    public boolean checkTypeInOnFunction(int lastType, int currType) {
        if (lastType != currType) {
            if (lastType == 1 || currType == 1) {// 1与其它类型不能一条指令读取
                return false;
            } else {//其它类型可同时读取
                return true;
            }
        } else {//相等可以一条指令读取
            return true;
        }
    }

    public byte[] getS7WriteBytes(PointDevice pd, Double writeValue) {
        S7WriteRequest request = new S7WriteRequest();
        request.setFunction((byte) 0x05);
        request.setArea((byte) pd.getStorageType());
        byte[] data;
        if (pd.getModWType() == 1) {
            request.setTransportSize((byte) 1);
            request.setDataTransportSize((byte) 3);
            request.setDataWriteLength(new byte[]{0, 1});
            data = new byte[1];
            data[0] = (byte) (writeValue.intValue() & 0x01);
        } else {
            request.setTransportSize((byte) 2);
            request.setDataTransportSize((byte) 4);
            request.setDataWriteLength(new byte[]{0, (byte) (pd.getModWlength()*8)});
            data = Util.realValueToBytes(writeValue, pd.getModWType());
        }
        int dataLength=4+pd.getModWlength();
        request.setDataLength(new byte[]{(byte)(dataLength>>8&0xff),(byte)(dataLength&0xff)});
        request.setRequestDataLength(new byte[]{(byte)(pd.getModWlength()>>8&0xff),(byte)(pd.getModWlength()&0xff)});
        int totalLength=35+pd.getModWlength();
        request.setTotalLength(new byte[]{(byte)(totalLength>>8&0xff),(byte)(totalLength&0xff)});
        if (pd.getModWFunction() == 132) {
            request.setDbNumber(new byte[]{0, 1});
        }
        int addressInt=pd.getModWAddress().intValue()*8;
        int addressPoint=pd.getModWAddress().subtract(new BigDecimal(pd.getModWAddress().intValue())).multiply(new BigDecimal("10")).intValue();
        int address=addressInt+addressPoint;
        request.setAddress(new byte[]{(byte)(address>>16&0xff),(byte)(address>>8&0xff),(byte)(address&0xff)});
        request.setWriteData(data);
        return request.getSendBytes();
    }
}
