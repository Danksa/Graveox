package de.dspindler.graveox.simulation;

import java.util.ArrayList;

import de.dspindler.graveox.simulation.physics.Camera;
import de.dspindler.graveox.simulation.physics.RigidBody;
import de.dspindler.graveox.simulation.tools.Tool;

public class SimulationModel
{
	// Simulation components
	private ArrayList<RigidBody>		bodies;
	private Camera						camera;
	
	private double						timeScale;
	private int							simulationSteps;
	private double						time;
	
	// Tools
	private Tool[]						tools;
	private int							selectedToolIndex;
	
	public SimulationModel(Tool[] tools)
	{
		this.bodies = new ArrayList<RigidBody>();
		this.camera = new Camera();
		
		this.timeScale = 1.0d;
		this.simulationSteps = 1;
		this.time = 0.0d;
		
		this.tools = tools;
		this.selectedToolIndex = 0;
	}
	
	public void setTimeScale(double timeScale)
	{
		this.timeScale = timeScale;
	}
	
	public void scaleTimeScale(double scale)
	{
		this.timeScale *= scale;
	}
	
	public double getTimeScale()
	{
		return timeScale;
	}
	
	public int getSimulationSteps()
	{
		return simulationSteps;
	}
	
	public void advanceTime(double deltaTime)
	{
		this.time += deltaTime;
	}
	
	public double getTime()
	{
		return time;
	}
	
	public void addBody(RigidBody body)
	{
		this.bodies.add(body);
	}
	
	public void removeBody(RigidBody body)
	{
		this.bodies.remove(body);
	}
	
	public RigidBody getBody(int index)
	{
		return bodies.get(index);
	}
	
	public ArrayList<RigidBody> getBodies()
	{
		return bodies;
	}
	
	public int getBodyCount()
	{
		return bodies.size();
	}
	
	public Camera getCamera()
	{
		return camera;
	}
	
	public void selectTool(int index)
	{
		this.selectedToolIndex = index;
	}
	
	public void initTools(SimulationPresenter simulation)
	{
		for(Tool t : tools)
		{
			t.setSimulationInstance(simulation);
		}
	}
	
	public Tool[] getTools()
	{
		return tools;
	}
	
	public Tool getSelectedTool()
	{
		return tools[selectedToolIndex];
	}
}
