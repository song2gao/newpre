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
import com.cic.pas.service.ServerContext;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

public class GetDataThread extends BaseThread {

    private Logger logger = Logger.getLogger(GetDataThread.class);
    private Map<String, byte[]> map = new LinkedHashMap<String, byte[]>();
    private TerminalDevice td;
    private Map<String, MeterDevice> nomalMeter;
    private Map<String, MeterDevice> faultMeter;
    public static boolean DEBUG = false;
    IoSession session;
    ModBusReadAndWrite modRW = new ModBusReadAndWrite();
    private boolean handFlag = false;
    int i = 0;
    private byte[] firstHand = CRC16M.HexString2Buf("03 00 00 16 11 E0 00 00 00 01 00 C1 02 10 00 C2 02 03 00 C0 01 0A");
    public int pduLength = 0;

    public GetDataThread(TerminalDevice td, IoSession session) {
        this.td = td;
        this.nomalMeter = td.getNormanMeter();
        this.faultMeter = td.getFaultMeter();
        this.session = session;
    }

    public void setSession(IoSession session) {
        this.session = session;

    }

    /**
     * create by: 高嵩
     * description: 检查握手状态
     * create time: 2019/8/28 11:05
     *
     * @return
     * @params
     */
    private void checkHandFlag() {
        switch (td.getNoticeAccord()) {
            case "Byte3761":
                handFlag = true;
                break;
            case "ByteS7":
                s7HandBuff();
                break;
            default:
                handFlag = true;
        }
    }

    @Override
    public void run() {
//        logger.info("["+this.getName() + "]数据采集线程启动");
        while (!exit) {
            boolean flag = true;
            if (flag) {
                checkSendBuff();
                if (session.isConnected()) {
                    if (handFlag) {
                        td.setIsOnline(1);
                        sendGetDataBuff();
                    } else {
                        checkHandFlag();
                    }
                } else {
                    td.setIsOnline(0);
                    handFlag = false;
                }
            } else {
                exit = true;
            }
        }
    }

    /**
     * create by: 高嵩
     * description: s7协议握手指令
     * create time: 2019/8/28 10:53
     *
     * @return
     * @params
     */
    private void s7HandBuff() {
        pduLength = 0;
        if (td.getDeviceModel().toLowerCase().equals("s7-200smart")) {
            firstHand = CRC16M.HexString2Buf("03 00 00 16 11 e0 00 00 00 04 00 c1 02 01 01 c2 02 01 01 c0 01 0a");
//            firstHand = CRC16M.HexString2Buf("03 00 00 16 11 E0 00 00 00 01 00 C1 02 10 00 C2 02 03 00 C0 01 0A");

        } else if (td.getDeviceModel().toLowerCase().equals("s7-200")) {
            firstHand = CRC16M.HexString2Buf("03 00 00 16 11 e0 00 00 00 01 00 c1 02 4d 57 c2 02 4d 57 c0 01 00");
        } else if (td.getDeviceModel().toLowerCase().equals("s7-300")) {
            firstHand = CRC16M.HexString2Buf("03 00 00 16 11 e0 00 00 00 01 00 c1 02 01 00 c2 02 01 00 c0 01 09");
        } else if (td.getDeviceModel().toLowerCase().equals("s7-400")) {
            firstHand = CRC16M.HexString2Buf("03 00 00 16 11 e0 00 00 00 01 00 c1 02 02 00 c2 02 02 00 c0 01 0a");
        } else if (td.getDeviceModel().toLowerCase().equals("s7-1200")) {
            firstHand = CRC16M.HexString2Buf("03 00 00 16 11 e0 00 00 00 01 00 c1 02 10 00 c2 02 03 01 c0 01 0a");
        } else if (td.getDeviceModel().toLowerCase().equals("s7-1500")) {
            firstHand = CRC16M.HexString2Buf("03 00 00 16 11 e0 00 00 00 01 00 c1 02 10 00 c2 02 03 01 c0 01 0a");
        }
        while (pduLength == 0) {
            if (exit) {
                break;
            }
            try {
                session.write(firstHand);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                logger.info("[" + this.getName() + "]数据采集线程关闭");
            }
        }
        handFlag = true;
    }

