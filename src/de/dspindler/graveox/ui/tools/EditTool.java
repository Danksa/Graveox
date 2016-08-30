package de.dspindler.graveox.ui.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class EditTool extends Tool
{
	public EditTool()
	{
		super("Edit", "Use to edit and manipulate bodies.");
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
		System.out.println("[EditTool][Click: " + e.getX() + ", " + e.getY() + "]");
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
