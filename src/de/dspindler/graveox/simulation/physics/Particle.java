package de.dspindler.graveox.simulation.physics;

import de.dspindler.graveox.simulation.physics.collision.CircleCollisionShape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Particle extends RigidBody
{
	private double			radius;
	
	public Particle()
	{
		super(new CircleCollisionShape(2.0d));
		
		this.radius = 4.0d;
	}

	@Override
	protected void onUpdate(double deltaTime)
	{
		
	}

	@Override
	protected void onRender(GraphicsContext g)
	{
		g.setFill(Color.GRAY);
		g.fillOval(position.x - radius, position.y - radius, radius * 2.0d, radius * 2.0d);
	}
}
