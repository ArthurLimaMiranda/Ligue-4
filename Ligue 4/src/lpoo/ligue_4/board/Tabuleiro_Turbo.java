package lpoo.ligue_4.board;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import lpoo.ligue_4.entidades.Entity;
import lpoo.ligue_4.entidades.Ficha;
import lpoo.ligue_4.exceptions.UShouldNotBeHere;
import lpoo.ligue_4.grafs.Spritesheet;
import lpoo.ligue_4.main.Game;

public class Tabuleiro_Turbo extends Tabuleiro {

	private boolean trocar, trocar_left, trocar_right;

	public Tabuleiro_Turbo() {
		
		TABULEIRO = new int[Width][Height] ;
		fichas = new ArrayList<Ficha>();
		spritesheet = new Spritesheet("res/spritesheet.png");
		
		for (int a = 0; a < nRounds; a++) {
			
			if (a % 2 != 0 && Game.player2.getTipo() == 2) {			
				fichas.add(new Ficha(2, 0, 0, 32, 32, spritesheet.getSprite(32*2, 0, 32, 32)));	
			}
			else if (a % 2 != 0 && Game.player2.getTipo() == 3) {		
				fichas.add(new Ficha(3, 0, 0, 32, 32, spritesheet.getSprite(32*3, 0, 32, 32)));
			}
			else {		
				fichas.add(new Ficha(1, 0, 0, 32, 32, spritesheet.getSprite(32*1, 0, 32, 32)));
			}		
		}
		
		
		for (int a = 0; a < 42; a++) {
			cores[a] = fichas.get(a).getModelo();
		}
		
		for (int a = 0; a < buxinCheio.length; a++) {
			buxinCheio[a] = false;
		}
		
	}
	
	public void update() {
		

		// AQ
		if(!drop) {
			
			for(int i=0; i<fichas.size(); i++) {
				Ficha e = fichas.get(i);
				e.update();
			}
			
			
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
							if(!buxinCheio[colunaChosen]) {
								drop = true;
								dropping = 0;
								System.out.println("Coluna: "+colunaChosen);
							}
							else {
								chosen = false;
								selected = false;
							}
							
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
			if(chosen && !drop && !buxinCheio[colunaChosen]) {
				TABULEIRO[colunaChosen][dropTo] = fichas.get(Round-1).getModelo();
				fichas.get(Round-1).setID(colunaChosen, dropTo);
				
				if(dropTo == 0) {
					buxinCheio[colunaChosen] = true;
				}
				
				fichas.get(Round-1).setX((colunaChosen*tileSize)+Game.WIDTH/offSet);
				fichas.get(Round-1).setY((dropTo*tileSize)+Game.HEIGHT/offSet);
				

				try {
				    // Some code that may throw UShouldNotBeHere exception
				    // ...
					
					if(ModoTurbo(colunaChosen, dropTo)) { // Aciona o Modo Turbo
						
						if(trocar_left) { // Troca a ficha da esquerda
							TABULEIRO[colunaChosen-1][dropTo] = fichas.get(Round-1).getModelo();
							for(int i=0;  i<fichas.size(); i++) {
								Ficha e = fichas.get(i);
								int[] id = e.getID();
								if((id[0] == colunaChosen-1) && (id[1] == dropTo)) {
									e.setModelo(fichas.get(Round-1).getModelo());
									e.setSprite(fichas.get(Round-1).getSprite());
								}
							}
							
							int vit1 = ChecarWin(dropTo, colunaChosen-1);	
							if(vit1!=0) {
								if(vit1==1) {
									Game.vitP1 = true;
								}
								else {
									Game.vitP2 = true;
								}
							}
						}
						
						if(trocar_right) {// Troca a ficha da direita
							TABULEIRO[colunaChosen+1][dropTo] = fichas.get(Round-1).getModelo();
							for(int i=0;  i<fichas.size(); i++) {
								Ficha e = fichas.get(i);
								int[] id = e.getID();
								if((id[0] == colunaChosen+1) && (id[1] == dropTo)) {
									e.setModelo(fichas.get(Round-1).getModelo());
									e.setSprite(fichas.get(Round-1).getSprite());
								}
							}
							
							int vit2 = ChecarWin(dropTo, colunaChosen+1);	
							if(vit2!=0) {
								if(vit2==1) {
									Game.vitP1 = true;
								}
								else {
									Game.vitP2 = true;
								}
							}
						}
						
					}
				} catch (UShouldNotBeHere e) {
				  System.out.println("YOU SHOULDN´T BE HERE!");						
				}
			
				
				
				
				
				//TO DO: Checar condicao de empate
				
				int vit = ChecarWin(dropTo, colunaChosen);
				if(vit!=0) {
					if(vit==1) {
						Game.vitP1 = true;
					}
					else {
						Game.vitP2 = true;
					}
				}

				Round++;
				System.out.println(Round);
				
				chosen = false;
							
			}
		}
		// AQ
		
	}
	
