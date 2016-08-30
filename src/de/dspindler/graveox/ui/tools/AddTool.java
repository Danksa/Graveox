package de.dspindler.graveox.ui.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class AddTool extends Tool
{
	public AddTool()
	{
		super("Add", "Use to add new bodies to the simulation.");
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
		
	}
}
