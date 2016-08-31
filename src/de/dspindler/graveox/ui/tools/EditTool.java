package de.dspindler.graveox.ui.tools;

import de.dspindler.graveox.simulation.SimulationController;
import de.dspindler.graveox.simulation.physics.RigidBody;
import de.dspindler.graveox.simulation.physics.Star;
import de.dspindler.graveox.ui.Grid;
import de.dspindler.graveox.util.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class EditTool extends Tool
{
	private RigidBody			selectedBody;
	private Grid				grid;
	
	private Vector2				dragStartPosition;
	private boolean				dragging;
	
	public EditTool(SimulationController simulation)
	{
		super("Edit", "Use to edit and manipulate bodies.", simulation, new EditToolPanel());
		
		selectedBody = null;
		grid = new Grid(new Vector2(), 200.0d, 10, 12);
		
		dragStartPosition = new Vector2();
		dragging = false;
	}

	@Override
	public void onMousePressed(MouseEvent e)
	{
		// Transform mouse position to world position
		Vector2 pos = new Vector2(e.getX(), e.getY());
		pos.set(simulation.getData().getCamera().toWorldSpace(pos));
		
		// Check if a body was clicked
		for(RigidBody b : simulation.getData().getBodies())
		{
			selectedBody = null;
			
			if(b instanceof Star)
			{
				if(Vector2.getDistance(pos, b.getPosition()) <= ((Star) b).getRadius())
				{
					selectedBody = b;
					
					// Center camera on body
					simulation.getData().getCamera().setTrackedBody(selectedBody);
					simulation.getData().getCamera().setPositionSmoothingFactor(1.0d);	// Disable camera smoothing while tracking body
					
					break;
				}
			}
		}
		
		// Set drag start position
		if(selectedBody == null)
		{
			dragStartPosition.set(e.getX(), e.getY());
			simulation.getData().getCamera().setTrackedBody(null);
			simulation.getData().getCamera().setPositionSmoothingFactor(0.1d);	// Enable camera smoothing while dragging the view
			dragging = true;
		}
	}
	
	@Override
	public void onMouseReleased(MouseEvent e)
	{
		if(dragging)
		{
			dragStartPosition.zero();
			dragging = false;
		}
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
		// Transform mouse position to world position
		Vector2 pos = new Vector2(e.getX(), e.getY());
		pos.set(simulation.getData().getCamera().toWorldSpace(pos));
		
		// If no body is selected, camera can be dragged
		if(dragging)
		{
			Vector2 camPos = dragStartPosition.clone().translate(-e.getX(), -e.getY());
			camPos.scale(1.0d / simulation.getData().getCamera().getScale());
			
			simulation.getData().getCamera().getPosition().add(camPos);
			
			dragStartPosition.set(e.getX(), e.getY());
		}
	}

	@Override
	public void onMouseScrolled(ScrollEvent e)
	{
		double scroll = Math.exp(e.getDeltaY() * 0.001d);
		
		super.zoom(scroll);
	}
	
	@Override
	public void onKeyPressed(KeyEvent e)
	{
		
	}

	@Override
	public void onKeyReleased(KeyEvent e)
	{
		switch(e.getCode())
		{
		case DELETE: // If the delete key is pressed, delete object, if selected
		{
			if(selectedBody != null)
			{
				simulation.getData().removeBody(selectedBody);
				selectedBody = null;
			}
			
			break;
		}
		default:
		{
			break;
		}
		}
	}

	@Override
	public void onKeyTyped(KeyEvent e)
	{
		
	}

	@Override
	public void update(double deltaTime)
	{
		if(selectedBody != null)
		{
			grid.setCenter(simulation.getData().getCamera().toCameraSpace(selectedBody.getPosition()));
		}
	}
	
	@Override
	public void renderForeground(GraphicsContext g)
	{
		// Draw length scale
		super.drawLengthScale(g);
	}

	@Override
	public void renderBackground(GraphicsContext g)
	{
		// Highlight selected body
		
		// TODO: Use body radius to determine highlight radius
		if(selectedBody != null)
		{
			// Draw grid
			grid.render(g);
			
			/*// Draw highlight
			g.setStroke(Color.BLUE);
			double ang;
			double step = 2.0d * Math.PI / 20.0d;
			Vector2 pos = simulation.getData().getCamera().toCameraSpace(selectedBody.getPosition());
			double radius = 40.0d;
			for(int i = 0; i < 20; ++i)
			{
				ang = i * step;
				g.strokeLine(pos.x + Math.cos(ang) * radius, pos.y + Math.sin(ang) * radius, pos.x + Math.cos(ang + step) * radius, pos.y + Math.sin(ang + step) * radius);
			}*/
		}
	}
}
