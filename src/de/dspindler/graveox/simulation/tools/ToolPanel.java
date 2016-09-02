package de.dspindler.graveox.simulation.tools;

import javafx.scene.control.TitledPane;

public abstract class ToolPanel extends TitledPane
{	
	public ToolPanel(String title)
	{
		super();
		super.setText(title);
		super.setCollapsible(false);
		super.setPrefWidth(300.0d);
		super.setPrefHeight(USE_COMPUTED_SIZE);
	}
}
