package com.tnf.fdoom.sound;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.HashMap;

import com.tnf.fdoom.GameContainer;
import com.tnf.fdoom.handlers.Logger;

public class Sound {
	/*public static final Sound playerHurt = new Sound("/playerhurt.wav");
	public static final Sound playerDeath = new Sound("/death.wav");
	public static final Sound monsterHurt = new Sound("/monsterhurt.wav");
	public static final Sound test = new Sound("/test.wav");
	public static final Sound pickup = new Sound("/pickup.wav");
	public static final Sound bossdeath = new Sound("/bossdeath.wav");
	public static final Sound craft = new Sound("/craft.wav");*/


	private static HashMap<String, Sound> sounds = new HashMap<String, Sound>();

	public static void load(String name, String filepath)
	{
		load(name, Sound.class.getResource(filepath));
	}

	public static void load(String name, URL url)
	{
		sounds.put(name, new Sound(url));
	}

	public static void play(String name)
	{
		try {
			sounds.get(name).play();
		} catch (Throwable e) {
			Logger.printLine("Could not play the selected sound clip. ", e);
		}
	}

	private AudioClip clip;

	private Sound(URL url) {
		try {
			clip = Applet.newAudioClip(url);
		} catch (Throwable e) {
			Logger.printLine("Could not load the sound clip but the name.", Logger.ERROR);
		}
	}

	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		} catch (Throwable e) {
			Logger.printLine("Could not play the selected sound clip.", Logger.ERROR);
		}
	}
}