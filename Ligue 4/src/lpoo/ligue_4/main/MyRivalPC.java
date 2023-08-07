package lpoo.ligue_4.main;


import java.util.Random;

import lpoo.ligue_4.board.Tabuleiro;
import lpoo.ligue_4.board.Tabuleiro_Turbo;
import lpoo.ligue_4.exceptions.BusaoLotado;
import lpoo.ligue_4.exceptions.UShouldNotBeHere;

public class MyRivalPC {
	
	private Random random = new Random();
	private int colunaRival, colunaTemp;
	
	public MyRivalPC() {
		colunaTemp = random.nextInt(7);
	}	
	public void TaCheio (boolean status) throws BusaoLotado {
		if(status) {
			BusaoLotado e = new BusaoLotado();
			throw e;
		}
	}
	
	public int BLOCKYOU(int coluna) {
		
		try {
			Tabuleiro_Turbo.ChecaCanto(coluna);
			colunaRival = random.nextInt(3) - 1 + coluna ;
			while(colunaRival == 0) {
				
				colunaRival = random.nextInt(3) -1 + coluna;
			}
		} catch (UShouldNotBeHere e) {
			e.printStackTrace();
			colunaRival = random.nextInt(3) - 1 + coluna ;
		}
		
		
		
		return colunaRival;
	}
	
	public int EasyPeasy(boolean[] colunasStatus, int coluna){
			
		try {
			Tabuleiro_Turbo.ChecaCanto(coluna);
			colunaRival = random.nextInt(3)- 1  + coluna;
			while (true){
				try {
					TaCheio(colunasStatus[colunaRival]);
					break;
				} 
				catch (BusaoLotado e) {
					e.printStackTrace();
					colunaRival = random.nextInt(3)-1  + coluna;
				}
			}
		} catch (UShouldNotBeHere e) {
			e.printStackTrace();
			colunaRival = random.nextInt(3)-1  + coluna;
		}
		
		
										
		return colunaRival;	
	}
	
	public int INEVERGonnaLetUWin(int[][] tabuleiro, int Height, int Width, boolean[] colunasStatus){
		
		int slot01, slot02, slot03, slot04;	
		
		
		
		for (int y = 0 ; y < Height -3 ; y++) {						
			for(int x = 0; x < Width ; x++ ) {
				slot01 = tabuleiro[x][y];
				slot02 = tabuleiro[x][y+1];
				slot03 = tabuleiro[x][y+2];
				slot04 = tabuleiro[x][y+3];
				
				if((x==colunaTemp) && (slot01==0) && (slot02==slot03) && (slot03==slot04) && (slot04==3)) {
					colunaRival = colunaTemp;
					return colunaRival;
				}

				if ((slot01==0) && (slot02==slot03) && (slot03==slot04) && (slot04==1)) {
					colunaRival = x;
					return colunaRival;					
				}
			}
		}		
		for(int y=0; y<Height; y++) {
			for (int x = 0 ; x < Width-3 ; x ++) {						
				slot01 = tabuleiro[x][y];
				slot02 = tabuleiro[x+1][y];
				slot03 = tabuleiro[x+2][y];
				slot04 = tabuleiro[x+3][y];

				if((y==Height-1) || (tabuleiro[x][y+1]!=0)) {
					if ((slot04==0) && (slot01!=0) && (slot01==slot02) && (slot02==slot03)) {
						colunaRival = x+3;
						return colunaRival;
					}
					
					else if ((slot01==0) && (slot02!=0) && (slot02==slot03) && (slot02==slot04)) {
						colunaRival = x;
						return colunaRival;
					}
					
					else if ((slot02==0) && (slot01!=0) && (slot01==slot03) && (slot03==slot04)) {
						colunaRival = x+1;
						return colunaRival;
					}
					
					else if ((slot03==0) && (slot01!=0) && (slot01==slot02) && (slot02==slot04)) {
						colunaRival = x+2;
						return colunaRival;
					}
				}
			}
		}
		
		for(int yTemp = 0; yTemp < Height-3 ; yTemp++ ) {
			slot01 = tabuleiro[colunaTemp][yTemp];
			slot02 = tabuleiro[colunaTemp][yTemp+1];
			slot03 = tabuleiro[colunaTemp][yTemp+2];
			slot04 = tabuleiro[colunaTemp][yTemp+3];
			
			if ((slot03==0) && (slot04==3)) {
				colunaRival = colunaTemp;
				return colunaRival;
			}
		}
		colunaTemp = random.nextInt(7);
		while (true){
			try {
				TaCheio(colunasStatus[colunaTemp]);
				break;
			} 
			catch (BusaoLotado e) {
				e.printStackTrace();
				colunaTemp = random.nextInt(7);
			}
		}
		colunaRival = colunaTemp;
			
		return colunaRival;	
	}	
}
