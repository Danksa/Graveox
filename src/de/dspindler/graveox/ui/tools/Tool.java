package de.dspindler.graveox.ui.tools;

import de.dspindler.graveox.simulation.EventListener;
import de.dspindler.graveox.simulation.SimulationController;
import javafx.scene.canvas.GraphicsContext;

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
	
	public abstract void update(double deltaTime);
	public abstract void render(GraphicsContext g);
}
