/*
 * Christopher Statton
 * OCCC Fall 2021
 * Advanced Java
 * Lightspeed Game
 * Main window for running and displaying the game
 */

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.awt.event.*;
import javax.swing.*;

public class GameWindow extends JPanel implements ActionListener {

	private TextWindow tw;
	private Music m;
	public Timer timer;
	private Image currentBG;
	private Backgrounds bg;
	private Stars stars1, stars2;
	private HashSet<Laser> lasers;
	private HashSet<EnemyLaser> lasersEnemy;
	private PlayerShip player;
	private HashSet<EnemyShip> enemies;
	private Action up, down, right, left, attack;
	private int playerX, playerY;
	private int min_x = 0, max_x = 700;
	private int min_y = 0, max_y = 490;
	private Font hudFont;
	private int enemiesNeeded, enemiesDefeated = 0, totalDefeated = 0;
	private JPanel parent;
	private CardLayout window;
	private int difficulty;
	private int levelCount = 1;
	private int aggro;
	private int bossMovement = 1;
	public int bossLevel = 5;
	private int bossHP = 40;
	
	public GameWindow(Image textBG, int difficulty, Music m)
	{
		// needed to display desired background for text sequences
		tw = new TextWindow(textBG, this, m);
		
		// music
		this.m = m;
		
		// gets difficulty from options menu
		this.difficulty = difficulty;
		
		// creates font for HP and Enemies Defeated ui
		hudFont = new Font("Orbitron", Font.PLAIN, 16);
		
		// setups backgrounds and star dust images
		bg = new Backgrounds();
		stars1 = new Stars();
		stars2 = new Stars();
		stars1.setX(400);
		stars1.setY(-50);
		stars2.setX(1200);
		stars2.setY(200);
		currentBG = bg.getBG((int)(Math.random()*4));
		
		// creates the player ship and sets starting position
		player = new PlayerShip();
		playerX = player.getX();
		playerY = player.getY();
		lasers = new HashSet<>();
		
		// sets starting aggro for enemies laser frequency
		aggro = 994;
		enemies = new HashSet<>();
		lasersEnemy = new HashSet<>();
		
		// starting value for how many needed to defeat
		enemiesNeeded = 3;
		
		// if difficulty was set to "Easy", increase player's HP
		if (difficulty == 1)
		{
			player.increaseHP(5);
		}
		
		// if difficulty was set to "Hard", increase levels before boss and increase boss HP
		if (difficulty == 3)
		{
			bossLevel = 10;
			bossHP = 80;
		}
		
		// if difficulty was set to "Demo", boss level is second level
		if (difficulty == 4)
		{
			bossLevel = 2;
		}
		
		// setups timer to desired "game speed"
		timer = new Timer(25, this);
		
		// creates key bindings needed to control ship
		up = new MoveUp();
		down = new MoveDown();
		left = new MoveLeft();
		right = new MoveRight();
		attack = new fireLaser();
		createKeyBindings();
		setFocusable(true);
	}
	
