package com.cic.httpserver;

public class RequestData {
	
	private String bsn;//网关序列号  唯一标识
	
	private String bnm;//网关名称
	
	private String tnm;//监测点名称
	
	private String tds;//监测点描述
	
	private String hid;
	
	private String ts;//时间
	
	private int vt;//数据类型  1 整型  2 浮点 3字符串
	
	private String val;//数值
	
	private String vq;// 0 good 1 bad

	public String getBsn() {
		return bsn;
	}

	public void setBsn(String bsn) {
		this.bsn = bsn;
	}

	public String getBnm() {
		return bnm;
	}

	public void setBnm(String bnm) {
		this.bnm = bnm;
	}

	public String getTnm() {
		return tnm;
	}

	public void setTnm(String tnm) {
		this.tnm = tnm;
	}

	public String getTds() {
		return tds;
	}

	public void setTds(String tds) {
		this.tds = tds;
	}

	public String getHid() {
		return hid;
	}

	public void setHid(String hid) {
		this.hid = hid;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public int getVt() {
		return vt;
	}

	public void setVt(int vt) {
		this.vt = vt;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getVq() {
		return vq;
	}

	public void setVq(String vq) {
		this.vq = vq;
	}
	
	

}
