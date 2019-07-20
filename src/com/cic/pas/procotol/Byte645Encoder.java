package com.cic.pas.procotol;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class Byte645Encoder extends ProtocolEncoderAdapter {
    private Logger logger=Logger.getLogger(Byte645Encoder.class);

    @Override
    public void encode(IoSession session, Object message,
                       ProtocolEncoderOutput out) throws Exception {
        // Response res = (Response) message;
        IoBuffer buff = IoBuffer.allocate(32);
        buff.setAutoExpand(true);
        buff.setAutoShrink(true);

        byte[] a = (byte[]) message;
        buff.put(a);

        buff.flip();
        out.write(buff);
        out.flush();
    }
}