	// method to paint all elements
	public void paintComponent(Graphics g)
	{
		// background image
		g.drawImage(currentBG, 0, 0, null);
		
		// player ship/explosion image
		if (player.getHP() > 0)
		{
			player.draw(g, 1);
		}
		else
		{
			player.draw(g, 2);
		}
		
		// enemy ship/explosion images
		for (Iterator<EnemyShip> it = enemies.iterator(); it.hasNext();) 
		{
		    EnemyShip e = it.next();
		    if (e.getHP() > 0 && e.getX() > -100)
		    {
				e.draw(g, 1);
		    }
		    else
		    {
		    	e.draw(g, 2);
		    	it.remove();
		    }
		}
		
		// player laser images
		for (Iterator<Laser> it = lasers.iterator(); it.hasNext();) 
		{
		    Laser e = it.next();
		    if (e.getHit() == true)
		    {
		    	e.draw(g, 2);
		    	it.remove();
		    }
		    else
		    {
		    	e.draw(g, 1);
		    }
		}
		
		// enemy laser images
		for (Iterator<EnemyLaser> it = lasersEnemy.iterator(); it.hasNext();) 
		{
		    EnemyLaser e = it.next();
		    if (e.getHit() == true)
		    {
		    	e.draw(g, 2);
		    	it.remove();
		    }
		    else
		    {
		    	e.draw(g, 1);
		    }
		}
		
		// star dust foreground image
		g.drawImage(stars1.getStars(), stars1.getX(), stars1.getY(), null);
		g.drawImage(stars2.getStars(), stars2.getX(), stars2.getY(), null);
		
		// HUD/UI
		g.setFont(hudFont);
		g.setColor(Color.YELLOW);
		g.drawString("Your HP: " + player.getHP(), 10, 30);
		g.drawString("Enemies Defeated: " + enemiesDefeated + "/" + enemiesNeeded, 500, 30);
		if (levelCount == bossLevel)
		{
			g.drawString("Boss HP: " + enemies.iterator().next().getHP(), 250, 30);	
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// moves star dust for illusion of movement
		stars1.setX(stars1.getX()-10);
		stars2.setX(stars2.getX()-10);
		
		// enemy ships actions if NOT boss level
		if (levelCount != bossLevel)
		{
			enemyActions();
		}
		
		// boss ship actions if boss level
		else
		{
			bossActions();
		}
		
		// move lasers across screen
		laserMove();
		
		// checks if laser hit a ship
		damage();
		
		// updates all image elements on screen
		repaint();
		
		// respawns enemies if needed
		enemyRespawn();
		
		// if player loses by running out of HP
		if (player.getHP() <= 0)
		{
			playerDeath();
		}
		
		// if player defeated all enemies needed for the level
		if (enemiesDefeated == enemiesNeeded)
		{
			completeLevel();
		}
	}
	
	// actions for normal enemies
	public void enemyActions()
	{
		for (EnemyShip enemy: enemies)
		{
			if (enemy.getHP() > 0)
			{
				enemy.setX(enemy.getX() - 3);
				if (enemy.getX() < 800 && enemy.getX() > -50)
				{
					int enemyFire = (int)(Math.random()*1001);
					if (enemyFire > enemy.getAggro())
					{
						lasersEnemy.add(new EnemyLaser(enemy.getX()-50, enemy.getY()+12));
					}
				}
			}
		}
	}
	
	// actions for boss enemy
	public void bossActions()
	{
		for (EnemyShip enemy: enemies)
		{
			if (enemy.getHP() > 0)
			{
				if (enemy.getX() <= 200)
				{
					bossMovement = 2;
				}
				else if (enemy.getX() >= 800)
				{
					bossMovement = 1;
				}
				if (bossMovement == 1)
				{
					enemy.setX(enemy.getX() - 3);
				}
				else
				{
					enemy.setX(enemy.getX() + 3);
				}
				if (enemy.getX() < 800 && enemy.getX() > -50)
				{
					int enemyFire = (int)(Math.random()*1001);
					if (enemyFire > enemy.getAggro())
					{
						lasersEnemy.add(new EnemyLaser(enemy.getX()-50, enemy.getY()+12));
					}
					enemyFire = (int)(Math.random()*1001);
					if (enemyFire > enemy.getAggro())
					{
						lasersEnemy.add(new EnemyLaser(enemy.getX()-50, enemy.getY()+212));
					}
					enemyFire = (int)(Math.random()*1001);
					if (enemyFire > enemy.getAggro())
					{
						lasersEnemy.add(new EnemyLaser(enemy.getX()-50, enemy.getY()+412));
					}
					
					// adds extra lasers for every difficulty but "Easy"
					if (difficulty != 1)
					{
						enemyFire = (int)(Math.random()*1001);
						if (enemyFire > enemy.getAggro())
						{
							lasersEnemy.add(new EnemyLaser(enemy.getX()-50, enemy.getY()+112));
						}
						enemyFire = (int)(Math.random()*1001);
						if (enemyFire > enemy.getAggro())
						{
							lasersEnemy.add(new EnemyLaser(enemy.getX()-50, enemy.getY()+312));
						}
					}
				}
			}
		}
	}
	
	// method for moving lasers across the screen
	public void laserMove()
	{
		// player lasers
		for (Iterator<Laser> it = lasers.iterator(); it.hasNext();) 
		{
		    Laser l = it.next();
		    l.setX(l.getX()+8);
		    if (l.getX() > player.getX() + player.getLaserRange())
		    {
		    	it.remove();
		    }
		}
		
		// enemy lasers
		for (Iterator<EnemyLaser> it = lasersEnemy.iterator(); it.hasNext();) 
		{
		    EnemyLaser l = it.next();
		    l.setX(l.getX()-6);
		    if (l.getX() < 0)
		    {
		    	it.remove();
		    }
		}
	}
	
	// method for respawning Enemy ships
	public void enemyRespawn()
	{	
		// normal levels
		if (enemies.size() < 5 && levelCount != bossLevel)
		{
			enemies.add(new EnemyShip(aggro));
		}
		
		// boss level
		else if (levelCount == bossLevel && enemies.size() < 1)
		{
			enemies.add(new EnemyShip(aggro, bossHP));
		}
	}
	
	// method to check if lasers hit a ship
	public void damage()
	{	
		// PLAYER HITTING ENEMY
		// if boss is not active
		if (levelCount != bossLevel)
		{
			for (EnemyShip enemy: enemies)
			{
				for (Laser l: lasers)
				{
					if (enemy.getX() - l.getX() <= 20 && enemy.getX() - l.getX() > 0 &&
							l.getY() - enemy.getY() <= 50 && l.getY() - enemy.getY() > -50)
					{
						l.setHit(true);
						enemy.takeDamage(1);
						if (enemy.getHP() <= 0)
						{
							enemy.setDeathX(enemy.getX());
							enemy.setDeathY(enemy.getY());
					    	enemy.deathSound.start();
					    	enemiesDefeated++;
					    	totalDefeated++;
						}
					}
				}
			}
		}
		
		// if boss IS active
		else
		{
			for (EnemyShip enemy: enemies)
			{
				for (Laser l: lasers)
				{
					if (enemy.getX() - l.getX() <= 10 && enemy.getX() - l.getX() > -10 &&
							l.getY() - enemy.getY() <= 500 && l.getY() - enemy.getY() > -500)
					{
						l.setHit(true);
						enemy.takeDamage(1);
						if (enemy.getHP() <= 0)
						{
							enemy.setDeathX(enemy.getX());
							enemy.setDeathY(enemy.getY());
					    	enemy.deathSound.start();
					    	enemiesDefeated++;
					    	totalDefeated++;
						}
					}
				}
			}
		}
		
		// ENEMY HITTING PLAYER
		for (EnemyLaser l: lasersEnemy)
		{
			if (l.getX() - player.getX() <= 30 && l.getX() - player.getX() > 10 &&
					l.getY() - player.getY() <= 50 && l.getY() - player.getY() > -50)
			{
				l.setHit(true);
				player.takeDamage(1);
				if (player.getHP() <= 0)
				{
					player.deathSound.start();
					player.setDeathX(player.getX());
					player.setDeathY(player.getY());
				}
			}
		}
	}
	
	// method for when/if player loses all HP
	public void playerDeath()
	{
		timer.stop();
		parent = (JPanel)getParent();
		window = (CardLayout) parent.getLayout();
		this.removeAll();
		tw.setTextType(4, 0, false);
		window.show(parent, "text");
	}
	
	// method for when/if player successfully completes a level
	public void completeLevel()
	{
		timer.stop();
		parent = (JPanel)getParent();
		window = (CardLayout) parent.getLayout();
		
		// starts another normal level
		if (levelCount != bossLevel)
		{
			levelCount++;
			tw.setTextType(1, 0, false);
		}
		
		// starts the final boss level
		else
		{
			levelCount++;
			tw.setTextType(3, 0, false);
		}
		
		// shows text window
		m.playVictoryMusic();
		window.show(parent, "text");
	}
	
	// method to reset positions and values when beginning a new level
	public void resetPositions()
	{
		enemiesDefeated = 0;
		if (levelCount != bossLevel)
		{
			aggro -= 2;
			enemiesNeeded+=2;
		}
		else
		{
			aggro = 985;
			enemiesNeeded=1;
		}
		player.setX(player.STARTING_X);
		player.setY(player.STARTING_Y);
		playerX = player.getX();
		playerY = player.getY();
		lasersEnemy.removeAll(lasersEnemy);
		lasers.removeAll(lasers);
		enemies.removeAll(enemies);
		currentBG = bg.getBG((int)(Math.random()*4));
		enemyRespawn();
	}
	
	// method to upgrade player HP
	public void upgradeHP(int increase)
	{
		player.increaseHP(increase);
	}
	
	// method to upgrade player laser range
	public void upgradeRange(int increase)
	{
		player.upgradeLaserRange(increase);
	}
	
	// method to upgrad player laser rate of fire
	public void upgradeROF(int increase)
	{
		player.upgradeROF(increase);
	}
	
	// get number of enemies defeated in previous level
	public int getLevelDefeated()
	{
		return enemiesDefeated;
	}
	
	// get number of enemies needed to win previous level
	public int getLevelNeeded()
	{
		return enemiesNeeded;
	}
	
	// gets total number of levels completed
	public int getLevelCount()
	{
		return levelCount;
	}
	
	// gets total number of enemies defeated
	public int getTotalDefeated()
	{
		return totalDefeated;
	}
	
	// method for getting the correct text window
	public TextWindow getTextWindow()
	{
		return tw;
	}
	
	
	/*
	 * 
	 * All code below is for keybind and movement purposes
	 * 
	 */
	
	public void createKeyBindings()
	{
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "moveUp");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F"), "fireLaser");
		getActionMap().put("moveRight", right);
		getActionMap().put("moveLeft", left);
		getActionMap().put("moveUp", up);
		getActionMap().put("moveDown", down);
		getActionMap().put("fireLaser", attack);
	}
	
	public class MoveUp extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e)
		{
			playerY -= 35;
			playerY = Math.max(min_y, playerY);
			playerY = Math.min(max_y, playerY);
			player.setY(playerY);
			repaint();
		}
	}
	public class MoveDown extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e)
		{
			playerY += 35;
			playerY = Math.max(min_y, playerY);
			playerY = Math.min(max_y, playerY);
			player.setY(playerY);
			repaint();
		}
	}
	public class MoveLeft extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e)
		{
			playerX -= 35;
			playerX = Math.max(min_x, playerX);
			playerX = Math.min(max_x, playerX);
			player.setX(playerX);
			repaint();
		}
	}
	public class MoveRight extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e)
		{
			playerX += 35;
			playerX = Math.max(min_x, playerX);
			playerX = Math.min(max_x, playerX);
			player.setX(playerX);
			repaint();
		}
	}
	
	public class fireLaser extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (lasers.size() < player.getLaserROF())
			{
				lasers.add(new Laser(player.getX()+50, player.getY()+12));
				repaint();
			}
		}
	}
}
