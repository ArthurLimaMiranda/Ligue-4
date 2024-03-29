package lpoo.ligue_4.ranking;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import lpoo.ligue_4.main.Game;

public class Ranking {
	public ArrayList<String[]> partidas;
	private String[] tipoJogo = {"Normal", "Turbo", "Turbo Maluco"};
	private String fileName = "res/ranking.csv";
	
	public void geraRanking() throws FileNotFoundException, IOException {
		partidas = new ArrayList<String[]>();
		try {
			LerRanking();
		}
		finally {
			String[] entries = { "Jogador1", "Jogador2", "Vencedor", "Modo", "Dificuldade" };
			String[] lastPartida = {Game.player1.getNome(), Game.player2.getNome(),
									(Game.VITP1?Game.player1.getNome():(Game.VITP2?Game.player2.getNome():"Empate")), 
									tipoJogo[Game.MODOJOGO], (Game.P2?"PvP":(Game.DIFICIL?"Dificil":"Facil"))};
			try (var fos = new FileOutputStream(fileName); 
			var osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
			var writer = new CSVWriter(osw)){
				writer.writeNext(entries);
				writer.writeNext(lastPartida); 
				for(int i=0; i<partidas.size(); i++) {
					lastPartida = partidas.get(i);
					writer.writeNext(lastPartida);	
				}
			}
		}
	}
	
	public void LerRanking() throws IOException {
		
		partidas = new ArrayList<String[]>();
		try (
            Reader reader = Files.newBufferedReader(Paths.get(fileName));
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
			) {
	            // Reading Records One by One in a String array
	            String[] nextRecord;
	            while ((nextRecord = csvReader.readNext()) != null) {
	            	partidas.add(nextRecord);
	            }
		}
	}
}
