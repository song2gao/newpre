package com.cic.pas.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.cic.pas.service.ServerSocketFactory;
import org.apache.log4j.Logger;

import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.service.ConnectorContext;
import org.apache.mina.transport.socket.SocketAcceptor;

public class ServerAccept extends BaseThread {
    private Logger logger = Logger.getLogger(ServerAccept.class);
    private TerminalDevice td;
    private String procotolType;

    public ServerAccept(TerminalDevice td, String procotolType) {
        this.td = td;
        this.procotolType = procotolType;
    }

    public void run() {
        if (td.getMSA() != null && !td.getMSA().equals("")) {
            ServerSocketFactory serverSocketFactory = new ServerSocketFactory();
            //1、创建服务端口监听
            SocketAcceptor channel = serverSocketFactory.createServerSocket(td.getMSA(), procotolType);
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