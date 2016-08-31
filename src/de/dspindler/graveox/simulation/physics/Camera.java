package de.dspindler.graveox.simulation.physics;

import de.dspindler.graveox.util.Vector2;
import javafx.scene.canvas.GraphicsContext;

public class Camera
{
	private Vector2				position;
	private Vector2				targetPosition;	// Used for smoothing the movement
	private double				positionSmoothing;
	
	private double				scale;
	private double				targetScale;	// Used for smoothing the zoom
	private double				zoomSmoothing;
	
	private RigidBody			trackedBody;
	private Vector2				spaceSize;		// Half the size of the visible space
	
	public Camera(Vector2 position, RigidBody trackedBody, double scale)
	{
		this.position = position.clone();
		this.scale = scale;
		this.targetScale = scale;
		this.trackedBody = trackedBody;
		this.spaceSize = new Vector2();
		this.targetPosition = position.clone();
		this.positionSmoothing = 0.1d;	// 0 - max smoothing, 1 - no smoothing
		this.zoomSmoothing = 0.1d;	// 0 - max smoothing, 1 - no smoothing
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
	
	public void setPositionSmoothingFactor(double factor)
	{
		this.positionSmoothing = factor;
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
		this.targetScale = scale;
	}
	
	public void scale(double scale)
	{
		this.targetScale *= scale;
	}
	
	public double getScale()
	{
		return targetScale;
	}
	
	public double getSmoothedScale()
	{
		return scale;
	}
	
	public void setPosition(Vector2 position)
	{
		this.targetPosition.set(position);
	}
	
	public Vector2 getPosition()
	{
		return targetPosition;
	}
	
	public void setSpaceSize(Vector2 size)
	{
		// Set space size to half, because it saves dividing by 2 often
		this.spaceSize.set(size).scale(0.5d);
	}
	
	public void setSpaceWidth(double width)
	{
		this.spaceSize.x = width * 0.5d;
	}
	
	public void setSpaceHeight(double height)
	{
		this.spaceSize.y = height * 0.5d;
	}
	
	public Vector2 getSpaceSize()
	{
		return spaceSize;
	}
	
	public Vector2 toWorldSpace(Vector2 point)
	{
		return point.clone().translate(-spaceSize.x, -spaceSize.y).scale(1.0d / scale).translate(position.x, position.y);
	}
	
	public Vector2 toCameraSpace(Vector2 point)
	{
		return point.clone().translate(-position.x, -position.y).scale(scale).translate(spaceSize.x, spaceSize.y);
	}
	
	public void update(double deltaTime)
	{
		if(trackedBody != null)
		{
			this.targetPosition.set(trackedBody.getPosition());
		}
		
		// Move to target position
		this.position.set(position.scale(1.0d - positionSmoothing).add(targetPosition.clone().scale(positionSmoothing)));
		
		// Zoom zo scale
		this.scale = (scale * (1.0d - zoomSmoothing)) + (targetScale * zoomSmoothing);
	}
	
	public void applyTransform(GraphicsContext g)
	{
		g.translate(spaceSize.x, spaceSize.y);
		g.scale(scale, scale);
		g.translate(-position.x, -position.y);
	}
}
