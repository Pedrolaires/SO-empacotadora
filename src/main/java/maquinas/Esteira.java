package maquinas;

public class Esteira {
	private static final double VOLUME_LIMITE = 5000;
	private int qtdProdutos;
	
	Esteira(int qtdProdutos){
		this.qtdProdutos = qtdProdutos;
	}
	
	public boolean rolarProdutos() {
		return true;
	}
	
	public boolean vazia() {
		if(this.qtdProdutos==0) 
			return true;
		else
			return false;		
	}
	
	public boolean retirarProduto() {
		if (this.qtdProdutos>0) {
			this.qtdProdutos--;
			return true;
		}else
			return false;
		
	}
	
	public int getQtdProdutos() {
		return qtdProdutos;
	}

	public void setQtdProdutos(int qtdProdutos) {
		this.qtdProdutos = qtdProdutos;
	}
}
