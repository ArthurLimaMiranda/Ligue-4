package lpoo.ligue_4.main;


import java.util.Random;

import lpoo.ligue_4.board.Tabuleiro;
import lpoo.ligue_4.exceptions.BusaoLotado;
import lpoo.ligue_4.exceptions.UShouldNotBeHere;

public class MyRivalPC {
	
	private Random random = new Random();
	private int ColunaRival;
	
	
	public int EasyPeasy(boolean[] colunasStatus) throws BusaoLotado{
			
		ColunaRival = random.nextInt(7);
		if(colunasStatus[ColunaRival]== true) {			
			while(colunasStatus[ColunaRival]== true) {			
				ColunaRival = random.nextInt(7);
				BusaoLotado e = new BusaoLotado();
				throw e;
			}			
		}
											
		return ColunaRival;
		
	}
	
	public int INEVERGonnaLetUWin(int coluna, boolean[] colunasStatus) throws BusaoLotado,UShouldNotBeHere{
		
		ColunaRival = coluna + random.nextInt(3) - 1;		
		
		if(ColunaRival <= 0) {	
			 UShouldNotBeHere e = new UShouldNotBeHere();
			 throw e;
		}
		else if(ColunaRival >= 7) {
			 UShouldNotBeHere e = new UShouldNotBeHere();
			 throw e;
		}
		else {
			if(colunasStatus[ColunaRival]== true) {
				
				while(colunasStatus[ColunaRival]== true) {			
					ColunaRival =  coluna + random.nextInt(3) - 1;
					BusaoLotado e = new BusaoLotado();
					throw e;
				}
		}
			
		}
											
		return ColunaRival;
		
	}	
}
