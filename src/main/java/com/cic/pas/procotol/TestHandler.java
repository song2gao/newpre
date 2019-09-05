/**
 * 
 */
package com.cic.pas.procotol;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * @author Administrator 终端和server进行通讯过程的处理
 */
public class TestHandler extends IoHandlerAdapter {

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		session.close(false);
	}


	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		System.out.println(message);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
//		String hexStr = CRC16M.getBufHexStr((byte[]) message);
//		logger.info("发送消息:" + hexStr);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
	}
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println(session);
		// logger.info("服务端与客户端创建连接...");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
	}

}
