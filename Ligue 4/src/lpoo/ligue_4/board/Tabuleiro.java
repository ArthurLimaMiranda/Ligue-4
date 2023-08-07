package lpoo.ligue_4.board;
import lpoo.ligue_4.sound_track.SoundEffects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import lpoo.ligue_4.entidades.Entity;
import lpoo.ligue_4.entidades.FichaE;
import lpoo.ligue_4.entidades.TabuleiroE;
import lpoo.ligue_4.exceptions.BusaoLotado;
import lpoo.ligue_4.grafs.Spritesheet;
import lpoo.ligue_4.main.Game;
import lpoo.ligue_4.main.MyRivalPC;

public class Tabuleiro implements InterfaceTabuleiro{

	public static final int Width = 7 , Height = 6, offSet = 3;
	public static int[][]  TABULEIRO;
	
	protected int tileSize = 30, dropSet = 7+tileSize, dropTo;
	protected float dropping;
	protected int coluna, colunaSelected = -1, colunaChosen = -1;
	protected boolean dentro = false, selected = false, chosen = false, drop = false;
	protected boolean[] buxinCheio = new boolean[7];
	
	protected FichaE fichaAr;
	protected ArrayList<FichaE> fichas;
	protected TabuleiroE[][] tabuleiroGraf;
	protected Spritesheet spritesheet;
	protected MyRivalPC ia;
	
	protected int [] cores = new int [42]; // salvar as cores das fichas
	protected int cor;
	
	protected int nRounds = 42;
	
	protected int Round = 1;	//Round inicial
	
	protected String Sound_Ficha = "res/cash-register-purchase-87313 (mp3cut.net).mp3";
	protected String Sound_Win = "res/bright-notifications-151766 (mp3cut.net).mp3";
	 
	SoundEffects sounds = new SoundEffects();
	public Tabuleiro() {
				
		TABULEIRO = new int[Width][Height];
		tabuleiroGraf = new TabuleiroE[Width][Height];
		fichas = new ArrayList<FichaE>();
		spritesheet = new Spritesheet("res/spritesheet.png");
		ia = new MyRivalPC();
		
		for (int a = 0; a < nRounds; a++) {
			
			if (a % 2 != 0 && Game.player2.getTipo() == 2) {			
				fichas.add(new FichaE(2, 0, 0, 32, 32, spritesheet.getSprite(32*2, 0, 32, 32)));	
			}
			else if (a % 2 != 0 && Game.player2.getTipo() == 3) {		
				fichas.add(new FichaE(3, 0, 0, 32, 32, spritesheet.getSprite(32*3, 0, 32, 32)));
			}
			else {		
				fichas.add(new FichaE(1, 0, 0, 32, 32, spritesheet.getSprite(32*1, 0, 32, 32)));
			}		
		}
		
		for (int a = 0; a < 42; a++) {
			cores[a] = fichas.get(a).getModelo();
		}
		
		for (int a = 0; a < buxinCheio.length; a++) {
			buxinCheio[a] = false;
		}
		
		for(int w=0; w<Width; w++) {
			for(int h=0; h<Height; h++) {
				if(w==0 && h==0) {
					tabuleiroGraf[w][h] = new TabuleiroE(0, 0, 32, 32, spritesheet.getSprite(32*5, 0, 32, 32));
				}
				else if(w==0 && h==Height-1) {
					tabuleiroGraf[w][h] = new TabuleiroE(0, 0, 32, 32, spritesheet.getSprite(32*6, 0, 32, 32));					
				}
				else if(w==Width-1 && h==0) {
					tabuleiroGraf[w][h] = new TabuleiroE(0, 0, 32, 32, spritesheet.getSprite(32*7, 0, 32, 32));
				}
				else if(w==Width-1 && h==Height-1) {
					tabuleiroGraf[w][h] = new TabuleiroE(0, 0, 32, 32, spritesheet.getSprite(32*8, 0, 32, 32));
				}
				else {
					tabuleiroGraf[w][h] = new TabuleiroE(0, 0, 32, 32, spritesheet.getSprite(32*4, 0, 32, 32));
				}
				
			}
		}
 	
		//Adiciona as fichas a cada Round com as cores certas					

	}
	
