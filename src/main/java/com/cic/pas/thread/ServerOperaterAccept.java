package com.cic.pas.thread;

import com.cic.pas.service.ServerSocketFactory;
import org.apache.log4j.Logger;
import org.apache.mina.transport.socket.SocketAcceptor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class ServerOperaterAccept extends BaseThread {
    private Logger logger = Logger.getLogger(ServerOperaterAccept.class);
    private int port;

    public ServerOperaterAccept(int port) {
        this.port = port;
    }

    public void run() {
        if (port != 0) {
            ServerSocketFactory serverSocketFactory = new ServerSocketFactory();
            //1、创建服务端口监听
            SocketAcceptor channel = serverSocketFactory.createOperaterServerSocket(port);
            try {
                channel.bind();
            } catch (IOException e) {
                channel.unbind();
                channel.dispose();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                e.printStackTrace(new PrintStream(baos));
                String exception = baos.toString();
                logger.error(exception);
            }
        }
    }
}
