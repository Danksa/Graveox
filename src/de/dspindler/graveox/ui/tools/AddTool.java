package de.dspindler.graveox.ui.tools;

import de.dspindler.graveox.simulation.SimulationController;
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
	
	public AddTool(SimulationController simulation)
	{
		super("Add", "Use to add new bodies to the simulation.", simulation);
		
		dragStartPosition = new Vector2();
		dragEndPosition = new Vector2();
		forceMultiplier = 1.0d;
		dragging = false;
		
		predictorPoints = new Vector2[100];
		for(int i = 0; i < predictorPoints.length; ++i)
		{
			predictorPoints[i] = new Vector2();
		}
	}
	
	private void calculatePredictor()
	{
		// Calculate predictor
		double mass = 10.0d;
		double radius = 5.0d;
		double force = Vector2.getDistance(dragStartPosition, dragEndPosition) * forceMultiplier;
		Vector2 velocity = Vector2.getPolar(-force, Vector2.getAngle(dragStartPosition, dragEndPosition));
				
		Star s = new Star(simulation.getData().getCamera().toWorldSpace(dragStartPosition), velocity, mass, 0.0d, 0.0d, 1.0d, radius);
		predictorPoints[0].set(dragStartPosition);
		
		int pointIndex = 1;
		Vector2 gravity;
		
		while(pointIndex < predictorPoints.length)
		{
			s.preUpdate(1.0d / 60.0d);
			
			// Simulate body
			for(RigidBody b : simulation.getData().getBodies())
			{
				gravity = Physics.getRelativisticGravity(s, b).scale(2.0d);
				s.applyForce(gravity);
			}
			
			s.update(1.0d / 60.0d, 0.0d);
			
			// Place predictor point
			if(Vector2.getDistance(s.getPosition(), predictorPoints[pointIndex - 1]) >= 10.0d)
			{
				predictorPoints[pointIndex++].set(simulation.getData().getCamera().toCameraSpace(s.getPosition()));
			}
		}
	}

	@Override
	public void onMousePressed(MouseEvent e)
	{
		dragStartPosition.set(e.getX(), e.getY());
		dragEndPosition.set(dragStartPosition);
		dragging = true;
	}

	@Override
	public void onMouseReleased(MouseEvent e)
	{
		dragEndPosition.set(e.getX(), e.getY());
		dragging = false;
		
		// Spawn body
		double mass = 10.0d;
		double radius = 5.0d;
		double force = Vector2.getDistance(dragStartPosition, dragEndPosition) * forceMultiplier;
		Vector2 velocity = Vector2.getPolar(-force, Vector2.getAngle(dragStartPosition, dragEndPosition));
		
		Star s = new Star(simulation.getData().getCamera().toWorldSpace(dragStartPosition), velocity, mass, 0.0d, 0.0d, 1.0d, radius);
		
		simulation.getData().addBody(s);
		simulation.getData().addTrail(new Trail(s, 100));
	}

	@Override
	public void onMouseClicked(MouseEvent e)
	{
		
	}

	@Override
	public void onMouseMoved(MouseEvent e)
	{
		
	}

	@Override
	public void onMouseDragged(MouseEvent e)
	{
		dragEndPosition.set(e.getX(), e.getY());
	}

	@Override
	public void onMouseScrolled(ScrollEvent e)
	{
		double scroll = Math.exp(e.getDeltaY() * 0.001d);
		
		if(dragging)
		{
			// If the user is dragging the mouse to shoot a planet, use the
			// mouse wheel to change the velocity multiplier
			forceMultiplier *= scroll;
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
	public void onKeyReleased(KeyEvent e)
	{
		
	}

	@Override
	public void onKeyTyped(KeyEvent e)
	{
		
	}

	@Override
	public void update(double deltaTime)
	{
		if(dragging)
		{
			// Recalculate predictor
			calculatePredictor();
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
			g.fillOval(dragStartPosition.x - 5, dragStartPosition.y - 5, 10, 10);
			
			// Draw force strength
			double force = Vector2.getDistance(dragStartPosition, dragEndPosition) * forceMultiplier;
			
			g.setFill(Color.WHITE);
			g.fillText(String.format("Force: %.2f", force), dragStartPosition.x + 5, dragStartPosition.y - 5);
		}
	}
	
	@Override
	public void renderBackground(GraphicsContext g)
	{
		
	}
}
