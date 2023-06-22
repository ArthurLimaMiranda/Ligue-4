package Ligue_4;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Tabuleiro {

	public static final int WIDTH = 6 , HEIGHT = 6;
	public static int[][]  TABULEIRO;
	
	public static int Slot_Vazio =  0,FICHA_p1 =  1,FICHA_p2 =  2, FICHA_pc = 3 ;
	
	public Tabuleiro() {
		
		TABULEIRO = new int[WIDTH][HEIGHT] ;
		
		TABULEIRO[0][0] = 1; // ficha Player 1 começa no priemiro slot
		
		/*for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				TABULEIRO[x][y] = new Random().nextInt(3);
		
	}
	}*/
	}
	
	public void render(Graphics g) {
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				g.setColor(Color.white); //slots das fichas
				g.drawRect(x*48, y*48, 48,48);
				
				int ficha = TABULEIRO[x][y];
				
				if(ficha == FICHA_pc) {
					g.setColor(Color.green);
					g.fillRect(x*48 + 12, y*48 + 12, 25, 25);
				}
				if(ficha == FICHA_p1) {
					g.setColor(Color.red);
					g.fillRect(x*48 + 12, y*48 + 12, 25, 25);
				}
				if(ficha == FICHA_p2) {
					g.setColor(Color.cyan);
					g.fillRect(x*48 + 12, y*48 + 12, 25, 25);
				}
				if(ficha == Slot_Vazio) {
					g.setColor(Color.blue);
					g.fillRect(x*48 + 12, y*48 + 12, 25, 25);
				}
				
				if(Game.selected) {
					//Posição de x e y no tabuleiro
					int Pos_x = Game.X_inicial/48;
					int Pos_y = Game.Y_inicial/48;
					//Desenha um retângulo
					g.setColor(Color.BLACK);
					g.drawRect(Pos_x*48,Pos_y*48,48,48); 
				}
				
				
			}
		}
		
	}
}
