package de.dspindler.graveox;

import de.dspindler.graveox.simulation.SimulationModel;
import de.dspindler.graveox.simulation.SimulationPresenter;
import de.dspindler.graveox.simulation.tools.AddTool;
import de.dspindler.graveox.simulation.tools.EditTool;
import de.dspindler.graveox.simulation.tools.Tool;
import javafx.application.Application;
import javafx.stage.Stage;

public class Graveox extends Application
{
	private static final Tool[]				TOOLS = new Tool[]{
			new EditTool(),
			new AddTool()
	};
	
	private static SimulationPresenter		simulation;
	
	public static void main(String[] args)
	{
		Application.launch(args);
	}
	
	public static SimulationPresenter getSimulation()
	{
		return simulation;
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		simulation = new SimulationPresenter(new SimulationModel(TOOLS));
		simulation.start(stage);
	}
}
