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

	public static final int WIDTH = 7 , HEIGHT = 6, OFFSET = 3, TILESIZE = 30, DROPSET = 7+TILESIZE, NROUNDS = 42;
	public static int[][]  TABULEIRO;
	
	protected int coluna, colunaSelected = -1, colunaChosen = -1, dropTo;
	protected float dropping;
	
	protected boolean dentro = false, selected = false, chosen = false, drop = false;
	protected boolean[] buxinCheio = new boolean[7];
	
	protected FichaE fichaAr;
	protected ArrayList<FichaE> fichas;
	protected TabuleiroE[][] tabuleiroGraf;
	protected Spritesheet spritesheet;
	protected MyRivalPC ia;
	
	protected int [] cores = new int [42]; 	//salvar as cores das fichas
	protected int cor;
	
	protected int Round = 1;	//Round inicial
	
	protected String Sound_Ficha = "res/cash-register-purchase-87313 (mp3cut.net).mp3";
	protected String Sound_Win = "res/bright-notifications-151766 (mp3cut.net).mp3";
	 
	SoundEffects sounds = new SoundEffects();
	public Tabuleiro() {
				
		TABULEIRO = new int[WIDTH][HEIGHT];
		tabuleiroGraf = new TabuleiroE[WIDTH][HEIGHT];
		fichas = new ArrayList<FichaE>();
		spritesheet = new Spritesheet("res/spritesheet.png");
		ia = new MyRivalPC();
		
		//Adiciona as fichas a cada Round com as cores certas
		for (int a = 0; a < NROUNDS; a++) {
			
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
		
		for(int w=0; w<WIDTH; w++) {
			for(int h=0; h<HEIGHT; h++) {
				if(w==0 && h==0) {
					tabuleiroGraf[w][h] = new TabuleiroE(0, 0, 32, 32, spritesheet.getSprite(32*5, 0, 32, 32));
				}
				else if(w==0 && h==HEIGHT-1) {
					tabuleiroGraf[w][h] = new TabuleiroE(0, 0, 32, 32, spritesheet.getSprite(32*6, 0, 32, 32));					
				}
				else if(w==WIDTH-1 && h==0) {
					tabuleiroGraf[w][h] = new TabuleiroE(0, 0, 32, 32, spritesheet.getSprite(32*7, 0, 32, 32));
				}
				else if(w==WIDTH-1 && h==HEIGHT-1) {
					tabuleiroGraf[w][h] = new TabuleiroE(0, 0, 32, 32, spritesheet.getSprite(32*8, 0, 32, 32));
				}
				else {
					tabuleiroGraf[w][h] = new TabuleiroE(0, 0, 32, 32, spritesheet.getSprite(32*4, 0, 32, 32));
				}
				
			}
		}
 	
							

	}
	
	public void update() {
		
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
					colunaChosen = ia.easyPeasy(this.buxinCheio, this.colunaChosen);
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
			
			
			if(chosen && !drop && !buxinCheio[colunaChosen]) {	//Insere a coluna e a linha no tabuleiro
				TABULEIRO[colunaChosen][dropTo] = fichas.get(Round-1).getModelo();	
				fichas.get(Round-1).setID(colunaChosen, dropTo);
				if(dropTo == 0) {
					buxinCheio[colunaChosen] = true;
				}
				fichas.get(Round-1).setX((colunaChosen*TILESIZE)+Game.WIDTH/OFFSET);
				fichas.get(Round-1).setY((dropTo*TILESIZE)+Game.HEIGHT/OFFSET);

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
	
	public void render(Graphics g) {

		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				
				g.setColor(Color.white); //slots das fichas

				if(Game.GAMESTART.equals("Game_Over")) {
					tabuleiroGraf[x][y].setX((x*TILESIZE+110)+Game.WIDTH*Game.SCALE/OFFSET);
					tabuleiroGraf[x][y].setY((y*TILESIZE+250)+Game.HEIGHT*Game.SCALE/OFFSET);						
				}
				
				else {
					tabuleiroGraf[x][y].setX((x*TILESIZE)+Game.WIDTH/OFFSET);
					tabuleiroGraf[x][y].setY((y*TILESIZE)+Game.HEIGHT/OFFSET);
					tabuleiroGraf[x][y].render(g);
				}
				tabuleiroGraf[x][y].render(g);
				
				for(int i=0; i<Round-1; i++) {
					FichaE e = fichas.get(i);		
					if(Game.GAMESTART.equals("Game_Over") && i<Round-1) {
						e.setX((e.getID()[0]*TILESIZE+110)+Game.WIDTH*Game.SCALE/OFFSET);
						e.setY((e.getID()[1]*TILESIZE+250)+Game.HEIGHT*Game.SCALE/OFFSET);
						e.render(g);							
					}
					else {
						e.render(g);
					}
				}
				
				
				
				cor = cores[Round- 1];
				fichaAr = new FichaE(cor, 0, 0, 32, 32, spritesheet.getSprite(32*cor, 0, 32, 32));
				
				if(!Game.GAMESTART.equals("Game_Over")) {
					fichaAr.render(g);
					
					if(dentro && !selected && !drop) {	//Destaca a coluna que o mouse esta em cima
						fichaAr.setX((coluna*TILESIZE)+Game.WIDTH/OFFSET);
						fichaAr.setY(Game.HEIGHT/OFFSET-DROPSET);
						fichaAr.render(g);
					}
						
					
					if(selected) {	//Destaca a coluna selecionada
						fichaAr.setX((colunaSelected*TILESIZE)+Game.WIDTH/OFFSET);
						fichaAr.setY(Game.HEIGHT/OFFSET-DROPSET);
						fichaAr.render(g);
					}
					
					
					if(drop) {	//Animação de queda				
						fichaAr.setX((colunaChosen*TILESIZE)+Game.WIDTH/OFFSET);
						fichaAr.setY((Game.HEIGHT/OFFSET)-DROPSET+(int)dropping);
						fichaAr.render(g);
						dropping+=0.1;
						if((int)dropping-DROPSET>=(dropTo*TILESIZE)-OFFSET+DROPSET-TILESIZE) {
							sounds.playMP3WithTimeout(Sound_Ficha, 300);
							drop=false;
						}
					}
				}
			}
		}
	}

	public int checarWin(int linha, int coluna) {

		int slot0, slot01, slot02, slot03, slot04;
		slot0 = TABULEIRO[coluna][linha];
		int xRel, yRel, sequencia;
		boolean sair;
		
		//Win na Linha Geral			
		for(int y=0; y<HEIGHT; y++) {
			for (int x = 0 ; x < WIDTH-3 ; x ++) {						
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
		for (int y = 0 ; y < HEIGHT -3 ; y ++) {							
			for(int x = 0; x < WIDTH ; x ++ ) {
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
			
			if((xRel<0 || yRel>HEIGHT-1) || (TABULEIRO[xRel][yRel] != slot0)) {
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
			
			if((xRel>WIDTH-1 || yRel<0) || (TABULEIRO[xRel][yRel] != slot0)) {
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
			
			if((xRel>WIDTH-1 || yRel>HEIGHT-1) || (TABULEIRO[xRel][yRel] != slot0)) {
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



