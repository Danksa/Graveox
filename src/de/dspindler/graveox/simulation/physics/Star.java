package de.dspindler.graveox.simulation.physics;

import de.dspindler.graveox.Graveox;
import de.dspindler.graveox.simulation.physics.collision.CircleCollisionShape;
import de.dspindler.graveox.util.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Star extends RigidBody
{
	private double			radius;
	private double			schwarzRadius;
	
	public Star(Vector2 position, Vector2 velocity, double mass, double angle, double angularVelocity, double inertia, double radius)
	{
		super(position, velocity, mass, angle, angularVelocity, inertia, new CircleCollisionShape(radius));
		
		this.radius = radius;
		this.schwarzRadius = 2.0d * Physics.GRAVITATIONAL_CONSTANT * mass / Physics.LIGHT_SPEED_SQUARED;
	}
	
	public Star()
	{
		super(new CircleCollisionShape(1.0d));
		
		this.radius = 1.0d;
	}
	
	@Override
	public void setMass(double mass)
	{
		super.setMass(mass);
		
		this.schwarzRadius = 2.0d * Physics.GRAVITATIONAL_CONSTANT * mass / Physics.LIGHT_SPEED_SQUARED;
	}
	
	public void setRadius(double radius)
	{		
		((CircleCollisionShape) this.getCollisionShape()).setRadius(radius);
		this.radius = radius;
		this.schwarzRadius = 2.0d * Physics.GRAVITATIONAL_CONSTANT * mass / Physics.LIGHT_SPEED_SQUARED;
	}
	
	public double getRadius()
	{
		return radius;
	}
	
	@Override
	protected void onUpdate(double deltaTime)
	{
		if(schwarzRadius > radius)
		{
			// Create black hole
			BlackHole blackHole = new BlackHole(position, velocity, mass);
			
			Graveox.getSimulation().removeBody(this);
			Graveox.getSimulation().addBody(blackHole);
			
			// Change tracked camera body, if necessary
			if(Graveox.getSimulation().getModel().getCamera().getTrackedBody() == this)
			{
				Graveox.getSimulation().getModel().getCamera().setTrackedBody(blackHole);
			}
		}
	}

	@Override
	protected void onRender(GraphicsContext g)
	{
		g.setFill(Color.ORANGE);
		g.fillOval(position.x - radius, position.y - radius, radius * 2.0d, radius * 2.0d);
	}
}
