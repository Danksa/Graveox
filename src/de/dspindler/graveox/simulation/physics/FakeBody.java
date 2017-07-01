package de.dspindler.graveox.simulation.physics;

import de.dspindler.graveox.simulation.physics.collision.CircleCollisionShape;
import de.dspindler.graveox.util.Vector2;
import javafx.scene.canvas.GraphicsContext;

public class FakeBody extends RigidBody
{
<<<<<<< HEAD
	public FakeBody(Vector2 position, Vector2 velocity, double mass)
=======
	public FakeBody(Vector2 position, double mass)
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
	{
		super(new CircleCollisionShape(1.0d));
		
		super.setPosition(position);
<<<<<<< HEAD
		super.setVelocity(velocity);
=======
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
		super.setMass(mass);
	}

	@Override
	protected void onUpdate(double deltaTime){}

	@Override
	protected void onRender(GraphicsContext g){}
}
