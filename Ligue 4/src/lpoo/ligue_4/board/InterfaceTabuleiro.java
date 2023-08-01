package lpoo.ligue_4.board;

import java.awt.Graphics;

import lpoo.ligue_4.exceptions.BusaoLotado;

public interface InterfaceTabuleiro {
	
	public abstract void update() throws BusaoLotado;
	
	public abstract void render(Graphics g);

	public abstract int ChecarWin(int linha, int coluna);

}
