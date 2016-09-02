package de.dspindler.graveox.simulation.physics.collision;

public class CircleCollisionShape implements CollisionShape
{
	private double			radius;
	
	public CircleCollisionShape(double radius)
	{
		this.radius = radius;
	}
	
	public void setRadius(double radius)
	{
		this.radius = radius;
	}
	
	public double getRadius()
	{
		return radius;
	}
	
	@Override
	public double getBroadRadius()
	{
		return radius * 1.5d;
	}
}
