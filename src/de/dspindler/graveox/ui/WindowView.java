package de.dspindler.graveox.ui;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class WindowView
{
	private Scene			scene;
	
	public WindowView()
	{
		
	}
	
	public void show(Stage stage)
	{
		stage.setTitle("Graveox");
		stage.setScene(scene);
		stage.show();
	}
}
