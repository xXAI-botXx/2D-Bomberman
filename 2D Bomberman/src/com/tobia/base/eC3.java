package com.tobia.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class eC3 extends AnimationTimer {

	
	private final int width = 800, height = 600;
	
	private ArrayList<GameObject> renderables = new ArrayList<GameObject>();
	private ArrayList<GameObject> updateables = new ArrayList<GameObject>();
	private Bird p1;
	private Control control;
	private GraphicsContext g;
	private Pipes pipes;
	private MusicLoader flapSound;
	private Image i1;
	private Boolean pauseOff = true;
	
	private Cloud cloud1;
	private Cloud cloud2;
	
	
	public eC3(Canvas c, Control control) {
		this.control = control;
		g = c.getGraphicsContext2D();
		
		pipes = new Pipes();
		p1 = new Bird(control);
		
		//p1.setGameloop(this);
		p1.setPipes(pipes);
		flapSound = new MusicLoader();
		flapSound.loadWav("2174__spexis__cloche2-spexis");
		p1.setMusic(flapSound);
		
		
		//Image
		try {
			i1 = new Image(getClass().getClassLoader().getResourceAsStream("Images/beatiplanet.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		//clouds init
		cloud1 = new Cloud(900, 150, 2, 1.0f, 13);
		
		cloud2 = new Cloud(1300, 300, 1, 1.0f, 30);
	
		
		//items laden
		addComponents();
		
		//canvas mouse listening
		c.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				switch(event.getButton()) {
				case PRIMARY:	p1.up();
					break;
				case SECONDARY:
					if(pauseOff) {
						pauseOff = false;
					}else {
						pauseOff = true;
					}
					break;
				case MIDDLE:
					if(p1.getGravityMode()) {
						p1.setGravityMode(false);
						p1.setGravity(p1.getGravity()*-1);
					}else {
						p1.setGravityMode(true);
						p1.setGravity(p1.getGravity()*-1);
					}
					break;
				default: p1.up();
					break;
				}
			}
			
		});
	}
	
	public void addComponents() {
		//Renderables
		addRenderable(cloud1);
		addRenderable(cloud2);
		addRenderable(p1);
		addRenderable(pipes);
		
		
		//Updatables
		addUpdateable(p1);
		addUpdateable(pipes);
		addUpdateable(cloud1);
		addUpdateable(cloud2);
	}
	
	@Override
	public void handle(long currentNanoTime) {
		if(pauseOff) {
			update();
		}
		render();
	}
	
	
	public void render() {
		g.clearRect(0, 0, width, height);
		
		
		//zeichnen der Background
		g.setFill(Color.LIGHTSKYBLUE);	
		g.fillRect(0, 0, 800, 600);
			
		g.drawImage(i1, 450, 100);
					
		for(GameObject o:renderables) {		
			o.render(g);
		}
		
		g.setFill(Color.WHITE);
		g.fillText("Score: "+p1.getScore(), 25, 50);
	}
	
	public void update() {
		for(GameObject o:updateables) {
			o.update();
		}
	}
	
	public void addRenderable(GameObject o) {
		renderables.add(o);
	}
	
	public void addUpdateable(GameObject o) {
		updateables.add(o);
	}
}

//innere Klasse -> 3 Röhren (6 -> oben und unten)
class Pipes implements GameObject{

	private int pipeWidth = 100;
	private int pipeHorizontalSpacing = 210;
	private int pipeVerticalSpacing = 180;
	private float xVel = -5.0f;
	private float x1, x2, x3;
	private float y1, y2, y3;
	
	private final int height = 600, width = 800;
	
	//The pipe that is closest to the bird
	private int currentPipe;
	
	private float[][] pipeCoords = new float[3][2];	//3 Pipes, with 2 Dates(x,y)
										//[x1, x2, x3] [y1, y2, y3]
	private Random rand;
	
