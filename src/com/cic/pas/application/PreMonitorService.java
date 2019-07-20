/**  
* @Project: PreAcquisitionSystem3.0

* @Title: PreMonitorService.java

* @Package com.cic.pas.common.serviceInterface

* @Description: TODO

* @author liuya7711@126.com

* @date 2014-2-28 下午04:06:39

* @Copyright: 2014 www.cic.cn

* @version V3.0  
*/
package com.cic.pas.application;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import com.cic.pas.preMonitor.IMonitorService;
import com.cic.pas.preMonitor.MonitorInfoBean;
import com.cic.pas.preMonitor.MonitorServiceImpl;

/** 
 * 版权所有(C)2013-2014 CIC 
 * 公司名称：北京中诚盛源技术发展有限公司
 * 版本:V3.0
 * 文件名：com.cic.pas.common.serviceInterface.PreMonitorService.java 
 * 作者: 郑瀚超 ,陈阿强
 * 创建时间: 2014-2-28下午04:06:39 
 * 修改时间：2014-2-28下午04:06:39 
 */
/** 
 * 前置机监视服务类
 * @ClassName: PreMonitorService 
 * @Description: TODO
 * @author lenovo 
 * @date 2014-2-28 下午04:06:39 
 *  
 */
public class PreMonitorService {

	
	private IMonitorService service = new MonitorServiceImpl();  
	private MonitorInfoBean monitorInfo = service.getMonitorInfoBean();
		
	private Sigar sigar = new Sigar();
	/**
	 * @throws Throwable  
	 * @Title: getCPU 
	 * @Description: TODO(得到前置机系统的CPU的利用率) 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws 
	 */
	public void getCPU(){
		
		
		CpuPerc cpuList[] = null;
		try {
			cpuList = sigar.getCpuPercList();
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		double totalRate = 0;
		for (int i = 0; i < cpuList.length; i++) {
			double tempRate = cpuList[i].getCombined();
			totalRate += tempRate;
			totalRate = totalRate/(i+1);
		}
		System.out.println("cpu总使用率:"+String.valueOf(totalRate));
	}
	/** 
	 * @Title: getMemory 
	 * @Description: TODO(得到系统内存信息) 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws 
	 */
	public void getMemory() {
		DecimalFormat df = new DecimalFormat("#0.00"); 
		Mem mem;
		try {
			mem = sigar.getMem();
			float totalMemory = (float)mem.getTotal() / 1024/1024/1024;
			float nowMemory = (float)mem.getUsed() / 1024/1024/1024;
			float decimal  = nowMemory/totalMemory;
			NumberFormat percentFormat =NumberFormat.getPercentInstance();
			String rateMemory = percentFormat.format(decimal);
			System.out.println("内存使用率为:"+rateMemory);
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 取得前置机的内存利用率
	 */
	private void memoryUtilization(){
		
		System.out.println("取得前置机的内存利用率");
	}
	
	/**
	 * 提供给监视服务方法
	 * (提供给外部调用)
	 */
	private void stateInfo(){
	
		System.out.println("前置机状态信息");
	}
}
