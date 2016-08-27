package de.dspindler.graveox;

import de.dspindler.graveox.ui.WindowController;
import de.dspindler.graveox.ui.WindowData;
import javafx.application.Application;
import javafx.stage.Stage;

public class Graveox extends Application
{
	private WindowController		window;
	
	public static void main(String[] args)
	{
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		window = new WindowController(new WindowData(stage));
		window.show();
	}
}
