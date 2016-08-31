package de.dspindler.graveox.simulation;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public abstract class EventListener
{
	private boolean			enabled;
	
	public EventListener()
	{
		this.enabled = true;
	}
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
	
	public abstract void onMousePressed(MouseEvent e);
	public abstract void onMouseReleased(MouseEvent e);
	public abstract void onMouseClicked(MouseEvent e);
	
	public abstract void onMouseMoved(MouseEvent e);
	public abstract void onMouseDragged(MouseEvent e);
	
	public abstract void onMouseScrolled(ScrollEvent e);
	
	public abstract void onKeyPressed(KeyEvent e);
	public abstract void onKeyReleased(KeyEvent e);
	public abstract void onKeyTyped(KeyEvent e);
}
