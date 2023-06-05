/*
 * Christopher Statton
 * OCCC Fall 2021
 * Advanced Java
 * Lightspeed Game
 * Handles all the various music throughout the game
 */

import javax.sound.sampled.*;

public class Music {

	public Clip[] levelMusic;
	public Clip bossMusic, menuMusic, victoryMusic, introMusic;
	
	public Music()
	{
		try
		{		
			// music for the boss fight
			AudioInputStream audio = AudioSystem.getAudioInputStream(this.getClass().getResource("/Lightspeed_Media/Boss.wav"));
			bossMusic = AudioSystem.getClip();
			bossMusic.open(audio);			
			
			// music for each level
			levelMusic = new Clip[3];
			for (int i = 0; i < levelMusic.length; i++)
			{
				audio = AudioSystem.getAudioInputStream(this.getClass().getResource("/Lightspeed_Media/Battle" + (i+1) + ".wav"));
				levelMusic[i] = AudioSystem.getClip();
				levelMusic[i].open(audio);
			}
			
			// music for completing a level
			audio = AudioSystem.getAudioInputStream(this.getClass().getResource("/Lightspeed_Media/Victory.wav"));
			victoryMusic =  AudioSystem.getClip();
			victoryMusic.open(audio);
			
			// music for the main menu
			audio = AudioSystem.getAudioInputStream(this.getClass().getResource("/Lightspeed_Media/Menu.wav"));
			menuMusic =  AudioSystem.getClip();
			menuMusic.open(audio);
			
			// music for the intro text when starting a new game
			audio = AudioSystem.getAudioInputStream(this.getClass().getResource("/Lightspeed_Media/Intro.wav"));
			introMusic =  AudioSystem.getClip();
			introMusic.open(audio);
		}
		catch (Exception e) 
		{
			System.out.println("Could not find or play music files. Exited Program.");
			System.out.println(e.toString());
			System.exit(0);
		}
	}
	
	// method for playing music each normal level; randomly chooses a song
	public void playLevelMusic()
	{
		stopAllMusic();
		resetMusic();
		int index = (int)(Math.random()*3);
		levelMusic[index].setFramePosition(0);
		levelMusic[index].loop(Clip.LOOP_CONTINUOUSLY);
		levelMusic[index].start();
	}
	
	public void playMenuMusic()
	{
		stopAllMusic();
		resetMusic();
		menuMusic.loop(Clip.LOOP_CONTINUOUSLY);
		menuMusic.start();
	}
	
	public void playVictoryMusic()
	{
		stopAllMusic();
		resetMusic();
		victoryMusic.loop(Clip.LOOP_CONTINUOUSLY);
		victoryMusic.start();
	}
	
	public void playBossMusic()
	{
		stopAllMusic();
		resetMusic();
		bossMusic.loop(Clip.LOOP_CONTINUOUSLY);
		bossMusic.start();
	}
	
	public void playIntroMusic()
	{
		stopAllMusic();
		resetMusic();
		introMusic.loop(Clip.LOOP_CONTINUOUSLY);
		introMusic.start();
	}
	
	// method to stop playing all music
	public void stopAllMusic()
	{
		bossMusic.stop();
		victoryMusic.stop();
		menuMusic.stop();
		introMusic.stop();
		for (int i = 0; i < levelMusic.length; i++)
		{
			levelMusic[i].stop();
		}
	}
	
	// method to restart all music
	public void resetMusic()
	{
		bossMusic.setFramePosition(0);
		bossMusic.setFramePosition(0);
		victoryMusic.setFramePosition(0);
		menuMusic.setFramePosition(0);
		introMusic.setFramePosition(0);
		for (int i = 0; i < levelMusic.length; i++)
		{
			levelMusic[i].setFramePosition(0);
		}
	}
}