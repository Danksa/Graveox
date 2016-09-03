package de.dspindler.graveox.simulation.tools;

import de.dspindler.graveox.simulation.physics.RigidBody;
import de.dspindler.graveox.simulation.physics.Trail;
import de.dspindler.graveox.util.Vector2;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

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
		
		// Tool Panel listeners
		
		// Trail enable check box
		((EditToolPanel) super.getPanel()).getTrailEnableBox().selectedProperty().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal)
			{
				if(selectedBody != null)
				{
					if(!selectedBody.hasTrail())
					{
						selectedBody.attachTrail(new Trail(200));
					}
					selectedBody.getTrail().show(newVal);
					((EditToolPanel) getPanel()).updateValues(selectedBody);
				}
			}
		});
		
		// Trail color picker
		((EditToolPanel) super.getPanel()).getTrailColorPicker().setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e)
			{
				if(selectedBody != null)
				{
					if(selectedBody.hasTrail())
					{
						selectedBody.getTrail().setColor(((EditToolPanel) getPanel()).getTrailColorPicker().getValue());
					}
				}
			}
		});
		
		// Trail length slider
		((EditToolPanel) super.getPanel()).getTrailLengthSlider().valueProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal)
			{
				if(selectedBody != null)
				{
					selectedBody.getTrail().setLength(newVal.intValue());
				}
			}
		});
		
		// Trail length check box
		((EditToolPanel) super.getPanel()).getTrailLengthBox().selectedProperty().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal)
			{
				if(selectedBody != null)
				{
					if(newVal)
					{
						// Length of -1 is infinite
						selectedBody.getTrail().setLength(-1);
					}
					else
					{
						selectedBody.getTrail().setLength(10);
					}
				}
			}
		});
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
			if(Vector2.getDistance(pos, b.getPosition()) <= b.getCollisionShape().getBroadRadius())
			{
				selectedBody = b;
				
				// Center camera on body
				super.getSimulation().getModel().getCamera().setTrackedBody(selectedBody);
				super.getSimulation().getModel().getCamera().setPositionSmoothingFactor(1.0d);	// Disable camera smoothing while tracking body
				
				break;
			}
		}
		
		// Set drag start position
		if(selectedBody == null)
		{
			this.dragStartPosition.set(e.getX(), e.getY());
			super.getSimulation().getModel().getCamera().setTrackedBody(null);
			super.getSimulation().getModel().getCamera().setPositionSmoothingFactor(0.1d);	// Enable camera smoothing while dragging the view
			this.state = State.DRAGGING_VIEW;
			
			// Update tool panel
			((EditToolPanel) super.getPanel()).updateValues(null);
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
	public void update(double deltaTime)
	{
		if(selectedBody != null)
		{
			((EditToolPanel) this.getPanel()).updateValues(selectedBody);
		}
	}

	@Override
	public void renderForeground(GraphicsContext g)
	{
		super.drawLengthScale(g);
		
		// Highlight selected body
		// Need to make this nicer!
		if(selectedBody != null)
		{
			g.setStroke(Color.AQUAMARINE);
			Vector2 pos = super.getSimulation().getModel().getCamera().toCameraSpace(selectedBody.getPosition());
			double ang;
			int steps = Math.max((int)(16.0d * super.getSimulation().getModel().getCamera().getScale()), 16);
			double step = 2.0d * Math.PI / steps;
			double radius = selectedBody.getCollisionShape().getBroadRadius() * super.getSimulation().getModel().getCamera().getSmoothedScale();
			
			for(int i = 0; i < steps; ++i)
			{
				ang = step * i;
				g.strokeLine(pos.x + Math.cos(ang) * radius, pos.y + Math.sin(ang) * radius, pos.x + Math.cos(ang + step) * radius, pos.y + Math.sin(ang + step) * radius);
			}
		}
	}

	@Override
	public void renderBackground(GraphicsContext g)
	{
		
	}
}
