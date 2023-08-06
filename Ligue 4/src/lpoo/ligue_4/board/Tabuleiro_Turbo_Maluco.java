package lpoo.ligue_4.board;

import java.awt.Color;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import lpoo.ligue_4.entidades.Entity;
import lpoo.ligue_4.entidades.Ficha;
import lpoo.ligue_4.exceptions.UShouldNotBeHere;
import lpoo.ligue_4.grafs.Spritesheet;
import lpoo.ligue_4.main.Game;



public class Tabuleiro_Turbo_Maluco extends Tabuleiro{
	
	private Random random;
	private  boolean trocar; 
	private int X_Maluco, Y_Maluco;
	private boolean Crazyness;
	//private int CrazynessLevel;

	
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
			
			if(Round%2!=0) {
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
			}
			
			else if(!chosen){
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
				
				sounds.playMP3WithTimeout(Sound_Ficha, 1000);
				TABULEIRO[colunaChosen][dropTo] = fichas.get(Round-1).getModelo();
				fichas.get(Round-1).setID(colunaChosen, dropTo);//HERE
				
				
				if(dropTo == 0) {
					buxinCheio[colunaChosen] = true;
				}
				
				fichas.get(Round-1).setX((colunaChosen*tileSize)+Game.WIDTH/offSet);
				fichas.get(Round-1).setY((dropTo*tileSize)+Game.HEIGHT/offSet);
				
				//Round++;
				//chosen = false;
				
				
				CrazynessLevel();
				
				try {
					
					if(Crazyness == true) {
						Modo_Maluco(dropTo,colunaChosen);
						
						if(trocar == true) {
							// BOTAR ALGO AQ
							TABULEIRO[X_Maluco][Y_Maluco] = fichas.get(Round-1).getModelo();
							
							//HERE
							for(int i=0;  i<fichas.size(); i++) {
								Ficha e = fichas.get(i);
								int[] id = e.getID();
								if( ((id[0] == X_Maluco) && (id[1] == dropTo))|| ((id[1] == colunaChosen) && (id[0] == Y_Maluco)) ) {
									e.setModelo(fichas.get(Round-1).getModelo());
									e.setSprite(fichas.get(Round-1).getSprite());
								}
							}
							
							//ARTHUR BOTA ALGO AQUI PRA RENDEZIRAR A PEÇA EH ISSO
							
							
						}
					}
					
				} catch (UShouldNotBeHere  e) {
					System.out.println("YOU SHOULDN´T BE HERE!");
				}
				//ATIVA MODO MALUCO!!
				
				
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
		//}
		// AQ
	
	
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

	public void Modo_Maluco(int linha, int coluna) throws UShouldNotBeHere{
		
		int Slot_Central = TABULEIRO[coluna][linha];
		
		int Crazy_coluna = random.nextInt(3)- 1 + coluna;
		int Crazy_linha = random.nextInt(3)- 1 + linha;
		
		int Slot_Maluco;
		
		trocar = false;
														
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
			
			System.out.println("Crazy_coluna:" + Crazy_coluna + "Crazy_linha:" + Crazy_linha);	
			if(Slot_Central == Slot_Maluco) {
				
				trocar = false;
				
			}else if(Slot_Maluco == 0) {
				
				trocar = false;
			}
			else {
				
				trocar = true;	
			
				X_Maluco =  Crazy_coluna;
				Y_Maluco =  Crazy_linha;
				
				System.out.println("MALUCO ON:" + trocar);
				
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






