package com.cic.pas.procotol;

import com.cic.pas.common.util.CRC16M;
import com.cic.pas.common.util.Util;
import com.cic.pas.procotol.s7.S7ReadRequest;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class ByteS7Encoder extends ProtocolEncoderAdapter {
    private Logger logger = Logger.getLogger(ByteS7Encoder.class);
    @Override
    public void encode(IoSession session, Object message,
                       ProtocolEncoderOutput out) throws Exception {
        // Response res = (Response) message;
        IoBuffer buff = IoBuffer.allocate(32);
        buff.setAutoExpand(true);
        buff.setAutoShrink(true);
        byte[] a = (byte[]) message;
        if(a.length==31&&(int)a[17]==4) {
            S7ReadRequest readRequest = new S7ReadRequest();
            readRequest.setSendBytes(a);
            String readType = readRequest.getTransportSize() + "";
            session.setAttribute("readType", readType);
        }
        buff.put(a);
//        System.out.println("TX:"+CRC16M.getBufHexStr(a));
        buff.flip();
        out.write(buff);
        out.flush();
    }
}