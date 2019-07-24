package com.cic.domain;

public class EsmspCalSumRule {
	private String ctd_code;
	
	private String ctd_name;
	
	private String value_plus;
	
	private String value_minus;

	public String getCtd_code() {
		return ctd_code;
	}

	public void setCtd_code(String ctdCode) {
		ctd_code = ctdCode;
	}

	public String getCtd_name() {
		return ctd_name;
	}

	public void setCtd_name(String ctdName) {
		ctd_name = ctdName;
	}

	public String getValue_plus() {
		return value_plus;
	}

	public void setValue_plus(String valuePlus) {
		value_plus = valuePlus;
	}

	public String getValue_minus() {
		return value_minus;
	}

	public void setValue_minus(String valueMinus) {
		value_minus = valueMinus;
	}
}
