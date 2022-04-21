package valor;

import java.util.ArrayList;
import java.util.List;

public class Pacote {
	private double volumeTotal;
	private List<Produto> produtos;
	
	public Pacote(double volumeTotal) {
		
		this.volumeTotal = volumeTotal;
	}
	
	public Pacote() {
		this.volumeTotal = 5000;
		this.produtos = new ArrayList<Produto>();
	}
	
	public void inserirProduto(Produto produto) {
		this.produtos.add(produto);
	}
	
	public boolean cheio() {
		if(this.vazio()) return false;
		Produto aux = this.produtos.get(0);
		if(aux.getVolume() * produtos.size() == this.volumeTotal)
			return true;
		return false;
	}

	public boolean vazio(){
		return (this.produtos.size() == 0) ? true : false;
	}

	public double getVolumeAtual(){
		double volume = 0;
		for(int i = 0; i < this.produtos.size(); i++){
			volume += this.produtos.get(i).getVolume();
		}
		return volume;
	}

	public double getVolumeTotal(){
		return this.volumeTotal;
	}
}
