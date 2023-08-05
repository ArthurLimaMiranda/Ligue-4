package SoundTrack;

import javax.swing.JLayer;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class SoundEffects{
	
	/*private String Sound_Menu = "/src/Sounds/light-pull-string-32448.mp3";
	private String Sound_Ficha = "/src/Sounds/cash-register-purchase-87313.mp3";
	private String Sound_Win = "/src/Sounds/bright-notifications-151766.mp3";*/

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
	

