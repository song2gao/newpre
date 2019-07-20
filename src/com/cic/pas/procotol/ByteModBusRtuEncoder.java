package com.cic.pas.procotol;

import com.cic.pas.common.util.CRC16M;
import com.cic.pas.common.util.Util;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * 版权所有(C)2013-2014 CIC
 * 公司名称：众智盈鑫能源科技（北京）有限公司
 * 版本:V 1.0
 * 文件名：com.cic.pas.procotol @NAME: ByteModBusRtuEncoder
 * 作者: 高嵩
 * 创建时间: 2019-06-03 10:52
 * 修改时间：2019-06-03 10:52
 * 功能描述
 */
public class ByteModBusRtuEncoder  extends ProtocolEncoderAdapter {

    @Override
    public void encode(IoSession session, Object message,
                       ProtocolEncoderOutput out) throws Exception {
        // Response res = (Response) message;
        IoBuffer buff = IoBuffer.allocate(32);
        buff.setAutoExpand(true);
        buff.setAutoShrink(true);

        byte[] a = (byte[]) message;
        int slaveId = a[0];
        int start = Util.bytesToInt(a, 2, 4);
        int len = Util.bytesToInt(a, 4, 6);
        session.setAttribute("slaveId", slaveId);
        session.setAttribute("start", start);
        session.setAttribute("len", len);
        byte[] send=CRC16M.getSendBuf(a);
        buff.put(send);
        session.setAttribute("sendMessage", send);
        buff.flip();
        out.write(buff);
        out.flush();
    }
}
