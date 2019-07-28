/**
 *
 */
package com.cic.pas.procotol;

import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cic.pas.thread.BaseThread;
import com.cic.pas.thread.GetDataThread;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import org.apache.mina.transport.socket.nio.NioSocketConnector;
import sun.reflect.generics.tree.Tree;

import com.cic.pas.application.DBVisitService;
import com.cic.pas.common.bean.Channel;
import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.PointDevice;
import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.common.util.CRC16M;
import com.cic.pas.common.util.Util;
import com.cic.pas.dao.BussinessConfig;
import com.cic.pas.service.ConnectorContext;
import com.cic.pas.service.ServerContext;
import com.cic.pas.thread.ClientThread;

/**
 * @author Administrator 终端和server进行通讯过程的处理
 */
public class ByteHandler extends IoHandlerAdapter {
    public static byte PFC = 0;
    private static final Logger logger = Logger.getLogger(ByteHandler.class);
    private static Map<String, Integer> map = new HashMap<String, Integer>();

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        session.close(false);
    }

    boolean flag = true;

    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        try {
            logger.info(session.getAttribute("terminal_name") + "断开连接!");
            String terminal_id=session.getAttribute("terminal_id").toString();
            ServerContext.remove(session.getId());
            TerminalDevice td=BussinessConfig.getTerminalByCode(terminal_id);
            td.setIsOnline(0);
            BaseThread thread=ServerContext.threadMap.get(terminal_id);
            thread.exit=true;
            ServerContext.removeThread(terminal_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPort(IoSession session) {
        SocketAddress client = session.getLocalAddress();
        String IP = client.toString().substring(
                client.toString().indexOf(":") + 1);
        return IP;
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status)
            throws Exception {
        logger.info("-客户端与服务端连接[空闲] - " + status.toString());
        if (session != null) {
            session.close(false);
        }
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        TerminalDevice td = getTerminalBySession(session);
        if(td!=null){
            GetDataThread thread=new GetDataThread(td,session);
            session.setAttribute("terminal_id",td.getCode());
            session.setAttribute("terminal_name",td.getName());
            session.setAttribute("terminal",td);
            thread.start();
            ServerContext.addSession(session);
            td.setIsOnline(1);
            ServerContext.addThread(td.getCode(),thread);
        }
    }

    public static String getRemoteIpPort(IoSession session) {
        String ipPort = session.getRemoteAddress().toString();
        return ipPort.substring(ipPort.indexOf("/")+1);
    }

    public static String getLoclPort(IoSession session) {
        String ipPort = session.getLocalAddress().toString();
        int index = ipPort.indexOf(":");
        return ipPort.substring(index + 1);
    }

    public static String getRemoteIp(IoSession session) {
        String ipPort = session.getRemoteAddress().toString();
        int index = ipPort.indexOf(":");
        return ipPort.substring(1, index);
    }
    public TerminalDevice getTerminalBySession(IoSession session){
        TerminalDevice td = null;
        if (session.getService() instanceof NioSocketConnector) {
            td = BussinessConfig.getTerminalByMSA(getRemoteIpPort(session));
            logger.info("采集器" + td.getName() + "(连接服务端" + session.getRemoteAddress() + "):成功");
        } else {
            String remoteIp = getRemoteIp(session);
            String port = getLoclPort(session);
            td = BussinessConfig.getTerminalByIpOrPort(remoteIp, port);
            if (td == null) {
                logger.info("未知的设备(" + session.getRemoteAddress() + "):连接上来了");
            } else {
                logger.info(td.getName() + "("
                        + session.getRemoteAddress() + ")" + ":连接上来了");
            }
        }
        return td;
    }

}
