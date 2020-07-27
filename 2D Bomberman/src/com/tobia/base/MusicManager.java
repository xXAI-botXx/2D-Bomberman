package com.tobia.base;

public class MusicManager implements Runnable{

	private Boolean running = false;
	
	//musics
	private MusicLoader checkSound;		//mit Swing, da es mit JavaFX irgendwie nicht funktioniert hat
	private MusicLoader blockSound;
	private MusicLoader continueSound;
	private MusicLoader gameoverSound;
	private MusicLoader battleSound;
	private MusicLoader chooseSound;
	private MusicLoader mainSound;
	private MusicLoader creditSound;
	private MusicLoader creditSound2;
	
	public MusicManager() {
		running = true;

		//init musics
		checkSound = new MusicLoader();
		checkSound.loadWav("correct");
		
		blockSound = new MusicLoader();
		blockSound.loadWav("block");
		
		continueSound = new MusicLoader();
		continueSound.loadWav("next");
		
		gameoverSound = new MusicLoader();
		gameoverSound.loadWav("gameover");
		
		
		battleSound = new MusicLoader();
		battleSound.loadWav("battle");
		
		mainSound = new MusicLoader();
		mainSound.loadWav("start");
		
		chooseSound = new MusicLoader();
		chooseSound.loadWav("characterChoose");
		
		creditSound = new MusicLoader();
		creditSound.loadWav("credit1");
		
		creditSound2 = new MusicLoader();
		creditSound2.loadWav("01 Boltzmann Brains");
	}
	
	
	public void exit() {
		running = false;
	}
	
	public void playMain() {
		stopAll();
		
		mainSound.playWhile();
	}
	
	public void playCredits() {
		stopAll();
		
		continueSound.play();
		continueSound.finish();
		creditSound.playWhile();
	}
	
	public void playCredits2() {
		stopAll();
	
		creditSound2.playWhile();
	}
	
	public void playChoose() {
		stopAll();
		
		continueSound.play();
		continueSound.finish();
		chooseSound.playWhile();
	}
	
	public void playBattle() {
		stopAll();
		
		continueSound.play();
		continueSound.finish();
		battleSound.playWhile();
	}
	
	public void playGameover() {
		stopAll();
		
		gameoverSound.playWhile();
	}
	
	public void playCheckSound() {
		checkSound.play();
		checkSound.finish();
	}
	
	public void playBlockSound() {
		blockSound.play();
		blockSound.finish();
	}
	
	public void stopAll() {
		checkSound.stop();
		blockSound.stop();
		continueSound.stop();
		gameoverSound.stop();
		battleSound.stop();
		mainSound.stop();
		chooseSound.stop();
		creditSound.stop();
		creditSound2.stop();
	}
	
	@Override
	public void run() {
		while(running) {
			//music will be called
		}
	}

}
