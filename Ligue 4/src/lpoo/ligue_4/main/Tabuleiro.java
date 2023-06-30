package lpoo.ligue_4.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Tabuleiro {

	public static final int Width = 7 , Height = 6, offSet = 3;
	public static int[][]  TABULEIRO;
	
	private int tileSize = 30, dropSet = 7+tileSize, dropTo;
	private float dropping;
	private int coluna, colunaSelected = -1, colunaChosen = -1;
	private boolean dentro = false, selected = false, chosen = false, drop = false;
	
	private ArrayList<Ficha> fichas;
	private int cor;
	
	public Tabuleiro() {

		TABULEIRO = new int[Width][Height] ;

		fichas = new ArrayList<>();
		fichas.add(new Ficha(1));
		cor = fichas.get(fichas.size() -1).modelo;
		
	}
	
	public void update() {
		
		if(!drop) {
			//Checa se o mouse se encontra dentro do tabuleiro e em qual coluna esta em cima
			if((Game.xPos>Game.WIDTH/offSet) && (Game.xPos<((Width*tileSize)-3+Game.WIDTH/offSet)) &&
			   (Game.yPos>=Game.HEIGHT/offSet) && (Game.yPos<=((Height*tileSize)+Game.HEIGHT/offSet))) {
				coluna = (Game.xPos/tileSize)-(Game.WIDTH/tileSize/offSet);	
				dentro = true;
				
				//Seleciona a coluna desejada
				if(Game.clicked) {
					Game.clicked = false;
					if(!selected) {
						selected = true;
						colunaSelected = coluna;
					}
					
					//Confirma a coluna, ativando a animação de queda ou cancela a seleção
					else {
						if(coluna == colunaSelected) {
							colunaChosen = colunaSelected;
							colunaSelected = -1;
							chosen = true;
							selected = false;
							//Checa se há alguma linha vazia na coluna desejada
							for(int lines=Height-1; lines>=0; lines--) {
								if(TABULEIRO[colunaChosen][lines] == 0) {
									dropTo = lines;
									break;
								}
							}
							drop = true;
							dropping = 0;
							System.out.println("Coluna: "+colunaChosen);
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
			//Insere a coluna e a linha no tabuleiro
			if(chosen && !drop) {
				TABULEIRO[colunaChosen][dropTo] = fichas.get(fichas.size() - 1).modelo;
				fichas.add(new Ficha(1));
				cor = fichas.get(fichas.size() -1).modelo;
				chosen = false;
			}
		}
	}
	
	public void render(Graphics g) {
		for(int x = 0; x < Width; x++) {
			for(int y = 0; y < Height; y++) {
				
				g.setColor(Color.white); //slots das fichas
				g.drawRect((x*tileSize)+Game.WIDTH/offSet, (y*tileSize)+Game.HEIGHT/offSet, tileSize, tileSize);
				
				//Printa no tabuleiro as linhas ja escolhidas
				if(TABULEIRO[x][y] != 0) {
					switch (TABULEIRO[x][y]) {
						case 1:
							g.setColor(Color.red);
							break;
		
						case 2:
							g.setColor(Color.cyan);
							break;
		
						case 3:
							g.setColor(Color.green);
							break;
					}
					
					g.fillRect((x*tileSize)+Game.WIDTH/offSet+dropSet-tileSize, (y*tileSize)+Game.HEIGHT/offSet+dropSet-tileSize, 17, 17);
				}			
				
				switch (cor) {
					case 1:
						g.setColor(Color.red);
						break;
	
					case 2:
						g.setColor(Color.cyan);
						break;
	
					case 3:
						g.setColor(Color.green);
						break;
				}	
				
				//Destaca a coluna que o mouse esta em cima
				if(dentro && !selected && !drop) {
					g.fillRect((coluna*tileSize)+Game.WIDTH/offSet+dropSet-tileSize, Game.HEIGHT/offSet-dropSet, 17, 17);
					g.setColor(Color.black);
					g.drawRect((coluna*tileSize)+Game.WIDTH/offSet, Game.HEIGHT/offSet, tileSize, Height*tileSize);	
				}
					
				//Destaca a coluna selecionada
				if(selected) {
					g.fillRect((colunaSelected*tileSize)+Game.WIDTH/offSet+dropSet-tileSize, Game.HEIGHT/offSet-dropSet, 17, 17);
					g.setColor(Color.red);
					g.drawRect((colunaSelected*tileSize)+Game.WIDTH/offSet, Game.HEIGHT/offSet, tileSize, Height*tileSize);
				}
				
				//Animação de queda				
				if(drop) {
					g.fillRect((colunaChosen*tileSize)+Game.WIDTH/offSet+dropSet-tileSize, (Game.HEIGHT/offSet)-dropSet+(int)dropping, 17, 17);
					dropping+=0.1;
					if((int)dropping-dropSet>=(dropTo*tileSize)+dropSet-tileSize) {
						drop=false;
					}
					
				}
			}
		}
	}
}
