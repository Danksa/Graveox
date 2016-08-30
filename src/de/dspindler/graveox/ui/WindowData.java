package de.dspindler.graveox.ui;

import javafx.stage.Stage;

public class WindowData
{
	private Stage			stage;
	
	private String			versionString;
	
	public WindowData(Stage stage)
	{
		this.stage = stage;
		
		// Hard-coded assignment of version string temporary
		this.versionString = "0.1.0 \"BigBang\"";
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
