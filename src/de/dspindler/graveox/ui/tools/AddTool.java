package de.dspindler.graveox.ui.tools;

import de.dspindler.graveox.simulation.SimulationController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class AddTool extends Tool
{
	public AddTool(SimulationController simulation)
	{
		super("Add", "Use to add new bodies to the simulation.", simulation);
	}

	@Override
	public void onMousePressed(MouseEvent e)
	{
		
	}

	@Override
	public void onMouseReleased(MouseEvent e)
	{
		
	}

	@Override
	public void onMouseClicked(MouseEvent e)
	{
		System.out.println("[AddTool][Click: " + e.getX() + ", " + e.getY() + "]");
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
	public void renderForeground(GraphicsContext g)
	{
		// Draw length scale
		super.drawLengthScale(g);
	}
	
	@Override
	public void renderBackground(GraphicsContext g)
	{
		
	}
}
