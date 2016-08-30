package de.dspindler.graveox.ui.tools;

import de.dspindler.graveox.simulation.EventListener;
import javafx.scene.canvas.GraphicsContext;

public abstract class Tool extends EventListener
{
	private String				name;
	private String				tooltip;
	
	public Tool(String name, String tooltip)
	{
		this.name = name;
		this.tooltip = tooltip;
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
