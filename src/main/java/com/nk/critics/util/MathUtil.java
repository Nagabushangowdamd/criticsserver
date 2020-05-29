package com.nk.critics.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {
	
	public static double doublePrecision(double value,int scale)
	{
		return BigDecimal.valueOf(value)
			    .setScale(scale, RoundingMode.HALF_UP).doubleValue();
		
	}

}
