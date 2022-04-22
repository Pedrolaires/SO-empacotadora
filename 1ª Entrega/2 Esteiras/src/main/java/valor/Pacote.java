package valor;

import java.util.ArrayList;
import java.util.List;

public class Pacote {
	private double volumeTotal;
	private List<Produto> produtos;

	public Pacote(double volumeTotal) {
		this.produtos = new ArrayList<Produto>();
		this.volumeTotal = volumeTotal;
	}

	public Pacote() {
		this.produtos = new ArrayList<Produto>();
		this.volumeTotal = 5000;
	}
	
	public List<Produto> getProdutos(){
		return this.produtos;
	}

	public void inserirProduto(Produto produto) {
		if (!estaCheio())
			this.produtos.add(produto);
	}

	public boolean estaCheio() {
		if(this.produtos.size() > 0) {
			Produto aux = this.produtos.get(0);
			if (aux.getVolume() * produtos.size() >= this.volumeTotal)
				return true;			
		}
		return false;
	}
}
