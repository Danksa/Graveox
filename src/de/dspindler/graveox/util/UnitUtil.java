package de.dspindler.graveox.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
		prefixes.put("�", new Unit("�", "micro", 1.0e-6d));
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
		
		Iterator it = lengthUnits.entrySet().iterator();
		Unit u;
		for(int i = 0; it.hasNext(); ++i)
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
	}
}
