package lpoo.ligue_4.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

import lpoo.ligue_4.board.Tabuleiro;
import lpoo.ligue_4.board.TabuleiroTurbo;
import lpoo.ligue_4.board.TabuleiroTurboMaluco;
import lpoo.ligue_4.exceptions.LimiteNome;
import lpoo.ligue_4.grafs.Cenario;
import lpoo.ligue_4.grafs.Spritesheet;
import lpoo.ligue_4.ranking.Ranking;
import lpoo.ligue_4.sound_track.SoundEffects;
import lpoo.ligue_4.sound_track.SoundGame;

public  class Game extends Canvas implements Runnable, MouseMotionListener, MouseListener ,KeyListener {

	public static final int WIDTH = 640, HEIGHT = 400, SCALE =  2;
	public static final int FPS = 1000/60;
	
	public static int XCLICK, YCLICK;
	public static int XPOS, YPOS;
	
	public static boolean NEWGAME = false;
	public static boolean CLICKED = false;
	public static boolean VITP1 = false, VITP2 = false, EMPATE = false;

	public static String GAMESTART = "Menu", PLAYERNAME="";
	public static int MODOJOGO = 0;
	public static boolean P2 = false, DIFICIL = false;

	public static Spritesheet spritesheetGame;
	
	public static Cenario cenario;
	public TabuleiroTurboMaluco tabuleiro_maluco;
	public TabuleiroTurbo tabuleiro_turbo;
	public Tabuleiro tabuleiro;
	public Menu menu;
	public static Ranking ranking;
	public static Player player1, player2;
	
