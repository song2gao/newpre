package com.cic.socket.sender;

import java.util.ArrayList;
import java.util.List;

import com.cic.domain.PomsCalculateTerminalDevice;
import com.cic.socket.AbstractConnectSender;
import com.cic.pas.common.net.Command;
import com.cic.pas.common.net.Message;
/**
 * 描述：计量表更新命令
 * @author wangruibo
 *
 */
public class MeterDeviceUpdateSender extends AbstractConnectSender{

	private List<PomsCalculateTerminalDevice> calculates;
	
	public MeterDeviceUpdateSender(String ip,int port,List<PomsCalculateTerminalDevice> list){
		super(ip,port);
		this.calculates = list;
	}
	
	public  List<Message> getMessages(){
		List<Message> messages = new ArrayList<Message>();
		for(PomsCalculateTerminalDevice cal:calculates){
			Message m = new Message();
			m.setC(Command.Update);
			m.setCollect(Integer.parseInt(cal.getPomsAssembledTerminalDevice().getAsstdCode()));
			m.setMeterId(cal.getId());
			messages.add(m);		
		}
		return messages;
	}
}
