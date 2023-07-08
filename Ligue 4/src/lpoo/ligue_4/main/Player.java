package lpoo.ligue_4.main;

public class Player{

	
	private int tipo;
	private String nome="";
	
	
	
	public Player(int tipo) {
		this.tipo = tipo;
	}
	
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getTipo() {
		return this.tipo;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return this.nome;
	}
}
	
	
