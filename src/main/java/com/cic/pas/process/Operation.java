package com.cic.pas.process;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cic.pas.service.ServerContext;
import com.cic.pas.thread.OperaterThread;
import com.cic.pas.thread.ServerOperaterAccept;

public class Operation {

    public void publishServe() throws Exception {
        Thread.sleep(10000);
        final ExecutorService executorService = Executors
                .newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 10);
        final ServerSocket serverSocket = new ServerSocket(ServerContext
                .getOpePort(), 500);
        System.out.println("Operation_TCP开始服务，监听端口:"
                + ServerContext.getOpePort() + "\t[" + new Date() + "]");
        Thread startOPerServer = new Thread() {
            public void run() {
                try {
                    while (true) {
                        Socket socket = serverSocket.accept();
                        OperationConnection connection = new OperationConnection(
                                socket);
                        Thread thread = new OperaterThread(connection);
						thread.setName(connection.getSocket().getInetAddress()
								.toString());
//						executorService.execute(thread);
                        thread.start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        startOPerServer.start();
    }
    public void publishServer()throws  Exception{
        ServerOperaterAccept accept = new ServerOperaterAccept(ServerContext
                .getOpePort());
        accept.setName("Operation_TCP");
        accept.start();
        System.out.println("Operation_TCP开始服务，监听端口:"
                + ServerContext.getOpePort() + "\t[" + new Date() + "]");
    }
    public void publishClient() throws Exception {
        Thread.sleep(10000);
        final ExecutorService executorService = Executors
                .newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 10);
        System.out.println("Operation_TCP开始服务，连接服务器:"
                + ServerContext.getOpeServerIp()+":"+ServerContext.getOpeServerPort() + "\t[" + new Date() + "]");
        OperationConnection connection = new OperationConnection(
                ServerContext.getOpeServerIp(),ServerContext.getOpeServerPort());
        Thread thread = new OperaterThread(connection);
        thread.start();

    }
}