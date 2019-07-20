package com.cic.pas.common.util;

/**
 * 字符串工具类
 * 
 * @author Eric
 */
public class StringUtils {

	/**
	 * ""
	 */
	public static final String EMPTY = "";

	/**
	 * 字符串为null 或 空字符串-true 否则-false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str) {

		return str == null || EMPTY.equals(str.trim()) ? true : false;
	}

	/**
	 * 字符串为null-true 否则-false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {

		return str == null ? true : false;
	}

	/**
	 * 字符串去两边空格
	 * 
	 * @param str
	 * @return
	 */
	public static String trim(String str) {

		return isNull(str) ? str : str.trim();
	}

	/**
	 * 字符串大写
	 * 
	 * @param str
	 * @return
	 */
	public static String toUpperCase(String str) {

		return isNullOrEmpty(str) ? str : str.trim().toUpperCase();
	}

	/**
	 * 字符串小写
	 * 
	 * @param str
	 * @return
	 */
	public static String toLowerCase(String str) {

		return isNullOrEmpty(str) ? str : str.trim().toLowerCase();
	}

	/**
	 * 分割字符串
	 * 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static String[] split(String str, String regex) {

		return isNull(str) ? null : str.split(regex);
	}

	/**
	 * Pascal命名法则：所有单词首字母大写，其他字母小写
	 * 
	 * @param str
	 * @return
	 */
	public static String toPascal(String str) {

		if (isNullOrEmpty(str))
			return str;

		String s = str.substring(0, 1).toUpperCase();
		if (str.length() > 1) {
			s += str.substring(1);
		}
		return s;
	}

	/**
	 * camel命名法则：第一个单词首字母大写，其他单词首字母小写，除首字母外其他字母小写
	 * 
	 * @param str
	 * @return
	 */
	public static String toCamel(String str) {

		if (isNullOrEmpty(str))
			return str;
		String s = str.substring(0, 1).toLowerCase();
		if (str.length() > 1) {
			s += str.substring(1);
		}
		return s;
	}

	/**
	 * 字节字符串反转  7856==>5678
	 * @param str
	 * @return
	 */
	public static String toSwapStr(String str){
	    if(str.length()%2!=0){
	        str='0'+str;
        }
		char[] ctdCodes=str.toCharArray();
		String ctdCode="";
		for(int i=ctdCodes.length-1;i>-1;i-=2){
			ctdCode+=ctdCodes[i-1]+""+ctdCodes[i];
		}
		return ctdCode;
	}

	/**
	 * 645协议 原码加上  33 33 33 33
	 * @param str
	 * @return
	 */
	public static String toPlus645(String str){
		String result="";
		for(int i=0;i<=str.length()-2;i+=2){
			String subStr=str.substring(i,i+2);
			String res=Integer.toHexString(Integer.parseInt(subStr,16)+Integer.parseInt("33",16));
			result+=StringUtils.leftPad(res,2,'0');
		}
		return result;
	}
    /**
     * 645协议 原码减去  33 33 33 33
     * @param str
     * @return
     */
    public static String toSubtract645(String str){
        String result="";
        for(int i=0;i<=str.length()-2;i+=2){
            String subStr=str.substring(i,i+2);
            String res=Integer.toHexString(Integer.parseInt(subStr,16)-Integer.parseInt("33",16));
            result+=StringUtils.leftPad(res,2,'0');
        }
        return result;
    }
	/**
	 * 字符串左边补字符
	 * @param str 字符串
	 * @param size 总长度
	 * @param padChar 字符
	 * @return
	 */
	public static String leftPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		} else {
			for(int i=str.length();i<size;i++){
				str=padChar+str;
			}
			return str;
		}
	}

	/**
	 * 计算累加和
	 * @param str 16进制字符串
	 * @return 累加和
	 */
	public static String checkCs(String str){
		if (str == null || str.equals("")) {
			return "";
		}
		int total = 0;
		int len = str.length();
		int num = 0;
		while (num < len) {
			String s = str.substring(num, num + 2);
			total += Integer.parseInt(s, 16);
			num = num + 2;
		}
		/**
		 * 用256求余最大是255，即16进制的FF
		 */
		int mod = total % 256;
		String hex = Integer.toHexString(mod);
		len = hex.length();
		// 如果不够校验位的长度，补0,这里用的是两位校验
		if (len < 2) {
			hex = "0" + hex;
		}
		return hex.toUpperCase();
	}
	 /* 计算累加和
	 * @param str 16进制字符串
	 * @return 累加和
	 */
	public static byte checkBytesCs(byte[] bytes,int end){
		int total = 0;
		for(int i=0;i<end;i++){
			total+=(int)bytes[i];
		}
		/**
		 * 用256求余最大是255，即16进制的FF
		 */
		int mod = total % 256;

		return (byte)total;
	}
	public static void main(String[] args){
		System.out.println(checkCs("68 32 00 32 00 68 0B 02 37 02 00 00 00 61 00 00 01 00".replaceAll(" ","")));
	}
}
