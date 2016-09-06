package de.dspindler.graveox.simulation.physics;

import de.dspindler.graveox.simulation.physics.collision.CircleCollisionShape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Particle extends RigidBody
{

	public Particle()
	{
		super(new CircleCollisionShape(2.0d));
	}

	@Override
	protected void onUpdate(double deltaTime)
	{
		
	}

	@Override
	protected void onRender(GraphicsContext g)
	{
		g.setFill(Color.GRAY);
		g.fillOval(position.x - 2.0d, position.y - 2.0d, 4.0d, 4.0d);
	}
}
