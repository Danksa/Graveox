package de.dspindler.graveox.ui;

import de.dspindler.graveox.simulation.SimulationView;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class WindowView
{
	private Scene			scene;
	
	// Root pane
	private AnchorPane		rootPane;
	
	// Basic layout
	private BorderPane		mainPane;
	
	// Simulation
	private SimulationView	simulationView;
	
	public WindowView()
	{
		rootPane = new AnchorPane();
		
		mainPane = new BorderPane();

		rootPane.getChildren().add(mainPane);
		
		scene = new Scene(rootPane, 1024, 768);
	}
	
	public void show(Stage stage, String versionString)
	{
		stage.setTitle("Graveox [v" + versionString + "]");
		stage.setScene(scene);
		stage.show();
	}
	
	public void setSimulationView(SimulationView simulationView)
	{
		this.simulationView = simulationView;
		this.mainPane.setCenter(this.simulationView.getCanvas());
	}
	
	public SimulationView getSimulationView()
	{
		return simulationView;
	}
	
	public Scene getScene()
	{
		return scene;
	}
}
