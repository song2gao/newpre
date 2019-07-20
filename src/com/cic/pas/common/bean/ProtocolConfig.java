/**  
* @Project: PreAcquisitionSystem3.0

* @Title: ProtocolConfig.java

* @Package com.cic.pas.bean

* @Description: TODO

* @author liuya7711@126.com

* @date 2014-2-28 下午01:58:22

* @Copyright: 2014 www.cic.cn

* @version V3.0  
*/
package com.cic.pas.common.bean;

import java.util.List;

/** 
 * 版权所有(C)2013-2014 CIC 
 * 公司名称：北京中诚盛源技术发展有限公司
 * 版本:V3.0
 * 文件名：com.cic.pas.bean.ProtocolConfig.java 
 * 作者: 郑瀚超,陈阿强
 * 创建时间: 2014-2-28下午01:58:22 
 * 修改时间：2014-2-28下午01:58:22 
 */
/** 
 * @ClassName: ProtocolConfig 
 * @Description: TODO
 * @author lenovo 
 * @date 2014-2-28 下午01:58:22 
 *  
 */
public class ProtocolConfig {
	
	private String proc_id;
	private String proc_itemName;
	private String proc_dataValue;
	private String proc_defaultValue;
	private String proc_comment;
	private String proc_proCodeForegin;
	
	private List<ProtocolConfig> protocolConfigList;//协议配置项
	
	
	public List<ProtocolConfig> getProtocolConfigList() {
		return protocolConfigList;
	}
	public void setProtocolConfigList(List<ProtocolConfig> protocolConfigList) {
		this.protocolConfigList = protocolConfigList;
	}
	/**
	 * @return the proc_id
	 */
	public String getProc_id() {
		return proc_id;
	}
	/** 
	 * @param procId 要设置的 proc_id 
	 */
	public void setProc_id(String procId) {
		proc_id = procId;
	}
	/**
	 * @return the proc_itemName
	 */
	public String getProc_itemName() {
		return proc_itemName;
	}
	/** 
	 * @param procItemName 要设置的 proc_itemName 
	 */
	public void setProc_itemName(String procItemName) {
		proc_itemName = procItemName;
	}
	/**
	 * @return the proc_dataValue
	 */
	public String getProc_dataValue() {
		return proc_dataValue;
	}
	/** 
	 * @param procDataValue 要设置的 proc_dataValue 
	 */
	public void setProc_dataValue(String procDataValue) {
		proc_dataValue = procDataValue;
	}
	/**
	 * @return the proc_defaultValue
	 */
	public String getProc_defaultValue() {
		return proc_defaultValue;
	}
	/** 
	 * @param procDefaultValue 要设置的 proc_defaultValue 
	 */
	public void setProc_defaultValue(String procDefaultValue) {
		proc_defaultValue = procDefaultValue;
	}
	/**
	 * @return the proc_comment
	 */
	public String getProc_comment() {
		return proc_comment;
	}
	/** 
	 * @param procComment 要设置的 proc_comment 
	 */
	public void setProc_comment(String procComment) {
		proc_comment = procComment;
	}
	/**
	 * @return the proc_proCodeForegin
	 */
	public String getProc_proCodeForegin() {
		return proc_proCodeForegin;
	}
	/** 
	 * @param procProCodeForegin 要设置的 proc_proCodeForegin 
	 */
	public void setProc_proCodeForegin(String procProCodeForegin) {
		proc_proCodeForegin = procProCodeForegin;
	}
	
}
