package lpoo.ligue_4.grafs;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Cenario {
	
	private Tile[] tiles;
	public static int WIDTH, HEIGHT;
	
	public Cenario(String path) {
		try {
			BufferedImage map  = ImageIO.read(new File(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			
			for(int xx=0; xx<map.getWidth(); xx++) {
				for(int yy=0; yy<map.getHeight(); yy++) {
					int pixelAt = xx+(yy*map.getWidth());
					if(pixels[pixelAt]==0xFFFF0000) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_SKY);
					}
					else if(pixels[pixelAt]==0xFF000000) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MURO);
					}
				}
			}
		}catch (IOException e) {e.printStackTrace();}
	}
	public void render(Graphics g) {
		for(int xx=0; xx<WIDTH; xx++){
			for(int yy=0; yy<HEIGHT; yy++) {
				Tile tile = tiles[xx+(yy*WIDTH)];
				tile.render(g);
			}
		}
	}
}
