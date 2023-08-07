package lpoo.ligue_4.main;

import com.opencsv.CSVWriter;
import java.io.FileNotFoundException;
import com.opencsv.CSVReader;
import java.io.Reader;
import com.opencsv.CSVReaderBuilder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Ranking {
	
	public ArrayList<String[]> partidas;
	private String[] tipoJogo = {"Normal", "Turbo", "Turbo Maluco"};
	private String fileName = "res/ranking.csv";
	
	public Ranking() {
		partidas = new ArrayList<String[]>();
	}
	
	public void GeraRanking() throws FileNotFoundException, IOException {
		String[] entries = { "Jogador1", "Jogador2", "Vencedor", "Modo", "Dificuldade" };
		String[] lastPartida = {Game.player1.getNome(), Game.player2.getNome(),
								(Game.vitP1?Game.player1.getNome():(Game.vitP2?Game.player2.getNome():"Empate")), 
								tipoJogo[Game.modoJogo], (Game.p2?"PvP":(Game.dificil?"Dificil":"Facil"))};
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
	
	public void LerRanking() throws IOException {

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
