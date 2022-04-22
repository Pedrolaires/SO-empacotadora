package valor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Pedido implements Comparable<Pedido> {
	private List<Produto> produtos;
	private String cliente;
	private int totalDeProdutos;
	private int prazoEmpacotamento;
	private boolean finalizado;
	private List<Pacote> pacotes;

	public Pedido(String cliente, int totalDeProdutos, int prazoEmpacotamento) {
		this.produtos = new ArrayList<Produto>();
		this.cliente = cliente;
		this.totalDeProdutos = totalDeProdutos;
		this.prazoEmpacotamento = prazoEmpacotamento;
		this.finalizado = false;
		this.pacotes = new ArrayList<Pacote>();
	}

	public Pedido(List<Produto> produtos, String cliente, int totalDeProdutos, int prazoEmpacotamento) {
		this.produtos = new ArrayList<Produto>();
		this.produtos = produtos;
		this.cliente = cliente;
		this.totalDeProdutos = totalDeProdutos;
		this.prazoEmpacotamento = prazoEmpacotamento;
		this.finalizado = false;
		this.pacotes = new ArrayList<Pacote>();
	}

	public List<Pacote> getPacotes() {
		return pacotes;
	}

	public void setPacote(Pacote Pacotes) {
		this.pacotes.add(Pacotes);
	}

	public int compareTo(Pedido o) {
		if (o.getPrazoEmpacotamento() > this.getPrazoEmpacotamento()) {
			if (this.haPrazo()) {
				return -1;
			}
			return 1;
		} else if (o.getPrazoEmpacotamento() < this.getPrazoEmpacotamento()) {
			if (o.haPrazo()) {
				return 1;
			}
			return -1;
		}
		return 0;
	}

	public boolean isFinalizado() {
		return finalizado;
	}

	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
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
		if (this.prazoEmpacotamento == 0)
			return false;
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (totalDeProdutos != other.totalDeProdutos)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Iterator<Pacote> pacote = this.pacotes.iterator();
		int i = 1;
		if(this.pacotes.size() > 0) {
			while(pacote.hasNext()) {
				Iterator<Produto> produto = pacote.next().getProdutos().iterator();
				while(produto.hasNext()) {
					sb.append("Produto " + i + ": " + produto.next().toString() + "\n");
					i++;
				}
			}
		}
		return sb.toString();		
	}
}
