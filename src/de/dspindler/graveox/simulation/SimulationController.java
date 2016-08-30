package de.dspindler.graveox.simulation;

import de.dspindler.graveox.simulation.physics.Camera;
import de.dspindler.graveox.simulation.physics.Physics;
import de.dspindler.graveox.simulation.physics.RigidBody;
import de.dspindler.graveox.simulation.physics.Star;
import de.dspindler.graveox.simulation.physics.Trail;
import de.dspindler.graveox.ui.Grid;
import de.dspindler.graveox.util.Vector2;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SimulationController
{
	private SimulationData			data;
	private SimulationView			view;
	private SimulationTimer			timer;
	private SimulationHandler		handler;
	
	public SimulationController(SimulationData data)
	{
		this.data = data;
		this.view = new SimulationView();
		this.timer = new SimulationTimer();
		this.handler = new SimulationHandler();
		
		// Attach event listeners
		view.getCanvas().setOnMouseClicked(handler.new MouseClicked());
		view.getCanvas().setOnMousePressed(handler.new MousePressed());
		view.getCanvas().setOnMouseReleased(handler.new MouseReleased());
		view.getCanvas().setOnMouseDragged(handler.new MouseDragged());
		view.getCanvas().setOnMouseMoved(handler.new MouseMoved());
		view.getCanvas().setOnKeyPressed(handler.new KeyPressed());
		view.getCanvas().setOnKeyReleased(handler.new KeyReleased());
		view.getCanvas().setOnKeyTyped(handler.new KeyTyped());
		
		// Test bodies
		data.addBody(new Star());
		((Star) data.getBodies().get(0)).setRadius(5.0d);
		((Star) data.getBodies().get(0)).setMass(20.0d);
		((Star) data.getBodies().get(0)).applyTorque(0.0d);
		((Star) data.getBodies().get(0)).setPosition(new Vector2(212.0d + 280.0d, 384.0d - 0));
		((Star) data.getBodies().get(0)).setVelocity(new Vector2(0.0d, 538.598d));
		data.addTrail(new Trail(data.getBodies().get(0), 200));
		
		data.addBody(new Star());
		((Star) data.getBodies().get(1)).setRadius(10.0d);
		((Star) data.getBodies().get(1)).setMass(6000000.0d);
		((Star) data.getBodies().get(1)).applyTorque(0.0d);
		((Star) data.getBodies().get(1)).setPosition(new Vector2(212.0d  + 200.0d, 384.0d - 0));
		((Star) data.getBodies().get(1)).setVelocity(new Vector2(0.0d, 0.0d));
		data.addTrail(new Trail(data.getBodies().get(1)));
		
		// Start simulation
		this.timer.start();
	}
	
	public SimulationView getView()
	{
		return view;
	}
	
	private class SimulationTimer extends AnimationTimer
	{
		// Timer attributes
		private double				timeScale;
		private double				time;
		private int					epoch;
		private int					simulationSteps;
		private double				deltaTime;
		
		// Testing some things
		private double				prevDir;
		private Vector2				apoapsis;
		private Vector2				periapsis;
		private boolean[]			quarter = new boolean[]{false, false, false, false, false};
		private int					cycles = 0;
		
		// Another test!
		private Grid				grid;
		private Grid				grid2;
		private Camera				camera = new Camera(new Vector2(512.0d, 384.0d));
		
		public SimulationTimer()
		{
			this.timeScale = 0.1d;
			this.time = 0.0d;
			this.epoch = 0;
			this.simulationSteps = 80;
			this.deltaTime = 0.0d;
			
			prevDir = 0.0d;
			apoapsis = new Vector2();
			periapsis = new Vector2();
			
			grid = new Grid(new Vector2(), 200.0d, 6, 6);
			grid2 = new Grid(new Vector2(), 200.0d, 6, 6);
		}
		
		private void update(double deltaTime, double time)
		{	
			// Update each bodies position and "half" velocity
			for(RigidBody b : data.getBodies())
			{
				b.preUpdate(deltaTime);
			}
			
			// Update trails
			for(Trail t : data.getTrails())
			{
				t.update(deltaTime);
			}
			
			// Apply some forces and stuff here!
			for(RigidBody a : data.getBodies())
			{
				for(RigidBody b : data.getBodies())
				{
					if(a != b)
					{
//						Physics.applyNewtonianGravity(a, b);
						Physics.applyRelativisticGravity(a, b);
					}
				}
			}
			
			RigidBody b1, b2;
			for(int i = 0; i < data.getBodyCount(); ++i)
			{
				b1 = data.getBodies().get(i);
				for(int j = i + 1; j < data.getBodyCount(); ++j)
				{
					b2 = data.getBodies().get(j);
					
					// Check collision
					if(b1 instanceof Star && b2 instanceof Star)
					{
						if(Vector2.getDistance(b1.getPosition(), b2.getPosition()) <= ((Star) b1).getRadius() + ((Star) b2).getRadius())
						{
							Physics.applyCollisionImpulse(b1, b2, deltaTime);
						}
					}
				}
			}
			
			// Update each body
			for(RigidBody b : data.getBodies())
			{
				b.update(deltaTime, time);
			}
			
			// TEST
			grid.setCenter(data.getBodies().get(0).getPosition());
			grid2.setCenter(data.getBodies().get(1).getPosition());
			
			if(camera.getTrackedBody() == null && data.getBodyCount() > 0)
			{
//				camera.setTrackedBody(data.getBodies().get(0));
			}
			
//			camera.scale(1.0d / 1.00001d);
			camera.update(deltaTime);
		}
		
		private void render(GraphicsContext g)
		{
			// Clear canvas
			g.setFill(Color.BLACK);
			g.fillRect(0, 0, view.getCanvas().getWidth(), view.getCanvas().getHeight());
			
			g.save();
			camera.applyTransform(g);
			
			// TEST
//			grid.render(g);
			grid2.render(g);
			
			// Draw trails
			for(Trail t : data.getTrails())
			{
				t.render(g);
			}
			
			// Draw bodies
			for(RigidBody b : data.getBodies())
			{
				b.render(g);
			}
			
			// Test
			RigidBody a = data.getBodies().get(0);
			RigidBody b = data.getBodies().get(1);
			
			Vector2 axis = b.getPosition().clone().subtract(a.getPosition());
			axis = axis.getLeftNormal();
			axis.scale(100.0d / axis.getMagnitude());
			
			Vector2 vel = b.getVelocity().clone().subtract(a.getVelocity());
			vel.scale(-100.0d / vel.getMagnitude());
			
			Vector2 diff = vel.clone().subtract(axis);
			Vector2 normal = axis.getLeftNormal().normalize();
			
			g.setStroke(Color.WHITE);
			g.strokeLine(a.getPosition().x + axis.x, a.getPosition().y + axis.y, a.getPosition().x, a.getPosition().y);
			
			g.setStroke(Color.GREEN);
			g.strokeLine(a.getPosition().x + vel.x, a.getPosition().y + vel.y, a.getPosition().x, a.getPosition().y);
			
			g.setStroke(Color.AQUAMARINE);
			g.strokeLine(a.getPosition().x + axis.x + diff.x, a.getPosition().y + axis.y + diff.y, a.getPosition().x + axis.x, a.getPosition().y + axis.y);
			
			double dir = diff.dot(normal);
			
			if(prevDir >= 0.0d && dir < 0.0d)
			{
				apoapsis.set(a.getPosition());
			}
			else if(prevDir <= 0.0d && dir > 0.0d)
			{
				periapsis.set(a.getPosition());
			}
			
			// Test
			prevDir = dir;
			double ang = Vector2.getAngle(new Vector2(512, 384), a.getPosition());
			if(ang >= 0.0d && ang < Math.PI * 0.5d)
			{
				quarter[0] = true;
//				System.out.println("1");
			}
			if(ang >= Math.PI * 0.5d && ang < Math.PI)
			{
				quarter[1] = true;
//				System.out.println("2");
			}
			if(ang >= -Math.PI && ang < -Math.PI * 0.5d)
			{
				quarter[2] = true;
//				System.out.println("3");
			}
			if(ang >= -Math.PI * 0.5d && ang < 0.0d)
			{
				quarter[3] = true;
//				System.out.println("4");
			}
			
			if(quarter[0] && quarter[1] && quarter[2] && quarter[3])
			{
				if(quarter[0] && quarter[4])
				{
					quarter[0] = false;
					quarter[1] = false;
					quarter[2] = false;
					quarter[3] = false;
					quarter[4] = false;
					
					++cycles;
				}
				else if(quarter[0] && !quarter[4])
				{
					if(ang >= 0.0d && ang < Math.PI * 0.5d)
					{
						quarter[4] = true;
//						System.out.println("1");
					}
				}
			}
			
			g.setStroke(Color.ORANGE);
			g.strokeLine(b.getPosition().x, b.getPosition().y, apoapsis.x, apoapsis.y);
			
			g.setStroke(Color.AQUAMARINE);
			g.strokeLine(b.getPosition().x, b.getPosition().y, periapsis.x, periapsis.y);
			
			// Restore camera transform
			g.restore();
			
			g.setFill(Color.WHITE);
			
			g.fillText("Dir: " + dir, 2, 40);
			
			g.fillText("Vel: " + data.getBodies().get(0).getVelocity().getMagnitude(), 2, 10);
			double gamma = 1.0d / Math.sqrt(1.0d - data.getBodies().get(0).getVelocity().getMagnitudeSquared() / Physics.LIGHT_SPEED_SQUARED);
			g.fillText("L-Factor: " + gamma, 2, 20);
			g.fillText("Cycles: " + cycles, 2, 50);
			
			Vector2 pos = new Vector2(512.0d - 100.0d, 384.0d);
			g.fillText("Local: " + pos + ", World: " + camera.toWorldSpace(pos), 2, 100);
			
			g.setFill(Color.WHITE);
			g.fillRect(512 - 2, 384 - 2, 4, 4);
			
			pos.set(500.0d, 0.0d);
			pos.set(camera.toCameraSpace(pos));
			g.setFill(Color.AQUAMARINE);
			g.fillRect(pos.x - 2, pos.y - 2, 4, 4);
		}
		
		@Override
		public void handle(long now)
		{
			deltaTime = timeScale / (60.0d * simulationSteps);
			
			for(int i = 0; i < simulationSteps; ++i)
			{
				update(deltaTime, time);
			}
			render(view.getContext());
			
			time = epoch * deltaTime;
		}	
	}
}
