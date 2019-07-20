package com.cic.pas.thread;

import com.cic.pas.application.ChannelMonitor;
import com.cic.pas.application.PreMonitorService;
import com.cic.pas.application.TerminalMonitor;

/**
 * 
*    
* 项目名称：preAcquistionSystem.3.2   
* 类名称：DynaMonitoring   
* 类描述：   
* 创建人：lenovo   
* 创建时间：2014-3-18 下午03:35:18   
* 修改人：lenovo   
* 修改时间：2014-3-18 下午03:35:18   
* 修改备注：   
* @version    
*
 */

/**
 * 运行动态监视服务
 */
public class DynaMonitorThread extends Thread{

	private ChannelMonitor chaMonitor;
	private PreMonitorService preService;
	private TerminalMonitor terMonitor;
	
	
	//通过构造函数传递参数，要监控的是通道、前置机、终端
	private DynaMonitorThread(ChannelMonitor channelMonitor,PreMonitorService preMonitorService,TerminalMonitor terminalMonitor, String content){
		
		this.chaMonitor=channelMonitor;
		this.preService=preMonitorService;
		this.terMonitor=terminalMonitor;
	}
	
	public void run() {
		
	}
	
	/**
	 * 各个监视服务的变化进行处理
	 */
	public void update(){
		
	}
	
	
}
