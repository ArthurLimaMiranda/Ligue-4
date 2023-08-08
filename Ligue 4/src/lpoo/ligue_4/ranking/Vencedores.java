package lpoo.ligue_4.ranking;

public class Vencedores{
	
	private String nome;
	private int pontos=1;
	
	public Vencedores(String nome) {
		this.nome = nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public void somarPontos() {
		this.pontos++;
	}
	
	public int getPontos() {
		return this.pontos;
	}
}
