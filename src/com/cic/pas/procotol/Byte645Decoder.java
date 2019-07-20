package com.cic.pas.procotol;

import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.PointDevice;
import com.cic.pas.common.util.CRC16M;
import com.cic.pas.common.util.StringUtils;
import com.cic.pas.dao.BussinessConfig;
import com.cic.pas.service.ConnectorContext;
import com.cic.pas.thread.Get645DataThread;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.math.BigDecimal;

public class Byte645Decoder extends CumulativeProtocolDecoder{

        private Logger logger = Logger.getLogger(com.cic.pas.procotol.Byte645Decoder.class);
        @Override
        public boolean doDecode(IoSession session, IoBuffer in,

                                ProtocolDecoderOutput out) throws Exception {
            String ip = "";
            String ctdAddress=session.getAttribute("ctdAddress").toString();
            String address=session.getAttribute("address").toString();
            if (in.remaining() > 0) {//
                Get645DataThread thread=(Get645DataThread) ConnectorContext.threadMap.get(ip);
                thread.waitTime=0;
                thread.goon();
                StringBuffer sb=new StringBuffer();
                sb.append(in.getHexDump());
                byte[] data=new byte[in.remaining()];
                in.get(data);
                analyticMessage(data,ctdAddress,address,ip);
            }
            return false;// 处理成功，让父类进行接收下个包
        }
        public void analyticMessage(byte[] message,String ctdAddress,String address,String ip){
            String messageStr=CRC16M.getBufHexStr(message).toUpperCase();
            String head=Integer.toHexString(message[0]&0xff);
            String end=Integer.toHexString(message[message.length-1]&0xff);
            if(head.equals("68")&&end.equals("16")){
                String cs=StringUtils.checkCs(messageStr.substring(0,messageStr.length()-4));
                String csRec=messageStr.substring(messageStr.length()-4,messageStr.length()-2);
                if(cs.equals(csRec)){
                    String ctdAddressRec=CRC16M.getBufHexStr(message,1,7);
                    int length=message[9]&0xff;
                    int datalength=length - 4;
                    String pointAddressRec=CRC16M.getBufHexStr(message,10,14);
                    if(ctdAddress.equals(ctdAddressRec)&&address.equals(pointAddressRec)) {
                        String data = CRC16M.getBufHexStr(message, 14, 14 +datalength );
                        data=StringUtils.toSubtract645(data);
                        data=StringUtils.leftPad(data,datalength,'0');
                        data=StringUtils.toSwapStr(data);
                        StringBuffer sb=new StringBuffer();
                        sb.append(data);
                        sb.insert(sb.length()-2,'.');
                        int ctdAddressLoc = Integer.parseInt(StringUtils.toSwapStr(ctdAddressRec));
                        int pointAddressLoc=Integer.parseInt(StringUtils.toSwapStr(StringUtils.toSubtract645(pointAddressRec)));
                        MeterDevice md =BussinessConfig.getMeterByTerIpAndAddress(ip,ctdAddressLoc);
                        for(PointDevice pd:md.getPointDevice()){
                            if(pd.getModAddress()==pointAddressLoc){
                                pd.setValue(new BigDecimal(sb.toString()));
                                logger.info(md.getName()+"==>"+pd.getName()+"==>"+pd.getValue());
                                break;
                            }
                        }
                    }
                }else{
                    logger.info("收到非法消息"+messageStr);
                }
            }else{
                logger.info("收到非法消息"+messageStr);
            }
        }
}
