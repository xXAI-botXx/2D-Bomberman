package com.tobia.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


//existiert nur für eine Runde (bzw. wird sie dann neugestartet)
public class Control extends Application{	

		//Variablen
		private final int width = 800, height = 600;
		
		private ArrayList<GameObject> renderables = new ArrayList<GameObject>();
		private ArrayList<GameObject> updateables = new ArrayList<GameObject>();
		
		private Player1 p1;
		private Player2 p2;
		
		private Boolean end = false, ee = false;
		
		private Button character1;
		private Button character2;
		private Button map1;
		private Button map2;
		private Button map3;
		private Button map4;
		private Button map5;
		private Button map6;
		private Button map7;
		private Button map8;
		private Button map9;
		private Button next;
		private Button next2;
		private Button bStart;
		private Button bCredits;
		private Button bRestart;
		private Button bBack;
		private Button bCharacterBack;
		private Button bMapBack;
		private Button bFaster;
		private Button bSlower;
		private Button bPause;
		private Button bPlay;
		private Button bE;
		private Label bomberman;
		private Label d2;
		private Label lowBudget;
		
		private Circle p1c1;
		private Circle p1c2;
		private Circle p2c1;
		private Circle p2c2;
		
		private Circle cMap;
		
		/*private File resource1;
		private URL resource;
		private Media media;
		private MediaPlayer checkSound;
		private MediaPlayer blockSound;*/ //-> funktioniert, aber nur für mp3
		
		private MusicManager musicManager;
		private Thread musicThread;
		
		private Scene startScene;
		private Scene creditScene;
		private Scene characterScene;
		private Scene mapScene;	//Mapwahl
		private Scene gameScene;	//Game
		
		private Gameloop game;
		private CreditLoop creditLoop;
		private eC3 eC3;
		
		
		private Canvas c;
		private Canvas c2;
		private Canvas canvas;
		private Control control;
		
		private Image img;
		//private Image img2;
		private Image img3;
		private Image img4;
		private Image img5;
		
		//Konstruktor -> Startet und steuert das game
		public Control() {
			p1 = new Player1();
			p2 = new Player2();
			
			control = this;
		}
		
