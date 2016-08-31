package de.dspindler.graveox.simulation;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class SimulationView
{
	private Canvas		canvas;
	private GraphicsContext		context;

	public SimulationView()
	{
		this.canvas = new Canvas(1024, 768);
		
		// Request focus on click, so that it can receive key events
		this.canvas.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>()
				{
					@Override
					public void handle(MouseEvent e)
					{
						canvas.requestFocus();
					}			
				});
		
		this.context = canvas.getGraphicsContext2D();
	}
	
	public GraphicsContext getContext()
	{
		return context;
	}
	
	public Canvas getCanvas()
	{
		return canvas;
	}
}
