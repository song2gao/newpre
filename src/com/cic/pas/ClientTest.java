package com.cic.pas;

public class ClientTest {
	public static void main(String[] args) throws InterruptedException {
		ThreadTest test=new ThreadTest();
		test.start();
		for(int i=0;i<10;i++){
			System.out.println("主线程"+i);
			Thread.sleep(100);
		}
	}

}
