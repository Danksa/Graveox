package de.dspindler.graveox.simulation;

import java.util.ArrayList;

import de.dspindler.graveox.simulation.physics.Camera;
import de.dspindler.graveox.simulation.physics.RigidBody;
import de.dspindler.graveox.simulation.physics.Trail;
import de.dspindler.graveox.ui.tools.Tool;

public class SimulationData
{
	// Objects
	private ArrayList<RigidBody>			bodies;
	private ArrayList<Trail>				trails;
	private Camera							camera;
	
	// Simulation parameters
	private double							timeScale;
	private int								simulationSteps;
	private double							time;
	
	// Tools
	private Tool[]							tools;
	
	public SimulationData()
	{
		bodies = new ArrayList<RigidBody>();
		trails = new ArrayList<Trail>();
		camera = new Camera();
		
		timeScale = 0.2d;
		simulationSteps = 1;
		time = 0.0d;
	}
	
	public void initTools(Tool[] tools)
	{
		this.tools = tools;
	}
	
	public Tool[] getTools()
	{
		return tools;
	}
	
	public void advanceTime(double deltaTime)
	{
		this.time += deltaTime;
	}
	
	public void setTime(double time)
	{
		this.time = time;
	}
	
	public double getTime()
	{
		return time;
	}
	
	public void setSimulationStepCount(int count)
	{
		this.simulationSteps = count;
	}
	
	public int getSimulationStepCount()
	{
		return simulationSteps;
	}
	
	public void setTimeScale(double scale)
	{
		this.timeScale = scale;
	}
	
	public double getTimeScale()
	{
		return timeScale;
	}
	
	public Camera getCamera()
	{
		return camera;
	}
	
	public ArrayList<RigidBody> getBodies()
	{
		return bodies;
	}
	
	public int getBodyCount()
	{
		return bodies.size();
	}
	
	public void addBody(RigidBody b)
	{
		bodies.add(b);
	}
	
	public void removeBody(RigidBody b)
	{
		bodies.remove(b);
	}
	
	public ArrayList<Trail> getTrails()
	{
		return trails;
	}
	
	public int getTrailCount()
	{
		return trails.size();
	}
	
	public void addTrail(Trail t)
	{
		trails.add(t);
	}
	
	public void removeTrail(Trail t)
	{
		trails.remove(t);
	}
}
