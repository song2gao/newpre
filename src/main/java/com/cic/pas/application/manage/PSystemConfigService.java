package com.cic.pas.application.manage;

import com.cic.pas.application.SysCfgService;
import com.cic.pas.common.base.impl.PBaseClass;
/** 
 * 系统配置管理服务
 * @ClassName: SystemConfigService 
 * @Description: TODO
 * @author lenovo 
 * @date 2014-2-20 下午04:34:34 
 *  
 */
public class PSystemConfigService extends PBaseClass{
	
	private static SysCfgService sysCfgService;
	
	static {
		
		//运维监听服务启动
		sysCfgService=new SysCfgService();
		
		sysCfgService.operationMonitor();
	}
	
}
