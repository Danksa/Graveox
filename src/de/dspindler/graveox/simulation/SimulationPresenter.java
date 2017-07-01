package de.dspindler.graveox.simulation;

import de.dspindler.graveox.simulation.physics.Particle;
import de.dspindler.graveox.simulation.physics.RigidBody;
import de.dspindler.graveox.simulation.physics.Star;
import de.dspindler.graveox.simulation.physics.optimization.GravityHandler;
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
	private GravityHandler			gravHandler;
	
	// test
	private double					gravTime = 0.0d;
	private int						fps = 0;
	private long					fpsTime = System.currentTimeMillis();
	private int						lastFps = 0;
	private double					renderTime = 0.0d;
	
	public SimulationPresenter(SimulationModel model)
	{
		this.model = model;
		this.model.initTools(this);
		this.view = new SimulationView();
		this.handler = new SimulationHandler();
		this.timer = new SimulationTimer();
		this.gravHandler = new GravityHandler(model);
		
		// Add tool as event listener
		this.handler.addListener(model.getSelectedTool());
		
		this.initEventHandlers();
		
		// Temporary fix, execute update for gravity handler once
//		this.gravHandler.update(1.0d);
		
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
		
		// Test
		RigidBody rb = new Star();
		rb.setPosition(new Vector2(0.0d, 0.0d));
		rb.setVelocity(new Vector2());
		rb.setMass(10000000.0d);
		rb.setStationary(true);
		((Star) rb).setRadius(1.0d);
		this.addBody(rb);
		
		model.getCamera().setTrackedBody(rb);
	}
	
	public void addBody(RigidBody b)
	{
		this.model.addBody(b);
	}
	
	public void removeBody(RigidBody b)
	{
		this.model.removeBody(b);
	}
	
	public SimulationModel getModel()
	{
		return model;
	}
	
	public SimulationView getView()
	{
		return view;
	}
	
	public void setTimeScale(double timeScale)
	{
		this.model.setTimeScale(timeScale);
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
	
	@Override
	public void timescaleChanged(double timeScale)
	{
		this.model.setTimeScale(timeScale);
	}
	
	private class SimulationTimer extends AnimationTimer
	{	
		private double			deltaTime;
		
		public SimulationTimer()
		{
			
		}
		
		private void update(double deltaTime)
		{
			// Remove bodies which are marked as such
			model.removeBodies();
			
			// Add bodies which were created
			model.addBodies();
			
			// Update tool
			model.getSelectedTool().update(deltaTime);
			
			// Udpate trails
			for(RigidBody b : model.getBodies())
			{
				b.updateTrail(deltaTime);
			}
			
			// Pre-update (currently unnecessary
//			for(RigidBody b : model.getBodies())
//			{
//				b.preUpdate(deltaTime);
//			}
			
			// Update gravity handler
			long time = System.nanoTime();
			gravHandler.update(deltaTime);
			// temporary
			gravTime = (System.nanoTime() - time) / 1000000.0d;
			if(System.currentTimeMillis() - fpsTime > 1000)
			{
				lastFps = fps;
				fps = 0;
				fpsTime += 1000;
			}
			
			// Collision
			for(int i = 0; i < model.getBodyCount(); ++i)
			{
				for(int j = i + 1; j < model.getBodyCount(); ++j)
				{
//					CollisionHandler.handleCollision(getModel().getBodies().get(i), getModel().getBodies().get(j), deltaTime);
					RigidBody a = model.getBody(i);
					RigidBody b = model.getBody(j);
					
					if(a instanceof Star && b instanceof Star)
					{
						if(Vector2.getDistance(a.getPosition(), b.getPosition()) <= ((Star) a).getRadius() + ((Star) b).getRadius())
						{
							if(a.getMass() > b.getMass())
							{
								a.setMass(a.getMass() + b.getMass());
								
								removeBody(b);
							}
							else
							{
								b.setMass(a.getMass() + b.getMass());
								
								removeBody(a);
							}
						}
					}
					
					if(a instanceof Star && b instanceof Particle)
					{
						if(b.getPosition().clone().subtract(a.getPosition()).getMagnitude() <= 15.0d)
						{
//							model.removeBody(b);
							b.setStationary(true);
						}
					}
					else if(b instanceof Star && a instanceof Particle)
					{
						if(b.getPosition().clone().subtract(a.getPosition()).getMagnitude() <= 15.0d)
						{
//							model.removeBody(a);
							a.setStationary(true);
						}
					}
				}
			}
			
			// Update bodies
			for(RigidBody b : model.getBodies())
			{
				if(b.isValid())
				{
					b.update(deltaTime);
				}
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
			
			// Render gravity handler
			gravHandler.render(g);
			
			// Restore transform previous to camera transform
			g.restore();
			
			// Render tool foreground
			model.getSelectedTool().renderForeground(g);
			
			// Render text
			g.setFill(Color.WHITE);
			g.fillText(String.format("Time: %.2f     Scale: %.2f", model.getTime(), model.getTimeScale()), 2, 10);
			g.fillText("Cam: " + model.getCamera().getPosition(), 2, 20);
			g.fillText("Tracking: " + (model.getCamera().getTrackedBody() != null), 2, 30);
			g.fillText("Bodies: " + model.getBodyCount(), 2, 50);
			g.fillText("GravTime: " + gravTime + "ms [x" + model.getSimulationSteps() + "]", 2, 70);
			g.fillText("FPS: " + lastFps, 2, 90);
			g.fillText("RenderTime: " + renderTime + "ms", 2, 110);
			g.fillText("TimeSum: " + (gravTime + renderTime) + "ms", 2, 130);
			
			// temp
			++fps;
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
			
			long time = System.nanoTime();
			view.clear();
			render(view.getGraphicsContext2D());
			renderTime = (System.nanoTime() - time) / 1000000.0d;
		}	
	}
}
