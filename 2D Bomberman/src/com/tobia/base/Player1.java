package com.tobia.base;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Player1 extends Player{
	
	//Variablen
	private int startPosX = 100, startPosY = 300;
	private Vertex vertex;
	private int speed = 50;
	private int character;	//gibt character an
	private int map; //braucht nur p1
	private int score = 0, scoreMult = 1;
	private int characterWidth = 50, characterHeight = 50;
	private ArrayList<ArrayList<String>> mapList;
	private Boolean noCollision, rendering, updating;
	private Player2 p2;
	private Gameloop gameloop;
	private MusicLoader collisionSound;
	
	private Thread bombThread;
	private NormalBomb bomb;
	private RangeBomb rbomb;
	
	
	public Player1() {
		init();
	}

	@Override
	public void init() {
		vertex = new Vertex(startPosX, startPosY);
		updating = true;
		rendering = true;
		dir = direction.NULL;
		noCollision = true;
	
		collisionSound = new MusicLoader();
		collisionSound.loadWav("kollision_2");
		
		bomb = new NormalBomb(this, p2, mapList);
		rbomb = new RangeBomb(this, p2, mapList);
	}

	@Override
	public void restart() {
		characterChoose = false;
		mapChoose = false;
		noCollision = true;
		dir = direction.NULL;
		
		updating = true;
		rendering = true;
		score = 0;
		
		//abfrgae einbauen
		bomb.restart();
		rbomb.restart();
		
		rangeBomb = false;
		normalBomb = false;
	}
	
	
	@Override
	public Vertex getPosition() {
		return  vertex;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int s) {
		score += s*scoreMult;
	}
	
	public void setScoreMult(int s) {
		scoreMult = s;
	}
	
	public int getScoreMult() {
		return scoreMult;
	}
	
	public void setCharacter(int ch) {
		character = ch;
		characterChoose = true;
	}
	
	public void setNoCharacter() {
		characterChoose = false;
	}
	
	public int getCharacter() {
		return character;
	}
	
	public void setGameloop(Gameloop gl) {
		gameloop = gl;
	}
	
	public void setMap(int map) {
		this.map = map;
		mapChoose = true;
	}
	
	public void setNoMap() {
		mapChoose = false;
	}
	
	public void setPosition(int x, int y) {
		vertex.setX(x);
		vertex.setY(y);
	}
	
	public void setPlayer2(Player2 p2) {
		this.p2 = p2;
		bomb.setPlayer(this, p2);
		rbomb.setPlayer(this, p2);
	}
	
	public void close() {
		if(bombThread != null || !bombThread.isInterrupted()) {
			bomb.setOn(false);
			rbomb.setOn(false);
		}
	}
	
	public void setMapReader(ArrayList<ArrayList<String>> map) {
		mapList = map;
		bomb.setMapList(map);
		rbomb.setMapList(map);
		if(character == 1) {
			bombThread = new Thread(bomb);
			bombThread.setName("NormalBomb1-Thread");
		}else {
			bombThread = new Thread(rbomb);
			bombThread.setName("RangeBomb1-Thread");
		}
		bombThread.start();	
	}
	
	public void borderCollision() {
		
		if(vertex.getY() < 0) {			//oben
			vertex.setY(0);
		}else if(vertex.getY() > 550) {	//unten
			vertex.setY(550);
		}else if(vertex.getX() < 0) {	//links
			vertex.setX(0);
		}else if(vertex.getX() > 750) {	//rechts
			vertex.setX(750);
		}
	}
	
	public int getMap() {
		return map;
	}
	
	
	public void collisionDetection() {	//dir gibt die änderung im nächsten schritt an -> nur, wenn es nicht an der Grenze ist!
		
		switch (dir){
		case UP: 
			if(	vertex.getY()/50 > 0	&&	vertex.getY()/50 <= 11) {
				if(mapList.get( (int) ( vertex.getY()/50 ) -1).get((int) vertex.getX()/50).equals("1") || mapList.get( (int) ( vertex.getY()/50 ) -1).get((int) vertex.getX()/50).equals("2") || mapList.get( (int) ( vertex.getY()/50 ) -1).get((int) vertex.getX()/50).equals("3") || mapList.get( (int) ( vertex.getY()/50 ) -1).get((int) vertex.getX()/50).equals("4") || mapList.get( (int) ( vertex.getY()/50 ) -1).get((int) vertex.getX()/50).equals("5") || mapList.get( (int) ( vertex.getY()/50 ) -1).get((int) vertex.getX()/50).equals("6") || mapList.get( (int) ( vertex.getY()/50 ) -1).get((int) vertex.getX()/50).equals("7")) {
					noCollision = false;
				}
			}
			break;
		case DOWN: 
			if(vertex.getY()/50 >= 0	&&	vertex.getY()/50 < 11) {
				if(mapList.get( (int)  (vertex.getY()/50 ) +1).get((int) vertex.getX()/50).equals("1") || mapList.get( (int)  (vertex.getY()/50 ) +1).get((int) vertex.getX()/50).equals("2") || mapList.get( (int)  (vertex.getY()/50 ) +1).get((int) vertex.getX()/50).equals("3")|| mapList.get( (int)  (vertex.getY()/50 ) +1).get((int) vertex.getX()/50).equals("4") || mapList.get( (int)  (vertex.getY()/50 ) +1).get((int) vertex.getX()/50).equals("5") || mapList.get( (int)  (vertex.getY()/50 ) +1).get((int) vertex.getX()/50).equals("6") || mapList.get( (int)  (vertex.getY()/50 ) +1).get((int) vertex.getX()/50).equals("7")) {
					noCollision = false;
				}
			}
			break;
		case LEFT: 
			if(vertex.getX()/50 > 0	&&	vertex.getX()/50 <= 15) {
				if(mapList.get( (int) vertex.getY()/50).get((int) ( vertex.getX()/50) -1).equals("1") || mapList.get( (int) vertex.getY()/50).get((int) ( vertex.getX()/50) -1).equals("2") || mapList.get( (int) vertex.getY()/50).get((int) ( vertex.getX()/50) -1).equals("3") || mapList.get( (int) vertex.getY()/50).get((int) ( vertex.getX()/50) -1).equals("4") || mapList.get( (int) vertex.getY()/50).get((int) ( vertex.getX()/50) -1).equals("5") || mapList.get( (int) vertex.getY()/50).get((int) ( vertex.getX()/50) -1).equals("6") || mapList.get( (int) vertex.getY()/50).get((int) ( vertex.getX()/50) -1).equals("7")) {
					noCollision = false;
				}
			}
			break;
		case RIGHT: 
			if(vertex.getX()/50 >= 0	&&	vertex.getX()/50 < 15) {
				if(mapList.get( (int) vertex.getY()/50).get((int) ( vertex.getX()/50) +1).equals("1") || mapList.get( (int) vertex.getY()/50).get((int) ( vertex.getX()/50) +1).equals("2") || mapList.get( (int) vertex.getY()/50).get((int) ( vertex.getX()/50) +1).equals("3") || mapList.get( (int) vertex.getY()/50).get((int) ( vertex.getX()/50) +1).equals("4") || mapList.get( (int) vertex.getY()/50).get((int) ( vertex.getX()/50) +1).equals("5") || mapList.get( (int) vertex.getY()/50).get((int) ( vertex.getX()/50) +1).equals("6") || mapList.get( (int) vertex.getY()/50).get((int) ( vertex.getX()/50) +1).equals("7")) {
					noCollision = false;
				}	
			}
			break;
		case NULL:	//keine bewegung und somit keine Kollision
			break;
		}
	}
	
	public void normalExplosion() {		
		if(normalBomb && character == 1) {
			if(bomb.getReady()) {
				bomb.setRun(true);
			}
			normalBomb = false;
		}else if(rangeBomb && character == 2) {	
			if(rbomb.getReady()) {
				rbomb.setRun(true);
			}
			rangeBomb = false;
		}
		
	}
	
	
	public void end() {
		close();
		p2.close();

		updating = false;
		rendering = false;
		
		gameloop.setRendering(false);
	}
	
	public void renderBackground(GraphicsContext g, int i) {
		switch(i) {
		case 0:
			g.setFill(Color.WHITESMOKE);
			g.fillRect(0, 0, 800, 600);
			
			g.setStroke(Color.BLUE);
			g.setLineWidth(30);
			g.strokeRect(0, 0, 800, 600);
			break;
		case 1:
			g.setFill(Color.WHITESMOKE);
			g.fillRect(0, 0, 800, 600);
			
			g.setStroke(Color.CADETBLUE);
			g.setLineWidth(30);
			g.strokeRect(0, 0, 800, 600);
			break;
		case 2:
			g.setFill(Color.WHITESMOKE);
			g.fillRect(0, 0, 800, 600);
			
			g.setStroke(Color.BLACK);
			g.setLineWidth(30);
			g.strokeRect(0, 0, 800, 600);
			break;
		}
		
	}
	
	
	//EventHandling - oder eigene Klasse, sind je nach Typ anders
	@Override
	public void update() {
		if(updating) {
			borderCollision();
			collisionDetection();
			normalExplosion();
			
			if(noCollision) {
				switch (dir){
				case UP:  vertex.setY((float)vertex.getY()-speed);
					break;
				case DOWN: vertex.setY((float)vertex.getY()+speed);
					break;
				case LEFT: vertex.setX((float)vertex.getX()-speed);
					break;
				case RIGHT: vertex.setX((float)vertex.getX()+speed);
					break;
				case NULL:
					break;
				}
			}else {
				collisionSound.play();
			}
			dir = direction.NULL;	//reset
			noCollision = true;	//reset
		}else {
			
		}
	}
	
	@Override
	public void render(GraphicsContext g) {		//graphicscontext wäre richtig
		if(rendering) {
			//p1
			g.setFill(Color.BLUE);
			g.fillRect(vertex.getX(), vertex.getY(), characterWidth, characterHeight);
			
			g.setFill(Color.WHITE);
			g.setFont(new Font("", 30));
			g.fillText("P1", vertex.getX()+8, vertex.getY()+35);
			
			
			// score p1
			g.setFill(Color.BLACK);
			g.setFont(new Font("", 30));
			g.fillText("P1 Score: " + this.getScore(), 10, 30);
			if(character == 1) {
				bomb.getRender(g);
			}else {
				rbomb.getRender(g);
			}
		}else {
			if(score > p2.getScore()) {
				renderBackground(g, 0);
				g.setFill(Color.BLUE);
				g.setFont(new Font("", 50));
				g.fillText("P1 Wins with a Score of " + this.getScore(), 90, 300-25);
				
				g.setFont(new Font("", 15));
				g.fillText("Restart by pressing 'Space' or 'Esc'", 550, 560);
			}else if(p2.getScore() > score) {
				renderBackground(g, 1);
				g.setFill(Color.CADETBLUE);
				g.setFont(new Font("", 50));
				g.fillText("P2 Wins with a Score of " + p2.getScore(), 90, 300-25);
				
				g.setFont(new Font("", 15));
				g.fillText("Restart by pressing 'Space' or 'Esc'", 550, 560);
			}else {
				renderBackground(g, 2);
				g.setFill(Color.BLACK);
				g.setFont(new Font("", 50));
				g.fillText("We have two loosers :D", 150, 200);
				g.fillText("with a score of " + this.getScore(), 200, 300);
				
				g.setFont(new Font("", 15));
				g.fillText("Restart by pressing 'Space' or 'Esc'", 550, 560);
			}
		}
	}

}
