package de.dspindler.graveox.util;

public class Unit
{
	private String			symbol;
	private String			name;
	private double			conversion;
	
	public Unit(String symbol, String name, double conversion)
	{
		this.symbol = symbol;
		this.name = name;
		this.conversion = conversion;
	}
	
	public Unit(String symbol, double conversion)
	{
		this(symbol, "not specified", conversion);
	}
	
	public String getSymbol()
	{
		return symbol;
	}
	
	public String getName()
	{
		return name;
	}
	
	public double getConversionValue()
	{
		return conversion;
	}
}
