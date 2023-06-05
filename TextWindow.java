/*
 * Christopher Statton
 * OCCC Fall 2021
 * Advanced Java
 * Lightspeed Game
 * Text Window for displaying event text
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TextWindow extends JPanel implements ActionListener {

	private Image bg;
	private GameWindow gw;
	private Music m;
	private JPanel textPanel;
	private JTextArea text;
	private Timer textTimer;
	private int textType, eventType;
	private int lineCount = 0;
	private JPanel parent;
	private CardLayout window;
	private JButton jumpButton;
	private boolean bossLevel;
	
	public TextWindow(Image background, GameWindow gw, Music m)
	{
		bg = background;
		this.gw = gw;
		this.m = m;
		
		textPanel = new JPanel();
		textPanel.setOpaque(false);
		
		// event text
		text = new JTextArea(12,40);
		text.setEditable(false);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setFont(new Font("Orbitron", Font.PLAIN, 18));
		text.setOpaque(false);	
		text.setForeground(Color.YELLOW);
		textPanel.add(text);
		
		// jump button
		jumpButton = new JButton("Jump!");
		jumpButton.setOpaque(true);
		jumpButton.setBackground(Color.YELLOW);
		jumpButton.setContentAreaFilled(true);
		jumpButton.setForeground(Color.BLACK);
		jumpButton.setFont(new Font("Orbitron", Font.BOLD, 26));
		jumpButton.setBorderPainted(false);
		jumpButton.addActionListener(this);
		jumpButton.setFocusPainted(false);
		jumpButton.setVisible(false);
		textPanel.add(jumpButton);
		
		textPanel.setPreferredSize(new Dimension(800, 600));
		text.setPreferredSize(new Dimension(700, 500));
		textPanel.setVisible(true);
		this.add(textPanel);
		
		// timer for displaying text sequentially
		textTimer = new Timer(1500, this);
	}
	
	// method for displaying the desired text
	public void showText()
	{
		// if player beats a normal level
		if (textType == 1)
		{ 
			winLevel();
		}
		
		// gets the current event text
		else if (textType == 2)
		{
			if (eventType == 1)
			{
				event1();
			}
			else if (eventType == 2)
			{
				event2();
			}
			else if (eventType == 3)
			{
				event3();
			}
			else if (eventType == 4)
			{
				event4();
			}
			else if (eventType == 5)
			{
				eventBoss();
			}
		}
		
		// if player defeats the boss and finished the game
		else if (textType == 3)
		{
			beatGame();
		}
		
		// if the player dies
		else
		{
			loseLevel();
		}
	}
	
	// method for displaying NORMAL level completion text
	public void winLevel()
	{
		if (lineCount == 1)
		{
			text.setText("SUCCESS!\n");
			text.append("You completed the level.\n");
			text.append("Ships Defeated: " + gw.getLevelDefeated() + "/" + gw.getLevelNeeded() + "\n\n");
		}
		else if (lineCount == 2)
		{
			text.append("The remaining enemies frantically jump out the system leaving behind "
					+ "only you and the debris of their fallen comrades.\n\n");
		}
		else
		{
			text.append("You take a minute to relax and then begin plotting your next jump.");
			textTimer.stop();
			jumpButton.setText("Open Map");
			jumpButton.setVisible(true);
		}
	}
	
	// method for displaying BOSS level completion text
	public void beatGame()
	{
		if (lineCount == 1)
		{
			text.setText("YOU WIN!\n\n");
		}
		else if (lineCount == 2)
		{
			text.append("By destroying the rebel's capital ship, you have dealt their forces a devastating blow.\n\n");
		}
		else if (lineCount == 3)
		{
			text.append("The UGE now has a chance to squash what remains of the rebel threat.\n\n");
		}
		else
		{
			textTimer.stop();
			text.append("Game Completed!\n");
			text.append("Total Ships Destroyed: " + gw.getTotalDefeated() + "\n");
			text.append("Total Levels Completed: " + (gw.getLevelCount()-1));
			jumpButton.setVisible(true);
		}
	}
	
	// method for displaying GAME OVER text
	public void loseLevel()
	{
		if (lineCount == 1)
		{
			text.setText("GAME OVER...\n\n");
			text.append("Your ship took too much damage and was destroyed.\n\n");
		}
		else if (lineCount == 2)
		{
			text.append("Though you fought valiantly, you were ultimately overcome by the rebel forces.\n\n");
		}
		else
		{
			textTimer.stop();
			text.append("\nTotal Ships Destroyed: " + gw.getTotalDefeated() + "\n");
			text.append("Total Levels Completed: " + (gw.getLevelCount()-1));
			jumpButton.setVisible(true);
		}
	}
	
	// method for other classes to set the desired text type
	public void setTextType(int type, int event, boolean boss)
	{
		jumpButton.setVisible(false);
		text.setText("");
		textType = type;
		if (textType == 1)
		{
			jumpButton.setText("Open Map");
		}
		else if (textType == 2)
		{
			jumpButton.setText("Jump!");
		}
		else
		{
			jumpButton.setText("Exit to Menu");
		}
		bossLevel = boss;
		lineCount = 0;
		eventType = event;
		textTimer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() != jumpButton)
		{
			++lineCount;
			showText();
		}
		else
		{
			parent = (JPanel)getParent();
			window = (CardLayout) parent.getLayout();
			if (bossLevel == true)
			{
				setTextType(2, 5, false);
				m.playBossMusic();
			}
			else
			{
				if (textType == 1)
				{
					window.show(parent, "map");
				}
				else if (textType == 2)
				{
					if (gw.getLevelCount() != gw.bossLevel)
					{
						m.playLevelMusic();
					}
					gw.resetPositions();
					gw.timer.start();
					window.show(parent, "game");
				}
				else
				{
					gw.removeAll();
					m.playMenuMusic();
					window.show(parent, "menu");
				}
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		g.drawImage(bg, 0, 0, null);
	}
	
	/*
	 * 
	 * All method below are the various random events that can occur
	 * 
	 */
	
	public void event1()
	{
		if (lineCount == 1)
		{
			text.setText("You jump to a new system...\n\n");
		}
		else if (lineCount == 2)
		{
			text.append("Noticing a nearby space station, you fly towards it.\n\n");
		}
		else if (lineCount == 3)
		{
			text.append("Upon docking in one of the exterior hangar bays, you meet some friendly UGE civilians who offer to repair your ship!\n\n");
			text.append("\nHP INCREASED!\n\n\n");
		}
		else if (lineCount == 4)
		{
			text.append("You thank them and prepare to jump to a nearby, rebel-controlled system...\n\n");
			gw.upgradeHP(3);
			jumpButton.setVisible(true);
		}
	}
	
	public void event2()
	{
		if (lineCount == 1)
		{
			text.setText("You jump to a new system...\n\n");
		}
		else if (lineCount == 2)
		{
			text.append("A distress signal pings across your dashboard. A UGE supply ship is being attacked by rebels.\n\n");

		}
		else if (lineCount == 3)
		{
			text.append("Immediately responding, you make short work of the small band of rebels.\n\n");
		}
		else if (lineCount == 4)
		{
			text.append("The supply ship's captain thanks you profusely and offers to upgrade you laser cannon!\n\n");
			text.append("\nLaser Range INCREASED!\n\n\n");
		}
		else if (lineCount == 5)
		{
			text.append("You thank them and prepare to jump to a nearby, rebel-controlled system...\n\n");
			gw.upgradeRange(50);
			jumpButton.setVisible(true);
		}
	}
	
	public void event3()
	{
		if (lineCount == 1)
		{
			text.setText("You jump to a new system...\n\n");
		}
		else if (lineCount == 2)
		{
			text.append("It appears the UGE and the insurrectionists recently battled here.\n\n");
		}
		else if (lineCount == 3)
		{
			text.append("However, nothing remains but destroyed ships.\n\n");
		}
		else if (lineCount == 4)
		{
			text.append("You scavenge what you can and manage to upgrade the rate of fire of your laser cannon!\n\n");
			text.append("\nRate of Fire INCREASED!\n\n\n");
		}
		else if (lineCount == 5)
		{
			text.append("You prepare to jump to a nearby rebel-controlled system...\n\n");
			gw.upgradeROF(3);
			jumpButton.setVisible(true);
		}
	}
	
	public void event4()
	{
		if (lineCount == 1)
		{
			text.setText("You jump to a new system...\n\n");
		}
		else if (lineCount == 2)
		{
			text.append("Your sensors detect somethind odd on a nearby moon.\n\n");
		}
		else if (lineCount == 3)
		{
			text.append("Upon landing on the moon, you discover a secret UGE research facility.\n\n");
		}
		else if (lineCount == 4)
		{
			text.append("The UGE scientists offer to support your cause and upgrade your ship!\n\n");
		}
		else if (lineCount == 5)
		{
			text.append("\nHP Slightly INCREASED!\n");
			text.append("Rate of Fire Slightly INCREASED!\n");
			text.append("Laser Range Slightly INCREASED!\n\n\n");
		}
		else if (lineCount == 6)
		{
			text.append("You thank them and prepare to jump to a nearby, rebel-controlled system...\n\n");
			gw.upgradeHP(2);
			gw.upgradeRange(25);
			gw.upgradeROF(2);
			jumpButton.setVisible(true);
		}
	}
	
	public void eventBoss()
	{
		if (lineCount == 1)
		{
			text.setText("However, as you are about to jump, you get an incoming transmission:\n\n");
		}
		else if (lineCount == 2)
		{
			text.append("...krshzzzzt...\n\n \"I have been tracking your ship for some time now.\"\n\n");
		}
		else if (lineCount == 3)
		{
			text.append("\" You have destroyed ENOUGH of my forces.\"\n\n");
		}
		else if (lineCount == 4)
		{
			text.append("\"No matter where you go next...I WILL DESTROY YOU!\" \n\n...krshzzzzt...\n\n");
		}
		else
		{
			textTimer.stop();
			text.append("The transmission frequency goes silent. You ready yourself and prepare for a final battle...\n\n");
			jumpButton.setVisible(true);
		}
	}
}