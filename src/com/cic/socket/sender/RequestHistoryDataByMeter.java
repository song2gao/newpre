package com.cic.socket.sender;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cic.domain.PomsCalculateTerminalDevice;
import com.cic.socket.AbstractConnectSender;
import com.cic.pas.common.net.Command;
import com.cic.pas.common.net.Message;
/**
 * 按照计量表采集历史数据
 * @author wangruibo
 *
 */
public class RequestHistoryDataByMeter extends AbstractConnectSender{

	private List<PomsCalculateTerminalDevice> calculates;
	private List<Object> ls;
	public RequestHistoryDataByMeter(String ip,int port,List<PomsCalculateTerminalDevice> list,List list2){
		super(ip,port);
		this.calculates = list;
		this.ls = list2;
	}
	
	public  List<Message> getMessages(){
		List<Message> messages = new ArrayList<Message>();
		for(PomsCalculateTerminalDevice cal:calculates){
			Message m = new Message();
			m.setC(Command.HistoryDataByMeter);
			m.setMeterId(cal.getId());
			Object [] sk = (Object[]) ls.get(0);
			
			
				m.setType(Integer.parseInt(sk[2].toString()));//类型
				m.setCollect(Integer.parseInt(sk[4].toString()));//采集器
				m.setScalePointName("InPw");	//编码
				//m.setScalePointName("InPwH");	//第二编码
				m.setStartDate((Date)sk[0]);//起始时间
				m.setEndDate((Date)sk[1]);//结束时间
			
			
			messages.add(m);		
		}
		return messages;
	}
}
