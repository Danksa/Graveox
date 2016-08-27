package de.dspindler.graveox.ui;

public class WindowController
{
	private WindowData			data;
	private WindowView			view;
	
	public WindowController(WindowData data)
	{
		this.data = data;
		this.view = new WindowView();
	}
	
	public void show()
	{
		view.show(data.getStage());
	}
}