	public void update() {
		
		if(!drop) {
			for(int i=0; i<fichas.size(); i++) {
				Entity e = fichas.get(i);
				e.update();
			}
			
			if(Round%2!=0||Game.p2) {
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
			
			else if(!chosen && !Game.p2){
				if(!Game.dificil) {
					colunaChosen = ia.EasyPeasy(this.buxinCheio, this.colunaChosen);
				}
				
				else {							
					if(Round == 2) {
						colunaChosen = ia.BLOCKYOU(this.colunaChosen);
						System.out.println("BLOCK");
					}
					else {
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
				fichas.get(Round-1).setID(colunaChosen, dropTo);
				if(dropTo == 0) {
					buxinCheio[colunaChosen] = true;
				}
				fichas.get(Round-1).setX((colunaChosen*tileSize)+Game.WIDTH/offSet);
				fichas.get(Round-1).setY((dropTo*tileSize)+Game.HEIGHT/offSet);

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
				
				Round++;		
				chosen = false;
				System.out.println(Round);
				
			}
		}
		// AQ
	}
	
	public void render(Graphics g) {

		for(int x = 0; x < Width; x++) {
			for(int y = 0; y < Height; y++) {
				
				g.setColor(Color.white); //slots das fichas

				if(Game.gameState.equals("Game_Over")) {
					tabuleiroGraf[x][y].setX((x*tileSize+110)+Game.WIDTH*Game.SCALE/offSet);
					tabuleiroGraf[x][y].setY((y*tileSize+250)+Game.HEIGHT*Game.SCALE/offSet);						
				}
				
				else {
					tabuleiroGraf[x][y].setX((x*tileSize)+Game.WIDTH/offSet);
					tabuleiroGraf[x][y].setY((y*tileSize)+Game.HEIGHT/offSet);
					tabuleiroGraf[x][y].render(g);
				}
				tabuleiroGraf[x][y].render(g);
				
				for(int i=0; i<Round-1; i++) {
					FichaE e = fichas.get(i);		
					if(Game.gameState.equals("Game_Over") && i<Round-1) {
						e.setX((e.getID()[0]*tileSize+110)+Game.WIDTH*Game.SCALE/offSet);
						e.setY((e.getID()[1]*tileSize+250)+Game.HEIGHT*Game.SCALE/offSet);
						e.render(g);							
					}
					else {
						e.render(g);
					}
				}
				
				
				
				cor = cores[Round- 1];
				fichaAr = new FichaE(cor, 0, 0, 32, 32, spritesheet.getSprite(32*cor, 0, 32, 32));
				
				if(!Game.gameState.equals("Game_Over")) {
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
							sounds.playMP3WithTimeout(Sound_Ficha, 300);
							drop=false;
						}
					}
				}
			}
		}
	}

	public int ChecarWin(int linha, int coluna) {

		int slot0, slot01, slot02, slot03, slot04;
		slot0 = TABULEIRO[coluna][linha];
		int xRel, yRel, sequencia;
		boolean sair;
		
		//Win na Linha Geral			
		for(int y=0; y<Height; y++) {
			for (int x = 0 ; x < Width-3 ; x ++) {						
				slot01 = TABULEIRO[x][y];
				slot02 = TABULEIRO[x+1][y];
				slot03 = TABULEIRO[x+2][y];
				slot04 = TABULEIRO[x+3][y];

				if ((slot01!=0) && (slot01==slot02) && (slot02==slot03) && (slot03==slot04)) {

					System.out.println("Win linha P:" + slot01);
					return slot01;
				}				 			
			}
		}
		
		// Win na Coluna Geral
		for (int y = 0 ; y < Height -3 ; y ++) {							
			for(int x = 0; x < Width ; x ++ ) {
				slot01 = TABULEIRO[x][y];
				slot02 = TABULEIRO[x][y+1];
				slot03 = TABULEIRO[x][y+2];
				slot04 = TABULEIRO[x][y+3];

				if ((slot01!=0) && (slot01==slot02) && (slot02==slot03) && (slot03==slot04)) {

					System.out.println("Win coluna P:" + slot01);
					return slot01;
				}				 		
			}
		}

		//Win Diagona Principal
		xRel = coluna;
		yRel = linha;
		sair = false;
		sequencia=1;
		while(!sair) {
			xRel--;
			yRel++;
			
			if((xRel<0 || yRel>Height-1) || (TABULEIRO[xRel][yRel] != slot0)) {
				sair=true;
			}
			else {
				sequencia++;
			}
		}
		
		xRel = coluna;
		yRel = linha;
		sair = false;
		while(!sair) {
			xRel++;
			yRel--;
			
			if((xRel>Width-1 || yRel<0) || (TABULEIRO[xRel][yRel] != slot0)) {
				sair=true;
			}
			else {
				sequencia++;
			}
		}
		
		if(sequencia>=4) {
			System.out.println("Win digPrin P:" + slot0);
			return slot0;
		}


	
		//Win Diagona Secundaira
		xRel = coluna;
		yRel = linha;
		sair = false;
		sequencia=1;
		while(!sair) {
			xRel++;
			yRel++;
			
			if((xRel>Width-1 || yRel>Height-1) || (TABULEIRO[xRel][yRel] != slot0)) {
				sair=true;
			}
			else {
				sequencia++;
			}
		}
		
		xRel = coluna;
		yRel = linha;
		sair = false;
		while(!sair) {
			xRel--;
			yRel--;
			
			if((xRel<0 || yRel<0) || (TABULEIRO[xRel][yRel] != slot0)) {
				sair=true;
			}
			else {
				sequencia++;
			}
		}
		
		if(sequencia>=4) {
			System.out.println("Win digSec P:" + slot0);
			return slot0;
		}
		return 0;
	}

}



