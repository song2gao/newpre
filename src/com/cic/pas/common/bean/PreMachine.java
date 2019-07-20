package com.cic.pas.common.bean;

import java.io.Serializable;
import java.util.Date;

import com.cic.pas.service.ServerContext;

public class PreMachine implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4712646030668036942L;
	
	private Double usedPercent;
	private Double cpuPerc;
	private String runtime;
	private Double netPerc;
	
	public PreMachine(){}
	
	public PreMachine(Double usedPercent,Double cpuPerc,Date date,Double netPerc){
		this.usedPercent = usedPercent ;
		this.cpuPerc = cpuPerc;
		long temp =	date.getTime() - ServerContext.runtime.getTime();
		this.runtime = getRumtimeByString(temp);
		this.netPerc = netPerc ;
	}
	
	public Double getUsedPercent() {
		return usedPercent;
	}
	public void setUsedPercent(Double usedPercent) {
		this.usedPercent = usedPercent;
	}
	public Double getCpuPerc() {
		return cpuPerc;
	}
	public void setCpuPerc(Double cpuPerc) {
		this.cpuPerc = cpuPerc;
	}
	public String getRuntime() {
		return runtime;
	}
	public void setRuntime(Date date) {
		long temp =	date.getTime() - ServerContext.runtime.getTime();
		this.runtime = getRumtimeByString(temp);
	}
	public Double getNetPerc() {
		return netPerc;
	}
	public void setNetPerc(Double netPerc) {
		this.netPerc = netPerc;
	}
			public static final String getRumtimeByString(long timeInMillis) {
				/*long daily = timeInMillis/86400000l ;
				long hours = 0;
				long minutes = 0;
				long seconds = 0;
				if(daily != 0l){
					long temp = timeInMillis%86400000l;
					hours = temp/3600000l;
					if(hours != 0l){
						long temp2 = temp%3600000l;
						minutes = temp2/60000l;
						if(minutes!=0l){
							long temp3 = temp2%60000l;
							seconds = temp3/1000l;
						}
					}
				}
				return daily+"天 "+hours+"小时 "+minutes+"分钟 "+seconds+"秒";*/
				
				long daily = timeInMillis/ (24 * 60 * 60 * 1000); 
				long hours = (timeInMillis/ (60 * 60 * 1000) - daily * 24); 
				long minutes = ((timeInMillis / (60 * 1000)) - daily * 24 * 60 - hours * 60); 
				long seconds = (timeInMillis/1000-daily*24*60*60-hours*60*60-minutes*60); 
				return daily+"天 "+hours+"小时 "+minutes+"分钟 "+seconds+"秒";
			}	
	}