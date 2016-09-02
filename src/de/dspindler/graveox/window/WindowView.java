package de.dspindler.graveox.window;

import de.dspindler.graveox.simulation.SimulationView;
import de.dspindler.graveox.simulation.tools.Tool;
import de.dspindler.graveox.simulation.tools.ToolPanel;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class WindowView
{
	// Scene
	private Scene			scene;
	
	// Root pane
	private AnchorPane		rootPane;
	
	// Tool bar components
	private ToolBar			toolbar;
	private ToggleButton[]	toolButtons;
	private ToggleGroup		toolGroup;
	
	// Simulation view pane
	private AnchorPane		simulationPane;
	
	// Tool pane
	private AnchorPane		toolPane;
	
	public WindowView()
	{
		this.rootPane = new AnchorPane();
		
		this.initToolbar();
		this.initToolPane();
		this.initSimulationPane();
		
		this.scene = new Scene(rootPane);
	}
	
	public void show(Stage stage)
	{
		stage.setTitle("Graveox [v0.1.0]");
		stage.setScene(scene);
		stage.show();
	}
	
	private void initToolbar()
	{
		this.toolbar = new ToolBar();
		
		// Set tool bar height
		this.toolbar.setMinHeight(36.0d);
		this.toolbar.setPrefHeight(36.0d);
		this.toolbar.setMaxHeight(36.0d);
		
		// Set anchors
		AnchorPane.setTopAnchor(toolbar, 0.0d);
		AnchorPane.setLeftAnchor(toolbar, 0.0d);
		AnchorPane.setRightAnchor(toolbar, 0.0d);
		
		this.rootPane.getChildren().add(toolbar);
	}
	
	private void initToolPane()
	{
		this.toolPane = new AnchorPane();
		
		// Set width
		this.toolPane.setMinWidth(300.0d);
		this.toolPane.setPrefWidth(300.0d);
		this.toolPane.setMaxWidth(300.0d);
		
		// Set anchors
		AnchorPane.setTopAnchor(toolPane, toolbar.getPrefHeight());
		AnchorPane.setRightAnchor(toolPane, 0.0d);
		AnchorPane.setBottomAnchor(toolPane, 0.0d);
		
		this.rootPane.getChildren().add(toolPane);
	}
	
	private void initSimulationPane()
	{
		this.simulationPane = new AnchorPane();
		
		// Set anchors
		AnchorPane.setTopAnchor(simulationPane, toolbar.getPrefHeight());
		AnchorPane.setLeftAnchor(simulationPane, 0.0d);
		AnchorPane.setRightAnchor(simulationPane, toolPane.getPrefWidth());
		AnchorPane.setBottomAnchor(simulationPane, 0.0d);
		
		this.simulationPane.setMinWidth(600.0d);
		this.simulationPane.setMinHeight(600.0d);
		
		this.rootPane.getChildren().add(simulationPane);
	}
	
	public void setTools(Tool[] tools)
	{
		this.toolGroup = new ToggleGroup();
		this.toolButtons = new ToggleButton[tools.length];
		
		for(int i = 0; i < tools.length; ++i)
		{
			this.toolButtons[i] = new ToggleButton(tools[i].getName());
			this.toolButtons[i].setToggleGroup(toolGroup);
			this.toolButtons[i].setUserData(i);
			
			this.addToolbarItem(toolButtons[i]);
		}
		this.toolButtons[0].setSelected(true);
	}
	
	public void addToolbarItem(Node node)
	{
		this.toolbar.getItems().add(node);
	}
	
	public void setSimulationView(SimulationView view)
	{
		view.widthProperty().bind(simulationPane.widthProperty());
		view.heightProperty().bind(simulationPane.heightProperty());
		
		// Remove previous simulation view, if necessary
		if(simulationPane.getChildren().size() > 0)
		{
			simulationPane.getChildren().clear();
		}
		
		// Add simulation view
		this.simulationPane.getChildren().add(view);
	}
	
	public void setToolPanel(ToolPanel panel)
	{
		// Remove old tool panel, if necessary
		if(toolPane.getChildren().size() > 0)
		{
			toolPane.getChildren().clear();
		}
		
		// Add tool panel
		this.toolPane.getChildren().add(panel);
	}
	
	public ToggleGroup getToolGroup()
	{
		return toolGroup;
	}
}
