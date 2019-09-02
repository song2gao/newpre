package com.cic.pas.thread;

import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.common.util.ClassUtils;
import com.cic.pas.dao.BussinessConfig;
import com.cic.pas.procotol.ByteHandler;
import com.cic.pas.service.ConnectorContext;
import com.cic.pas.service.ConnectorSocketFactory;
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
    NioSocketConnector socketConnector;
    String address;
    IoSession session;
    int port;
    TerminalDevice td;
    private Logger logger = Logger.getLogger(ClientConnectThread.class);

    public ClientConnectThread(String address, int port, String protocol) {
        this.address=address;
        this.port=port;
       socketConnector=ConnectorSocketFactory.getSocketConnector(address,port,protocol);
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
                session = future.getSession();
                logger.info("连接服务端[" + (td == null ? "" : td.getName()) + "("
                        + address
                        + ":"
                        + port
                        + ")]成功");
                exit = true;
            } catch (RuntimeIoException e) {
                logger.info("连接服务端[" + (td == null ? "" : td.getName()) + "("
                        + address
                        + ":"
                        + port
                        + ")]失败，一分钟后再次发起连接");
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
