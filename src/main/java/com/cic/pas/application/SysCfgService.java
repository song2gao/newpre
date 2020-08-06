package com.cic.pas.application;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.cic.pas.common.bean.PreMachine;
import com.cic.pas.process.Operation;
import com.cic.pas.process.OperationFactory;

/** 
 * 版权所有(C)2013-2014 CIC 
 * 公司名称：北京中诚盛源技术发展有限公司
 * 版本:V3.2
 * 文件名：com.cic.pas.common.service.SysCfgMonitor.java 
 * 作者: 郑瀚超 ,陈阿强
 * 创建时间: 2014-2-28下午05:23:37 
 * 修改时间：2014-2-28下午05:23:37 
 */
/** 
 * 系统配置管理服务
 * @ClassName: SysCfgMonitor 
 * @Description: TODO
 * @author lenovo 
 * @date 2014-2-28 下午05:23:37 
 *  
 */
public class SysCfgService {
	
	private Logger logger=Logger.getLogger(SysCfgService.class);
	/**
	 * 运维服务
	 */
	private static  Operation operation;
	
	private static List<PreMachine> preMachine;
	
	/**
	 * @Description: TODO(.......)
	 * @param     设定文件 
	 * @return void    返回类型 
	 */
	static {
		
		//实例化运维服务的逻辑类
		operation = OperationFactory.getInstance();
		
		preMachine=new ArrayList<PreMachine>();
		
		System.out.println("系统配置管理服务启动");
	}
	
	/** 
	 * @Title: readCfgParam 
	 * @Description: TODO(前置机参数配置服务) 
	 * @param
	 * @return void    返回类型 
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public void readCfgParam() {

		logger.info("读取前置机配置信息");

	}
	
	/**
	 * @Description: TODO(系统配置变更触发) 
	 * @param
	 * @return void    返回类型 
	 * @throws 
	 */
	public void sysConfigChanges(){
		System.out.println("系统配置变更服务");
	}
	
	/**
	 * @Description: TODO(运维配置监听) 
	 * @param
	 * @return void    返回类型 
	 * @throws 
	 */
	public void operationMonitor(){
		
		try {
//			operation.publishServe();
			operation.publishServer();
			//operation.publishClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("启动运维配置监听");
	}
	
}
