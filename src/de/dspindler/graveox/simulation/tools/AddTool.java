package de.dspindler.graveox.simulation.tools;

import de.dspindler.graveox.simulation.physics.Physics;
import de.dspindler.graveox.simulation.physics.RigidBody;
import de.dspindler.graveox.simulation.physics.Star;
import de.dspindler.graveox.simulation.physics.Trail;
import de.dspindler.graveox.util.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

public class AddTool extends Tool
{
	private Vector2				dragStartPosition;
	private Vector2				dragEndPosition;
	private double				forceMultiplier;
	private boolean				dragging;
	
	private Vector2[]			predictorPoints;
	
	// Planet properties
	private double				mass;
	private double				radius;
	
	public AddTool()
	{
		super("Add Tool", new AddToolPanel());
		
		this.dragStartPosition = new Vector2();
		this.dragEndPosition = new Vector2();
		this.forceMultiplier = 1.0d;
		this.dragging = false;
		
		this.predictorPoints = new Vector2[400];
		for(int i = 0; i < predictorPoints.length; ++i)
		{
			this.predictorPoints[i] = new Vector2();
		}
		
		this.mass = 10.0d;
		this.radius = 5.0d;
	}

	private void calculatePredictor()
	{
		// Calculate predictor
		double force = Vector2.getDistance(dragStartPosition, dragEndPosition) * forceMultiplier;
		Vector2 velocity = Vector2.getPolar(-force, Vector2.getAngle(dragStartPosition, dragEndPosition));
				
		Star s = new Star(super.getSimulation().getModel().getCamera().toWorldSpace(dragStartPosition), velocity, mass, 0.0d, 0.0d, 1.0d, radius);
		predictorPoints[0].set(dragStartPosition);
		
		double distance = 0.0d;
		Vector2 oldPos = new Vector2();
		int pointIndex = 1;
		Vector2 gravity;
		
		while(pointIndex < predictorPoints.length)
		{
			oldPos.set(s.getPosition());
			
			s.preUpdate(1.0d / 60.0d);
			
			// Simulate body
			for(RigidBody b : super.getSimulation().getModel().getBodies())
			{
				gravity = Physics.getRelativisticGravity(s, b).scale(2.0d);
				s.applyForce(gravity);
			}
			
			s.update(1.0d / 60.0d);
			
			distance += Vector2.getDistance(oldPos, s.getPosition());
			
			// Place predictor point
			if(distance >= 10.0d)
			{
				distance = 0.0d;
				predictorPoints[pointIndex++].set(super.getSimulation().getModel().getCamera().toCameraSpace(s.getPosition()));
			}
		}
	}
	
	@Override
	public void onMousePressed(MouseEvent e)
	{
		this.dragStartPosition.set(e.getX(), e.getY());
		this.dragEndPosition.set(dragStartPosition);
		this.dragging = true;
	}

	@Override
	public void onMouseReleased(MouseEvent e)
	{
		this.dragEndPosition.set(e.getX(), e.getY());
		this.dragging = false;
		
		// Spawn body
		double force = Vector2.getDistance(dragStartPosition, dragEndPosition) * forceMultiplier;
		Vector2 velocity = Vector2.getPolar(-force, Vector2.getAngle(dragStartPosition, dragEndPosition));
		
		Star s = new Star(super.getSimulation().getModel().getCamera().toWorldSpace(dragStartPosition), velocity, mass, 0.0d, 0.0d, 1.0d, radius);
		s.attachTrail(new Trail(200));
		
		super.getSimulation().getModel().addBody(s);
	}

	@Override
	public void onMouseClicked(MouseEvent e){}

	@Override
	public void onMouseMoved(MouseEvent e){}

	@Override
	public void onMouseDragged(MouseEvent e)
	{
		this.dragEndPosition.set(e.getX(), e.getY());
	}

	@Override
	public void onMouseScrolled(ScrollEvent e)
	{
		double scroll = Math.exp(e.getDeltaY() * 0.001d);
		
		if(dragging)
		{
			// If the user is dragging the mouse to shoot a planet, use the
			// mouse wheel to change the velocity multiplier
			this.forceMultiplier *= scroll;
		}
		else
		{
			super.zoom(scroll);
		}
	}

