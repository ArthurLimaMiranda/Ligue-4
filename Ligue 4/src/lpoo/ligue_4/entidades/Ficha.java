package lpoo.ligue_4.entidades;

import java.awt.image.BufferedImage;

public class Ficha extends Entity{
	
	private int modelo;
	private int[] id = new int[2];
	
	
	public Ficha(int modelo, int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.modelo = modelo;
	}
	
	public void setModelo(int modelo) {
		this.modelo = modelo;
	}
	
	public int getModelo() {
		return this.modelo;
	}
	
	public void setID(int coluna, int linha) {
		this.id[0] = coluna;
		this.id[1] = linha;
	}

	public int[] getID() {
		return this.id;
	}
	
}
