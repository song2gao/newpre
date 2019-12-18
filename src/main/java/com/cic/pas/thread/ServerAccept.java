package com.cic.pas.thread;

import java.io.IOException;

import com.cic.pas.service.ServerSocketFactory;
import org.apache.log4j.Logger;

import com.cic.pas.common.bean.TerminalDevice;
import org.apache.mina.transport.socket.SocketAcceptor;

public class ServerAccept extends BaseThread {
    private Logger logger = Logger.getLogger(ServerAccept.class);
    private String procotolType;
    private int port;
    public ServerAccept(int port, String procotolType) {
        this.procotolType = procotolType;
        this.port=port;
    }

    public void run() {
        if (port!=0) {
            ServerSocketFactory serverSocketFactory = new ServerSocketFactory();
            //1、创建服务端口监听
            SocketAcceptor channel = serverSocketFactory.createServerSocket(port, procotolType);
            try {
                channel.bind();
            } catch (IOException e) {
                channel.unbind();
                channel.dispose();
                e.printStackTrace();
            }
        }
    }
}
