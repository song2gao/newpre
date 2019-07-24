package com.cic.pas.common.bean;

import java.sql.Timestamp;

public class PointData {
	
	private int id;
	
	private String ctd_code;
	
	private String mmp_code;
	
	private Timestamp date_code;
	
	private double value;
	
	private double value1;
	
	private String note;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCtd_code() {
		return ctd_code;
	}

	public void setCtd_code(String ctdCode) {
		ctd_code = ctdCode;
	}

	public String getMmp_code() {
		return mmp_code;
	}

	public void setMmp_code(String mmpCode) {
		mmp_code = mmpCode;
	}

	public Timestamp getDate_code() {
		return date_code;
	}

	public void setDate_code(Timestamp dateCode) {
		date_code = dateCode;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getValue1() {
		return value1;
	}

	public void setValue1(double value1) {
		this.value1 = value1;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
