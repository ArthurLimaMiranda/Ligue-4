package lpoo.ligue_4.main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import lpoo.ligue_4.exceptions.LimiteNome;
import lpoo.ligue_4.ranking.Vencedores;
import lpoo.ligue_4.sound_track.SoundEffects;
public class Menu {

	SoundEffects sounds = new SoundEffects();
	private String Sound_Menu = "res/light-pull-string-32448 (mp3cut.net).mp3";
	
	
	private int choice=0, coluna=0, tela=0, options=4, linhaMax=4, maxColuna=2, modosDeJogo=3, modoRanking = 0;
	private int[] maxChoice = {options-1, linhaMax, 1};
	private String modoJogo="Modo Normal", modoShowRanking ="MelhoresJogadores";
	private ArrayList<Vencedores> vencedoresLista;
	
	public boolean up, down, left, right, enter, writeNameP1=false, writeNameP2=false, noFile=true;
	
	public void setTela(int tela) {
		this.tela=tela;
	}
	
	
	public void update() throws LimiteNome {
		
		if(!writeNameP1 && !writeNameP2) {
			if(down) {
				down = false;
				choice++;
				if(choice>maxChoice[tela]) {
					choice = 0;
				}
			}
			else if(up) {
				up = false;
				choice--;
				if(choice<0) {
					choice = maxChoice[tela];
				}
			}
			
			
			else if(left) {
				left = false;	
				
				if(tela==1) {				
					coluna--;
					
					if(coluna<0) {
						coluna = maxColuna-1;
					}
		
					if(choice==1) {
						Game.p2 = !Game.p2;
						try {
							Game.player2.setNome("");
						} catch (LimiteNome e) {e.printStackTrace();}
					}
					
					else if(choice==2) {
						Game.modoJogo--;
						if(Game.modoJogo<0) {
							Game.modoJogo=modosDeJogo-1;
						}
					}
				}
				
				else if(tela==2) {
					if(choice==0) {
						modoRanking--;
						if(modoRanking<0) {
							modoRanking = 1;
						}
					}
				}
			}
			
			else if(right) {
				right = false;
				
				if(tela==1) {
					coluna++;
					if(coluna>maxColuna-1) {
						coluna = 0;
					}
					
					if(choice==1) {
						Game.p2 = !Game.p2;
						try {
							Game.player2.setNome("");
						} catch (LimiteNome e) {e.printStackTrace();}
					}
					
					else if(choice==2) {
						Game.modoJogo++;
						if(Game.modoJogo>modosDeJogo-1) {
							Game.modoJogo=0;
						}
					}
				}
				
				else if(tela==2) {
					if(choice==0) {
						modoRanking++;
						if(modoRanking>1) {
							modoRanking = 0;
						}
					}
				}
			}
			
		}
		
		if(enter) {

			sounds.playMP3WithTimeout(Sound_Menu, 1000);
			enter=false;
			if(tela==0) {
				if(choice==0) {
					tela=1;
					coluna=0;
					try {
						Game.player1.setNome("");
						Game.player2.setNome("");
						Game.dificil = false;
						Game.modoJogo=0;
						Game.p2=false;
					} catch (LimiteNome e) {e.printStackTrace();}
					
				}
				
				else if(choice==1) {
					tela=2;
					try {
						Game.ranking.LerRanking();
						
						vencedoresLista = new ArrayList<Vencedores>();
						for(int i=0; i<Game.ranking.partidas.size(); i++) {
							Vencedores winner = new Vencedores(Game.ranking.partidas.get(i)[2]);
							boolean temNome = false;
							for(int a=0; a<vencedoresLista.size(); a++) {
								Vencedores v = vencedoresLista.get(a);
								if(v.getNome().equals(winner.getNome())) {
									temNome = true;
									v.somarPontos();
								}
							}
							if(!temNome && !(!Game.ranking.partidas.get(i)[4].equals("PvP") && !Game.ranking.partidas.get(i)[0].equals(winner.getNome()))) {
								vencedoresLista.add(winner);
							}
							
						}
						Collections.sort(vencedoresLista, Comparator.comparing(Vencedores::getPontos));
						Collections.reverse(vencedoresLista);
						
						
					} catch (IOException e) {e.printStackTrace();}
					choice=0;
				}
				
				else if(choice==2) {
					tela=3;
				}
				
				else if(choice==3) {
					System.exit(1);
				}
			}
			
			else if(tela==1) {
				if(choice==0) {
					if(coluna==0 && !writeNameP1) {
						Game.playerName = "";
						writeNameP1=true;
					}
					else if(Game.p2 && coluna== 1 && !writeNameP2) {
						Game.playerName = "";
						writeNameP2=true;
					}
					
					else if(!Game.p2 && coluna== 1) {
						Game.dificil = !Game.dificil;
					}


					if(writeNameP1 && coluna==0 && Game.playerName.length()>=1) {
						writeNameP1 = false;
						try {
							Game.player1.setNome(Game.playerName);
						} 
						catch (LimiteNome e) {
							e.printStackTrace();
							Game.player1.setNome("");
						}
						Game.playerName = "";
					}
					
					if(Game.p2 && writeNameP2 && coluna==1 && Game.playerName.length()>=1) {
						writeNameP2 = false;
						try {
							Game.player2.setNome(Game.playerName);
						} 
						catch (LimiteNome e) {
							e.printStackTrace();
							Game.player2.setNome("");
						}
						Game.playerName = "";
					}
				}
				
				else if(choice==3) {
					if(Game.player1.getNome()!="" && (!Game.p2 || Game.player2.getNome()!="")) {
						if(!Game.p2) {
							Game.player2.setTipo(3);
						}
						
						Game.newGame = true;
						Game.gameState="Normal";
						
					}
				}
				
				else if(choice==4) {
					tela=0;
					choice=0;
				}
			}
			else if(tela==2) {
				if(choice==1) {
					tela=0;
				}
			}
			else if(tela==3) {
				tela=0;
			}
		}
	}
	
