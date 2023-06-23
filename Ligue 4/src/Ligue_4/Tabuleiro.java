package Ligue_4;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Tabuleiro {

	public static final int Width = 7 , Height = 6, offSet = 3;
	public static int[][]  TABULEIRO;
	
	private int tileSize = 30;
	
	public Tabuleiro() {
		
		TABULEIRO = new int[Width][Height] ;
		
		TABULEIRO[0][0] = 1;
		
	}
	
	public void update() {
		//Ativar Mouse
		if(Game.selected && (Game.xClick != -1 && Game.yClick != -1) ) {


		}				
	}
	
	
	public void render(Graphics g) {
		for(int x = 0; x < Width; x++) {
			for(int y = 0; y < Height; y++) {
				g.setColor(Color.white); //slots das fichas
				g.drawRect((x*tileSize)+Game.WIDTH/offSet, (y*tileSize)+Game.HEIGHT/offSet, tileSize, tileSize);
				if((Game.xClick>=Game.WIDTH/offSet) && (Game.xClick<=((Width*tileSize)+Game.WIDTH/offSet)) &&
				   (Game.yClick>=Game.HEIGHT/offSet) && (Game.yClick<=((Height*tileSize)+Game.HEIGHT/offSet))) {
					int coluna = (Game.xClick/tileSize)-(Game.WIDTH/tileSize/offSet);
					g.setColor(Color.black);
					g.drawRect((coluna*tileSize)+Game.WIDTH/offSet, Game.HEIGHT/offSet, tileSize, Height*tileSize);
					
					if(Game.selected == false) {
						Game.selected = true;
						System.out.print("\n\n\n");
						System.out.println(Game.WIDTH/tileSize/offSet);
	
					}
					
				}
							
				
			}
		}
		
	}
}
