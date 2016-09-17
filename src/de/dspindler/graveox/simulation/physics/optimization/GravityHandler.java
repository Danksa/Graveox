package de.dspindler.graveox.simulation.physics.optimization;

import de.dspindler.graveox.simulation.SimulationModel;
import de.dspindler.graveox.simulation.physics.Particle;
import de.dspindler.graveox.simulation.physics.RigidBody;
import de.dspindler.graveox.util.Vector2;
import javafx.scene.canvas.GraphicsContext;

public class GravityHandler
{
	private SimulationModel			model;
	
	private Vector2					min;
	private Vector2					max;
	
	private TreeNode				root;
	
	public GravityHandler(SimulationModel model)
	{
		this.model = model;
		
		this.min = new Vector2(-Double.MAX_VALUE * 0.5d, -Double.MAX_VALUE * 0.5d);
		this.max = new Vector2(Double.MAX_VALUE * 0.5d, Double.MAX_VALUE * 0.5d);
		
		this.root = new TreeNode(min, new Vector2(Double.MAX_VALUE, Double.MAX_VALUE));
//		this.root = new TreeNode(new Vector2(-1000.0d, -1000.0d), new Vector2(2000.0d, 2000.0d));
	}
	
	public void update(double deltaTime)
	{
		this.min.zero();
		this.max.zero();
		
		// Find min and max
		for(RigidBody b : model.getBodies())
		{
			if(!(b instanceof Particle))
			{
				this.min.x = Math.min(min.x, b.getPosition().x);
				this.min.y = Math.min(min.y, b.getPosition().y);
				
				this.max.x = Math.max(max.x, b.getPosition().x);
				this.max.y = Math.max(max.y, b.getPosition().y);
			}
		}
		
		// Make square
		if((max.x - min.x) > (max.y - min.y))
		{
			double diff = (max.x - min.x) - (max.y - min.y);
			this.min.y -= diff * 0.5d;
			this.max.y += diff * 0.5d;
		}
		else
		{
			double diff = (max.y - min.y) - (max.x - min.x);
			this.min.x -= diff * 0.5d;
			this.max.x += diff * 0.5d;
		}
		
		// Set root size and position
		this.root.setPosition(min);
		this.root.setSize(max.clone().subtract(min).translate(1, 1));
		
		this.root.updateBodies(model.getBodies());
		
		// Apply gravity
		for(RigidBody b : model.getBodies())
		{
			b.applyForce(root.getForce(b));
		}
	}
	
	public void render(GraphicsContext g)
	{
//		this.root.render(g);
	}
}
