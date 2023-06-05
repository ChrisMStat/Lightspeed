/*
 * Christopher Statton
 * OCCC Fall 2021
 * Advanced Java
 * Lightspeed Game
 * Player Ship
 */

import java.awt.*;
import javax.imageio.*;
import javax.sound.sampled.*;

public class PlayerShip {

	private Image ship;
	private Image[] explosion;
	public final int STARTING_X = 20;
	public final int STARTING_Y = 225;
	private int x, y;
	private int hp;
	private int deathX, deathY;
	private int laserRange, laserROF;
	private final int DEFAULT_RANGE = 300;
	private final int DEFAULT_HP = 5;
	private final int DEFAULT_ROF = 3;
	public Clip deathSound;
	
	// ship constructor
	public PlayerShip()
	{		
		try
		{
			ship = ImageIO.read(this.getClass().getResource("/Lightspeed_Media/ship1.png")).getScaledInstance(75, 75, Image.SCALE_SMOOTH);
			explosion = new Image[5];
			for (int i = 0; i < explosion.length; i++)
			{
				explosion[i] = ImageIO.read(this.getClass().getResource("/Lightspeed_Media/Explosion" + (i+1) + ".png")).getScaledInstance(75, 75, Image.SCALE_SMOOTH);
			}
			
			// sound effect for death
			AudioInputStream audio = AudioSystem.getAudioInputStream(this.getClass().getResource("/Lightspeed_Media/playerDeath.wav"));
			deathSound = AudioSystem.getClip();
			deathSound.open(audio);			
			
			// stats
			laserRange = DEFAULT_RANGE;
			laserROF = DEFAULT_ROF;
			hp = DEFAULT_HP;
			x = STARTING_X;
			y = STARTING_Y;
		}
		catch (Exception e) 
		{
			System.out.println("Could not find player ship images. Exited Program.");
			System.exit(0);
		}
	}
	
	/*
	 * 
	 *  Methods for ship image
	 *
	 */
	
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
	
	public int getHP()
	{
		return hp;
	}
	
	/*
	 * 
	 *  Methods for ship upgrades
	 *
	 */
	
	public void setHP()
	{
		hp = DEFAULT_HP;
	}
	
	public void increaseHP(int increase)
	{
		hp += increase;
	}
	
	public void takeDamage(int damage)
	{
		hp -= damage;
	}
	
	public int getLaserRange()
	{
		return laserRange;
	}
	
	public int getLaserROF()
	{
		return laserROF;
	}
	
	public void upgradeLaserRange(int increase)
	{
		laserRange += increase;
	}
	
	public void setLaserRange(int increase)
	{
		laserRange = DEFAULT_RANGE;
	}
	
	public void upgradeROF(int increase)
	{
		laserROF += increase;
	}
	
	/*
	 * 
	 *  Methods for ship runs out of HP
	 *
	 */
	
	public int getDeathX()
	{
		return deathX;
	}
	
	public void setDeathX(int value)
	{
		deathX = value;
	}
	
	public int getDeathY()
	{
		return deathY;
	}
	
	public void setDeathY(int value)
	{
		deathY = value;
	}
	
	// method for drawing the ship image
	public void draw(Graphics g, int type)
	{
		if (type == 1)
		{
			g.drawImage(ship, x, y, null);
		}
		else if (type == 2)
		{
			for (int i = 0; i < explosion.length; i++)
			{
				g.drawImage(explosion[i], x, y, null);
			}
		}
	}
}