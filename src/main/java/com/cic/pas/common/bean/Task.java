/**  
* @Project: PreAcquisitionSystem3.0

* @Title: Task.java

* @Package com.cic.pas.bean

* @Description: TODO

* @author liuya7711@126.com

* @date 2014-2-21 下午02:22:55

* @Copyright: 2014 www.cic.cn

* @version V3.0  
*/
package com.cic.pas.common.bean;

import java.util.Date;

/** 
 * 版权所有(C)2013-2014 CIC 
 * 公司名称：北京中诚盛源技术发展有限公司
 * 版本:V3.0
 * 文件名：com.cic.pas.bean.Task.java 
 * 作者: 郑瀚超,陈阿强
 * 创建时间: 2014-2-21下午02:22:55 
 * 修改时间：2014-2-21下午02:22:55 
 */
/** 
 * @ClassName: Task 
 * @author lenovo 
 * @date 2014-2-21 下午02:22:55 
 *  
 */
//任务基类
public class Task {
	private String tas_name;
	private String tas_type;
	private Date tas_startTime;
	private Date tas_endTime;
	private String tas_describe;
	/**
	 * @return the tas_name
	 */
	public String getTas_name() {
		return tas_name;
	}
	/** 
	 * @param tasName 要设置的 tas_name 
	 */
	public void setTas_name(String tasName) {
		tas_name = tasName;
	}
	/**
	 * @return the tas_type
	 */
	public String getTas_type() {
		return tas_type;
	}
	/** 
	 * @param tasType 要设置的 tas_type 
	 */
	public void setTas_type(String tasType) {
		tas_type = tasType;
	}
	/**
	 * @return the tas_startTime
	 */
	public Date getTas_startTime() {
		return tas_startTime;
	}
	/** 
	 * @param tasStartTime 要设置的 tas_startTime 
	 */
	public void setTas_startTime(Date tasStartTime) {
		tas_startTime = tasStartTime;
	}
	/**
	 * @return the tas_endTime
	 */
	public Date getTas_endTime() {
		return tas_endTime;
	}
	/** 
	 * @param tasEndTime 要设置的 tas_endTime 
	 */
	public void setTas_endTime(Date tasEndTime) {
		tas_endTime = tasEndTime;
	}
	/**
	 * @return the tas_describe
	 */
	public String getTas_describe() {
		return tas_describe;
	}
	/** 
	 * @param tasDescribe 要设置的 tas_describe 
	 */
	public void setTas_describe(String tasDescribe) {
		tas_describe = tasDescribe;
	}
	
}
