package de.dspindler.graveox.simulation.physics;

import de.dspindler.graveox.util.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Star extends RigidBody
{
	private double			radius;
	
	public Star(Vector2 position, Vector2 velocity, double mass, double angle, double angularVelocity, double inertia, double radius)
	{
		super(position, velocity, mass, angle, angularVelocity, inertia);
		
		this.radius = radius;
	}
	
	public Star()
	{
		super();
		
		this.radius = 1.0d;
	}

	@Override
	protected void onUpdate(double deltaTime, double time)
	{
		
	}

	@Override
	protected void onRender(GraphicsContext g)
	{
		g.setFill(Color.ORANGE);
		g.fillOval(position.x - radius, position.y - radius, radius * 2.0d, radius * 2.0d);
	}
	
	public void setRadius(double radius)
	{
		this.radius = radius;
	}
	
	public double getRadius()
	{
		return radius;
	}
}
