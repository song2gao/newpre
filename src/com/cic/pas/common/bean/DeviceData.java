package com.cic.pas.common.bean;

import java.util.List;

public class DeviceData {
	
	private String collectorID;
	
	private List<Data> data;

	public String getCollectorID() {
		return collectorID;
	}

	public void setCollectorID(String collectorID) {
		this.collectorID = collectorID;
	}

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

}
