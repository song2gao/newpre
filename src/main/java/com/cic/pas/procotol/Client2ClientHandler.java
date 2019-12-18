package com.cic.pas.procotol;

import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.dao.BussinessConfig;
import com.cic.pas.service.ConnectorSocketFactory;
import com.cic.pas.service.ServerContext;
import com.cic.pas.thread.ClientConnectThread;
import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.util.*;

/**
 * @author Administrator 终端和server进行通讯过程的处理
 */
public class  Client2ClientHandler extends IoHandlerAdapter {
    public static byte PFC = 0;
    private static final Logger logger = Logger.getLogger(ByteHandler.class);
    private static Map<String, Integer> map = new HashMap<String, Integer>();

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        cause.printStackTrace();
//        sessionClosed(session);
    }

    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        System.out.println(message);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println(message);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        try {
            logger.info(session.getAttribute("terminal_name") + "断开连接!");
            String terminal_id = session.getAttribute("terminal_id").toString();
            TerminalDevice td = BussinessConfig.getTerminalByCode(terminal_id);
            td.setIsOnline(0);
            Map<Long, IoSession> sessions = ServerContext.client2clientMap.get(terminal_id);
            NioSocketConnector socketConnector = (NioSocketConnector) session.getService();
            socketConnector.dispose();
            String clientFlag = session.getAttribute("clientFlag").toString();
            switch (clientFlag) {
                case "1":
                    for (long key : sessions.keySet()) {
                        IoSession s = sessions.get(key);
                        if (s.getAttribute("clientFlag").toString().equals("2")) {
                            s.closeNow();
                            break;
                        }
                    }
                    String ip1 = session.getAttribute("ip1").toString();
                    int port1 = Integer.parseInt(session.getAttribute("port1").toString());
                    String ip2 = session.getAttribute("ip2").toString();
                    int port2 = Integer.parseInt(session.getAttribute("port2").toString());
                    reConnection(td, ip1, port1, ip2, port2);
                    break;
                case "2":
                    for (long key : sessions.keySet()) {
                        IoSession s = sessions.get(key);
                        if (s.getAttribute("clientFlag").toString().equals("1")) {
                            ip2 = s.getAttribute("ip2").toString();
                            port2 = Integer.parseInt(s.getAttribute("port2").toString());
                            if (s.isConnected()) {
                                reConnection(td, ip2, port2, null, 0);
                            } else {
                                s.closeNow();
                            }
                            break;
                        }
                    }
            }

        } catch (
                Exception e)

        {
            e.printStackTrace();
        }

    }

    private void reConnection(TerminalDevice td, String ip1, int port1, String ip2, int port2) {
        NioSocketConnector socketConnector;
        IoSession session;
        socketConnector = ConnectorSocketFactory.getSocketConnector(ip1, port1, td.getProcotolCode(), td.getNoticeManner());
        for (; ; ) {
            try {
                Thread.sleep(6000);
                ConnectFuture future = socketConnector
                        .connect();
                future.awaitUninterruptibly();// 等待连接创建成功
                session = future.getSession();// 获取会话
                if (session.isConnected()) {
                    if(ip2!=null){
                        session.setAttribute("ip1",ip1);
                        session.setAttribute("port1",port1);
                        session.setAttribute("ip2",ip2);
                        session.setAttribute("port2",port2);
                    }
                    String message = "断线重连[" + (td == null ? "" : td.getName()) + "("
                            + socketConnector
                            .getDefaultRemoteAddress()
                            .getHostName()
                            + ":"
                            + socketConnector
                            .getDefaultRemoteAddress()
                            .getPort() + ")]成功";
                    logger.info(message);
                    break;
                } else {
                    System.out.println(td == null ? "" : td.getName() + "未成功建立连接");
                }
            } catch (Exception ex) {
                logger.info("断线重连[" + (td == null ? "" : td.getName()) + "("
                        + socketConnector
                        .getDefaultRemoteAddress()
                        .getHostName()
                        + ":"
                        + socketConnector
                        .getDefaultRemoteAddress()
                        .getPort() + ")]失败，一分钟后再次发起连接");
            }
        }
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sessionOpened");
        Object obj = session.getAttribute("ip1");
        String terminal_id=session.getAttribute("terminal_id").toString();
        TerminalDevice td = BussinessConfig.getTerminalByCode(terminal_id);
        if (obj==null) {// client2 连接成功
            if (td != null) {
                session.setAttribute("terminal_name", td.getName());
                session.setAttribute("terminal", td);
                session.setAttribute("clientFlag", 2);
                td.setIsOnline(1);
                Map<Long, IoSession> sessions = ServerContext.client2clientMap.get(td.getCode());
                sessions.put(session.getId(), session);
                ServerContext.client2clientMap.put(td.getCode(), sessions);
            } else {
                session.closeNow();
                System.out.println("未找到采集器" + BussinessConfig.getRemoteIp(session));
            }
        } else {// client1连接成功时去发起client2连接
            if (td != null) {
                String ip2 = session.getAttribute("ip2").toString();
                int port2 = Integer.parseInt(session.getAttribute("port2").toString());
                session.setAttribute("clientFlag", 1);
                Map<Long, IoSession> sessions = new LinkedHashMap<>();
                sessions.put(session.getId(), session);
                ServerContext.client2clientMap.put(td.getCode(), sessions);
                ClientConnectThread thread = new ClientConnectThread(ip2,port2,td.getProcotolCode(),td);
                thread.start();
            } else {
                session.closeNow();
                System.out.println("未找到采集器" + BussinessConfig.getRemoteIp(session));
            }
        }
    }

    public TerminalDevice getTerminalBySession(String ipPort1, String ipPort2) {
        TerminalDevice td;
        String msa = ipPort1 + "," + ipPort2;
        td = BussinessConfig.getTerminalByMSA(msa);
        return td;
    }


}
