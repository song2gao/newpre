package com.cic.pas.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.springframework.jdbc.core.JdbcTemplate;

import com.cic.pas.application.manage.PChannelService;
import com.cic.pas.common.bean.Channel;
import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.dao.BussinessConfig;
import com.cic.pas.dao.DBConfigDao;
import com.cic.pas.dao.LogDao;
import com.cic.pas.dao.LogFactory;
import com.cic.pas.service.ServerContext;


/** 
 * 版权所有(C)2013-2014 CIC 
 * 公司名称：北京中诚盛源技术发展有限公司
 * 版本:V3.0
 * 文件名：com.cic.pas.base.serviceInterface.apply.ChannelConfigService.java 
 * 作者: 刘亚 
 * 创建时间: 2014-2-26上午09:06:09 
 * 修改时间：2014-2-26上午09:06:09 
 */
/** 
 * @ClassName: ChannelConfigService 
 * @Description: 通道配置服务
 * @author lenovo 
 * @date 2014-2-26 上午09:06:09 
 *  
 */
@SuppressWarnings("all")
public class ChannelConfigService extends PChannelService{

	public static final Logger logger = Logger.getLogger(ChannelConfigService.class);
	
	/**
	 * 保存实时数据信息(充当实时内存库)
	 */
	public static List<TerminalDevice> CurrentTerminalList = null;
	/**
	 * 业务逻辑配置信息
	 */
	public static BussinessConfig config=BussinessConfig.getConfig();;
	
	static{
		CurrentTerminalList=Collections.synchronizedList(new ArrayList<TerminalDevice>());
		CurrentTerminalList=config.terminalList;
		
		LogFactory.getInstance().saveLog("加载通道配置参数完成",LogDao.operation);
	}
	/** 
	 * @Title: readConfig 
	 * @Description: TODO(通过数据库访问服务读取通道信息表/采集器、计量表、测点中的配置内容) 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws 
	 */
	private void readConfig() {
		// TODO Auto-generated method stub
	
		
		System.out.println("读取通道配置信息");
	}
	
	
	/** 
	 * @Title: addChannel 
	 * @Description: TODO(添加通道) 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws 
	 */
	private void addChannel(String socketPort,SocketAcceptor socketAcceptor ) {

		
		ServerContext.serverSocketList.put(socketPort, socketAcceptor);
		logger.info("通道"+socketPort+"已添加....");
	}
	/** 
	 * @Title: romoveChannel 
	 * @Description: TODO(移除通道) 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws 
	 */
	
	public boolean removeChannel(String socketPort) {
		// TODO Auto-generated method stub
		boolean b=false;
		for (String map : ServerContext.serverSocketList.keySet()) {
			
			if (socketPort.equals(map)) {
				
				ServerContext.serverSocketList.remove(socketPort);
				b=true;
			}
		}
		if (!b) {
			logger.info("需要移除的通道不存在....");
		}
		return b;

	}
}
