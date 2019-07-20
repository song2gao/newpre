package com.cic.pas.common.bean;

import java.math.BigDecimal;

/**
 * @author: gaosong
 * @date:2017-12-9 下午06:24:14
 * @version :1.0.0
 * 
 */
public class CoolHotData {
	/**
	 * 表计编码
	 */
	private String code;
	/**
	 * 日期
	 */
	private String dateCode;
	/**
	 * 时间
	 */
	private String timeCode;
	/**
	 * 累计热量
	 */
	private BigDecimal hotValue;
	/**
	 * 热量单位 
	 */
	private String hotUnit;
	/**
	 * 累计冷量
	 */
	private BigDecimal coolValue;
	/**
	 * 冷量单位
	 */
	private String coolUnit;
	/**
	 * 热累计流量 
	 */
	private BigDecimal volumeHot;
	/**
	 * 冷累计流量
	 */
	private BigDecimal volumeCool;
	/**
	 * 瞬时流速
	 */
	private BigDecimal speed;
	/**
	 * 供水温度
	 */
	private BigDecimal flTemp;
	/**
	 * 回水温度
	 */
	private BigDecimal retTemp;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDateCode() {
		return dateCode;
	}
	public void setDateCode(String dateCode) {
		this.dateCode = dateCode;
	}
	public String getTimeCode() {
		return timeCode;
	}
	public void setTimeCode(String timeCode) {
		this.timeCode = timeCode;
	}
	public BigDecimal getHotValue() {
		return hotValue;
	}
	public void setHotValue(BigDecimal hotValue) {
		this.hotValue = hotValue;
	}
	public String getHotUnit() {
		return hotUnit;
	}
	public void setHotUnit(String hotUnit) {
		this.hotUnit = hotUnit;
	}
	public BigDecimal getCoolValue() {
		return coolValue;
	}
	public void setCoolValue(BigDecimal coolValue) {
		this.coolValue = coolValue;
	}
	public String getCoolUnit() {
		return coolUnit;
	}
	public void setCoolUnit(String coolUnit) {
		this.coolUnit = coolUnit;
	}
	public BigDecimal getVolumeHot() {
		return volumeHot;
	}
	public void setVolumeHot(BigDecimal volumeHot) {
		this.volumeHot = volumeHot;
	}
	public BigDecimal getVolumeCool() {
		return volumeCool;
	}
	public void setVolumeCool(BigDecimal volumeCool) {
		this.volumeCool = volumeCool;
	}
	public BigDecimal getSpeed() {
		return speed;
	}
	public void setSpeed(BigDecimal speed) {
		this.speed = speed;
	}
	public BigDecimal getFlTemp() {
		return flTemp;
	}
	public void setFlTemp(BigDecimal flTemp) {
		this.flTemp = flTemp;
	}
	public BigDecimal getRetTemp() {
		return retTemp;
	}
	public void setRetTemp(BigDecimal retTemp) {
		this.retTemp = retTemp;
	}
}
