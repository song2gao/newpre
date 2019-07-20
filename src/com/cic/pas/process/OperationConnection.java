package com.cic.pas.process;

import java.io.IOException;
import java.net.Socket;

import com.cic.pas.common.net.Connection;


public class OperationConnection extends Connection{

	public OperationConnection(Socket socket) {
		super(socket);
	}
	public OperationConnection(String ip,int port){
		super(ip,port);
	}
	@Override
	public Object readObject() {
		Object obj = null;
		try {
			obj = ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
