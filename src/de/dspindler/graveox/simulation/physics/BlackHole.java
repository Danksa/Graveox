package de.dspindler.graveox.simulation.physics;

import de.dspindler.graveox.Graveox;
import de.dspindler.graveox.simulation.physics.collision.CircleCollisionShape;
import de.dspindler.graveox.util.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BlackHole extends RigidBody
{
	private double			schwarzRadius;
	
	public BlackHole(Vector2 position, Vector2 velocity, double mass)
	{
		super(position, velocity, mass, 0.0d, 0.0d, 1.0d, new CircleCollisionShape(2.0d * Physics.GRAVITATIONAL_CONSTANT * mass / Physics.LIGHT_SPEED_SQUARED));
		
		this.calcSchwarzschildRadius();
	}

	private void calcSchwarzschildRadius()
	{
		this.schwarzRadius = 2.0d * Physics.GRAVITATIONAL_CONSTANT * mass / Physics.LIGHT_SPEED_SQUARED;
	}
	
	@Override
	protected void onUpdate(double deltaTime)
	{
		// Update mass due to Hawking radiation
		super.setMass(Math.pow(super.getMass() * super.getMass() * super.getMass() - deltaTime * Physics.REDUCED_PLANCK_CONSTANT * Physics.LIGHT_SPEED_SQUARED * Physics.LIGHT_SPEED_SQUARED / (Physics.GRAVITATIONAL_CONSTANT * Physics.GRAVITATIONAL_CONSTANT * 5120 * Math.PI), 1.0d / 3.0d));
		
		// Update Schwarzschild radius
		this.calcSchwarzschildRadius();
		
		// Update collision shape
		((CircleCollisionShape) super.getCollisionShape()).setRadius(schwarzRadius);
		
		// Remove if it evaporated completely
		if(mass <= 0.0d)
		{
			Graveox.getSimulation().removeBody(this);
		}
	}

	@Override
	protected void onRender(GraphicsContext g)
	{
		g.setFill(Color.WHITE);
		g.fillOval(position.x - schwarzRadius, position.y - schwarzRadius, schwarzRadius * 2.0d, schwarzRadius * 2.0d);
		
		g.setFill(Color.BLACK);
		g.fillOval(position.x - schwarzRadius * 0.9d, position.y - schwarzRadius * 0.9d, schwarzRadius * 1.8d, schwarzRadius * 1.8d);
	}
}
