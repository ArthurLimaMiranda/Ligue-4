package lpoo.ligue_4.main;

import lpoo.ligue_4.exceptions.LimiteNome;

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
	
	public void setNome(String nome) throws LimiteNome{
		
		if(nome.length() > 12) {
			LimiteNome e = new LimiteNome();
			throw e;
		}
		else {
			this.nome = nome;
			}
						
	}

	public String getNome() {
		return this.nome;
	}
}
	
	
