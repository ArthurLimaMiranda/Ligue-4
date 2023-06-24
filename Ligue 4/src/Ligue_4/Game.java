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

	public static final int WIDTH = 640, HEIGHT = 400, SCALE =  2;
	public static final int FPS = 1000/60;
	
	public static int xClick, yClick;
	public static int xPos, yPos;
	public static  int nClicks = 0;
	
	public static boolean clicked = false;

	
	public Tabuleiro tabuleiro;
	
	public BufferedImage image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);

	
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
		tabuleiro.update();		
			
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
