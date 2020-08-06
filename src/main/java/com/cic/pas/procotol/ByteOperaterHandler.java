/**
 *
 */
package com.cic.pas.procotol;

import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.common.net.ReturnMessage;
import com.cic.pas.common.util.CRC16M;
import com.cic.pas.common.util.Util;
import com.cic.pas.dao.BussinessConfig;
import com.cic.pas.process.Processer;
import com.cic.pas.process.ProcesserFactory;
import com.cic.pas.service.ConnectorSocketFactory;
import com.cic.pas.service.ServerContext;
import com.cic.pas.thread.BaseThread;
import com.cic.pas.thread.GetDataThread;
import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator 终端和server进行通讯过程的处理
 */
public class ByteOperaterHandler extends IoHandlerAdapter {
    public static byte PFC = 0;
    private static final Logger logger = Logger.getLogger(ByteOperaterHandler.class);
    private static Map<String, Integer> map = new HashMap<String, Integer>();


    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        cause.printStackTrace(new PrintStream(baos));
        String exception = baos.toString();
        logger.error(exception);
//        sessionClosed(session);
    }

    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {

    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println(message);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {

    }

    @Override
    public void sessionCreated(IoSession session) {
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) {
        logger.info("-客户端与服务端连接[空闲] - " + status.toString());
        if (session != null) {
            session.closeNow();
        }
    }

    @Override
    public void sessionOpened(IoSession session) {
        logger.info(session.getRemoteAddress() + "连接上来了");
        try {
            byte[] bytes = CRC16M.HexString2Buf("ac ed 00 05".replaceAll(" ",""));
//            session.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
