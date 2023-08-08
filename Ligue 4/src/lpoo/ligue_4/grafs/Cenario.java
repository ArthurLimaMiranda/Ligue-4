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
					else if(pixels[pixelAt]==0xFF0026FF) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MUROTOPO);
					}
					else if(pixels[pixelAt]==0xFF7F593F) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MUROTOPOGATO1);
					}
					else if(pixels[pixelAt]==0xFFFFD800) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MUROTOPOGATO2);
					}
					else if(pixels[pixelAt]==0xFFFFE97F) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MUROTOPOGATO3);
					}
					else if(pixels[pixelAt]==0xFF7F6A00) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MUROTOPOGATO4);
					}
					
					
					else if(pixels[pixelAt]==0xFFA17FFF) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MUROTOPOARVORE1);
					}
					else if(pixels[pixelAt]==0xFF503F7F) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MUROTOPOARVORE2);
					}
					else if(pixels[pixelAt]==0xFF606060) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MUROTOPOARVORE3);
					}
					else if(pixels[pixelAt]==0xFF4CFF00) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MUROTOPOARVORE4);
					}
					else if(pixels[pixelAt]==0xFF267F00) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MUROTOPOARVORE5);
					}
					else if(pixels[pixelAt]==0xFF7FFF8E) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MUROTOPOARVORE6);
					}
					else if(pixels[pixelAt]==0xFF3F7F47) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MUROTOPOARVORE7);
					}
					else if(pixels[pixelAt]==0xFF0094FF) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MUROTOPOARVORE8);
					}
					else if(pixels[pixelAt]==0xFF004A7F) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MUROTOPOARVORE9);
					}
					else if(pixels[pixelAt]==0xFF7FC9FF) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MUROTOPOARVORE10);
					}
					else if(pixels[pixelAt]==0xFF3F647F) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MUROTOPOARVORE11);
					}
					else if(pixels[pixelAt]==0xFFFF006E) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MUROTOPOARVORE12);
					}
					else if(pixels[pixelAt]==0xFF7F0037) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MUROTOPOARVORE13);
					}
					else if(pixels[pixelAt]==0xFFFF7FB6) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MUROTOPOARVORE14);
					}
					else if(pixels[pixelAt]==0xFFFF7F7F) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MUROTOPOARVORE15);
					}
					else if(pixels[pixelAt]==0xFF7F3F3F) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MUROTOPOARVORE16);
					}
					else if(pixels[pixelAt]==0xFFFF3F5A) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MUROTOPOARVORE17);
					}
					else if(pixels[pixelAt]==0xFF7F3F5B) {
						tiles[pixelAt] = new Tile(xx*32,yy*32, Tile.TILE_MUROTOPOARVORE18);
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
