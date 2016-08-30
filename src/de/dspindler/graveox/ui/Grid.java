package de.dspindler.graveox.ui;

import de.dspindler.graveox.util.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Grid
{
	private Vector2			center;
	private double			radius;
	private int				segmentCount;
	private double			segmentGap;
	private int				sectorCount;
	
	public Grid(Vector2 center, double radius, int segmentCount, int sectorCount)
	{
		this.center = center.clone();
		this.radius = radius;
		this.segmentCount = segmentCount;
		this.segmentGap = radius / segmentCount;
		this.sectorCount = sectorCount;
	}
	
	public void setCenter(Vector2 center)
	{
		this.center.set(center);
	}
	
	public void render(GraphicsContext g)
	{
		double sin, cos;
		double angle;
		double stepSize = Math.PI * 2.0d / sectorCount;
		
		int steps;
		double addAngle;
		double stepSize2;
		double sin2, cos2;
		double sin3, cos3;
		
		for(int i = 0; i < sectorCount; ++i)
		{
			angle = i * stepSize;
			sin = Math.sin(angle);
			cos = Math.cos(angle);
			
			// Draw radial lines
			g.setStroke(Color.WHITE);
			g.strokeLine(center.x, center.y, center.x + cos * radius, center.y + sin * radius);
			
			// Draw tangent lines
			for(int y = 1; y < segmentCount + 1; ++y)
			{
				steps = (int)((segmentGap * y * stepSize) / 6.0d);
				stepSize2 = stepSize / steps;
				
				for(int j = 0; j < steps; ++j)
				{
					addAngle = j * stepSize2;
					sin2 = Math.sin(angle + addAngle);
					cos2 = Math.cos(angle + addAngle);
					
					sin3 = Math.sin(angle + addAngle + stepSize2);
					cos3 = Math.cos(angle + addAngle + stepSize2);
					
					g.strokeLine(center.x + cos2 * segmentGap * y, center.y + sin2 * segmentGap * y, center.x + cos3 * segmentGap * y, center.y + sin3 * segmentGap * y);
				}
			}
		}
	}
}
