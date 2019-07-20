package com.cic.socket.sender;

import java.util.ArrayList;
import java.util.List;

import com.cic.domain.PomsCalculateTerminalDevice;
import com.cic.pas.common.bean.MeterDevice;
import com.cic.socket.AbstractConnectSender;
import com.cic.pas.common.net.Command;
import com.cic.pas.common.net.Message;
/**
 * 描述:请求计量表的数据
 * @author wangruibo
 *
 */
public class MeterDeviceMeterDataSender extends AbstractConnectSender{
	
	private List<MeterDevice> calculates;
	
	public MeterDeviceMeterDataSender(String ip,int port,List<MeterDevice> list){
		super(ip,port);
		this.calculates = list;
	}
	
	public  List<Message> getMessages(){
		List<Message> messages = new ArrayList<Message>();
		for(MeterDevice cal:calculates){
			Message m = new Message();
			m.setC(Command.MeterData);
			m.setMeterId(cal.getId());
			messages.add(m);		
		}
		return messages;
	}
}
