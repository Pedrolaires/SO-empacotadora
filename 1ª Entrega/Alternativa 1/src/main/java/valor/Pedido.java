package valor;

import java.util.ArrayList;
import java.util.List;

public class Pedido implements Comparable<Pedido> {
	private List<Produto> produtos;
	private String cliente;
	private int totalDeProdutos;
	private int prazoEmpacotamento;
	
	public Pedido(String cliente, int totalDeProdutos, int prazoEmpacotamento) {
		this.produtos = new ArrayList<Produto>();
		this.cliente = cliente;
		this.totalDeProdutos = totalDeProdutos;
		this.prazoEmpacotamento = prazoEmpacotamento;
	}

	public Pedido(List<Produto> produtos, String cliente, int totalDeProdutos, int prazoEmpacotamento) {
		this.produtos = new ArrayList<Produto>();
		this.produtos = produtos;
		this.cliente = cliente;
		this.totalDeProdutos = totalDeProdutos;
		this.prazoEmpacotamento = prazoEmpacotamento;
	}
	
	public int compareTo(Pedido o) {
		if(o.getPrazoEmpacotamento() > this.getPrazoEmpacotamento()) {
			if(this.haPrazo()) {
				return -1;
			}
			return 1;
		}else if(o.getPrazoEmpacotamento() < this.getPrazoEmpacotamento()) {
			if(o.haPrazo()) {
				return 1;
			}
		return -1;
		}
		return 0;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public int getTotalDeProdutos() {
		return totalDeProdutos;
	}

	public void setTotalDeProdutos(int totalDeProdutos) {
		this.totalDeProdutos = totalDeProdutos;
	}

	public int getPrazoEmpacotamento() {
		return prazoEmpacotamento;
	}

	public void setPrazoEmpacotamento(int prazoEmpacotamento) {
		this.prazoEmpacotamento = prazoEmpacotamento;
	}
	
	public boolean haPrazo() {
		if(this.prazoEmpacotamento == 0) 
			return false;
		return true;
	}
	
	public int paraInteiro(String string) {		
		return Integer.parseInt(string);
	}
	
}