	public Pipes() {
		rand = new Random();
		
		resetPipes();
	}
	
	
	public void resetPipes() {
		currentPipe = 0;
		
		x1 = width * 2;	//mit kleinem delay, damit man kurz Zeit hat
		x2 = x1 + pipeWidth + pipeHorizontalSpacing;
		x3 = x2 + pipeWidth + pipeHorizontalSpacing;
		
		y1 = getRandomY();
		y2 = getRandomY();
		y3 = getRandomY();
	}
	
	
	public int getRandomY() {
		return rand.nextInt((int)(height * 0.4f)) + (height / 10);	//anders schreiben -> siehe clouds
	}
	
	public float[] getCurrentPipe() {
		return pipeCoords[currentPipe];	//jenachdem, welche Pipe die nächste ist, wird diese geupdatet und hier verwendet zum zeichnen
	}
	
	public int getCurrentPipeID() {
		return currentPipe;
	}
	
	public int getPipeWidth() {
		return pipeWidth;
	}
	
	public int getHorizontalSpacing() {
		return pipeHorizontalSpacing;
	}
	
	public int getVerticalSpacing() {
		return pipeVerticalSpacing;
	}
	
	@Override
	public void render(GraphicsContext g) {
			g.setFill(Color.DARKGREEN);
			
			//Pipe 1
			g.fillRect((int) (x1 +xVel), 0, pipeWidth, (int) y1);	
			g.fillRect((int) (x1 +xVel), (int)(y1 + pipeVerticalSpacing), pipeWidth, height);	//nun spiegelt man die Röhre, indem man die Y Koordiante nimmt, diese gibt an bis wohin die obere Pipe geht, addiert dort dran den Abstand, den man zuvor geschrieben hat
			//Pipe 2
			g.fillRect((int) (x2 +xVel), 0, pipeWidth, (int) y2);	
			g.fillRect((int) (x2 +xVel), (int)(y2 + pipeVerticalSpacing), pipeWidth, height);	//nun spiegelt man die Röhre, indem man die Y Koordiante nimmt, diese gibt an bis wohin die obere Pipe geht, addiert dort dran den Abstand, den man zuvor geschrieben hat
			//Pipe 3
			g.fillRect((int) (x3 + xVel), 0, pipeWidth, (int) y3);	
			g.fillRect((int) (x3 + xVel), (int)(y3 + pipeVerticalSpacing), pipeWidth, height);	//nun spiegelt man die Röhre, indem man die Y Koordiante nimmt, diese gibt an bis wohin die obere Pipe geht, addiert dort dran den Abstand, den man zuvor geschrieben hat
	}


	@Override
	public void update() {
		x1 += xVel;	//Pipes bewegen sich, durch die Bewegung in X Richtung
		x2 += xVel;
		x3 += xVel;
		
		if(x1 + pipeWidth < 0) {	// wenn die rechte Ecke der Röhre aus dem Bildschirm verschwunden ist (<0). Das bedeutet, wenn die komplette Röhre nicht mehr sichtbar ist, dann: resete die Position der Pipe
			x1 = width;	//reset ganz rechts vom Game-Bildschirm
			y1 = getRandomY();	//neue random Position auf der Y-Achse
			currentPipe = 1;	//current Pipe = die Pipe mit wem der Spieler kollidieren könnte, in diesem Fall ist das die nächste Pipe
		}//hab ich später hinzugefügt
		
		if(x2 + pipeWidth < 0) {	// wenn die rechte Ecke der Röhre aus dem Bildschirm verschwunden ist (<0). Das bedeutet, wenn die komplette Röhre nicht mehr sichtbar ist, dann: resete die Position der Pipe
			x2 = width;	//reset ganz rechts vom Game-Bildschirm
			y2 = getRandomY();	//neue random Position auf der Y-Achse
			currentPipe = 2;	//current Pipe = die Pipe mit wem der Spieler kollidieren könnte, in diesem Fall ist das die nächste Pipe
		}
		
		if(x3 + pipeWidth < 0) {	// wenn die rechte Ecke der Röhre aus dem Bildschirm verschwunden ist (<0). Das bedeutet, wenn die komplette Röhre nicht mehr sichtbar ist, dann: resete die Position der Pipe
			x3 = width;	//reset ganz rechts vom Game-Bildschirm
			y3 = getRandomY();	//neue random Position auf der Y-Achse
			currentPipe = 0;	//current Pipe = die Pipe mit wem der Spieler kollidieren könnte, in diesem Fall ist das die nächste Pipe
		}
		
		
		switch(currentPipe) {	//updatet die position der Pipes, je nachdem welche Pipe die nächste ist. Siehe getCurrentPipe
		case 0:
			pipeCoords[0][0] = x1;
			pipeCoords[0][1] = y1;
			break;
		case 1:
			pipeCoords[1][0] = x2;
			pipeCoords[1][1] = y2;
			break;
		case 2:
			pipeCoords[2][0] = x3;
			pipeCoords[2][1] = y3;
			break;
		}

	}

}


