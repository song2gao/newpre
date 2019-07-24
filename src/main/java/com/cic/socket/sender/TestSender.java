package com.cic.socket.sender;

import java.util.ArrayList;
import java.util.List;


import com.cic.socket.AbstractConnectSender;
import com.cic.pas.common.net.Command;
import com.cic.pas.common.net.Message;
/**
 * 此类为与前置机通信测试类
 * @author wangruibo
 *
 */
public class TestSender extends AbstractConnectSender{
	
	public TestSender(String ip,int port){
		super(ip,port);
	}
	public  List<Message> getMessages(){
		List<Message> messages = new ArrayList<Message>();
		Message m = new Message();
		m.setMeterId("67");
		m.setC(Command.Disable);
		messages.add(m);
		Message m1 = new Message();
		m1.setMeterId("68");
		m1.setC(Command.Disable);
		messages.add(m1);
		Message m2 = new Message();
		m2.setMeterId("69");
		m2.setC(Command.Disable);
		messages.add(m2);
		Message m3 = new Message();
		m3.setMeterId("70");
		m3.setC(Command.Disable);
		messages.add(m3);
		return messages;
	}
	public static void main(String[] args){
		//TestSender ts = new TestSender("192.168.9.17",7005);
		//List<ReturnMessage> list = new ArrayList<ReturnMessage>();
		//list = ts.response();
		//for(ReturnMessage rm:list){
		//	System.out.println(rm.getStatus()+"---"+rm.getObject());
		//}
		try{
			TestSender ts = new TestSender("192.168.9.17",7005);
		}catch(Exception e){
			
		}finally{
			System.out.println(111);
		}
	}
}
