/**  
* @Project: PreAcquisitionSystem3.0

* @Title: RunningStatusInterface.java

* @Package com.cic.pas.base.serviceInterface

* @Description: TODO

* @author liuya7711@126.com

* @date 2014-2-20 下午04:32:37

* @Copyright: 2014 www.cic.cn

* @version V3.0  
*/
package com.cic.pas.application.manage;

import com.cic.pas.application.ChannelMonitor;
import com.cic.pas.application.PreMonitorService;
import com.cic.pas.application.TerminalMonitor;
import com.cic.pas.common.base.impl.PBaseClass;

/** 
 * 版权所有(C)2013-2014 CIC 
 * 公司名称：北京中诚盛源技术发展有限公司
 * 版本:V3.0
 * 文件名：com.cic.pas.base.serviceInterface.RunningStatusInterface.java 
 * 作者: 刘亚 
 * 创建时间: 2014-2-20下午04:32:37 
 * 修改时间：2014-2-20下午04:32:37 
 */
/** 
 * @ClassName: RunningStatusInterface 
 * @Description: 运行状态监视管理层
 * @author lenovo 
 * @date 2014-2-20 下午04:32:37 
 *  
 */
	public class PRunningStatusService extends PBaseClass implements Runnable{
	
	
	
	public void startPmService()  {
			PreMonitorService preMonitorService = new PreMonitorService();
			preMonitorService.getCPU();
			preMonitorService.getMemory();
	}
	
	public void startChannelMonitor() {
		new ChannelMonitor();
	}
	
	public void startTerminalMonitor() {
		new TerminalMonitor();
	}

	@Override
	public void run() {
		this.startPmService();
		
	}
}
