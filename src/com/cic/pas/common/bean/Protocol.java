/**  
* @Project: PreAcquisitionSystem3.0

* @Title: Protocol.java

* @Package com.cic.pas.bean

* @Description: TODO

* @author liuya7711@126.com

* @date 2014-2-28 上午10:06:41

* @Copyright: 2014 www.cic.cn

* @version V3.0  
*/
package com.cic.pas.common.bean;
/** 
 * 版权所有(C)2013-2014 CIC 
 * 公司名称：北京中诚盛源技术发展有限公司
 * 版本:V3.0
 * 文件名：com.cic.pas.bean.Protocol.java 
 * 作者: 郑瀚超,陈阿强
 * 创建时间: 2014-2-28上午10:06:41 
 * 修改时间：2014-2-28上午10:06:41 
 */
/** 
 * @ClassName: Protocol 
 * @Description: TODO
 * @author lenovo 
 * @date 2014-2-28 上午10:06:41 
 *  
 */
public class Protocol {
	private String id;
	private String cha_number;
	private String cha_addressPort;
	private String pro_proClass;
	private String pro_proSide;
	private String pro_commPro;
	private String pro_proDescription;
	private ProtocolConfig config;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/** 
	 * @param id 要设置的 id 
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the cha_number
	 */
	public String getCha_number() {
		return cha_number;
	}
	/** 
	 * @param chaNumber 要设置的 cha_number 
	 */
	public void setCha_number(String chaNumber) {
		cha_number = chaNumber;
	}
	/**
	 * @return the cha_addressPort
	 */
	public String getCha_addressPort() {
		return cha_addressPort;
	}
	/** 
	 * @param chaAddressPort 要设置的 cha_addressPort 
	 */
	public void setCha_addressPort(String chaAddressPort) {
		cha_addressPort = chaAddressPort;
	}
	/**
	 * @return the pro_proClass
	 */
	public String getPro_proClass() {
		return pro_proClass;
	}
	/** 
	 * @param proProClass 要设置的 pro_proClass 
	 */
	public void setPro_proClass(String proProClass) {
		pro_proClass = proProClass;
	}
	/**
	 * @return the pro_proSide
	 */
	public String getPro_proSide() {
		return pro_proSide;
	}
	/** 
	 * @param proProSide 要设置的 pro_proSide 
	 */
	public void setPro_proSide(String proProSide) {
		pro_proSide = proProSide;
	}
	/**
	 * @return the pro_commPro
	 */
	public String getPro_commPro() {
		return pro_commPro;
	}
	/** 
	 * @param proCommPro 要设置的 pro_commPro 
	 */
	public void setPro_commPro(String proCommPro) {
		pro_commPro = proCommPro;
	}
	/**
	 * @return the pro_proDescription
	 */
	public String getPro_proDescription() {
		return pro_proDescription;
	}
	/** 
	 * @param proProDescription 要设置的 pro_proDescription 
	 */
	public void setPro_proDescription(String proProDescription) {
		pro_proDescription = proProDescription;
	}
	/**
	 * @return the config
	 */
	public ProtocolConfig getConfig() {
		return config;
	}
	/** 
	 * @param config 要设置的 config 
	 */
	public void setConfig(ProtocolConfig config) {
		this.config = config;
	}
	
}
