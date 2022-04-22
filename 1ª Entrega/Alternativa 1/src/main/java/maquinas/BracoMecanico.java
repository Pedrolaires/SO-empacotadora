package maquinas;

import valor.Produto;

public class BracoMecanico {
	private double TEMPO_DEPRODUCAO = 5;
	
	public BracoMecanico() {}
	
	public double getTempoDeProducao() {
		return this.TEMPO_DEPRODUCAO;
	}
	
	public boolean empacotarProduto(Produto produto) {
		return produto.setEmpacotado(true);
	}
	
	public boolean removerEmbalagem() {
		return true;
	}
	
}
