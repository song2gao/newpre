package com.cic.pas.thread;

import com.cic.pas.common.net.Connection;
import com.cic.pas.common.net.ReturnMessage;
import com.cic.pas.process.Processer;
import com.cic.pas.process.ProcesserFactory;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class OperaterThread extends Thread {
    private Connection connection;
    private Logger logger = Logger.getLogger(OperaterThread.class);

    private Processer processer;

    public OperaterThread(Connection connection) {
        this.connection = connection;
        processer = ProcesserFactory.getInstance();
        setDaemon(true);
    }

    @Override
    public void run() {
        while (connection.isConnection()) {
            try {
                Object object = connection.readObject();
                ReturnMessage response = processer.executeSome(object);
                connection.send(response);
//                Thread.currentThread().interrupt();
            } catch (Exception e) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                e.printStackTrace(new PrintStream(baos));
                String exception = baos.toString();
                logger.error(exception);
            } finally {
                connection.close();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}