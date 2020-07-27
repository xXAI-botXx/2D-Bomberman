package com.tobia.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
//import javafx.scene.paint.Color;

public class SpeedItem implements GameObject{

	private Player2 p2;
	private Player1 p1;
	
	private Vertex v;
	private Boolean exist = true, notTheEnd = true;	//oder objekt zerstören
	private ArrayList<ArrayList<String>> map;
	private Boolean spawnPositionisOk = false;
	private MusicLoader mc;
	
	private Image img;
	
	public SpeedItem(Player1 p1, Player2 p2, ArrayList<ArrayList<String>> map) {
		this.p1 = p1;
		this.p2 = p2;
		this.map = map;
		
		init();
	}
	
	public void init() {
		int x = (int) (Math.random() * 15);	//16 Zahlen
		int y = (int) (Math.random() * 11);	//12 zahlen 0...11
		
		while(!spawnPositionisOk) {	//soll nicht in einem unzerstörbaren Block spawnen
			if(map.get(y).get(x).equals("2")) {
				x = (int) (Math.random() * 15);
				y = (int) (Math.random() * 11);
			}else {
				spawnPositionisOk = true;
			}
		}
		v = new Vertex(x*50, y*50);
			
		mc = new MusicLoader();
		mc.loadWav("speeditem");
		
		try {
			img = new Image(getClass().getClassLoader().getResourceAsStream("Images/speeditem.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void end() {
		notTheEnd = false;
	}
	
	public void restart() {
		notTheEnd = true;
	}
	
	public void checkCollision() {
		if(p1.getPosition().getX() == v.getX() && p1.getPosition().getY() == v.getY()) {	//Spieler 1 auf dem Item steht
			p1.setScoreMult(2);
			mc.play();
			exist = false;
		}else if(p2.getPosition().getX() == v.getX() && p2.getPosition().getY() == v.getY()) {	//Spieler 2 auf dem Item steht
			p2.setScoreMult(2);
			mc.play();
			exist = false;
		}
	}

	@Override
	public void render(GraphicsContext g) {
		if(exist && notTheEnd) {
//			g.setFill(Color.BLUEVIOLET);
//			g.fillOval(v.getX(), v.getY(), 30, 30);
			g.drawImage(img, v.getX(), v.getY());
		}
	}

	@Override
	public void update() {
		if(exist && notTheEnd) {
			checkCollision();
		}
	}
	
	
}
