package de.dspindler.graveox.ui.tools;

import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

public abstract class ToolPanel extends TitledPane
{
	private String				toolName;
	
	private AnchorPane			contentPane;
	
	public ToolPanel(String toolName)
	{
		this.toolName = toolName;
		this.contentPane = new AnchorPane();
		
		this.init();
	}
	
	private void init()
	{
		super.setText(toolName);
		super.setCollapsible(false);
		
		super.setMinWidth(200.0d);
		
		super.getChildren().add(contentPane);
	}
	
	protected AnchorPane getContentPane()
	{
		return contentPane;
	}
}
