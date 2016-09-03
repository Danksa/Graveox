package de.dspindler.graveox.simulation;

import de.dspindler.graveox.simulation.physics.Physics;
import de.dspindler.graveox.simulation.physics.RigidBody;
import de.dspindler.graveox.simulation.physics.Star;
import de.dspindler.graveox.util.Vector2;
import de.dspindler.graveox.window.WindowListener;
import de.dspindler.graveox.window.WindowModel;
import de.dspindler.graveox.window.WindowPresenter;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SimulationPresenter implements WindowListener
{
	private SimulationModel			model;
	private SimulationView			view;
	private SimulationHandler		handler;
	private WindowPresenter			window;
	private SimulationTimer			timer;
	
	public SimulationPresenter(SimulationModel model)
	{
		this.model = model;
		this.model.initTools(this);
		this.view = new SimulationView();
		this.handler = new SimulationHandler();
		this.timer = new SimulationTimer();
		
		// Add tool as event listener
		this.handler.addListener(model.getSelectedTool());
		
		this.initEventHandlers();
		
		// Test bodies
		Star body = new Star();
		body.setPosition(new Vector2(0.0d, 0.0d));
		body.setVelocity(new Vector2(0.0d, 0.0d));
		body.setMass(10000000.0d);
		body.setRadius(10.0d);
		
		this.model.addBody(body);
		
		// Start simulation
		this.timer.start();
	}
	
	private void initEventHandlers()
	{
		this.view.setOnMouseClicked(handler.new MouseClicked());
		this.view.setOnMousePressed(handler.new MousePressed());
		this.view.setOnMouseReleased(handler.new MouseReleased());
		this.view.setOnMouseDragged(handler.new MouseDragged());
		this.view.setOnMouseMoved(handler.new MouseMoved());
		this.view.setOnScroll(handler.new MouseScrolled());
		this.view.setOnKeyPressed(handler.new KeyPressed());
		this.view.setOnKeyReleased(handler.new KeyReleased());
		this.view.setOnKeyTyped(handler.new KeyTyped());
		
		this.view.widthProperty().addListener((ov, oldVal, newVal) -> model.getCamera().setSpaceWidth(newVal.doubleValue()));
		this.view.heightProperty().addListener((ov, oldVal, newVal) -> model.getCamera().setSpaceHeight(newVal.doubleValue()));
	}
	
	public void start(Stage stage)
	{
		this.window = new WindowPresenter(new WindowModel(stage));
		this.window.setSimulationView(view);
		this.window.setTools(model.getTools());
		this.window.addListener(this);
		this.window.show();
		
		this.toolSelected(0);
	}
	
	public SimulationModel getModel()
	{
		return model;
	}
	
	public SimulationView getView()
	{
		return view;
	}

	@Override
	public void toolSelected(int index)
	{
		// Unregister last tool
		this.handler.removeListener(model.getSelectedTool());
		
		// Select new tool
		this.model.selectTool(index);
		
		// Register new tool
		this.handler.addListener(model.getSelectedTool());
		
		// Show tool panel
		this.window.selectTool(model.getSelectedTool());
	}
	
	private class SimulationTimer extends AnimationTimer
	{	
		private double			deltaTime;
		
		public SimulationTimer()
		{
			
		}
		
		private void update(double deltaTime)
		{
			// Update tool
			model.getSelectedTool().update(deltaTime);
			
			// Udpate trails
			for(RigidBody b : model.getBodies())
			{
				b.updateTrail(deltaTime);
			}
			
			// Update positions
			for(RigidBody b : model.getBodies())
			{
				b.preUpdate(deltaTime);
			}
			
			// Apply forces here
			for(RigidBody a : model.getBodies())
			{
				for(RigidBody b : model.getBodies())
				{
					if(a != b)
					{
						Physics.applyRelativisticGravity(a, b);
					}
				}
			}
			
			// Update velocities
			for(RigidBody b : model.getBodies())
			{
				b.update(deltaTime);
			}
			
			// Update camera
			model.getCamera().update(deltaTime);
		}
		
		private void render(GraphicsContext g)
		{
			// Render tool background
			model.getSelectedTool().renderBackground(g);
			
			// Apply camera transformation
			g.save();
			model.getCamera().applyTransform(g);
			
			// Render trails
			for(RigidBody b : model.getBodies())
			{
				b.renderTrail(g);
			}
			
			// Render bodies
			for(RigidBody b : model.getBodies())
			{
				b.render(g);
			}
			
			// Restore transform previous to camera transform
			g.restore();
			
			// Render tool foreground
			model.getSelectedTool().renderForeground(g);
			
			// Render text
			g.setFill(Color.WHITE);
			g.fillText("Time: " + model.getTime(), 2, 10);
			g.fillText("Cam: " + model.getCamera().getPosition(), 2, 20);
			g.fillText("Tracking: " + (model.getCamera().getTrackedBody() != null), 2, 30);
//			g.fillText("Test: M\u2609", 2, 50);
		}
		
		@Override
		public void handle(long now)
		{
			deltaTime = model.getTimeScale() / (60.0d * model.getSimulationSteps());
			
			for(int i = 0; i < model.getSimulationSteps(); ++i)
			{
				update(deltaTime);
				model.advanceTime(deltaTime);
			}
			
			view.clear();
			render(view.getGraphicsContext2D());
		}	
	}
}
