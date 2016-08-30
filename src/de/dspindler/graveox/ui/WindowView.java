package de.dspindler.graveox.ui;

import de.dspindler.graveox.simulation.SimulationView;
import de.dspindler.graveox.ui.tools.AddTool;
import de.dspindler.graveox.ui.tools.EditTool;
import de.dspindler.graveox.ui.tools.Tool;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
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
	
	// Toolbar
	private ToolBar			toolbar;
	private ToggleGroup		toolButtonGroup;
	private ToggleButton[]	toolButtons;
	
	public WindowView()
	{
		rootPane = new AnchorPane();
		mainPane = new BorderPane();
		rootPane.getChildren().add(mainPane);
		
		scene = new Scene(rootPane, 1024, 768);
	}
	
	public void initToolbar(Tool[] tools)
	{
		// Initialize button group
		toolButtonGroup = new ToggleGroup();
		
		// Initialize tool buttons
		toolButtons = new ToggleButton[tools.length];
		for(int i = 0; i < toolButtons.length; ++i)
		{
			// Change this to set the icon later
			toolButtons[i] = new ToggleButton(tools[i].getName());
			toolButtons[i].setToggleGroup(toolButtonGroup);
			toolButtons[i].setUserData(tools[i]);
			toolButtons[i].setTooltip(new Tooltip(tools[i].getTooltip()));
			
			// Attach tool's eventlistener
			
			
			// Select first button
			if(i == 0)
			{
				toolButtons[i].setSelected(true);
			}
		}
		
		// Initialize toolbar
		toolbar = new ToolBar(toolButtons);
		
		// Place toolbar in top pane of the mainPane
		mainPane.setTop(toolbar);
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
		this.simulationView.getCanvas().setHeight(simulationView.getCanvas().getHeight() - toolbar.getHeight());
	}
	
	public SimulationView getSimulationView()
	{		
		return simulationView;
	}
	
	public ToolBar getToolbar()
	{
		return toolbar;
	}
	
	public ToggleGroup getButtonGroup()
	{
		return toolButtonGroup;
	}
	
	public Scene getScene()
	{
		return scene;
	}
}
