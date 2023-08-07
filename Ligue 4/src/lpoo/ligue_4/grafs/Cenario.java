package lpoo.ligue_4.grafs;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Cenario {

	public Cenario(String path) {
		try {
			BufferedImage map  = ImageIO.read(new File(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for(int i=0; i< pixels.length; i++) {
				
			}
		}catch (IOException e) {e.printStackTrace();}
	}
	
}
