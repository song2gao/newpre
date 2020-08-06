package com.cic.pas.service;

import java.net.InetSocketAddress;

import com.cic.pas.procotol.Client2ClientHandler;
import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.cic.pas.common.util.ClassUtils;
import com.cic.pas.procotol.ByteHandler;

public class ConnectorSocketFactory {

    public static final Logger logger = Logger
            .getLogger(ServerSocketFactory.class);


    /**
     * 创建客户端连接
     *
     * @param
     * @param protocol
     * @throws Exception
     * @throws Exception
     * @throws InstantiationException
     */

    public SocketConnector createClientSocket(String address,int port,
                                              String protocol) {
        NioSocketConnector socketConnector = new NioSocketConnector();
        socketConnector.setConnectTimeoutMillis(30000); // 设置连接超时
        // 断线重连回调拦截器
        socketConnector.getFilterChain().addFirst("reconnection",
                new IoFilterAdapter() {
                    @Override
                    public void sessionClosed(NextFilter nextFilter,
                                              IoSession session) {
                        for (; ; ) {
                            try {
                                Thread.sleep(3000);
                                ConnectFuture future = socketConnector
                                        .connect(new InetSocketAddress(address,port));
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
                                    // ConnectorContext.socketConnectorlist.put(addressport,
                                    // value)
//                                    ConnectorContext.clientMap.put(address,
//                                            session);
                                    break;
                                }
                            } catch (Exception ex) {
                                logger.info("重连服务器[" + socketConnector
                                        .getDefaultRemoteAddress()
                                        .getHostName()
                                        + ":"
                                        + socketConnector
                                        .getDefaultRemoteAddress()
                                        .getPort() + "]失败,3秒再连接一次:"
                                        + ex.getMessage());
                            }
                        }
                    }
                });
        try {
            socketConnector.getFilterChain().addLast(
                    "codec",
                    new ProtocolCodecFilter((ProtocolEncoder) ClassUtils
                            .getClass("com.cic.pas.procotol." + protocol + "Encoder")
                            .newInstance(), (ProtocolDecoder) ClassUtils
                            .getClass("com.cic.pas.procotol." + protocol + "Decoder")
                            .newInstance()));
        } catch (Exception e) {
            // TODO IllegalAccessException InstantiationException 这里需要处理两个异常
            // 并进行异常发生后的操作
            e.printStackTrace();
        }
        socketConnector.getSessionConfig().setIdleTime(IdleStatus.READER_IDLE, 60);
        socketConnector.getSessionConfig().setIdleTime(IdleStatus.WRITER_IDLE, 60);
        socketConnector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
        socketConnector.setHandler(new ByteHandler());
        socketConnector.setDefaultRemoteAddress(new InetSocketAddress(
                address, port));
        // 设置链接超时时间
        socketConnector.setConnectTimeoutCheckInterval(30);
        // 连接服务器，知道端口、地址
        ConnectFuture cf = socketConnector.connect(new InetSocketAddress(address,port));
        // 等待连接创建完成
        cf.awaitUninterruptibly();
//        socketConnector.dispose();
        return socketConnector;
    }
    public static NioSocketConnector getSocketConnector(String address,int port,String protocol,String type){
        NioSocketConnector socketConnector = new NioSocketConnector();
        socketConnector.setConnectTimeoutMillis(1000); // 设置连接超时
        try {
            socketConnector.getFilterChain().addLast(
                    "codec",
                    new ProtocolCodecFilter((ProtocolEncoder) ClassUtils
                            .getClass("com.cic.pas.procotol." + protocol + "Encoder")
                            .newInstance(), (ProtocolDecoder) ClassUtils
                            .getClass("com.cic.pas.procotol." + protocol + "Decoder")
                            .newInstance()));
        } catch (Exception e) {
            // TODO IllegalAccessException InstantiationException 这里需要处理两个异常
            // 并进行异常发生后的操作
            e.printStackTrace();
        }
        socketConnector.getSessionConfig().setIdleTime(IdleStatus.READER_IDLE, 60);
        switch (type){
            case "2":
            case "3":
            case "4":
                socketConnector.setHandler(new ByteHandler());
                break;
            case "5":
                socketConnector.setHandler(new Client2ClientHandler());
                break;
        }
        socketConnector.setDefaultRemoteAddress(new InetSocketAddress(
                address, port));
        return socketConnector;
    }

}
