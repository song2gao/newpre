package com.cic.pas.procotol;

import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.PointDevice;
import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.common.util.CRC16M;
import com.cic.pas.common.util.DataType;
import com.cic.pas.common.util.StringUtils;
import com.cic.pas.common.util.Util;
import com.cic.pas.dao.BussinessConfig;
import com.cic.pas.service.ServerContext;
import com.cic.pas.thread.BaseThread;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Byte645Decoder extends CumulativeProtocolDecoder {

    private Logger logger = Logger.getLogger(com.cic.pas.procotol.Byte645Decoder.class);

    @Override
    public boolean doDecode(IoSession session, IoBuffer in,

                            ProtocolDecoderOutput out) throws Exception {
        String ip = "";
        String ctdAddress = session.getAttribute("type").toString();
        String address = session.getAttribute("readAddress").toString();
        if (in.remaining() > 0) {//
            in.mark();// 标记当前位置，以便reset
            // 因为我的前数据包的长度是保存在第6字节中，
            int headSize = 10;
            byte[] headBytes = new byte[headSize];
            in.get(headBytes, 0, headSize);
            int size = Integer.parseInt(Util.bytesToValueRealOffset(headBytes, 9, DataType.ONE_BYTE).toString());
            in.reset();
            if (size > in.remaining()) {// 如果消息内容不够，则重置，相当于不读取size
                return false;// 父类接收新数据，以拼凑成完整数据
            } else {
                byte[] data = new byte[size + headSize + 2];
                try {
                    in.get(data, 0, size + headSize + 2);
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println(size+headSize+2);
                }
//                String sendMessage=session.getAttribute("sendMessage").toString();
//                String recMessage=CRC16M.getBufHexStr(data);
                String terminal_id = session.getAttribute("terminal_id").toString();
                String ctdCode = session.getAttribute("ctdCode").toString();
                analyticMessage(data, ctdAddress, address, terminal_id, ctdCode);
                BaseThread thread = ServerContext.threadMap.get(terminal_id);
                if (thread != null) {
                    synchronized (thread) {
                        thread.isReviced = true;
                        thread.notify();
                    }
                }
            }
        }
        return false;// 处理成功，让父类进行接收下个包
    }

    public void analyticMessage(byte[] message, String ctdAddress, String address, String terminalCode, String ctdCode) {
        String messageStr = CRC16M.getBufHexStr(message).toUpperCase();
        String head = Integer.toHexString(message[0] & 0xff);
        String end = Integer.toHexString(message[message.length - 1] & 0xff);
        if (head.equals("68") && end.equals("16")) {
            String cs = StringUtils.checkCs(messageStr.substring(0, messageStr.length() - 4));
            String csRec = messageStr.substring(messageStr.length() - 4, messageStr.length() - 2);
            if (cs.equals(csRec)) {
                String ctdAddressRec = CRC16M.getBufHexStr(message, 1, 7);
                int length = message[9] & 0xff;
                int datalength = length - 4;
                String pointAddressRec = CRC16M.getBufHexStr(message, 10, 14);
                if (ctdAddress.equals(ctdAddressRec) && address.equals(pointAddressRec)) {
                    String data = CRC16M.getBufHexStr(message, 14, 14 + datalength);
                    data = StringUtils.toSubtract645(data);
                    data = StringUtils.leftPad(data, datalength, '0');
                    data = StringUtils.toSwapStr(data);
                    StringBuffer sb = new StringBuffer();
                    sb.append(data);
                    sb.insert(sb.length() - 2, '.');
//                    int ctdAddressLoc = Integer.parseInt(StringUtils.toSwapStr(ctdAddressRec));
                    int pointAddressLoc = Integer.parseInt(StringUtils.toSwapStr(StringUtils.toSubtract645(pointAddressRec)));
                    setValue(terminalCode, ctdCode, pointAddressLoc, new BigDecimal(sb.toString()));

                }
            } else {
                logger.info("收到非法消息" + messageStr);
            }
        } else {
            logger.info("收到非法消息" + messageStr);
        }
    }

    private void setValue(String terminalCode, String ctdCode, int pdCode, BigDecimal value) {
        String dateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        for (TerminalDevice td : BussinessConfig.terminalList) {
            if (td.getCode().equals(terminalCode)) {
                for (MeterDevice md : td.getMeterList()) {
                    if (md.getCode().equals(ctdCode)) {
                        md.setStatus(1);
                        md.setLastCollectDate(dateTime);
                        for (PointDevice pd : md.getPointDevice()) {
                            if (pd.getModAddress().intValue() == pdCode) {
                                value = Util.manageData(value, pd
                                        .getFormular());
                                if(pd.getIsCt()==1){
                                    value=value.multiply(new BigDecimal(md.getCt()));
                                }
                                if(pd.getIsPt()==1){
                                    value=value.multiply(new BigDecimal(md.getPt()));
                                }
                                pd.setTime(dateTime);
                                pd.setValue(value);
//                                logger.info(md.getName() + "==>" + pd.getName() + "==>" + pd.getValue());
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
}
