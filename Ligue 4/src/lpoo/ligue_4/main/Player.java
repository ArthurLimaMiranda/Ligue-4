package lpoo.ligue_4.main;

public class Player{

	
	boolean P1 = true; // Player 1 que sempre existe;
	boolean P2, PC ; //  Player 2 e Pc;
	
	public boolean setP2() {
		
		if(PC == true) {			
			P2 = false;
		}	
		return P2;
	}
	
	public void getP2(boolean P2) {
		
		this.P2 = P2;	
	}
	
}
