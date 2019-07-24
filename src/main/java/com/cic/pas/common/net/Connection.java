/**
 * 
 */
package com.cic.pas.common.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * @author Administrator 
 * 相当于一条TCP Connection
 * 这里有可能出错
 */
public class Connection {
	protected Socket socket;
	protected InputStream is;
	protected OutputStream os;
	protected ObjectOutputStream oos;
	public ObjectInputStream ois;
	
	public Connection(){
		
	}
	
	public Connection(Socket socket, String IP, int port) {
		try {
			this.socket = socket;
			os = socket.getOutputStream();		
			is = socket.getInputStream();
			oos = new ObjectOutputStream(os);
			ois = new ObjectInputStream(is);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	};

	public Connection(String IP, int port) {
		try {
			socket = new Socket(IP, port);	
			os = socket.getOutputStream();
			is = socket.getInputStream();
			oos = new ObjectOutputStream(os);
			ois = new ObjectInputStream(is);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Connection(Socket socket) {
		try {
			this.socket = socket;
			os = socket.getOutputStream();
			is = socket.getInputStream();
			oos = new ObjectOutputStream(os);
			ois = new ObjectInputStream(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
	
	public int read() throws Exception{
		return is.read();
	}
	
	public Object readObject(){
		Request request = null;
		try {
			request = (Request) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return request;
	}
	
	public Response sendMessage(Request request) throws Exception {
		oos.writeObject(request);
		oos.flush();
		Response response = (Response) ois.readObject();
		return response;
	}
	public void send(Object obj) throws Exception {
		oos.writeObject(obj);
		oos.flush();
		close();
	}

	public void sendMessage(Response response) throws Exception {
		oos.writeObject(response);
		oos.flush();
	}

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

	public boolean isConnection() {
		return socket.isConnected();
	}

	public Socket getSocket() {
		return this.socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public InputStream getIs() {
		return this.is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public OutputStream getOs() {
		return this.os;
	}

	public void setOs(OutputStream os) {
		this.os = os;
	}

}
