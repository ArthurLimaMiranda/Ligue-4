package lpoo.ligue_4.board;

import java.awt.Color;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import lpoo.ligue_4.entidades.Entity;
import lpoo.ligue_4.entidades.FichaE;
import lpoo.ligue_4.exceptions.UShouldNotBeHere;
import lpoo.ligue_4.grafs.Spritesheet;
import lpoo.ligue_4.main.Game;



public class Tabuleiro_Turbo_Maluco extends Tabuleiro{
	
	private Random random;
	private  boolean trocar; 
	private int X_Maluco, Y_Maluco;
	private boolean Crazyness;

	public Tabuleiro_Turbo_Maluco() {
		random = new Random();
	}
		
	public void update() {
		
		
		// AQ
		if(!drop) {
			
			for(int i=0; i<fichas.size(); i++) {
				Entity e = fichas.get(i);
				e.update();
			}
			
			for(int w=0; w<Width; w++) {
				if((w!=colunaSelected)) {
					for(int h=0; h<Height; h++) {
						if(w==0 && h==0) {
							tabuleiroGraf[w][h].setSprite(spritesheet.getSprite(32*5, 0, 32, 32));
						}
						else if(w==0 && h==Height-1) {
							tabuleiroGraf[w][h].setSprite(spritesheet.getSprite(32*6, 0, 32, 32));;					
						}
						else if(w==Width-1 && h==0) {
							tabuleiroGraf[w][h].setSprite(spritesheet.getSprite(32*7, 0, 32, 32));
						}
						else if(w==Width-1 && h==Height-1) {
							tabuleiroGraf[w][h].setSprite(spritesheet.getSprite(32*8, 0, 32, 32));
						}
						else {
							tabuleiroGraf[w][h].setSprite(spritesheet.getSprite(32*4, 0, 32, 32));
						}
						
					}
				}
			}
			
			if(Round%2!=0||Game.p2) {
				//Checa se o mouse se encontra dentro do tabuleiro e em qual coluna esta em cima
				if((Game.xPos>Game.WIDTH/offSet) && (Game.xPos<((Width*tileSize)-3+Game.WIDTH/offSet)) &&
				   (Game.yPos>=Game.HEIGHT/offSet) && (Game.yPos<=((Height*tileSize)+Game.HEIGHT/offSet))) {
					coluna = (Game.xPos/tileSize)-(Game.WIDTH/tileSize/offSet);	
					dentro = true;
					
					if(coluna!=colunaSelected && coluna!=colunaChosen ) {
						for(int h=0; h<Height; h++) {
							if(coluna==0 && h==0) {
								tabuleiroGraf[coluna][h].setSprite(spritesheet.getSprite(32*5, 32*2, 32, 32));
							}
							else if(coluna==0 && h==Height-1) {
								tabuleiroGraf[coluna][h].setSprite(spritesheet.getSprite(32*6, 32*2, 32, 32));;					
							}
							else if(coluna==Width-1 && h==0) {
								tabuleiroGraf[coluna][h].setSprite(spritesheet.getSprite(32*7, 32*2, 32, 32));
							}
							else if(coluna==Width-1 && h==Height-1) {
								tabuleiroGraf[coluna][h].setSprite(spritesheet.getSprite(32*8, 32*2, 32, 32));
							}
							else {
								tabuleiroGraf[coluna][h].setSprite(spritesheet.getSprite(32*4, 32*2, 32, 32));
							}
							
						}
					}
					
					//Seleciona a coluna desejada
					if(Game.clicked) {
						Game.clicked = false;
						if(!selected) {
							selected = true;
							colunaSelected = coluna;
							
							for(int h=0; h<Height; h++) {
								if(colunaSelected==0 && h==0) {
									tabuleiroGraf[colunaSelected][h].setSprite(spritesheet.getSprite(32*5, 32*1, 32, 32));
								}
								else if(colunaSelected==0 && h==Height-1) {
									tabuleiroGraf[colunaSelected][h].setSprite(spritesheet.getSprite(32*6, 32*1, 32, 32));;					
								}
								else if(colunaSelected==Width-1 && h==0) {
									tabuleiroGraf[colunaSelected][h].setSprite(spritesheet.getSprite(32*7, 32*1, 32, 32));
								}
								else if(colunaSelected==Width-1 && h==Height-1) {
									tabuleiroGraf[colunaSelected][h].setSprite(spritesheet.getSprite(32*8, 32*1, 32, 32));
								}
								else {
									tabuleiroGraf[colunaSelected][h].setSprite(spritesheet.getSprite(32*4, 32*1, 32, 32));
								}
								
							}
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
								colunaSelected = -1;
							}
						}
					}
					
				}
						
				else {
					dentro = false;
				}
			}
			
			else if(!chosen && !Game.p2){
				if(!Game.dificil) {
					colunaChosen = ia.EasyPeasy(this.buxinCheio,colunaSelected);
				}
				
				else {
					if(Round == 2) {
						colunaChosen = ia.BLOCKYOU(this.colunaChosen);
						System.out.println("BLOCK");
					}else {
						colunaChosen = ia.INEVERGonnaLetUWin(TABULEIRO, Height, Width, this.buxinCheio);
					}
					}
				
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
			
			//Insere a coluna e a linha no tabuleiro
			if(chosen && !drop && !buxinCheio[colunaChosen]) {
				
				TABULEIRO[colunaChosen][dropTo] = fichas.get(Round-1).getModelo();
				fichas.get(Round-1).setID(colunaChosen, dropTo);//HERE
				
				
				if(dropTo == 0) {
					buxinCheio[colunaChosen] = true;
				}
				
				fichas.get(Round-1).setX((colunaChosen*tileSize)+Game.WIDTH/offSet);
				fichas.get(Round-1).setY((dropTo*tileSize)+Game.HEIGHT/offSet);
				
				//Round++;
				//chosen = false;
				
				
				if(Round >= 2) {
					CrazynessLevel();
					}							
				try {
					
					if(Crazyness == true) {
						Modo_Maluco(dropTo,colunaChosen);
						
						if(trocar == true) {
							// BOTAR ALGO AQ
							TABULEIRO[X_Maluco][Y_Maluco] = fichas.get(Round-1).getModelo();
							
							//HERE
							for(int i=0;  i<fichas.size(); i++) {
								FichaE e = fichas.get(i);
								int[] id = e.getID();
								if( ((id[0] == X_Maluco)  && (id[1] == Y_Maluco))    ) {
									e.setModelo(fichas.get(Round-1).getModelo());
									e.setSprite(fichas.get(Round-1).getSprite());
								}
							}
							
							//ARTHUR BOTA ALGO AQUI PRA RENDEZIRAR A PEÇA EH ISSO			
							int vit = ChecarWin(dropTo, colunaChosen);
							if(vit!=0) {
								if(vit==1) {
									Game.vitP1 = true;
								}
								
								
								else {
									Game.vitP2 = true;
								}
							}
						}
						
						
					}
					
				} catch (UShouldNotBeHere  e) {
					System.out.println("YOU SHOULDN´T BE HERE!");
				}
				
				int vit = ChecarWin(dropTo, colunaChosen);
				if(vit!=0) {
					if(vit==1) {
						Game.vitP1 = true;
					}
					else {
						Game.vitP2 = true;
					}
				}
				
				if(Round == nRounds-1) {
					Game.empate = true;
				}
				
				Round++;//HERE
				chosen = false;
				System.out.println(Round);
				 
				
				
			}
			}
		}


