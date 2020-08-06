package com.cic.pas.thread;

import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.service.ConnectorSocketFactory;
import org.apache.log4j.Logger;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

public class ClientConnectThread extends BaseThread {
    NioSocketConnector socketConnector;
    String address;
    String protocol;
    String address2;
    IoSession session;
    int port;
    int port2;
    TerminalDevice td;
    String currentAddr;
    int currentPort;
    private Logger logger = Logger.getLogger(ClientConnectThread.class);

    public ClientConnectThread(String address, int port, String protocol, TerminalDevice td) {
        this.td = td;
        this.address = address;
        this.port = port;
        this.protocol = protocol;
        currentAddr=address;
        currentPort=port;
        socketConnector = ConnectorSocketFactory.getSocketConnector(address, port, protocol, td.getNoticeManner());
    }

    public ClientConnectThread(String address, int port, String address2, int port2, String protocol, TerminalDevice td) {
        this.address = address;
        this.port = port;
        this.address2 = address2;
        this.port2 = port2;
        this.td = td;
        socketConnector = ConnectorSocketFactory.getSocketConnector(address, port, protocol, td.getNoticeManner());
    }

    @Override
    public void run() {
        boolean currentBackup = false;
        String backupIp = td.getMsaBackUp();
        String adressBackUp = null;
        int portBackUp = 0;
        if (backupIp != null && !backupIp.equals("")) {
            int index = backupIp.indexOf(":");
            adressBackUp = backupIp.substring(0, index);
            portBackUp = Integer.parseInt(backupIp.substring(index + 1));
        }
        while (!exit) {
            // 添加处理器
            try {
                if(adressBackUp!=null) {
                    if (currentBackup) {
                        currentAddr=address;
                        currentPort=port;
                        currentBackup = false;
                    } else {
                        currentAddr=adressBackUp;
                        currentPort=portBackUp;
                        currentBackup = true;
                    }
                    socketConnector = ConnectorSocketFactory.getSocketConnector(currentAddr, currentPort, protocol, td.getNoticeManner());
                }
                ConnectFuture future = socketConnector
                        .connect(new InetSocketAddress(currentAddr,currentPort));
                socketConnector.setConnectTimeoutMillis(3000);
                // 等待连接创建成功
                future.awaitUninterruptibly();
                // 获取会话
                session = future.getSession();
                logger.info("连接服务端[" + (td == null ? "" : td.getName()) + "("
                        + currentAddr
                        + ":"
                        + currentPort
                        + ")]成功");
                session.setAttribute("terminal_id", td.getCode());
                if (address2 != null) {
                    session.setAttribute("ip1", address);
                    session.setAttribute("port1", port);
                    session.setAttribute("ip2", address2);
                    session.setAttribute("port2", port2);
                }
                exit = true;
            } catch (RuntimeIoException e) {
                logger.info("连接服务端[" + (td == null ? "" : td.getName()) + "("
                        + currentAddr
                        + ":"
                        + currentPort
                        + ")]失败，一分钟后再次发起连接");
                try {
                    Thread.sleep(60000);
                } catch (Exception ex) {
                    logger.info("断线重连等待失败，直接再次发起连接");
                }

            }
        }

    }
}