	@Override
	public void onKeyPressed(KeyEvent e)
	{
		
	}

	@Override
	public void onKeyReleased(KeyEvent e){}

	@Override
	public void onKeyTyped(KeyEvent e){}

	@Override
	public void update(double deltaTime)
	{
		if(dragging)
		{
			// Recalculate predictor
			this.calculatePredictor();
		}
	}

	@Override
	public void renderForeground(GraphicsContext g)
	{
		// Draw length scale
		super.drawLengthScale(g);
		
		if(dragging)
		{
			// Draw shoot indicator
			g.setStroke(Color.RED);
			g.strokeLine(dragStartPosition.x, dragStartPosition.y, dragEndPosition.x, dragEndPosition.y);
			
			// Draw predictor
			g.setStroke(Color.WHITE);
			for(int i = 0; i < predictorPoints.length - 1; ++i)
			{
				g.strokeLine(predictorPoints[i].x, predictorPoints[i].y, predictorPoints[i + 1].x, predictorPoints[i + 1].y);
			}
			
			// Draw body
			g.setFill(Color.ORANGE);
			g.fillOval(dragStartPosition.x - radius, dragStartPosition.y - radius, radius * 2.0d, radius * 2.0d);
			
			// Draw force strength
			double force = Vector2.getDistance(dragStartPosition, dragEndPosition) * forceMultiplier;
			
			g.setFill(Color.WHITE);
			g.fillText(String.format("Force: %.2f", force), dragStartPosition.x + radius, dragStartPosition.y - radius);
		}
	}

