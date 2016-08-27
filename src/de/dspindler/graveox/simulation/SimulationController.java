package de.dspindler.graveox.simulation;

import de.dspindler.graveox.simulation.physics.RigidBody;
import de.dspindler.graveox.simulation.physics.Star;
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
		
		data.addBody(new Star());
		((Star) data.getBodies().get(0)).setRadius(10.0d);
		((Star) data.getBodies().get(0)).setPosition(new Vector2(512.0d, 100.0d));
		((Star) data.getBodies().get(0)).setVelocity(new Vector2(100.0d, 0.0d));
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
			// Update each bodies position
			for(RigidBody b : data.getBodies())
			{
				b.preUpdate(deltaTime);
			}
			
			// Apply some forces and stuff here!
			Vector2 force = new Vector2();
			force.set(data.getBodies().get(0).getPosition()).subtract(new Vector2(512.0d, 384.0d));
			force.normalize().scale(-6000000.0d / Vector2.getDistanceSquared(data.getBodies().get(0).getPosition(), new Vector2(512.0d, 384.0d)));
			
			data.getBodies().get(0).applyForce(force);
			
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
//			g.fillRect(0, 0, view.getCanvas().getWidth(), view.getCanvas().getHeight());
			
			// Draw bodies
			for(RigidBody b : data.getBodies())
			{
				b.render(g);
			}
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
