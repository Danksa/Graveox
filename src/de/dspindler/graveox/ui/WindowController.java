package de.dspindler.graveox.ui;

import de.dspindler.graveox.simulation.SimulationController;
import de.dspindler.graveox.simulation.SimulationData;
import de.dspindler.graveox.ui.tools.Tool;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Toggle;

public class WindowController
{
	// Window attributes
	private WindowData				data;
	private WindowView				view;
	
	// Simulation controller
	private SimulationController	simulation;
	
	public WindowController(WindowData data)
	{
		// Initialize attributes
		this.data = data;
		this.view = new WindowView();
		
		// Initialize simulation
		this.simulation = new SimulationController(new SimulationData());
		
		// Initialize tools
		this.view.initToolbar(simulation.getData().getTools());
		
		// Show simulation
		this.view.setSimulationView(simulation.getView());
		
		// Attach event handlers
		this.view.getScene().widthProperty().addListener(new WidthListener());
		this.view.getScene().heightProperty().addListener(new HeightListener());
		
		// Set toolbar event listener
		this.view.getButtonGroup().selectedToggleProperty().addListener(new ToolbarListener());
	}
	
	public void show()
	{
		view.show(data.getStage(), data.getVersionString());
	}
	
	private class WidthListener implements ChangeListener<Number>
	{
		@Override
		public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth)
		{
			view.getSimulationView().getCanvas().setWidth(newSceneWidth.doubleValue());
			simulation.setWidth(newSceneWidth.doubleValue());
		}
	}
	
	private class HeightListener implements ChangeListener<Number>
	{
		@Override
		public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight)
		{
			view.getSimulationView().getCanvas().setHeight(newSceneHeight.doubleValue());
			
			// Subtract toolbar height from scene height and send it to the simulation
			simulation.setHeight(newSceneHeight.doubleValue() - view.getToolbar().getHeight());
		}
	}
	
	private class ToolbarListener implements ChangeListener<Toggle>
	{
		@Override
		public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle newToggle)
		{
			if(newToggle != null)
			{
				Tool selected = (Tool) view.getButtonGroup().getSelectedToggle().getUserData();
				
				// Select new tool
				simulation.getData().selectTool(selected);
				
				System.out.println("The tool \"" + selected.getName() + "\" was selected!");
			}
		}
	}
}
