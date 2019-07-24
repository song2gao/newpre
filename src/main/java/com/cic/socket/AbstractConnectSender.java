package com.cic.socket;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.cic.pas.common.net.Command;
import com.cic.pas.common.net.Message;
import com.cic.pas.common.net.ReturnMessage;

public abstract class AbstractConnectSender implements SendingMessage{
	protected String serverIP = "";
	protected int port;
	protected Socket socket=null;
	protected InputStream is=null;
	protected OutputStream os=null;
	protected ObjectOutputStream oos=null;
	protected ObjectInputStream ois=null;
	
	public AbstractConnectSender(String IP, int port) {
		try {
			this.serverIP = IP;
			this.port = port;
			
			socket = new Socket(this.serverIP,this.port);	
			is = socket.getInputStream();
			os = socket.getOutputStream();
			ois = new ObjectInputStream(is);
			oos = new ObjectOutputStream(os);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public AbstractConnectSender(Socket socket) {
		try {
			this.socket = socket;
			os = socket.getOutputStream();
			is = socket.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
	/**
	 * 描述：与前置机进行通信的最重要的方法
	 * @return 接受服务器端返回结果；
	 */
	public List<ReturnMessage> response(){
		List<ReturnMessage> returnMessages = new ArrayList<ReturnMessage>();
		List<Message> messages = getMessages();
		try{
			for(Message m:messages){
				oos.writeObject(m);
				oos.flush();
				ReturnMessage rm = (ReturnMessage)ois.readObject();
				returnMessages.add(rm);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return returnMessages;
	}
	/**
	 * 描述：根据不用的应用 场景，只要覆盖此类，完成Message对象的封装即可，无需关心通信方式
	 * @return
	 */
	protected abstract List<Message> getMessages();
	
	public void close() {
		try {
			if (os != null) {
				os.close();
			}
			
			if (is != null) {
				is.close();
			}
			
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public OutputStream getOs() {
		return os;
	}

	public void setOs(OutputStream os) {
		this.os = os;
	}
	
}
