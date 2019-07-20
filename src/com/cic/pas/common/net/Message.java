package com.cic.pas.common.net;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.PointDevice;
//参数 
public class Message implements Serializable{

	private static final long serialVersionUID = 7719755457410213403L;
	
	Command c;
	
	String meterId;
	
	String meterCode;
	
	String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	MeterDevice md;
	
	public MeterDevice getMd() {
		return md;
	}

	public void setMd(MeterDevice md) {
		this.md = md;
	}

	Map<String, String[]> map;
	
	public Map<String, String[]> getMap() {
		return map;
	}

	public void setMap(Map<String, String[]> map) {
		this.map = map;
	}

	public String getMeterCode() {
		return meterCode;
	}

	public void setMeterCode(String meterCode) {
		this.meterCode = meterCode;
	}

	public String getPointCode() {
		return pointCode;
	}

	public void setPointCode(String pointCode) {
		this.pointCode = pointCode;
	}

	String pointCode;
	
	int collect;
	
	Date startDate;
	
	Date endDate;
	
	int m ;
	
	int n ;
	
	int type ;//补数类型  1 2 3
	
	String scalePointName;

	Map<String,Object> configMap;
	
	public Command getC() {
		return c;
	}
	public Map<String, Object> getConfigMap() {
		return configMap;
	}

	public void setConfigMap(Map<String, Object> configMap) {
		this.configMap = configMap;
	}
	public void setC(Command c) {
		this.c = c;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}


	public int getCollect() {
		return collect;
	}

	public void setCollect(int collect) {
		this.collect = collect;
	}

	public String getMeterId() {
		return meterId;
	}

	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getScalePointName() {
		return scalePointName;
	}

	public void setScalePointName(String scalePointName) {
		this.scalePointName = scalePointName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
