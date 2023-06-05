/*
 * Christopher Statton
 * OCCC Fall 2021
 * Advanced Java
 * Lightspeed Game
 * Background Images
 */

import java.awt.Image;
import javax.imageio.*;

public class Backgrounds {

	private Image[] bg;
	private int x = 0;
	private int y = 0;
	
	public Backgrounds()
	{
		bg = new Image[4];
		
		try
		{
			for (int i = 0; i < bg.length; i++)
			{
				bg[i] = ImageIO.read(this.getClass().getResource("/Lightspeed_Media/bg" + (i+1) + ".jpg")).getScaledInstance(800, 600, Image.SCALE_SMOOTH);
			}
		}
		catch (Exception e) 
		{
			System.out.println("Could not find background images. Exited Program.");
			System.out.println(e.toString());
			System.exit(0);
		}
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setX(int newPosition)
	{
		x = newPosition;
	}
	
	public void setY(int newPosition)
	{
		y = newPosition;
	}
	
	// method to get a different background each level
	public Image getBG(int index)
	{
		return bg[index];
	}
}
