package com.cic.pas;
public class ThreadTest extends Thread {
	public void run(){
		for(int i=0;i<10;i++){
			System.out.println("线程一"+i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
