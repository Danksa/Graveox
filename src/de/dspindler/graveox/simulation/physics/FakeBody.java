package de.dspindler.graveox.simulation.physics;

import de.dspindler.graveox.simulation.physics.collision.CircleCollisionShape;
import de.dspindler.graveox.util.Vector2;
import javafx.scene.canvas.GraphicsContext;

public class FakeBody extends RigidBody
{
	public FakeBody(Vector2 position, double mass)
	{
		super(new CircleCollisionShape(1.0d));
		
		super.setPosition(position);
		super.setMass(mass);
	}

	@Override
	protected void onUpdate(double deltaTime){}

	@Override
	protected void onRender(GraphicsContext g){}
}
