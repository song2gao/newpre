package com.cic.pas.common.util;

import java.math.BigDecimal;

public class NumberUnits {
	public static double twoDoubleValue(double d) {
		return new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
	}
}
