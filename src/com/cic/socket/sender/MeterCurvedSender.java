package com.cic.socket.sender;

import java.util.ArrayList;
import java.util.List;

import com.cic.pas.common.bean.EsmspSumMeasurOrganizationDay;
import com.cic.pas.common.net.Command;
import com.cic.pas.common.net.Message;
import com.cic.socket.AbstractConnectSender;
/**
 * 描述:计量终端详细内容信息
 * @author wangruibo
 *
 */
public class MeterCurvedSender extends AbstractConnectSender{

	private List<EsmspSumMeasurOrganizationDay> daylist;
	
	public MeterCurvedSender(String ip,int port,List<EsmspSumMeasurOrganizationDay> list){
		super(ip,port);
		this.daylist = list;
	}
	public  List<Message> getMessages(){
		List<Message> messages = new ArrayList<Message>();
		for(EsmspSumMeasurOrganizationDay day:daylist){
			Message m = new Message();
			m.setC(Command.MeterDataCurved);
			m.setMeterCode(day.getEusCode());
			m.setPointCode(day.getMmpCode());
			messages.add(m);		
		}
		return messages;
	}
}
