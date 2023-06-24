package Ligue_4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Tabuleiro {

	public static final int Width = 7 , Height = 6, offSet = 3;
	public static int[][]  TABULEIRO;
	
	private int tileSize = 30;
	private int coluna, colunaSelected = -1, colunaChosen = -1;
	private boolean dentro = false, selected = false, chosen = false;
	
	public ArrayList<Ficha> fichas;
	
	public Tabuleiro() {

		TABULEIRO = new int[Width][Height] ;
		TABULEIRO[0][0] = 1;
		
		fichas = new ArrayList<>();
		
		fichas.add(new Ficha(1));
		
	}
	
	public void update() {
		
		if((Game.xPos>Game.WIDTH/offSet) && (Game.xPos<((Width*tileSize)-3+Game.WIDTH/offSet)) &&
		   (Game.yPos>=Game.HEIGHT/offSet) && (Game.yPos<=((Height*tileSize)+Game.HEIGHT/offSet))) {
			coluna = (Game.xPos/tileSize)-(Game.WIDTH/tileSize/offSet);	
			dentro = true;
			if(Game.clicked) {
				Game.clicked = false;
				if(!selected) {
					selected = true;
					colunaSelected = coluna;
				}
				else {
					if(coluna == colunaSelected) {
						colunaChosen = colunaSelected;
						colunaSelected = -1;
						chosen = true;
						selected = false;
					}
					else {
						chosen = false;
						selected = false;
					}
				}
			}
			
		}
		else {
			dentro = false;
		}				
	}
	
	public void render(Graphics g) {
		for(int x = 0; x < Width; x++) {
			for(int y = 0; y < Height; y++) {
				
				g.setColor(Color.white); //slots das fichas
				g.drawRect((x*tileSize)+Game.WIDTH/offSet, (y*tileSize)+Game.HEIGHT/offSet, tileSize, tileSize);
				
				Ficha cor = fichas.get(fichas.size() -1);			
				
				if(cor.modelo == 1) {
					
					g.setColor(Color.red);
					g.fillRect((coluna*tileSize)+Game.WIDTH/offSet + 6, Game.HEIGHT/offSet + 6, 17, 17);
				}
				
				if(cor.modelo == 2) {
					
					g.setColor(Color.cyan);
					g.fillRect((coluna*tileSize)+Game.WIDTH/offSet + 6, Game.HEIGHT/offSet + 6, 17, 17);
				}
				
				if(cor.modelo == 3) {
					
					g.setColor(Color.green);
					g.fillRect((coluna*tileSize)+Game.WIDTH/offSet + 6, Game.HEIGHT/offSet + 6, 17, 17);
				}
				
				
				if(selected) {
					g.setColor(Color.red);
					g.drawRect((colunaSelected*tileSize)+Game.WIDTH/offSet, Game.HEIGHT/offSet, tileSize, Height*tileSize);
				}
				// Aciona "Selecionado"
				if(dentro && !selected) {

						g.setColor(Color.black);
						g.drawRect((coluna*tileSize)+Game.WIDTH/offSet, Game.HEIGHT/offSet, tileSize, Height*tileSize);	
						
				}		
				
				
				
			}
		}
	}
}
