package de.dspindler.graveox.simulation;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class SimulationView extends Canvas
{
	public SimulationView()
	{
		// Request focus on mouse event, to enable key events
		super.addEventFilter(MouseEvent.ANY, (e) -> super.requestFocus());
	}
	
	public void clear()
	{
		super.getGraphicsContext2D().setFill(Color.BLACK);
		super.getGraphicsContext2D().fillRect(0.0d, 0.0d, super.getWidth(), super.getHeight());
	}
}
