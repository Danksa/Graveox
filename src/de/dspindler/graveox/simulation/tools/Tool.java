package de.dspindler.graveox.simulation.tools;

import de.dspindler.graveox.simulation.EventListener;
import de.dspindler.graveox.simulation.SimulationPresenter;
import de.dspindler.graveox.util.UnitUtil;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Tool implements EventListener
{
	// Name of the tool
	private String					name;
	
	// Instance of simulation
	private SimulationPresenter		simulation;
	
	// Corresponding tool panel
	private ToolPanel				panel;
	
	public Tool(String name, ToolPanel panel)
	{
		this.name = name;
		this.panel = panel;
		this.simulation = null;
	}
	
	public void setSimulationInstance(SimulationPresenter simulation)
	{
		this.simulation = simulation;
	}
	
	protected SimulationPresenter getSimulation()
	{
		return simulation;
	}
	
	protected void zoom(double zoom)
	{
		simulation.getModel().getCamera().scale(zoom);
	}
	
	protected void drawLengthScale(GraphicsContext g)
	{
		double scale = simulation.getModel().getCamera().getSmoothedScale();
		
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
		double x1 = simulation.getModel().getCamera().getSpaceSize().x * 2.0d - padding - 50.0d;
		double x0 = x1 - length;
		double y = simulation.getModel().getCamera().getSpaceSize().y * 2.0d - padding;
		
		// Draw scale
		g.setStroke(Color.WHITE);
		g.strokeLine(x0, y, x1, y);
		g.strokeLine(x0, y, x0, y - 5.0d);
		g.strokeLine(x1, y, x1, y - 5.0d);
		
		int decimals = 0;
		if(power < -2)
		{
			decimals = 3;
		}
		
		// Draw length text
		g.setFill(Color.WHITE);
		g.fillText(UnitUtil.toUnitString(100.0d * Math.pow(10.0d, power), decimals) + "px", x1 + 15.0d, y);
	}
	
	public abstract void update(double deltaTime);
	
	public abstract void renderForeground(GraphicsContext g);
	public abstract void renderBackground(GraphicsContext g);
	
	public String getName()
	{
		return name;
	}
	
	public ToolPanel getPanel()
	{
		return panel;
	}
}

//package de.dspindler.graveox.ui.tools;
//
//import de.dspindler.graveox.simulation.EventListener;
//import de.dspindler.graveox.simulation.SimulationController;
//import de.dspindler.graveox.util.UnitUtil;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.paint.Color;
//
//public abstract class Tool extends EventListener implements ToolPanelListener
//{
//	// Tool properties
//	private String					name;
//	private String					tooltip;
//	
//	// Simulation instance
//	protected SimulationController	simulation;
//	
//	public Tool(String name, String tooltip, SimulationController simulation)
//	{
//		this.name = name;
//		this.tooltip = tooltip;
//		this.simulation = simulation;
//		
//		// Disable listener as default
//		super.setEnabled(false);
//	}
//	
//	public String getName()
//	{
//		return name;
//	}
//	
//	public String getTooltip()
//	{
//		return tooltip;
//	}
//	
//	protected void zoom(double zoom)
//	{
//		simulation.getData().getCamera().scale(zoom);
//	}
//	
//	protected void drawLengthScale(GraphicsContext g)
//	{
//		double scale = simulation.getData().getCamera().getSmoothedScale();
//		
//		// Padding from right and bottom edge
//		double padding = 20.0d;
//		
//		double minLength = 20.0d; // pixel
//		double maxLength = 200.0d; // pixel
//		double length = maxLength * 0.5d * scale;
//		
//		int power = 0;
//		
//		while(length < minLength)
//		{
//			++power;
//			length *= 10.0d;
//		}
//		
//		while(length > maxLength)
//		{
//			--power;
//			length *= 0.1d;
//		}
//		
//		// Position values for convenience
//		double x1 = simulation.getData().getCamera().getSpaceSize().x * 2.0d - padding - 50.0d;
//		double x0 = x1 - length;
//		double y = simulation.getData().getCamera().getSpaceSize().y * 2.0d - padding;
//		
//		// Draw scale
//		g.setStroke(Color.WHITE);
//		g.strokeLine(x0, y, x1, y);
//		g.strokeLine(x0, y, x0, y - 5.0d);
//		g.strokeLine(x1, y, x1, y - 5.0d);
//		
//		// Draw length text
//		g.setFill(Color.WHITE);
//		g.fillText(UnitUtil.toUnitString(100.0d * Math.pow(10.0d, power), 0) + "px", x1 + 15.0d, y);
//	}
//	
//	public abstract void update(double deltaTime);
//	
//	public abstract void renderForeground(GraphicsContext g);
//	public abstract void renderBackground(GraphicsContext g);
//}
