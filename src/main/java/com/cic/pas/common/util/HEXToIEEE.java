package com.cic.pas.common.util;

public class HEXToIEEE {

	private static String[] hexs = new String[] { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	private static String[] bins = new String[] { "0000", "0001", "0010",
			"0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010",
			"1011", "1100", "1101", "1110", "1111" };

	// 返回十六进制数的二进制形式
	private static String getBin(String hex) {
		int i;
		for (i = 0; i < hexs.length && !hex.toLowerCase().equals(hexs[i]); i++)
			;
		return bins[i];
	}

	// 这里只处理规格化数，非规格化数，NaN，finite等没有考虑
	static float binaryStringToFloat(final String binaryString) {
		// float是32位，将这个binaryString左边补0补足32位，如果是Double补足64位。
		final String stringValue = leftPad(binaryString, '0', 32);

		// 首位是符号部分，占1位。
		// 如果符号位是0则代表正数，1代表负数
		final int sign = stringValue.charAt(0) == '0' ? 1 : -1;

		// 第2到9位是指数部分，float占8位，double占11位。
		final String exponentStr = stringValue.substring(1, 9);

		// 将这个二进制字符串转成整数，由于指数部分加了偏移量（float偏移量是127，double是1023）
		// 所以实际值要减去127
		final int exponent = Integer.parseInt(exponentStr, 2) - 127;

		// 最后的23位是尾数部分，由于规格化数，小数点左边隐含一个1，现在加上
		final String mantissaStr = "1".concat(stringValue.substring(9, 32));
		// 这里用double，尽量保持精度，最好用BigDecimal，这里只是方便计算所以用double
		double mantissa = 0.0;

		for (int i = 0; i < mantissaStr.length(); i++) {
			final int intValue = Character.getNumericValue(mantissaStr
					.charAt(i));
			// 计算小数部分，具体请查阅二进制小数转10进制数相关资料
			mantissa += (intValue * Math.pow(2, -i));
		}
		// 根据IEEE 754 标准计算：符号位 * 2的指数次方 * 尾数部分
		return (float) (sign * Math.pow(2, exponent) * mantissa);
	}

	// 一个简单的补齐方法，很简单，没考虑很周到。具体请参考common-long.jar/StringUtils.leftPad()
	static String leftPad(final String str, final char padChar, int length) {
		final int repeat = length - str.length();

		if (repeat <= 0) {
			return str;
		}

		final char[] buf = new char[repeat];
		for (int i = 0; i < buf.length; i++) {
			buf[i] = padChar;
		}
		return new String(buf).concat(str);
	}

	/**
	 * @desc 位序颠倒
	 * @param source
	 * @return
	 */
	static String reverse8BitsHexString(final String source) {
		final int length = source.length();

		final char[] result = new char[length];

		for (int i = length - 1; i > 0; i = i - 2) {
			result[length - i - 1] = source.charAt(i - 1);
			result[length - i] = source.charAt(i);
		}

		return new String(result);
	}
	 public static float getFloat(String str) {
		 
	        /* 用十六进制的"C1C8FDF3"，转成一个十进制整数(这个十进制数并不是浮点数)： */
		// String str = "0B22D0000";
	        StringBuffer buff = new StringBuffer();
	        int i;
	        for (i = 0; i < str.length(); i++) {
	            buff.append(getBin(str.substring(i, i + 1)));
	        }
	        /* 然后用这个十进制整数得到一个二进制字符串： */
	        // 结果就是：11000001110010001111110111110011
	        String binaryString = buff.toString();
	        return binaryStringToFloat(binaryString);
	    }
	 public static void main(String[] args){
		 /* 用十六进制的"C1C8FDF3"，转成一个十进制整数(这个十进制数并不是浮点数)： */
		 		String str = "43960000";
		        StringBuffer buff = new StringBuffer();
		        int i;
		        for (i = 0; i < str.length(); i++) {
		            buff.append(getBin(str.substring(i, i + 1)));
		        }
		        /* 然后用这个十进制整数得到一个二进制字符串： */
		        // 结果就是：11000001110010001111110111110011
		        String binaryString = buff.toString();
		        System.out.println(binaryString);
		        System.out.println(binaryStringToFloat(binaryString));
		 
	 }
}
