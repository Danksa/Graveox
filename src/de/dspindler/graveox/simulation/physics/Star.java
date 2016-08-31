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
		
		g.setStroke(Color.WHITE);
		g.strokeLine(position.x, position.y, position.x + Math.cos(angle) * radius, position.y + Math.sin(angle) * radius);
		
		// Schwarzschild radius draw test
		double r = 2.0d * Physics.GRAVITATIONAL_CONSTANT * mass / Physics.LIGHT_SPEED_SQUARED;
		double ang;
		int steps = 32;
		double step = 2.0d * Math.PI / steps;
		
		for(int i = 0; i < steps; ++i)
		{
			ang = step * i;
			g.setStroke(Color.WHITE);
			g.strokeLine(position.x + Math.cos(ang) * r, position.y + Math.sin(ang) * r, position.x + Math.cos(ang + step) * r, position.y + Math.sin(ang + step) * r);
			
			g.setStroke(Color.RED);
			g.strokeLine(position.x + Math.cos(ang) * r * 1.5d, position.y + Math.sin(ang) * r * 1.5d, position.x + Math.cos(ang + step) * r * 1.5d, position.y + Math.sin(ang + step) * r * 1.5d);
		}
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