	public void render(Graphics g) {

		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.drawString(">Conecta 4<", (Game.WIDTH*Game.SCALE)/2-90, (Game.HEIGHT*Game.SCALE)/2-30);
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.drawString("Novo Jogo  " + (choice==0? "   <--" : ""), (Game.WIDTH*Game.SCALE)/2-90, (Game.HEIGHT*Game.SCALE)/2+20);
		g.drawString("Ranking      " + (choice==1? "   <--" : ""), (Game.WIDTH*Game.SCALE)/2-90, (Game.HEIGHT*Game.SCALE)/2+50);
		g.drawString("Regras        " + (choice==2? "   <--" : ""), (Game.WIDTH*Game.SCALE)/2-90, (Game.HEIGHT*Game.SCALE)/2+80);
		g.drawString("Sair            " + (choice==3? "   <--" : ""), (Game.WIDTH*Game.SCALE)/2-90, (Game.HEIGHT*Game.SCALE)/2+110);
		
		if(tela==1) {
			g.setColor(Color.black);
			g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
			g.setColor(Color.white);
			

			g.setFont(new Font("arial", Font.BOLD, 20));
			g.drawRect((Game.WIDTH*Game.SCALE)/2-40-245, (Game.HEIGHT*Game.SCALE)/2-130, 180, 30);
			
			if(!writeNameP1) {
				if(choice==0 && coluna==0) {
					g.fillRect((Game.WIDTH*Game.SCALE)/2-40-240, (Game.HEIGHT*Game.SCALE)/2-124, 171, 19);
				}
				else {
					g.drawString(Game.player1.getNome(), (Game.WIDTH*Game.SCALE)/2-40-238, (Game.HEIGHT*Game.SCALE)/2-108);
				}
			}
			
			else if(choice==0 && coluna==0 && writeNameP1) {
				g.drawString(Game.playerName, (Game.WIDTH*Game.SCALE)/2-40-238, (Game.HEIGHT*Game.SCALE)/2-108);
			}
			g.drawString("Nome p1", (Game.WIDTH*Game.SCALE)/2-40-200, (Game.HEIGHT*Game.SCALE)/2-75);
			
			
			g.drawRect((Game.WIDTH*Game.SCALE)/2-40+155, (Game.HEIGHT*Game.SCALE)/2-130, 180, 30);

			if(Game.p2) {
				if(!writeNameP2) {
					if(choice==0 && coluna==1) {
						g.fillRect((Game.WIDTH*Game.SCALE)/2-40+160, (Game.HEIGHT*Game.SCALE)/2-124, 171, 19);
					}
					else {
						g.drawString(Game.player2.getNome(), (Game.WIDTH*Game.SCALE)/2-40+160, (Game.HEIGHT*Game.SCALE)/2-108);
					}
				}
				
				else if(choice==0 && coluna==1 && writeNameP2) {
					g.drawString(Game.playerName, (Game.WIDTH*Game.SCALE)/2-40+160, (Game.HEIGHT*Game.SCALE)/2-108);
				}
				g.drawString("Nome p2", (Game.WIDTH*Game.SCALE)/2-40+200, (Game.HEIGHT*Game.SCALE)/2-75);
			}
									
			
			else {
				try {
					Game.player2.setNome("Computador");
				}catch (LimiteNome e) {e.printStackTrace();}
				
				g.setColor(Color.gray);
				g.setFont(new Font("arial", Font.BOLD, 12));
				g.drawString("(Aperte enter para mudar)", (Game.WIDTH*Game.SCALE)/2-95+225, (Game.HEIGHT*Game.SCALE)/2-140);
				g.setFont(new Font("arial", Font.BOLD, 20));
				g.setColor(Color.white);
				if(!Game.dificil) {
					g.drawString("Facil", (Game.WIDTH*Game.SCALE)/2-40+225, (Game.HEIGHT*Game.SCALE)/2-108);
				}
				else {
					g.drawString("Dificil", (Game.WIDTH*Game.SCALE)/2-45+225, (Game.HEIGHT*Game.SCALE)/2-108);
				}
				
				
				if(choice==0 && coluna==1) {
					g.drawString("> Dificuldade <", (Game.WIDTH*Game.SCALE)/2-60+200, (Game.HEIGHT*Game.SCALE)/2-75);
				}
				else {
					g.drawString("< Dificuldade >", (Game.WIDTH*Game.SCALE)/2-60+200, (Game.HEIGHT*Game.SCALE)/2-75);
				}
				
				
				
				g.setColor(Color.white);
			}

			if(choice==1) {
				if(Game.p2) {
					g.drawString("<  P v P  >", (Game.WIDTH*Game.SCALE)/2-50, (Game.HEIGHT*Game.SCALE)/2+55);
				}
				
				else{
					g.drawString("<  P v PC  >", (Game.WIDTH*Game.SCALE)/2-57, (Game.HEIGHT*Game.SCALE)/2+55);
				}
			}
			
			else {			
				if(Game.p2) {
					g.drawString(">P v P<", (Game.WIDTH*Game.SCALE)/2-37, (Game.HEIGHT*Game.SCALE)/2+55);
				}
				
				else{
					g.drawString(">P v PC<", (Game.WIDTH*Game.SCALE)/2-43, (Game.HEIGHT*Game.SCALE)/2+55);
				}				
			}
			
			
			if(choice==2) {				
				if(Game.modoJogo==0) {
					modoJogo = "Modo Normal";
					g.drawString("< "+modoJogo+" >", (Game.WIDTH*Game.SCALE)/2-75, (Game.HEIGHT*Game.SCALE)/2+110);
				}
				else if(Game.modoJogo==1) {
					modoJogo = "Modo Turbo";
					g.drawString("< "+modoJogo+" >", (Game.WIDTH*Game.SCALE)/2-68, (Game.HEIGHT*Game.SCALE)/2+110);
				}
				else if(Game.modoJogo == 2) {
					modoJogo = "Modo Turbo Maluco";
					g.drawString("< "+modoJogo+" >", (Game.WIDTH*Game.SCALE)/2-100, (Game.HEIGHT*Game.SCALE)/2+110);
				}
			}
			
			else {
				if(Game.modoJogo==0) {
					modoJogo = "Modo Normal";
					g.drawString(">"+modoJogo+"<", (Game.WIDTH*Game.SCALE)/2-70, (Game.HEIGHT*Game.SCALE)/2+110);
				}
				else if(Game.modoJogo==1) {
					modoJogo = "Modo Turbo";
					g.drawString(">"+modoJogo+"<", (Game.WIDTH*Game.SCALE)/2-68, (Game.HEIGHT*Game.SCALE)/2+110);
				}
				else if(Game.modoJogo == 2) {
					modoJogo = "Modo Turbo Maluco";
					g.drawString("> "+modoJogo+" <", (Game.WIDTH*Game.SCALE)/2-100, (Game.HEIGHT*Game.SCALE)/2+110);
				}
			}
			
			g.drawString((choice==3? "< Iniciar >" : ">Iniciar<"), (choice==3? (Game.WIDTH*Game.SCALE)/2-48:  (Game.WIDTH*Game.SCALE)/2-40), (Game.HEIGHT*Game.SCALE)/2+165);
			g.drawString((choice==4? "< Retornar >" : ">Retornar<"), (choice==4? (Game.WIDTH*Game.SCALE)/2-60:(Game.WIDTH*Game.SCALE)/2-53), (Game.HEIGHT*Game.SCALE)/2+220);

		}
		
		else if(tela==2) {
			g.setColor(Color.black);
			g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString(">Ranking<", (Game.WIDTH*Game.SCALE)/2-75, (Game.HEIGHT*Game.SCALE)/2-150);
			g.setFont(new Font("arial", Font.BOLD, 20));
			
			if(modoRanking==0) {
				g.drawRect((Game.WIDTH*Game.SCALE)/2-200, (Game.HEIGHT*Game.SCALE)/2-120, 400, 315);
			}
			else {
				g.drawRect((Game.WIDTH*Game.SCALE)/2-500, (Game.HEIGHT*Game.SCALE)/2-120, 1000, 315);	
			}
			
			try {
				for(int a=0; a<10; a++) {
					if(modoRanking==0) {
						Vencedores v = vencedoresLista.get(a);
						g.drawString((a+1)+"- "+v.getNome()+", "+v.getPontos()+" pontos", (Game.WIDTH*Game.SCALE)/2-180, (Game.HEIGHT*Game.SCALE)/2-90+(a*30));
					}
					else {
						g.drawString((a+1)+"- "+Game.ranking.partidas.get(a)[0]+" vs "+Game.ranking.partidas.get(a)[1]+" = "+
									 Game.ranking.partidas.get(a)[2]+" (Modo: "+Game.ranking.partidas.get(a)[3]+", Dificuldade: "+
									 Game.ranking.partidas.get(a)[4]+")", (Game.WIDTH*Game.SCALE)/2-450, (Game.HEIGHT*Game.SCALE)/2-90+(a*30));		
					}
					noFile = false;
				}
			}
			
			catch (Exception e) {
				if(noFile) {
					g.drawString("Não há partidas", (Game.WIDTH*Game.SCALE)/2-75, (Game.HEIGHT*Game.SCALE)/2-90+(5*27));
				}	
			}
			
			
			if(choice==0) {				
				if(modoRanking==0) {
					modoShowRanking ="Melhores Jogadores";
					g.drawString("< "+modoShowRanking+" >", (Game.WIDTH*Game.SCALE)/2-115, (Game.HEIGHT*Game.SCALE)/2+220);
				}
				else {
					modoShowRanking ="Ùltimas Partidas";
					g.drawString("< "+modoShowRanking+" >", (Game.WIDTH*Game.SCALE)/2-95, (Game.HEIGHT*Game.SCALE)/2+220);
				}
			}
			
			else {
				if(modoRanking==0) {
					modoShowRanking ="Melhores Jogadores";
					g.drawString(">"+modoShowRanking+"<", (Game.WIDTH*Game.SCALE)/2-108, (Game.HEIGHT*Game.SCALE)/2+220);
				}
				else {
					modoShowRanking ="Ùltimas Partidas";
					g.drawString(">"+modoShowRanking+"<", (Game.WIDTH*Game.SCALE)/2-90, (Game.HEIGHT*Game.SCALE)/2+220);
				}
			}
			
			
			if(choice==1) {
				g.drawString("< Voltar >", (Game.WIDTH*Game.SCALE)/2-45, (Game.HEIGHT*Game.SCALE)/2+250);
			}
			else {
				g.drawString(">Voltar<", (Game.WIDTH*Game.SCALE)/2-40, (Game.HEIGHT*Game.SCALE)/2+250);

			}
		}
		
		else if(tela==3) {
			g.setColor(Color.black);
			g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString(">Regras<", (Game.WIDTH*Game.SCALE)/2-75, (Game.HEIGHT*Game.SCALE)/2-150);
			g.setFont(new Font("arial", Font.BOLD, 20));
			
			g.drawRect((Game.WIDTH*Game.SCALE)/2-325, (Game.HEIGHT*Game.SCALE)/2-120, 650, 315);

			g.drawString("1- O objetivo do jogo é colocar 4 peças em sequência", (Game.WIDTH*Game.SCALE)/2-290, (Game.HEIGHT*Game.SCALE)/2-120+(1*30));
			g.drawString("    (Horizontal, Vertical ou Diagonal);", (Game.WIDTH*Game.SCALE)/2-290, (Game.HEIGHT*Game.SCALE)/2-120+(2*30));
			g.drawString("2- O jogo pode ser jogado por 2 jogadores ou um jogador", (Game.WIDTH*Game.SCALE)/2-290, (Game.HEIGHT*Game.SCALE)/2-120+(3*30));
			g.drawString("    contra uma IA de dois níveis de dificuldade;", (Game.WIDTH*Game.SCALE)/2-290, (Game.HEIGHT*Game.SCALE)/2-120+(4*30));
			g.drawString("3- Esta versão possui 3 modos de jogo:", (Game.WIDTH*Game.SCALE)/2-290, (Game.HEIGHT*Game.SCALE)/2-120+(5*30));
			g.drawString("*- Modo Normal (As peças jogadas não podem mudar)", (Game.WIDTH*Game.SCALE)/2-260, (Game.HEIGHT*Game.SCALE)/2-120+(6*30));
			g.drawString("*- Modo Turbo (Ao jogar uma peça as suas vizinhas", (Game.WIDTH*Game.SCALE)/2-260, (Game.HEIGHT*Game.SCALE)/2-120+(7*30));
			g.drawString("   laterais se tornarão iguais a ela)", (Game.WIDTH*Game.SCALE)/2-260, (Game.HEIGHT*Game.SCALE)/2-120+(8*30));
			g.drawString("*- Modo Turbo Maluco (Ao jogar uma peça há uma chance", (Game.WIDTH*Game.SCALE)/2-260, (Game.HEIGHT*Game.SCALE)/2-120+(9*30));
			g.drawString("   de uma peça ao seu redor se tornar igual a ela)", (Game.WIDTH*Game.SCALE)/2-260, (Game.HEIGHT*Game.SCALE)/2-120+(10*30));
			
	
			g.drawString("< Voltar >", (Game.WIDTH*Game.SCALE)/2-45, (Game.HEIGHT*Game.SCALE)/2+250);

		}
		
	}

}
