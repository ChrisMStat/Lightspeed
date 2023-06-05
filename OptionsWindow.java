/*
 * Christopher Statton
 * OCCC Fall 2021
 * Advanced Java
 * Lightspeed Game
 * Options Window
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class OptionsWindow extends JPanel implements ActionListener {

	private Image bg;
	private CreditsWindow cw;
	private JButton closeButton, creditsButton;
	private ButtonGroup difficulties;
	private JRadioButton[] difficulty;
	private JPanel buttonPanel;
	private JPanel parent;
	private CardLayout window;
	
	public OptionsWindow(Image bg, CreditsWindow cw)
	{
		this.bg = bg;
		this.cw = cw;
		
		// panel for the difficulty buttons/options
		buttonPanel = new JPanel(new GridBagLayout());
		buttonPanel.setOpaque(false);
		
		// layout specifications
		GridBagConstraints layout = new GridBagConstraints();
		layout.gridy = 0;
		layout.insets = new Insets(0,0,30,0);
		
		// Label at the top of the window
		JLabel difficultyLabel = new JLabel("SELECT DIFFICULTY");
		difficultyLabel.setFont(new Font("Orbitron", Font.BOLD, 20));
		difficultyLabel.setForeground(Color.YELLOW);
		buttonPanel.add(difficultyLabel, layout);

		difficulties = new ButtonGroup();
		difficulty = new JRadioButton[4];
		
		// all difficulty buttons/options and their descriptions
		difficulty[0] = new JRadioButton("EASY: more starting HP - easier boss fight", false);
		difficulty[1] = new JRadioButton("NORMAL: default starting HP - normal boss fight", true);
		difficulty[2] = new JRadioButton("HARD: default starting HP - more levels - harder boss fight", false);
		difficulty[3] = new JRadioButton("DEMO: one normal level and the final boss level"
				+ " - FOR DEMO PURPOSES ONLY", false);
		
		// set ups each radial button as desired
		layout.anchor = GridBagConstraints.WEST;
		layout.insets = new Insets(0,0,10,0);
		for (int i = 0; i < difficulty.length; i++)
		{
			difficulty[i].setFont(new Font("Orbitron", Font.PLAIN, 14));
			difficulty[i].setForeground(Color.YELLOW);
			difficulty[i].setFocusPainted(false);
			difficulty[i].setBorderPainted(false);
			difficulty[i].setOpaque(false);
			difficulties.add(difficulty[i]);
			difficulty[i].addActionListener(this);
			layout.gridy++;
			buttonPanel.add(difficulty[i], layout);
		}
		
		// credits button
		creditsButton = new JButton("Show Credits");
		buttonSettings(creditsButton);
		layout.insets = new Insets(50,0,0,0);
		layout.anchor = GridBagConstraints.CENTER;
		layout.weighty = 10;
		layout.gridy++;
		buttonPanel.add(creditsButton, layout);
		
		// close button for options screen
		closeButton = new JButton("Close");
		buttonSettings(closeButton);		
		layout.gridy++;
		layout.insets = new Insets(20,0,0,0);
		buttonPanel.add(closeButton, layout);
		
		buttonPanel.setPreferredSize(new Dimension(800, 600));
		buttonPanel.setVisible(true);
		this.add(buttonPanel, BorderLayout.NORTH);
	}
	
	// method for button settings
	public void buttonSettings(JButton button)
	{
		button.setOpaque(true);
		button.setBackground(Color.YELLOW);
		button.setContentAreaFilled(true);
		button.setForeground(Color.BLACK);
		button.setFont(new Font("Orbitron", Font.BOLD, 26));
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.addActionListener(this);
	}
	
	// gets difficulty for use in GameWindow
	public int getDifficulty()
	{
		if (difficulty[0].isSelected())
		{
			return 1;
		}
		else if (difficulty[1].isSelected())
		{
			return 2;
		}
		else if (difficulty[2].isSelected())
		{
			return 3;
		}
		else if (difficulty[3].isSelected())
		{
			return 4;
		}
		return 2;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		parent = (JPanel)getParent();
		window = (CardLayout) parent.getLayout();
		
		if (e.getSource() == closeButton)
		{
			window.show(parent, "menu");
		}
		else if (e.getSource() == creditsButton)
		{
			cw.setLineCount(0);
			cw.creditsTimer.start();
			window.show(parent, "credits");
		}
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		g.drawImage(bg, 0, 0, null);
	}
}