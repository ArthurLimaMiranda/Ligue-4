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
