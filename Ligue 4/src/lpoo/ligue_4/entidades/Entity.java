package lpoo.ligue_4.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Entity {
	private int x;
	private int y;
	private int width;
	private int height;
	
	private BufferedImage sprite;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}
	
	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}
	
	public  BufferedImage getSprite() {
		return this.sprite;
	}
	
	public int getX() {
		return this.x;
	}
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return this.y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth() {
		return this.width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return this.height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX(), this.getY(), null);
	}
	
	public void update() {
		
	}
}


