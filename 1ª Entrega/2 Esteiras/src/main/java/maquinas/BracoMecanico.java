package maquinas;

import valor.Pedido;
import valor.Produto;

public class BracoMecanico {
	private final double TEMPO_DE_PRODUCAO = 5;

	public BracoMecanico() {
	}

	public double getTempoDeProducao() {
		return this.TEMPO_DE_PRODUCAO;
	}

	public Produto empacotarProduto(Produto produto) {
		produto.setEmpacotado(true);
		return produto;
	}

	public boolean removerPacote(Pedido p) {
		int count = 0;
		for (int i = 0; i < p.getProdutos().size(); i++) {
			if(p.getProdutos().get(i).estaEmpacotado()) {
				count++;
			}
		}
		if(count == p.getProdutos().size()) {
			p.setFinalizado(true);
		}
		return true;
	}
}
