package com.cic.socket.sender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cic.pas.common.net.Command;
import com.cic.pas.common.net.Message;
import com.cic.socket.AbstractConnectSender;

public class MeterDeviceDataByCtdCodeSender extends AbstractConnectSender {

private String ctdCode="1";
	
	public MeterDeviceDataByCtdCodeSender(String ip,int port,String ctdCode){
		super(ip,port);
		this.ctdCode=ctdCode;
	}
	
	public  List<Message> getMessages(){
		List<Message> messages = new ArrayList<Message>();
			Message m = new Message();
			m.setC(Command.MeterDataByCtdCode);
			m.setMeterCode(ctdCode);
			messages.add(m);		
		return messages;
	}

}
