package util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import maquinas.Esteira;
import valor.Pedido;
import valor.Produto;

public class Relatorio {
	private ArquivoTextoEscrita arquivo;
	
	
	public Relatorio(String caminho) {
		arquivo = new ArquivoTextoEscrita();
		arquivo.openFile(caminho);
	}

	/**
	 * Cria um log na pasta de relatorios com base na passagem de produtos pela esteira
	 * @param esteira
	 * @param produto
	 * @param p
	 */
	public synchronized void criarLogEsteira(MyTimer timer,Esteira esteira, Produto produto, Pedido pedido) {
		Lock lock = new ReentrantLock();
		StringBuilder sb = new StringBuilder();
		
		sb.append("["+ timer.getHora() + "h, " + timer.getMinuto() + "min, "+ timer.getSegundo() + "seg] "+ esteira.getNome() +": Produto: " + produto.getIndice() +
				" do cliente: *" + pedido.getCliente() + "* rolou!");
		
		lock.lock();
		arquivo.write(sb.toString());
		lock.unlock();
	}
	
	/**
	 * Relatorio final
	 */
	public void gerarRelatorioFinal(MyTimer timer, int tempoMedioPedido, int qtdPedidosFeitosAte12h) {
		Lock lock = new ReentrantLock();
		StringBuilder sb = new StringBuilder();
		
		sb.append("# Relatório final de uma rotina de trabalho se iniciando ás 08:00 e com término ás 17:00\n"
				+ "## Dados coletados através de simulações computacionais!\n"
				+ "### Utilizamos uma fila de prioridade para priorizar os pedidos com prazo mais curto"
				+ "\n____________________________________________________________________________________\n\n"
				+ "Tempo médio na realização de cada pedido: " + tempoMedioPedido/60 + "min\n"
				+ "Pedidos finalizados até as 12horas: " + qtdPedidosFeitosAte12h);
		
		lock.lock();
		arquivo.write(sb.toString());
		lock.unlock();
	}
	
	/**
	 * Cria o relat�rio de pedidos conclu�dos at� o meio dia
	 * @param p
	 */
	public void criarRelatorio12h(MyTimer timer, Pedido p, int qtdPedidosFeitos) {
		 Lock lock = new ReentrantLock();
		 
		StringBuilder sb = new StringBuilder();
		
		
		sb.append("\n## " + qtdPedidosFeitos + " - ["+ timer.getHora() + "h, " + timer.getMinuto() + "min, "+ timer.getSegundo() + "seg]\n### Nome: " + p.getCliente() +", status: " + (p.isFinalizado() ? "Finalizado" : "Nao finalizado!"));
		sb.append("\n### Quantidade de pacotes utilizados: " + p.getPacotes().size());
		sb.append("\n_____________________________________________\n");	
		
		lock.lock();
		arquivo.write(sb.toString());
		lock.unlock();
	}
	
	/**
	 * Cria o log contendo os pedidos, o cliente e o hor�rio.
	 * @param p
	 */
	public void criarLogPedidos(MyTimer timer, Pedido p, String nomeEquipamento, int qtdPedidosFeitos) {
		Lock lock = new ReentrantLock();
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("\n## " + qtdPedidosFeitos + " - ["+ timer.getHora() + "h, " + timer.getMinuto() + "min, "+ timer.getSegundo() + "seg]\n### Nome: " + p.getCliente() +", status: " + (p.isFinalizado() ? "Finalizado" : "N�o finalizado!"));
		sb.append("\nEquipamento utilizado: " + nomeEquipamento);
		sb.append("\nPedido realizado " + (p.getChegada().getHora() == 0  && p.getChegada().getMinuto() == 0 ? "Antecipadamente" : " as "+ p.getChegada().getHora() + "h"));		
		sb.append("\nQuantidade de pacotes utilizados: " + p.getPacotes().size());
		sb.append("\n" + p.toString());
		sb.append("\n_____________________________________________");
		
		lock.lock();
		arquivo.write(sb.toString());
		lock.unlock();
	}
	
	public void criarLogResumidoPedidos(MyTimer timer, Pedido p, String nomeEquipamento, int qtdPedidosFeitos) {
		Lock lock = new ReentrantLock();
		
		StringBuilder sb = new StringBuilder();
		sb.append("\n## " + qtdPedidosFeitos + " - ["+ timer.getHora() + "h, " + timer.getMinuto() + "min, "+ timer.getSegundo() + "seg]\n### Nome: " + p.getCliente() +", status: " + (p.isFinalizado() ? "Finalizado" : "N�o finalizado!"));
		sb.append("\nEquipamento utilizado: " + nomeEquipamento);
		sb.append("\nPedido realizado " + (p.getChegada().getHora() == 0  && p.getChegada().getMinuto() == 0 ? "Antecipadamente" : " as "+ p.getChegada().getHora() + "h"));
		sb.append("\nQuantidade de pacotes utilizados: " + p.getPacotes().size());
		sb.append("\n_____________________________________________");
		
		lock.lock();
		arquivo.write(sb.toString());
		lock.unlock();
	}
	public synchronized void fecharArquivo() {
		arquivo.closeFile();
	}
}
