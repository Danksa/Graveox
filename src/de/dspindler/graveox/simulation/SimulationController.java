package de.dspindler.graveox.simulation;

import de.dspindler.graveox.simulation.physics.Physics;
import de.dspindler.graveox.simulation.physics.RigidBody;
import de.dspindler.graveox.simulation.physics.Star;
import de.dspindler.graveox.simulation.physics.Trail;
import de.dspindler.graveox.ui.tools.AddTool;
import de.dspindler.graveox.ui.tools.EditTool;
import de.dspindler.graveox.ui.tools.Tool;
import de.dspindler.graveox.util.Vector2;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SimulationController
{
	// Simulation parts
	private SimulationData			data;
	private SimulationView			view;
	private SimulationTimer			timer;
	private SimulationHandler		handler;
	
	public SimulationController(SimulationData data)
	{
		// Initialize parts
		this.data = data;
		this.view = new SimulationView();
		this.timer = new SimulationTimer();
		this.handler = new SimulationHandler();
		
		// Attach event listeners
		this.view.getCanvas().setOnMouseClicked(handler.new MouseClicked());
		this.view.getCanvas().setOnMousePressed(handler.new MousePressed());
		this.view.getCanvas().setOnMouseReleased(handler.new MouseReleased());
		this.view.getCanvas().setOnMouseDragged(handler.new MouseDragged());
		this.view.getCanvas().setOnMouseMoved(handler.new MouseMoved());
		this.view.getCanvas().setOnScroll(handler.new MouseScrolled());
		this.view.getCanvas().setOnKeyPressed(handler.new KeyPressed());
		this.view.getCanvas().setOnKeyReleased(handler.new KeyReleased());
		this.view.getCanvas().setOnKeyTyped(handler.new KeyTyped());
		
		// Initialize tools
		this.data.initTools(new Tool[]{
				new EditTool(this),
				new AddTool(this)
		});
		
		// Add event listeners from the tools
		this.handler.addListeners(data.getTools());
		
		// Initialize camera
		data.getCamera().setSpaceWidth(view.getCanvas().getWidth());
		data.getCamera().setSpaceHeight(view.getCanvas().getHeight() - 36.0d); // Subtracting 36 because of the toolbar height
		
		// Test bodies
		data.addBody(new Star());
		((Star) data.getBodies().get(0)).setRadius(10.0d);
		((Star) data.getBodies().get(0)).setMass(6000000.0d);
		((Star) data.getBodies().get(0)).applyTorque(0.0d);
		((Star) data.getBodies().get(0)).setPosition(new Vector2(212.0d  + 200.0d, 384.0d - 0));
		((Star) data.getBodies().get(0)).setVelocity(new Vector2(0.0d, 0.0d));
		data.addTrail(new Trail(data.getBodies().get(0)));
		
		/*data.addBody(new Star());
		((Star) data.getBodies().get(0)).setRadius(5.0d);
		((Star) data.getBodies().get(0)).setMass(20.0d);
		((Star) data.getBodies().get(0)).applyTorque(0.0d);
		((Star) data.getBodies().get(0)).setPosition(new Vector2(212.0d + 280.0d, 384.0d - 0));
		((Star) data.getBodies().get(0)).setVelocity(new Vector2(0.0d, 538.598d));
		data.addTrail(new Trail(data.getBodies().get(0), 200));*/
		
		/*data.addBody(new Star());
		((Star) data.getBodies().get(1)).setRadius(10.0d);
		((Star) data.getBodies().get(1)).setMass(6000000.0d);
		((Star) data.getBodies().get(1)).applyTorque(0.0d);
		((Star) data.getBodies().get(1)).setPosition(new Vector2(212.0d  + 200.0d, 384.0d - 0));
		((Star) data.getBodies().get(1)).setVelocity(new Vector2(0.0d, 0.0d));
		data.addTrail(new Trail(data.getBodies().get(1)));*/
		
		// Position camera
		data.getCamera().setPosition(new Vector2(512.0d, 384.0d));
//		 Track first body
//		data.getCamera().setTrackedBody(data.getBodies().get(0));
		
		// Start simulation
		this.timer.start();
	}
	
	public SimulationHandler getEventHandler()
	{
		return handler;
	}
	
	public SimulationView getView()
	{
		return view;
	}
	
	public SimulationData getData()
	{
		return data;
	}
	
	public void setWidth(double width)
	{
		data.getCamera().setSpaceWidth(width);
	}
	
	public void setHeight(double height)
	{
		data.getCamera().setSpaceHeight(height);
	}
	
	private class SimulationTimer extends AnimationTimer
	{
		private double				deltaTime;
		
		public SimulationTimer()
		{
			
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
			
			// Do collision things!
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
							// Disable for now
//							Physics.applyCollisionImpulse(b1, b2, deltaTime);
						}
					}
				}
			}
			
			// Update each body
			for(RigidBody b : data.getBodies())
			{
				b.update(deltaTime, time);
			}
			
			// Update camera
			data.getCamera().update(deltaTime);
			
			// Update tool
			data.getSelectedTool().update(deltaTime);
		}
		
		private void render(GraphicsContext g)
		{
			// Clear canvas
			g.setFill(Color.BLACK);
			g.fillRect(0, 0, view.getCanvas().getWidth(), view.getCanvas().getHeight());
			
			// Render tool background
			data.getSelectedTool().renderBackground(g);
			
			// Apply camera transformation
			g.save();
			data.getCamera().applyTransform(g);
			
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
			
			// Restore transform from camera
			g.restore();
			
			// Render tool foreground
			data.getSelectedTool().renderForeground(g);
			
			g.setFill(Color.WHITE);
			// Draw some text here
			g.fillText(String.format("World Time: %.2fs", data.getTime()), 2, 10);
			g.fillText(String.format("Dim: %s", data.getCamera().getSpaceSize().clone().scale(2.0d)), 2, 20);
		}
		
		@Override
		public void handle(long now)
		{
			deltaTime = data.getTimeScale() / (60.0d * data.getSimulationStepCount());
			
			for(int i = 0; i < data.getSimulationStepCount(); ++i)
			{
				update(deltaTime, data.getTime());
			}
			render(view.getContext());
			
			data.advanceTime(deltaTime);
		}	
	}
}
