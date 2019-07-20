package com.cic.pas.common.util;

import java.util.UUID;

/**
 * UUID工具类
 * 
 * @author Eric
 */
public class UuidUtils {

	/**
	 * 生成UUID
	 * 
	 * @return UUID
	 */
	public static String randomUUID() {

		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}
}