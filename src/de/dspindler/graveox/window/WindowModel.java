package de.dspindler.graveox.window;

import javafx.stage.Stage;

public class WindowModel
{
	private Stage				stage;
	
	public WindowModel(Stage stage)
	{
		this.stage = stage;
	}
	
	public Stage getStage()
	{
		return stage;
	}
}