	public void Modo_Maluco(int linha, int coluna) throws UShouldNotBeHere{
		
		int Slot_Central = TABULEIRO[coluna][linha];
		
		
		int Slot_Maluco;
		
		trocar = false;
		
		int tentativas = 0;
		
		while(trocar == false && tentativas <=7) {
			
			int Crazy_coluna = random.nextInt(3)- 1 + coluna;
			int Crazy_linha = random.nextInt(3)- 1 + linha;
			
			if(Crazy_coluna >= Width ) {// fim do tabuleiro	
				UShouldNotBeHere e = new UShouldNotBeHere();
				throw e;
			}
			if(Crazy_coluna <= 0) {// fim do tabuleiro
				UShouldNotBeHere e = new UShouldNotBeHere();
				throw e;
			}
			if(Crazy_linha >= Height ) {// fim do tabuleiro
				UShouldNotBeHere e = new UShouldNotBeHere();
				throw e;
			}
			if(Crazy_linha <= 0) {// fim do tabuleiro
				UShouldNotBeHere e = new UShouldNotBeHere();
				throw e;
			}
			else {
				 Slot_Maluco = TABULEIRO[Crazy_coluna][Crazy_linha];
			}
			
			
			tentativas ++;
					
			if(Slot_Central == Slot_Maluco) {
				
				trocar = false;
				
			}else if(Slot_Maluco == 0) {
				
				trocar = false;
			}
			else {
				System.out.println("Crazy_coluna:" + Crazy_coluna + "Crazy_linha:" + Crazy_linha);	
				trocar = true;	
			
				X_Maluco =  Crazy_coluna;
				Y_Maluco =  Crazy_linha;
				
				System.out.println("MALUCO ON:" + trocar);
				
			}
			
		}
			
												
		
	}
	
	//Nivel de loucura
	public void CrazynessLevel() {
		
		Crazyness = false;
		int CrazynesLevel = random.nextInt(5);
		
		if(CrazynesLevel >= 2) {
			Crazyness = true;
		}
		else {
			Crazyness = false;
		}
		
		System.out.println("Crazynesse:" + Crazyness);
		
	}

}