	public void render(Graphics g) {
		
		for(int x = 0; x < Width; x++) {
			for(int y = 0; y < Height; y++) {
				
				g.setColor(Color.white); //slots das fichas
				g.drawRect((x*tileSize)+Game.WIDTH/offSet, (y*tileSize)+Game.HEIGHT/offSet, tileSize, tileSize);
				
				for(int i=0; i<Round; i++) {
					Entity e = fichas.get(i);
					e.render(g);
				}
				
				cor = cores[Round- 1];
				fichaAr = new Ficha(cor, 0, 0, 32, 32, spritesheet.getSprite(32*cor, 0, 32, 32));
				
				//Destaca a coluna que o mouse esta em cima
				if(dentro && !selected && !drop) {
					fichaAr.setX((coluna*tileSize)+Game.WIDTH/offSet);
					fichaAr.setY(Game.HEIGHT/offSet-dropSet);
					fichaAr.render(g);
					g.setColor(Color.black);
					g.drawRect((coluna*tileSize)+Game.WIDTH/offSet, Game.HEIGHT/offSet, tileSize, Height*tileSize);	
				}
					
				//Destaca a coluna selecionada
				if(selected) {	
					fichaAr.setX((colunaSelected*tileSize)+Game.WIDTH/offSet);
					fichaAr.setY(Game.HEIGHT/offSet-dropSet);
					fichaAr.render(g);
					g.setColor(Color.red);
					g.drawRect((colunaSelected*tileSize)+Game.WIDTH/offSet, Game.HEIGHT/offSet, tileSize, Height*tileSize);
				}
				
				//Animação de queda				
				if(drop) {
					fichaAr.setX((colunaChosen*tileSize)+Game.WIDTH/offSet);
					fichaAr.setY((Game.HEIGHT/offSet)-dropSet+(int)dropping);
					fichaAr.render(g);
					dropping+=0.1;
					if((int)dropping-dropSet>=(dropTo*tileSize)-offSet+dropSet-tileSize) {
						drop=false;
						
					}
				}
			}
		}	
		
	}
			
		
	
	
	public boolean ModoTurbo(int coluna, int linha) throws UShouldNotBeHere{
			
		int Slot_Centro = TABULEIRO[coluna][linha];
		int Slot_Esquerda,Slot_Direita;	
		
		if (coluna == Width-1) {	// fim do tabuleiro		
			 
			  //Slot_Direita = Slot_Centro;
			  //Slot_Esquerda = TABULEIRO[coluna-1][linha]; 
			  UShouldNotBeHere e = new UShouldNotBeHere();
			  throw e;
			 
		}
		
		else if(coluna == 0) {	 // fim do tabuleiro
			 //Slot_Esquerda = Slot_Centro;
			 //Slot_Direita = TABULEIRO[coluna+1][linha];	
			 UShouldNotBeHere e = new UShouldNotBeHere();
			 throw e;
		}
		
		else {		
			 Slot_Esquerda = TABULEIRO[coluna -1][linha];
			 Slot_Direita = TABULEIRO[coluna +1][linha];
			
		}
	 
		trocar = false;
		this.trocar_left = false;
		this.trocar_right = false;
			
		if ((Slot_Centro != Slot_Esquerda) && (Slot_Esquerda!=0)) {				
			this.trocar_left = true;
			trocar = true;
				
		}
		if ((Slot_Centro != Slot_Direita)&& Slot_Direita!=0) {
			this.trocar_right = true;	
			trocar = true;
		}				
		
		return trocar;
									
	}
}
