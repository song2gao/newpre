package com.cic.pas.common.bean;

import java.util.List;

/** 
 * 版权所有(C)2013-2014 CIC 
 * 公司名称：北京中诚盛源技术发展有限公司
 * 版本:V3.0
 * 文件名：com.cic.pas.bean.Channel.java 
 * 作者: 郑瀚超,陈阿强
 * 创建时间: 2014-2-21下午02:21:48 
 * 修改时间：2014-2-21下午02:21:48 
 */
/** 通道类
 * @ClassName: Channel 
 * @Description: TODO
 * @author lenovo 
 * @date 2014-2-21 下午02:21:48 
 *  
 */
public class Channel {
	private String primaryKey; //主键
	private String cha_number;//通道号
	private String cha_protocolCode; //通道协议编码
	private String cha_addressPort;//通道IP及端口
	private int cha_useMark; //使用标志
	private String cha_detectionMark;//检测标志
	private String cha_commMode; //通讯方式
	private String cha_reserveChannel;//备用通道
	private String cha_runStatus; //运行状态
	private Protocol protocol; //通信协议
	
	private String cha_faultdate;//故障时间
	private String cha_redate;//恢复时间
	private String cha_rundate;//运行时间
	private String cha_faultTimes;//故障次数
	private String cha_sendNum;//发送字节数
	private String cha_receiveNum;//接收字节数
	private String cha_lastDate;//上次交互时间
	private String cha_dropNum;//掉线次数
	private String cha_resendNum;//重发次数
	
	private String cha_onlineTime;//累计在线次数
	
	private String cha_errorrate;//误码率
	
	private List<TerminalDevice> terminalList; //采集器列表
	
	
	public List<TerminalDevice> getTerminalList() {
		return terminalList;
	}
	public void setTerminalList(List<TerminalDevice> terminalList) {
		this.terminalList = terminalList;
	}
	
	public Channel(){
		
	}
	
	
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
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
	 * @return the cha_protocolCode
	 */
	public String getCha_protocolCode() {
		return cha_protocolCode;
	}
	/** 
	 * @param chaProtocolCode 要设置的 cha_protocolCode 
	 */
	public void setCha_protocolCode(String chaProtocolCode) {
		cha_protocolCode = chaProtocolCode;
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
	 * @return the cha_useMark
	 */
	public int getCha_useMark() {
		return cha_useMark;
	}
	/** 
	 * @param chaUseMark 要设置的 cha_useMark 
	 */
	public void setCha_useMark(int chaUseMark) {
		cha_useMark = chaUseMark;
	}
	/**
	 * @return the cha_detectionMark
	 */
	public String getCha_detectionMark() {
		return cha_detectionMark;
	}
	/** 
	 * @param chaDetectionMark 要设置的 cha_detectionMark 
	 */
	public void setCha_detectionMark(String chaDetectionMark) {
		cha_detectionMark = chaDetectionMark;
	}
	/**
	 * @return the cha_commMode
	 */
	public String getCha_commMode() {
		return cha_commMode;
	}
	/** 
	 * @param chaCommMode 要设置的 cha_commMode 
	 */
	public void setCha_commMode(String chaCommMode) {
		cha_commMode = chaCommMode;
	}
	/**
	 * @return the cha_reserveChannel
	 */
	public String getCha_reserveChannel() {
		return cha_reserveChannel;
	}
	/** 
	 * @param chaReserveChannel 要设置的 cha_reserveChannel 
	 */
	public void setCha_reserveChannel(String chaReserveChannel) {
		cha_reserveChannel = chaReserveChannel;
	}
	/**
	 * @return the cha_runStatus
	 */
	public String getCha_runStatus() {
		return cha_runStatus;
	}
	/** 
	 * @param chaRunStatus 要设置的 cha_runStatus 
	 */
	public void setCha_runStatus(String chaRunStatus) {
		cha_runStatus = chaRunStatus;
	}
	/**
	 * @return the protocol
	 */
	public Protocol getProtocol() {
		return protocol;
	}
	/** 
	 * @param protocol 要设置的 protocol 
	 */
	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}
	public String getCha_faultdate() {
		return cha_faultdate;
	}
	public void setCha_faultdate(String chaFaultdate) {
		cha_faultdate = chaFaultdate;
	}
	public String getCha_redate() {
		return cha_redate;
	}
	public void setCha_redate(String chaRedate) {
		cha_redate = chaRedate;
	}
	public String getCha_rundate() {
		return cha_rundate;
	}
	public void setCha_rundate(String chaRundate) {
		cha_rundate = chaRundate;
	}
	public String getCha_faultTimes() {
		return cha_faultTimes;
	}
	public void setCha_faultTimes(String chaFaultTimes) {
		cha_faultTimes = chaFaultTimes;
	}
	public String getCha_sendNum() {
		return cha_sendNum;
	}
	public void setCha_sendNum(String chaSendNum) {
		cha_sendNum = chaSendNum;
	}
	public String getCha_receiveNum() {
		return cha_receiveNum;
	}
	public void setCha_receiveNum(String chaReceiveNum) {
		cha_receiveNum = chaReceiveNum;
	}
	public String getCha_lastDate() {
		return cha_lastDate;
	}
	public void setCha_lastDate(String chaLastDate) {
		cha_lastDate = chaLastDate;
	}
	public String getCha_dropNum() {
		return cha_dropNum;
	}
	public void setCha_dropNum(String chaDropNum) {
		cha_dropNum = chaDropNum;
	}
	public String getCha_resendNum() {
		return cha_resendNum;
	}
	public void setCha_resendNum(String chaResendNum) {
		cha_resendNum = chaResendNum;
	}
	public String getCha_onlineTime() {
		return cha_onlineTime;
	}
	public void setCha_onlineTime(String chaOnlineTime) {
		cha_onlineTime = chaOnlineTime;
	}
	public String getCha_errorrate() {
		return cha_errorrate;
	}
	public void setCha_errorrate(String chaErrorrate) {
		cha_errorrate = chaErrorrate;
	}
	
	
}
