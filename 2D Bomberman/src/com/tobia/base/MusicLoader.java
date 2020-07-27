package com.tobia.base;

import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MusicLoader{
	
	private URL sound;
	private Clip clip;
	private Boolean playing = false;	
	
	public MusicLoader() {

	}

	public void loadWav(String songName) {	//relativ machen
		sound = getClass().getClassLoader().getResource("Sound/"+songName+".wav");
		
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {	
		try {
			playing = true;
			clip.setMicrosecondPosition(0);
			clip.start();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}	
	
	public void playWhile() {
		try {
			playing = true;
			clip.setMicrosecondPosition(0);
			clip.loop(clip.LOOP_CONTINUOUSLY);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		if(playing) {
			clip.stop();
			playing = false;
		}
	}
	
	public void finish() {
		playing = false;
	}

}
