package de.dspindler.graveox.simulation;

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
			
		}
		
		private void render(GraphicsContext g)
		{
			g.setFill(Color.BLACK);
			g.fillRect(0, 0, view.getCanvas().getWidth(), view.getCanvas().getHeight());
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
