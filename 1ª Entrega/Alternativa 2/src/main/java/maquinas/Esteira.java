package maquinas;

import java.util.ArrayList;
import java.util.List;
import valor.Produto;

public class Esteira {
	private static final double VOLUME_LIMITE = 5000;
	private List<Produto> produtos;

	public Esteira(List<Produto> produtos) {
		this.produtos = new ArrayList<Produto>();
		this.produtos = produtos;
	}

	public Esteira() {
		this.produtos = new ArrayList<Produto>();
	}

	public Produto rolarProdutos() {
		return this.produtos.remove(0);
	}

	public boolean estaVazia() {
		if (this.produtos.size() > 0)
			return false;
		return true;
	}

	public double getVolumeLimite() {
		return this.VOLUME_LIMITE;
	}

	public List<Produto> getprodutos() {
		return this.produtos;
	}

	public void setprodutos(List produtos) {
		this.produtos = produtos;
	}
}
