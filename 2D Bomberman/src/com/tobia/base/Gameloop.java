package com.tobia.base;


import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Gameloop extends AnimationTimer /*implements Runnable*/{

	
	private final int width = 800, height = 600;
	
	private ArrayList<GameObject> renderables = new ArrayList<GameObject>();
	private ArrayList<GameObject> updateables = new ArrayList<GameObject>();
	private Player1 p1;
	private Player2 p2;
	private Control control;
	private GraphicsContext g;
	private ArrayList<ArrayList<String>> map;
	private MapReader mapReader;
	private int mapChoose = 0;
	private Boolean rendering = true;
	private Boolean endGameMusicOn = false;
	
	private SpeedItem speedItem;
	
	public Gameloop(Player1 p1, Player2 p2, Canvas c, Control control) {
		this.p1 = p1;
		this.p2 = p2;
		this.control = control;
		g = c.getGraphicsContext2D();
		
		//überprüfen welche map -> muss noch rein
		mapReader = new MapReader();
		loadMap();
		map = mapReader.getMap();	//zuvor, muss gelesen werden!
		
		//p1 und p2 map übergeben
		p1.setMapReader(map);
		p2.setMapReader(map);
		
		p1.setPlayer2(p2);
		p2.setPlayer1(p1);
		
		p1.setGameloop(this);
		
		//items laden
		speedItem = new SpeedItem(p1, p2, map);
		
		addComponents();
	}
	
	public void loadMap() {
		mapChoose = p1.getMap();
		
		switch(mapChoose) {		//füge blöcke hinzu, wo man nur von rechts durch kann, oder so -> macht es interesanter-> vlt ist man auf einerm Block nicht angreifbar -> dazu noch die abfrage ausweiten, wegen 2 blöcken oder mehr-> soll nicht durch die unzerstörbaren durch gehen
		case 0:	mapReader.loadFile("map1");
				p1.setPosition(100, 300);
				p2.setPosition(700, 300);
			break;
		case 1:	mapReader.loadFile("map2");
				p1.setPosition(400, 0);
				p2.setPosition(400, 550);
			break;
		case 2:	mapReader.loadFile("map3");
				p1.setPosition(0, 300);
				p2.setPosition(750, 300);
			break;
		case 3:	mapReader.loadFile("map4");
				p1.setPosition(150, 400);
				p2.setPosition(550, 100);
			break;
		case 4:	mapReader.loadFile("map5");
				p1.setPosition(300, 300);
				p2.setPosition(550, 100);
			break;
		case 5:	mapReader.loadFile("map6");
				p1.setPosition(200, 150);
				p2.setPosition(550, 400);
			break;
		case 6:	mapReader.loadFile("map7");
				p1.setPosition(750, 0);
				p2.setPosition(750, 550);
			break;
		case 7:	mapReader.loadFile("map8");
				p1.setPosition(0, 0);
				p2.setPosition(750, 0);
			break;
		case 8:	mapReader.loadFile("map9");
				p1.setPosition(0, 0);
				p2.setPosition(750, 550);
			break;
		}
	}
	
	
	public void addComponents() {
		//Renderables
		this.addRenderable(p1);
		this.addRenderable(p2);
		this.addRenderable(speedItem);
		
		//Updatables
		this.addUpdateable(p1);
		this.addUpdateable(p2);
		this.addUpdateable(speedItem);
	}
	
	@Override
	public void handle(long currentNanoTime) {
		update();
		render();
	}
	
	
	public void render() {
		if(rendering) {
			g.clearRect(0, 0, width, height);
			
			
			//zeichnen der Map
			mapRender(g);
		}
			//System.out.println((125-125%50)/50);
					
		for(GameObject o:renderables) {		
			o.render(g);
		}
			
	}
	
	public void update() {
		for(GameObject o:updateables) {
			o.update();
		}
		if(!rendering && !endGameMusicOn) {	//spiel zu Ende
			control.endGameMusic();
			endGameMusicOn = true;
		}
	}
	
	public void addRenderable(GameObject o) {
		renderables.add(o);
	}
	
	public void addUpdateable(GameObject o) {
		updateables.add(o);
	}
	
	
	
	public void mapRender(GraphicsContext g) {
		for(int i = 0; i < map.size()/*11*/; i++) {
			for(int m = 0; m < map.get(0).size()/*15*/; m++) {
				
				switch(map.get(i).get(m)) {
				case "0":	// background
					g.setFill(Color.LIGHTGREY);
					g.fillRect(m*50, i*50, 50, 50);
					break;
				case "1":	//earth-blocks
					g.setFill(Color.BROWN);
					g.fillRect(m*50, i*50, 50, 50);
					break;
				case "2":	//unbreakable
					g.setFill(Color.DARKGRAY);
					g.fillRect(m*50, i*50, 50, 50);
					break;
				case "3":	//silber 2 mal -> letztes mal
					g.setFill(Color.GOLD);
					g.fillRect(m*50, i*50, 50, 50);
					break;
				case "4":	//silber 1 mal
					g.setFill(Color.GOLD);
					g.fillRect(m*50, i*50, 50, 50);
					break;
				case "5":	//gold 3 mal	-> letztes mal
					g.setFill(Color.CYAN);
					g.fillRect(m*50, i*50, 50, 50);
					break;
				case "6":	//gold 2 mal
					g.setFill(Color.CYAN);
					g.fillRect(m*50, i*50, 50, 50);
					break;
				case "7":	//gold 1 mal
					g.setFill(Color.CYAN);
					g.fillRect(m*50, i*50, 50, 50);
					break;
				}
				
			}
		}
	}
	
	public void setRendering(Boolean r) {	//wenn zu Ende dann diese Methode von p1
		rendering = r;
		speedItem.end();
	}
	
}
