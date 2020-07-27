package com.tobia.base;



import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CreditLoop extends AnimationTimer{

	private GraphicsContext g;
	private int changer = 10;
	private int changeschanger = 1;
	private int starChanger;	
	private Boolean change = true;
	private Control control;
	
	private ArrayList<DarkCloud> clouds;
	private DarkCloud darkCloud1;
	private DarkCloud darkCloud2;
	private DarkCloud darkCloud3;
	private Boolean eeMode = false;
	private Image i1;
	
	
	public CreditLoop(Canvas c, Control control) {
		this.control = control;
		g = c.getGraphicsContext2D();
		
		darkCloud1 = new DarkCloud(900, 150, 2, 1, 13);	//0.5
		darkCloud2 = new DarkCloud(1300, 300, 1, 1, 30);		//0.5
		darkCloud3 = new DarkCloud(-250, 400, 2, -1, 30);	//-0.5
		darkCloud3.setRight();
		
		clouds = new ArrayList<DarkCloud>();
		clouds.add(darkCloud1);
		clouds.add(darkCloud2);
		clouds.add(darkCloud3);
		
		try {
			i1 = new Image(new FileInputStream("src/Images/moon.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void handle(long arg0) {
		update();
		render();
	}
	
	public void render() {
		g.clearRect(0, 0, 800, 600);
		
		//Hilfslinie -> kann geklammert werden!
//		g.setFill(Color.RED);
//		g.fillRect(400, 0, 1, 600);
		
		//automatisch generierte Sterne
		if(eeMode) {
			g.setFill(Color.BLACK);
			g.fillRect(0, 0, 800, 600);
			
			g.drawImage(i1, 100, 100);
			
			for(DarkCloud s: clouds) {
				s.render(g);
			}
			
//			g.setFill(Color.RED);
//			g.setStroke(Color.GHOSTWHITE);
//			g.fillOval(100, 100, 150, 150);
//			g.strokeOval(100, 100, 150, 150);
			
			
		}else {	//normaler mode
		
			//sterne
			g.setFill(Color.BLUE);
			g.fillRect(115, 315 + starChanger, 1, 50);
			
			g.setFill(Color.BLUE);
			g.fillRect(460, 430 + starChanger, 1, 40);
			
			g.setFill(Color.BLUE);
			g.fillRect(700, 300 + starChanger, 1, 130);
			
			g.setFill(Color.BLUE);
			g.fillRect(400, 10 + starChanger, 1, 90);
			
			g.setFill(Color.BLUE);
			g.fillRect(400, 10 + starChanger, 1, 90);
			
			g.setFill(Color.BLUE);
			g.fillRect(200, -150 + starChanger, 1, 120);
			
			g.setFill(Color.BLUE);
			g.fillRect(10, -100 + starChanger, 1, 10);
			
			g.setFill(Color.BLUE);
			g.fillRect(550, -300 + starChanger, 1, 90);
			
			g.setFill(Color.BLUE);
			g.fillRect(130, -360 + starChanger, 1, 90);
			
			g.setFill(Color.BLUE);
			g.fillRect(320, -500 + starChanger, 1, 45);
			
			g.setFill(Color.BLUE);
			g.fillRect(730, -550 + starChanger, 1, 200);
			
			g.setFill(Color.BLUE);
			g.fillRect(210, -645 + starChanger, 1, 30);
			
			g.setFill(Color.BLUE);
			g.fillRect(600, -850 + starChanger, 1, 89);
			
			g.setFill(Color.BLUE);
			g.fillRect(400, -750 + starChanger, 1, 45);
			
			g.setFill(Color.BLUE);
			g.fillRect(260, -1300 + starChanger, 1, 300);
			
			//namen
			g.setFill(Color.BLACK);
			g.setFont(new Font("", 50));
			g.fillText("Director", 310, 600 - changer);
			
			g.setFill(Color.DARKGRAY);
			g.setFont(new Font("", 40));
			g.fillText("Tobia Ippolito", 280, 700 - changer);
			
			//next
			g.setFill(Color.BLACK);
			g.setFont(new Font("", 50));
			g.fillText("Executive Producer", 200, 900 - changer);
			
			g.setFill(Color.DARKGRAY);
			g.setFont(new Font("", 40));
			g.fillText("Tobia Ippolito", 280, 1000 - changer);
			
			//next
			g.setFill(Color.BLACK);
			g.setFont(new Font("", 50));
			g.fillText("Production Coordinator", 150, 1200 - changer);
			
			g.setFill(Color.DARKGRAY);
			g.setFont(new Font("", 40));
			g.fillText("Tobia Ippolito", 280, 1300 - changer);
			
			//next
			g.setFill(Color.BLACK);
			g.setFont(new Font("", 50));
			g.fillText("Lead Engineer", 250, 1500 - changer);
			
			g.setFill(Color.DARKGRAY);
			g.setFont(new Font("", 40));
			g.fillText("Tobia Ippolito", 280, 1600 - changer);
			
			//next
			g.setFill(Color.BLACK);
			g.setFont(new Font("", 50));
			g.fillText("Art Director", 275, 1800 - changer);
			
			g.setFill(Color.DARKGRAY);
			g.setFont(new Font("", 40));
			g.fillText("Tobia Ippolito", 280, 1900 - changer);
			
			//next
			g.setFill(Color.BLACK);
			g.setFont(new Font("", 50));
			g.fillText("Special Thanks to", 210, 2100 - changer);
			
			g.setFill(Color.DARKGRAY);
			g.setFont(new Font("", 40));
			g.fillText("Tobia Ippolito", 280, 2200 - changer);
		}
	}
	
	public void update() {
		if(change) {
			
			if(eeMode) {	
				for(DarkCloud s: clouds) {
					s.update();
				}
			}else {
				changer = changer + changeschanger;
				setStarChanger();
			}
		}
		checkEsterEgg();
	}
	
	public void restart() {
		eeMode = false;
		changer = 10;
		changeschanger = 1;
		change = true;
		control.eeStop();
	}
	
	public void faster() {
		if(change) {
			if(eeMode) {
				for(DarkCloud s: clouds) s.faster();
			}else {
				changeschanger++;
			}
		}
	}
	
	public void slower() {
		if(change) {
			if(eeMode) {
				for(DarkCloud s: clouds) s.slower();
			}else {
				changeschanger--;
			}
		}
	}
	
	public void play() {
		change = true;
	}
	
	public void pause() {
		change = false;
	}
	
	public void setStarChanger() {
		starChanger = (int) (changer * 0.6);
	}
	
	public void checkEsterEgg() {
		if(changer >= 3200) {
			if(eeMode == false) {	//nur bei modus wechsel ausführen
				control.eeStart();
			}
			eeMode = true;
		}else {
			if(eeMode == true) {	//nur bei modus wechsel ausführen
				control.eeStop();
			}
			eeMode = false;
		}
	}
}
