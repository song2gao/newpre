package com.cic.pas.procotol;

import java.net.SocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;

import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.dao.BussinessConfig;
import com.cic.pas.service.ServerContext;


public class CheckFilter extends IoFilterAdapter {
	
	public static final Logger logger = Logger.getLogger(CheckFilter.class);
	public static byte PFC = 0 ;
	@SuppressWarnings("static-access")
	@Override
	/**
	 * 验证
	 */
	public void messageReceived(NextFilter nextFilter, IoSession session,
			Object message) throws Exception {
				if(session.getAttribute("terminalList")==null){
					session.setAttribute("terminalList",BussinessConfig.config.terminalInfo);
				}
			super.messageReceived(nextFilter, session, message);
	}
	
	private String getIP(IoSession session) {
		SocketAddress client = session.getRemoteAddress();
		String IP = client.toString().substring(1,
				client.toString().lastIndexOf(":"));
		return IP;
	}

	@Override
	public void sessionIdle(NextFilter nextFilter, IoSession session,
			IdleStatus status) throws Exception {
	
		super.sessionIdle(nextFilter, session, status);
	}

	@Override
	public void sessionClosed(NextFilter nextFilter, IoSession session)
			throws Exception {
		
		logger.info(getIP(session) + "断开连接!");
		session.close(false);
		ServerContext.remove(session.getId());
		//super.sessionClosed(nextFilter, session);
	}

	@Override
	public void messageSent(NextFilter nextFilter, IoSession session,
			WriteRequest writeRequest) throws Exception {
		super.messageSent(nextFilter, session, writeRequest);
	}

	@Override
	public void sessionOpened(NextFilter nextFilter, IoSession session)
			throws Exception {
		boolean flag = false;
		String IP = getIP(session);
		for (TerminalDevice terminal : BussinessConfig.config.terminalList) {
			if (terminal.getMSA().equals(IP)) {
				ServerContext.addSession(session);
				logger.info(terminal.getName() + "(" + IP + ")" + ":连接上来了");
				flag = true;
			}
		
		}
		if (!flag) {
			logger.info("未知的设备(" + IP + "):连接上来了");
			// session.close(false);
		}
		super.sessionOpened(nextFilter, session);
	}

	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		session.close(false);
	}
	
}
