package de.dspindler.graveox.simulation;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public interface EventListener
{
	public void onMousePressed(MouseEvent e);
	public void onMouseReleased(MouseEvent e);
	public void onMouseClicked(MouseEvent e);
	
	public void onMouseMoved(MouseEvent e);
	public void onMouseDragged(MouseEvent e);
	
	public void onMouseScrolled(ScrollEvent e);
	
	public void onKeyPressed(KeyEvent e);
	public void onKeyReleased(KeyEvent e);
	public void onKeyTyped(KeyEvent e);
}
