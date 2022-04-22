package maquinas;

import java.util.ArrayList;
import java.util.List;
import valor.Produto;

public class Esteira {
	private static final double VOLUME_LIMITE = 5000;
	private List<Produto> produtos;
	private String nome;

	public Esteira(String nome,List<Produto> produtos) {
		this.setNome(nome);
		this.produtos = new ArrayList<Produto>();
		this.produtos = produtos;
	}

	public Esteira(String nome) {
		this.setNome(nome);
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

	public List<Produto> getProdutos() {
		return this.produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
