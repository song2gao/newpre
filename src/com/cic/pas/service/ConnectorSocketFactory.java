package com.cic.pas.service;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cic.pas.thread.BaseThread;
import com.cic.pas.thread.ClientConnectThread;
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
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.cic.pas.common.util.ClassUtils;
import com.cic.pas.dao.BussinessConfig;
import com.cic.pas.procotol.ByteHandler;
import com.cic.pas.thread.ClientThread;

public class ConnectorSocketFactory {

	public static final Logger logger = Logger
			.getLogger(ServerSocketFactory.class);
	public volatile static SocketConnector socketConnector;
	public volatile static IoSession session;
	public volatile static String address;

	/**
	 * 创建客户端连接
	 * 
	 * @param
	 * @param protocol
	 * @throws Exception
	 * @throws Exception
	 * @throws InstantiationException
	 */

	public SocketConnector createClientSocket(String addressport,
			String protocol) {
//		int point=addressport.indexOf(":");
//		address = addressport.substring(0,point);
//		int port=Integer.parseInt(addressport.substring(point+1));
//		socketConnector = new NioSocketConnector();
//		socketConnector.setConnectTimeoutMillis(30000); // 设置连接超时
//		// 断线重连回调拦截器
//		socketConnector.getFilterChain().addFirst("reconnection",
//				new IoFilterAdapter() {
//					@Override
//					public void sessionClosed(NextFilter nextFilter,
//							IoSession session) throws Exception {
//						for (;;) {
//							try {
//								Thread.sleep(3000);
//								ConnectFuture future = socketConnector
//										.connect();
//								future.awaitUninterruptibly();// 等待连接创建成功
//								session = future.getSession();// 获取会话
//								if (session.isConnected()) {
//									logger.info("断线重连["
//											+ socketConnector
//													.getDefaultRemoteAddress()
//													.getHostName()
//											+ ":"
//											+ socketConnector
//													.getDefaultRemoteAddress()
//													.getPort() + "]成功");
//									// ConnectorContext.socketConnectorlist.put(addressport,
//									// value)
//									ConnectorContext.clientMap.put(address,
//											session);
//									break;
//								}
//							} catch (Exception ex) {
//								logger.info("重连服务器["+ socketConnector
//										.getDefaultRemoteAddress()
//										.getHostName()
//								+ ":"
//								+ socketConnector
//										.getDefaultRemoteAddress()
//										.getPort()+"]失败,3秒再连接一次:"
//										+ ex.getMessage());
//							}
//						}
//					}
//				});
//
//		// 设置过滤器
//		// socketConnector.getFilterChain().addLast("logger", new
//		// LoggingFilter());
//
//		try {
//			socketConnector.getFilterChain().addLast(
//					"codec",
//					new ProtocolCodecFilter((ProtocolEncoder) ClassUtils
//							.getClass("com.cic.pas.procotol.Byte3761Encoder")
//							.newInstance(), (ProtocolDecoder) ClassUtils
//							.getClass("com.cic.pas.procotol.ModBusTcpDecoder")
//							.newInstance()));
//			// 添加报文及数据解析的过滤器
//			// socketAcceptor.getFilterChain().addLast("messageParseFilter",(IoFilterAdapter)ClassUtils.getClass("com.cee.protocol.MessageParseFilter").newInstance());
//			// socketAcceptor.getFilterChain().addLast("messageParseFilter",new
//			// MessageParseFilter());
//		} catch (Exception e) {
//			// TODO IllegalAccessException InstantiationException 这里需要处理两个异常
//			// 并进行异常发生后的操作
//			e.printStackTrace();
//		}
//		// socketConnector.getFilterChain().addLast("threadPool",
//		// new ExecutorFilter(Executors.newCachedThreadPool()));
//		SocketSessionConfig cfg = socketConnector.getSessionConfig();
//		//cfg.setUseReadOperation(true);
//		cfg.setIdleTime(IdleStatus.BOTH_IDLE, 30000);
//		cfg.setIdleTime(IdleStatus.READER_IDLE, 120000);
//		cfg.setIdleTime(IdleStatus.WRITER_IDLE, 50000);
//		socketConnector.setHandler(new ByteHandler());
//		// socketConnector.getSessionConfig().setReadBufferSize(16383 + 8);//
//		// Buffer大小告诉底层操作系统应该分配多大空间放置到来的数据
//		// socketConnector.getSessionConfig()
//		// .setIdleTime(IdleStatus.BOTH_IDLE, 10);
//		// TODO 端口号从address中截取的处理
//		// String address = addressport.substring(0, addressport.indexOf(":"));
//		// try {
//		// //port = new DatagramSocket(0).getPort();
//		// } catch (SocketException e) {
//		// // TODO Auto-generated catch block
//		// e.printStackTrace();
//		// }
//		socketConnector.setDefaultRemoteAddress(new InetSocketAddress(
//				address, port));
//		BaseThread thread=new ClientConnectThread(socketConnector,address,port);
//		thread.start();
//		// ConnectFuture cf = socketConnector.connect(new InetSocketAddress(
//		// addressport, port));
//		// session=cf.getSession();
//		//
//		// // 将端口监听服务放入map进行管理
//		// ConnectorContext.socketConnectorlist.put(addressport,
//		// socketConnector);
//		// ConnectorContext.clientMap.put(session.getId(), session);
//
//		// logger.info("创建连接,端口:" + port);
		return socketConnector;
	}

}
