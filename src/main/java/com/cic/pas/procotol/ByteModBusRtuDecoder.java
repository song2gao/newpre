package com.cic.pas.procotol;

import com.cic.pas.common.util.CRC16M;
import com.cic.pas.common.util.DataType;
import com.cic.pas.common.util.Util;
import com.cic.pas.service.ServerContext;
import com.cic.pas.thread.BaseThread;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.math.BigDecimal;

/**
 * 版权所有(C)2013-2014 CIC
 * 公司名称：众智盈鑫能源科技（北京）有限公司
 * 版本:V 1.0
 * 文件名：com.cic.pas.procotol @NAME: ByteModBusRtuDecoder
 * 作者: 高嵩
 * 创建时间: 2019-06-03 10:52
 * 修改时间：2019-06-03 10:52
 * 功能描述
 */
public class ByteModBusRtuDecoder extends CumulativeProtocolDecoder {

    ByteModBusDecoder decoder = new ByteModBusDecoder();

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
        if (in.remaining() > 3) {//
            int slaveId = (int) session.getAttribute("slaveId");
            int len = (int) session.getAttribute("len");
            in.mark();// 标记当前位置，以便reset
            // 因为我的前数据包的长度是保存在第6字节中，
            int headSize = 3;
            byte[] headBytes = new byte[headSize];
            in.get(headBytes, 0, headSize);
            int address = Util.bytesToInt(headBytes, 0, 1);
            int function = Util.bytesToInt(headBytes, 1, 2);
            int size = 0;
            if (function == 1 || function == 2 || function == 3 || function == 4) {
                size = Util.bytesToInt(headBytes, 2, 3);
            } else {
                size = 3;
            }
            in.reset();
            if (slaveId != address && len * 2 != size) {
                byte[] toDel = new byte[in.remaining()];
                in.get(toDel);
                return false;
            }
            if (size + 5 > in.remaining()) {// 如果消息内容不够，则重置，相当于不读取size
                return false;// 父类接收新数据，以拼凑成完整数据
            } else {
                byte[] bytes = new byte[size + 5];
                in.get(bytes);
                String terminal_id = session.getAttribute("terminal_id").toString();
                BigDecimal start = new BigDecimal(session.getAttribute("readAddress").toString());
                int readType = Integer.parseInt(session.getAttribute("readType").toString());
                String ctdCode = session.getAttribute("ctdCode").toString();
                byte[] sendBytes = (byte[]) session.getAttribute("sendMessage");
                String sendStr = CRC16M.getBufHexStr(sendBytes);
                String recStr = CRC16M.getBufHexStr(bytes);
//                System.out.println("TX:"+sendStr);
//                System.out.println("RX:"+recStr);
                checkMessage(session, terminal_id, ctdCode, slaveId, start, len, bytes, readType, sendStr, recStr);
                if (in.remaining() > 0) {// 如果读取内容后还粘了包，就让父类再重读 一次，进行下一次解析
                    // in.flip();
                    return true;
                }
            }
        }

        return false;// 处理成功，让父类进行接收下个包
    }

    public void checkMessage(IoSession session, String terminalCode, String ctdCode, int slaveId, BigDecimal start, int len, byte[] bytes, int readType, String sendStr, String recStr) {
        int id = bytes[0];
        int function = bytes[1];
        int length = Util.bytesToInt(bytes, 2, 3);
        if (id == slaveId) {
            BaseThread thread = ServerContext.threadMap.get(terminalCode);
            if (thread == null) {
                return;
            }
            synchronized (thread) {
                thread.isReviced = true;
                thread.notify();
            }
            switch (function) {
                case 1:
                case 2:
                    byte[] bitByteData = Util.bytesSub(bytes, 3, bytes.length - 5);
                    byte[] bitData = getBinsOfBytes(bitByteData);
                    decoder.decoder(terminalCode, ctdCode, function, start, bitData, sendStr, recStr);
                    break;
                case 3:
                case 4:
                    if (length == len * 2) {
                        byte[] data = Util.bytesSub(bytes, 3, bytes.length - 5);
                        if (readType == 1) {
                            byte[] newData = getBinsOfBytes(data);
                            decoder.decoder(terminalCode, ctdCode, function, start, newData, sendStr, recStr);
                        } else {
                            decoder.decoder(terminalCode, ctdCode, function, start, data, sendStr, recStr);
                        }
                    }
                    break;
                case 15:
                case 16:
                    session.setAttribute("writeResult", true);
                    break;

            }
        } else {
            System.out.println("无效消息[" + recStr + "]");
        }

    }
    /**
     * create by: 高嵩
     * description: 字节数组转化为二进制    返回数组下标0对应 最低位（第0位）
     * 01 01 返回 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 1
     * create time: 2019/11/17 17:49
     * @params
     * @return
     */
    private byte[] getBinsOfBytes( byte[] bitByteData) {
        byte[] bitData = new byte[bitByteData.length * 8];
        for (int i = 0; i < bitByteData.length; i++) {
            bitData[i * 8 + 0] = (byte) (bitByteData[i] >> 7 & 0x01);
            bitData[i * 8 + 1] = (byte) (bitByteData[i] >> 6 & 0x01);
            bitData[i * 8 + 2] = (byte) (bitByteData[i] >> 5 & 0x01);
            bitData[i * 8 + 3] = (byte) (bitByteData[i] >> 4 & 0x01);
            bitData[i * 8 + 4] = (byte) (bitByteData[i] >> 3 & 0x01);
            bitData[i * 8 + 5] = (byte) (bitByteData[i] >> 2 & 0x01);
            bitData[i * 8 + 6] = (byte) (bitByteData[i] >> 1 & 0x01);
            bitData[i * 8 + 7] = (byte) (bitByteData[i] & 0x01);
        }
        return bitData;
    }

}
