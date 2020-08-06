package com.cic.pas;

import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;


public class TestThread extends Thread {

    private Logger logger=Logger.getLogger(TestThread.class);

    private Map<String, Integer> map;

    public TestThread(Map<String, Integer> map) {
        this.map = map;
    }

    public void run() {
        while (true) {
            int flag = 0;
            if (map.containsKey("flag")) {
                flag = map.get("flag") + 1;
            }
            if (flag == 65535) {
                flag = 0;
            }
            map.put("flag", flag);
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + "flag值为：" + map.get("flag"));
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
