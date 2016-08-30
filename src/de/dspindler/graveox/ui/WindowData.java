package de.dspindler.graveox.ui;

import de.dspindler.graveox.ui.tools.Tool;
import javafx.stage.Stage;

public class WindowData
{
	private Stage			stage;
	
	private String			versionString;
	
	private Tool[]			tools;
	
	public WindowData(Stage stage)
	{
		this.stage = stage;
		
		// Hard-coded assignment of version string temporary
		this.versionString = "0.1.0 \"BigBang\"";
	}
	
	public void setTools(Tool[] tools)
	{
		this.tools = tools;
	}
	
	public Tool[] getTools()
	{
		return tools;
	}
	
	public Stage getStage()
	{
		return stage;
	}
	
	public String getVersionString()
	{
		return versionString;
	}
}
