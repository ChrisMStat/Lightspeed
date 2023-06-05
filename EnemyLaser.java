/*
 * Christopher Statton
 * OCCC Fall 2021
 * Advanced Java
 * Lightspeed Game
 * Enemy Lasers
 */

import java.awt.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class EnemyLaser {

	private int x, y;
	private Image laser, explosion;
	private boolean hit = false;
	public Clip laserSound;
	
	public EnemyLaser(int startingX, int startingY)
	{
		try
		{
			laser = ImageIO.read(this.getClass().getResource("/Lightspeed_Media/Enemylaser.png")).getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			explosion = ImageIO.read(this.getClass().getResource("/Lightspeed_Media/Explosion3.png"));
			x = startingX;
			y = startingY;
			
			// sound effects
			AudioInputStream audio = AudioSystem.getAudioInputStream(this.getClass().getResource("/Lightspeed_Media/enemyLaser.wav"));
			laserSound = AudioSystem.getClip();
			laserSound.open(audio);
			laserSound.start();
			
		}
		catch (Exception e) 
		{
			System.out.println("Could not find enemy laser image. Exited Program.");
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
	
	public boolean getHit()
	{
		return hit;
	}
	
	// method to set if this specific laser hit the player ship
	public void setHit(boolean value)
	{
		hit = value;
	}
	
	public void draw(Graphics g, int type)
	{
		if (type == 1)
		{
			g.drawImage(laser, x, y, null);
		}
		else if (type == 2)
		{
			g.drawImage(explosion, x, y, null);
		}
	}
}
