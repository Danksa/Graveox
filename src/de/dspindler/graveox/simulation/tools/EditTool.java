package de.dspindler.graveox.simulation.tools;

import de.dspindler.graveox.simulation.physics.RigidBody;
import de.dspindler.graveox.simulation.physics.Star;
import de.dspindler.graveox.util.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class EditTool extends Tool
{
	private enum State {
			IDLE,
			DRAGGING_VIEW
	};
	
	// Common attributes
	private State			state;
	
	// Drag attributes
	private Vector2			dragStartPosition;
	
	// Track attributes
	private RigidBody		selectedBody;
	
	public EditTool()
	{
		super("Edit Tool", new EditToolPanel());
		
		this.state = State.IDLE;
		this.dragStartPosition = new Vector2();
	}

	@Override
	public void onMousePressed(MouseEvent e)
	{
		// Transform mouse position to world position
		Vector2 pos = new Vector2(e.getX(), e.getY());
		pos.set(super.getSimulation().getModel().getCamera().toWorldSpace(pos));
		
		selectedBody = null;
		for(RigidBody b : super.getSimulation().getModel().getBodies())
		{
			if(b instanceof Star)
			{
				if(Vector2.getDistance(pos, b.getPosition()) <= ((Star) b).getRadius())
					{
						selectedBody = b;
						
						// Center camera on body
						super.getSimulation().getModel().getCamera().setTrackedBody(selectedBody);
						super.getSimulation().getModel().getCamera().setPositionSmoothingFactor(1.0d);	// Disable camera smoothing while tracking body
						
						break;
					}
			}
		}
		
		// Set drag start position
		if(selectedBody == null)
		{
			this.dragStartPosition.set(e.getX(), e.getY());
			super.getSimulation().getModel().getCamera().setTrackedBody(null);
			super.getSimulation().getModel().getCamera().setPositionSmoothingFactor(0.1d);	// Enable camera smoothing while dragging the view
			this.state = State.DRAGGING_VIEW;
		}
	}

	@Override
	public void onMouseReleased(MouseEvent e)
	{
		if(state == State.DRAGGING_VIEW)
		{
			this.dragStartPosition.zero();
			this.state = State.IDLE;
		}
	}

	@Override
	public void onMouseClicked(MouseEvent e){}

	@Override
	public void onMouseMoved(MouseEvent e){}

	@Override
	public void onMouseDragged(MouseEvent e)
	{
		// Transform mouse position to world position
		Vector2 pos = new Vector2(e.getX(), e.getY());
		pos.set(super.getSimulation().getModel().getCamera().toWorldSpace(pos));
		
		// If no body is selected, camera can be dragged
		if(state == State.DRAGGING_VIEW)
		{
			Vector2 camPos = dragStartPosition.clone().translate(-e.getX(), -e.getY());
			camPos.scale(1.0d / super.getSimulation().getModel().getCamera().getScale());
			
			// WRONG POSITION INSTANCE???!?!?!??!
			super.getSimulation().getModel().getCamera().getPosition().add(camPos);
			
//			System.out.println(super.getSimulation().getModel().getCamera().getPosition().hashCode());
			
			this.dragStartPosition.set(e.getX(), e.getY());
		}
	}

	@Override
	public void onMouseScrolled(ScrollEvent e)
	{
		super.zoom(Math.exp(e.getDeltaY() * 0.001d));
	}

	@Override
	public void onKeyPressed(KeyEvent e){}

	@Override
	public void onKeyReleased(KeyEvent e){}

	@Override
	public void onKeyTyped(KeyEvent e){}

	@Override
	public void update(double deltaTime){}

	@Override
	public void renderForeground(GraphicsContext g)
	{
		super.drawLengthScale(g);
	}

	@Override
	public void renderBackground(GraphicsContext g)
	{
		
	}
}
