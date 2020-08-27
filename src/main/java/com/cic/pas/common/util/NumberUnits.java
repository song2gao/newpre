package com.cic.pas.common.util;

import java.math.BigDecimal;

public class NumberUnits {
	public static double twoDoubleValue(double d) {
		return new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
	}
	/**
	 * @Title: clearNoUseZeroForBigDecimal
	 * @Description: 去掉BigDecimal尾部多余的0，通过stripTrailingZeros().toPlainString()实现
	 * @param num
	 * @return BigDecimal
	 */
	public static BigDecimal clearNoUseZeroForBigDecimal(BigDecimal num) {
		BigDecimal returnNum = null;
		String numStr = num.stripTrailingZeros().toPlainString();
		if (numStr.indexOf(".") == -1) {
			// 如果num 不含有小数点,使用stripTrailingZeros()处理时,变成了科学计数法
			returnNum = new BigDecimal(numStr);
		} else {
			if (num.compareTo(BigDecimal.ZERO) == 0) {
				returnNum = BigDecimal.ZERO;
			} else {
				returnNum = num.stripTrailingZeros();
			}
		}
		return returnNum;
	}
}
