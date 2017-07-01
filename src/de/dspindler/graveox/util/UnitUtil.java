package de.dspindler.graveox.util;

<<<<<<< HEAD
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class UnitUtil
{
	private static Map<String, Unit>		prefixes;
	private static Map<String, Unit>		lengthUnits;
	private static Map<String, Unit>		massUnits;
	private static Map<String, Unit>		timeUnits;
	
	static
	{
		// Initialize prefixes
		prefixes = new HashMap<String, Unit>();
		
		prefixes.put("y", new Unit("y", "yocto", 1.0e-24d));
		prefixes.put("z", new Unit("z", "zepto", 1.0e-21d));
		prefixes.put("a", new Unit("a", "atto", 1.0e-18d));
		prefixes.put("f", new Unit("f", "femto", 1.0e-15d));
		prefixes.put("p", new Unit("p", "pico", 1.0e-12d));
		prefixes.put("n", new Unit("n", "nano", 1.0e-9d));
		prefixes.put("µ", new Unit("µ", "micro", 1.0e-6d));
		prefixes.put("m", new Unit("m", "milli", 1.0e-3d));
		prefixes.put("c", new Unit("c", "centi", 1.0e-2d));
		prefixes.put("d", new Unit("d", "deci", 1.0e-1d));
		
		prefixes.put("h", new Unit("h", "hecto", 1.0e2d));
		prefixes.put("k", new Unit("k", "kilo", 1.0e3d));
		prefixes.put("M", new Unit("M", "mega", 1.0e6d));
		prefixes.put("G", new Unit("G", "giga", 1.0e9d));
		prefixes.put("T", new Unit("T", "tera", 1.0e12d));
		prefixes.put("P", new Unit("P", "peta", 1.0e15d));
		prefixes.put("E", new Unit("E", "exa", 1.0e18d));
		prefixes.put("Z", new Unit("Z", "zetta", 1.0e21d));
		prefixes.put("Y", new Unit("Y", "yotta", 1.0e24d));
		
		// Initialize length units
		lengthUnits = new HashMap<String, Unit>();
		
		lengthUnits.put("m", new Unit("m", "meter", 1.0d));
		lengthUnits.put("mi", new Unit("mi", "mile", 1.609344e3d));
		lengthUnits.put("Re", new Unit("Re", "earth radius", 6.378137e6d));
		lengthUnits.put("ls", new Unit("ls", "light second", 2.99792458e8d));
		lengthUnits.put("Rs", new Unit("Rs", "sun radius", 6.96342e8d));
		lengthUnits.put("lm", new Unit("lm", "light minute", 1.798754748e10d));
		lengthUnits.put("AU", new Unit("AU", "astronomical unit", 1.495978707e11d));
		lengthUnits.put("lh", new Unit("lh", "light hour", 1.079252849e12d));
		lengthUnits.put("ld", new Unit("ld", "light day", 2.590206837e13d));
		lengthUnits.put("lw", new Unit("lw", "light week", 1.813144786e14d));
		lengthUnits.put("ly", new Unit("ly", "light year", 9.4607304725808e15d));
		lengthUnits.put("pc", new Unit("pc", "parsec", 3.0856775814914e16d));
		
		// Initialize mass units
		massUnits = new HashMap<String, Unit>();
		
		massUnits.put("g", new Unit("g", "gram", 1.0e-3d));
		massUnits.put("t", new Unit("t", "tonne", 1.0e3d));
		massUnits.put("Me", new Unit("Me", "earth mass", 5.972e24d));
		massUnits.put("Mj", new Unit("Mj", "jupiter mass", 1.8986e27d));
		massUnits.put("Ms", new Unit("Ms", "solar mass", 1.98855e30d));
		
		// Initialize time units
		timeUnits = new HashMap<String, Unit>();
		
		timeUnits.put("s", new Unit("s", "second", 1.0d));
		timeUnits.put("min", new Unit("min", "minute", 6.0e1d));
		timeUnits.put("h", new Unit("h", "hour", 3.6e3d));
		timeUnits.put("D", new Unit("D", "day", 8.64e4d));
		timeUnits.put("a", new Unit("a", "Julian year", 3.15576e7d));
	}
	
	public static String toLength(double length, String unit)
	{
		String smallest = "m";
		double val;
		
		Iterator<Entry<String, Unit>> it = lengthUnits.entrySet().iterator();
		Unit u;
		
		while(it.hasNext())
		{
			u = (Unit)(((Map.Entry<String, Unit>) it.next()).getValue());
			
			val = convertLength(length, unit, u.getSymbol());
			
			if(val < convertLength(length, unit, smallest) && val >= 1.0d)
			{
				smallest = u.getSymbol();
			}
		}
		
		System.out.println("Input: " + length + " " + unit);
		System.out.println("Output: " + convertLength(length, unit, smallest) + " " + smallest);
		
		return "";
	}
	
	public static double convertLength(double value, String from, String to)
	{
		double prefixVal1 = 1.0d;
		double prefixVal2 = 1.0d;
		
		if(lengthUnits.get(from) == null)
		{	
			String prefix = from.substring(0, 1);
			System.out.println(prefix);
			if(prefixes.get(prefix) == null)
			{
				System.out.println("[UnitUtil] Could not convert value, unit \"" + from + "\" is unknown!");
				
				return value;
			}
			
			from = from.substring(1);
			prefixVal1 = prefixes.get(prefix).getConversionValue();
		}
		
		if(lengthUnits.get(to) == null)
		{
			String prefix = to.substring(0, 1);
			
			if(prefixes.get(prefix) == null)
			{
				System.out.println("[UnitUtil] Could not convert value, unit \"" + to + "\" is unknown!");
				
				return value;
			}
			
			to = to.substring(1);
			prefixVal2 = prefixes.get(prefix).getConversionValue();
		}
		
		return value * (prefixVal1 / prefixVal2) * (lengthUnits.get(from).getConversionValue() / lengthUnits.get(to).getConversionValue());
	}
	
	public static double convertMass(double value, String from, String to)
	{
		double prefixVal1 = 1.0d;
		double prefixVal2 = 1.0d;
		
		if(massUnits.get(from) == null)
		{	
			String prefix = from.substring(0, 1);
			System.out.println(prefix);
			if(prefixes.get(prefix) == null)
			{
				System.out.println("[UnitUtil] Could not convert value, unit \"" + from + "\" is unknown!");
				
				return value;
			}
			
			from = from.substring(1);
			prefixVal1 = prefixes.get(prefix).getConversionValue();
		}
		
		if(massUnits.get(to) == null)
		{
			String prefix = to.substring(0, 1);
			
			if(prefixes.get(prefix) == null)
			{
				System.out.println("[UnitUtil] Could not convert value, unit \"" + to + "\" is unknown!");
				
				return value;
			}
			
			to = to.substring(1);
			prefixVal2 = prefixes.get(prefix).getConversionValue();
		}
		
		return value * (prefixVal1 / prefixVal2) * (massUnits.get(from).getConversionValue() / massUnits.get(to).getConversionValue());
	}
	
	public static double convertTime(double value, String from, String to)
	{
		double prefixVal1 = 1.0d;
		double prefixVal2 = 1.0d;
		
		if(timeUnits.get(from) == null)
		{	
			String prefix = from.substring(0, 1);
			System.out.println(prefix);
			if(prefixes.get(prefix) == null)
			{
				System.out.println("[UnitUtil] Could not convert value, unit \"" + from + "\" is unknown!");
				
				return value;
			}
			
			from = from.substring(1);
			prefixVal1 = prefixes.get(prefix).getConversionValue();
		}
		
		if(timeUnits.get(to) == null)
		{
			String prefix = to.substring(0, 1);
			
			if(prefixes.get(prefix) == null)
			{
				System.out.println("[UnitUtil] Could not convert value, unit \"" + to + "\" is unknown!");
				
				return value;
			}
			
			to = to.substring(1);
			prefixVal2 = prefixes.get(prefix).getConversionValue();
		}
		
		return value * (prefixVal1 / prefixVal2) * (timeUnits.get(from).getConversionValue() / timeUnits.get(to).getConversionValue());
=======
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
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
	}
}
