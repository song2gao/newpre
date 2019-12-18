package com.cic.pas.procotol;

import java.math.BigDecimal;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.cic.pas.service.ServerContext;
import com.cic.pas.thread.BaseThread;
import com.cic.pas.thread.GetDataThread;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.PointDevice;
import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.common.util.CRC16M;
import com.cic.pas.common.util.Util;
import com.cic.pas.dao.BussinessConfig;

public class Byte3761Decoder extends CumulativeProtocolDecoder {
    private Logger logger = Logger.getLogger(Byte3761Decoder.class);
    /**
     * 这个方法的返回值是重点：
     * <p>
     * 1、当内容刚好时，返回false，告知父类接收下一批内容
     * <p>
     * 2、内容不够时需要下一批发过来的内容，此时返回false，这样父类 CumulativeProtocolDecoder
     * <p>
     * 会将内容放进IoSession中，等下次来数据后就自动拼装再交给本类的doDecode
     * <p>
     * 3、当内容多时，返回true，因为需要再将本批数据进行读取，父类会将剩余的数据再次推送本
     * <p>
     * 类的doDecode
     */
    @Override
    public boolean doDecode(IoSession session, IoBuffer in,
                            ProtocolDecoderOutput out) throws Exception {
        if (in.remaining() > 0) {//
            in.mark();// 标记当前位置，以便reset
            // 因为我的前数据包的长度是保存在第6字节中，
            int headSize = 3;
            byte[] headBytes = new byte[headSize];
            in.get(headBytes, 0, headSize);
            int size = Integer.parseInt(Util.bytesToValueRealOffset(headBytes, 1, 22).toString());
            size = size >> 2;
            size = size + 8;
            in.reset();

            if (size > in.remaining()) {// 如果消息内容不够，则重置，相当于不读取size
                return false;// 父类接收新数据，以拼凑成完整数据
            } else {
                byte[] bytes = new byte[size];
                in.get(bytes);
                System.out.println("RX:"+CRC16M.getBufHexStr(bytes));
                checkMessage(session,bytes);
                BaseThread thread = ServerContext.threadMap
                        .get(session.getAttribute("terminal_id")+"");
                if(thread!=null) {
                    synchronized (thread) {
                        thread.isReviced=true;
                        thread.notify();

                    }
                }
                if (in.remaining() > 0) {// 如果读取内容后还粘了包，就让父类再重读 一次，进行下一次解析
                    // in.flip();
                    return true;
                }
            }
        }

        return false;// 处理成功，让父类进行接收下个包
    }

