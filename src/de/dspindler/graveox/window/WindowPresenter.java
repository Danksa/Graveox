package de.dspindler.graveox.window;

import java.util.ArrayList;

import de.dspindler.graveox.simulation.SimulationView;
import de.dspindler.graveox.simulation.tools.Tool;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Toggle;

public class WindowPresenter
{
	private WindowModel						model;
	private WindowView						view;
	
	private ArrayList<WindowListener>		listeners;
	
	public WindowPresenter(WindowModel model)
	{
		this.model = model;
		this.view = new WindowView();
		
		this.listeners = new ArrayList<WindowListener>();
	}
	
	private void initEventHandlers()
	{
		// Tool selected event
		this.view.getToolGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			@Override
			public void changed(ObservableValue<? extends Toggle> ov, Toggle oldToggle, Toggle newToggle)
			{
				if(newToggle == null)
				{
					return;
				}
				
				for(WindowListener l : listeners)
				{
					l.toolSelected((int) newToggle.getUserData());
				}
			}
		});
	}
	
	public void show()
	{
		this.view.show(model.getStage());
		this.initEventHandlers();
	}
	
	public void addListener(WindowListener listener)
	{
		this.listeners.add(listener);
	}
	
	public void setSimulationView(SimulationView view)
	{
		this.view.setSimulationView(view);
	}
	
	public void setTools(Tool[] tools)
	{
		this.view.setTools(tools);
	}
	
	public void selectTool(Tool tool)
	{
		System.out.println("[WindowPresenter][Selected Tool: \"" + tool.getName() + "\"!]");
		this.view.setToolPanel(tool.getPanel());
	}
}
