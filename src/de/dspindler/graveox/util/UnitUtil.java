package de.dspindler.graveox.util;

public class UnitUtil
{
	private static String[]			units = new String[]{
			"a",	// 10^-18
			"f",	// 10^-15
			"p",	// 10^-12
			"n",	// 10^-9
			"µ",	// 10^-6
			"m",	// 10^-3
			"",		// 10^0
			"k",	// 10^3
			"M",	// 10^6
			"G",	// 10^9
			"T",	// 10^12
			"P",	// 10^15
			"E"		// 10^18
	};
	
	public static String toUnitString(double value)
	{
		return UnitUtil.toUnitString(value, 3);
	}
	
	public static String toUnitString(double value, int decimals)
	{
		int exp = (int) Math.log10(value);
		int index = Math.min(Math.max(exp, -18), 18) / 3 + 6;
		return String.format("%." + decimals + "f%s", value * Math.pow(1000.0d, -index + 6), units[index]);
	}
}
