package lpoo.ligue_4.grafs;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import lpoo.ligue_4.main.Game;

public  class Tile {
	public static BufferedImage TILE_MURO = Game.spritesheetGame.getSprite(32*0, 32*1, 32, 32);
	public static BufferedImage TILE_MUROTOPO = Game.spritesheetGame.getSprite(32*1, 32*1, 32, 32);
	public static BufferedImage TILE_MUROTOPOGATO1 = Game.spritesheetGame.getSprite(32*2, 32*1, 32, 32);
	public static BufferedImage TILE_MUROTOPOGATO2 = Game.spritesheetGame.getSprite(32*3, 32*1, 32, 32);
	public static BufferedImage TILE_MUROTOPOGATO3 = Game.spritesheetGame.getSprite(32*2, 32*2, 32, 32);
	public static BufferedImage TILE_MUROTOPOGATO4 = Game.spritesheetGame.getSprite(32*3, 32*2, 32, 32);
	
	public static BufferedImage TILE_MUROTOPOARVORE1 = Game.spritesheetGame.getSprite(32*0, 32*3, 32, 32);
	public static BufferedImage TILE_MUROTOPOARVORE2 = Game.spritesheetGame.getSprite(32*1, 32*3, 32, 32);
	public static BufferedImage TILE_MUROTOPOARVORE3 = Game.spritesheetGame.getSprite(32*2, 32*3, 32, 32);
	public static BufferedImage TILE_MUROTOPOARVORE4 = Game.spritesheetGame.getSprite(32*3, 32*3, 32, 32);
	
	public static BufferedImage TILE_MUROTOPOARVORE5 = Game.spritesheetGame.getSprite(32*0, 32*4, 32, 32);
	public static BufferedImage TILE_MUROTOPOARVORE6 = Game.spritesheetGame.getSprite(32*1, 32*4, 32, 32);
	public static BufferedImage TILE_MUROTOPOARVORE7 = Game.spritesheetGame.getSprite(32*2, 32*4, 32, 32);
	public static BufferedImage TILE_MUROTOPOARVORE8 = Game.spritesheetGame.getSprite(32*3, 32*4, 32, 32);
	public static BufferedImage TILE_MUROTOPOARVORE9 = Game.spritesheetGame.getSprite(32*4, 32*4, 32, 32);
	
	public static BufferedImage TILE_MUROTOPOARVORE10 = Game.spritesheetGame.getSprite(32*0, 32*5, 32, 32);
	public static BufferedImage TILE_MUROTOPOARVORE11 = Game.spritesheetGame.getSprite(32*1, 32*5, 32, 32);
	public static BufferedImage TILE_MUROTOPOARVORE12 = Game.spritesheetGame.getSprite(32*2, 32*5, 32, 32);
	public static BufferedImage TILE_MUROTOPOARVORE13 = Game.spritesheetGame.getSprite(32*3, 32*5, 32, 32);
	public static BufferedImage TILE_MUROTOPOARVORE14 = Game.spritesheetGame.getSprite(32*4, 32*5, 32, 32);
	
	public static BufferedImage TILE_MUROTOPOARVORE15 = Game.spritesheetGame.getSprite(32*0, 32*6, 32, 32);
	public static BufferedImage TILE_MUROTOPOARVORE16 = Game.spritesheetGame.getSprite(32*1, 32*6, 32, 32);
	public static BufferedImage TILE_MUROTOPOARVORE17 = Game.spritesheetGame.getSprite(32*2, 32*6, 32, 32);
	public static BufferedImage TILE_MUROTOPOARVORE18 = Game.spritesheetGame.getSprite(32*3, 32*6, 32, 32);
	
	
	public static BufferedImage TILE_SKY = Game.spritesheetGame.getSprite(32*0, 32*2, 32, 32);
	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x, y, null);
	}
}