	public BufferedImage image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);
	
	private boolean press = false;
	private int framesPress = 0;
	SoundEffects sounds = new SoundEffects();
	protected String Sound_Win = "res/bright-notifications-151766 (mp3cut.net).mp3";
	protected static String Sound_Gamer = "res/Different Names.mp3";
	
	static SoundGame soundgame;
	
	
	
	public Game() {
		
		
		
		this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE)); // dimensiona tela
		this.addMouseListener(this); //EVENTOS DO MOUSE
		this.addMouseMotionListener(this);
		
		//EVENTOS DO TECLADO
		this.setFocusable(true);
		this.addKeyListener(this);
		
		spritesheetGame = new Spritesheet("res/spritesheet.png");
		cenario = new Cenario("res/map.png");
		player1 = new Player(1);
		player2 = new Player(2);
		ranking = new Ranking();
		
		try {
			ranking.LerRanking();
		} catch (IOException e) {e.printStackTrace();}
		
		menu = new Menu();
		
		
	}

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("A busca por 4 conexões");
		Game game = new Game();
		frame.add(game);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		soundgame = new SoundGame(Sound_Gamer);
		
		new Thread(game).start();	
		
		soundgame.play();
		
		
	}
	
	public void update() {
		
		if(GAMESTART.equals("Menu")) {
			VITP1 = VITP2 = false;
			try {
				menu.update();
			} catch (LimiteNome e) {e.printStackTrace();}
			
			if(NEWGAME) {
				if(MODOJOGO==0) { //Normal mode
					this.tabuleiro = new Tabuleiro();
				}
				
				else if(MODOJOGO==1) { //Modo Turbo on
					this.tabuleiro_turbo = new TabuleiroTurbo();
				}
				else if(MODOJOGO == 2) {//Modo Turbo Maluco
					this.tabuleiro_maluco = new TabuleiroTurboMaluco();
				}

				NEWGAME = false;
			}
		}
		
		else if(GAMESTART.equals("Normal")) {
			
			if(MODOJOGO==0) {
				this.tabuleiro.update();
			}
			
			else if(MODOJOGO==1) {
				this.tabuleiro_turbo.update();
			}
			
			else if(MODOJOGO== 2) {
				this.tabuleiro_maluco.update();
			}
				
			if(VITP1 || VITP2 || EMPATE) {
				GAMESTART = "Game_Over";
				try {
					ranking.geraRanking();
				} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
				sounds.playMP3WithTimeout(Sound_Win, 1000);
				
			}
		}
		
		else if(GAMESTART.equals("Game_Over")) {		
			framesPress++;
			if(framesPress>=35) {
				framesPress=0;
				press = !press;
				
			}
		}			
	}
	
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}	
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if(GAMESTART.equals("Normal")) {
			cenario.render(g);
			if(MODOJOGO==0) {
				this.tabuleiro.render(g);
			}			
			else if(MODOJOGO==1) {
				this.tabuleiro_turbo.render(g);
			}
			else if(MODOJOGO== 2) {
				this.tabuleiro_maluco.render(g);
			}
		}
		
		
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,WIDTH*SCALE, HEIGHT*SCALE ,null);
		
		if(GAMESTART.equals("Menu")) {
			menu.render(g);
		}
		
		else if(GAMESTART.equals("Game_Over")) {
			Graphics2D g2 = (Graphics2D)g;
			if(MODOJOGO==0) {
				this.tabuleiro.render(g2);
			}
			else if(MODOJOGO==1) {
				this.tabuleiro_turbo.render(g2);
			}
			else if(MODOJOGO== 2) {
				this.tabuleiro_maluco.render(g2);
			}
			g2.setColor(new Color(0,0,0,127));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g2.setFont(new Font("arial", Font.BOLD, 28));
			g2.setColor(Color.white);
			
			String linha1 = "Fim de jogo,";
			String linha2 = "";
			if(VITP1) {
				linha1+= " vitoria de: ";
				linha2+= player1.getNome();
			}
			else if(player2.getTipo()==2) {
				linha1+= " vitoria de: ";
				linha2+= player2.getNome();
			}
			else if(player2.getTipo()==3) {
				linha1+= " vitoria do computador";
			}
			else {
				linha1+= " empate";
			}
			
			g2.drawString(linha1, (WIDTH*SCALE)/2-140-(linha1.length()/2), HEIGHT*SCALE/2-30);
			g2.drawString(linha2, (WIDTH*SCALE)/2-140-(linha1.length()/2), HEIGHT*SCALE/2);
			
			if(press) {
				g2.setColor(Color.gray);
				g2.setFont(new Font("arial", Font.BOLD, 20));
				g2.drawString(">Pressione qualquer tecla para continuar<", (WIDTH*SCALE)/2-198, HEIGHT*SCALE/2+60);
			}
		}
		
		bs.show();
	}
	
	
	public void run () {
		
		while(true) {
			update();
			render();
			try {
				Thread.sleep(FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void mouseClicked(MouseEvent e) {

			Game.XCLICK = e.getX()/SCALE;
			Game.YCLICK = e.getY()/SCALE;
			Game.CLICKED = true;

	}
	

	public void mouseMoved(MouseEvent f) {
		
		Game.XPOS = f.getX()/SCALE;
		Game.YPOS = f.getY()/SCALE;
		Game.CLICKED = false;
		
	}
	
	
	
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}
	
	
	public void mouseReleased(MouseEvent arg0) {

	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	//EVENTOS TECLADO
		
	
	public void keyPressed(KeyEvent e) {
			
			if(GAMESTART.equals("Game_Over")){
				GAMESTART = "Menu";
				player1 = new Player(1);
				player2 = new Player(2);
				menu = new Menu();
				MODOJOGO=0;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				if(GAMESTART == "Menu") {
					menu.enter = true;
				}
			}
				
			else if(e.getKeyCode() == KeyEvent.VK_UP) {

				if(GAMESTART == "Menu") {
					menu.up = true;
				}
				
			}	
				
			else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				if(GAMESTART == "Menu") {
					menu.down = true;
				}
			}
			
			else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				if(GAMESTART == "Menu") {
					menu.left = true;
				}
			}
			
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if(GAMESTART == "Menu") {
					menu.right = true;
				}
			}

		}
		
		
	public void keyReleased(KeyEvent e) {
		
			
		}
		
	public void keyTyped(KeyEvent e) {
			if(menu.writeNameP1 || menu.writeNameP2) {
				char c = e.getKeyChar();
				if (Character.isAlphabetic(c) || Character.isDigit(c)) {
					if(PLAYERNAME.length()<=12) {
						PLAYERNAME += c;
					}
                   
                } else if (c == KeyEvent.VK_BACK_SPACE && PLAYERNAME.length() > 0) {
                	PLAYERNAME = PLAYERNAME.substring(0, PLAYERNAME.length() - 1);
                }
			}
		}
	
}
