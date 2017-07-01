package de.dspindler.graveox.simulation.physics;

import de.dspindler.graveox.simulation.physics.collision.CircleCollisionShape;
<<<<<<< HEAD
import de.dspindler.graveox.util.Vector2;
=======
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Particle extends RigidBody
{
	private double			radius;
	
<<<<<<< HEAD
	public Particle(Vector2 position, Vector2 velocity, double mass)
	{
		super(position, velocity, mass, 0.0d, 0.0d, 0.0d, new CircleCollisionShape(2.0d));
	}
	
=======
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
	public Particle()
	{
		super(new CircleCollisionShape(2.0d));
		
<<<<<<< HEAD
		this.radius = 8.0d;
=======
		this.radius = 4.0d;
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
	}

	@Override
	protected void onUpdate(double deltaTime)
	{
		
	}

	@Override
	protected void onRender(GraphicsContext g)
	{
<<<<<<< HEAD
//		g.setFill(Color.GRAY);
		g.setFill(Color.rgb(255, 255, 255, 0.2d));
=======
		g.setFill(Color.GRAY);
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
		g.fillOval(position.x - radius, position.y - radius, radius * 2.0d, radius * 2.0d);
	}
}
