package lpoo.ligue_4.entidades;

import java.awt.image.BufferedImage;

public class Ficha extends Entity{
	
	public int modelo;
	
	public Ficha(int modelo, int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.modelo = modelo;
	}
	
	public int getModelo() {
		return this.modelo;
	}
}
