package com.cic.domain;

import java.math.BigDecimal;

public class EsmspSumMonth {
	private String dateCode;
	
	private String eusCode;
	
	private String eusName;
	
	private BigDecimal lastValue;
	
	private BigDecimal currentValue;
	
	private BigDecimal value;
	
	private BigDecimal valueChange;
	
	private int codeOrder;

	public String getDateCode() {
		return dateCode;
	}

	public void setDateCode(String dateCode) {
		this.dateCode = dateCode;
	}

	public String getEusCode() {
		return eusCode;
	}

	public void setEusCode(String eusCode) {
		this.eusCode = eusCode;
	}

	public String getEusName() {
		return eusName;
	}

	public void setEusName(String eusName) {
		this.eusName = eusName;
	}

	public BigDecimal getLastValue() {
		return lastValue;
	}

	public void setLastValue(BigDecimal lastValue) {
		this.lastValue = lastValue;
	}

	public BigDecimal getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(BigDecimal currentValue) {
		this.currentValue = currentValue;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getValueChange() {
		return valueChange;
	}

	public void setValueChange(BigDecimal valueChange) {
		this.valueChange = valueChange;
	}

	public int getCodeOrder() {
		return codeOrder;
	}

	public void setCodeOrder(int codeOrder) {
		this.codeOrder = codeOrder;
	}
	
	

}	
