/*
 * Christopher Statton
 * OCCC Fall 2021
 * Advanced Java
 * Lightspeed Game
 * Enemy Ships
 */

import java.awt.*;
import javax.imageio.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;

public class EnemyShip extends JPanel {

	private Image ship;
	private Image[] explosion;
	private int x = (int)((Math.random()*1125)+875);
	private int y = (int)((Math.random()*480)+10);
	private int deathX, deathY;
	private int aggro;
	public Clip deathSound;
	private int hp = 3;
	private final int BOSS_Y = 40, BOSS_X = 1100;
	
	// normal ship constructor
	public EnemyShip(int newAggro)
	{	
		try
		{
			int shipDesign = (int)((Math.random()*4)+1);
			ship = ImageIO.read(this.getClass().getResource("/Lightspeed_Media/enemy" + shipDesign + ".png")).getScaledInstance(75, 75, Image.SCALE_SMOOTH);
			
			explosion = new Image[5];
			for (int i = 0; i < explosion.length; i++)
			{
				explosion[i] = ImageIO.read(this.getClass().getResource("/Lightspeed_Media/Explosion" + (i+1) + ".png")).getScaledInstance(75, 75, Image.SCALE_SMOOTH);
			}
			
			// sound effect for dying/blowing up
			AudioInputStream audio = AudioSystem.getAudioInputStream(this.getClass().getResource("/Lightspeed_Media/enemyDeath.wav"));
			deathSound = AudioSystem.getClip();
			deathSound.open(audio);
			
			//stats
			aggro = newAggro;
		}
		catch (Exception e) 
		{
			System.out.println("Could not find enemy ship images. Exited Program.");
			System.exit(0);
		}
	}
	
	// boss ship constructor
	public EnemyShip(int newAggro, int bossHP)
	{	
		try
		{
			ship = ImageIO.read(this.getClass().getResource("/Lightspeed_Media/Boss.png")).getScaledInstance(150, 500, Image.SCALE_SMOOTH);
			
			explosion = new Image[5];
			for (int i = 0; i < explosion.length; i++)
			{
				explosion[i] = ImageIO.read(this.getClass().getResource("/Lightspeed_Media/Explosion" + (i+1) + ".png")).getScaledInstance(150, 500, Image.SCALE_SMOOTH);
			}
			
			// death sound effect
			AudioInputStream audio = AudioSystem.getAudioInputStream(this.getClass().getResource("/Lightspeed_Media/enemyDeath.wav"));
			deathSound = AudioSystem.getClip();
			deathSound.open(audio);
			
			// stats
			hp = bossHP;
			x = BOSS_X;
			y = BOSS_Y;
			aggro = newAggro;
		}
		catch (Exception e) 
		{
			System.out.println("Could not find enemy ship images. Exited Program.");
			System.exit(0);
		}
	}
	
	/*
	 * 
	 * Methods for enemy ship image
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
	
	/*
	 * 
	 * Methods for enemy ship stats
	 * 
	 */
	
	public int getAggro()
	{
		return aggro;
	}
	
	public void setAggro(int newAggro)
	{
		aggro = newAggro;
	}
	
	public int getHP()
	{
		return hp;
	}
	
	public void setHP(int newHP)
	{
		hp = newHP;
	}
	
	public void takeDamage(int damage)
	{
		hp -= damage;
	}
	
	/*
	 * 
	 * Methods for enemy ship death
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
	
	// method for drawing enemy ship
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