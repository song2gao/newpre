package com.cic.pas.common.classLoader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class URLClassLoaderUtil {

	private URLClassLoader classLoader = null;
	private String jarFileName;
	private boolean isFile = true;

	public String getJarFileName() {
		return jarFileName;
	}

	public void setJarFileName(String jarFileName) {
		this.jarFileName = jarFileName;
	}

	public boolean isFile() {
		return isFile;
	}

	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}

	public URLClassLoader getClassLoader() {
		return classLoader;
	}

	public void setClassLoader(URLClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	/**
	 * 加载具体的某一jar包
	 * @param jarFileName
	 */
	public URLClassLoaderUtil(String jarFileName) {
		this.setJarFileName(jarFileName);
		this.init();
	}

	/**
	 * 加载jar包 当isFile为false是加载文件夹下所有jar
	 * @param jarFileName 路径
	 * @param isFile
	 */
	public URLClassLoaderUtil(String jarFileName, boolean isFile) {
		this.setJarFileName(jarFileName);
		this.setFile(isFile);
		this.init();
	}

	/**
	 *初始化，读取文件信息，并将jar文件路径加入到CLASSPATH
	 */
	private void init() {
		if (this.isFile) {
			File file = new File(jarFileName);
			addPath(file.toURI().toString());
		} else {
			ReadJarFile readJarFile = new ReadJarFile(jarFileName, new String[] { "jar", "zip" });
			addPath(readJarFile.getJarUrls().toArray());
		}
	}

	/**
	 *添加单个jar路径到CLASSPATH
	 *@paramjarURL
	 */
	private void addPath(String jarURL) {
		try {
			classLoader = new URLClassLoader(new URL[] { new URL(jarURL) });
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加jar路径数组到CLASSPATH
	 * @param jarUrls
	 */
	private void addPath(Object[] jarUrls) {
		URL[] urls = new URL[jarUrls.length];
		for (int i = 0; i < jarUrls.length; i++) {
			try {
				urls[i] = new URL(jarUrls[i].toString());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		classLoader = new URLClassLoader(urls);
	}
}
