package de.dspindler.graveox.ui.tools;

import de.dspindler.graveox.simulation.EventListener;
import de.dspindler.graveox.simulation.SimulationController;
import de.dspindler.graveox.util.UnitUtil;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Tool extends EventListener
{
	private String					name;
	private String					tooltip;
	protected SimulationController	simulation;
	
	public Tool(String name, String tooltip, SimulationController simulation)
	{
		this.name = name;
		this.tooltip = tooltip;
		this.simulation = simulation;
		
		// Disable listener as default
		super.setEnabled(false);
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getTooltip()
	{
		return tooltip;
	}
	
	protected void zoom(double zoom)
	{
		simulation.getData().getCamera().scale(zoom);
	}
	
	protected void drawLengthScale(GraphicsContext g)
	{
		double scale = simulation.getData().getCamera().getSmoothedScale();
		
		// Padding from right and bottom edge
		double padding = 20.0d;
		
		double minLength = 20.0d; // pixel
		double maxLength = 200.0d; // pixel
		double length = maxLength * 0.5d * scale;
		
		int power = 0;
		
		while(length < minLength)
		{
			++power;
			length *= 10.0d;
		}
		
		while(length > maxLength)
		{
			--power;
			length *= 0.1d;
		}
		
		// Position values for convenience
		double x1 = simulation.getData().getCamera().getSpaceSize().x * 2.0d - padding - 50.0d;
		double x0 = x1 - length;
		double y = simulation.getData().getCamera().getSpaceSize().y * 2.0d - padding;
		
		// Draw scale
		g.setStroke(Color.WHITE);
		g.strokeLine(x0, y, x1, y);
		g.strokeLine(x0, y, x0, y - 5.0d);
		g.strokeLine(x1, y, x1, y - 5.0d);
		
		// Draw length text
		g.setFill(Color.WHITE);
		g.fillText(UnitUtil.toUnitString(100.0d * Math.pow(10.0d, power), 0) + "px", x1 + 15.0d, y);
	}
	
	public abstract void update(double deltaTime);
	
	public abstract void renderForeground(GraphicsContext g);
	public abstract void renderBackground(GraphicsContext g);
}
