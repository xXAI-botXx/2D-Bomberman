package com.tobia.base;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Player2 extends Player{

	//Variablen
		private int startPosX = 700, startPosY = 300;
		private Vertex vertex;
		private int speed = 50;
		private int character;
		private int score = 0, scoreMult = 1;
		private int characterWidth = 50, characterHeight = 50;
		private ArrayList<ArrayList<String>> mapList;
		private Boolean noCollision, updating, rendering;
		private Player1 p1;
		private MusicLoader collisionSound;
		
		private Thread bombThread;
		private NormalBomb2 bomb;
		private RangeBomb2 rbomb;
		
		public Player2() {
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
			
			bomb = new NormalBomb2(p1, this, mapList);
			rbomb = new RangeBomb2(p1, this, mapList);
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
		
		public void setPosition(int x, int y) {
			vertex.setX(x);
			vertex.setY(y);
		}
		
		public void setPlayer1(Player1 p1) {
			this.p1 = p1;
			bomb.setPlayer(p1, this);
			rbomb.setPlayer(p1, this);
		}
		
		public void setMapReader(ArrayList<ArrayList<String>> map) {
			mapList = map;
			bomb.setMapList(map);
			rbomb.setMapList(map);
			if(character == 1) {
				bombThread = new Thread(bomb);
				bombThread.setName("NormalBomb2-Thread");
			}else {
				bombThread = new Thread(rbomb);
				bombThread.setName("RangeBomb2-Thread");
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
			case NULL:	//keine bewegung, somit keine kollision möglich
				break;
			}
		}
		
		public void close() {
			if(bombThread != null || !bombThread.isInterrupted()) {
				bomb.setOn(false);
				rbomb.setOn(false);	
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
			updating = false;
			rendering = false;
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
					case UP: vertex.setY((float)vertex.getY()-speed);
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
				noCollision = true;
			}
		}
		
		@Override
		public void render(GraphicsContext g) {
			if(rendering) {
				//p2
				g.setFill(Color.CADETBLUE);
				g.fillRect(vertex.getX(), vertex.getY(), characterWidth, characterHeight);
				
				g.setFill(Color.WHITE);
				g.setFont(new Font("", 30));
				g.fillText("P2", vertex.getX()+8, vertex.getY()+35);
				
				// score p2
				g.setFill(Color.BLACK);
				g.setFont(new Font("", 30));
				g.fillText("P2 Score: " + this.getScore(), 600, 30);
				
				if(character == 1) {
					bomb.getRender(g);
				}else {
					rbomb.getRender(g);
				}
			}
		}
}
