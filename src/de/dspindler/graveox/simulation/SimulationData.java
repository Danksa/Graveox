package de.dspindler.graveox.simulation;

import java.util.ArrayList;

import de.dspindler.graveox.simulation.physics.RigidBody;
import de.dspindler.graveox.simulation.physics.Trail;

public class SimulationData
{
	private ArrayList<RigidBody>			bodies;
	private ArrayList<Trail>				trails;
	
	public SimulationData()
	{
		bodies = new ArrayList<RigidBody>();
		trails = new ArrayList<Trail>();
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
