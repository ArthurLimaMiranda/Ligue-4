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



public class TabuleiroTurboMaluco extends Tabuleiro{
	
	private Random random;
	private  boolean trocar; 
	private int X_Maluco, Y_Maluco;
	private boolean Crazyness;

	public TabuleiroTurboMaluco() {
		random = new Random();
	}
		
	public void update() {
		
		
		
		if(!drop) {
			
			for(int i=0; i<fichas.size(); i++) {
				Entity e = fichas.get(i);
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
					
					
					if(Game.CLICKED) {	//Seleciona a coluna desejadas
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
						
						
						else {	//Confirma a coluna, ativando a animação de queda ou cancela a seleção
							if(coluna == colunaSelected) {
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
					colunaChosen = ia.easyPeasy(this.buxinCheio,colunaSelected);
				}
				
				else {
					if(Round == 2) {
						colunaChosen = ia.blockYou(this.colunaChosen);
						System.out.println("BLOCK");
					}else {
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
			
			
			if(chosen && !drop && !buxinCheio[colunaChosen]) {	//Insere a coluna e a linha no tabuleiro
				
				TABULEIRO[colunaChosen][dropTo] = fichas.get(Round-1).getModelo();
				fichas.get(Round-1).setID(colunaChosen, dropTo);//HERE
				
				
				if(dropTo == 0) {
					buxinCheio[colunaChosen] = true;
				}
				
				fichas.get(Round-1).setX((colunaChosen*TILESIZE)+Game.WIDTH/OFFSET);
				fichas.get(Round-1).setY((dropTo*TILESIZE)+Game.HEIGHT/OFFSET);
				
			
				
				
				if(Round >= 2) {
					crazynessLevel();
					}							
				try {
					
					if(Crazyness == true) {
						modoMaluco(dropTo,colunaChosen);
						
						if(trocar == true) {
							
							TABULEIRO[X_Maluco][Y_Maluco] = fichas.get(Round-1).getModelo();
							
						
							for(int i=0;  i<fichas.size(); i++) {
								FichaE e = fichas.get(i);
								int[] id = e.getID();
								if( ((id[0] == X_Maluco)  && (id[1] == Y_Maluco))    ) {
									e.setModelo(fichas.get(Round-1).getModelo());
									e.setSprite(fichas.get(Round-1).getSprite());
								}
							}
							
							//ARTHUR BOTA ALGO AQUI PRA RENDEZIRAR A PEÇA EH ISSO			
							int vit = checarWin(dropTo, colunaChosen);
							if(vit!=0) {
								if(vit==1) {
									Game.VITP1 = true;
								}
								
								
								else {
									Game.VITP2 = true;
								}
							}
						}
						
						
					}
					
				} catch (UShouldNotBeHere  e) {
					System.out.println("YOU SHOULDN´T BE HERE!");
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
				chosen = false;
				System.out.println(Round);
				 
				
				
			}
			}
		}


	public void modoMaluco(int linha, int coluna) throws UShouldNotBeHere{
		
		int Slot_Central = TABULEIRO[coluna][linha];
		
		
		int Slot_Maluco;
		
		trocar = false;
		
		int tentativas = 0;
		
		while(trocar == false && tentativas <=7) {
			
			int Crazy_coluna = random.nextInt(3)- 1 + coluna;
			int Crazy_linha = random.nextInt(3)- 1 + linha;
			
			if(Crazy_coluna >= WIDTH ) {// fim do tabuleiro	
				UShouldNotBeHere e = new UShouldNotBeHere();
				throw e;
			}
			if(Crazy_coluna <= 0) {// fim do tabuleiro
				UShouldNotBeHere e = new UShouldNotBeHere();
				throw e;
			}
			if(Crazy_linha >= HEIGHT ) {// fim do tabuleiro
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
	
	
	public void crazynessLevel() { 	//Nivel de loucura
		
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