class Bird implements GameObject{
	
	//Variablen
	private Control control;
	private int startPosX = 100, startPosY = 100;
	private Vertex vertex;
	private float yVel;	//Geschwindigkeit, wäre ebenfalls in einem Vertex in begriffen, oder man erweitert Vertex...
	private float baseYVel = -6.0f;	//Grund-Geschwindigkeit, wäre ebenfalls in einem Vertex in begriffen, oder man erweitert Vertex...
	private float gravity = 0.25f;	//gravity
	private final int width = 800, height = 600;
	private final int characterWidth = 50, characterHeight = 50;
	
	private MusicLoader flap;
	
	private float rechteObereEckeX;
	private float rechteUntereEckeX;
	private float rechteObereEckeY;
	private float rechteUntereEckeY;
	
	private float linkeUntereEckeX;
	private float linkeObereEckeX;	//normal x
	private float linkeUntereEckeY;
	private float linkeObereEckeY;	//normal y
	
	private Pipes pipes;
	private int scoredPipe = 0;	//=ID von der Pipe (current)
	
	private int score = 0;
	
	private direction dir;
	
	private Boolean normalGravity = true;
	
	
	
	public Bird(Control control) {
		this.control = control;
		init();
	}
	
	public enum direction{
		UP, NULL
	}
	
	public void up() {
		dir = direction.UP;
	}
	
