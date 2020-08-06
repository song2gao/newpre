package com.cic.pas;

import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ThreadTest extends Thread {
    private Logger logger=Logger.getLogger(ThreadTest.class);
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("线程一" + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                e.printStackTrace(new PrintStream(baos));
                String exception = baos.toString();
                logger.error(exception);
            }
        }
    }

}
