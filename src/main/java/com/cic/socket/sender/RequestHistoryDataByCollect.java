package com.cic.socket.sender;

import java.util.ArrayList;
import java.util.List;

import com.cic.domain.PomsAssembledTerminalDevice;
import com.cic.socket.AbstractConnectSender;
import com.cic.pas.common.net.Command;
import com.cic.pas.common.net.Message;
/**
 * 按照采集器请求历史数据
 * @author wangruibo
 *
 */
public class RequestHistoryDataByCollect extends AbstractConnectSender {
	private List<PomsAssembledTerminalDevice> terminals;
	public RequestHistoryDataByCollect(String ip,int port,List<PomsAssembledTerminalDevice> list){
		super(ip,port);
		this.terminals = list;
	}
	public  List<Message> getMessages(){
		List<Message> messages = new ArrayList<Message>();
		for(PomsAssembledTerminalDevice terminal:terminals){
			Message m = new Message();
			m.setC(Command.HistoryDataByCollect);
			m.setCollect(Integer.parseInt(terminal.getAsstdCode()));
			messages.add(m);		
		}
		return messages;
	}
}
