package com.cic.pas.thread;

import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.service.ConnectorSocketFactory;
import org.apache.log4j.Logger;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
public class ClientConnectThread extends BaseThread {
    NioSocketConnector socketConnector;
    String address;
    String address2;
    IoSession session;
    int port;
    int port2;
    TerminalDevice td;
    private Logger logger = Logger.getLogger(ClientConnectThread.class);

    public ClientConnectThread(String address, int port, String protocol,TerminalDevice td) {
        this.td=td;
        this.address=address;
        this.port=port;
       socketConnector=ConnectorSocketFactory.getSocketConnector(address,port,protocol,td.getNoticeManner());
    }
    public ClientConnectThread(String address, int port,String address2,int port2, String protocol,TerminalDevice td) {
        this.address=address;
        this.port=port;
        this.address2=address2;
        this.port2=port2;
        this.td=td;
        socketConnector=ConnectorSocketFactory.getSocketConnector(address,port,protocol,td.getNoticeManner());
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
                session.setAttribute("terminal_id", td.getCode());
                if(address2!=null){
                    session.setAttribute("ip1",address);
                    session.setAttribute("port1",port);
                    session.setAttribute("ip2",address2);
                    session.setAttribute("port2",port2);
                }
                exit = true;
            } catch (RuntimeIoException e) {
                logger.info("连接服务端[" + (td == null ? "" : td.getName()) + "("
                        + address
                        + ":"
                        + port
                        + ")]失败，一分钟后再次发起连接");
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }// 连接失败后,重连10次,间隔30s

            }
        }

    }
}
