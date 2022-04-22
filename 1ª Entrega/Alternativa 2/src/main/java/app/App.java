package app;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import maquinas.BracoMecanico;
import maquinas.Esteira;
import util.ArquivoTextoEscrita;
import util.MyTimer;
import valor.Pacote;
import valor.Pedido;
import valor.Produto;


public class App {
	
	static MyTimer timer = new MyTimer(8,0,0);
	static double tempoMedioPorPedido;
	static ArquivoTextoEscrita logPedidos = new ArquivoTextoEscrita();
	static ArquivoTextoEscrita logResumidoPedidos = new ArquivoTextoEscrita();
	static int qtdPedidosFeitos;

	public static void main(String[] args) {
		
		Queue<Pedido> pedidos = new PriorityQueue<Pedido>();
		BracoMecanico bracoMecanico = new BracoMecanico();
		Esteira esteira = new Esteira();
		
		criarPedidos(pedidos);
		logPedidos.abrirArquivo("logPedidos.txt");
		logResumidoPedidos.abrirArquivo("logResumidoPedidos.txt");
		iniciarProcesso(bracoMecanico, esteira, pedidos);
		logResumidoPedidos.fecharArquivo();
		logPedidos.fecharArquivo();
	}
	
	/**
	 * Simula o processo de trabalho da fábrica.
	 * @param bracoMecanico
	 * @param esteira
	 * @param pedidos
	 */
	private static void iniciarProcesso(BracoMecanico bracoMecanico, Esteira esteira, Queue<Pedido> pedidos) {
		while(pedidos.size() > 0 || timer.getHora() == 17) {
			Pedido p = pedidos.remove();
			
			esteira.setprodutos(p.getProdutos());
			
			while(!p.isFinalizado()) {
				Pacote pkg = new Pacote();
				
				//a esteira rola os 20 produtos necessários para encher o pacote.
				for(int i = 0; i < 20; i++) {
					pkg.inserirProduto(bracoMecanico.empacotarProduto(esteira.rolarProdutos()));
					if(pkg.estaCheio()) {
						timer.incrementaSegundo(5);
						transicao(bracoMecanico, esteira, p);
						p.setPacote(pkg);
						i = 21;
					}
				}
			}
			qtdPedidosFeitos++;
			criarLogPedidos(p);
			criarLogResumidoPedidos(p);
		}
		
	}			
	
	/**
	 * Transicao entre a remocao do pacote e o despache
	 * @param braco
	 * @param esteira
	 * @param p
	 */
	private static void transicao(BracoMecanico braco, Esteira esteira, Pedido p) {
		braco.removerPacote(p);
		timer.incrementaSegundo(0.5);
	}
	
	/* private static void criarRelatorio12h(Queue<Pedido> pedidosAte12h) {
		StringBuilder sb = new StringBuilder();
		
		while(pedidosAte12h.size() > 0) {
			Pedido aux = pedidosAte12h.remove();
			sb.append(qtdPedidosFeitos + " - ["+ timer.getHora() + "h, " + timer.getMinuto() + "min, "+ timer.getSegundo() + "seg] " + aux.getCliente() +" status: " + (aux.isFinalizado() ? "Finalizado" : "Não finalizado!"));
			sb.append("\nQuantidade de pacotes utilizados: " + aux.getPacotes().size());
			sb.append("\n_____________________________________________\n");
		}
		
		relatorio12h.escrever(sb.toString());
	} */
	
	/**
	 * Cria o log contendo os pedidos, o cliente e o horário.
	 * @param p
	 */
	private static void criarLogPedidos(Pedido p) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n" + qtdPedidosFeitos + " - ["+ timer.getHora() + "h, " + timer.getMinuto() + "min, "+ timer.getSegundo() + "seg]\nNome: " + p.getCliente() +", status: " + (p.isFinalizado() ? "Finalizado" : "Não finalizado!"));
		sb.append("\nQuantidade de pacotes utilizados: " + p.getPacotes().size());
		sb.append("\n" + p.toString());
		sb.append("\n_____________________________________________");
		logPedidos.escrever(sb.toString());	
	}
	
	private static void criarLogResumidoPedidos(Pedido p) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n" + qtdPedidosFeitos + " - ["+ timer.getHora() + "h, " + timer.getMinuto() + "min, "+ timer.getSegundo() + "seg]\nNome: " + p.getCliente() +", status: " + (p.isFinalizado() ? "Finalizado" : "Não finalizado!"));
		sb.append("\nQuantidade de pacotes utilizados: " + p.getPacotes().size());
		sb.append("\n_____________________________________________");
		logResumidoPedidos.escrever(sb.toString());	
	}
	
	/**
	 * Cria os pedidos com base no arquivo da base de dados.
	 * @param pedidos
	 */
	public static void criarPedidos(Queue<Pedido> pedidos) {
		String enderecoArq = "./BaseDeDados/dados_tp01.txt";

		ArquivoTextoLeitura arquivoEntrada = new ArquivoTextoLeitura(enderecoArq);

		int tam = Integer.parseInt(arquivoEntrada.lerBuffer());

		for (int i = 0; i < tam; i++) {

			List<Produto> listaProdutos = new ArrayList<Produto>();

			String leitura = arquivoEntrada.lerBuffer();
			String[] intermediario = leitura.split(";");

			for (int j = 0; j < Integer.parseInt(intermediario[1]); j++) {
				Produto p = new Produto();
				listaProdutos.add(p);
			}

			Pedido e = new Pedido(listaProdutos, intermediario[0], listaProdutos.size(), Integer.parseInt(intermediario[2]));
			pedidos.add(e);
		}
		arquivoEntrada.fecharArquivo();
	}

}
