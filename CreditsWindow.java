/*
 * Christopher Statton
 * OCCC Fall 2021
 * Advanced Java
 * Lightspeed Game
 * Credits Window
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CreditsWindow extends JPanel implements ActionListener {

	private Image bg;
	private JPanel parent;
	private CardLayout window;
	private JButton closeButton;
	private JPanel textPanel;
	private JTextArea text;
	public Timer creditsTimer;
	private int lineCount;
	
	public CreditsWindow(Image bg)
	{
		this.bg = bg;
		
		textPanel = new JPanel();
		textPanel.setOpaque(false);
		
		// displayed credits text
		text = new JTextArea(12,40);
		text.setEditable(false);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setFont(new Font("Orbitron", Font.PLAIN, 16));
		text.setOpaque(false);	
		text.setForeground(Color.YELLOW);
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
		
		// sets timer to display credits one by one
		creditsTimer = new Timer(3000, this);
	}
	
	// method to display the credits sequentially
	public void displayText()
	{
		if (lineCount == 1)
		{
			text.setText("Designed by: Christopher Statton\n\n");
		}
		else if (lineCount == 2)
		{
			text.append("Programmed by: Christopher Statton\n\n");
		}
		else if (lineCount == 3)
		{
			text.append("All game image assets and backgrounds were gathered from opengameart.org\n\n");
		}
		else
		{
			creditsTimer.stop();
			text.append("Music and sound effects were also gathered from opengameart.org.\n\n");
		}
	}
	
	// sets/resets line count
	public void setLineCount(int startingLine)
	{
		lineCount = startingLine;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		parent = (JPanel)getParent();
		window = (CardLayout) parent.getLayout();
		
		if (e.getSource() == closeButton)
		{
			window.show(parent, "options");
		}
		else
		{
			lineCount++;
			displayText();
		}
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		g.drawImage(bg, 0, 0, null);
	}
}