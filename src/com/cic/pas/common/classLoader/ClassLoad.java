package com.cic.pas.common.classLoader;

import java.net.URLClassLoader;

import com.cic.pas.common.config.Configer;

public class ClassLoad {
	
	/**
	 * 按照默认目录获得URLClassLoader，默认目录在config.xml文件中配置，key：defaultLoadClassPath
	 * 默认目录为文件夹
	 * @return
	 */
	public static URLClassLoader getURLClassLoader(){
		return getURLClassLoader(Configer.getInstance().get("defaultLoadClassPath"), false);
	}
	
	/**
	 * 按照默认目录获得URLClassLoader，默认目录在config.xml文件中配置，key：defaultLoadClassPath
	 * @param isFile true-文件 false-文件夹
	 * @return
	 */
	public static URLClassLoader getURLClassLoader(boolean isFile){
		return getURLClassLoader(Configer.getInstance().get("defaultLoadClassPath"), isFile);
	}
	
	/**
	 * 获得URLClassLoader
	 * 默认目录为文件夹
	 * @param url URL 目录
	 * @return
	 */
	public static URLClassLoader getURLClassLoader(String url) {
		return getURLClassLoader(url, false);
	}
	
	/**
	 * 获得URLClassLoader
	 * @param url 目录
	 * @param isFile true-文件 false-文件夹
	 * @return
	 */
	public static URLClassLoader getURLClassLoader(String url, boolean isFile){
		URLClassLoaderUtil classLoaderUtil = new URLClassLoaderUtil(url, isFile);
		return classLoaderUtil.getClassLoader();
	}
}