	public void down() {
		dir = direction.NULL;
	}
	

	
	public void init() {
		vertex = new Vertex(startPosX, startPosY);
		dir = direction.NULL;
		
		rechteObereEckeX = (int)vertex.getX() + characterWidth;
		rechteUntereEckeX = (int)vertex.getX() + characterWidth;
		rechteObereEckeY = (int)vertex.getY();
		rechteUntereEckeY = (int)vertex.getY() + characterHeight;
		
		linkeObereEckeX = (int)vertex.getX();	//Bilder werden an der linken oberen Ecke gespawnt. 
		linkeUntereEckeX = (int)vertex.getX();
		linkeObereEckeY = (int)vertex.getY();
		linkeUntereEckeY = (int)vertex.getY() + characterHeight;
	
	}
	
	
	public Vertex getPosition() {
		return  vertex;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setPosition(int x, int y) {
		vertex.setX(x);
		vertex.setY(y);
	}
	
	public void setPipes(Pipes p) {
		pipes = p;
	}

	public void setMusic(MusicLoader flapSound) {
		flap = flapSound;
	}
	
	public void flap() {	//ein Flügelschlag
		if(normalGravity) {
			yVel = baseYVel;	//geschwindigkeit wird nach oben gestellt -> durch gravitation wird das wieder runtergezogen
		}else {
			yVel = baseYVel*-1;
		}
		flap.play();
	}
	
	
	public void setGravity(float gravity) {		// dann kann man als Spieler Optionen vornehmen
		this.gravity = gravity;
	}
	
	public float getGravity() {
		return gravity;
	}
	
	
	public void end() {
		control.stopEE();
	}
	
	public Boolean getGravityMode() {
		return normalGravity;
	}
	
	public void setGravityMode(Boolean b) {
		normalGravity = b;
	}
			
	
	//EventHandling - oder eigene Klasse, sind je nach Typ anders
	@Override
	public void update() {
		
		vertex.setY((float)vertex.getY()+yVel);	//zuerst nach oben
		yVel += gravity;	//dann wird er nach unten gezogen
		
		if(vertex.getY() < 0) {	//oberer Bildschirm
			vertex.setY(0);	//der Spieler soll im Bildschirm bleiben
			yVel = 0;
		}
		
		if(dir == dir.UP) {
			flap();
			dir = dir.NULL; //reset
		}
		
		
		
		//muss geupdatet werden, bevor man zur collision detectin kommt
		rechteObereEckeX = (int)vertex.getX() + characterWidth;
		rechteUntereEckeX = (int)vertex.getX() + characterWidth;
		rechteObereEckeY = (int)vertex.getY();
		rechteUntereEckeY = (int)vertex.getY() + characterHeight;	//flapUp.getcharacterHeight() -> bei bildern
		
		linkeObereEckeX = (int)vertex.getX();	//Bilder werden an der linken oberen Ecke gespawnt. 
		linkeUntereEckeX = (int)vertex.getX();
		linkeObereEckeY = (int)vertex.getY();
		linkeUntereEckeY = (int)vertex.getY() + characterHeight;
		
		//Collision detection
		float[] pipeCoords = pipes.getCurrentPipe();
		float pipeX = pipeCoords[0];
		float pipeY = pipeCoords[1];
			
		//wenn der Spieler mit den Pipes kollidiert / berührt				//aktuell wird nur die Linke Ecke geprüft
		if(((linkeObereEckeX >= pipeX && linkeObereEckeX <= pipeX + pipes.getPipeWidth() //wenn der Bird in der Röhre ist (X-Achse) und:
				&& (linkeObereEckeY <= pipeY || linkeObereEckeY >= pipeY + pipes.getVerticalSpacing()))	//wenn der Bird eine Röhre oben oder unten berührt (wenn y kleiner als pipeY -> oben ist Y=0 und wird nach unten hin größer, also wenn y kleiner als das ende der oberen Röhre ist oder wenn y größer als die untere Röhre ist
				
				|| vertex.getY() > height ||		// wenn der Bird unten am Bildschirm ist, unten ist Y größer als oben, deswegen ob y größer als die fenster höhe ist
				
				(rechteObereEckeX  >= pipeX && rechteObereEckeX <= pipeX + pipes.getPipeWidth()	//rechte obere Ecke: Wenn im Bereich einer Röhre und:
				&& (rechteObereEckeY <= pipeY || rechteObereEckeY >= pipeY + pipes.getVerticalSpacing()))
				
				|| (rechteUntereEckeX  >= pipeX && rechteUntereEckeX <= pipeX + pipes.getPipeWidth()	//rechte untere Ecke: Wenn im Bereich einer Röhre und:
				&& (rechteUntereEckeY <= pipeY || rechteUntereEckeY >= pipeY + pipes.getVerticalSpacing()))
				
				|| (linkeUntereEckeX  >= pipeX && linkeUntereEckeX <= pipeX + pipes.getPipeWidth()	//linke untere Ecke: Wenn im Bereich einer Röhre und:
				&& (linkeUntereEckeY <= pipeY || linkeUntereEckeY >= pipeY + pipes.getVerticalSpacing())))) {	//diese überprüfung könnte man jetzt weglassen
			
			end();	//gameover
			
		}else {
			int currentPipeID = pipes.getCurrentPipeID();
				
			if(scoredPipe != currentPipeID){	//prüft, ob die aktuelle Pipe noch aktuell ist -> könnte man auch anders prüfen -> indem man den hinteren Punkt der aktuellen Röhre prüft
				score += 1;
			} 		//wenn die letzte Pipe die aktuelle ist, soll der score gleich bleiben. Wenn die aktuelle Pipe nicht die letzte Pipe ist (also noch nicht durchsquert wurde, oder gerade dabei) soll der Score um eins erhöht werden 
			scoredPipe = currentPipeID;	//nun wird die aktuelle Pipe zu der letzten Pipe
		}
	}
	
	@Override
	public void render(GraphicsContext g) {		
		g.setFill(Color.BLACK);
	
		g.fillRect(vertex.getX(), vertex.getY(), characterWidth, characterHeight);
		
		if(normalGravity) {
			if(yVel > 0) {	//wenn bird fällt
				g.fillRect(vertex.getX()-5, vertex.getY()+5, 1, 20);
				g.fillRect(vertex.getX()+60, vertex.getY()+15, 1, 35);
				g.fillRect(vertex.getX()-7, vertex.getY()+2, 1, 10);
				g.fillRect(vertex.getX()+10, vertex.getY()-25, 1, 24);
				g.fillRect(vertex.getX()+29, vertex.getY()-35, 1, 34);
				g.fillRect(vertex.getX()+19, vertex.getY()-12, 1, 11);
				g.fillRect(vertex.getX()+13, vertex.getY()-15, 1, 5);
			}else if(yVel < 0) {	//wenn bird fliegt
				g.fillRect(vertex.getX()+10, vertex.getY()+75, 1, 24);
				g.fillRect(vertex.getX()+29, vertex.getY()+85, 1, 34);
				g.fillRect(vertex.getX()+19, vertex.getY()+62, 1, 11);
				g.fillRect(vertex.getX()+11, vertex.getY()+65, 1, 5);
			}
		}else {
			if(yVel > 0) {	//wenn bird springt (nach unten)
				g.fillRect(vertex.getX()+10, vertex.getY()-30, 1, 24);	
				g.fillRect(vertex.getX()+2, vertex.getY()-55, 1, 34);
				g.fillRect(vertex.getX()+19, vertex.getY()-25, 1, 11);
				g.fillRect(vertex.getX()+29, vertex.getY()-13, 1, 5);
				g.fillRect(vertex.getX()+38, vertex.getY()-60, 1, 55);
			}else if(yVel < 0) {	//wenn bird fällt (nach oben)
				g.fillRect(vertex.getX()-5, vertex.getY()+55, 1, 20);
				g.fillRect(vertex.getX()+60, vertex.getY()+75, 1, 10);	
				g.fillRect(vertex.getX()+54, vertex.getY()+24, 1, 8);
				g.fillRect(vertex.getX()+56, vertex.getY()+35, 1, 4);
				g.fillRect(vertex.getX()+45, vertex.getY()+55, 1, 24);
				g.fillRect(vertex.getX()-7, vertex.getY()+41, 1, 10);
				g.fillRect(vertex.getX()+10, vertex.getY()+69, 1, 40);
				g.fillRect(vertex.getX()+29, vertex.getY()+99, 1, 34);
				//g.fillRect(vertex.getX()+33, vertex.getY()+110, 1, 5);
				g.fillRect(vertex.getX()+19, vertex.getY()+39, 1, 11);
				g.fillRect(vertex.getX()+13, vertex.getY()+60, 1, 5);
			}
			
		}
	}

}

class Cloud implements GameObject{

	private int größe = 0;
	private int y, width;	//seitengröße eines vierecks
	private float x, v;		//vlt alle gleich schnell, dann unnötig -> dann lieber int
	private Boolean left = true;
	
	public Cloud(float x, int y, int größe) {
		this.x = x;
		this.y = y;
		this.größe = größe;
		
		setWidthRelativeToSize(größe);
	}
	
	public Cloud(float x, int y, int größe, float v) {
		this.x = x;
		this.y = y;
		this.größe = größe;
		this.v = v;
		
		setWidthRelativeToSize(größe);
	}
	
	public Cloud(float x, int y, int größe, float v, int width) {
		this.x = x;
		this.y = y;
		this.größe = größe;
		this.v = v;
		
		setWidth(width);
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
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
		setWidth(i);
	}
	
	public void setRight() {
		left = false;
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
				g.setFill(Color.WHITE);
				
				g.fillRect((int) x, y, width, width);
				g.fillRect((int) x - width, y, width, width);
				g.fillRect((int)x - width, y - width, width, width);
				g.fillRect((int)x - 2*width, y, width, width);
			}else if(größe == 1) {
				g.setFill(Color.WHITE);
				
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
				g.setFill(Color.WHITE);
				
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
	}
	
}


