package de.dspindler.graveox.ui;

import de.dspindler.graveox.simulation.SimulationController;
import de.dspindler.graveox.simulation.SimulationData;
import de.dspindler.graveox.simulation.SimulationView;
import de.dspindler.graveox.ui.tools.EmptyToolPanel;
import de.dspindler.graveox.ui.tools.Tool;
import de.dspindler.graveox.ui.tools.ToolPanel;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
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
	private AnchorPane		mainPane;
	
	// Simulation
	private SimulationView	simulationView;
	private AnchorPane		simulationPane;
	
	// Toolbar
	private ToolBar			toolbar;
	private ToggleGroup		toolButtonGroup;
	private ToggleButton[]	toolButtons;
	
	// Tool panel
	private ToolPanel		toolPanel;
	
	public WindowView()
	{
		rootPane = new AnchorPane();
		mainPane = new AnchorPane();
		AnchorPane.setTopAnchor(mainPane, 0.0d);
		AnchorPane.setBottomAnchor(mainPane, 0.0d);
		AnchorPane.setLeftAnchor(mainPane, 0.0d);
		AnchorPane.setRightAnchor(mainPane, 0.0d);
		
		rootPane.getChildren().add(mainPane);
		
		scene = new Scene(rootPane, 1024 + 200, 768);
	}
	
	public void init(SimulationController controller)
	{
		setSimulationView(controller.getView());
		initToolbar(controller.getData().getTools());
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
			
			// Select first button
			if(i == 0)
			{
				toolButtons[i].setSelected(true);
				
				// Display corresponding tool panel
				this.setToolPanel(tools[i].getPanel());
			}
		}
		
		// Initialize toolbar
		toolbar = new ToolBar(toolButtons);
		toolbar.getItems().add(new Separator());
		
		// Place toolbar in top pane of the mainPane
		AnchorPane.setTopAnchor(toolbar, 0.0d);
		AnchorPane.setLeftAnchor(toolbar, 0.0d);
		AnchorPane.setRightAnchor(toolbar, 0.0d);
		toolbar.minHeight(36.0d);
		toolbar.prefHeight(36.0d);
		toolbar.maxHeight(36.0d);
		mainPane.getChildren().add(toolbar);
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
		
		AnchorPane.setTopAnchor(simulationView.getCanvas(), 36.0d);
		AnchorPane.setLeftAnchor(simulationView.getCanvas(), 0.0d);
		AnchorPane.setRightAnchor(simulationView.getCanvas(), 200.0d);
		AnchorPane.setBottomAnchor(simulationView.getCanvas(), 0.0d);
		this.mainPane.getChildren().add(simulationView.getCanvas());
	}
	
	public void setToolPanel(ToolPanel panel)
	{
		if(panel == null)
		{
			panel = new EmptyToolPanel();
		}
		
		this.toolPanel = panel;
		this.toolPanel.minWidth(200.0d);
		this.toolPanel.prefWidth(200.0d);
		this.toolPanel.maxWidth(200.0d);
		
		AnchorPane.setTopAnchor(panel, 36.0d);
		AnchorPane.setRightAnchor(panel, 0.0d);
		AnchorPane.setBottomAnchor(panel, 0.0d);
		this.mainPane.getChildren().add(panel);
	}
	
	public ToolPanel getToolPanel()
	{
		return toolPanel;
	}
	
	public AnchorPane getMainPane()
	{
		return mainPane;
	}
	
	public AnchorPane getRootPane()
	{
		return rootPane;
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
