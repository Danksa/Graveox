package de.dspindler.graveox.ui.tools;

import de.dspindler.graveox.simulation.SimulationController;
import de.dspindler.graveox.simulation.physics.RigidBody;
import de.dspindler.graveox.simulation.physics.Star;
import de.dspindler.graveox.util.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class EditTool extends Tool
{
	private RigidBody			selectedBody;
	
	public EditTool(SimulationController simulation)
	{
		super("Edit", "Use to edit and manipulate bodies.", simulation);
		
		selectedBody = null;
	}

	@Override
	public void onMousePressed(MouseEvent e)
	{
		
	}

	@Override
	public void onMouseReleased(MouseEvent e)
	{
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
					break;
				}
			}
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
		
	}

	@Override
	public void render(GraphicsContext g)
	{
		// Highlight selected body
		
		// TODO: Use body radius to determine highlight radius
		if(selectedBody != null)
		{
			g.setStroke(Color.BLUE);
			double ang;
			double step = 2.0d * Math.PI / 20.0d;
			Vector2 pos = simulation.getData().getCamera().toCameraSpace(selectedBody.getPosition());
			for(int i = 0; i < 20; ++i)
			{
				ang = i * step;
				g.strokeLine(pos.x + Math.cos(ang) * 20.0d, pos.y + Math.sin(ang) * 20.0d, pos.x + Math.cos(ang + step) * 20.0d, pos.y + Math.sin(ang + step) * 20.0d);
			}
		}
	}
}
