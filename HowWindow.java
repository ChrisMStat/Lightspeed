/*
 * Christopher Statton
 * OCCC Fall 2021
 * Advanced Java
 * How To Play Panel for Lightspeed game
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HowWindow extends JPanel implements ActionListener {

	private Image bg;
	private JButton closeButton;
	private JPanel textPanel;
	private JTextArea text;
	private JPanel parent;
	private CardLayout window;
	
	public HowWindow(Image bg)
	{
		this.bg = bg;
		
		textPanel = new JPanel();
		textPanel.setOpaque(false);

		// how to play text
		text = new JTextArea(12,40);
		text.setEditable(false);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setFont(new Font("Orbitron", Font.PLAIN, 14));
		text.setOpaque(false);	
		text.setForeground(Color.YELLOW);
		setInstructions();
		textPanel.add(text);
		
		// close button
		closeButton = new JButton("Close");
		closeButton.setOpaque(true);
		closeButton.setBackground(Color.YELLOW);
		closeButton.setContentAreaFilled(true);
		closeButton.setForeground(Color.BLACK);
		closeButton.setFont(new Font("Orbitron", Font.BOLD, 26));
		closeButton.setBorderPainted(false);
		closeButton.setFocusPainted(false);
		closeButton.addActionListener(this);
		textPanel.add(closeButton);
		
		textPanel.setPreferredSize(new Dimension(800, 600));
		text.setPreferredSize(new Dimension(700, 500));
		textPanel.setVisible(true);
		this.add(textPanel);
	}
	
	// method for setting how to play text
	public void setInstructions()
	{
		text.setText("CONTROLS:\n\n");
		text.append("- Use arrow keys to move ship.\n");
		text.append("- Use 'F' to fire lasers.\n");
		text.append("- Use mouse to click the beacons on map.\n");
		
		text.append("\nGAME INSTRUCTIONS:\n\n");
		text.append("- Your ship's health is displayed at the top left of the screen during each level.\n");
		text.append("- Each level you must defeat a certain number of ships.\n");
		text.append("- The number needed increases each level and is displayed at the top right of the screen.\n");
		text.append("- A map opens as soon you complete a level. You must then select a beacon to jump/warp to.\n");
		text.append("- To beat the game, you must complete all levels and defeat the final boss ship.\n");
		
		text.append("\nTIPS:\n\n");
		text.append("- Each level gets slightly more difficult.\n");
		text.append("- The map beacons are the yellow squares on the galaxy map. The layout is random each run.\n");
		text.append("- Each new beacon you jump to will reward you with a completely random ship upgrade.\n");
		text.append("- Try flying behind the boss if it gets too close to you.\n");
		text.append("- Additionally, you can find difficulty toggles in the 'Options' menu.\n");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == closeButton)
		{
			parent = (JPanel)getParent();
			window = (CardLayout) parent.getLayout();
			window.show(parent, "menu");
		}
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		g.drawImage(bg, 0, 0, null);
	}
}