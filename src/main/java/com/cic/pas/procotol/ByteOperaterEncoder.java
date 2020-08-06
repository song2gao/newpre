package com.cic.pas.procotol;

import com.cic.pas.common.util.CRC16M;
import com.cic.pas.procotol.s7.S7ReadRequest;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class ByteOperaterEncoder extends ProtocolEncoderAdapter {
    private Logger logger = Logger.getLogger(ByteS7Encoder.class);

    @Override
    public void encode(IoSession session, Object message,
                       ProtocolEncoderOutput out) throws Exception {
        // Response res = (Response) message;
        IoBuffer buff = IoBuffer.allocate(32);
        buff.setAutoExpand(true);
        buff.setAutoShrink(true);
        byte[] a = (byte[]) message;
        buff.put((byte) 0x68);
        buff.putInt(a.length);
        buff.put(a);
        buff.flip();
        out.write(buff);
        out.flush();
    }
}
