package de.dspindler.graveox.simulation.physics;

import de.dspindler.graveox.simulation.physics.collision.CollisionShape;
import de.dspindler.graveox.util.Vector2;
import javafx.scene.canvas.GraphicsContext;

public abstract class RigidBody
{
	// Linear motion properties
	protected Vector2			position;
	protected Vector2			velocity;
	private Vector2				acceleration;
	protected double			mass;
	
	// Rotational motion properties
	protected double			angle;
	protected double			angularVelocity;
	private double				angularAcceleration;
	protected double			inertia;
	
	// Collision
	private CollisionShape		collisionShape;
	
	// Trail
	private Trail				trail;
	
	// For optimization
	private double				inverseMass;
	private double				inverseInertia;
	private Vector2				netForce;
	private double				netTorque;
	
	public RigidBody(Vector2 position, Vector2 velocity, double mass, double angle, double angularVelocity, double inertia, CollisionShape collisionShape)
	{
		this.position = position.clone();
		this.velocity = velocity.clone();
		this.acceleration = new Vector2();
		this.mass = mass;
		
		this.angle = angle;
		this.angularVelocity = angularVelocity;
		this.angularAcceleration = 0.0d;
		this.inertia = inertia;
		
		this.inverseMass = 1.0d / mass;
		this.inverseInertia = 1.0d / inertia;
		this.netForce = new Vector2();
		this.netTorque = 0.0d;
		
		this.collisionShape = collisionShape;
		
		this.trail = null;
	}
	
	public RigidBody(CollisionShape collisionShape)
	{
		this(new Vector2(), new Vector2(), 1.0d, 0.0d, 0.0d, 1.0d, collisionShape);
	}
	
	public CollisionShape getCollisionShape()
	{
		return collisionShape;
	}
	
	public void attachTrail(Trail trail)
	{
		this.trail = trail;
		this.trail.attach(this);
	}
	
	public void detachTrail()
	{
		this.trail = null;
	}
	
	public void showTrail(boolean show)
	{
		this.trail.show(show);
	}
	
	public boolean isTrailShown()
	{
		if(trail == null)
		{
			return false;
		}
		
		return trail.isShown();
	}
	
	public boolean hasTrail()
	{
		return trail != null;
	}
	
	public Trail getTrail()
	{
		return trail;
	}
	
	public void applyForce(Vector2 force, Vector2 point)
	{
		this.netForce.add(force);
		this.netTorque += force.dot(point);
	}
	
	public void applyForce(Vector2 force)
	{
		this.netForce.add(force);
	}
	
	public void applyImpulse(Vector2 impulse)
	{
		this.netForce.add(impulse.scale(mass));
	}
	
	public void applyTorque(double torque)
	{
		this.netTorque += torque;
	}
	
	public void applyAngularForce(double force, Vector2 point)
	{
		this.netTorque += force * point.getMagnitude();
	}
	
	public Vector2 toLocalSpace(Vector2 p)
	{
		Vector2 t = new Vector2();
		Vector2 p2 = p.clone();
		p2.subtract(position);
		t.x = p2.x * Math.cos(angle) - p2.y * Math.sin(angle);
		t.y = p2.x * Math.sin(angle) + p2.y * Math.cos(angle);
		return t;
	}
	
	public Vector2 toWorldSpace(Vector2 p)
	{
		Vector2 t = new Vector2();
		t.x = p.x * Math.cos(angle) - p.y * Math.sin(angle);
		t.y = p.x * Math.sin(angle) + p.y * Math.cos(angle);
		t.add(position);
		return t;
	}
	
	public void preUpdate(double deltaTime)
	{
		// Calculate new values for linear components using Verlet integration
		
		// The position of all objects has to be updated, before the velocity and forces are updated!
		
		// Calculate position
		position.add(acceleration.clone().add(velocity).scale(deltaTime));
		velocity.add(acceleration);
		
		// Same for rotation
		angle += (angularAcceleration + angularVelocity) * deltaTime;
		angularVelocity += angularAcceleration;
	}
	
	public void update(double deltaTime)
	{
		updateLinear(deltaTime);
		updateRotational(deltaTime);
		
		onUpdate(deltaTime);
	}
	
	public void updateTrail(double deltaTime)
	{
		if(this.hasTrail())
		{
			this.trail.update(deltaTime);
		}
	}
	
	private void updateLinear(double deltaTime)
	{
		// Calculate new values for linear components using Verlet integration
		acceleration.set(netForce).scale(inverseMass * 0.5d * deltaTime);
		velocity.add(acceleration);
		netForce.zero();
	}
	
	private void updateRotational(double deltaTime)
	{
		// Calculate new values for rotational components using Verlet integration
		angularAcceleration = netTorque * inverseInertia * 0.5d * deltaTime;
		angularVelocity += angularAcceleration;
		
		// Reset net torque
		netTorque = 0.0d;
	}
	
	public void render(GraphicsContext g)
	{
		// May implement some debug rendering info here
		
		onRender(g);
	}
	
	public void renderTrail(GraphicsContext g)
	{
		if(this.hasTrail())
		{
			this.trail.render(g);
		}
	}
	
	protected abstract void onUpdate(double deltaTime);
	protected abstract void onRender(GraphicsContext g);
	
	public void setPosition(Vector2 position)
	{
		this.position.set(position);
	}
	
	public Vector2 getPosition()
	{
		return position;
	}
	
	public void setVelocity(Vector2 velocity)
	{
		this.velocity.set(velocity);
	}
	
	public Vector2 getVelocity()
	{
		return velocity;
	}
	
	public void setMass(double mass)
	{
		this.mass = mass;
		this.inverseMass = 1.0d / mass;
	}
	
	public double getMass()
	{
		return mass;
	}
	
	public double getInverseMass()
	{
		return inverseMass;
	}
	
	public void setAngle(double angle)
	{
		this.angle = angle;
	}
	
	public double getAngle()
	{
		return angle;
	}
	
	public void setAngularVelocity(double angularVelocity)
	{
		this.angularVelocity = angularVelocity;
	}
	
	public double getAngularVelocity()
	{
		return angularVelocity;
	}
	
	public void setInertia(double inertia)
	{
		this.inertia = inertia;
		this.inverseInertia = 1.0d / inertia;
	}
	
	public double getInertia()
	{
		return inertia;
	}
	
	public double getInverseInertia()
	{
		return inverseInertia;
	}
}