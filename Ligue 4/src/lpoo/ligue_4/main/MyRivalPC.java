package lpoo.ligue_4.main;


import java.util.Random;

public class MyRivalPC {
	
	private Random random;
	private int ColunaRival;
	
	Tabuleiro tabuleiro;
	
	
	public int EasyPeasy(int coluna) throws BusaoLotado{
				
		ColunaRival = random.nextInt(7);
		
		
		if(tabuleiro.getBuxinCheio(ColunaRival)== true) {
			
			while(tabuleiro.getBuxinCheio(ColunaRival)== true) {			
				ColunaRival = random.nextInt(7);
				BusaoLotado e = new BusaoLotado();
				throw e;
			}			
		}
											
		return ColunaRival;
		
	}
	
	public int DontLetUWin(int coluna) throws BusaoLotado,UShouldNotBeHere{
		
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
			if(tabuleiro.getBuxinCheio(ColunaRival)== true) {
				
				while(tabuleiro.getBuxinCheio(ColunaRival)== true) {			
					ColunaRival =  coluna + random.nextInt(3) - 1;
					BusaoLotado e = new BusaoLotado();
					throw e;
				}
		}
			
		}
											
		return ColunaRival;
		
	}
	
	
	
	
	
	public void INEVERGonnaLetUWin(int coluna) {
		
		//PINTE E O BORDE AQ SO N DEIXE O CARA GANHAR
	}
	
}
