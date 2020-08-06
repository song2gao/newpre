/**
 *
 */
package com.cic.pas.procotol;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.cic.pas.service.ConnectorSocketFactory;
import com.cic.pas.thread.BaseThread;
import com.cic.pas.thread.GetDataThread;
import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.dao.BussinessConfig;
import com.cic.pas.service.ServerContext;

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
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        cause.printStackTrace(new PrintStream(baos));
        String exception = baos.toString();
        logger.error(exception);
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
            Object obj = session.getAttribute("terminal_id");
            if (obj != null) {
                logger.info(session.getAttribute("terminal_name") + "断开连接!");
                String terminal_id = obj.toString();
                ServerContext.remove(session.getId());
                TerminalDevice td = BussinessConfig.getTerminalByCode(terminal_id);
                td.setIsOnline(0);
                BaseThread thread = ServerContext.threadMap.get(terminal_id);
                if (thread != null) {
                    thread.exit = true;
                    ServerContext.removeThread(terminal_id);
                    logger.info(thread.getName() + "数据采集线程关闭");
                } else {
                    logger.info(session.getAttribute("terminal_name") + "数据采集线程未启动");
                }
                if (session.getService() instanceof NioSocketConnector) {
                    NioSocketConnector socketConnector = (NioSocketConnector) session.getService();
                    socketConnector.dispose();
                    String ipPort = td.getMSA();
                    int index = ipPort.indexOf(":");
                    String ip = ipPort.substring(0, index);
                    int port = Integer.parseInt(ipPort.substring(index + 1));
                    socketConnector = ConnectorSocketFactory.getSocketConnector(ip, port, td.getProcotolCode(), td.getNoticeManner());
                    boolean currentBackup = false;
                    String backupIp = td.getMsaBackUp();
                    String adressBackUp = null;
                    int portBackUp = 0;
                    if (backupIp != null && !backupIp.equals("")) {
                        int indexBackUp = backupIp.indexOf(":");
                        adressBackUp = backupIp.substring(0, indexBackUp);
                        portBackUp = Integer.parseInt(backupIp.substring(indexBackUp + 1));
                    }
                    for (; ; ) {
                        try {
                            String toReconnectIp=ip;
                            int toReconnectPort=port;
                            if (adressBackUp != null) {
                                if (currentBackup) {
                                    socketConnector = ConnectorSocketFactory.getSocketConnector(ip, port, td.getProcotolCode(), td.getNoticeManner());
                                    currentBackup = false;
                                    toReconnectIp=ip;
                                    toReconnectPort=port;
                                } else {
                                    socketConnector = ConnectorSocketFactory.getSocketConnector(adressBackUp, portBackUp, td.getProcotolCode(), td.getNoticeManner());
                                    currentBackup = true;
                                    toReconnectIp=adressBackUp;
                                    toReconnectPort=portBackUp;
                                }
                            }
                            ConnectFuture future = socketConnector
                                    .connect(new InetSocketAddress(toReconnectIp,toReconnectPort));
                            future.awaitUninterruptibly();// 等待连接创建成功
                            session = future.getSession();// 获取会话
                            if (session.isConnected()) {
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
                            try {
                                Thread.sleep(60000);
                            } catch (Exception e) {
                                logger.info("断线重连等待失败，直接再次发起连接");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(baos));
            String exception = baos.toString();
            logger.error(exception);
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
        TerminalDevice td = getTerminalBySession(session);
        if (td != null) {
            if (td.getNoticeAccord().equals("Byte3761")) {

            } else {
                ServerContext.addSession(session);
                td.setIsOnline(1);
                session.setAttribute("terminal_id", td.getCode());
                session.setAttribute("terminal_name", td.getName());
                session.setAttribute("terminal", td);
                if (ServerContext.threadMap.containsKey(td.getCode())) {
                    GetDataThread old = (GetDataThread) ServerContext.threadMap.get(td.getCode());
                    if (old != null) {
                        old.exit = true;
                    }
                    ServerContext.threadMap.remove(td.getCode());
                }
                GetDataThread thread = new GetDataThread(td, session);
                thread.setName(td.getName() + "[" + td.getCode() + "]");
                thread.start();
                ServerContext.addThread(td.getCode(), thread);
            }
        } else {
            logger.info("未找到采集器,关闭连接");
            session.closeNow();
        }
    }

    public TerminalDevice getTerminalBySession(IoSession session) {
        TerminalDevice td = null;
        if (session.getService() instanceof NioSocketConnector) {
            td = BussinessConfig.getTerminalByMSA(BussinessConfig.getRemoteIpPort(session));
            logger.info("采集器[" + td.getName() + "(连接服务端" + session.getRemoteAddress() + ")]成功");
        } else {
            String remoteIp = BussinessConfig.getRemoteIp(session);
            String port = BussinessConfig.getLoclPort(session);
            td = BussinessConfig.getTerminalByIpOrPort(remoteIp, port);
            if (td == null) {
                logger.info("未知的设备(" + session.getRemoteAddress() + "):连接上来了");
            } else {
                logger.info("采集器[" + td.getName() + "("
                        + session.getRemoteAddress() + ")" + "]连接上来了");
            }
        }
        return td;
    }

}
