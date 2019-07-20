package com.cic.pas.thread;

public class BaseThread extends Thread {
    public boolean suspended = false;
    public boolean exit = false;
    public boolean isReviced=false;
    @Override
    public void run() {
    }
    /**
     * 暂停
     */
    public void pause() {
        suspended = true;
    }

    /**
     * 继续
     */
    public synchronized void goon() {
        suspended = false;
        notify();
    }

}
