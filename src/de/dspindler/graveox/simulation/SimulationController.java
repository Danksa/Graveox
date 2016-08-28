package de.dspindler.graveox.simulation;

import de.dspindler.graveox.simulation.physics.Physics;
import de.dspindler.graveox.simulation.physics.RigidBody;
import de.dspindler.graveox.simulation.physics.Star;
import de.dspindler.graveox.simulation.physics.Trail;
import de.dspindler.graveox.util.Vector2;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SimulationController
{
	private SimulationData			data;
	private SimulationView			view;
	private SimulationTimer			timer;
	
	public SimulationController(SimulationData data)
	{
		this.data = data;
		this.view = new SimulationView();
		this.timer = new SimulationTimer();
		this.timer.start();
		/*
		data.addBody(new Star());
		((Star) data.getBodies().get(0)).setRadius(10.0d);
		((Star) data.getBodies().get(0)).setMass(10000.0d);
		((Star) data.getBodies().get(0)).applyTorque(0.0d);
		((Star) data.getBodies().get(0)).setPosition(new Vector2(512.0d + 0.0d, 384.0d));
		((Star) data.getBodies().get(0)).setVelocity(new Vector2(-10.0d, 0.0d));
		
		data.addBody(new Star());
		((Star) data.getBodies().get(1)).setRadius(10.0d);
		((Star) data.getBodies().get(1)).setMass(10000.0d);
		((Star) data.getBodies().get(1)).applyTorque(0.0d);
		((Star) data.getBodies().get(1)).setPosition(new Vector2(512.0d + 0.0d, 384.0d + 50.0d));
		((Star) data.getBodies().get(1)).setVelocity(new Vector2(10.0d, 0.0d));
		
		data.addTrail(new Trail(data.getBodies().get(1)));
		data.addTrail(new Trail(data.getBodies().get(0)));*/
		
		double v = 10.0d;
		
		/*data.addBody(new Star());
		((Star) data.getBodies().get(0)).setRadius(10.0d);
		((Star) data.getBodies().get(0)).setMass(1.0d);
		((Star) data.getBodies().get(0)).applyTorque(0.0d);
		((Star) data.getBodies().get(0)).setPosition(new Vector2(212.0d, 384.0d));
		((Star) data.getBodies().get(0)).setVelocity(new Vector2(0.0d, 10.0d));
		data.addTrail(new Trail(data.getBodies().get(0)));
		
		data.addBody(new Star());
		((Star) data.getBodies().get(1)).setRadius(50.0d);
		((Star) data.getBodies().get(1)).setMass(10000.0d);
		((Star) data.getBodies().get(1)).applyTorque(0.0d);
		((Star) data.getBodies().get(1)).setPosition(new Vector2(212.0d + 80.0d, 384.0d));
		((Star) data.getBodies().get(1)).setVelocity(new Vector2(0.0d, 0));
		data.addTrail(new Trail(data.getBodies().get(1)));*/
		
		// Test for fun *temporary*
		Vector2 pos = new Vector2(212.0d, 384.0d);
		double vel = 16.0d * 3.2d * 3.2d * 0.19d;
		double angle;
		double rad = 80.0d;
		Vector2 velVec = new Vector2();
		double m = 1000.0d;
		int j = 50;
		for(int i = 0; i < j; ++i)
		{
			angle = (double)(i) / j * Math.PI * 2.0d;
			data.addBody(new Star(pos.clone().add(new Vector2().setPolar(rad, angle)), velVec.setPolar(vel, angle).getRightNormal().translate(0.0d, -v), m, 0.0d, 0.0d, 1.0d, 2.0d));
		}
		
		pos.translate(300.0d, 0.0d);
		for(int i = 0; i < j; ++i)
		{
			angle = (double)(i) / j * Math.PI * 2.0d;
			data.addBody(new Star(pos.clone().add(new Vector2().setPolar(rad, angle)), velVec.setPolar(vel, angle).getRightNormal().translate(0.0d, v), m, 0.0d, 0.0d, 1.0d, 2.0d));
		}
	}
	
	public SimulationView getView()
	{
		return view;
	}
	
	private class SimulationTimer extends AnimationTimer
	{
		private double				timeScale;
		private double				time;
		private int					epoch;
		private int					simulationSteps;
		private double				deltaTime;
		
		public SimulationTimer()
		{
			this.timeScale = 1.0d;
			this.time = 0.0d;
			this.epoch = 0;
			this.simulationSteps = 1;
			this.deltaTime = 0.0d;
		}
		
		private void update(double deltaTime, double time)
		{	
			// Update each bodies position and "half" velocity
			for(RigidBody b : data.getBodies())
			{
				b.preUpdate(deltaTime);
			}
			
			// Update trails
			for(Trail t : data.getTrails())
			{
				t.update(deltaTime);
			}
			
			// Apply some forces and stuff here!
			for(RigidBody a : data.getBodies())
			{
				for(RigidBody b : data.getBodies())
				{
					if(a != b)
					{
//						Physics.applyNewtonianGravity(a, b);
						Physics.applyRelativisticGravity(a, b);
					}
				}
			}
			
			RigidBody b1, b2;
			for(int i = 0; i < data.getBodyCount(); ++i)
			{
				b1 = data.getBodies().get(i);
				for(int j = i + 1; j < data.getBodyCount(); ++j)
				{
					b2 = data.getBodies().get(j);
					
					// Check collision
					if(b1 instanceof Star && b2 instanceof Star)
					{
						if(Vector2.getDistance(b1.getPosition(), b2.getPosition()) <= ((Star) b1).getRadius() + ((Star) b2).getRadius())
						{
							Physics.applyCollisionImpulse(b1, b2, deltaTime);
						}
					}
				}
			}
			
			// Update each body
			for(RigidBody b : data.getBodies())
			{
				b.update(deltaTime, time);
			}
		}
		
		private void render(GraphicsContext g)
		{
			// Clear canvas
			g.setFill(Color.BLACK);
			g.fillRect(0, 0, view.getCanvas().getWidth(), view.getCanvas().getHeight());
			
			// Draw trails
			for(Trail t : data.getTrails())
			{
				t.render(g);
			}
			
			// Draw bodies
			for(RigidBody b : data.getBodies())
			{
				b.render(g);
			}
			
			g.setFill(Color.WHITE);
			g.fillText("Vel: " + data.getBodies().get(0).getVelocity().getMagnitude(), 2, 10);
			
			double energy = 0.0d;
			RigidBody a = data.getBodies().get(0);
			RigidBody b = data.getBodies().get(1);
			double dist = Vector2.getDistance(a.getPosition(), b.getPosition());
			
			energy += 0.5d * a.getMass() * a.getVelocity().getMagnitudeSquared();
//			energy += a.getMass() * b.getMass() * Physics.GRAVITATIONAL_CONSTANT / (dist);
			
			energy += 0.5d * b.getMass() * b.getVelocity().getMagnitudeSquared();
//			energy += a.getMass() * b.getMass() * Physics.GRAVITATIONAL_CONSTANT / (dist);
			
			g.fillText("E: " + energy, 2, 20);
			g.fillText("AngVel: " + data.getBodies().get(1).getAngularVelocity(), 2, 30);
		}
		
		@Override
		public void handle(long now)
		{
			deltaTime = timeScale / (60.0d * simulationSteps);
			
			for(int i = 0; i < simulationSteps; ++i)
			{
				update(deltaTime, time);
			}
			render(view.getContext());
			
			time = epoch * deltaTime;
		}	
	}
}
