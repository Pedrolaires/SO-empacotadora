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
	static double tempoTransicao = 0.5;
	static double tempoProducaoPacote = 5;
	static double tempoMedioPedido;
	static int totalDePacotes = 0;
	static ArquivoTextoEscrita logPedidos = new ArquivoTextoEscrita();
	static ArquivoTextoEscrita logResumidoPedidos = new ArquivoTextoEscrita();
	static ArquivoTextoEscrita log12horas = new ArquivoTextoEscrita();
	static ArquivoTextoEscrita logEsteiras = new ArquivoTextoEscrita();
	static ArquivoTextoEscrita relatorioFinal = new ArquivoTextoEscrita();
	static int qtdPedidosFeitos;
	static int qtdPedidosFeitosAte12h = 0;
	

	public static void main(String[] args) {
		
		Queue<Pedido> pedidos = new PriorityQueue<Pedido>();
		BracoMecanico bracoMecanico = new BracoMecanico();
		Esteira esteira = new Esteira("Esteira 1");
		
		criarPedidos(pedidos);
		logPedidos.abrirArquivo("./Relatorios/logPedidos.md");
		logResumidoPedidos.abrirArquivo("./Relatorios/logResumidoPedidos.md");
		log12horas.abrirArquivo("./Relatorios/log12horas.md");
		logEsteiras.abrirArquivo("./Relatorios/logEsteiras.md");
		relatorioFinal.abrirArquivo("./Relatorios/relatorioFinal.md");
		iniciarProcesso(bracoMecanico, esteira, pedidos);
		logEsteiras.fecharArquivo();
		logResumidoPedidos.fecharArquivo();
		logPedidos.fecharArquivo();
		log12horas.fecharArquivo();
		relatorioFinal.fecharArquivo();
	}
	
	/**
	 * Simula o processo de trabalho da fábrica.
	 * @param bracoMecanico
	 * @param esteira
	 * @param pedidos
	 */
	private static void iniciarProcesso(BracoMecanico bracoMecanico, Esteira esteira, Queue<Pedido> pedidos) {
		int volumeComportadoNoPacote = 5000;
		
		while(pedidos.size() > 0 || timer.getHora() == 17) {
			Pedido p = pedidos.remove();
			int qtdProdutos = (int) (volumeComportadoNoPacote  / p.getProdutos().get(0).getVolume());
			
			esteira.setprodutos(p.getProdutos());
			
			while(!p.isFinalizado()) {
				Pacote pkg = new Pacote();
				
				//a esteira rola os 20 produtos necessários para encher o pacote.
				for(int i = 0; i < qtdProdutos; i++) {
					Produto produto = esteira.rolarProdutos();
					pkg.inserirProduto(bracoMecanico.empacotarProduto(produto));
					criarLogEsteira(esteira,produto, p);
					if(pkg.estaCheio()) {
						timer.incrementaSegundo(tempoProducaoPacote);
						transicao(bracoMecanico, esteira, p);
						p.setPacote(pkg);
						totalDePacotes +=1;
						i = 21;
					}
				}
			}
			tempoMedioPedido += p.getPacotes().size()*tempoProducaoPacote + p.getPacotes().size()*tempoTransicao;
			qtdPedidosFeitos++;
			if(timer.getHora() < 12)
				criarRelatorio12h(p);

			criarLogPedidos(p);
			criarLogResumidoPedidos(p);
		}
		tempoMedioPedido = tempoMedioPedido/qtdPedidosFeitos;
		gerarRelatorioFinal();
		
	}			
	
	/**
	 * Transicao entre a remocao do pacote e o despache
	 * @param braco
	 * @param esteira
	 * @param p
	 */
	private static void transicao(BracoMecanico braco, Esteira esteira, Pedido p) {
		braco.removerPacote(p);
		timer.incrementaSegundo(tempoTransicao);
	}
	
	/**
	 * Cria um log na pasta de relatorios com base na passagem de produtos pela esteira
	 * @param esteira
	 * @param produto
	 * @param p
	 */
	private static void criarLogEsteira(Esteira esteira, Produto produto, Pedido p) {
		StringBuilder sb = new StringBuilder();
		sb.append("["+ timer.getHora() + "h, " + timer.getMinuto() + "min, "+ timer.getSegundo() + "seg] "+ esteira.getNome() +": Produto: " + produto.getIndice() +
				" do cliente: *" + p.getCliente() + "* rolou!");
		logEsteiras.escrever(sb.toString());
	}
	
	/**
	 * Relatorio final
	 */
	private static void gerarRelatorioFinal() {
		StringBuilder sb = new StringBuilder();
		sb.append("# Relatório final de uma rotina de trabalho se iniciando às 08:00 e com término às 17:00\n"
				+ "## Dados coletados através de simulações computacionais!\n"
				+ "### Utilizamos uma fila de prioridade para priorizar os pedidos com prazo mais curto"
				+ "\n____________________________________________________________________________________\n\n"
				+ "Tempo médio na realização de cada pedido: " + tempoMedioPedido/60 + "min\n"
				+ "Pedidos finalizados até as 12horas: " + qtdPedidosFeitosAte12h);
		
		relatorioFinal.escrever(sb.toString());
	}
	
	/**
	 * Cria o relatório de pedidos concluídos até o meio dia
	 * @param p
	 */
	 private static void criarRelatorio12h(Pedido p) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("\n## " + qtdPedidosFeitos + " - ["+ timer.getHora() + "h, " + timer.getMinuto() + "min, "+ timer.getSegundo() + "seg]\n### Nome: " + p.getCliente() +", status: " + (p.isFinalizado() ? "Finalizado" : "Não finalizado!"));
		sb.append("\n### Quantidade de pacotes utilizados: " + p.getPacotes().size());
		sb.append("\n_____________________________________________\n");	
		
		log12horas.escrever(sb.toString());
		qtdPedidosFeitosAte12h++;
	}
	
	/**
	 * Cria o log contendo os pedidos, o cliente e o horário.
	 * @param p
	 */
	private static void criarLogPedidos(Pedido p) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n## " + qtdPedidosFeitos + " - ["+ timer.getHora() + "h, " + timer.getMinuto() + "min, "+ timer.getSegundo() + "seg]\n### Nome: " + p.getCliente() +", status: " + (p.isFinalizado() ? "Finalizado" : "Não finalizado!"));
		sb.append("\nQuantidade de pacotes utilizados: " + p.getPacotes().size());
		sb.append("\n" + p.toString());
		sb.append("\n_____________________________________________");
		logPedidos.escrever(sb.toString());	
	}
	
	private static void criarLogResumidoPedidos(Pedido p) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n## " + qtdPedidosFeitos + " - ["+ timer.getHora() + "h, " + timer.getMinuto() + "min, "+ timer.getSegundo() + "seg]\n### Nome: " + p.getCliente() +", status: " + (p.isFinalizado() ? "Finalizado" : "Não finalizado!"));
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
				p.setIndice(j+1);
				listaProdutos.add(p);
			}

			Pedido e = new Pedido(listaProdutos, intermediario[0], listaProdutos.size(), Integer.parseInt(intermediario[2]));
			pedidos.add(e);
		}
		arquivoEntrada.fecharArquivo();
	}

}
