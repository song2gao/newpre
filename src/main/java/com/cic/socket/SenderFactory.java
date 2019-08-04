package com.cic.socket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cic.domain.PomsAssembledTerminalDevice;
import com.cic.domain.PomsCalculateTerminalDevice;
import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.net.ReturnMessage;
import com.cic.socket.sender.*;

/**
 * 描述:此类为与前置机通信的接口工厂，根据不同的业务应用需求，创建不同的接口对象，进行调用接口方法并通信。
 * @author wangruibo
 *
 */
public class SenderFactory {
	private String serverIP;
	private int port;
	
	public  SenderFactory(String serverIP,int port){
		this.serverIP = serverIP;
		this.port = port;
	}
	/**
	 * 获得测试类对象
	 * @return
	 */
	public SendingMessage getTestSender(){
		
		SendingMessage  ts = new TestSender(serverIP,port);
		
		return ts;
	}
	/**
	 * 命令 创建新增命令
	 * @param list 计量仪表的集合
	 * @return
	 */
	public SendingMessage getMeterDeviceCreateSender(List<PomsCalculateTerminalDevice> list){
		
		SendingMessage  mdcs = new MeterDeviceCreateSender(serverIP,port,list);
		
		return mdcs;
	}
	/**
	 * 命令 删除命令
	 * @param list 计量仪表集合
	 * @return
	 */
	public SendingMessage getMeterDeviceDeleteSender(List<PomsCalculateTerminalDevice> list){
		SendingMessage  mdds = new MeterDeviceDeleteSender(serverIP,port,list);
		
		return mdds;
	}
	/**
	 * 计量表终端详细信息
	 * @param list 计量仪表集合
	 * @return
	 * xiangx
	 */
	public SendingMessage getMeterDeviceDetailSender(List<PomsCalculateTerminalDevice> list){
		SendingMessage  ts = new MeterDeviceDetailSender(serverIP,port,list);
		
		return ts;
	}
	/**
	 * 命令 停用命令
	 * @param list 计量仪表集合
	 * @return
	 */
	public SendingMessage getMeterDeviceDisableSender(List<PomsCalculateTerminalDevice> list){
		SendingMessage  ts = new MeterDeviceDisableSender(serverIP,port,list);
		
		return ts;
	}
	/**
	 * 请求计量仪表终端数据
	 * @param list 计量仪表集合
	 * @return
	 * shuju
	 */
	public SendingMessage getMeterDeviceMeterDataSender(List<MeterDevice> list){
		SendingMessage  ts = new MeterDeviceMeterDataSender(serverIP,port,list);
		
		return ts;
	}
	/**
	 * 命令 正常
	 * @param list 计量仪表集合
	 * @return
	 */
	public SendingMessage getMeterDeviceNormalSender(List<PomsCalculateTerminalDevice> list){
		SendingMessage  ts = new MeterDeviceNormalSender(serverIP,port,list);
		
		return ts;	
		}
	/**
	 * 计量仪表终端状态信息
	 * @param list 计量仪表集合
	 * @return
	 */
	public SendingMessage getMeterDeviceStatusSender(List<PomsCalculateTerminalDevice> list){
		SendingMessage  ts = new MeterDeviceStatusSender(serverIP,port,list);
		
		return ts;
	}
	/**
	 * 命令  测试命令
	 * @param list 计量仪表集合
	 * @return
	 */
	public SendingMessage getMeterDeviceTrialSender(List<PomsCalculateTerminalDevice> list){
		SendingMessage  ts = new MeterDeviceTrialSender(serverIP,port,list);
		
		return ts;
	}
	/**
	 * 命令 更新命令
	 * @param list 计量仪表集合
	 * @return
	 */
	public SendingMessage getMeterDeviceUpdateSender(List<PomsCalculateTerminalDevice> list){
		SendingMessage  ts = new MeterDeviceUpdateSender(serverIP,port,list);
		
		return ts;
	}
	/**
	 * 根据采集器，请求历史数据
	 * @param list 采集器集合
	 * @return
	 */
	public SendingMessage getRequestHistoryDataByCollect(List<PomsAssembledTerminalDevice> list){
		SendingMessage  ts = new RequestHistoryDataByCollect(serverIP,port,list);
		
		return ts;
	}
	/**
	 * 根据计量仪表，请求历史数据
	 * @param list 计量仪表集合
	 * @return
	 */
	public SendingMessage getRequestHistoryDataByMeter(List<PomsCalculateTerminalDevice> list,List list2){
		SendingMessage  ts = new RequestHistoryDataByMeter(serverIP,port,list,list2);
		
		return ts;
	}
	/**
	 * 获得采集器的详细信息
	 * @param list 采集器集合
	 * @return
	 */
	public SendingMessage getTerminalDeviceDetailSender(List<PomsAssembledTerminalDevice> list){
		SendingMessage  ts = new TerminalDeviceDetailSender(serverIP,port,list);
		
		return ts;
	}
	/**
	 * 获得采集器的状态信息
	 * @param list 采集器集合
	 * @return
	 */
	public SendingMessage getTerminalDeviceStatusSender(List<PomsAssembledTerminalDevice> list){
		SendingMessage  ts = new TerminalDeviceStatusSender(serverIP,port,list);
		
		return ts;
	}
	/**
	 * 方法名: getSystemDataSender
	 * 描述: 得到用能系统实时数据
	 * 参数: [map]
	 * 返回值: com.cic.socket.SendingMessage
	 * 作者:高嵩
	 * 创建时间: 2019/8/3 11:41
	 **/
	public SendingMessage getSystemDataSender(Map<String,Object> map){
		SendingMessage  ts = new SystemDataSender(serverIP,port,map);
		return ts;
	}
	
	public static void main(String[] args){
		SenderFactory sf = new SenderFactory("192.168.9.17",7005);
		List<ReturnMessage> list = new ArrayList<ReturnMessage>();
		SendingMessage sm = sf.getTestSender();
		list = sm.response();
		for(ReturnMessage rm:list){
			System.out.println("this is:"+rm.getStatus());
		}
	}
}
