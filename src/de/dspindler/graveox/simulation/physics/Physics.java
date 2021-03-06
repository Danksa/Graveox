package de.dspindler.graveox.simulation.physics;

import de.dspindler.graveox.util.Vector2;

public class Physics
{
	public static final double			GRAVITATIONAL_CONSTANT = 1.0d;
	public static final double			LIGHT_SPEED = 1000.0d;
	public static final double			LIGHT_SPEED_SQUARED = LIGHT_SPEED * LIGHT_SPEED;
	public static final double			REDUCED_PLANCK_CONSTANT = 1.054571800e-34d;
	
	public static void applyNewtonianGravity(RigidBody a, RigidBody b)
	{
		if(a instanceof Particle && b instanceof Particle)
		{
			return;
		}
		else
		{
			Vector2 force = getNewtonianGravity(a, b);
			
			if(a instanceof Particle || b instanceof Particle)
			{
				if(a instanceof Particle)
				{
					a.applyForce(force);
				}
				if(b instanceof Particle)
				{
					force.invert();
					b.applyForce(force);
				}
			}
			else
			{
				a.applyForce(force);
				force.invert();
				b.applyForce(force);
			}
		}
	}
	
	public static Vector2 getNewtonianGravity(RigidBody a, RigidBody b)
	{
		Vector2 relPos = b.getPosition().clone().subtract(a.getPosition());
		relPos.scale(GRAVITATIONAL_CONSTANT * a.getMass() * b.getMass() / (relPos.getMagnitudeSquared() * relPos.getMagnitude()));
		
		return relPos;
	}
	
	public static void applyRelativisticGravity(RigidBody a, RigidBody b)
	{
		if(a instanceof Particle && b instanceof Particle)
		{
			return;
		}
		else
		{
			Vector2 force = getRelativisticGravity2(a, b);
			
			if(a instanceof Particle || b instanceof Particle)
			{
				if(a instanceof Particle)
				{
					a.applyForce(force);
				}
				if(b instanceof Particle)
				{
					force.invert();
					b.applyForce(force);
				}
			}
			else
			{
				a.applyForce(force);
				force.invert();
				b.applyForce(force);
			}
		}
	}
	
	public static Vector2 getRelativisticGravity(RigidBody a, RigidBody b)
	{
		Vector2 relPos = b.getPosition().clone().subtract(a.getPosition());
		Vector2 relVel = b.getVelocity().clone().subtract(a.getVelocity());
		
		double angVel = relPos.cross(relVel);
		angVel *= angVel;
		double dist2 = 1.0d / relPos.getMagnitudeSquared();
		
		double force = GRAVITATIONAL_CONSTANT * a.getMass() * b.getMass() * dist2 * (1.0d + 3.0d * angVel * dist2 / LIGHT_SPEED_SQUARED);
		
		relPos.scale(force * Math.sqrt(dist2));

		return relPos;
	}
	
	public static Vector2 getRelativisticGravity2(RigidBody a, RigidBody b)
	{
		Vector2 relPos = b.getPosition().clone().subtract(a.getPosition());
		Vector2 relVel = b.getVelocity().clone().subtract(a.getVelocity());
		
		double mu = a.getMass();
		double angVel = relPos.cross(relVel) * mu;
		angVel *= angVel;
		double dist2 = relPos.getMagnitudeSquared();
		
		double force = GRAVITATIONAL_CONSTANT * a.getMass() * b.getMass() / relPos.getMagnitudeSquared() + 3.0d * GRAVITATIONAL_CONSTANT * angVel * (a.getMass() + b.getMass()) / (LIGHT_SPEED_SQUARED * mu * dist2 * dist2);
		
		relPos.scale(force / relPos.getMagnitude());
		
		return relPos;
	}
	
	public static void applyCollisionImpulse(RigidBody a, RigidBody b, double deltaTime)
	{
		Vector2 relVel = b.getVelocity().clone().subtract(a.getVelocity());
		Vector2 normal = b.getPosition().clone().subtract(a.getPosition()).normalize();
		
		double velN = relVel.dot(normal);
		
		if(velN < 0)
		{
			double e = 0.8d;
			double j = -(1.0d + e) * velN / (a.getInverseMass() + b.getInverseMass());
			Vector2 impulse = normal.clone().scale(j / deltaTime);
			b.applyForce(impulse);
			impulse.invert();
			a.applyForce(impulse);
			
			/*if(a instanceof Star && b instanceof Star)
			{
				// Correct position
				double radiusA = ((Star) a).getRadius();
				double radiusB = ((Star) b).getRadius();
				
				Vector2 p = a.getPosition().clone().add(normal.clone().scale(radiusA));
				Vector2 posa = normal.clone().scale(-radiusA).add(p);
				Vector2 posb = normal.clone().scale(radiusB).add(p);
				
				a.setPosition(posa);
				b.setPosition(posb);
			}*/
		}
	}
}
