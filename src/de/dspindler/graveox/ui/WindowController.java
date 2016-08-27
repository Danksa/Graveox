package de.dspindler.graveox.ui;

import de.dspindler.graveox.simulation.SimulationController;
import de.dspindler.graveox.simulation.SimulationData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

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
		
		// Attach event handlers
		this.view.getScene().widthProperty().addListener(new WidthListener());
		this.view.getScene().heightProperty().addListener(new HeightListener());
		
		// Initialize simulation
		this.simulation = new SimulationController(new SimulationData());
		this.view.setSimulationView(simulation.getView());
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
		}
	}
	
	private class HeightListener implements ChangeListener<Number>
	{
		@Override
		public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight)
		{
			view.getSimulationView().getCanvas().setHeight(newSceneHeight.doubleValue());
		}
	}
}
