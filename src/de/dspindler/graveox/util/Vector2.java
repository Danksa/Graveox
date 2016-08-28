package de.dspindler.graveox.util;

public class Vector2
{
	public double			x;
	public double			y;
	
	public Vector2(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2()
	{
		this(0.0d, 0.0d);
	}
	
	public double getMagnitudeSquared()
	{
		return x * x + y * y;
	}
	
	public double getMagnitude()
	{
		return Math.sqrt(this.getMagnitudeSquared());
	}
	
	public double getDirection()
	{
		return Math.atan2(y, x);
	}
	
	public Vector2 set(double x, double y)
	{
		this.x = x;
		this.y = y;
		return this;
	}
	
	public Vector2 set(Vector2 b)
	{
		this.x = b.x;
		this.y = b.y;
		return this;
	}
	
	public Vector2 setPolar(double magnitude, double direction)
	{
		this.x = Math.cos(direction) * magnitude;
		this.y = Math.sin(direction) * magnitude;
		return this;
	}
	
	public Vector2 translate(double x, double y)
	{
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Vector2 add(Vector2 b)
	{
		this.x += b.x;
		this.y += b.y;
		return this;
	}
	
	public Vector2 subtract(Vector2 b)
	{
		this.x -= b.x;
		this.y -= b.y;
		return this;
	}
	
	public Vector2 scale(double s)
	{
		this.x *= s;
		this.y *= s;
		return this;
	}
	
	public Vector2 multiply(Vector2 b)
	{
		this.x *= b.x;
		this.y *= b.y;
		return this;
	}
	
	public Vector2 divide(Vector2 b)
	{
		this.x /= b.x;
		this.y /= b.y;
		return this;
	}
	
	public double dot(Vector2 b)
	{
		return x * b.x + y * b.y;
	}
	
	public double cross(Vector2 b)
	{
		return x * b.y - y * b.x;
	}
	
	public Vector2 normalize()
	{
		double mag = this.getMagnitude();
		this.x /= mag;
		this.y /= mag;
		return this;
	}
	
	public Vector2 invert()
	{
		this.x = -x;
		this.y = -y;
		return this;
	}
	
	public Vector2 zero()
	{
		this.x = 0.0d;
		this.y = 0.0d;
		return this;
	}
	
	public Vector2 getLeftNormal()
	{
		return new Vector2(y, -x);
	}
	
	public Vector2 getRightNormal()
	{
		return new Vector2(-y, x);
	}
	
	public Vector2 clone()
	{
		return new Vector2(x, y);
	}
	
	public String toString()
	{
		return String.format("[Vector2][%.2f\t%.2f]", x, y);
	}
	
	// Static mathods
	public static double getDistanceSquared(Vector2 a, Vector2 b)
	{
		return (b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y);
	}
	
	public static double getDistance(Vector2 a, Vector2 b)
	{
		return Math.sqrt(Vector2.getDistanceSquared(a, b));
	}
	
	public static double getAngle(Vector2 a, Vector2 b)
	{
		return Math.atan2(b.y - a.y, b.x - a.x);
	}
	
	public static double dot(Vector2 a, Vector2 b)
	{
		return a.dot(b);
	}
	
	public static double cross(Vector2 a, Vector2 b)
	{
		return a.cross(b);
	}
	
	public static Vector2 project(Vector2 a, Vector2 b)
	{
		return a.clone().scale(a.dot(b) / a.getMagnitudeSquared());
	}
}