	@Override
	public void renderBackground(GraphicsContext g)
	{
		
	}
	
}
//package de.dspindler.graveox.ui.tools;
//
//import de.dspindler.graveox.simulation.SimulationController;
//import de.dspindler.graveox.simulation.physics.Physics;
//import de.dspindler.graveox.simulation.physics.RigidBody;
//import de.dspindler.graveox.simulation.physics.Star;
//import de.dspindler.graveox.simulation.physics.Trail;
//import de.dspindler.graveox.util.Vector2;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.input.ScrollEvent;
//import javafx.scene.paint.Color;
//
//public class AddTool extends Tool
//{
//	private Vector2				dragStartPosition;
//	private Vector2				dragEndPosition;
//	private double				forceMultiplier;
//	private boolean				dragging;
//	
//	private Vector2[]			predictorPoints;
//	
//	// Planet properties
//	private double				mass;
//	private double				radius;
//	
//	public AddTool(SimulationController simulation)
//	{
//		super("Add", "Use to add new bodies to the simulation.", simulation);
//		
//		this.dragStartPosition = new Vector2();
//		this.dragEndPosition = new Vector2();
//		this.forceMultiplier = 1.0d;
//		this.dragging = false;
//		
//		this.predictorPoints = new Vector2[400];
//		for(int i = 0; i < predictorPoints.length; ++i)
//		{
//			this.predictorPoints[i] = new Vector2();
//		}
//		
//		this.mass = 10.0d;
//		this.radius = 5.0d;
//	}
//	
//	private void calculatePredictor()
//	{
//		// Calculate predictor
//		double force = Vector2.getDistance(dragStartPosition, dragEndPosition) * forceMultiplier;
//		Vector2 velocity = Vector2.getPolar(-force, Vector2.getAngle(dragStartPosition, dragEndPosition));
//				
//		Star s = new Star(simulation.getData().getCamera().toWorldSpace(dragStartPosition), velocity, mass, 0.0d, 0.0d, 1.0d, radius);
//		predictorPoints[0].set(dragStartPosition);
//		
//		double distance = 0.0d;
//		Vector2 oldPos = new Vector2();
//		int pointIndex = 1;
//		Vector2 gravity;
//		
//		while(pointIndex < predictorPoints.length)
//		{
//			oldPos.set(s.getPosition());
//			
//			s.preUpdate(1.0d / 60.0d);
//			
//			// Simulate body
//			for(RigidBody b : simulation.getData().getBodies())
//			{
//				gravity = Physics.getRelativisticGravity(s, b).scale(2.0d);
//				s.applyForce(gravity);
//			}
//			
//			s.update(1.0d / 60.0d, 0.0d);
//			
//			distance += Vector2.getDistance(oldPos, s.getPosition());
//			
//			// Place predictor point
////			if(Vector2.getDistance(s.getPosition(), predictorPoints[pointIndex - 1]) >= 10.0d)
//			if(distance >= 10.0d)
//			{
//				distance = 0.0d;
//				predictorPoints[pointIndex++].set(simulation.getData().getCamera().toCameraSpace(s.getPosition()));
//			}
//		}
//	}
//
//	@Override
//	public void onMousePressed(MouseEvent e)
//	{
//		dragStartPosition.set(e.getX(), e.getY());
//		dragEndPosition.set(dragStartPosition);
//		dragging = true;
//	}
//
//	@Override
//	public void onMouseReleased(MouseEvent e)
//	{
//		dragEndPosition.set(e.getX(), e.getY());
//		dragging = false;
//		
//		// Spawn body
//		double force = Vector2.getDistance(dragStartPosition, dragEndPosition) * forceMultiplier;
//		Vector2 velocity = Vector2.getPolar(-force, Vector2.getAngle(dragStartPosition, dragEndPosition));
//		
//		Star s = new Star(simulation.getData().getCamera().toWorldSpace(dragStartPosition), velocity, mass, 0.0d, 0.0d, 1.0d, radius);
//		s.attachTrail(new Trail(200));
//		
//		simulation.getData().addBody(s);
//	}
//
//	@Override
//	public void onMouseClicked(MouseEvent e)
//	{
//		
//	}
//
//	@Override
//	public void onMouseMoved(MouseEvent e)
//	{
//		
//	}
//
//	@Override
//	public void onMouseDragged(MouseEvent e)
//	{
//		dragEndPosition.set(e.getX(), e.getY());
//	}
//
//	@Override
//	public void onMouseScrolled(ScrollEvent e)
//	{
//		double scroll = Math.exp(e.getDeltaY() * 0.001d);
//		
//		if(dragging)
//		{
//			// If the user is dragging the mouse to shoot a planet, use the
//			// mouse wheel to change the velocity multiplier
//			forceMultiplier *= scroll;
//		}
//		else
//		{
//			super.zoom(scroll);
//		}
//	}
//	
//	@Override
//	public void onKeyPressed(KeyEvent e)
//	{
//		
//	}
//
//	@Override
//	public void onKeyReleased(KeyEvent e)
//	{
//		
//	}
//
//	@Override
//	public void onKeyTyped(KeyEvent e)
//	{
//		
//	}
//	
//	@Override
//	public void toolPanelChanged(ToolPanel panel)
//	{
//		
//	}
//
//	@Override
//	public void update(double deltaTime)
//	{
//		if(dragging)
//		{
//			// Recalculate predictor
//			calculatePredictor();
//		}
//	}
//
//	@Override
//	public void renderForeground(GraphicsContext g)
//	{
//		// Draw length scale
//		super.drawLengthScale(g);
//		
//		if(dragging)
//		{
//			// Draw shoot indicator
//			g.setStroke(Color.RED);
//			g.strokeLine(dragStartPosition.x, dragStartPosition.y, dragEndPosition.x, dragEndPosition.y);
//			
//			// Draw predictor
//			g.setStroke(Color.WHITE);
//			for(int i = 0; i < predictorPoints.length - 1; ++i)
//			{
//				g.strokeLine(predictorPoints[i].x, predictorPoints[i].y, predictorPoints[i + 1].x, predictorPoints[i + 1].y);
//			}
//			
//			// Draw body
//			g.setFill(Color.ORANGE);
//			g.fillOval(dragStartPosition.x - radius, dragStartPosition.y - radius, radius * 2.0d, radius * 2.0d);
//			
//			// Draw force strength
//			double force = Vector2.getDistance(dragStartPosition, dragEndPosition) * forceMultiplier;
//			
//			g.setFill(Color.WHITE);
//			g.fillText(String.format("Force: %.2f", force), dragStartPosition.x + radius, dragStartPosition.y - radius);
//		}
//	}
//	
//	@Override
//	public void renderBackground(GraphicsContext g)
//	{
//		
//	}
//}
