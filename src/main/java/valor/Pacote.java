package valor;

import java.util.List;

public class Pacote {
	private double volumeTotal;
	private List<Produto> produtos;
	
	public Pacote(double volumeTotal) {
		
		this.volumeTotal = volumeTotal;
	}
	
	public Pacote() {
		this.volumeTotal = 5000;
	}
	
	public void inserirProduto(Produto produto) {
		this.produtos.add(produto);
	}
	
	public boolean cheio() {
		Produto aux = this.produtos.get(1);
		if(aux.getVolume() * produtos.size() == this.volumeTotal)
			return true;
		return false;
	}
}
