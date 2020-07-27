package com.tobia.base;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DarkCloud implements GameObject{
	private int größe = 0;
	private int y, width;	//seitengröße eines vierecks
	private float x, v;		//vlt alle gleich schnell, dann unnötig -> dann lieber int
	private Boolean left = true;
	
	public DarkCloud(float x, int y, int größe) {
		this.x = x;
		this.y = y;
		this.größe = größe;
		
		setWidthRelativeToSize(größe);
	}
	
	public DarkCloud(float x, int y, int größe, float v) {
		this.x = x;
		this.y = y;
		this.größe = größe;
		this.v = v;
		
		setWidthRelativeToSize(größe);
	}
	
	public DarkCloud(float x, int y, int größe, float v, int width) {
		this.x = x;
		this.y = y;
		this.größe = größe;
		this.v = v;
		this.width = width;
	}
	
	//arrayList mit Raindrops
	
	public void setWidthRelativeToSize(int größe) {
		switch(größe) {
		case 0:	width = 20;
			break;
		case 1:	width = 30;
			break;
		}
	}
	
	public void setVelocity(float v) {
		this.v = v;
	}
	
	public void setPosition(float x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setGröße(int i) {
		größe = i;
		setWidthRelativeToSize(i);
	}
	
	public void setRight() {
		left = false;
	}
	
	public int getX() {
		return (int) x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		switch(größe) {
		case 1:		return width*6;
		case 2:		return width*15;
		}
		return 10;
	}
	
	public int getSpeed() {
		return (int) v;
	}
	
	public void checkLeftCollision() {
		if(x+width < 0) {
			x = 900;	//vlt relativ zu größe und breite
			int max = 600-width;
			int min = 0;
			y = (int) (Math.random()*(max - min + 1) + min);
		}
	}
	
	public void checkRightCollision() {
		if(x+width > 1200) {	//eigentlich 600
			x = 0;	//vlt relativ zu größe und breite
			int max = 600-width;
			int min = 0;
			y = (int) (Math.random()*(max - min + 1) + min);
		}
	}
	
	public void faster() {
		if(left) {
			v += 1;
		}else {
			v -= 1;
		}
	}
	
	public void slower() {
		if(left && v > 0) {
			v -= 1;
		}else if(!left && v < 0){
			v += 1;
		}
	}
	
	@Override
	public void update() {	//überladene Methode für StartRender
		x -= v;
		
		if(left) {
			checkLeftCollision();
		}else {
			checkRightCollision();
		}
	}

	@Override
	public void render(GraphicsContext g) {
		
		if(größe == 0) {
			g.setFill(Color.RED);
			
			g.fillRect((int) x, y, width, width);
			g.fillRect((int) x - width, y, width, width);
			g.fillRect((int)x - width, y - width, width, width);
			g.fillRect((int)x - 2*width, y, width, width);
		}else if(größe == 1) {
			g.setFill(Color.RED);
			
			//mittlere ebene
			g.fillRect((int) x, y, width, width);	//ganz rechts
			g.fillRect((int) x - width, y, width, width);
			g.fillRect((int) x - 2*width, y, width, width);
			g.fillRect((int) x - 3*width, y, width, width);
			g.fillRect((int) x - 4*width, y, width, width);
			g.fillRect((int) x - 5*width, y, width, width);
			
			//oben 1
			g.fillRect((int) x - width, y - width, width, width);
			g.fillRect((int) x - 2*width, y - width, width, width);
			g.fillRect((int) x - 3*width, y - width, width, width);
			g.fillRect((int) x - 4*width, y - width, width, width);
			g.fillRect((int) x - 5*width, y - width, width, width);
			
			//oben 2
			g.fillRect((int) x - 2*width + width/2, y - 2*width, width, width);
			g.fillRect((int) x - 3*width + width/2, y - 2*width, width, width);
			
			//oben 3
			
			//unten 1
			g.fillRect((int) x, y + width, width, width);	//ganz rechts
			g.fillRect((int) x - width, y + width, width, width);
			g.fillRect((int) x - 2*width, y + width, width, width);
			g.fillRect((int) x - 3*width, y + width, width, width);
			g.fillRect((int) x - 4*width, y + width, width, width);
		
			//unten 2
			//g.fillRect((int) x - width, y + 2*width, width, width);
			g.fillRect((int) x - 2*width, y + 2*width, width, width);
			
			
			//spezial
			g.fillRect((int) x - 5*width - width/2, y - width/2, width/2, width/2);
			g.fillRect((int) x - 2*width - width/2, y + 2*width, width/2, width/2);
			g.fillRect((int) x - width, y + 2*width, width/4, width/4);
			
		}else if(größe == 2) {
			g.setFill(Color.RED);
			
			//mittlere ebene
			g.fillRect((int) x, y, width, width);	//ganz rechts
			g.fillRect((int) x - width, y, width, width);
			g.fillRect((int) x - 2*width, y, width, width);
			g.fillRect((int) x - 3*width, y, width, width);
			g.fillRect((int) x - 4*width, y, width, width);
			g.fillRect((int) x - 5*width, y, width, width);
			g.fillRect((int) x - 6*width, y, width, width);
			g.fillRect((int) x - 7*width, y, width, width);
			g.fillRect((int) x - 8*width, y, width, width);
			g.fillRect((int) x - 9*width, y, width, width);
			g.fillRect((int) x - 10*width, y, width, width);
			g.fillRect((int) x - 11*width, y, width, width);
			g.fillRect((int) x - 12*width, y, width, width);
			g.fillRect((int) x - 13*width, y, width, (int) (width*1.75));
			//g.fillRect((int) x - 14*width, y, width, width);
			
			//oben 1
			g.fillRect((int) x - 2*width, y - width, width, width);
			g.fillRect((int) x - 3*width, y - width, width, width);
			g.fillRect((int) x - 4*width, y - width, width, width);
			g.fillRect((int) x - 5*width, y - width, width, width);
			g.fillRect((int) x - 5*width, y - width, width, width);
			g.fillRect((int) x - 6*width, y - width, width, width);
			g.fillRect((int) x - 7*width, y - width, width, width);
			g.fillRect((int) x - 8*width, y - width, width, width);
			g.fillRect((int) x - 9*width, y - width, width, width);
			g.fillRect((int) x - 10*width, y - width, width, width);
			g.fillRect((int) x - 11*width, y - width, width, width);
			g.fillRect((int) x - 12*width, y - width, width, width);
			
			//oben 2
			g.fillRect((int) x - 3*width , y - 2*width, width, width);
			g.fillRect((int) x - 4*width , y - 2*width, width, width);
			g.fillRect((int) x - 5*width , y - 2*width, width, width);
			g.fillRect((int) x - 6*width , y - 2*width, width, width);
			g.fillRect((int) x - 7*width , y - 2*width, width, width);
			g.fillRect((int) x - 8*width , y - 2*width, width, width);
			g.fillRect((int) x - 9*width , y - 2*width, width, width);
			g.fillRect((int) x - 10*width , y - 2*width, width, width);
			
			//oben 3
			g.fillRect((int) x - 4*width , y - 3*width, width, width);
			g.fillRect((int) x - 7*width , y - 3*width, width, width);
			g.fillRect((int) x - 8*width , y - 3*width, width, width);
			g.fillRect((int) x - 9*width , y - 3*width, width, width);
			
			//oben 4
			g.fillRect((int) x - 8*width , y - 4*width, width, width);
			
			//unten 1
			g.fillRect((int) x - 2*width, y + width, width/2, width/2);
			g.fillRect((int) x - 3*width, y + width, width, width);
			g.fillRect((int) x - 4*width, y + width, width, width);
			g.fillRect((int) x - 5*width, y + width, width, width);
			g.fillRect((int) x - 6*width, y + width, width, width);
			g.fillRect((int) x - 7*width, y + width, width, width);
			g.fillRect((int) x - 8*width, y + width, width, width);
			g.fillRect((int) x - 9*width, y + width, width, width);
			g.fillRect((int) x - 10*width, y + width, width, width);
			g.fillRect((int) x - 11*width, y + width, width, width);
			g.fillRect((int) x - 12*width, y + width, width, width);
		
			//unten 2
			g.fillRect((int) x - 4*width, y + 2*width, width, width);
			g.fillRect((int) x - 5*width, y + 2*width, width, width);
			g.fillRect((int) x - 6*width, y + 2*width, width, width);
			g.fillRect((int) x - 7*width, y + 2*width, width, width);
			g.fillRect((int) x - 8*width, y + 2*width, width, width);
			g.fillRect((int) x - 9*width, y + 2*width, width, width);
			g.fillRect((int) x - 10*width, y + 2*width, width, width);
			
			//special
			g.fillRect((int) x - 3*width , y + 2*width, width/3, width/3);	//rechts unten
			g.fillRect((int) x - 10*width - width/2, y + 2*width, width/2, width/2);	//links unten
			g.fillRect((int) x - 10*width - width/3 - width/2, y + 2*width, width/3, width/3);	//links unten
			g.fillRect((int) x - 10*width - width/6 - width/3 - width/2, y + 2*width, width/6, width/6);	//links unten
			g.fillRect((int) x - 10*width - width/2, y - 2*width + width/2 +1, width/2, width/2);	//links oben
		}
		
		//regen
		if(v == 0) {
			
		}
			
	}

}

class Raindrop implements GameObject{
	
	public Raindrop() {
		
	}

	@Override
	public void render(GraphicsContext g) {
		
	}

	@Override
	public void update() {
		
	}
}