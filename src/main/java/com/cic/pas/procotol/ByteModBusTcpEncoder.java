package com.cic.pas.procotol;

import com.cic.pas.common.util.Util;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * 版权所有(C)2013-2014 CIC
 * 公司名称：众智盈鑫能源科技（北京）有限公司
 * 版本:V 1.0
 * 文件名：com.cic.pas.procotol @NAME: ByteModBusTcpEncoder
 * 作者: 高嵩
 * 创建时间: 2019-06-03 10:52
 * 修改时间：2019-06-03 10:52
 * 功能描述
 */
public class ByteModBusTcpEncoder  extends ProtocolEncoderAdapter {
    public short headId=0;
    @Override
    public void encode(IoSession session, Object message,
                       ProtocolEncoderOutput out) throws Exception {
        // Response res = (Response) message;
        IoBuffer buff = IoBuffer.allocate(32);
        buff.setAutoExpand(true);
        buff.setAutoShrink(true);
        byte[] a = (byte[]) message;
        int size=a.length;
        byte[] send=new byte[size+6];
        send[0]=(byte)(headId&(0xff>>8));
        send[1]=(byte)(headId&0xff);
        send[2]=0;
        send[3]=0;
        send[4]=(byte)(size&(0xff>>8));
        send[5]=(byte)(size&0xff);
        session.setAttribute("headId",headId);
        for(int i=0;i<a.length;i++){
            send[i+6]=a[i];
        }
        session.setAttribute("sendMessage", send);
        buff.put(send);
        headId++;
        buff.flip();
        out.write(buff);
        out.flush();
    }
}
