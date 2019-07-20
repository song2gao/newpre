package com.cic.pas.thread;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

import com.cic.pas.common.bean.*;
import com.cic.pas.common.util.NumberUnits;
import com.cic.pas.dao.DBConfigDao;
import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.cic.httpclient.HttpRequester;
import com.cic.httpclient.HttpRespons;
import com.cic.pas.dao.BussinessConfig;

public class DataCenterThread extends Thread {


	private Logger logger = Logger.getLogger(DataCenterThread.class);
	
	public void run() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		while (true) {
			List<DeviceData> deviceDatas=getData();
			for(DeviceData deviceData:deviceDatas) {
				//logger.info("<==============开始上传数据采集器ID"+deviceData.getCollectorID()+"================"+format.format(new Date())+">");
				JSONObject json = new JSONObject();
				json.put("1", deviceData);
				HttpRequester requester = new HttpRequester();
				Map<String, String> parameters = new HashMap<String, String>();
				String deviceDataJson = json.getString("1");
				parameters.put("deviceDataJson", deviceDataJson);
				Map<String, String> properties = new HashMap<String, String>();
				properties.put("Content-Type", "application/json;charset=UTF-8");
				String dateTime = format.format(new Date());
				try {
					String[] urls = DBConfigDao.serverConfigPro.getProperty("dataCenterUrls").split(",");
					for (String url : urls) {
						HttpRespons respons = requester.send(url, "POST", parameters,
								properties);
						if (respons.getCode() == 200) {
                            //logger.info("<==============数据上传服务器" + url + "成功采集器ID"+deviceData.getCollectorID()+"================" + dateTime + ">");
						} else {
                            logger.info("<==============数据上传服务器" + url + "失败采集器ID"+deviceData.getCollectorID() + respons.getContent() + "================" + dateTime + ">");
							failUploadHandle(dateTime, deviceDataJson);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(12000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private List<DeviceData> getData() {
		Map<String,List<Data>> map=new HashMap<String,List<Data>>();
		for (SystemParams params : BussinessConfig.systemParams) {
					Data data = new Data();
					data.setBuildID(params.getEulId());
					data.setCollectorID(params.getEulId());
					data.setDeviceTypeID("");
					data.setDeviceID(params.getEusCode());
					data.setDevicePosition(params.getEulName());
					data.setDeviceName(params.getEusName());
					data.setDeviceAttributesID(params.getParamCode());
					data.setDeviceAttributesName(params.getParamName());
					data.setDeviceAttributesUnit(params.getParamUnit());
					if (params.getParamType() == 1) {
						int value = params.getParamValue().intValue();
						data.setCollectStatusData(value + "");
						data.setCollectData("");
					} else {
						data.setCollectData(NumberUnits.twoDoubleValue(params.getParamValue().doubleValue()) + "");
						data.setCollectStatusData("");
					}
					data.setCollectTime(params.getTime());
					if(map.containsKey(params.getEulId())){
						map.get(params.getEulId()).add(data);
					}else{
						List<Data> list=new ArrayList<Data>();
						list.add(data);
						map.put(params.getEulId(),list);
					}
		}
		List<DeviceData> deviceDatas=new ArrayList<DeviceData>();
		for(String k:map.keySet()) {
			DeviceData deviceData = new DeviceData();
			deviceData.setCollectorID(k);
			deviceData.setData(map.get(k));
			deviceDatas.add(deviceData);
		}
		return deviceDatas;
	}
	public void failUploadHandle(String dateTime,String dataStr){
		BrokenData brokenData=new BrokenData();
		brokenData.setDateTime(dateTime);
		brokenData.setDataStr(dataStr);
		BussinessConfig.config.brokenDataList.add(brokenData);
	}
}
