package com.cic.pas.procotol;

import com.cic.pas.common.util.CRC16M;
import com.cic.pas.common.util.DataType;
import com.cic.pas.common.util.Util;
import com.cic.pas.service.ServerContext;
import com.cic.pas.thread.BaseThread;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.math.BigDecimal;

/**
 * 版权所有(C)2013-2014 CIC
 * 公司名称：众智盈鑫能源科技（北京）有限公司
 * 版本:V 1.0
 * 文件名：com.cic.pas.procotol @NAME: ByteModBusTcpDecoder
 * 作者: 高嵩
 * 创建时间: 2019-06-03 10:51
 * 修改时间：2019-06-03 10:51
 * 功能描述
 */
public class ByteModBusTcpDecoder extends CumulativeProtocolDecoder {

    ByteModBusDecoder decoder = new ByteModBusDecoder();
    private Logger logger = Logger.getLogger(ByteModBusTcpDecoder.class);

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
            if(in.remaining()==1){
                byte[] test=new byte[1];
                in.get(test);
                return false;
            }
            in.mark();// 标记当前位置，以便reset
            // 因为我的前数据包的长度是保存在第6字节中，
            int headSize = 6;
            byte[] headBytes = new byte[headSize];
            in.get(headBytes, 0, headSize);
            int size = Integer.parseInt(Util.bytesToValueRealOffset(headBytes, 4, DataType.TWO_BYTE_INT_UNSIGNED).toString());
            in.reset();
            if (size +headSize> in.remaining()) {// 如果消息内容不够，则重置，相当于不读取size
                return false;// 父类接收新数据，以拼凑成完整数据
            } else {
                byte[] bytes = new byte[headSize+size];
                in.get(bytes,0,headSize+size);
                int slaveId = (int) session.getAttribute("slaveId");
                long sessionId = session.getId();
                String terminal_id = session.getAttribute("terminal_id").toString();
                BigDecimal start = new BigDecimal(session.getAttribute("readAddress").toString());
                int readType=Integer.parseInt(session.getAttribute("readType").toString());
                int len = (int) session.getAttribute("len");
                String ctdCode = session.getAttribute("ctdCode").toString();
                byte[] sendBytes = (byte[]) session.getAttribute("sendMessage");
                String sendStr = CRC16M.getBufHexStr(sendBytes);
                String recStr = CRC16M.getBufHexStr(bytes);
//                logger.info(recStr);
                checkMessage(session,terminal_id, ctdCode, slaveId, start, len, bytes,readType, sendStr, recStr);
                if (in.remaining() > 0) {// 如果读取内容后还粘了包，就让父类再重读 一次，进行下一次解析
                    // in.flip();
                    return true;
                }
            }
        }

        return false;// 处理成功，让父类进行接收下个包
    }

    public void checkMessage(IoSession session, String terminalCode, String ctdCode, int slaveId, BigDecimal start, int len, byte[] bytes,int readType, String sendStr, String recStr) {
        int id = bytes[6];
        int function = bytes[7];
        int length = Util.bytesToInt(bytes, 8, 9);
        if (id == slaveId) {
            switch (function) {
                case 3:
                case 4:
                    if (len * 2 == length) {
                        byte[] data = Util.bytesSub(bytes, 9, bytes.length - 9);
                        decoder.decoder(terminalCode, ctdCode,function, start, data, sendStr, recStr);
                    }
                    break;
                case 15:
                case 16:
                    session.setAttribute("writeResult", true);
                    break;
            }
            BaseThread thread = ServerContext.threadMap.get(terminalCode);
            synchronized (thread) {
                thread.isReviced = true;
                thread.notify();
            }
        } else {
            System.out.println("无效消息");
        }

    }

}
