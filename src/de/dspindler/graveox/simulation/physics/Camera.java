package de.dspindler.graveox.simulation.physics;

import de.dspindler.graveox.util.Vector2;
import javafx.scene.canvas.GraphicsContext;

public class Camera
{
	private Vector2				position;
	private RigidBody			trackedBody;
	private double				scale;
	
	public Camera(Vector2 position, RigidBody trackedBody, double scale)
	{
		this.position = position.clone();
		this.scale = scale;
		this.trackedBody = trackedBody;
	}
	
	public Camera(Vector2 position, double scale)
	{
		this(position, null, scale);
	}
	
	public Camera(Vector2 position)
	{
		this(position, null, 1.0d);
	}
	
	public Camera(RigidBody trackedBody, double scale)
	{
		this(new Vector2(), trackedBody, scale);
	}
	
	public Camera(RigidBody trackedBody)
	{
		this(new Vector2(), trackedBody, 1.0d);
	}
	
	public Camera()
	{
		this(new Vector2(), null, 1.0d);
	}
	
	public void setTrackedBody(RigidBody body)
	{
		this.trackedBody = body;
	}
	
	public RigidBody getTrackedBody()
	{
		return trackedBody;
	}
	
	public void setScale(double scale)
	{
		this.scale = scale;
	}
	
	public void scale(double scale)
	{
		this.scale *= scale;
	}
	
	public double getScale()
	{
		return scale;
	}
	
	public void setPosition(Vector2 position)
	{
		this.position.set(position);
	}
	
	public Vector2 getPosition()
	{
		return position;
	}
	
	public Vector2 toWorldSpace(Vector2 point)
	{
		return point.clone().translate(-512, -384).scale(1.0d / scale).translate(position.x, position.y);
	}
	
	public Vector2 toCameraSpace(Vector2 point)
	{
		return point.clone().translate(-position.x, -position.y).scale(scale).translate(512, 384);
	}
	
	public void update(double deltaTime)
	{
		if(trackedBody != null)
		{
			this.position.set(trackedBody.getPosition());
		}
	}
	
	public void applyTransform(GraphicsContext g)
	{
		g.translate(512, 384);
		g.scale(scale, scale);
		g.translate(-position.x, -position.y);
	}
}