		@Override
		public void start(Stage stage1) throws Exception {
			//initiierung der Szenen und deren Komponenten (unteranderem auch ein KeyListener)
			init();
			initScene1(stage1);
			initScene2(stage1);
			initScene3(stage1);
			initCreditScene(stage1); 
			
			//Start Szene
			
			//Layout
			Pane rootReal = new Pane();
			GridPane root = new GridPane();
			root.setVgap(25);
			root.setHgap(15);
			root.setAlignment(Pos.BASELINE_CENTER);
			
			//Elemente
			canvas = new Canvas(800, 600);
			GraphicsContext g = canvas.getGraphicsContext2D();
			g.drawImage(img, 35, 150);
			//g.drawImage(img2, 450, 5);
			g.drawImage(img3, 650, 65);
			g.drawImage(img4, 300, 200);
			g.drawImage(img5, 530, 450);
			
			bStart = new Button("Start");
			bStart.setScaleX(3);
			bStart.setScaleY(3);
			
			bStart.setOnMouseClicked(new EventHandler<MouseEvent>() {	//anonyme innere Klasse

				@Override
				public void handle(MouseEvent arg0) {
					stage1.setScene(characterScene);
					stage1.setTitle("2D Bomberman - character choose");
					stage1.show();
					
					musicManager.playChoose();
				}
				
			});
			
			bCredits = new Button("Credits");
			bCredits.setScaleX(3);
			bCredits.setScaleY(3);
			
			bCredits.setOnMouseClicked(new EventHandler<MouseEvent>() {	//anonyme innere Klasse

				@Override
				public void handle(MouseEvent arg0) {
					stage1.setScene(creditScene);
					stage1.setTitle("2D Bomberman - Credits");
					creditLoop = new CreditLoop(c, control);
					creditLoop.start();
					
					musicManager.playCredits();
					
					stage1.show();
				}
				
			});
			
			bomberman = new Label("Bomberman!");
			bomberman.setFont(new Font("Arial", 70));
			bomberman.setTextFill(Color.RED);
			
			d2 = new Label("2D");
			d2.setFont(new Font("Arial", 70));
			d2.setTextFill(Color.RED);
			
			lowBudget = new Label("low budget*");
			lowBudget.setFont(new Font("Arial", 15));
			
			//Elemente zum Layout hinzufügen
			root.add(bomberman, 2, 0);
			root.add(d2, 0, 0);
			root.add(lowBudget, 1, 0);

			rootReal.getChildren().add(canvas);
			rootReal.getChildren().add(bStart);
			rootReal.getChildren().add(bCredits);
			rootReal.getChildren().add(root);
			
			root.getTransforms().add(new Translate(90, 50));
			bStart.getTransforms().add(new Translate(117, 80));
			bCredits.getTransforms().add(new Translate(115, 140));
			
			
			//Musik starten
			musicManager.playMain();
			
			//Scene
			startScene = new Scene(rootReal, width, height);
			
			//falls geschlossen -> sonst wird es nicht richtig beendet (in manchen Szenarios)
			stage1.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent arg0) {
					if(stage1.getScene() == gameScene) {
						p1.close();
						p2.close();
					}
				}
			});
			
			stage1.setScene(startScene);
			stage1.setTitle("2D Bomberman - Menü");
			stage1.setResizable(false);
			stage1.show();
		}
	
		
		public void init() {
			updateables.add(p1);
			updateables.add(p2);
			
			renderables.add(p1);
			renderables.add(p2);
		
			musicManager = new MusicManager();
			musicThread = new Thread(musicManager);
			musicThread.setName("MUSIC_THREAD");
			
			try {
				img = new Image(new FileInputStream("src/Images/pixelBomberman.png"));
				//img2 = new Image(new FileInputStream("src/Images/flappyBird.png"));
				img3 = new Image(new FileInputStream("src/Images/boom.png"));
				img4 = new Image(new FileInputStream("src/Images/smoke.png"));
				img5 = new Image(new FileInputStream("src/Images/walkingBomb.png"));
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		public void initScene1(Stage stage1) {	//charakterwahl
			//Layout
			GridPane root1 = new GridPane();
			
			Pane root1next = new Pane();
			
			//elements
			character1 = new Button("Character1"/*bild*/);
			character1.setFocusTraversable(false);
			character1.setOnMouseClicked(new EventHandler() {
				@Override
				public void handle(Event arg0) {
					if(!p1.getCharacterCheck()) {
						p1.setCharacter(1);
						p1c1.setVisible(true);
						musicManager.playCheckSound();
						//plus sound und icon
					}else if(!p2.getCharacterCheck()) {
						p2.setCharacter(1);
						p2c1.setVisible(true);
						musicManager.playCheckSound();
						//plus sound und icon
					}else {
						musicManager.playBlockSound();
						//dumpfer sound
					}
				}
			});
			
			character2 = new Button("Character2");
			character2.setFocusTraversable(false);
			character2.setOnMouseClicked(new EventHandler() {
				@Override
				public void handle(Event arg0) {
					if(!p1.getCharacterCheck()) {
						p1.setCharacter(2);
						p1c2.setVisible(true);
						musicManager.playCheckSound();
					}else if(!p2.getCharacterCheck()) {
						p2.setCharacter(2);
						p2c2.setVisible(true);
						musicManager.playCheckSound();
					}else {
						musicManager.playBlockSound();
						//dumpfer sound
					}
				}
			});
			
			next = new Button("Continue");
			next.setLayoutX(width*0.8);
			next.setLayoutY(height*0.8);
			next.setScaleX(2);
			next.setScaleY(2);
			next.setFocusTraversable(false);
			next.setOnMouseClicked(new EventHandler() {	//anonyme innere Klasse
				@Override
				public void handle(Event arg0) {
					if(p1.getCharacterCheck() && p2.getCharacterCheck()) {
						stage1.setScene(mapScene);
						stage1.setTitle("2D Bomberman - map choose");
						stage1.show();
						musicManager.playCheckSound();
					}else {
						musicManager.playBlockSound();
					}
				}
			});
			
			Label characterChoose = new Label("Choose your Character");
			characterChoose.getTransforms().add(new Translate(width/2-150, characterChoose.getLayoutY()+25));
			characterChoose.setFont(Font.font("Cambria", 32));
			
			p1c1 = new Circle(80, 145, 10, Color.BLUE);
			p1c1.setVisible(false);
			
			p1c2 = new Circle(170, 145, 10, Color.BLUE);
			p1c2.setVisible(false);
			
			p2c1 = new Circle(110, 145, 10, Color.CADETBLUE);
			p2c1.setVisible(false);
			
			p2c2 = new Circle(200, 145, 10, Color.CADETBLUE);
			p2c2.setVisible(false);
			
			bCharacterBack = new Button("<-");
			bCharacterBack.setFocusTraversable(false);
			bCharacterBack.setLayoutX(35);
			bCharacterBack.setLayoutY(50);
			bCharacterBack.setOnMouseClicked(new EventHandler() {
				@Override
				public void handle(Event arg0) {
					if(p2.getCharacterCheck()) {
						if(p2.getCharacter() == 1) {
							p2c1.setVisible(false);
							p2.setNoCharacter();
						}else if(p2.getCharacter() == 2) {
							p2c2.setVisible(false);
							p2.setNoCharacter();
						}
						
						musicManager.playCheckSound();
						//plus sound und icon
					}else if(p1.getCharacterCheck()) {
						if(p1.getCharacter() == 1) {
							p1c1.setVisible(false);
							p1.setNoCharacter();
						}else if(p1.getCharacter() == 2) {
							p1c2.setVisible(false);
							p1.setNoCharacter();
						}
						
						musicManager.playCheckSound();
						//plus sound und icon
					} else {
						musicManager.playMain();
                		
                		stage1.setScene(startScene);
                		stage1.setTitle("2D Bomberman - Menü");
                		stage1.show();
					}
				}
			});
			
			//adden
			root1.add(character1, 0, 0);
			root1.add(character2, 1, 0);
			root1next.getChildren().add(p1c1);
			root1next.getChildren().add(p1c2);
			root1next.getChildren().add(p2c1);
			root1next.getChildren().add(p2c2);
			root1next.getChildren().add(next);
			root1next.getChildren().add(root1);
			root1next.getChildren().add(characterChoose);
			root1next.getChildren().add(bCharacterBack);
			
			root1.getTransforms().add(new Translate(50, 150));
			
			
			characterScene = new Scene(root1next, width, height);
			
		}
		
		public void initScene2(Stage stage1) {	//map-wahl
			//Layout
			GridPane root2 = new GridPane();
			Pane root2next = new Pane();
			
			//elements
			map1 = new Button("Map1");
			map1.setFocusTraversable(false);
			map1.setOnMouseClicked(new EventHandler() {
				@Override
				public void handle(Event arg0) {
					p1.setMap(0);
					cMap.setLayoutX(map1.getLayoutX()+map1.getWidth()+10);
					cMap.setVisible(true);
					musicManager.playCheckSound();
				}
			});
			
			map2 = new Button("Map2");
			map2.setFocusTraversable(false);
			map2.setOnMouseClicked(new EventHandler() {
				@Override
				public void handle(Event arg0) {
					p1.setMap(1);
					cMap.setLayoutX(map2.getLayoutX()+map2.getWidth()+10);
					cMap.setVisible(true);;
					musicManager.playCheckSound();
				}
			});
			
			map3 = new Button("Map3");
			map3.setFocusTraversable(false);
			map3.setOnMouseClicked(new EventHandler() {
				@Override
				public void handle(Event arg0) {
					p1.setMap(2);
					cMap.setLayoutX(map3.getLayoutX()+map3.getWidth()+10);
					cMap.setVisible(true);
					musicManager.playCheckSound();
				}
			});
			
			map4 = new Button("Map4");
			map4.setFocusTraversable(false);
			map4.setOnMouseClicked(new EventHandler() {
				@Override
				public void handle(Event arg0) {
					p1.setMap(3);
					cMap.setLayoutX(map4.getLayoutX()+map4.getWidth()+10);
					cMap.setVisible(true);
					musicManager.playCheckSound();
						
				}
			});
			
			map5 = new Button("Map5");
			map5.setFocusTraversable(false);
			map5.setOnMouseClicked(new EventHandler() {
				@Override
				public void handle(Event arg0) {
					p1.setMap(4);
					cMap.setLayoutX(map5.getLayoutX()+map5.getWidth()+10);
					cMap.setVisible(true);
					musicManager.playCheckSound();
				}
			});
			
			map6 = new Button("Map6");
			map6.setFocusTraversable(false);
			map6.setOnMouseClicked(new EventHandler() {
				@Override
				public void handle(Event arg0) {
					p1.setMap(5);
					cMap.setLayoutX(map6.getLayoutX()+map6.getWidth()+10);
					cMap.setVisible(true);
					musicManager.playCheckSound();
				}
			});
			
			map7 = new Button("Map7");
			map7.setFocusTraversable(false);
			map7.setOnMouseClicked(new EventHandler() {
				@Override
				public void handle(Event arg0) {
					p1.setMap(6);
					cMap.setLayoutX(map7.getLayoutX()+map7.getWidth()+10);
					cMap.setVisible(true);
					musicManager.playCheckSound();
				}
			});
			
			map8 = new Button("Map8");
			map8.setFocusTraversable(false);
			map8.setOnMouseClicked(new EventHandler() {
				@Override
				public void handle(Event arg0) {
					p1.setMap(7);
					cMap.setLayoutX(map8.getLayoutX()+map8.getWidth()+10);
					cMap.setVisible(true);
					musicManager.playCheckSound();
				}
			});
			
			map9 = new Button("Map9");
			map9.setFocusTraversable(false);
			map9.setOnMouseClicked(new EventHandler() {
				@Override
				public void handle(Event arg0) {
					p1.setMap(8);
					cMap.setLayoutX(map9.getLayoutX()+map9.getWidth()+10);
					cMap.setVisible(true);
					musicManager.playCheckSound();
				}
			});
			
			
			next2 = new Button("Continue");
			next2.setLayoutX(width*0.8);
			next2.setLayoutY(height*0.8);
			next2.setScaleX(2);
			next2.setScaleY(2);
			next2.setFocusTraversable(false);
			next2.setOnMouseClicked(new EventHandler() {	//anonyme innere Klasse
				@Override
				public void handle(Event arg0) {
					if(p1.getMapCheck()) {
						game = new Gameloop(p1, p2, c2, control);
						//game.gameRun();
						game.start();
						stage1.setScene(gameScene);
						stage1.setTitle("2D Bomberman - Game");
						musicManager.playBattle();
						stage1.show(); 
					}else {
						musicManager.playBlockSound();
					}
				}
			});
			
			Label mapChoose = new Label("Choose your Map");
			mapChoose.getTransforms().add(new Translate(width/2-125, mapChoose.getLayoutY()+25));
			mapChoose.setFont(Font.font("Cambria", 32));
			
			cMap = new Circle(0, 145, 10, Color.HOTPINK);
			cMap.setVisible(false);
			
			
			bMapBack = new Button("<-");
			bMapBack.setFocusTraversable(false);
			bMapBack.setLayoutX(35);
			bMapBack.setLayoutY(50);
			bMapBack.setOnMouseClicked(new EventHandler() {
				@Override
				public void handle(Event arg0) {
					if(p1.getMapCheck()) {
						cMap.setVisible(false);
						p1.setNoMap();
					}
					
					stage1.setTitle("2D Bomberman - character choose");
					stage1.setScene(characterScene);
					stage1.show();
				}
			});
			
			//adden
			root2.add(map1, 0, 0);
			root2.add(map2, 1, 0);
			root2.add(map3, 2, 0);
			root2.add(map4, 3, 0);
			root2.add(map5, 4, 0);
			root2.add(map6, 5, 0);
			root2.add(map7, 6, 0);
			root2.add(map8, 7, 0);
			root2.add(map9, 8, 0);
			root2next.getChildren().add(root2);
			root2next.getChildren().addAll(next2);
			root2next.getChildren().add(mapChoose);
			root2next.getChildren().add(bMapBack);
			root2next.getChildren().add(cMap);
			
			root2.getTransforms().add(new Translate(50, 150));
			
			mapScene = new Scene(root2next, width, height);
					
		}
		
		public void initScene3(Stage stage1) {	//Game
			//Layout
			Pane root3 = new Pane();
			
			gameScene = new Scene(root3, width, height);
			
			//elements
			c2 = new Canvas(width, height);

			//add elemnts to layout
			root3.getChildren().add(c2);
			
			//könnte man auch weiter unten einzeln erzeugen
			gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {	//event wird bei der szene abgehört und darauf reagiert
				
				@Override
				public void handle(KeyEvent event) {
					  switch (event.getCode()) {
						//player 1
		                  case W:    p1.up();
		                  	break;
		                  case S:  p1.down(); 
		                  	break;
		                  case A:	p1.left(); 
		                  	break;
		                  case D:	p1.right(); 
		                  	break;
		                  case E:	p1.bomb();	
		                  	break;
		               
	                  	//player2
		                  case UP:    p2.up();
		                  	break;
		                  case DOWN:  p2.down(); 
		                  	break;
		                  case LEFT: p2.left();
		                  	break;
		                  case RIGHT: p2.right();
		                  	break;
		                  case PAGE_DOWN: p2.bomb();
		                	  break;
		                	  
		                //end
		                  case ESCAPE:	
		                	  if(end == true) {
		                		  reload();
		                	  }
		                	  break;
		                  case SPACE:	
		                	  if(end == true) {
		                		p1.restart();
		                		p2.restart();
		                		//game.restart();
		                		game.stop();	//key
		                		restart();
		                		
		                		musicManager.playMain();
		                		
		                		stage1.setScene(startScene);
		                		stage1.setTitle("2D Bomberman - Menü");
		                		stage1.show();
		                		
		                		end = false;
		                	  }
		                	  break;
		              }
				}
			});
	
		}
		
		
		public void initCreditScene(Stage stage1) {	//Credits
			//Layout
			Pane root4 = new Pane();
			
			//Elements
			c = new Canvas(width, height);
			
			bBack = new Button("<-");
			bBack.setFocusTraversable(false);
			bBack.setLayoutX(35);
			bBack.setLayoutY(50);
			bBack.setOnMouseClicked(new EventHandler() {
				@Override
				public void handle(Event arg0) {
					creditLoop.stop();
					eeStop();
					musicManager.playCheckSound();
					
					musicManager.playMain();
					
					stage1.setScene(startScene);
					stage1.show();
				}
			});
			
			bRestart = new Button("restart");
			bRestart.setFocusTraversable(false);
			bRestart.setLayoutX(700);
			bRestart.setLayoutY(50);
			bRestart.setOnMouseClicked(new EventHandler() {
				@Override
				public void handle(Event arg0) {
					creditLoop.restart();
					musicManager.playCheckSound();
				}
			});
			
			bFaster = new Button(">>");
			bFaster.setFocusTraversable(false);
			bFaster.setLayoutX(700);
			bFaster.setLayoutY(100);
			bFaster.setOnMouseClicked(new EventHandler() {
				@Override
				public void handle(Event arg0) {
					creditLoop.faster();
					musicManager.playCheckSound();
				}
			});
			
			bSlower = new Button("<<");
			bSlower.setFocusTraversable(false);
			bSlower.setLayoutX(750);
			bSlower.setLayoutY(100);
			bSlower.setOnMouseClicked(new EventHandler() {
				@Override
				public void handle(Event arg0) {
					creditLoop.slower();
					musicManager.playCheckSound();
				}
			});
			
			bPause = new Button("||");
			bPause.setFocusTraversable(false);
			bPause.setLayoutX(700);
			bPause.setLayoutY(150);
			bPause.setOnMouseClicked(new EventHandler() {
				@Override
				public void handle(Event arg0) {
					creditLoop.pause();
					musicManager.playCheckSound();
				}
			});
			
			bPlay = new Button(">");
			bPlay.setFocusTraversable(false);
			bPlay.setLayoutX(750);
			bPlay.setLayoutY(150);
			bPlay.setOnMouseClicked(new EventHandler() {
				@Override
				public void handle(Event arg0) {
					creditLoop.play();
					musicManager.playCheckSound();
				}
			});
			
			bE = new Button("Surprise");
			bE.setFocusTraversable(false);
			bE.setLayoutX(width/2 - 30);
			bE.setLayoutY(height/2 - bE.getHeight()/2);
			bE.setOnMouseClicked(new EventHandler() {
				@Override
				public void handle(Event arg0) {
					if(ee) {
					musicManager.playCheckSound();
					creditLoop.stop();
					
					bE.setVisible(false);
					bPlay.setVisible(false);
					bPause.setVisible(false);
					bSlower.setVisible(false);
					bFaster.setVisible(false);
					bRestart.setVisible(false);
					bBack.setVisible(false);
					
					eC3 = new eC3(c, control);
					eC3.start();
					}
				}
			});
			bE.setVisible(false);
			
			//adden
			root4.getChildren().add(c);
			root4.getChildren().add(bRestart);
			root4.getChildren().add(bBack);
			root4.getChildren().add(bFaster);
			root4.getChildren().add(bSlower);
			root4.getChildren().add(bPause);
			root4.getChildren().add(bPlay);
			root4.getChildren().add(bE);
			
			creditScene = new Scene(root4, 800, 600);
		}
		
		public void eeStart() {
				musicManager.playCredits2();
				ee = true;
				bE.setVisible(true);
		}
		
		public void eeStop() {
			ee = false;
			bE.setVisible(false);
			
			musicManager.playCredits();
		}
		
		public void stopEE() {
			eC3.stop();
			creditLoop.start();
			
			bE.setVisible(true);
			bPlay.setVisible(true);
			bPause.setVisible(true);
			bSlower.setVisible(true);
			bFaster.setVisible(true);
			bRestart.setVisible(true);
			bBack.setVisible(true);
		}
		
		public void endGameMusic() {
			end = true;
			
			musicManager.playGameover();
		}
		
		public void restart() {
			p1c1.setVisible(false);
			p1c2.setVisible(false);
			p2c1.setVisible(false);
			p2c2.setVisible(false);
			
			cMap.setVisible(false);
		}
		
		public void reload() {	//alternative restart möglichkeit
			try {
				new ProcessBuilder("start.bat").start();
				System.exit(0);
			}catch(IOException e) {
				System.err.println("Can not find .bat file in dist folder");
			}
		}
		

		public static void main(String[] args) {
			launch(args);	//startet automatisch die start(Stage stage1) Methode
		}
	}
