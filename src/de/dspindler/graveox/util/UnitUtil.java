package de.dspindler.graveox.util;

public class UnitUtil
{
	private static String[]			prefixes = new String[]{
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
	
	private static String[]			lengthUnits = new String[]{
			"m",	// meter
			"AU",	// astronomical unit
			"ly",	// light year
			"pc"	// parsec
	};
	
	private static double[][]		prefixConversionTable = new double[][]{
		//    to:     a        f        p        n        µ         m         1         k         M         G         T         P         E
		/* from a */ {1.0d,    1.0e-3d, 1.0e-6d, 1.0e-9d, 1.0e-12d, 1.0e-15d, 1.0e-18d, 1.0e-21d, 1.0e-24d, 1.0e-27d, 1.0e-30d, 1.0e-33d, 1.0e-36d},
		/* from f */ {1.0e3d,  1.0d,    1.0e-3d, 1.0e-6d, 1.0e-9d,  1.0e-12d, 1.0e-15d, 1.0e-18d, 1.0e-21d, 1.0e-24d, 1.0e-27d, 1.0e-30d, 1.0e-33d},
		/* from p */ {1.0e6d,  1.0e3d,  1.0d,    1.0e-3d, 1.0e-6d,  1.0e-9d,  1.0e-12d, 1.0e-15d, 1.0e-18d, 1.0e-21d, 1.0e-24d, 1.0e-27d, 1.0e-30d},
		/* from n */ {1.0e9d,  1.0e6d,  1.0e3d,  1.0d,    1.0e-3d,  1.0e-6d,  1.0e-9d,  1.0e-12d, 1.0e-15d, 1.0e-18d, 1.0e-21d, 1.0e-24d, 1.0e-27d},
		/* from µ */ {1.0e12d, 1.0e9d,  1.0e6d,  1.0e3d,  1.0d,     1.0e-3d,  1.0e-6d,  1.0e-9d,  1.0e-12d, 1.0e-15d, 1.0e-18d, 1.0e-21d, 1.0e-24d},
		/* from m */ {1.0e15d, 1.0e12d, 1.0e9d,  1.0e6d,  1.0e3d,   1.0d,     1.0e-3d,  1.0e-6d,  1.0e-9d,  1.0e-12d, 1.0e-15d, 1.0e-18d, 1.0e-21d},
		/* from 1 */ {1.0e18d, 1.0e15d, 1.0e12d, 1.0e9d,  1.0e6d,   1.0e3d,   1.0d,     1.0e-3d,  1.0e-6d,  1.0e-9d,  1.0e-12d, 1.0e-15d, 1.0e-18d},
		/* from k */ {1.0e21d, 1.0e18d, 1.0e15d, 1.0e12d, 1.0e9d,   1.0e6d,   1.0e3d,   1.0d,     1.0e-3d,  1.0e-6d,  1.0e-9d,  1.0e-12d, 1.0e-15d},
		/* from M */ {1.0e24d, 1.0e21d, 1.0e18d, 1.0e15d, 1.0e12d,  1.0e9d,   1.0e6d,   1.0e3d,   1.0d,     1.0e-3d,  1.0e-6d,  1.0e-9d,  1.0e-12d},
		/* from G */ {1.0e27d, 1.0e24d, 1.0e21d, 1.0e18d, 1.0e15d,  1.0e12d,  1.0e9d,   1.0e6d,   1.0e3d,   1.0d,     1.0e-3d,  1.0e-6d,  1.0e-9d},
		/* from T */ {1.0e30d, 1.0e27d, 1.0e24d, 1.0e21d, 1.0e18d,  1.0e15d,  1.0e12d,  1.0e9d,   1.0e6d,   1.0e3d,   1.0d,     1.0e-3d,  1.0e-6d},
		/* from P */ {1.0e33d, 1.0e30d, 1.0e27d, 1.0e24d, 1.0e21d,  1.0e18d,  1.0e15d,  1.0e12d,  1.0e9d,   1.0e6d,   1.0e3d,   1.0d,     1.0e-3d},
		/* from E */ {1.0e36d, 1.0e33d, 1.0e30d, 1.0e27d, 1.0e24d,  1.0e21d,  1.0e18d,  1.0e15d,  1.0e12d,  1.0e9d,   1.0e6d,   1.0e3d,   1.0d}
	};
	
	private static double[][]		lengthConversionTable = new double[][]{
		//    to:     m                      AU                     ly                     pc
		/* from m */  {1.0d,                 6.6845871222684e-12d,  1.0570008340246e-16d,  3.2407792894699e-17d},
		/* from AU */ {149597870700.0d,      1.0d,                  1.581250749821e-5d,    4.8481368111335e-6d},
		/* from ly */ {9460730472580800.0d,  63241.077084266d,      1.0d,                  0.30660139378796d},
		/* from pc */ {30856775814671900.0d, 206264.80624547d,      3.2615642195300003d,   1.0d}
	};
	
	public static String toUnitString(double value)
	{
		return UnitUtil.toUnitString(value, 3);
	}
	
	public static String toUnitString(double value, int decimals)
	{
		int exp = (int) Math.log10(value);
		int index = Math.min(Math.max(exp, -18), 18) / 3 + 6;
		return String.format("%." + decimals + "f%s", value * Math.pow(1000.0d, -index + 6), prefixes[index]);
	}
	
	public static double convertPrefix(double value, String fromPrefix, String toPrefix)
	{
		int fromIndex = -1;
		int toIndex = -1;
		
		for(int i = 0; i < prefixes.length; ++i)
		{
			if(prefixes[i].equals(fromPrefix))
			{
				fromIndex = i;
			}
			if(prefixes[i].equals(toPrefix))
			{
				toIndex = i;
			}
		}
		
		if(fromIndex == -1 || toIndex == -1)
		{
			System.out.println("Cannot convert from unit-prefix \"" + fromPrefix + "\" to \"" + toPrefix + "\"! (One prefix is unknown)");
			return Double.NaN;
		}
		
		return prefixConversionTable[fromIndex][toIndex] * value;
	}
	
	public static double convertLengthUnit(double value, String fromUnit, String fromPrefix, String toUnit, String toPrefix)
	{
		double val = convertPrefix(value, fromPrefix, toPrefix);
		
		// One of the prefixes doesn't exist
		if(Double.isNaN(val))
		{
			return val;
		}
		
		int fromIndex = -1;
		int toIndex = -1;
		
		for(int i = 0; i < lengthUnits.length; ++i)
		{
			if(lengthUnits[i].equals(fromUnit))
			{
				fromIndex = i;
			}
			if(lengthUnits[i].equals(toUnit))
			{
				toIndex = i;
			}
		}
		
		if(fromIndex == -1 || toIndex == -1)
		{
			System.out.println("Cannot convert from length unit \"" + fromUnit + "\" to \"" + toUnit + "\"! (One unit is unknown)");
			return Double.NaN;
		}
		
		val = val * lengthConversionTable[fromIndex][toIndex];
		
		return val;
	}
}
