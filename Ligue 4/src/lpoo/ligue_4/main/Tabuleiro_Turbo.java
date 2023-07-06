package lpoo.ligue_4.main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import lpoo.ligue_4.entidades.Entity;
import lpoo.ligue_4.entidades.Ficha;
import lpoo.ligue_4.grafs.Spritesheet;

public class Tabuleiro_Turbo extends Tabuleiro {

	public static final int Width = 7 , Height = 6, offSet = 3;
	public static int[][]  TABULEIRO;
	
	protected int tileSize = 30, dropSet = 7+tileSize, dropTo;
	protected float dropping;
	protected int coluna, colunaSelected = -1, colunaChosen = -1;
	protected boolean dentro = false, selected = false, chosen = false, drop = false;
	protected boolean[] buxinCheio = new boolean[7];
	
	protected Ficha fichaAr;
	protected Ficha ficha_trocada;
	protected ArrayList<Ficha> fichas;
	protected Spritesheet spritesheet;
	
	protected int [] cores = new int [42]; // salvar as cores das fichas
	protected int cor;
	
	protected int nRounds = 42;
	
	protected int Round = 1;
	protected boolean P2 = true;
	
	boolean trocar,trocar_left, trocar_right;
	int cor_trocada ;
	int[][] id = new int [42][42];
	int id_ficha;
	
	
	public Tabuleiro_Turbo() {
		
		TABULEIRO = new int[Width][Height] ;
		fichas = new ArrayList<Ficha>();
		spritesheet = new Spritesheet("res/spritesheet.png");
		
		for (int a = 0; a < nRounds; a++) {
			
			if (a % 2 != 0 && P2 == true) {			
				fichas.add(new Ficha(2, 0, 0, 32, 32, spritesheet.getSprite(32*2, 0, 32, 32)));	
			}
			else if (a % 2 != 0 && P2 == false) {		
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
	
	public void update_Turbo() {
		

		// AQ
		if(!drop) {
			
			for(int i=0; i<fichas.size(); i++) {
				Entity e = fichas.get(i);
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
				TABULEIRO[colunaChosen][dropTo] = fichas.get(Round-1).modelo;
				
				if(dropTo == 0) {
					buxinCheio[colunaChosen] = true;
				}
				
				fichas.get(Round-1).setX((colunaChosen*tileSize)+Game.WIDTH/offSet);
				fichas.get(Round-1).setY((dropTo*tileSize)+Game.HEIGHT/offSet);
				
				
				cor_trocada = getId(dropTo ,colunaChosen);
				
				System.out.println("cor_trocada:" + cor_trocada);
				
				Round++;
				chosen = false;
							
				
				if(Chamar_ChecarWin(dropTo, colunaChosen)!=0) {
					Game.vitP1 = true;
					System.out.println("WINNNN");
					
				}
				
				
				System.out.println(Round);
				ModoTurbo(dropTo,colunaChosen);// N é a música da luisa sonsa
				System.out.println(cor_trocada);
				System.out.println(trocar_left);
				System.out.println(trocar_right);
			}
		}
		// AQ
		
	}
	
	public void render_Turbo(Graphics g) {
		
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
				//ficha_trocada = new Ficha(cor_trocada, 0, 0, 32, 32, spritesheet.getSprite(32*cor_trocada, 0, 32, 32));
				
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
				
				
				
				if(trocar_left == true) {
					
					System.out.println("trocar a cor da esquerda");
					fichaAr = new Ficha(cor_trocada, 0, 0, 32, 32, spritesheet.getSprite(32*cor_trocada, 0, 32, 32));
					
					
					fichaAr.setX(dropTo);
					fichaAr.setY(colunaChosen);	
					
					fichaAr.render(g);
					
				}
				
				if(trocar_right == true) {				
					
					System.out.println("trocar a cor da direita");
					
					fichaAr = new Ficha(cor_trocada, 0, 0, 32, 32, spritesheet.getSprite(32*cor_trocada, 0, 32, 32));			
					fichaAr.setX(dropTo);
					fichaAr.setY(colunaChosen);
					
					fichaAr.render(g);
					
					
				}
			
				//Animação de queda				
				if(drop) {
					
					
					fichaAr.setX((colunaChosen*tileSize)+Game.WIDTH/offSet);
					fichaAr.setY((Game.HEIGHT/offSet)-dropSet+(int)dropping);
					fichaAr.render(g);
					dropping+=0.1;
					
					
					//id[]
					
					if((int)dropping-dropSet>=(dropTo*tileSize)-offSet+dropSet-tileSize) {
						drop=false;
						
					}
					
					trocar_right = false;
					trocar_left = false;
					
				}
			}
		}
		
		
		}
			
		
	
	
	public boolean ModoTurbo(int linha, int coluna) {
			
		int Slot_Centro = TABULEIRO[coluna][linha];
		int Slot_Esquerda,Slot_Direita;	
		
		if (coluna == Width) {
			
			  Slot_Direita = TABULEIRO[coluna][linha];
			  Slot_Esquerda = TABULEIRO[coluna-1][linha];
			 
		}if( coluna == 0) {
			
			 Slot_Esquerda = TABULEIRO[coluna][linha];
			 Slot_Direita = TABULEIRO[coluna+1][linha];
			 
			 
		}else {
			
			 Slot_Esquerda = TABULEIRO[coluna -1][linha];
			 Slot_Direita = TABULEIRO[coluna +1][linha];
			 		 
		}
			
			 	 
		 if ((Slot_Centro != Slot_Esquerda) && Slot_Esquerda!=0) {				
				System.out.println("TROCOU" + Slot_Esquerda);	
				cor_trocada = Slot_Centro;
				return trocar_left = true;
				//fichaAr = new Ficha(cor_trocada, coluna-1, linha, 32, 32, spritesheet.getSprite(32*cor, 0, 32, 32));
				
			}
			if ((Slot_Centro != Slot_Direita)&& Slot_Direita!=0) {
				System.out.println("TROCOU" + Slot_Direita);	
				cor_trocada = Slot_Centro;
				return trocar_right = true;
				//fichaAr = new Ficha(cor_trocada, coluna+1, linha, 32, 32, spritesheet.getSprite(32*cor, 0, 32, 32));
				
			}	
			else {
				System.out.println("nada");
				trocar_left = false;
				trocar_right =false;
				return trocar = false;
			}
									
		}
		


		
	public int getId(int linha, int coluna) {
		
		id_ficha = fichas.get(Round-1).getModelo();
		
		id[linha][coluna] = id_ficha;
		
		System.out.println("id:" +id_ficha);
		
		return id_ficha;
	}	
	
	public void setId(int id_ficha) {
		
		this.id_ficha =id_ficha;
		
	}

	
	
	public int Chamar_ChecarWin(int linha, int coluna){
					
		return super.ChecarWin(linha, coluna);
	}
	
	
	
	
}
