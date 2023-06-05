/*
 * Christopher Statton
 * OCCC Fall 2021
 * Advanced Java
 * Lightspeed Game
 * Foreground images
 * This gives the illusion of movement in-game
 */

import java.awt.Image;
import javax.imageio.*;

public class Stars {

	private Image stars;
	private int x, y;
	
	public Stars()
	{
		try
		{
			stars = ImageIO.read(this.getClass().getResource("/Lightspeed_Media/stars.png")).getScaledInstance(700, 350, Image.SCALE_SMOOTH);
		}
		catch (Exception e) 
		{
			System.out.println("Could not find Stars images. Exited Program.");
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
		if (newPosition < -800)
		{
			x = 800;
		}
		else
		{
			x = newPosition;
		}
	}
	
	public void setY(int newPosition)
	{
		y = newPosition;
	}
	
	public Image getStars()
	{
		return stars;
	}
}