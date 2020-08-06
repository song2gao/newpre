package com.cic.pas.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.cic.pas.procotol.ByteOperaterDecoder;
import com.cic.pas.procotol.ByteOperaterEncoder;
import com.cic.pas.procotol.ByteOperaterHandler;
import com.cic.pas.thread.BaseThread;
import com.cic.pas.thread.ClientConnectThread;
import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import com.cic.pas.common.util.ClassUtils;
import com.cic.pas.procotol.ByteHandler;

/**
 * serverSocket创建工厂
 * <p>
 * 项目名称：preAcquistionSystem.3.2 类名称：serverSocketFactory 类描述： 创建人：lenovo
 * 创建时间：2014-3-19 下午02:49:24 修改人：lenovo 修改时间：2014-3-19 下午02:49:24 修改备注：
 */
public class ServerSocketFactory {

    public static final Logger logger = Logger
            .getLogger(ServerSocketFactory.class);
    SocketConnector socketConnector;
    public static IoSession session;


    /**
     * 通道创建
     *
     * @param
     * @param protocol
     * @throws Exception
     * @throws InstantiationException
     */

    public SocketAcceptor createServerSocket(int port, String protocol) {
        SocketAcceptor socketAcceptor = new NioSocketAcceptor();
        try {
            socketAcceptor.getFilterChain().addLast(
                    "codec",
                    //new ProtocolCodecFilter( new PrefixedStringCodecFactory(Charset.forName("UTF-8"))));
                    new ProtocolCodecFilter((ProtocolEncoder) ClassUtils
                            .getClass("com.cic.pas.procotol." + protocol + "Encoder").newInstance(),
                            (ProtocolDecoder) ClassUtils.getClass("com.cic.pas.procotol." + protocol + "Decoder")
                                    .newInstance()));
        } catch (Exception e) {
            // TODO IllegalAccessException InstantiationException 这里需要处理两个异常 并进行异常发生后的操作
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(baos));
            String exception = baos.toString();
            logger.error(exception);
        }
        socketAcceptor.getFilterChain().addLast("threadPool",
                new ExecutorFilter(Executors.newCachedThreadPool()));
        socketAcceptor.setHandler(new ByteHandler());
        socketAcceptor.getSessionConfig().setReadBufferSize(16383 + 8);// Buffer大小告诉底层操作系统应该分配多大空间放置到来的数据
        socketAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 60 * 5);
        //TODO 端口号从address中截取的处理
        socketAcceptor.setDefaultLocalAddress(new InetSocketAddress(Integer
                .valueOf(port)));
        // 将端口监听服务放入map进行管理
        ServerContext.serverSocketList
                .put(String.valueOf(port), socketAcceptor);
        socketAcceptor.getSessionConfig().setSoLinger(0);
        socketAcceptor.getSessionConfig().setReuseAddress(true);
        logger.info("创建通道,端口:" + port);
        return socketAcceptor;
    }
    /**
     * 通道创建
     *
     * @param
     * @param
     * @throws Exception
     * @throws InstantiationException
     */

    public SocketAcceptor createOperaterServerSocket(int port) {
        SocketAcceptor socketAcceptor = new NioSocketAcceptor();
        try {
            socketAcceptor.getFilterChain().addLast(
                    "codec",
                    //new ProtocolCodecFilter( new PrefixedStringCodecFactory(Charset.forName("UTF-8"))));
                    new ProtocolCodecFilter(ByteOperaterEncoder.class,
                            ByteOperaterDecoder.class));
        } catch (Exception e) {
            // TODO IllegalAccessException InstantiationException 这里需要处理两个异常 并进行异常发生后的操作
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(baos));
            String exception = baos.toString();
            logger.error(exception);
        }
        socketAcceptor.getFilterChain().addLast("threadPool",
                new ExecutorFilter(Executors.newCachedThreadPool()));
        socketAcceptor.setHandler(new ByteOperaterHandler());
        socketAcceptor.getSessionConfig().setReadBufferSize(16383 + 8);// Buffer大小告诉底层操作系统应该分配多大空间放置到来的数据
        socketAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 60 * 5);
        //TODO 端口号从address中截取的处理
        socketAcceptor.setDefaultLocalAddress(new InetSocketAddress(Integer
                .valueOf(port)));
        // 将端口监听服务放入map进行管理
        ServerContext.serverSocketList
                .put(String.valueOf(port), socketAcceptor);
        socketAcceptor.getSessionConfig().setSoLinger(0);
        socketAcceptor.getSessionConfig().setReuseAddress(true);
        logger.info("创建通道,端口:" + port);
        return socketAcceptor;
    }
}
