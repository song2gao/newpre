package com.cic.pas.procotol;

import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.PointDevice;
import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.common.util.CRC16M;
import com.cic.pas.common.util.DataType;
import com.cic.pas.common.util.ModBusUtil;
import com.cic.pas.common.util.Util;
import com.cic.pas.dao.BussinessConfig;
import com.cic.pas.procotol.s7.S7ReadResponse;
import com.cic.pas.procotol.s7.S7Response;
import com.cic.pas.service.ServerContext;
import com.cic.pas.thread.BaseThread;
import com.cic.pas.thread.GetDataThread;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ByteS7Decoder extends CumulativeProtocolDecoder {
    private Logger logger = Logger.getLogger(ByteS7Decoder.class);
    private byte[] secondHand = CRC16M.HexString2Buf("03 00 00 19 02 F0 80 32 01 00 00 CC C1 00 08 00 00 F0 00 00 01 00 01 03 C0");

    @Override
    public boolean doDecode(IoSession session, IoBuffer in,
                            ProtocolDecoderOutput out) throws Exception {
        if (in.remaining() > 0) {//
            in.mark();// 标记当前位置，以便reset
            // 因为我的前数据包的长度是保存在第6字节中，
            int headSize = 4;
            byte[] headBytes = new byte[headSize];
            in.get(headBytes, 0, headSize);
            int size = Integer.parseInt(Util.bytesToValueRealOffset(headBytes, 2, DataType.TWO_BYTE_INT_UNSIGNED).toString());
            in.reset();
            if (size > in.remaining()) {// 如果消息内容不够，则重置，相当于不读取size
                return false;// 父类接收新数据，以拼凑成完整数据
            } else {
                byte[] bytes = new byte[size];
                in.get(bytes, 0, size);
                String terminal_id = session.getAttribute("terminal_id").toString();
                String messageHex = CRC16M.getBufHexStr(bytes);
                int cotpFunction = Integer.parseInt(Util.bytesToValueRealOffset(bytes, 5, DataType.ONE_BYTE).toString());
                int function = Integer.parseInt(Util.bytesToValueRealOffset(bytes, 19, DataType.ONE_BYTE).toString());
                if (bytes.length == 22 && cotpFunction == 208) {
//                    logger.info("一次握手成功\r\n报文：" + messageHex);
                    session.write(secondHand);
                } else if (bytes.length == 27 && function == 240) {//0xf0
//                    logger.info("二次握手成功\r\n报文：" + messageHex);
                    int pduLength = Integer.parseInt(Util.bytesToValueRealOffset(bytes, bytes.length - 2, DataType.TWO_BYTE_INT_UNSIGNED).toString());
                    GetDataThread thread = (GetDataThread) ServerContext.threadMap.get(terminal_id);
                    if (thread != null) {
                        thread.pduLength = pduLength;
                    }
                } else {
                    String ctdCode = session.getAttribute("ctdCode").toString();
                    int readType = Integer.parseInt(session.getAttribute("readType").toString());
                    BigDecimal readAddress = new BigDecimal(session.getAttribute("readAddress").toString());
                    analyticMessage(bytes, terminal_id, ctdCode, readType, readAddress, session);
                }
                if (in.remaining() > 0) {// 如果读取内容后还粘了包，就让父类再重读 一次，进行下一次解析
                    // in.flip();
                    return true;
                }
            }
        }
        return false;// 处理成功，让父类进行接收下个包
    }

    public void analyticMessage(byte[] message, String terminal_id, String ctdCode, int readType, BigDecimal readAddress,IoSession session) {
        S7Response response = new S7Response();
        response.setResponseBytes(message);
        if (response.getReturnCode() == (byte) 0xff && response.getErrorClass() == 0 && response.getErrorCode() == 0) {
            if (response.getFunction() == 0x04) {
                byte[] data = response.getResponseData();
                if (response.getFunction() == 0x04) {
                    MeterDevice md = BussinessConfig.getPointsByTerCodeAndMeterCode(terminal_id, ctdCode);
                    BigDecimal i = new BigDecimal("0");
                    BigDecimal lastlen = new BigDecimal(0);
                    for (PointDevice pd : md.getPointDevice()) {
                        readAddress = readAddress.add(lastlen);
                        if (pd.getModAddress().compareTo(readAddress) == 0) {
                            BigDecimal pointlen = new BigDecimal(0);
                            if (i.intValue() + pointlen.intValue() > data.length) {
                                break;
                            }
                            pointlen = setPointValues(data, readAddress, md, i, pd, readType);
                            lastlen = pointlen;
                            i = i.add(lastlen);
                            BigDecimal pointValue = pd.getModAddress().subtract(new BigDecimal(pd.getModAddress().intValue()));
                            int flag = pointValue.compareTo(new BigDecimal("0.6"));
                            if (flag > 0) {
                                lastlen = lastlen.add(new BigDecimal("0.2"));
                            }
                        } else {
                            lastlen = new BigDecimal("0");
                        }
                    }
                }
            } else if (response.getFunction() == 0x05) {
                if(response.getReturnCode()==(byte)0xff){
                    session.setAttribute("writeResult",true);
                }
            }
        } else {
            if (response.getErrorClass() != 0) {
                logger.info("错误(errorClass):" + response.getErrorClassStr());
            }
            if (response.getErrorCode() != 0) {
                logger.info("错误(errrorCode):" + response.getErrorCodeStr());
            }
            if (response.getReturnCode() != (byte) 0xff) {
                logger.info("错误(returnCode):" + response.getReturnCodeStr());
            }
        }
        BaseThread thread = ServerContext.threadMap.get(terminal_id);
        if (thread == null) {
            return;
        }
        synchronized (thread) {
            thread.isReviced = true;
            thread.notify();
        }
    }

    private BigDecimal setPointValues(byte[] data, BigDecimal address, MeterDevice md, BigDecimal i, PointDevice pd, int readType) {
        BigDecimal pointlen;
        BigDecimal value;
        if (pd.getMmpType() == 1) {
            pointlen = new BigDecimal(pd.getPointLen()).divide(new BigDecimal("10"));
            if (readType == 1) {
                value = new BigDecimal(data[i.intValue()]);
            } else {
                byte b = Byte.parseByte(Util.bytesToValueRealOffset(data, i.intValue(),
                        DataType.ONE_BYTE).toString());
                byte[] bins = Util.byteToBinaryArray(b);
                int binIndex = address.subtract(new BigDecimal(address.intValue())).multiply(new BigDecimal("10")).intValue();
                value = new BigDecimal(bins[binIndex]);
            }
        } else {
            pointlen = new BigDecimal(pd.getPointLen());
            value = new BigDecimal(Util.bytesToValueRealOffset(data, i.intValue(),
                    pd.getMmpType()).toString());
        }
        if (pd.getIsCt() == 1) {
            value = value.multiply(new BigDecimal(md.getCt()));
        }
        if (pd.getIsPt() == 1) {
            value = value.multiply(new BigDecimal(md.getPt()));
        }
        String showValue = value + "";
        try {
            if (pd.getIsBit() == 1) {
                try {
                    if (pd.getFormatMap() != null) {
                        showValue = pd.getFormatMap().get(value.intValue() + "").toString();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                value = Util.manageData(value, pd
                        .getFormular());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        pd.setValue(value);
        pd.setShowValue(showValue);
        String dateTime = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss").format(new Date());
        pd.setTime(dateTime);
//        System.out.println(pd.getName()+":"+pd.getValue());
        if (pd.getCode().equals("10000")) {//PLC采集表计通讯状态
            if (pd.getValue().intValue() > 0) {
                md.setStatus(0);
            } else {
                md.setStatus(1);
                md.setLastCollectDate(dateTime);
            }
        } else {
            if (ByteModBusDecoder.getOnlineStatus(md)) {
                md.setStatus(1);
            }
            md.setLastCollectDate(dateTime);
        }
        return pointlen;
    }

    public int getAddress(BigDecimal address) {
        int resultInt = address.intValue() * 8;
        int resultFloat = (int) (address.multiply(new BigDecimal(address.intValue())).doubleValue() * 10);
        return resultInt + resultFloat;
    }
}
