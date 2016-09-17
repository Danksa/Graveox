package de.dspindler.graveox.simulation.tools;

import de.dspindler.graveox.simulation.physics.Particle;
import de.dspindler.graveox.simulation.physics.Physics;
import de.dspindler.graveox.simulation.physics.RigidBody;
import de.dspindler.graveox.simulation.physics.Star;
import de.dspindler.graveox.simulation.physics.Trail;
import de.dspindler.graveox.util.Vector2;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

public class AddTool extends Tool
{
	private Vector2				dragStartPosition;
	private Vector2				dragEndPosition;
	private double				forceMultiplier;
	private boolean				dragging;
	
	private Vector2[]			predictorPoints;
	
	private Vector2				mousePos;
	
	// Planet properties
	private double				mass;
	private double				density;
	private double				radius;
	
	// Particle spawner
	private boolean				spawnParticles;
	private double				spawnTimer;
	
	public AddTool()
	{
		super("Add Tool", new AddToolPanel());
		
		this.dragStartPosition = new Vector2();
		this.dragEndPosition = new Vector2();
		this.forceMultiplier = 1.0d;
		this.dragging = false;
		
		this.predictorPoints = new Vector2[400];
		for(int i = 0; i < predictorPoints.length; ++i)
		{
			this.predictorPoints[i] = new Vector2();
		}
		
		this.mousePos = new Vector2();
		
		// mass = volume * density
		// volume = mass / density
		// volume = 4 / 3 * pi * radius^3
		// radius = (3 * volume / (4 * pi))^(1/3)
		
		this.mass = 1000.0d;
		this.density = 1.0d;
		this.radius = Math.cbrt((3.0d * mass / density) / (4.0d * Math.PI));
		
		this.spawnParticles = false;
		this.spawnTimer = 0.0d;
		
		// Event listeners
		((AddToolPanel) super.getPanel()).getMassPropertyField().addValueListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal)
			{
				mass = newVal.doubleValue();
				
				// recalculate radius
				radius = Math.cbrt((3.0d * mass / density) / (4.0d * Math.PI));
			}
		});
		
		((AddToolPanel) super.getPanel()).getDensityPropertyField().addValueListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal)
			{
				density = newVal.doubleValue();
				
				// recalculate radius
				radius = Math.cbrt((3.0d * mass / density) / (4.0d * Math.PI));
			}
		});
		
		// Update tool panel
		((AddToolPanel) super.getPanel()).updateValues(mass, density);
	}

	private void calculatePredictor()
	{
		// Calculate predictor
		double force = Vector2.getDistance(dragStartPosition, dragEndPosition) * forceMultiplier;
		Vector2 velocity = Vector2.getPolar(-force, Vector2.getAngle(dragStartPosition, dragEndPosition));
		
		// Add camera velocity, if tracking body
		if(super.getSimulation().getModel().getCamera().getTrackedBody() != null)
		{
			velocity.subtract(super.getSimulation().getModel().getCamera().getTrackedBody().getVelocity());
		}
		
		Star s = new Star(super.getSimulation().getModel().getCamera().toWorldSpace(dragStartPosition), velocity, mass, 0.0d, 0.0d, 1.0d, radius);
		predictorPoints[0].set(dragStartPosition);
		
		double distance = 0.0d;
		Vector2 oldPos = new Vector2();
		int pointIndex = 1;
		Vector2 gravity;
//		double deltaTime = super.getSimulation().getModel().getTimeScale() / (60.0d * super.getSimulation().getModel().getSimulationSteps());
		double deltaTime = 1.0d / (60.0d * 1.0d);
		double time = 0.0d;
		
		while(pointIndex < predictorPoints.length)
		{
			oldPos.set(s.getPosition());
			
			s.preUpdate(deltaTime);
			
			// Simulate body
			for(RigidBody b : super.getSimulation().getModel().getBodies())
			{
				if(b instanceof Particle)
				{
					continue;
				}
				
				gravity = Physics.getRelativisticGravity(s, b);
//				gravity = Physics.getNewtonianGravity(s, b);
				s.applyForce(gravity);
				
				// check if inside the photon sphere, if so, there is no need to further calculate
				if(Vector2.getDistance(b.getPosition(), s.getPosition()) <= (3.0d * Physics.GRAVITATIONAL_CONSTANT * b.getMass() / Physics.LIGHT_SPEED_SQUARED))
				{
					for(int j = pointIndex; j < predictorPoints.length; ++j)
					{
						predictorPoints[pointIndex++].set(super.getSimulation().getModel().getCamera().toCameraSpace(s.getPosition()));
					}
					return;
				}
			}
			
			s.update(deltaTime);
			
			distance += Vector2.getDistance(oldPos, s.getPosition());
			
			// Place predictor point
			if(distance >= 10.0d)
			{
				distance = 0.0d;
				predictorPoints[pointIndex++].set(super.getSimulation().getModel().getCamera().toCameraSpace(s.getPosition()));
			}
			
			// Max calc time
			if(time > 20.0d)
			{
				while(pointIndex < predictorPoints.length)
				{
					predictorPoints[pointIndex++].set(super.getSimulation().getModel().getCamera().toCameraSpace(s.getPosition()));
				}
				return;
			}
			
			time += deltaTime;
		}
	}
	
	@Override
	public void onMousePressed(MouseEvent e)
	{
		this.dragStartPosition.set(e.getX(), e.getY());
		this.dragEndPosition.set(dragStartPosition);
		this.dragging = true;
	}

	@Override
	public void onMouseReleased(MouseEvent e)
	{
		this.dragEndPosition.set(e.getX(), e.getY());
		this.dragging = false;
		
		// Spawn body
		if(!spawnParticles)
		{
			double force = Vector2.getDistance(dragStartPosition, dragEndPosition) * forceMultiplier;
			Vector2 velocity = Vector2.getPolar(-force, Vector2.getAngle(dragStartPosition, dragEndPosition));
			
			// Add camera velocity, if tracking body
			if(super.getSimulation().getModel().getCamera().getTrackedBody() != null)
			{
				velocity.subtract(super.getSimulation().getModel().getCamera().getTrackedBody().getVelocity());
			}
			
			Star s = new Star(super.getSimulation().getModel().getCamera().toWorldSpace(dragStartPosition), velocity, mass, 0.0d, 0.0d, 1.0d, radius);
			s.attachTrail(new Trail(200));
			
			super.getSimulation().addBody(s);
		}
		
		this.mousePos.set(e.getX(), e.getY());
	}

	@Override
	public void onMouseClicked(MouseEvent e){}

	@Override
	public void onMouseMoved(MouseEvent e)
	{
		this.mousePos.set(e.getX(), e.getY());
	}

	@Override
	public void onMouseDragged(MouseEvent e)
	{
		this.dragEndPosition.set(e.getX(), e.getY());
	}

	@Override
	public void onMouseScrolled(ScrollEvent e)
	{
		double scroll = Math.exp(e.getDeltaY() * 0.001d);
		
		if(dragging)
		{
			// If the user is dragging the mouse to shoot a planet, use the
			// mouse wheel to change the velocity multiplier
			this.forceMultiplier *= scroll;
		}
		else
		{
			super.zoom(scroll);
		}
	}

	@Override
	public void onKeyPressed(KeyEvent e)
	{
		switch(e.getCode())
		{
		case SPACE:
		{
			this.spawnParticles = true;
			
			break;
		}
		default:
		{
			break;
		}
		}
	}

	@Override
	public void onKeyReleased(KeyEvent e)
	{
		switch(e.getCode())
		{
		case SPACE:
		{
			this.spawnParticles = false;
			
			break;
		}
		default:
		{
			break;
		}
		}
	}

	@Override
	public void onKeyTyped(KeyEvent e){}

	@Override
	public void update(double deltaTime)
	{
		if(dragging)
		{
			// Recalculate predictor
			if(!spawnParticles)
			{
				this.calculatePredictor();
			}
			
			if(spawnParticles && spawnTimer > 0.01d)
			{
				Particle p = new Particle();
				p.setMass(mass);
				p.setPosition(super.getSimulation().getModel().getCamera().toWorldSpace(dragStartPosition));
				
				double force = Vector2.getDistance(dragStartPosition, dragEndPosition) * forceMultiplier;
				Vector2 velocity = Vector2.getPolar(-force, Vector2.getAngle(dragStartPosition, dragEndPosition));
				
				p.setVelocity(velocity);
				
				super.getSimulation().addBody(p);
				
				this.spawnTimer = 0.0d;
			}
			
			this.spawnTimer += deltaTime;
		}
	}

	@Override
	public void renderForeground(GraphicsContext g)
	{
		// Draw length scale
		super.drawLengthScale(g);
		
		double scale = super.getSimulation().getModel().getCamera().getScale();
		
		if(dragging)
		{
			// Draw shoot indicator
			g.setStroke(Color.RED);
			g.strokeLine(dragStartPosition.x, dragStartPosition.y, dragEndPosition.x, dragEndPosition.y);
			
			// Draw predictor
			g.setStroke(Color.WHITE);
			for(int i = 0; i < predictorPoints.length - 1; ++i)
			{
				g.strokeLine(predictorPoints[i].x, predictorPoints[i].y, predictorPoints[i + 1].x, predictorPoints[i + 1].y);
			}
			
			// Draw body
			g.setFill(Color.ORANGE);
			g.fillOval(dragStartPosition.x - radius * scale, dragStartPosition.y - radius * scale, radius * 2.0d * scale, radius * 2.0d * scale);
			
			// Draw force strength
			double force = Vector2.getDistance(dragStartPosition, dragEndPosition) * forceMultiplier;
			
			g.setFill(Color.WHITE);
			g.fillText(String.format("Force: %.2f", force), dragStartPosition.x + 5, dragStartPosition.y - 5);
		}
		else
		{
			// Draw body
			g.setFill(Color.ORANGE);
			g.fillOval(mousePos.x - radius * scale, mousePos.y - radius * scale, radius * 2.0d * scale, radius * 2.0d * scale);
		}
	}

	@Override
	public void renderBackground(GraphicsContext g)
	{
		
	}
	
}
