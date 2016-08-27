package de.dspindler.graveox.simulation;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class SimulationView
{
	private Canvas			canvas;
	private GraphicsContext	context;

	public SimulationView()
	{
		this.canvas = new Canvas(1024, 768);
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
