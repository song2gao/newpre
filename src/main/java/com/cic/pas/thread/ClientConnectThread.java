package com.cic.pas.thread;

import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.common.util.ClassUtils;
import com.cic.pas.dao.BussinessConfig;
import com.cic.pas.procotol.ByteHandler;
import com.cic.pas.service.ConnectorContext;
import com.cic.pas.service.ServerContext;
import org.apache.log4j.Logger;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientConnectThread extends BaseThread {
    SocketConnector socketConnector;
    String address;
    int port;
    String protocol;
    private Logger logger = Logger.getLogger(ClientConnectThread.class);
    public ClientConnectThread(String address,int port,String protocol) {
        this.socketConnector = new NioSocketConnector();
        this.address=address;
        this.port=port;
        this.protocol=protocol;
        socketConnector = new NioSocketConnector();
        socketConnector.setConnectTimeoutMillis(30000); // 设置连接超时
        // 断线重连回调拦截器
        socketConnector.getFilterChain().addFirst("reconnection",
                new IoFilterAdapter() {
                    @Override
                    public void sessionClosed(NextFilter nextFilter,
                                              IoSession session) throws Exception {
                        TerminalDevice td=BussinessConfig.getTerminalByCode(session.getAttribute("terminal_id").toString());
                        td.setIsOnline(0);
                        BaseThread thread=ServerContext.threadMap.get(td.getCode());
                        thread.exit=true;
                        ServerContext.removeThread(td.getCode());
                        logger.info(td.getName()+ "断开连接!");
                        while (!session.isConnected()) {
                            try {
                                Thread.sleep(6000);
                                ConnectFuture future = socketConnector
                                        .connect();
                                future.awaitUninterruptibly();// 等待连接创建成功
                                session = future.getSession();// 获取会话
                                if (session.isConnected()) {
                                    logger.info("断线重连["
                                            + socketConnector
                                            .getDefaultRemoteAddress()
                                            .getHostName()
                                            + ":"
                                            + socketConnector
                                            .getDefaultRemoteAddress()
                                            .getPort() + "]成功");
                                    break;
                                }
                            } catch (Exception ex) {
                                logger.info("重连服务器"+socketConnector.getDefaultRemoteAddress()+"失败,1分钟后再连接一次:"
                                        + ex.getMessage());
                            }
                        }
                    }
                });
        try {
            socketConnector.getFilterChain().addLast(
                    "codec",
                    new ProtocolCodecFilter((ProtocolEncoder) ClassUtils
                            .getClass("com.cic.pas.procotol."+protocol+"Encoder")
                            .newInstance(), (ProtocolDecoder) ClassUtils
                            .getClass("com.cic.pas.procotol."+protocol+"Decoder")
                            .newInstance()));
        } catch (Exception e) {
            // TODO IllegalAccessException InstantiationException 这里需要处理两个异常
            // 并进行异常发生后的操作
            e.printStackTrace();
        }
        socketConnector.getSessionConfig().setIdleTime(IdleStatus.READER_IDLE, 60);
        socketConnector.setHandler(new ByteHandler());
        socketConnector.setDefaultRemoteAddress(new InetSocketAddress(
                address, port));
    }

    @Override
    public void run() {
        while (!exit) {
            // 添加处理器
            try {
                ConnectFuture future = socketConnector.connect();
                // 等待连接创建成功
                future.awaitUninterruptibly();
                // 获取会话
                IoSession session = future.getSession();
//                logger.info("连接服务端"
//                        + address
//                        + ":"
//                        + port
//                        + "[成功]"
//                        + ",,时间:"
//                        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//                        .format(new Date()));
                exit=true;
            } catch (RuntimeIoException e) {
                logger.error("连接服务端"
                                + address
                                + ":"
                                + port
                                + "失败"
                                + ",,时间:"
                                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .format(new Date())
                                + ")");
//                logger.error("连接服务端"
//                        + address
//                        + ":"
//                        + port
//                        + "失败"
//                        + ",,时间:"
//                        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//                        .format(new Date())
//                        + ", 连接MSG异常,请检查MSG端口、IP是否正确,MSG服务是否启动,异常内容:"
//                        + e.getMessage(), e);
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }// 连接失败后,重连10次,间隔30s

            }
        }

    }
}
