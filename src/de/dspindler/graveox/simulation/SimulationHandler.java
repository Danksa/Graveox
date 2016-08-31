package de.dspindler.graveox.simulation;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class SimulationHandler
{
	private ArrayList<EventListener>			listeners;
	
	public SimulationHandler()
	{
		listeners = new ArrayList<EventListener>();
	}
	
	public void addListener(EventListener listener)
	{
		this.listeners.add(listener);
	}
	
	public void addListeners(EventListener[] listeners)
	{
		for(EventListener l : listeners)
		{
			this.addListener(l);
		}
	}
	
	public void removeListener(EventListener listener)
	{
		this.listeners.remove(listener);
	}
	
	public class MouseClicked implements EventHandler<MouseEvent>
	{
		@Override
		public void handle(MouseEvent e)
		{
			for(EventListener l : listeners)
			{
				if(l.isEnabled())
				{
					l.onMouseClicked(e);
				}
			}
		}
	}
	
	public class MousePressed implements EventHandler<MouseEvent>
	{
		@Override
		public void handle(MouseEvent e)
		{
			for(EventListener l : listeners)
			{
				if(l.isEnabled())
				{
					l.onMousePressed(e);
				}
			}
		}	
	}
	
	public class MouseReleased implements EventHandler<MouseEvent>
	{
		@Override
		public void handle(MouseEvent e)
		{
			for(EventListener l : listeners)
			{
				if(l.isEnabled())
				{
					l.onMouseReleased(e);
				}
			}
		}	
	}
	
	public class MouseDragged implements EventHandler<MouseEvent>
	{
		@Override
		public void handle(MouseEvent e)
		{
			for(EventListener l : listeners)
			{
				if(l.isEnabled())
				{
					l.onMouseDragged(e);
				}
			}
		}
	}
	
	public class MouseMoved implements EventHandler<MouseEvent>
	{
		@Override
		public void handle(MouseEvent e)
		{
			for(EventListener l : listeners)
			{
				if(l.isEnabled())
				{
					l.onMouseMoved(e);
				}
			}
		}	
	}
	
	public class MouseScrolled implements EventHandler<ScrollEvent>
	{
		@Override
		public void handle(ScrollEvent e)
		{
			for(EventListener l : listeners)
			{
				if(l.isEnabled())
				{
					l.onMouseScrolled(e);
				}
			}
		}	
	}
	
	public class KeyPressed implements EventHandler<KeyEvent>
	{
		@Override
		public void handle(KeyEvent e)
		{
			for(EventListener l : listeners)
			{
				if(l.isEnabled())
				{
					l.onKeyPressed(e);
				}
			}
		}	
	}
	
	public class KeyReleased implements EventHandler<KeyEvent>
	{
		@Override
		public void handle(KeyEvent e)
		{
			for(EventListener l : listeners)
			{
				if(l.isEnabled())
				{
					l.onKeyReleased(e);
				}
			}
		}	
	}
	
	public class KeyTyped implements EventHandler<KeyEvent>
	{
		@Override
		public void handle(KeyEvent e)
		{
			for(EventListener l : listeners)
			{
				if(l.isEnabled())
				{
					l.onKeyTyped(e);
				}
			}
		}	
	}
}
