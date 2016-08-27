package de.dspindler.graveox.simulation;

import java.util.ArrayList;

import de.dspindler.graveox.simulation.physics.RigidBody;

public class SimulationData
{
	private ArrayList<RigidBody>			bodies;
	
	public SimulationData()
	{
		bodies = new ArrayList<RigidBody>();
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
}
