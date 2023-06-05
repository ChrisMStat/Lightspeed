/*
 * Christopher Statton
 * OCCC Fall 2021
 * Advanced Java
 * Lightspeed Game
 * Main Menu Window
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class MainMenu extends JPanel implements ActionListener {

	private JButton startButton, howButton, optionsButton, quitButton;
	private Font titleFont;
	private Image bg;
	private JPanel mainPanel;
	private CardLayout cl;
	public JPanel cardsPanel;
	public GameWindow gw;
	private MapWindow mw;
	private IntroWindow iw;
	private TextWindow tw;
	private HowWindow hw;
	private OptionsWindow ow;
	private CreditsWindow cw;
	private Music m;
	private Font gameFont;
	
	public MainMenu()
	{
		// finds and opens background image and font
		openBackground();
		openFont();
		
		// creates card layout and panel
		cl = new CardLayout();
		cardsPanel = new JPanel(cl);
		
		// panel that holds everything
		mainPanel = new JPanel(new BorderLayout());
		
		// game title
		titleFont = new Font("Orbitron", Font.BOLD, 75);
		JPanel titlePanel = new JPanel();
		JLabel titleLabel = new JLabel("Lightspeed");
		titleLabel.setFont(titleFont);
		titleLabel.setForeground(Color.YELLOW);
		titlePanel.add(titleLabel);
		
		// buttons
		startButton = new JButton("Start New Game");
		buttonSettings(startButton);
		howButton = new JButton("How to Play");
		buttonSettings(howButton);
		optionsButton = new JButton("Options");
		buttonSettings(optionsButton);
		quitButton = new JButton("Quit");
		buttonSettings(quitButton);
		
		// button layout
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		GridBagConstraints layout = new GridBagConstraints();
		layout.anchor = GridBagConstraints.CENTER;
		layout.insets = new Insets(100, 0, 0, 0);
		layout.gridy = 0;
		buttonPanel.add(startButton, layout);
		layout.insets = new Insets(50, 0, 0, 0);
		layout.gridy = 1;
		buttonPanel.add(howButton, layout);
		layout.gridy = 2;
		buttonPanel.add(optionsButton, layout);
		layout.gridy = 3;
		buttonPanel.add(quitButton, layout);
		
		// ensures panel background is invisible
		buttonPanel.setOpaque(false);
		titlePanel.setOpaque(false);
				
		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		mainPanel.setOpaque(false);
				
		// creates all main menu "screens" or windows
		m = new Music();
		m.playMenuMusic();
		cardsPanel.add(mainPanel, "menu");
		cardsPanel.add(hw = new HowWindow(bg), "how");
		cardsPanel.add(cw = new CreditsWindow(bg), "credits");
		cardsPanel.add(ow = new OptionsWindow(bg, cw), "options");
		cardsPanel.setOpaque(false);
		cardsPanel.setPreferredSize(new Dimension(800, 600));
		this.add(cardsPanel);
		
		setVisible(true);
	}
	
	// method for opening background image
	public void openBackground()
	{
		try
		{
			bg = ImageIO.read(this.getClass().getResource("/Lightspeed_Media/MMBG.jpg")).getScaledInstance(800, 600, Image.SCALE_SMOOTH);
		}
		catch (Exception e) 
		{
			System.out.println("Could not find main menu background image. Exited Program.");
			System.out.println(e.toString());
			System.exit(0);
		}
	}
	
	// method for opening "Orbitron" font file
	public void openFont()
	{
		try
		{
			gameFont = Font.createFont(Font.TRUETYPE_FONT, new File("Orbitron-VariableFont_wght.ttf"));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(gameFont);
		}
		catch (Exception e) 
		{
			System.out.println("Could not find font. Exited Program.");
			System.out.println(e.toString());
			System.exit(0);
		}
	}
	
	// method for button settings
	public void buttonSettings(JButton button)
	{
		button.setOpaque(true);
		button.setFont(new Font("Orbitron", Font.PLAIN, 24));
		button.setBackground(Color.BLACK);
		button.setContentAreaFilled(false);
		button.setForeground(Color.YELLOW);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.addActionListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		g.drawImage(bg, 0, 0, null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startButton)
		{
			// creates a new game instance
			cardsPanel.add(gw = new GameWindow(bg, ow.getDifficulty(), m), "game");
			cardsPanel.add(iw = new IntroWindow(bg, gw, m), "intro");
			cardsPanel.add(gw.getTextWindow(), "text");
			cardsPanel.add(mw = new MapWindow(gw, gw.getTextWindow()), "map");
			m.playIntroMusic();
			cl.show(cardsPanel, "intro");			
		}
		
		else if (e.getSource() == howButton)
		{
			cl.show(cardsPanel, "how");
		}
		
		else if (e.getSource() == optionsButton)
		{
			cl.show(cardsPanel, "options");
		}
		
		else if (e.getSource() == quitButton)
		{
			System.exit(0);
		}
	}
}