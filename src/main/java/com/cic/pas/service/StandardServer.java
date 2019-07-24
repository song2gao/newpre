
package com.cic.pas.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.cic.pas.application.ChannelConfigService;
import com.cic.pas.application.ChannelManageService;
import com.cic.pas.common.bean.Channel;
import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.dao.BussinessConfig;
import com.cic.pas.dao.LogDao;
import com.cic.pas.dao.LogFactory;


public class StandardServer implements StandardServerMBean{
	public static Logger logger = Logger.getLogger(StandardServer.class);
	
	/**
	 * 线程池
	 */
	private ExecutorService executorService;

	
	public  StandardServer(){
		
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()* ServerContext.getPoolSize());
		
	}
	
	/*
	 *
	 1、将编码、解码、handler 处理器 剥离mina 结构
	 2、定义协议接口，将上述信息集成进去
	 3、加载通道（创建简监听时），使用协议相应的编码、解码器，handler处理器
	 4、监听启动
	 
	 */
    
	
	@Override
	public void startService(){
		try {
			
			//端口监听服务
			List<Channel> channelList =ChannelConfigService.config.channelList;
			//List<TerminalDevice> list=BussinessConfig.config.terminalList;
			new ChannelManageService().open(channelList);
			
			
			
		} 
		catch (Exception e) {
			LogFactory.getInstance().saveLog("启动服务异常",LogDao.exception);
		}
	}

	/**
	 * 关闭服务
	 */
	 public void endService() {}

}