package com.cic.pas.common.classLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadJarFile {

	private List<String> jarNames = new ArrayList<String>();
	private List<String> jarUrls = new ArrayList<String>();

	public List<String> getJarNames() {
		return jarNames;
	}

	public List<String> getJarUrls() {
		return jarUrls;
	}

	/**
	 * 读取指定文件夹的文件
	 * @param jarFileName 路径
	 * @param strings 后缀
	 */
	public ReadJarFile(String jarFileName, String[] strings) {
		File f = new File(jarFileName);
		File[] fl = f.listFiles();
		for (File file : fl) {
			for (String str : strings) {
				if (file.getName().endsWith(str)) {
					jarNames.add(file.getName());
					jarUrls.add(file.toURI().toString());
				}
			}
		}
	}
}
