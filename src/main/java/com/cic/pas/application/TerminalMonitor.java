package com.cic.pas.application;

/** 
 * 版权所有(C)2013-2014 CIC 
 * 公司名称：北京中诚盛源技术发展有限公司
 * 版本:V3.0
 * 文件名：com.cic.pas.common.serviceInterface.TerminalMonitor.java 
 * 作者: 郑瀚超 ,陈阿强
 * 创建时间: 2014-2-28下午04:11:03 
 * 修改时间：2014-2-28下午04:11:03 
 */
/** 
 * @ClassName: TerminalMonitor 
 * @Description: 终端监视（A 采集器  B 计量表）
 * @author lenovo 
 * @date 2014-2-28 下午04:11:03 
 *  
 */
public class TerminalMonitor {
	
	/** 
	 * @Title: isOnline 
	 * @Description: TODO(是否在线) 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws 
	 */
	private void isOnline() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 终端的状态信息
	 */
	private void getTermStates() {
		// TODO Auto-generated method stub
		
	}
	
	/** 
	 * @Title: saveAlert 
	 * @Description: TODO(终端预警-暂时放在此处) 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws 
	 */
	private void saveAlert() {
		// TODO Auto-generated method stub

	}
	
	/** 
	 * @Title: saveFault 
	 * @Description: TODO(终端故障-暂时放在此处) 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws 
	 */
	private void saveFault() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 提供给监视服务方法
	 * （提供给外部使用）
	 */
	private void stateInfo(){
		System.out.println("终端状态信息");
	}
}
