package lpoo.ligue_4.main;

import java.awt.Canvas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

import lpoo.ligue_4.main.Menu;

public  class Game extends Canvas implements Runnable, MouseMotionListener, MouseListener ,KeyListener {

	public static final int WIDTH = 640, HEIGHT = 400, SCALE =  2;
	public static final int FPS = 1000/60;
	
	public static int xClick, yClick;
	public static int xPos, yPos;
	
	public static boolean newGame = false;
	public static boolean clicked = false;
	public static boolean vitP1 = false, vitP2 = false;

	public static String gameState = "Menu", playerName="";
	public static int modoJogo = 0; //aletra modo de jogo (1 e 0 ))
	public static boolean p2 = false;

	public Tabuleiro_Turbo tabuleiro_turbo;
	public Tabuleiro tabuleiro;
	public Menu menu;
	public static Player player1, player2;
	
	public BufferedImage image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);
	
	private boolean press = false;
	private int framesPress = 0;
	
	
	public Game() {
		
		
		this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE)); // dimensiona tela
		this.addMouseListener(this); //EVENTOS DO MOUSE
		this.addMouseMotionListener(this);
		
		//EVENTOS DO TECLADO
		this.setFocusable(true);
		this.addKeyListener(this);
		
		player1 = new Player(1);
		player2 = new Player(2);
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
		
		new Thread(game).start();	
		
	}
	
	public void update() {
		
		if(gameState.equals("Menu")) {
			vitP1 = vitP2 = false;
			menu.update();
			
			if(newGame) {
				if(modoJogo==0) { //Normal mode
					this.tabuleiro = new Tabuleiro();
				}
				
				else if(modoJogo==1) { //Modo Turbo on
					this.tabuleiro_turbo = new Tabuleiro_Turbo();
				}

				newGame = false;
			}
		}
		
		else if(gameState.equals("Normal")) {
			
			if(modoJogo==0) {
				this.tabuleiro.update();
			}
			
			else if(modoJogo==1) {
				this.tabuleiro_turbo.update();
			}
			
				
			if(vitP1 || vitP2) {
				//GeraRanking(vitP1, vitP2);
				gameState = "Game_Over";
				
			}
		}
		
		else if(gameState.equals("Game_Over")) {
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
		Graphics g =image.getGraphics();
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if(gameState.equals("Normal")) {
			if(modoJogo==0) {
				this.tabuleiro.render(g);
			}
			
			else if(modoJogo==1) {
				this.tabuleiro_turbo.render(g);
			}
		}
		
		
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,WIDTH*SCALE, HEIGHT*SCALE ,null);
		
		if(gameState.equals("Menu")) {
			menu.render(g);
		}
		
		else if(gameState.equals("Game_Over")) {
			
			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(new Color(0,0,0,100));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g2.setFont(new Font("arial", Font.BOLD, 28));
			g2.setColor(Color.white);
			
			String linha1 = "Fim de jogo, vitoria";
			if(vitP1) {
				linha1+= " de: "+ player1.getNome();
			}
			else if(player2.getTipo()==2) {
				linha1+= " de: "+player2.getNome();
			}
			else {
				linha1+= " do computador";
			}
			
			g2.drawString(linha1, (WIDTH*SCALE)/2-205-(linha1.length()/2), HEIGHT*SCALE/2-30);
			
			if(press) {
				g2.setColor(Color.gray);
				g2.setFont(new Font("arial", Font.BOLD, 20));
				g2.drawString(">Pressione qualquer tecla para continuar<", (WIDTH*SCALE)/2-198, HEIGHT*SCALE/2+90);
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
	
	
	public void Win() {
		
		
	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {

			Game.xClick = e.getX()/SCALE;
			Game.yClick = e.getY()/SCALE;
			Game.clicked = true;

	}
	
	@Override
	public void mouseMoved(MouseEvent f) {
		
		Game.xPos = f.getX()/SCALE;
		Game.yPos = f.getY()/SCALE;
		Game.clicked = false;
		
	}
	
	
	
	
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
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
		
		@Override
	public void keyPressed(KeyEvent e) {
			
			if(gameState.equals("Game_Over")){
				gameState = "Menu";
				player1 = new Player(1);
				player2 = new Player(2);
				menu = new Menu();
				modoJogo=0;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				if(gameState == "Menu") {
					menu.enter = true;
				}
			}
				
			else if(e.getKeyCode() == KeyEvent.VK_UP) {

				if(gameState == "Menu") {
					menu.up = true;
				}
				
			}	
				
			else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				if(gameState == "Menu") {
					menu.down = true;
				}
			}
			
			else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				if(gameState == "Menu") {
					menu.left = true;
				}
			}
			
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if(gameState == "Menu") {
					menu.right = true;
				}
			}

		}
		
		@Override
	public void keyReleased(KeyEvent e) {
		
			
		}
		
		@Override
	public void keyTyped(KeyEvent e) {
			if(menu.writeNameP1 || menu.writeNameP2) {
				char c = e.getKeyChar();
				if (Character.isAlphabetic(c) || Character.isDigit(c)) {
					if(playerName.length()<=13) {
						playerName += c;
					}
                   
                } else if (c == KeyEvent.VK_BACK_SPACE && playerName.length() > 0) {
                	playerName = playerName.substring(0, playerName.length() - 1);
                }
			}
		}
	
}
