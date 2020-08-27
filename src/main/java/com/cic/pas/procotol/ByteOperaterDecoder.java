package com.cic.pas.procotol;

import com.alibaba.fastjson.JSONObject;
import com.cic.pas.common.net.ReturnMessage;
import com.cic.pas.common.util.CRC16M;
import com.cic.pas.common.util.Util;
import com.cic.pas.process.Processer;
import com.cic.pas.process.ProcesserFactory;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class ByteOperaterDecoder extends CumulativeProtocolDecoder {
    private Processer processer = ProcesserFactory.getInstance();

    @Override
    public boolean doDecode(IoSession session, IoBuffer in,
                            ProtocolDecoderOutput out) throws Exception {
        if (in.remaining() > 5) {
            in.mark();
            byte[] headBytes = new byte[5];
            in.get(headBytes);
            int length = Util.bytesToInt(headBytes, 1, 5);
            in.reset();
            if (in.remaining() < length+5) {// 如果消息内容不够，则重置，相当于不读取size
                return false;// 父类接收新数据，以拼凑成完整数据
            } else {
                byte[] bytes = new byte[length+5];
                in.get(bytes);
                byte[] data=new byte[length];
                System.arraycopy(bytes,5,data,0,length);
                String json = new String(data,"UTF-8");
                ReturnMessage response = processer.executeSome(json);
                String resStr = JSONObject.toJSONString(response);
                session.write(resStr.getBytes("UTF-8"));
                if (in.remaining() > 0) {// 如果读取内容后还粘了包，就让父类再重读 一次，进行下一次解析
                    // in.flip();
                    return true;
                }
            }
        }
        return false;// 处理成功，让父类进行接收下个包
    }

}
