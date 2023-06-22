package Ligue_4;

import java.awt.Canvas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public  class Game extends Canvas implements Runnable, MouseMotionListener, MouseListener ,KeyListener {

	public static final int WIDTH =288, HEIGHT = 288;//Tamanho janela
	public static final int SCALE = 2;//Escala 
	
	public static final int FPS = 1000/60;
	
	public Tabuleiro tabuleiro;
	
	
	public BufferedImage image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);
	
	public static boolean selected = false;
	
	public static int X_inicial = 0, Y_inicial = 0;
	public static int X_final = -1, Y_final = -1;
	
	public Game() {
		
		//EVENTOS DO MOUSE
		this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		//EVENTOS DO TECLADO
		this.setFocusable(true);
		this.addKeyListener(this);
		
		tabuleiro = new Tabuleiro();
	}
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Ligue 4");
		Game game = new Game();
		frame.add(game);
		frame.setResizable(false); // fixar tamanho janela
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		new Thread(game).start();	
		
	}
	
	public void update() {
		//Ativar Mouse
		if(Game.selected && (Game.X_final != -1 && Game.Y_final != -1) ) {
			int Pos_x0 = Game.X_inicial/48;
			int Pos_y0 = Game.Y_inicial/48;
			
			int Pos_xf = Game.X_final/48;
			int Pos_yf = Game.Y_final/48;
			
			if((Pos_xf == Pos_x0 + 1 || Pos_xf == Pos_x0 - 1)) {		
					System.out.println("Slot Permitido!");
					
					// Move a peça de coluna
					
					int Pos_inicial = Tabuleiro.TABULEIRO[Pos_x0][Pos_y0];
					int Pos_next = Tabuleiro.TABULEIRO[Pos_xf][Pos_yf];
					
					
					Tabuleiro.TABULEIRO[Pos_xf][Pos_yf] = Pos_inicial; 
					Tabuleiro.TABULEIRO[Pos_x0][Pos_y0] = Pos_next;
	
					
					Game.X_final = -1;
					Game.Y_final = -1;
					Game.selected = false;								
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
		//Renderização(início)
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		tabuleiro.render(g);
		//Renderização(fim)
		
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,WIDTH*SCALE, HEIGHT*SCALE ,null);
		
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
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(Game.selected == false) {
			Game.selected = true;
			//pega a pósição de x e y na janela em tempo real
			Game.X_inicial = e.getX()/2;
			Game.Y_inicial = e.getY()/2;
		}else {
			Game.X_final = e.getX()/2;
			Game.Y_final = e.getY()/2;
		}
		
		if(e.getClickCount() == 2) {
			// BOTAR ANIMAÇÃO DE QUEDA AQUI!!!!!!!!!!!!!!!!
			System.out.println("QUEDA");
			Game.X_inicial = e.getX()/2;
			Game.Y_inicial = e.getY()/2;
			
			
		}
		
	}
	
	//EVENTOS MOUSE
	
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
		// TODO Auto-generated method stub
		
	}
	
	
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	//EVENTOS TECLADO
		
		@Override
	public void keyPressed(KeyEvent e) {
			
			 													
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				
				Game.X_final = Game.X_inicial/48 -1;
							
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				
				Game.X_final = Game.X_inicial/48 + 1;
			}
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				
				System.out.println("QUEDA");
			}
			

			
		}
		
		@Override
	public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
	public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	
}
