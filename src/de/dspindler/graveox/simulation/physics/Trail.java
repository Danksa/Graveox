package de.dspindler.graveox.simulation.physics;

import java.util.ArrayList;

import de.dspindler.graveox.util.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Trail
{
	private Vector2				position;
	private RigidBody			parent;
	private ArrayList<Vector2>	points;
	private int					length;
	
	public Trail(RigidBody parent, int length)
	{
		this.position = parent.getPosition().clone();
		this.parent = parent;
		this.points = new ArrayList<Vector2>();
		this.points.add(position.clone());
		this.length = length;
	}
	
	public Trail(RigidBody parent)
	{
		this(parent, -1);
	}
	
	public Trail()
	{
		this(null, -1);
	}
	
	public void attach(RigidBody body)
	{
		this.parent = body;
	}
	
	public boolean isAttachedTo(RigidBody body)
	{
		return parent == body;
	}
	
	public void update(double deltaTime)
	{
		if(parent != null)
		{
			position.set(parent.getPosition());
			
			if(Vector2.getDistanceSquared(position, points.get(points.size() - 1)) >= 100.0d)
			{
				points.add(parent.getPosition().clone());
				
				if(length > 0 && points.size() > length)
				{
					points.remove(0);
				}
			}
		}
	}
	
	public void render(GraphicsContext g)
	{
//		g.setFill(Color.WHITE);
//		g.fillText("Length: " + points.size(), position.x + 5, position.y - 7);
		
		// Draw trail
		g.setStroke(Color.GREEN);
		for(int i = 0; i < points.size() - 1; ++i)
		{
			g.strokeLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y);
		}
		
		g.strokeLine(position.x, position.y, points.get(points.size() - 1).x, points.get(points.size() - 1).y);
	}
}
