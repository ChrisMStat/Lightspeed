/*
 * Christopher Statton
 * OCCC Fall 2021
 * Advanced Java
 * Lightspeed Game Launcher
 */

import javax.swing.*;

public class Lightspeed extends JFrame {

	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	public static void main(String[] args) {
		Lightspeed startGame = new Lightspeed();
	}
	
	public Lightspeed()
	{
		super("Lightspeed");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		MainMenu menu = new MainMenu();
		this.add(menu);	
		setVisible(true);			
	}
}