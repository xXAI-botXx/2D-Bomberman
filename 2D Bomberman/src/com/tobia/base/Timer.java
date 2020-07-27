package com.tobia.base;

public class Timer{


	private long begin = 0;
	private long now;
	private Boolean imDone = false;
	private Boolean isWorking = false;
	
	public Timer() {
		
	}
	
	public void waitPls(int howLong){
		imDone = false;
		
		begin = System.currentTimeMillis();
		now = System.currentTimeMillis();
		while(now - begin < howLong) {
			now = System.currentTimeMillis();
			//System.out.println("im waiting");
		}
	
		imDone = true;
	}
	
	public void waitWhileAnimation() {
		imDone = false;
		isWorking = true;
		
		begin = System.currentTimeMillis();
		now = System.currentTimeMillis();
		while(now - begin < 1000) {
			now = System.currentTimeMillis();
		}
		imDone = true;
		isWorking = false;
	}
	
	public Boolean imnotWorking() {	//false -> arbeitet noch
		return imDone;
	}
	
	public Boolean isWorking() {
		return isWorking;
	}
}
