package lpoo.ligue_4.sound_track;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javazoom.jl.player.Player;

public class SoundGame implements Runnable {
    private String filePath;
    private Player player;
    private boolean isPlaying;

    public SoundGame(String filePath) {
        this.filePath = filePath;
        this.isPlaying = false;
    }

    public void play() {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
            isPlaying = true;

            new Thread(this).start();
        } catch (Exception e) {
            System.out.println("Erro ao reproduzir o áudio: " + e);
        }
    }

    public void stop() {
        if (isPlaying) {
            player.close();
            isPlaying = false;
        }
    }

    @Override
    public void run() {
   	 try {
            while (isPlaying) {
                player.play();
                // Reinicia a reprodução do áudio quando chegar ao final
                FileInputStream fis = new FileInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(fis);
                player = new Player(bis);
            }
        } catch (Exception e) {
            System.out.println("Erro ao reproduzir o áudio: " + e);
        }
   }
}