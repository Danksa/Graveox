package de.dspindler.graveox.simulation.physics;

import de.dspindler.graveox.simulation.physics.collision.CircleCollisionShape;
import de.dspindler.graveox.util.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Particle extends RigidBody
{
	private double			radius;
	
	public Particle(Vector2 position, Vector2 velocity, double mass)
	{
		super(position, velocity, mass, 0.0d, 0.0d, 0.0d, new CircleCollisionShape(2.0d));
	}
	
	public Particle()
	{
		super(new CircleCollisionShape(2.0d));
		
		this.radius = 8.0d;
	}

	@Override
	protected void onUpdate(double deltaTime)
	{
		
	}

	@Override
	protected void onRender(GraphicsContext g)
	{
//		g.setFill(Color.GRAY);
		g.setFill(Color.rgb(255, 255, 255, 0.2d));
		g.fillOval(position.x - radius, position.y - radius, radius * 2.0d, radius * 2.0d);
	}
}
