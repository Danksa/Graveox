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
	private Color				color;
	private boolean				show;
	
	public Trail(int length)
	{
		this.position = new Vector2();
		this.parent = null;
		this.points = new ArrayList<Vector2>();
		this.length = length;
		this.color = Color.GREEN;
		this.show = true;
	}
	
	public Trail()
	{
		this(-1);
	}
	
	public void show(boolean show)
	{
		this.show = show;
		
		if(!show)
		{
			points.clear();
		}
	}
	
	public boolean isShown()
	{
		return show;
	}
	
	public void attach(RigidBody body)
	{
		this.parent = body;
		this.position.set(body.getPosition());
		this.points.add(body.getPosition().clone());
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setLength(int length)
	{
		this.length = length;
	}
	
	public int getLength()
	{
		return length;
	}
	
	public void update(double deltaTime)
	{
		if(parent != null && show)
		{
			if(points.size() == 0)
			{
				points.add(parent.getPosition().clone());
			}
			else
			{
				position.set(parent.getPosition());
				
				if(Vector2.getDistanceSquared(position, points.get(points.size() - 1)) >= 100.0d)
				{
					points.add(parent.getPosition().clone());
					
					while(length > 0 && points.size() > length)
					{
						points.remove(0);
					}
				}
			}
		}
	}
	
	public void render(GraphicsContext g)
	{
		if(show)
		{
			// Draw trail
			g.setStroke(this.color);
			for(int i = 0; i < points.size() - 1; ++i)
			{
				g.strokeLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y);
			}
			
			g.strokeLine(position.x, position.y, points.get(points.size() - 1).x, points.get(points.size() - 1).y);
		}
	}
}
