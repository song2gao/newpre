package com.cic.pas;

import java.util.Map;


public class TestThread extends Thread {

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
				System.out.println(Thread.currentThread().getName()+"flag值为："+map.get("flag"));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
