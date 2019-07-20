package com.cic.pas.procotol;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.cic.pas.application.SysCfgService;
import com.cic.pas.common.net.Response;
import com.cic.pas.common.util.CRC16M;
import com.cic.pas.common.util.Util;
import com.cic.pas.preMonitor.Bytes;

public class Byte3761Encoder extends ProtocolEncoderAdapter {
	private Logger logger=Logger.getLogger(Byte3761Encoder.class);

	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		// Response res = (Response) message;
		IoBuffer buff = IoBuffer.allocate(32);
		buff.setAutoExpand(true);
		buff.setAutoShrink(true);

		byte[] a = (byte[]) message;
		if(a.length<3){
			Object login=session.getAttribute("login");
			{
				if(login==null||(Boolean)login==false){
					return;
				}
			}
			Integer terminal_id=Integer.parseInt(session.getAttribute("terminal_id").toString());
			byte[] sendBytes =new byte[20];
			sendBytes[0] = sendBytes[5] = 0x68;
			sendBytes[1] = sendBytes[3] = 0x32;
			sendBytes[2] = sendBytes[4] = 0;
			sendBytes[6] = (byte) Integer.parseInt("4B", 16);
			sendBytes[7] = 0x02;
			sendBytes[8] = 0x37;
			sendBytes[9] = (byte) (terminal_id % 256);
			sendBytes[10] = (byte) (terminal_id / 256);
			sendBytes[11] = (byte) Integer.parseInt("F2", 16);
			sendBytes[12] = (byte) Integer.parseInt("0C", 16);
			int seq=Integer.parseInt(session.getAttribute("SEQ").toString());
			seq++;
			if(seq>15){
				seq=0;
			}
//			System.out.println("取出SEQ："+Integer.toHexString(112+seq));
			byte byte13 = (byte) (112 +seq);
			session.setAttribute("SEQ",seq);
			sendBytes[13] = byte13;
			sendBytes[14] = a[0];
			sendBytes[15] = a[1];
			sendBytes[16] = 0x01;
			sendBytes[17] = 0x10;
			sendBytes[18]=0;
			String lowStr=Util.byteToBinary(sendBytes[14]);
			int low=0;
			int binInStr=lowStr.indexOf("1");
			if(binInStr>-1){
				low=lowStr.length()-binInStr;
			}
			int high=((int) sendBytes[15] - 1) * 8;
			int pn = high + low;
			for (int i = 0; i < 12; i++) {
				sendBytes[18] += sendBytes[6 + i];
			}//cs
			sendBytes[19]=0x16;
//			logger.info("["+session.getAttribute("terminal_Name")+"请求数据帧]:[pn="+pn+"]["+CRC16M.getBufHexStr(sendBytes)+"]");
			buff.put(sendBytes);
		}else {
			buff.put(a);
		}

		buff.flip();
		out.write(buff);
		out.flush();
	}

}
