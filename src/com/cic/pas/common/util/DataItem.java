package com.cic.pas.common.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class DataItem {

	/**
	 * 时间标签
	 */
	private Date date;
	/**
	 * 终端号Pn
	 */
	private int group = -1;
	/**
	 * 计量设备号
	 */
	private Map<Integer, Map<Integer, String>> parameters;

	public DataItem() {
		parameters = new HashMap<Integer, Map<Integer, String>>();
	}

	public void addParameters2(int portID, int key, String value)throws Exception {
		if (group == -1) {
			throw new Exception("Item还没有设置group属性,请先调用setGroup()方法在调用此方法!");
		} else {
			Map<Integer, String> temMap = parameters.get(portID);
			temMap.put(key, value);
		}
	}
	/**
	 * 保存计量表测点值
	 * @param portID 计量表
	 * @param key 测点编码
	 * @param value 测点值
	 * @throws Exception
	 */
	public void addParameters(int portID, int key, String value)throws Exception {
		if (group == -1) {
			throw new Exception("Item还没有设置group属性,请先调用setGroup()方法在调用此方法!");
		} else {
				Map<Integer, String> temMap = parameters.get(portID);
				if(temMap==null) return ;
				temMap.put(key, value);
		}
	}
	
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getGroup() {
		return this.group;
	}

	public Map<Integer, Map<Integer, String>> getParameters() {
		return this.parameters;
	}

	public void setParameters(Map<Integer, Map<Integer, String>> parameters) {
		this.parameters = parameters;
	}

	public String getParameters(int number, Integer key) {
		Map<Integer, String> temMap = null;
		if (parameters.get(number) != null) {
			temMap = parameters.get(number);
			return temMap.get(key);
		}
		return null;
	}
	
/*	public String toString() {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("时间标签:" + date + ";"+System.getProperty("line.separator"));
		sBuffer.append("终端号:" + group + ";"+System.getProperty("line.separator"));
		for (Integer temp : parameters.keySet()) {
			sBuffer.append("设备号:" + temp);
			sBuffer.append("(");
			Map<Integer, String> vaMap = parameters.get(temp);
			for (Integer str : vaMap.keySet()) {
				sBuffer.append(str + ":" + (String) vaMap.get(str) + ":");
			}
			sBuffer.append(")");
		}
		return sBuffer.toString();
	}
	
	public String toString2(){
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("日期:"+date+System.getProperty("line.separator"));
		sBuffer.append("终端号:"+group+System.getProperty("line.separator"));
		for (Integer portNumber : parameters.keySet()) {
			sBuffer.append("设备号:" + portNumber);
			Map<Integer, String> vaMap = parameters.get(portNumber);
			sBuffer.append("("+vaMap.size()+")"+System.getProperty("line.separator"));	
		}
		return sBuffer.toString();
	}*/

	/**
	 * 新建表计终端MAP
	 * @param group 采集器
	 * @param portId 计量表
	 * @throws Exception
	 */
	public void setGroup(int group,int portId) throws Exception {
			Map<Integer, String> portDataMap = new HashMap<Integer, String>();
			parameters.put(portId, portDataMap);
			this.group = group;
	}
	
	@Override
	public boolean equals(Object obj) {
		DataItem item = (DataItem) obj;
		return date.equals(item.getDate()) && group == item.getGroup();
	}

	@Override
	public int hashCode() {
		return (int) date.getTime() + group * 16 + 1;
	}

}
