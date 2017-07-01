package de.dspindler.graveox.simulation.physics.optimization;

import java.util.ArrayList;

import de.dspindler.graveox.simulation.physics.FakeBody;
import de.dspindler.graveox.simulation.physics.Particle;
import de.dspindler.graveox.simulation.physics.Physics;
import de.dspindler.graveox.simulation.physics.RigidBody;
import de.dspindler.graveox.util.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TreeNode
{
<<<<<<< HEAD
	private static final double		THETA = 0.5d;
	
	private Vector2					massCenter;
	private double					massSum;
	private Vector2					avgVelocity;
=======
	private static final double		THETA = 0.8d;
	
	private Vector2					massCenter;
	private double					massSum;
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
	
	private Vector2					position;
	private Vector2					size;
	private Vector2					center;
	
	private TreeNode				topLeft;
	private TreeNode				topRight;
	private TreeNode				bottomLeft;
	private TreeNode				bottomRight;
	
	private ArrayList<RigidBody>	bodies;
	private Vector2					halfSize;
	private FakeBody				fakeBody;
	
	public TreeNode(Vector2 position, Vector2 size)
	{
		this.position = position.clone();
		this.size = size.clone();
		this.center = size.clone().scale(0.5d).add(position);
		
<<<<<<< HEAD
		this.fakeBody = new FakeBody(new Vector2(), new Vector2(), 1.0d);
=======
		this.fakeBody = new FakeBody(new Vector2(), 1.0d);
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
//		System.out.println("Size: " + size);
		
		this.bodies = new ArrayList<RigidBody>();
		this.halfSize = size.clone().scale(0.5d);
		
		this.massCenter = new Vector2();
		this.massSum = 0.0d;
<<<<<<< HEAD
		this.avgVelocity = new Vector2();
=======
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
		
		this.topLeft = null;
		this.topRight = null;
		this.bottomLeft = null;
		this.bottomRight = null;
	}
	
	public Vector2 getForce(RigidBody b)
	{
		Vector2 force = new Vector2();
		
		if(bodies.size() == 1)
		{
			if(bodies.get(0) != b)
			{
				if(!(bodies.get(0) instanceof Particle))
				{
					force.add(Physics.getRelativisticGravity(b, bodies.get(0)));
				}
			}
		}
		else
		{
			double dist = Vector2.getDistance(b.getPosition(), massCenter);
			double size = this.size.x;
			double ratio = size / dist;
			
			if(ratio < THETA)
			{
				force.add(Physics.getRelativisticGravity(b, fakeBody));
			}
			else
			{
				if(topLeft != null)
				{
					force.add(topLeft.getForce(b));
					force.add(topRight.getForce(b));
					force.add(bottomLeft.getForce(b));
					force.add(bottomRight.getForce(b));
				}
			}
		}
		
		return force;
	}
	
	public void setPosition(Vector2 position)
	{
		this.position.set(position);
		this.center = size.clone().scale(0.5d).add(position);
		
		if(topLeft != null)
		{
			this.topLeft.setPosition(position);
			this.topRight.setPosition(position.clone().translate(halfSize.x, 0.0d));
			this.bottomLeft.setPosition(position.clone().translate(0.0d, halfSize.y));
			this.bottomRight.setPosition(position.clone().translate(halfSize.x, halfSize.y));
		}
	}
	
	public void setSize(Vector2 size)
	{
		this.size.set(size);
		this.center = size.clone().scale(0.5d).add(position);
		this.halfSize.set(size).scale(0.5d);
		
		if(topLeft != null)
		{
			this.topLeft.setSize(halfSize);
			this.topRight.setSize(halfSize);
			this.bottomLeft.setSize(halfSize);
			this.bottomRight.setSize(halfSize);
		}
	}
	
	public Vector2 getCenter()
	{
		return center;
	}
	
	public Vector2 getMassCenter()
	{
		return massCenter;
	}
	
	public double getMass()
	{
		return massSum;
	}
	
	public Vector2 getSize()
	{
		return size;
	}
	
	public boolean areBodiesInside()
	{
		for(RigidBody b : bodies)
		{
			if(!isInside(b))
			{
				return false;
			}
		}
		
		return true;
	}
	
	private boolean isInside(RigidBody b)
	{
<<<<<<< HEAD
		if(b.getPosition().x >= position.x && b.getPosition().x < position.x + size.x)
		{
			if(b.getPosition().y >= position.y && b.getPosition().y < position.y + size.y)
=======
		if(b.getPosition().x >= position.x && b.getPosition().x <= position.x + size.x)
		{
			if(b.getPosition().y >= position.y && b.getPosition().y <= position.y + size.y)
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	public void updateBodies(ArrayList<RigidBody> bodies)
	{		
		this.bodies.clear();
		for(RigidBody b : bodies)
		{
			if(isInside(b) && !(b instanceof Particle))
			{
				this.bodies.add(b);
			}
		}
		
<<<<<<< HEAD
		this.avgVelocity.zero();
=======
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
		this.massSum = 0.0d;
		this.massCenter.zero();
		for(RigidBody b : this.bodies)
		{
			if(!(b instanceof Particle))
			{
				this.massSum += b.getMass();
				this.massCenter.add(b.getPosition().clone().scale(b.getMass()));
<<<<<<< HEAD
				this.avgVelocity.add(b.getVelocity());
			}
		}
		this.massCenter.scale(1.0d / massSum);
		this.avgVelocity.scale(1.0d / bodies.size());
		
		this.fakeBody.setMass(massSum);
		this.fakeBody.setPosition(massCenter);
		this.fakeBody.setVelocity(avgVelocity);
=======
			}
		}
		this.massCenter.scale(1.0d / massSum);
		
		this.fakeBody.setMass(massSum);
		this.fakeBody.setPosition(massCenter);
>>>>>>> ecb846ef15b803224d64c3847fd3f83385893ab7
		
		if(this.bodies.size() > 1)
		{
			if(topLeft == null)
			{
				this.topLeft = new TreeNode(position, halfSize);
				this.topRight = new TreeNode(position.clone().translate(halfSize.x, 0.0d), halfSize);
				this.bottomLeft = new TreeNode(position.clone().translate(0.0d, halfSize.y), halfSize);
				this.bottomRight = new TreeNode(position.clone().translate(halfSize.x, halfSize.y), halfSize);
			}
			
			this.topLeft.updateBodies(this.bodies);
			this.topRight.updateBodies(this.bodies);
			this.bottomLeft.updateBodies(this.bodies);
			this.bottomRight.updateBodies(this.bodies);
		}
		else
		{
			this.topLeft = null;
			this.topRight = null;
			this.bottomLeft = null;
			this.bottomRight = null;
		}
	}
	
	public void render(GraphicsContext g)
	{
		g.setStroke(Color.WHITE);
		g.strokeLine(position.x, position.y, position.x + size.x, position.y);
		g.strokeLine(position.x, position.y + size.y, position.x + size.x, position.y + size.y);
		g.strokeLine(position.x, position.y, position.x, position.y + size.y);
		g.strokeLine(position.x + size.x, position.y, position.x + size.x, position.y + size.y);
		
		if(topLeft != null && topRight != null && bottomLeft != null && bottomRight != null)
		{
			this.topLeft.render(g);
			this.topRight.render(g);
			this.bottomLeft.render(g);
			this.bottomRight.render(g);
		}
		
		// Draw relation line
	/*	g.setStroke(Color.GRAY);
		for(RigidBody b : bodies)
		{
			g.strokeLine(b.getPosition().x, b.getPosition().y, center.x, center.y);
		}*/
		
		// Render square center
//		g.setFill(Color.BLUE);
//		g.fillRect(center.x - 4, center.y - 4, 8, 8);
		
		// Render mass center
//		double s = massSum * 0.00001d;
//		System.out.println(s);
//		g.setFill(Color.RED);
//		g.fillRect(massCenter.x - s * 0.5d, massCenter.y - s * 0.5d, s, s);
	}
}
