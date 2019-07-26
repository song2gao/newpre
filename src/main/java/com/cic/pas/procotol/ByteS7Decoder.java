package com.cic.pas.procotol;

import com.cic.pas.common.bean.Data;
import com.cic.pas.common.util.CRC16M;
import com.cic.pas.common.util.DataType;
import com.cic.pas.common.util.Util;
import com.cic.pas.service.ServerContext;
import com.cic.pas.thread.BaseThread;
import com.cic.pas.thread.GetDataThread;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;


public class ByteS7Decoder extends CumulativeProtocolDecoder {
    private Logger logger = Logger.getLogger(com.cic.pas.procotol.ByteS7Decoder.class);
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
                int function = Integer.parseInt(Util.bytesToValueRealOffset(bytes, 17, DataType.ONE_BYTE).toString());
                if (bytes.length == 22) {
                    logger.info("一次握手成功\r\n报文：" + messageHex);
                    session.write(secondHand);
                } else if (bytes.length == 27 && function == 240) {//0xf0
                    logger.info("二次握手成功\r\n报文：" + messageHex);
                    int pduLength = Integer.parseInt(Util.bytesToValueRealOffset(bytes, bytes.length - 2, DataType.TWO_BYTE_INT_UNSIGNED).toString());
                    GetDataThread thread = (GetDataThread) ServerContext.threadMap.get(terminal_id);
                    if (thread != null) {
                        thread.pduLength = pduLength;
                    }
                } else{
                    analyticMessage(bytes,terminal_id);
                }
                if (in.remaining() > 0) {// 如果读取内容后还粘了包，就让父类再重读 一次，进行下一次解析
                    // in.flip();
                    return true;
                }
            }
        }
        return false;// 处理成功，让父类进行接收下个包
    }

    public void analyticMessage(byte[] message,String terminal_id) {
        String messageHex = CRC16M.getBufHexStr(message);
        logger.info(messageHex);
    }
}
