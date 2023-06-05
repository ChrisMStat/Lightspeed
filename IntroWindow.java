/*
 * Christopher Statton
 * OCCC Fall 2021
 * Advanced Java
 * Lightspeed Game
 * Intro Text Window when starting a new game
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class IntroWindow extends JPanel implements ActionListener {

	private JPanel introPanel;
	private JTextArea introText;
	private Timer textTimer;
	private Image bg;
	private int line = 0;
	private GameWindow gw;
	private Music m;
	private JPanel parent;
	private CardLayout window;
	
	public IntroWindow(Image background, GameWindow gw, Music m)
	{
		bg = background;
		this.gw = gw;
		this.m = m;
		introPanel = new JPanel();
		introPanel.setOpaque(false);
		introText = new JTextArea(12,40);
		introText.setEditable(false);
		introText.setLineWrap(true);
		introText.setWrapStyleWord(true);
		introText.setFont(new Font("Orbitron", Font.PLAIN, 18));
		introText.setOpaque(false);	
		introText.setForeground(Color.YELLOW);
		introPanel.add(introText);
		introPanel.setPreferredSize(new Dimension(800, 600));
		
		this.add(introPanel);
		
		// timer to display each line of text sequentially
		textTimer = new Timer(3000, this);
		textTimer.start();
	}
	
	// method to display text line by line
	public void showIntroText()
	{
		if (line == 1)
		{
			introText.append("\nThe year is 2765. The galaxy is dying...\n\n");
		}
		else if (line == 2)
		{
			introText.append("A war rages between the Unified Governemnt of Earth and insurrectionists...\n\n");
		}
		else if (line == 3)
		{
			introText.append("The insurrectionists are stealing fuel, food, and supplies from UGE civilians...murdering as they go...\n\n");
		}
		else if (line == 4)
		{
			introText.append("You are the UGE's best pilot and you have been tasked with eliminating this rebel threat.\n\n\n\n");
		}
		else if (line == 5)
		{
			introText.append("Good Luck...\n");
		}
		else
		{
			textTimer.stop();
			parent = (JPanel)getParent();
			window = (CardLayout) parent.getLayout();
			
			// starts and displays the game
			gw.timer.start();
			m.playLevelMusic();
			window.show(parent, "game");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		++line;
		showIntroText();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		g.drawImage(bg, 0, 0, null);
	}
}