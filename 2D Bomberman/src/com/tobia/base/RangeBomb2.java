package com.tobia.base;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class RangeBomb2 implements Runnable/*, Condition*/{

	private Player1 p1;
	private Player2 p2;
	private int x, y, i = 0, scoreFeedback;
	private Boolean imReady = true;
	private Timer timer;
	private ArrayList<ArrayList<String>> mapList;
	private Boolean exOb1 = false, exOb2 = false, exUn1 = false, exUn2 = false, exRe1 = false, exRe2 = false, exLi1 = false, exLi2 = false;
	private int exOb1Score = 0, exOb2Score = 0, exUn1Score = 0, exUn2Score = 0, exRe1Score = 0, exRe2Score = 0, exLi1Score = 0, exLi2Score = 0;
	private int slideY;
	private Boolean run = false;
	private Boolean on = true;
	private Boolean explosionTime = false;
	
	private MusicLoader bombSound;
	
	public RangeBomb2(Player1 p1, Player2 p2, ArrayList<ArrayList<String>> mapList) {
		this.p1 = p1;
		this.p2 = p2;
		this.mapList = mapList;
		timer = new Timer();
		bombSound = new MusicLoader();
		bombSound.loadWav("explosion");
		}
	
	
	public void activate() {
		
		exOb1 = false;
		exOb2 = false;
		exUn1 = false;
		exUn2 = false;
		exRe1 = false;
		exRe2 = false;
		exLi1 = false;
		exLi2 = false;
		
		exOb1Score = 0;
		exOb2Score = 0;
		exUn1Score = 0;
		exUn2Score = 0;
		exLi1Score = 0;
		exLi2Score = 0;
		exRe1Score = 0;
		exRe2Score = 0;

		slideY = 0;
		
		imReady = false;
		
		x = (int) p2.getPosition().getX();	//position der Bombe wird bestimmt
		y = (int) p2.getPosition().getY();
		i = 0; 	//für rendern der explosion
		scoreFeedback = 0; //für score anzeige (kurz) -> Feedback
		
		überprüfung();
		nowWait();
		dotonExplosion();
		playerCollision();
		
		imReady = true;
		run = false;
	}
	
	public void nowWait() {
		timer.waitPls(2000);
	}
	
	public void dotonExplosion() {
		explosionTime = true;
		
		if(y-50 >= 0) {	//sonst array out of bounds!
			//1 obendrüber
			if(mapList.get((y/50)-1).get( x/50).equals("1")) {
					mapList.get((y/50)-1).set(x/50, "0");
					p2.setScore(10);
					exOb1Score = 10;
				}else if(mapList.get((y/50)-1).get( x/50).equals("3")) {
					mapList.get((y/50)-1).set(x/50, "0");
					p2.setScore(100); 	//gold bonus
					exOb1Score = 100;
				}else if(mapList.get((y/50)-1).get( x/50).equals("4")) {
					mapList.get((y/50)-1).set(x/50, "3");
				}else if(mapList.get((y/50)-1).get( x/50).equals("5")) {
					mapList.get((y/50)-1).set(x/50, "0");
					p2.setScore(200);
					exOb1Score = 200;
				}else if(mapList.get((y/50)-1).get( x/50).equals("6")) {
					mapList.get((y/50)-1).set(x/50, "5");
				}else if(mapList.get((y/50)-1).get( x/50).equals("7")) {
					mapList.get((y/50)-1).set(x/50, "6");
				}
			}
		
		if(y-100 >= 0) {	//sonst array out of bounds!
			//2 obendrüber
			if(mapList.get((y/50)-2).get(x/50).equals("1") && !mapList.get((y/50)-1).get(x/50).equals("2") && !mapList.get((y/50)-1).get(x/50).equals("3") && !mapList.get((y/50)-1).get(x/50).equals("4") && !mapList.get((y/50)-1).get(x/50).equals("5") && !mapList.get((y/50)-1).get(x/50).equals("6") && !mapList.get((y/50)-1).get(x/50).equals("7")) {
				mapList.get((y/50)-2).set(x/50, "0");
				p2.setScore(10);
				exOb2Score = 10;
			}else if(mapList.get((y/50)-2).get(x/50).equals("3") && !mapList.get((y/50)-1).get(x/50).equals("2") && !mapList.get((y/50)-1).get(x/50).equals("3") && !mapList.get((y/50)-1).get(x/50).equals("4") && !mapList.get((y/50)-1).get(x/50).equals("5") && !mapList.get((y/50)-1).get(x/50).equals("6") && !mapList.get((y/50)-1).get(x/50).equals("7")) {
				mapList.get((y/50)-2).set(x/50, "0");
				p2.setScore(100); 	//gold bonus
				exOb2Score = 100;
			}else if(mapList.get((y/50)-2).get(x/50).equals("4") && !mapList.get((y/50)-1).get(x/50).equals("2") && !mapList.get((y/50)-1).get(x/50).equals("3") && !mapList.get((y/50)-1).get(x/50).equals("4") && !mapList.get((y/50)-1).get(x/50).equals("5") && !mapList.get((y/50)-1).get(x/50).equals("6") && !mapList.get((y/50)-1).get(x/50).equals("7")) {
				mapList.get((y/50)-2).set(x/50, "3");
			}else if(mapList.get((y/50)-2).get(x/50).equals("5") && !mapList.get((y/50)-1).get(x/50).equals("2") && !mapList.get((y/50)-1).get(x/50).equals("3") && !mapList.get((y/50)-1).get(x/50).equals("4") && !mapList.get((y/50)-1).get(x/50).equals("5") && !mapList.get((y/50)-1).get(x/50).equals("6") && !mapList.get((y/50)-1).get(x/50).equals("7")) {
				mapList.get((y/50)-2).set(x/50, "0");
				p2.setScore(200);
				exOb2Score = 200;
			}else if(mapList.get((y/50)-2).get(x/50).equals("6") && !mapList.get((y/50)-1).get(x/50).equals("2") && !mapList.get((y/50)-1).get(x/50).equals("3") && !mapList.get((y/50)-1).get(x/50).equals("4") && !mapList.get((y/50)-1).get(x/50).equals("5") && !mapList.get((y/50)-1).get(x/50).equals("6") && !mapList.get((y/50)-1).get(x/50).equals("7")) {
				mapList.get((y/50)-2).set(x/50, "5");
			}else if(mapList.get((y/50)-2).get(x/50).equals("7") && !mapList.get((y/50)-1).get(x/50).equals("2") && !mapList.get((y/50)-1).get(x/50).equals("3") && !mapList.get((y/50)-1).get(x/50).equals("4") && !mapList.get((y/50)-1).get(x/50).equals("5") && !mapList.get((y/50)-1).get(x/50).equals("6") && !mapList.get((y/50)-1).get(x/50).equals("7")) {
				mapList.get((y/50)-2).set(x/50, "6");
			}
		}
		
		if(y+50 < 600) {	//sonst array out of bounds!
			//1 untendrunter
			if(mapList.get((y/50)+1).get(x/50).equals("1")) {
				mapList.get((y/50)+1).set(x/50, "0");
				p2.setScore(10);
				exUn1Score = 10;
			}else if(mapList.get((y/50)+1).get(x/50).equals("3")) {
				mapList.get((y/50)+1).set(x/50, "0");
				p2.setScore(100); 	//gold bonus
				exUn1Score = 100;
			}else if(mapList.get((y/50)+1).get(x/50).equals("4")) {
				mapList.get((y/50)+1).set(x/50, "3");
			}else if(mapList.get((y/50)+1).get(x/50).equals("5")) {
				mapList.get((y/50)+1).set(x/50, "0");
				p2.setScore(200);
				exUn1Score = 200;
			}else if(mapList.get((y/50)+1).get(x/50).equals("6")) {
				mapList.get((y/50)+1).set(x/50, "5");
			}else if(mapList.get((y/50)+1).get(x/50).equals("7")) {
				mapList.get((y/50)+1).set(x/50, "6");
			}
		}
		
		if(y+100 < 600) {
			//2 untendrunter
			if(mapList.get((y/50)+2).get(x/50).equals("1") && !mapList.get((y/50)+1).get(x/50).equals("2") && !mapList.get((y/50)+1).get(x/50).equals("3") && !mapList.get((y/50)+1).get(x/50).equals("4") && !mapList.get((y/50)+1).get(x/50).equals("5") && !mapList.get((y/50)+1).get(x/50).equals("6") && !mapList.get((y/50)+1).get(x/50).equals("7")) {
				mapList.get((y/50)+2).set(x/50, "0");
				p2.setScore(10);
				exUn2Score = 10;
			}else if(mapList.get((y/50)+2).get(x/50).equals("3") && !mapList.get((y/50)+1).get(x/50).equals("2") && !mapList.get((y/50)+1).get(x/50).equals("3") && !mapList.get((y/50)+1).get(x/50).equals("4") && !mapList.get((y/50)+1).get(x/50).equals("5") && !mapList.get((y/50)+1).get(x/50).equals("6") && !mapList.get((y/50)+1).get(x/50).equals("7")) {
				mapList.get((y/50)+2).set(x/50, "0");
				p2.setScore(100); 	//gold bonus
				exUn2Score = 100;
			}else if(mapList.get((y/50)+2).get(x/50).equals("4") && !mapList.get((y/50)+1).get(x/50).equals("2") && !mapList.get((y/50)+1).get(x/50).equals("3") && !mapList.get((y/50)+1).get(x/50).equals("4") && !mapList.get((y/50)+1).get(x/50).equals("5") && !mapList.get((y/50)+1).get(x/50).equals("6") && !mapList.get((y/50)+1).get(x/50).equals("7")) {
				mapList.get((y/50)+2).set(x/50, "3");
			}else if(mapList.get((y/50)+2).get(x/50).equals("5") && !mapList.get((y/50)+1).get(x/50).equals("2") && !mapList.get((y/50)+1).get(x/50).equals("3") && !mapList.get((y/50)+1).get(x/50).equals("4") && !mapList.get((y/50)+1).get(x/50).equals("5") && !mapList.get((y/50)+1).get(x/50).equals("6") && !mapList.get((y/50)+1).get(x/50).equals("7")) {
				mapList.get((y/50)+2).set(x/50, "0");
				p2.setScore(200);
				exUn2Score = 200;
			}else if(mapList.get((y/50)+2).get(x/50).equals("6") && !mapList.get((y/50)+1).get(x/50).equals("2") && !mapList.get((y/50)+1).get(x/50).equals("3") && !mapList.get((y/50)+1).get(x/50).equals("4") && !mapList.get((y/50)+1).get(x/50).equals("5") && !mapList.get((y/50)+1).get(x/50).equals("6") && !mapList.get((y/50)+1).get(x/50).equals("7")) {
				mapList.get((y/50)+2).set(x/50, "5");
			}else if(mapList.get((y/50)+2).get(x/50).equals("7") && !mapList.get((y/50)+1).get(x/50).equals("2") && !mapList.get((y/50)+1).get(x/50).equals("3") && !mapList.get((y/50)+1).get(x/50).equals("4") && !mapList.get((y/50)+1).get(x/50).equals("5") && !mapList.get((y/50)+1).get(x/50).equals("6") && !mapList.get((y/50)+1).get(x/50).equals("7")) {
				mapList.get((y/50)+2).set(x/50, "6");
			}
		}
		
		if(x+50 < 800) {
		//1 rechts
			if(mapList.get(y/50).get((x/50)+1).equals("1")) {
				mapList.get(y/50).set((x/50)+1, "0");
				p2.setScore(10);
				exRe1Score = 10;
			}else if(mapList.get(y/50).get((x/50)+1).equals("3")) {
				mapList.get(y/50).set((x/50)+1, "0");
				p2.setScore(100); 	//gold bonus
				exRe1Score = 100;
			}else if(mapList.get(y/50).get((x/50)+1).equals("4")) {
				mapList.get(y/50).set((x/50)+1, "3");
			}else if(mapList.get(y/50).get((x/50)+1).equals("5")) {
				mapList.get(y/50).set((x/50)+1, "0");
				p2.setScore(200);
				exRe1Score = 200;
			}else if(mapList.get(y/50).get((x/50)+1).equals("6")) {
				mapList.get(y/50).set((x/50)+1, "5");
			}else if(mapList.get(y/50).get((x/50)+1).equals("7")) {
				mapList.get(y/50).set((x/50)+1, "6");
			}
		}
		
		if(x+100 < 800) {
			//2 rechts
			if(mapList.get(y/50).get((x/50)+2).equals("1") && !mapList.get(y/50).get((x/50)+1).equals("2") && !mapList.get(y/50).get((x/50)+1).equals("3") && !mapList.get(y/50).get((x/50)+1).equals("4") && !mapList.get(y/50).get((x/50)+1).equals("5") && !mapList.get(y/50).get((x/50)+1).equals("6") && !mapList.get(y/50).get((x/50)+1).equals("7")) {
				mapList.get(y/50).set((x/50)+2, "0");
				p2.setScore(10);
				exRe2Score = 10;
			}else if(mapList.get(y/50).get((x/50)+2).equals("3") && !mapList.get(y/50).get((x/50)+1).equals("2") && !mapList.get(y/50).get((x/50)+1).equals("3") && !mapList.get(y/50).get((x/50)+1).equals("4") && !mapList.get(y/50).get((x/50)+1).equals("5") && !mapList.get(y/50).get((x/50)+1).equals("6") && !mapList.get(y/50).get((x/50)+1).equals("7")) {
				mapList.get(y/50).set((x/50)+2, "0");
				p2.setScore(100); 	//gold bonus
				exRe2Score = 100;
			}else if(mapList.get(y/50).get((x/50)+2).equals("4") && !mapList.get(y/50).get((x/50)+1).equals("2") && !mapList.get(y/50).get((x/50)+1).equals("3") && !mapList.get(y/50).get((x/50)+1).equals("4") && !mapList.get(y/50).get((x/50)+1).equals("5") && !mapList.get(y/50).get((x/50)+1).equals("6") && !mapList.get(y/50).get((x/50)+1).equals("7")) {
				mapList.get(y/50).set((x/50)+2, "3");
			}else if(mapList.get(y/50).get((x/50)+2).equals("5") && !mapList.get(y/50).get((x/50)+1).equals("2") && !mapList.get(y/50).get((x/50)+1).equals("3") && !mapList.get(y/50).get((x/50)+1).equals("4") && !mapList.get(y/50).get((x/50)+1).equals("5") && !mapList.get(y/50).get((x/50)+1).equals("6") && !mapList.get(y/50).get((x/50)+1).equals("7")) {
				mapList.get(y/50).set((x/50)+2, "0");
				p2.setScore(200);
				exRe2Score = 200;
			}else if(mapList.get(y/50).get((x/50)+2).equals("6") && !mapList.get(y/50).get((x/50)+1).equals("2") && !mapList.get(y/50).get((x/50)+1).equals("3") && !mapList.get(y/50).get((x/50)+1).equals("4") && !mapList.get(y/50).get((x/50)+1).equals("5") && !mapList.get(y/50).get((x/50)+1).equals("6") && !mapList.get(y/50).get((x/50)+1).equals("7")) {
				mapList.get(y/50).set((x/50)+2, "5");
			}else if(mapList.get(y/50).get((x/50)+2).equals("7") && !mapList.get(y/50).get((x/50)+1).equals("2") && !mapList.get(y/50).get((x/50)+1).equals("3") && !mapList.get(y/50).get((x/50)+1).equals("4") && !mapList.get(y/50).get((x/50)+1).equals("5") && !mapList.get(y/50).get((x/50)+1).equals("6") && !mapList.get(y/50).get((x/50)+1).equals("7")) {
				mapList.get(y/50).set((x/50)+2, "6");
			}
		}
		
		if(x-50 >= 0) {
			//1 links
			if(mapList.get(y/50).get((x/50)-1).equals("1")) {
				mapList.get(y/50).set((x/50)-1, "0");
				p2.setScore(10);
				exLi1Score = 10;
			}else if(mapList.get(y/50).get((x/50)-1).equals("3")) {
				mapList.get(y/50).set((x/50)-1, "0");
				p2.setScore(100); 	//gold bonus
				exLi1Score = 100;
			}else if(mapList.get(y/50).get((x/50)-1).equals("4")) {
				mapList.get(y/50).set((x/50)-1, "3");
			}else if(mapList.get(y/50).get((x/50)-1).equals("5")) {
				mapList.get(y/50).set((x/50)-1, "0");
				p2.setScore(200);
				exLi1Score = 200;
			}else if(mapList.get(y/50).get((x/50)-1).equals("6")) {
				mapList.get(y/50).set((x/50)-1, "5");
			}else if(mapList.get(y/50).get((x/50)-1).equals("7")) {
				mapList.get(y/50).set((x/50)-1, "6");
			}
		}
		
		if(x-100 >= 0) {
			//2 links
			if(mapList.get(y/50).get((x/50)-2).equals("1") && !mapList.get(y/50).get((x/50)-1).equals("2") && !mapList.get(y/50).get((x/50)-1).equals("3") && !mapList.get(y/50).get((x/50)-1).equals("4") && !mapList.get(y/50).get((x/50)-1).equals("5") && !mapList.get(y/50).get((x/50)-1).equals("6") && !mapList.get(y/50).get((x/50)-1).equals("7")) {
				mapList.get(y/50).set((x/50)-2, "0");
				p2.setScore(10);
				exLi2Score = 10;
			}else if(mapList.get(y/50).get((x/50)-2).equals("3") && !mapList.get(y/50).get((x/50)-1).equals("2") && !mapList.get(y/50).get((x/50)-1).equals("3") && !mapList.get(y/50).get((x/50)-1).equals("4") && !mapList.get(y/50).get((x/50)-1).equals("5") && !mapList.get(y/50).get((x/50)-1).equals("6") && !mapList.get(y/50).get((x/50)-1).equals("7")) {
				mapList.get(y/50).set((x/50)-2, "0");
				p2.setScore(100); 	//gold bonus
				exLi2Score = 100;
			}else if(mapList.get(y/50).get((x/50)-2).equals("4") && !mapList.get(y/50).get((x/50)-1).equals("2") && !mapList.get(y/50).get((x/50)-1).equals("3") && !mapList.get(y/50).get((x/50)-1).equals("4") && !mapList.get(y/50).get((x/50)-1).equals("5") && !mapList.get(y/50).get((x/50)-1).equals("6") && !mapList.get(y/50).get((x/50)-1).equals("7")) {
				mapList.get(y/50).set((x/50)-2, "3");
			}else if(mapList.get(y/50).get((x/50)-2).equals("5") && !mapList.get(y/50).get((x/50)-1).equals("2") && !mapList.get(y/50).get((x/50)-1).equals("3") && !mapList.get(y/50).get((x/50)-1).equals("4") && !mapList.get(y/50).get((x/50)-1).equals("5") && !mapList.get(y/50).get((x/50)-1).equals("6") && !mapList.get(y/50).get((x/50)-1).equals("7")) {
				mapList.get(y/50).set((x/50)-2, "0");
				p2.setScore(200);
				exLi2Score = 200;
			}else if(mapList.get(y/50).get((x/50)-2).equals("6") && !mapList.get(y/50).get((x/50)-1).equals("2") && !mapList.get(y/50).get((x/50)-1).equals("3") && !mapList.get(y/50).get((x/50)-1).equals("4") && !mapList.get(y/50).get((x/50)-1).equals("5") && !mapList.get(y/50).get((x/50)-1).equals("6") && !mapList.get(y/50).get((x/50)-1).equals("7")) {
				mapList.get(y/50).set((x/50)-2, "5");
			}else if(mapList.get(y/50).get((x/50)-2).equals("7") && !mapList.get(y/50).get((x/50)-1).equals("2") && !mapList.get(y/50).get((x/50)-1).equals("3") && !mapList.get(y/50).get((x/50)-1).equals("4") && !mapList.get(y/50).get((x/50)-1).equals("5") && !mapList.get(y/50).get((x/50)-1).equals("6") && !mapList.get(y/50).get((x/50)-1).equals("7")) {
				mapList.get(y/50).set((x/50)-2, "6");
			}
		}
	}
	
	public Boolean getReady() {
		return imReady;
	}
	
	public void setMapList(ArrayList<ArrayList<String>> map) {
		mapList = map;
	}
	
	public Boolean finish() {
		return imReady;
	}
	
	public void setRun(Boolean b) {
		run = b;
	}
	
	public void setOn(Boolean b) {
		on = b;
	}
	
	public void setPlayer(Player1 p1, Player2 p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public void getRender(GraphicsContext g) {
		if(imReady == false) {	//solange er wartet -> mehr oder weniger
			g.setFill(Color.BLACK);
			g.fillOval(x, y, 50, 50);
			
			renderPreExplosion(g);
		}else {	
			if(explosionTime == true) {
				if(i < 25) {
					renderExplosion(g);	
					i++;
				}else {
					explosionTime = false;	//jz wird resettet
				}
			}
		}
		
		
		if(scoreFeedback < 40 && imReady && i >= 25) {//nach der explosion sollen punkte angezeigt werden		
			scoreFeedback++;
				renderPoints(g);
				slideY++;
		}
	}
	
	public void renderExplosion(GraphicsContext g) {
		g.setFill(Color.RED);
		if(exOb1) g.fillRect(x, y-50, 50, 50);
		if(exOb2) g.fillRect(x, y-100, 50, 50);
		if(exUn1) g.fillRect(x, y+50, 50, 50);
		if(exUn2) g.fillRect(x, y+100, 50, 50);
		if(exRe1) g.fillRect(x+50, y, 50, 50);
		if(exRe2) g.fillRect(x+100, y, 50, 50);
		if(exLi1) g.fillRect(x-50, y, 50, 50);
		if(exLi2) g.fillRect(x-100, y, 50, 50);
		g.fillRect(x, y, 50, 50);
	}
	
	public void renderPreExplosion(GraphicsContext g) {
		g.setStroke(Color.RED);
		g.setLineWidth(4);
		
		if(exOb1) g.strokeRect(x, y-50, 50, 50);
		if(exOb2) g.strokeRect(x, y-100, 50, 50);
		if(exUn1) g.strokeRect(x, y+50, 50, 50);
		if(exUn2) g.strokeRect(x, y+100, 50, 50);
		if(exRe1) g.strokeRect(x+50, y, 50, 50);
		if(exRe2) g.strokeRect(x+100, y, 50, 50);
		if(exLi1) g.strokeRect(x-50, y, 50, 50);
		if(exLi2) g.strokeRect(x-100, y, 50, 50);
		g.strokeRect(x, y, 50, 50);
	}
	
	public void renderPoints(GraphicsContext g) {
		
		if(exOb1) {
			if(exOb1Score != 0) {
				if(p2.getScoreMult() == 1) {
					g.setFill(Color.WHITE);
					g.setFont(new Font("", 30));
					g.fillText("+"+exOb1Score, x, (y-50)-slideY);
				}else {
					g.setFill(Color.WHITE);
					g.setFont(new Font("", 30));
					g.fillText("+"+exOb1Score*p2.getScoreMult(), x, (y-50)-slideY);
				}	
			}
		}
		if(exOb2) {
			if(exOb2Score != 0) {
				if(p2.getScoreMult() == 1) {
					g.setFill(Color.WHITE);
					g.setFont(new Font("", 30));
					g.fillText("+"+exOb2Score, x, (y-100)-slideY);
				}else {
					g.setFill(Color.WHITE);
					g.setFont(new Font("", 30));
					g.fillText("+"+exOb2Score*p2.getScoreMult(), x, (y-100)-slideY);
				}	
			}
		}
		if(exUn1) {
			if(exUn1Score != 0) {
				if(p2.getScoreMult() == 1) {
					g.setFill(Color.WHITE);
					g.setFont(new Font("", 30));
					g.fillText("+"+exUn1Score, x, (y+50)-slideY);
				}else {
					g.setFill(Color.WHITE);
					g.setFont(new Font("", 30));
					g.fillText("+"+exUn1Score*p2.getScoreMult(), x, (y+50)-slideY);
				}	
			}
		}
		if(exUn2) {
			if(exUn2Score != 0) {
				if(p2.getScoreMult() == 1) {
					g.setFill(Color.WHITE);
					g.setFont(new Font("", 30));
					g.fillText("+"+exUn2Score, x, (y+100)-slideY);
				}else {
					g.setFill(Color.WHITE);
					g.setFont(new Font("", 30));
					g.fillText("+"+exUn2Score*p2.getScoreMult(), x, (y+100)-slideY);
				}
			}
		}
		if(exRe1) {
			if(exRe1Score != 0) {
				if(p2.getScoreMult() == 1) {
					g.setFill(Color.WHITE);
					g.setFont(new Font("", 30));
					g.fillText("+"+exRe1Score, x+50, y-slideY);
				}else {
					g.setFill(Color.WHITE);
					g.setFont(new Font("", 30));
					g.fillText("+"+exRe1Score*p2.getScoreMult(), x+50, y-slideY);
				}	
			}
		}
		if(exRe2) {
			if(exRe2Score != 0) {
				if(p2.getScoreMult() == 1) {
					g.setFill(Color.WHITE);
					g.setFont(new Font("", 30));
					g.fillText("+"+exRe2Score, x+100, y-slideY);
				}else {
					g.setFill(Color.WHITE);
					g.setFont(new Font("", 30));
					g.fillText("+"+exRe2Score*p2.getScoreMult(), x+100, y-slideY);
				}	
			}
		}
		if(exLi1) {
			if(exLi1Score != 0) {
				if(p2.getScoreMult() == 1) {
					g.setFill(Color.WHITE);
					g.setFont(new Font("", 30));
					g.fillText("+"+exLi1Score, x-50, y-slideY);
				}else {
					g.setFill(Color.WHITE);
					g.setFont(new Font("", 30));
					g.fillText("+"+exLi1Score*p2.getScoreMult(), x-50, y-slideY);
				}	
			}
		}
		if(exLi2) {
			if(exLi2Score != 0) {
				if(p2.getScoreMult() == 1) {
					g.setFill(Color.WHITE);
					g.setFont(new Font("", 30));
					g.fillText("+"+exLi1Score, x-100, y-slideY);
				}else {
					g.setFill(Color.WHITE);
					g.setFont(new Font("", 30));
					g.fillText("+"+exLi1Score*p2.getScoreMult(), x-100, y-slideY);
				}
			}
		}
	}
	

	public void überprüfung() {
		
		if(y-50 >= 0 && !mapList.get((y/50)-1).get(x/50).contentEquals("2")) {	//sonst array out of bounds! und block überprüfung
			//1 obendrüber
			exOb1 = true;	
			}
		
		if(y-100 >= 0  && !mapList.get((y/50)-2).get(x/50).contentEquals("2") && !mapList.get((y/50)-1).get(x/50).contentEquals("2") && !mapList.get((y/50)-1).get(x/50).contentEquals("3") && !mapList.get((y/50)-1).get(x/50).contentEquals("4") && !mapList.get((y/50)-1).get(x/50).contentEquals("5") && !mapList.get((y/50)-1).get(x/50).contentEquals("6") && !mapList.get((y/50)-1).get(x/50).contentEquals("7")) {	//sonst array out of bounds!
			//2 obendrüber
			exOb2 = true;
		}
		
		if(y+50 < 600  && !mapList.get((y/50)+1).get(x/50).contentEquals("2")) {	//sonst array out of bounds!
			//1 untendrunter
			exUn1 = true;
		}
		
		if(y+100 < 600   && !mapList.get((y/50)+2).get(x/50).contentEquals("2") && !mapList.get((y/50)+1).get(x/50).contentEquals("2") && !mapList.get((y/50)+1).get(x/50).contentEquals("3") && !mapList.get((y/50)+1).get(x/50).contentEquals("4") && !mapList.get((y/50)+1).get(x/50).contentEquals("5") && !mapList.get((y/50)+1).get(x/50).contentEquals("6") && !mapList.get((y/50)+1).get(x/50).contentEquals("7")) {
			//2 untendrunter
			exUn2 = true;
		}
		
		if(x+50 < 800 && !mapList.get(y/50).get((x/50)+1).contentEquals("2")) {
			//1 rechts
			exRe1 = true;
		}
		
		if(x+100 < 800 && !mapList.get(y/50).get((x/50)+2).contentEquals("2") && !mapList.get(y/50).get((x/50)+1).contentEquals("2") && !mapList.get(y/50).get((x/50)+1).contentEquals("3") && !mapList.get(y/50).get((x/50)+1).contentEquals("4") && !mapList.get(y/50).get((x/50)+1).contentEquals("5") && !mapList.get(y/50).get((x/50)+1).contentEquals("6") && !mapList.get(y/50).get((x/50)+1).contentEquals("7")) {
			//2 rechts
				exRe2 = true;
		}
		
		if(x-50 >= 0 && !mapList.get(y/50).get((x/50)-1).contentEquals("2")) {
			//1 links
				exLi1 = true;
		}
		
		if(x-100 >= 0 && !mapList.get(y/50).get((x/50)-2).contentEquals("2") && !mapList.get(y/50).get((x/50)-1).contentEquals("2") && !mapList.get(y/50).get((x/50)-1).contentEquals("3") && !mapList.get(y/50).get((x/50)-1).contentEquals("4") && !mapList.get(y/50).get((x/50)-1).contentEquals("5") && !mapList.get(y/50).get((x/50)-1).contentEquals("6") && !mapList.get(y/50).get((x/50)-1).contentEquals("7")) {
			//2 links
				exLi2 = true;
		}
	}

	public Boolean p2Collision() {
		if(y-50 >= 0) {	//sonst array out of bounds!
			//1 obendrüber	
			if(y-50 == p2.getPosition().getY() && x == p2.getPosition().getX()) {
					return true;
				}
			}
		
		if(y-100 >= 0) {	//sonst array out of bounds!
			//2 obendrüber
			if(y-100 == p2.getPosition().getY() && x == p2.getPosition().getX() && !mapList.get((int) (y/50) - 1).get(x/50).equals("2") && !mapList.get((int) (y/50) - 1).get(x/50).equals("3") && !mapList.get((int) (y/50) - 1).get(x/50).equals("4") && !mapList.get((int) (y/50) - 1).get(x/50).equals("5") && !mapList.get((int) (y/50) - 1).get(x/50).equals("6") && !mapList.get((int) (y/50) - 1).get(x/50).equals("7")) {
				return true;
			}
		}
		
		if(y+50 < 600) {	//sonst array out of bounds!
			//1 untendrunter
			if(y+50 == p2.getPosition().getY() && x == p2.getPosition().getX()) {
				return true;
			}
		}
		
		if(y+100 < 600) {
			//2 untendrunter
			if(y+100 == p2.getPosition().getY() && x == p2.getPosition().getX() && !mapList.get((int) (y/50) + 1).get(x/50).equals("2") && !mapList.get((int) (y/50) + 1).get(x/50).equals("3") && !mapList.get((int) (y/50) + 1).get(x/50).equals("4") && !mapList.get((int) (y/50) + 1).get(x/50).equals("5") && !mapList.get((int) (y/50) + 1).get(x/50).equals("6") && !mapList.get((int) (y/50) + 1).get(x/50).equals("7")) {
				return true;
			}
		}
		
		if(x+50 < 800) {
		//1 rechts
			if(y == p2.getPosition().getY() && x+50 == p2.getPosition().getX()) {
				return true;
			}
		}
		
		if(x+100 < 800) {
			//2 rechts
			if(y == p2.getPosition().getY() && x+100 == p2.getPosition().getX() && !mapList.get((int) y/50).get((x/50)+1).equals("2") && !mapList.get((int) y/50).get((x/50)+1).equals("3") && !mapList.get((int) y/50).get((x/50)+1).equals("4") && !mapList.get((int) y/50).get((x/50)+1).equals("5") && !mapList.get((int) y/50).get((x/50)+1).equals("6") && !mapList.get((int) y/50).get((x/50)+1).equals("7")) {
				return true;
			}
		}
		
		if(x-50 >= 0) {
			//1 links
			if(y == p2.getPosition().getY() && x-50 == p2.getPosition().getX()) {
				return true;
			}
		}
		
		if(x-100 >= 0) {
			//2 links
			if(y == p2.getPosition().getY() && x-100 == p2.getPosition().getX() && !mapList.get((int) y/50).get((x/50)-1).equals("2") && !mapList.get((int) y/50).get((x/50)-1).equals("3") && !mapList.get((int) y/50).get((x/50)-1).equals("4") && !mapList.get((int) y/50).get((x/50)-1).equals("5") && !mapList.get((int) y/50).get((x/50)-1).equals("6") && !mapList.get((int) y/50).get((x/50)-1).equals("7")) {
				return true;
			}
		}
		
		if(y == p2.getPosition().getY() && x == p2.getPosition().getX()) {
			return true;
		}
		
		return false;
	}

		//überprüfung, ob block dazwischen
	public Boolean p1Collision() {
		if(y-50 >= 0) {	//sonst array out of bounds!
			//1 obendrüber	
			if(y-50 == p1.getPosition().getY() && x == p1.getPosition().getX()) {
					return true;
				}
			}
		
		if(y-100 >= 0) {	//sonst array out of bounds!
			//2 obendrüber
			if(y-100 == p1.getPosition().getY() && x == p1.getPosition().getX() && !mapList.get((int) (y/50) - 1).get(x/50).equals("2") && !mapList.get((int) (y/50) - 1).get(x/50).equals("3") && !mapList.get((int) (y/50) - 1).get(x/50).equals("4") && !mapList.get((int) (y/50) - 1).get(x/50).equals("5") && !mapList.get((int) (y/50) - 1).get(x/50).equals("6") && !mapList.get((int) (y/50) - 1).get(x/50).equals("7")) {
				return true;
			}
		}
		
		if(y+50 < 600) {	//sonst array out of bounds!
			//1 untendrunter
			if(y+50 == p1.getPosition().getY() && x == p1.getPosition().getX()) {
				return true;
			}
		}
		
		if(y+100 < 600) {
			//2 untendrunter
			if(y+100 == p1.getPosition().getY() && x == p1.getPosition().getX() && !mapList.get((int) (y/50) + 1).get(x/50).equals("2") && !mapList.get((int) (y/50) + 1).get(x/50).equals("3") && !mapList.get((int) (y/50) + 1).get(x/50).equals("4") && !mapList.get((int) (y/50) + 1).get(x/50).equals("5") && !mapList.get((int) (y/50) + 1).get(x/50).equals("6") && !mapList.get((int) (y/50) + 1).get(x/50).equals("7")) {
				return true;
			}
		}
		
		if(x+50 < 800) {
		//1 rechts
			if(y == p1.getPosition().getY() && x+50 == p1.getPosition().getX()) {
				return true;
			}
		}
		
		if(x+100 < 800) {
			//2 rechts
			if(y == p1.getPosition().getY() && x+100 == p1.getPosition().getX() && !mapList.get((int) y/50).get((x/50)+1).equals("2") && !mapList.get((int) y/50).get((x/50)+1).equals("3") && !mapList.get((int) y/50).get((x/50)+1).equals("4") && !mapList.get((int) y/50).get((x/50)+1).equals("5") && !mapList.get((int) y/50).get((x/50)+1).equals("6") && !mapList.get((int) y/50).get((x/50)+1).equals("7")) {
				return true;
			}
		}
		
		if(x-50 >= 0) {
			//1 links
			if(y == p1.getPosition().getY() && x-50 == p1.getPosition().getX()) {
				return true;
			}
		}
		
		if(x-100 >= 0) {
			//2 links
			if(y == p1.getPosition().getY() && x-100 == p1.getPosition().getX() && !mapList.get((int) y/50).get((x/50)-1).equals("2") && !mapList.get((int) y/50).get((x/50)-1).equals("3") && !mapList.get((int) y/50).get((x/50)-1).equals("4") && !mapList.get((int) y/50).get((x/50)-1).equals("5") && !mapList.get((int) y/50).get((x/50)-1).equals("6") && !mapList.get((int) y/50).get((x/50)-1).equals("7")) {
				return true;
			}
		}
		
		if(y == p1.getPosition().getY() && x == p1.getPosition().getX()) {
			return true;
		}
		
		return false;
	}
	
	public void playerCollision() {
		if(p2Collision() && p1Collision()) {
			p1.setScore(p1.getScore()+1000);
			p2.setScore(p2.getScore()+1000);
			p1.end();
			p2.end();
		}else if(p2Collision()) {
			p1.setScore(p1.getScore()+1000);
			p1.end();
			p2.end();
		}else if(p1Collision()) {
			p2.setScore(p2.getScore()+1000);
			p1.end();	//p1 soll methode im control aufrufen -> neue musik
			p2.end();
		}
	}
	
	@Override
	public void run() {
		while(on) {
			System.out.println("Bombe wartet auf eine Aktion");
			if(run) {
				activate();
			}
		}
	}
	
	public void restart() {
		on = true;
	}

}

