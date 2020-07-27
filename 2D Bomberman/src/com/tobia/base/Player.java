package com.tobia.base;



import javafx.scene.canvas.GraphicsContext;


//bei dieser weise sind alle charaktere in dieser Klasse
//je nach player typ, werden anderen aktionen ausgeführt (manche charaktere langsamer oder haben mehr rüstung,...)
//von dieser Klasse erben die anderen unterklassen (die verschiedenen Charaktere)
public abstract class Player implements GameObject{

	//Variablen
	protected Boolean characterChoose = false;
	protected Boolean mapChoose = false;
	protected Boolean normalBomb = false;
	protected Boolean rangeBomb = false;
	protected direction dir;
	
	public abstract void init();
	
	public abstract void restart();
	
	
	public enum direction{
		UP, DOWN, LEFT, RIGHT, NULL
	}
	
	public void up() {
		dir = direction.UP;
	}
	
	public void right() {
		dir = direction.RIGHT;
	}
	
	public void left() {
		dir = direction.LEFT;
	}
	
	public void down() {
		dir = direction.DOWN;
	}
	
	public void bomb() {
		normalBomb = true;
		rangeBomb = true;
	}
	
	//public abstract void setRun(Boolean b);
	
	public abstract Vertex getPosition();
	
	public abstract void setCharacter(int ch);
	
	public abstract int getCharacter();
	
	public Boolean getCharacterCheck() {
		return characterChoose;
	}
	
	public Boolean getMapCheck() {
		return mapChoose;
	}
	
	//EventHandling - oder eigene Klasse, sind je nach Typ anders
	public abstract void update();
	
	public abstract void render(GraphicsContext g);		
}
