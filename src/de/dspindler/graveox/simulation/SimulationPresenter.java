package de.dspindler.graveox.simulation;

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
		
		// Test bodies
		int numBodies = 1400;
		double minRadius = 300.0d;
		double maxRadius = 1500.0d;
		
		double minVel = 150.0d;
		double maxVel = 400.0d;
		
		double minMass = 10.0d;
		double maxMass = 100.0d;
		
		double scale = 0.1d;
		
		double r, v, a, dir, m;
		Vector2 pos = new Vector2();
		Vector2 vel = new Vector2();
		RigidBody rb;
		
		for(int i = 0; i < numBodies; ++i)
		{
			a = 2.0d * Math.PI * Math.random();
			r = minRadius + (maxRadius - minRadius) * Math.random();
			v = minVel + (maxVel - minVel) * Math.random();
			
			v = v * (1.1d - r / maxRadius);
			
//			dir = Math.signum(Math.random() - 0.5d);
			dir = 1;
			m = minMass + (maxMass - minMass) * Math.random();
			
			pos.setPolar(r, a);
			vel.setPolar(v, a + Math.PI * 0.5d * dir);
			
			rb = new Star();
			rb.setPosition(pos);
			rb.setVelocity(vel);
			rb.setMass(m);
//			rb.attachTrail(new Trail(1000));
			((Star) rb).setRadius(m * scale);
			
			this.addBody(rb);
		}
		
		rb = new Star();
		rb.setPosition(new Vector2());
		rb.setVelocity(new Vector2());
		rb.setMass(10000000.0d);
		((Star) rb).setRadius(10.0d);
		this.addBody(rb);
		
		/*double minX = -5000.0d;
		double maxX = 5000.0d;
		double minY = -5000.0d;
		double maxY = 5000.0d;
		
		double minR = 500.0d;
		double maxR = 5000.0d;
		
		double minVel = 0.0d;
		double maxVel = 10.0d;
		
		double minMass = 10.0d;
		double maxMass = 1000.0d;
		double mass;
		
		double radius = 12.0d;
		double r, a;
		
		Vector2 pos = new Vector2();
		Vector2 vel = new Vector2();
		
		for(int i = 0; i < 100; ++i)
		{
//			pos.x = (minX + (maxX - minX) * Math.random());
//			pos.y = (minY + (maxY - minY) * Math.random());
			r = minR + (maxR - minR) * Math.random();
			a = Math.random();
			pos.x = Math.cos(2.0d * Math.PI * a) * r;
			pos.y = Math.sin(2.0d * Math.PI * a) * r;
			
			vel.setPolar(minVel + (maxVel - minVel) * Math.random(), 2.0d * Math.PI * Math.random());
			
			mass = (minMass + (maxMass - minMass) * Math.random());
			radius = mass * 0.012d;
			
			this.addBody(new Star(pos, vel, mass, 0.0d, 0.0d, mass, radius));
		}*/
		
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
			// Update tool
			model.getSelectedTool().update(deltaTime);
			
			// Temporary boundaries
			/*for(int i = 0; i < model.getBodyCount(); ++i)
			{
				if(model.getBody(i).getPosition().x < -100000.0d || model.getBody(i).getPosition().x > 100000.0d)
				{
					model.removeBody(model.getBody(i));
					--i;
					System.out.println("Boundary");
					continue;
				}
				if(model.getBody(i).getPosition().y < -100000.0d || model.getBody(i).getPosition().y > 100000.0d)
				{
					model.removeBody(model.getBody(i));
					--i;
					System.out.println("Boundary");
					continue;
				}
			}*/
			
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
			
			// Update gravity handler
			long time = System.nanoTime();
			gravHandler.update(deltaTime);
			// temporary
			gravTime = (System.nanoTime() - time) / 1000000.0d;
			
			// Collision
			for(int i = 0; i < model.getBodyCount(); ++i)
			{
				for(int j = i + 1; j < model.getBodyCount(); ++j)
				{
//					CollisionHandler.handleCollision(getModel().getBodies().get(i), getModel().getBodies().get(j), deltaTime);
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
