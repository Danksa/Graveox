package de.dspindler.graveox.simulation.physics;

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
		
		if(schwarzRadius > radius)
		{
			((CircleCollisionShape) super.getCollisionShape()).setRadius(schwarzRadius);
		}
	}
	
	public Star()
	{
		super(new CircleCollisionShape(1.0d));
		
		this.radius = 1.0d;
	}

	public void setRadius(double radius)
	{
		((CircleCollisionShape) this.getCollisionShape()).setRadius(radius);
		this.radius = radius;
		this.schwarzRadius = 2.0d * Physics.GRAVITATIONAL_CONSTANT * mass / Physics.LIGHT_SPEED_SQUARED;
	}
	
	@Override
	protected void onUpdate(double deltaTime){}

	@Override
	protected void onRender(GraphicsContext g)
	{
		g.setFill(Color.ORANGE);
		g.fillOval(position.x - radius, position.y - radius, radius * 2.0d, radius * 2.0d);
		
//		g.setStroke(Color.WHITE);
//		g.strokeLine(position.x, position.y, position.x + Math.cos(angle) * radius, position.y + Math.sin(angle) * radius);
		
		// Schwarzschild radius draw test
		if(schwarzRadius > radius)
		{
			// Draw photon sphere
//			g.setFill(Color.rgb(255, 0, 0, 0.3d));
//			g.fillOval(position.x - schwarzRadius * 1.5d, position.y - schwarzRadius * 1.5d, schwarzRadius * 3.0d, schwarzRadius * 3.0d);
			
			// Draw black hole
			g.setFill(Color.WHITE);
			g.fillOval(position.x - schwarzRadius, position.y - schwarzRadius, schwarzRadius * 2.0d, schwarzRadius * 2.0d);
			
			g.setFill(Color.BLACK);
			g.fillOval(position.x - schwarzRadius * 0.9d, position.y - schwarzRadius * 0.9d, schwarzRadius * 1.8d, schwarzRadius * 1.8d);
		}
	}
}
