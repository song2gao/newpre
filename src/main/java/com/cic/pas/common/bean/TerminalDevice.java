package com.cic.pas.common.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 
 * 采集器
 */
public class TerminalDevice implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9149359919365522117L;
	private String primaryKey;
	/**采集终端编码*/
	private int id;
	/**采集终端名称*/
	private String name;
	/**采集终端位置*/
	private String location;
	/**采集终端内网ip地址                --备注 外网没用*/    
	private String MSA;
	/**采集终端的下行报文*/
	private String code;
	/**采集终端的下行报文 一个PnFn*/
	private List<String> fnCodes = new ArrayList<String>();
	/**采集终端运行状态*/
	private String runnable_state;
	/**采集终端生产厂家*/
	private String production;
	/**采集终端下测点集*/
	private List<MeterDevice> meterList;
	/**在线离线*/
	private int isOnline;
	/**通讯方式*/
	private String noticeManner;
	/**通讯协议*/
	private String noticeAccord;
	/**
	 * 正常表计个数(通讯)
	 */
	private int normalMeterCount;
	/**
	 * 故障表计个数(通讯)
	 */
	private int faultMeterCount;

	/**通讯类型 0,TCP 1,UDP 2GPRS*/
	private int asstd_Comunicate_Type;

	/**上次交互时间*/
	private Date asstd_LastDate;
	/**
	 * 设备型号
	 */
	private String deviceModel;
	/**
	 * 采集器通讯状态
	 */
	private int connectState=0;

	private int roundCycle=3000;

	private int timeOut=3000;

	private int calculateCycle=300;

	public int getNormalMeterCount() {
		return normalMeterCount;
	}

	public void setNormalMeterCount(int normalMeterCount) {
		this.normalMeterCount = normalMeterCount;
	}

	public int getFaultMeterCount() {
		return faultMeterCount;
	}

	public void setFaultMeterCount(int faultMeterCount) {
		this.faultMeterCount = faultMeterCount;
	}

	/**通道号*/
	private String asstd_Channelnum;

	public int getConnectState() {
		return connectState;
	}

	public void setConnectState(int connectState) {
		this.connectState = connectState;
	}

	public int getRoundCycle() {
		return roundCycle;
	}

	public void setRoundCycle(int roundCycle) {
		this.roundCycle = roundCycle;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public int getCalculateCycle() {
		return calculateCycle;
	}

	public void setCalculateCycle(int calculateCycle) {
		this.calculateCycle = calculateCycle;
	}

	public TerminalDevice() {
		//portList = new ArrayList<MeterDevice>();
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMSA() {
		return this.MSA;
	}

	public void setMSA(String msa) {
		this.MSA = msa;
	}

	public void addPort(MeterDevice meter) {
		meterList.add(meter);
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "id=" + id + ";" + "name=" + name + ";" + "location=" + location
				+ ";" + "MSA=" + MSA + ";" + meterList + "\n";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<String> getFnCodes() {
		return fnCodes;
	}

	public void setFnCodes(List<String> fnCodes) {
		this.fnCodes = fnCodes;
	}

	public String getRunnable_state() {
		return runnable_state;
	}

	public void setRunnable_state(String runnableState) {
		runnable_state = runnableState;
	}

	public String getProduction() {
		return production;
	}

	public void setProduction(String production) {
		this.production = production;
	}

	public List<MeterDevice> getMeterList() {
		return meterList;
	}

	public void setMeterList(List<MeterDevice> meterList) {
		this.meterList = meterList;
	}


	public int getIsOnline() {
		return isOnline;
	}


	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
	}


	public String getNoticeManner() {
		return noticeManner;
	}


	public void setNoticeManner(String noticeManner) {
		this.noticeManner = noticeManner;
	}


	public String getNoticeAccord() {
		return noticeAccord;
	}


	public void setNoticeAccord(String noticeAccord) {
		this.noticeAccord = noticeAccord;
	}


	public String getPrimaryKey() {
		return primaryKey;
	}


	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}


	public String getAsstd_Channelnum() {
		return asstd_Channelnum;
	}


	public void setAsstd_Channelnum(String asstdChannelnum) {
		asstd_Channelnum = asstdChannelnum;
	}


	public int getAsstd_Comunicate_Type() {
		return asstd_Comunicate_Type;
	}


	public void setAsstd_Comunicate_Type(int asstdComunicateType) {
		asstd_Comunicate_Type = asstdComunicateType;
	}


	public Date getAsstd_LastDate() {
		return asstd_LastDate;
	}


	public void setAsstd_LastDate(Date asstdLastDate) {
		asstd_LastDate = asstdLastDate;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
}
