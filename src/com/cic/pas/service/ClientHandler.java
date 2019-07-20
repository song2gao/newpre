package com.cic.pas.service;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.PointDevice;
import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.common.util.CRC16M;
import com.cic.pas.common.util.Util;
import com.cic.pas.dao.BussinessConfig;

public class ClientHandler extends IoHandlerAdapter {
	public static Logger logger = Logger.getLogger(StandardServer.class);
	public static int address = 0;
	private Map<String, byte[]> map = new HashMap<String, byte[]>();
	private String sendStr = "";

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		System.out.println("服务器发生异常");
		cause.printStackTrace();
	}

	@Override
	public void messageReceived(IoSession iosession, Object message)
			throws Exception {
		logger.info("从" + getIP(iosession) + "收到消息:" + message);
	}

	@Override
	public void sessionIdle(IoSession iosession, IdleStatus status) {
		logger.info("-客户端与服务端连接[空闲] - " + status.toString());
		if (iosession != null) {
			iosession.close(true);
		}
		System.out.println("IDLE " + iosession.getIdleCount(status));
	}

	@Override
	public void messageSent(IoSession iosession, Object message)
			throws Exception {
		//String hexStr = CRC16M.getBufHexStr((byte[]) message);
		logger.info("向" + getIP(iosession) + "发送消息:" + CRC16M.getBufHexStr((byte[]) message));
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
//		String ip = session.getRemoteAddress().toString().substring(1);
//		TerminalDevice terminalDevice = BussinessConfig.getTerminalByIP(ip);
//		for (MeterDevice meter : terminalDevice.getMeterList()) {
//			getSendBuff(meter);
//		}
//		if(map!=null){
//			for(String key : map.keySet()){
//				sendStr=key;
//				session.write(map.get(key));
//				break;
//			}
//		}

	}

	private String getIP(IoSession session) {
		SocketAddress client = session.getRemoteAddress();
		String IP = client.toString().substring(1,
				client.toString().lastIndexOf(":"));
		return IP;
	}

	

}
