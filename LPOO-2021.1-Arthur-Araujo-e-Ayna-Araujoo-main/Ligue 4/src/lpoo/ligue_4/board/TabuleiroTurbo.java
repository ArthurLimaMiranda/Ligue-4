package lpoo.ligue_4.board;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import lpoo.ligue_4.entidades.Entity;
import lpoo.ligue_4.entidades.FichaE;
import lpoo.ligue_4.exceptions.BusaoLotado;
import lpoo.ligue_4.exceptions.UShouldNotBeHere;
import lpoo.ligue_4.grafs.Spritesheet;
import lpoo.ligue_4.main.Game;
import lpoo.ligue_4.main.MyRivalPC;

public class TabuleiroTurbo extends Tabuleiro {

	private boolean trocar, trocar_left, trocar_right;

	public void update(){
		
		// AQ
		if(!drop) {
			
			for(int i=0; i<fichas.size(); i++) {
				FichaE e = fichas.get(i);
				e.update();
			}
			
			for(int w=0; w<WIDTH; w++) {
				if((w!=colunaSelected)) {
					for(int h=0; h<HEIGHT; h++) {
						if(w==0 && h==0) {
							tabuleiroGraf[w][h].setSprite(spritesheet.getSprite(32*5, 0, 32, 32));
						}
						else if(w==0 && h==HEIGHT-1) {
							tabuleiroGraf[w][h].setSprite(spritesheet.getSprite(32*6, 0, 32, 32));;					
						}
						else if(w==WIDTH-1 && h==0) {
							tabuleiroGraf[w][h].setSprite(spritesheet.getSprite(32*7, 0, 32, 32));
						}
						else if(w==WIDTH-1 && h==HEIGHT-1) {
							tabuleiroGraf[w][h].setSprite(spritesheet.getSprite(32*8, 0, 32, 32));
						}
						else {
							tabuleiroGraf[w][h].setSprite(spritesheet.getSprite(32*4, 0, 32, 32));
						}
						
					}
				}
			}			
			
			if(Round%2!=0||Game.P2) {
				//Checa se o mouse se encontra dentro do tabuleiro e em qual coluna esta em cima
				if((Game.XPOS>Game.WIDTH/OFFSET) && (Game.XPOS<((WIDTH*TILESIZE)-3+Game.WIDTH/OFFSET)) && (Game.YPOS>=Game.HEIGHT/OFFSET) && (Game.YPOS<=((HEIGHT*TILESIZE)+Game.HEIGHT/OFFSET))) {
					coluna = (Game.XPOS/TILESIZE)-(Game.WIDTH/TILESIZE/OFFSET);	
					dentro = true;
					
					if(coluna!=colunaSelected && coluna!=colunaChosen ) {
						for(int h=0; h<HEIGHT; h++) {
							if(coluna==0 && h==0) {
								tabuleiroGraf[coluna][h].setSprite(spritesheet.getSprite(32*5, 32*2, 32, 32));
							}
							else if(coluna==0 && h==HEIGHT-1) {
								tabuleiroGraf[coluna][h].setSprite(spritesheet.getSprite(32*6, 32*2, 32, 32));;					
							}
							else if(coluna==WIDTH-1 && h==0) {
								tabuleiroGraf[coluna][h].setSprite(spritesheet.getSprite(32*7, 32*2, 32, 32));
							}
							else if(coluna==WIDTH-1 && h==HEIGHT-1) {
								tabuleiroGraf[coluna][h].setSprite(spritesheet.getSprite(32*8, 32*2, 32, 32));
							}
							else {
								tabuleiroGraf[coluna][h].setSprite(spritesheet.getSprite(32*4, 32*2, 32, 32));
							}
							
						}
					}
					
					
					if(Game.CLICKED) {	//Seleciona a coluna desejada
						Game.CLICKED = false;
						if(!selected) {
							selected = true;
							colunaSelected = coluna;
							
							for(int h=0; h<HEIGHT; h++) {
								if(colunaSelected==0 && h==0) {
									tabuleiroGraf[colunaSelected][h].setSprite(spritesheet.getSprite(32*5, 32*1, 32, 32));
								}
								else if(colunaSelected==0 && h==HEIGHT-1) {
									tabuleiroGraf[colunaSelected][h].setSprite(spritesheet.getSprite(32*6, 32*1, 32, 32));;					
								}
								else if(colunaSelected==WIDTH-1 && h==0) {
									tabuleiroGraf[colunaSelected][h].setSprite(spritesheet.getSprite(32*7, 32*1, 32, 32));
								}
								else if(colunaSelected==WIDTH-1 && h==HEIGHT-1) {
									tabuleiroGraf[colunaSelected][h].setSprite(spritesheet.getSprite(32*8, 32*1, 32, 32));
								}
								else {
									tabuleiroGraf[colunaSelected][h].setSprite(spritesheet.getSprite(32*4, 32*1, 32, 32));
								}
								
							}
						}
						
						
						else {
							if(coluna == colunaSelected) {	//Confirma a coluna, ativando a animação de queda ou cancela a seleção
								colunaChosen = colunaSelected;
								colunaSelected = -1;
								chosen = true;
								selected = false;
								
								for(int lines=HEIGHT-1; lines>=0; lines--) {	//Checa se há alguma linha vazia na coluna desejada
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
								colunaSelected = -1;
							}
						}
					}
					
				}
						
				else {
					dentro = false;
				}
			}
			
			else if(!chosen && !Game.P2){
				if(!Game.DIFICIL) {
					colunaChosen = ia.easyPeasy(this.buxinCheio,colunaChosen);
				}
				
				else {
					if(Round == 2) {
						colunaChosen = ia.blockYou(this.colunaChosen);
						System.out.println("BLOCK");
					}
					else {
						colunaChosen = ia.iNeverGonnaLetUWin(TABULEIRO, HEIGHT, WIDTH, this.buxinCheio);
					}
				}
				
				colunaSelected = -1;
				chosen = true;
				selected = false;
				
				for(int lines=HEIGHT-1; lines>=0; lines--) {	//Checa se há alguma linha vazia na coluna desejada
					if(TABULEIRO[colunaChosen][lines] == 0) {
						dropTo = lines;
						break;
					}
				}
				
				drop = true;
				dropping = 0;
				System.out.println("Coluna: "+colunaChosen);
			}
			
		
			if(chosen && !drop && !buxinCheio[colunaChosen]) { 	//Insere a coluna e a linha no tabuleiro
				TABULEIRO[colunaChosen][dropTo] = fichas.get(Round-1).getModelo();
				fichas.get(Round-1).setID(colunaChosen, dropTo);
				
				if(dropTo == 0) {
					buxinCheio[colunaChosen] = true;
				}
				
				fichas.get(Round-1).setX((colunaChosen*TILESIZE)+Game.WIDTH/OFFSET);
				fichas.get(Round-1).setY((dropTo*TILESIZE)+Game.HEIGHT/OFFSET);

					
					if(modoTurbo(colunaChosen, dropTo)) { // Aciona o Modo Turbo
						
						if(trocar_left) { // Troca a ficha da esquerda
							TABULEIRO[colunaChosen-1][dropTo] = fichas.get(Round-1).getModelo();
							for(int i=0;  i<fichas.size(); i++) {
								FichaE e = fichas.get(i);
								int[] id = e.getID();
								if((id[0] == colunaChosen-1) && (id[1] == dropTo)) {
									e.setModelo(fichas.get(Round-1).getModelo());
									e.setSprite(fichas.get(Round-1).getSprite());
								}
							}
							
							int vit1 = checarWin(dropTo, colunaChosen-1);	
							if(vit1!=0) {
								if(vit1==1) {
									Game.VITP1 = true;
								}
								else {
									Game.VITP2 = true;
								}
							}
						}
						
						if(trocar_right) {// Troca a ficha da direita
							TABULEIRO[colunaChosen+1][dropTo] = fichas.get(Round-1).getModelo();
							for(int i=0;  i<fichas.size(); i++) {
								FichaE e = fichas.get(i);
								int[] id = e.getID();
								if((id[0] == colunaChosen+1) && (id[1] == dropTo)) {
									e.setModelo(fichas.get(Round-1).getModelo());
									e.setSprite(fichas.get(Round-1).getSprite());
								}
							}
							
							int vit2 = checarWin(dropTo, colunaChosen+1);	
							if(vit2!=0) {
								if(vit2==1) {
									Game.VITP1 = true;
								}
								else {
									Game.VITP2 = true;
								}
							}
						}
						
					}
				
				
				
				int vit = checarWin(dropTo, colunaChosen);
				if(vit!=0) {
					if(vit==1) {
						Game.VITP1 = true;
					}
					else {
						Game.VITP2 = true;
					}
				}
				
				if(Round == NROUNDS-1) {
					Game.EMPATE = true;
				}

				Round++;
				System.out.println(Round);
				
				chosen = false;
							
			}
		}
		
		
	}
	
	public static void checaCanto(int coluna) throws UShouldNotBeHere {
		if((coluna == WIDTH-1) || (coluna == 0)) {
			UShouldNotBeHere e = new UShouldNotBeHere();
			throw e;
		}
	}
	
	
	public boolean modoTurbo(int coluna, int linha){
		
		int Slot_Centro = TABULEIRO[coluna][linha];
		int Slot_Esquerda = 0, Slot_Direita = 0;	
		
		try {
			checaCanto(coluna);
			Slot_Esquerda = TABULEIRO[coluna -1][linha];
			Slot_Direita = TABULEIRO[coluna +1][linha];		
		} 
		catch (UShouldNotBeHere e) {
			e.printStackTrace();
			if (coluna == WIDTH-1) {	// fim do tabuleiro				 
				  Slot_Direita = Slot_Centro;
				  Slot_Esquerda = TABULEIRO[coluna-1][linha]; 	 
			}
			
			else if(coluna == 0) {	 // fim do tabuleiro
				 Slot_Esquerda = Slot_Centro;
				 Slot_Direita = TABULEIRO[coluna+1][linha];		 
			}
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
