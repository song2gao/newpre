package com.cic.pas.common.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Configer {
	
	private Configer(){}
	
	private static Configer config = new Configer();
	
	private static Map<String, String> configs = config.analysis();
	
	/**
	 * 获取Configer实例对象
	 * @return Configer实例对象
	 */
	public static Configer getInstance(){
		return config;
	}
	
	/**
	 * 根据key获取value
	 * @param key
	 * @return value，如配置中不存在该key，则返回null
	 */
	public String get(String key){
		
		return configs.get(key);
	}
	
	private Map<String, String> analysis(){
		
		Map<String, String> map = new HashMap<String, String>(0);
		
		SAXReader reader = new SAXReader();
		Document document = null;
		
		try {
			document = reader.read(this.getClass().getResource("/config.xml"));
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}
		
		Element root = document.getRootElement();
		for (Iterator<Element> i = root.elementIterator(); i.hasNext(); ) {
		       Element element = i.next();
		       map.put(element.attributeValue("key"), 
		    		   element.attributeValue("value"));
		}
		return map;
	}
}
