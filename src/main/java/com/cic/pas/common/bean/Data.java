package com.cic.pas.common.bean;

public class Data {
	
	private String buildID;//建筑ID
	
	private String collectorID;//采集器ID
	
	private String deviceTypeID;//设备类型
	
	private String deviceID;//设备ID
	
	private String devicePosition;//设备位置
	
	private String deviceName;//设备名称
	
	private String deviceAttributesID;//设备参数ID
	
	private String deviceAttributesName;//设备参数名称
	
	private String deviceAttributesUnit;//参数单位
	
	private String collectData;//采集值
	
	private String collectStatusData;//采集状态值
	
	private String collectTime;//采集时间

	public String getBuildID() {
		return buildID;
	}

	public void setBuildID(String buildID) {
		this.buildID = buildID;
	}

	public String getCollectorID() {
		return collectorID;
	}

	public void setCollectorID(String collectorID) {
		this.collectorID = collectorID;
	}

	public String getDeviceTypeID() {
		return deviceTypeID;
	}

	public void setDeviceTypeID(String deviceTypeID) {
		this.deviceTypeID = deviceTypeID;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getDevicePosition() {
		return devicePosition;
	}

	public void setDevicePosition(String devicePosition) {
		this.devicePosition = devicePosition;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceAttributesID() {
		return deviceAttributesID;
	}

	public void setDeviceAttributesID(String deviceAttributesID) {
		this.deviceAttributesID = deviceAttributesID;
	}

	public String getDeviceAttributesName() {
		return deviceAttributesName;
	}

	public void setDeviceAttributesName(String deviceAttributesName) {
		this.deviceAttributesName = deviceAttributesName;
	}

	public String getDeviceAttributesUnit() {
		return deviceAttributesUnit;
	}

	public void setDeviceAttributesUnit(String deviceAttributesUnit) {
		this.deviceAttributesUnit = deviceAttributesUnit;
	}

	public String getCollectData() {
		return collectData;
	}

	public void setCollectData(String collectData) {
		this.collectData = collectData;
	}

	public String getCollectStatusData() {
		return collectStatusData;
	}

	public void setCollectStatusData(String collectStatusData) {
		this.collectStatusData = collectStatusData;
	}

	public String getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
	}

}
