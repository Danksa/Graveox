package de.dspindler.graveox.simulation.physics.collision;

import de.dspindler.graveox.simulation.physics.Physics;
import de.dspindler.graveox.simulation.physics.RigidBody;
import de.dspindler.graveox.util.Vector2;

public class CollisionHandler
{
	public static void handleCollision(RigidBody a, RigidBody b, double deltaTime)
	{
		if(a.getCollisionShape() instanceof CircleCollisionShape && b.getCollisionShape() instanceof CircleCollisionShape)
		{
			circleCircleCollision(a, b, deltaTime);
		}
	}
	
	private static void circleCircleCollision(RigidBody a, RigidBody b, double deltaTime)
	{
		double radiusA = ((CircleCollisionShape) a.getCollisionShape()).getRadius();
		double radiusB = ((CircleCollisionShape) b.getCollisionShape()).getRadius();
		
		double rad = (radiusA + radiusB) * (radiusA + radiusB);
		if(Vector2.getDistanceSquared(a.getPosition(), b.getPosition()) <= rad)
		{
			Physics.applyCollisionImpulse(a, b, deltaTime);
		}
	}
	
	// Test, doesn't work reliable!
	public static void correctPositions(RigidBody a, RigidBody b)
	{
		double radiusA = ((CircleCollisionShape) a.getCollisionShape()).getRadius();
		double radiusB = ((CircleCollisionShape) b.getCollisionShape()).getRadius();
		
		double rad = (radiusA + radiusB) * (radiusA + radiusB);
		double distSquared = Vector2.getDistanceSquared(a.getPosition(), b.getPosition());
		if(distSquared <= rad)
		{
			// Correct position
			Vector2 relVel = b.getVelocity().clone().subtract(a.getVelocity());
			
			double penetrationPercent = (distSquared - rad) / rad + relVel.getMagnitude() * 0.001d;
			double force = penetrationPercent * 10000.0d;
			
			Vector2 normal = b.getPosition().clone().subtract(a.getPosition()).normalize();
			normal.scale(force);
			
			a.applyImpulse(normal);
			normal.invert();
			b.applyImpulse(normal);
		}
	}
}