    private void saveData(String asstdCode, String ctdCode, BigDecimal value) {
        for (TerminalDevice td : BussinessConfig.terminalList) {
            if (td.getCode().equals(asstdCode)) {
                for (MeterDevice md : td.getMeterList()) {
                    if (md.getAddress()==Integer.parseInt(ctdCode)) {
                        for (PointDevice pd : md.getPointDevice()) {
                            if (pd.getCode().equals("31")) {
                                String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                                md.setLastCollectDate(dateTime);
                                md.setStatus(1);
                                pd.setTime(dateTime);
                                if (pd.getIsCt() == 1) {
                                    value = value.multiply(new BigDecimal(md.getCt()));
                                }
                                if (pd.getIsPt() == 1) {
                                    value = value.multiply(new BigDecimal(md.getPt()));
                                }
                                pd.setValue(value);
//                                logger.info("[" + td.getName() + "][" + md.getName() + "][" + pd.getName() + "]:" + pd.getValue());
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

    private String getIP(IoSession session) {
        SocketAddress client = session.getRemoteAddress();
        String ipPort=client.toString().substring(1);
        String IP = ipPort.substring(0,ipPort.indexOf(":"));
        return IP;
    }

    public void checkMessage(IoSession session, byte[] bytes) {
        if (bytes != null) {
            String ip = getIP(session);
            int byte6 = (int) bytes[6];
            int prm = byte6 >> 6 & 0x01;
            int cmd = byte6 & 0x0f;
            switch (prm) {
                case 1:
                    switch (cmd) {
                        case 9:
                            prm1function09(session, bytes, ip);
                            break;
                    }
                    break;
                case 0:
                    switch (cmd) {
                        case 9:
                            break;
                        case 8:
                            prm0function08(session,bytes);
                            break;
                    }
                    break;
            }
        } else {
            logger.info("服务器接收消息不是字节数组，请检查...............");
        }
    }

    private void prm1function09(IoSession session, byte[] bytes, String ip) {
        int terminal_id = (int) bytes[10] * 256 + (int) bytes[9];
        int aFN = (int)bytes[12];
        int fN=  (int) bytes[17] * 8 + (int) bytes[16];
        if(fN==1) {
            for (TerminalDevice t : BussinessConfig.config.terminalList) {
                if (terminal_id == Integer.parseInt(t.getCode())) {
                    t.setMSA(ip);
                    t.setIsOnline(1);
                    session.setAttribute("terminal_id", terminal_id);
                    session.setAttribute("terminal_Name", t.getName());
                    session.setAttribute("login",true);
                    ServerContext.addSession(session);
                    logger.info("采集设备 [ " + t.getName() + " ]正确接入系统");
                    logger.info("[" + t.getName() + "登录帧]:[" + CRC16M.getBufHexStr(bytes) + "]");
                    byte[] bytes0 = new byte[20];// = Util.getByte(temp0);
                    bytes0[0] = bytes0[5] = 0x68;
                    bytes0[1] = bytes0[3] = 0x32;
                    bytes0[2] = bytes0[4] = 0x0;
                    bytes0[6] = 0x40;
                    bytes0[7] = 0x02;
                    bytes0[8] = 0x37;
                    bytes0[9] = (byte) (terminal_id % 256);
                    bytes0[10] = (byte) (terminal_id / 256);
                    bytes0[11] = (byte) Integer.parseInt("F2", 16);
                    bytes0[12] = 0;
                    bytes0[13] = 0x60;
                    bytes0[14] = bytes0[15] = 0x0;//pn
                    bytes0[16] = 1;//fn
                    bytes0[17] = 0;
                    int seq =(int)bytes[13]&0x0f;
                    bytes0[13]=(byte)(0x60+seq);
                    for (int i = 0; i < 12; i++) {
                        bytes0[18] += bytes0[6 + i];
                    }//cs
                    bytes0[19] = 0x16;
                    int rseq = (int) bytes0[13]&0x0f;
                    logger.info("[" + t.getName() + "确认帧]:[" + CRC16M.getBufHexStr(bytes0) + "]");
                    session.setAttribute("SEQ", seq);
                    session.setAttribute("RSEQ", rseq);
                    session.setAttribute("terminal_id",terminal_id);
                    session.setAttribute("terminal_name", t.getName());
                    session.setAttribute("terminal", t);
                    session.write(bytes0);
                    BaseThread thread=new GetDataThread(t,session);
                    thread.setName(t.getName()+"["+t.getCode()+"]");
                    ((GetDataThread) thread).handFlag=true;
                    thread.start();
                    ServerContext.addThread(t.getCode(), thread);
                }
            }
        }else if(fN==3) {
           //发送心跳数据
            if (bytes.length == 20) {
                //String temp0 = ProtocolUtil.xintao(terminal_id+"");
                String name = session.getAttribute("terminal_Name").toString();
                logger.info("[" + name + "心跳帧]:[" + CRC16M.getBufHexStr(bytes) + "]");
                byte[] bytes0 = new byte[20];// = Util.getByte(temp0);
                bytes0[0] = bytes0[5] = 0x68;
                bytes0[1] = bytes0[3] = 0x32;
                bytes0[2] = bytes0[4] = 0x0;
                bytes0[6] = 0x40;
                bytes0[7] = 0x02;
                bytes0[8] = 0x37;
                bytes0[9] = (byte) (terminal_id % 256);
                bytes0[10] = (byte) (terminal_id / 256);
                bytes0[11] = (byte) Integer.parseInt("F2", 16);
                bytes0[12] = 0;
                int rseq = Integer.parseInt(session.getAttribute("RSEQ").toString());
                rseq++;
                if (rseq > 15) {
                    rseq = 0;
                }
                bytes0[13] = (byte) (rseq + 96);
                int seq =(int)bytes[13]&0x0f;
                session.setAttribute("SEQ", seq);
                session.setAttribute("RSEQ",rseq);
                bytes0[14] = bytes0[15] = 0x0;//pn
                bytes0[16] = 1;//fn
                bytes0[17] = 0;
                for (int i = 0; i < 12; i++) {
                    bytes0[18] += bytes0[6 + i];
                }//cs
                bytes0[19] = 0x16;
                logger.info("[" + name + "心跳响应帧]:[" + CRC16M.getBufHexStr(bytes0) + "]");
                session.write(bytes0);
            }
        }
    }
    private void prm0function08(IoSession session,byte[] bytes){
        byte[] sbyte = new byte[bytes.length];
        for (int i = 0; i < sbyte.length; i++) {
            sbyte[i] = bytes[i];
        }
        int asstdCode = (int) bytes[9] + (int) bytes[10] * 256;
        int aFN = (int)bytes[12];
        int rseq = (int)bytes[13]&0x0f;
        session.setAttribute("RSEQ", rseq);
        String lowStr=Util.byteToBinary(bytes[14]);
        int low=0;
        int binInStr=lowStr.indexOf("1");
        if(binInStr>-1){
            low=lowStr.length()-binInStr;
        }
        int high=((int) bytes[15] - 1) * 8;
        int pn = high + low;
        int fn = (int) bytes[17] * 8 + (int) bytes[16];
        if(aFN==0){
            String ctdCode=session.getAttribute("ctdCode").toString();
            String terminalCode=session.getAttribute("terminal_id").toString();
            MeterDevice md=BussinessConfig.getMeterByTerminalCodeAndCtdCode(terminalCode,ctdCode);
            md.setStatus(0);
            logger.info("["+session.getAttribute("terminal_Name")+"终端否定所发请求]:[pn=" + pn + "][" + CRC16M.getBufHexStr(bytes) + "]");
        }else {
            logger.info("["+session.getAttribute("terminal_Name")+"回复数据帧]:[pn=" + pn + "][" + CRC16M.getBufHexStr(bytes) + "]");
            saveData(asstdCode + "", pn + "", Util.bytesHexStrToBIgDecimal(bytes, 24, 5));
        }
    }
    public static void main(String[] args){
        byte[] bytes=CRC16M.HexString2Buf("0005");
        String lowStr=Util.byteToBinary(bytes[0]);
        int low=0;
        int binInStr=lowStr.indexOf("1");
        if(binInStr>-1){
            low=lowStr.length()-binInStr;
        }
        int height=((int) bytes[1] - 1) * 8;
        int pn = height + low;
        System.out.println(low);
        System.out.println(height);
        System.out.println(pn);
    }
}