    /**
     * create by: 高嵩
     * description: 检查数据采集指令是否生成
     * create time: 2019/8/28 9:38
     *
     * @return
     * @params
     */
    private void checkSendBuff() {
        if (map.size() == 0) {
            if (td.getNoticeAccord().equals("Byte3761")) {
                for (MeterDevice meter : td.getMeterList()) {
                    getSendBuff3761(meter);
                }
                handFlag = true;
            } else if (td.getNoticeAccord().equals("ByteS7")) {
                s7HandBuff();
                for (MeterDevice meter : td.getMeterList()) {
                    getSendBuffS7(meter);
                }
            } else {
                for (MeterDevice meter : td.getMeterList()) {
                    getSendBuffModBus(meter);
                }
                handFlag = true;
            }
        }
    }

    /**
     * create by: 高嵩
     * description: 发送采集数据指令
     * create time: 2019/8/28 9:26
     *
     * @return
     * @params
     */
    private void sendGetDataBuff() {
        if (faultMeter.size() == td.getMeterList().size() && nomalMeter.size() == 0) {
            logger.info("[" + td.getName() + "]所有设备均不在线,断开采集器连接");
            td.setFaultMeter(new HashMap<>());
            td.setNormanMeter(new HashMap<>());
            session.closeNow();
        }
        for (String key : map.keySet()) {
            if (!session.isConnected()) {
                break;
            }
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
            String type = key.substring(firstIndex + 1, lastIndex);//寄存器类型
            String address = key.substring(lastIndex + 1);
            if(td.getNoticeAccord().equals("Byte3761")){
                int seq=Integer.parseInt(session.getAttribute("SEQ").toString());
                seq++;
                if(seq>15){
                    seq=0;
                }
//                System.out.println("取出SEQ："+Integer.toHexString(112+seq));
                byte byte13 = (byte) (112 +seq);
                session.setAttribute("SEQ",seq);
                values[13] = byte13;
                for (int i = 0; i < 12; i++) {
                    values[18] += values[6 + i];
                }//cs
            }else if(td.getNoticeAccord().indexOf("ModBus")>-1){
                int slaveId = values[0];
                int start = Util.bytesToInt(values, 2, 4);
                int len = Util.bytesToInt(values, 4, 6);
                session.setAttribute("slaveId", slaveId);
                session.setAttribute("start", start);
                session.setAttribute("len", len);
            }
            session.setAttribute("ctdCode", ctdCode);
            session.setAttribute("readAddress", address);
            session.setAttribute("type", type);
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
                        }
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                        logger.info("[" + this.getName() + "]数据采集线程关闭");
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        logger.info("[" + this.getName() + "]数据采集线程关闭");
                    }
                }
            }
            try {
                Thread.sleep(td.getCalculateCycle());
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                logger.info("[" + this.getName() + "]数据采集线程关闭");

            }
        }
        try {
            Thread.sleep(td.getRoundCycle());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setMeterStatus(String ctdCode, int value) {
        for (MeterDevice md : td.getMeterList()) {
            if (md.getCode().equals(ctdCode)) {
                if (value == 0) {
                    logger.info("<<=[采集器:" + td.getName() + "][表计" + md.getName() + "]=>>超时");
                    if (nomalMeter.containsKey(ctdCode)) {
                        nomalMeter.remove(ctdCode);
                    }
                    faultMeter.put(ctdCode, md);
                } else {
                    if (faultMeter.containsKey(ctdCode)) {
                        faultMeter.remove(ctdCode);
                    }
                    nomalMeter.put(ctdCode, md);
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
//        List<PointDevice> allPints = meter.getPointDevice();// 所有参数 含非采集参数
        List<PointDevice> points =meter.getPointDevice();// 采集参数
//        MeterDevice timingMd = new MeterDevice();
//        timingMd.setCode(meter.getCode());
//        timingMd.setAddress(meter.getAddress());
//        timingMd.setIncrease(meter.getIncrease());
//        timingMd.setType(meter.getType());
//        timingMd.setPointDevice(new ArrayList<PointDevice>());
//        for (PointDevice p : allPints) {
//            if (p.getIsCollect() == 1) {
//                points.add(p);
//                if (p.getCode().equals("311")) {
//                    timingMd.getPointDevice().add(p);
//                }
//            } else {
//                timingMd.getPointDevice().add(p);
//            }
//        }
//        if (timingMd.getPointDevice().size() > 2) {
//            TimingRunStopThread t = new TimingRunStopThread(timingMd, this);
//            t.setName(meter.getName() + "自控");
//            t.start();
//            if (ConnectorContext.deviceAuto.containsKey(timingMd.getCode())) {
//                BaseThread old = ConnectorContext.deviceAuto.get(meter.getCode());
//                old.exit = true;
//                ConnectorContext.deviceAuto.remove(meter.getCode());
//            }
//            ConnectorContext.deviceAuto.put(meter.getCode(), t);
//        }
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

    /**
     * create by: 高嵩
     * description: 376.1协议数据采集指令
     * create time: 2019/8/28 11:11
     *
     * @return
     * @params
     */
    public void getSendBuff3761(MeterDevice md) {
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
        sendBytes[13]=0;
        int high=(md.getAddress()-1)/8+1;
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
        String key = md.getCode() + ":3:31";
        map.put(key, sendBytes);
    }

    /**
     * create by: 高嵩
     * description: s7协议数据采集指令
     * create time: 2019/8/28 11:11
     *
     * @return
     * @params
     */
    public void getSendBuffS7(MeterDevice meter) {
        List<PointDevice> points = meter.getPointDevice();
        BigDecimal start;
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
            byte[] values = getS7Send(start, p.getPointLen(), p.getStorageType(), p.getMmpType(), p.getDbIndex());
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
                            byte[] values = getS7Send(begin, len, lastfunction, p.getMmpType(), p.getDbIndex());
                            map.put(key, values);
                            key = meter.getCode() + ":" + p.getStorageType() + ":" + address;
                            begin = address;
                            len = curlen;
                        } else {
                            len += curlen;
                            if (i == points.size() - 1) {
                                byte[] values = getS7Send(begin, len, lastfunction, p.getMmpType(), p.getDbIndex());
                                map.put(key, values);
                            }
                        }
                    } else {
                        byte[] values = getS7Send(begin, len, lastfunction, lastMmpType, p.getDbIndex());
                        map.put(key, values);
                        if (i == points.size() - 1) {
                            byte[] last = getS7Send(address, curlen, p.getStorageType(), p.getMmpType(), p.getDbIndex());
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

    private byte[] getS7Send(BigDecimal start, int length, int type, int mmpType, int dbIndex) {
        S7ReadRequest send = new S7ReadRequest();
        send.setArea((byte) type);
        if (type == 132) {
            send.setDbNumber(new byte[]{(byte) (dbIndex >> 8 & 0xff), (byte) (dbIndex & 0xff)});
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

    /**
     * create by: 高嵩
     * description: s7协议写入
     * create time: 2019/8/28 9:21
     *
     * @return
     * @params
     */
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
            request.setDataWriteLength(new byte[]{0, (byte) (pd.getModWlength() * 8)});
            data = Util.realValueToBytes(writeValue, pd.getModWType());
        }
        int dataLength = 4 + pd.getModWlength();
        request.setDataLength(new byte[]{(byte) (dataLength >> 8 & 0xff), (byte) (dataLength & 0xff)});
        request.setRequestDataLength(new byte[]{(byte) (pd.getModWlength() >> 8 & 0xff), (byte) (pd.getModWlength() & 0xff)});
        int totalLength = 35 + pd.getModWlength();
        request.setTotalLength(new byte[]{(byte) (totalLength >> 8 & 0xff), (byte) (totalLength & 0xff)});
        if (pd.getModWFunction() == 132) {
            int dbNumber = pd.getDbIndex();
            request.setDbNumber(new byte[]{(byte) (dbNumber >> 8 & 0xff), (byte) (dbNumber & 0xff)});
        }
        int addressInt = pd.getModWAddress().intValue() * 8;
        int addressPoint = pd.getModWAddress().subtract(new BigDecimal(pd.getModWAddress().intValue())).multiply(new BigDecimal("10")).intValue();
        int address = addressInt + addressPoint;
        request.setAddress(new byte[]{(byte) (address >> 16 & 0xff), (byte) (address >> 8 & 0xff), (byte) (address & 0xff)});
        request.setWriteData(data);
        return request.getSendBytes();
    }
}
