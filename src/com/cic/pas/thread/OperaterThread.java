package com.cic.pas.thread;

import com.cic.pas.common.net.Connection;
import com.cic.pas.common.net.ReturnMessage;
import com.cic.pas.process.Processer;
import com.cic.pas.process.ProcesserFactory;


public class OperaterThread extends Thread{
	private Connection connection;
	private Processer processer;
	public OperaterThread(Connection connection) {
		this.connection = connection;
		processer = ProcesserFactory.getInstance();
		setDaemon(true);
	}
	@Override
	public void run() {
		try {
			//while(true){
			Object object = connection.readObject();
			ReturnMessage response = processer.executeSome(object);
			connection.send(response);
			connection.close();
			Thread.currentThread().interrupt();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//connection.close();
		}
	}
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}