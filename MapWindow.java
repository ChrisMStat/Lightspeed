/*
 * Christopher Statton
 * OCCC Fall 2021
 * Advanced Java
 * Lightspeed Game
 * Map Window that opens when players beats a level
 */

import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MapWindow extends JPanel implements ActionListener {

	private Image galaxy;
	private JButton[] beacons;
	private final int TOTAL_BEACONS = 10;
	private TextWindow tw;
	private GameWindow gw;
	private JPanel parent;
	private CardLayout window;
	
	public MapWindow(GameWindow gw, TextWindow tw)
	{
		this.gw = gw;
		this.tw = tw;
		openBackground();
		
		JPanel beaconPanel = new JPanel();
		beaconPanel.setOpaque(false);
		beaconPanel.setLayout(null);
		
		beacons = new JButton[TOTAL_BEACONS];
		for (int i = 0; i < beacons.length; i++)
		{
			beacons[i] = new JButton();
			beacons[i].setOpaque(true);
			beacons[i].setBackground(Color.YELLOW);
			beacons[i].setBorderPainted(false);
			beacons[i].addActionListener(this);
			beacons[i].setBounds((int)((Math.random()*680)+30), (int)((Math.random()*520)+30), 10, 10);
			beaconPanel.add(beacons[i]);
		}
		
		beaconPanel.setPreferredSize(new Dimension(800, 600));
		this.add(beaconPanel);
	}
	
	public void openBackground()
	{
		try
		{
			galaxy = ImageIO.read(this.getClass().getResource("/Lightspeed_Media/galaxy.jpg")).getScaledInstance(800, 600, Image.SCALE_SMOOTH);
		}
		catch (Exception e) 
		{
			System.out.println("Could not find galaxy map iamge. Exited Program.");
			System.exit(0);
		}
	}
	
	// gets a random event
	public void getBeaconEvent()
	{
		int event = (int)((Math.random()*3)+1);
		if (gw.getLevelCount() == gw.bossLevel)
		{
			tw.setTextType(2, event, true);
		}
		else
		{
			tw.setTextType(2, event, false);
		}
		
		// switches to event text window/screen
		parent = (JPanel)getParent();
		window = (CardLayout) parent.getLayout();
		window.show(parent, "text");
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		g.drawImage(galaxy, 0, 0, null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < beacons.length; i++)
		{
			if (e.getSource() == beacons[i])
			{
				beacons[i].setVisible(false);
				getBeaconEvent();
			}
		}
	}	
}