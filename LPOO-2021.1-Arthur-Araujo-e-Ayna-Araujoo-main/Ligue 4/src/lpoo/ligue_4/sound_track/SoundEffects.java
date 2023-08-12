package lpoo.ligue_4.sound_track;

import javax.swing.JLayer;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class SoundEffects{
	
	  public static void playMP3WithTimeout(String filePath, long timeoutInMillis) {
	        Thread playbackThread = new Thread(() -> {
	            try {
	                FileInputStream fis = new FileInputStream(filePath);
	                Player player = new Player(fis);

	                System.out.println("Tocando arquivo: " + filePath);

	                player.play();

	                System.out.println("Término da reprodução.");

	            } catch (FileNotFoundException e) {
	                System.out.println("Arquivo não encontrado: " + filePath);
	            } catch (JavaLayerException e) {
	                System.out.println("Erro ao reproduzir o arquivo MP3: " + e.getMessage());
	            }
	        });

	        playbackThread.start();

	        try {
	            playbackThread.join(timeoutInMillis); // Aguarda até que a thread termine ou o timeout expire
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        if (playbackThread.isAlive()) {
	            // Se a thread ainda estiver ativa após o timeout, interrompa-a
	            playbackThread.interrupt();
	            System.out.println("Reprodução interrompida devido ao tempo limite de " + timeoutInMillis + " milissegundos.");
	        }
	    }
	}
	


