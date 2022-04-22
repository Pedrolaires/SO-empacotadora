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
	static double tempoMedioPedido;
	static ArquivoTextoEscrita logPedidos = new ArquivoTextoEscrita();
	static ArquivoTextoEscrita logResumidoPedidos = new ArquivoTextoEscrita();
	static ArquivoTextoEscrita log12horas = new ArquivoTextoEscrita();
	static ArquivoTextoEscrita logEsteiras = new ArquivoTextoEscrita();
	static ArquivoTextoEscrita relatorioFinal = new ArquivoTextoEscrita();
	static int qtdPedidosFeitos;

	public static void main(String[] args) {
		
		Queue<Pedido> pedidos = new PriorityQueue<Pedido>();
		BracoMecanico bracoMecanico = new BracoMecanico();
		Esteira esteira1 = new Esteira("Esteira 1");
		Esteira esteira2 = new Esteira("Esteira 2");
		
		criarPedidos(pedidos);
		logPedidos.abrirArquivo("./Relatorios/logPedidos.md");
		logResumidoPedidos.abrirArquivo("./Relatorios/logResumidoPedidos.md");
		log12horas.abrirArquivo("./Relatorios/log12horas.md");
		logEsteiras.abrirArquivo("./Relatorios/logEsteiras.md");
		relatorioFinal.abrirArquivo("./Relatorios/relatorioFinal.md");
		iniciarProcesso(bracoMecanico, esteira1, esteira2, pedidos);
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
	private static void iniciarProcesso(BracoMecanico bracoMecanico, Esteira esteira1, Esteira esteira2, Queue<Pedido> pedidos) {
		int volumeComportadoNoPacote = 5000;
		
		while(pedidos.size() > 0 || timer.getHora() == 17) {
			Pedido p = pedidos.remove();
			
			int qtdProdutos = (int) (volumeComportadoNoPacote  / p.getProdutos().get(0).getVolume());

			esteira1.setProdutos(p.getProdutos());
			esteira2.setProdutos(p.getProdutos());
			
			while(!p.isFinalizado()) {
				Pacote pkg = new Pacote();
				
				//as esteiras rolam os 20 produtos (10 em cada) necessários para encher o pacote.
				for(int i = 0; i < qtdProdutos; i+=2) {
					// braço empacontando cada item que as 2 esteiras estão rolando
					
					if(!esteira1.estaVazia()){
						Produto produto = esteira1.rolarProdutos();
						pkg.inserirProduto(bracoMecanico.empacotarProduto(produto));
						criarLogEsteira(esteira1,produto, p);
					}
					if(!esteira2.estaVazia()){
						Produto produto = esteira2.rolarProdutos();
						pkg.inserirProduto(bracoMecanico.empacotarProduto(produto));
						criarLogEsteira(esteira2,produto, p);
					}
					
					if(pkg.estaCheio() || (esteira1.estaVazia() && esteira2.estaVazia())) {
						timer.incrementaSegundo(5);
						transicao(bracoMecanico, esteira1, p);
						p.setPacote(pkg);	
						i = qtdProdutos + 1;
					}
				}
			}
			tempoMedioPedido += timer.toSegundos(timer.getHora(),timer.getMinuto(),timer.getSegundo());
			qtdPedidosFeitos++;
			if(timer.getHora() < 12)
				criarRelatorio12h(p);

			criarLogPedidos(p);
			criarLogResumidoPedidos(p);
		}
		gerarRelatorioFinal();
		
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
				+ "### Utilizamos uma fila de prioridade para priorizar os pedidos com prazo mais curto\n"
				+ "### Após a introdução de uma nova esteira para realizar a rolagem de produtos foi concluído que o tempo gasto foi o mesmo"
				+ "tendo em vista que a estrutura possui apenas 1 braco mecanico\n"
				+ "Mais informacoes podem ser observadas nos logs e no relatório final da alternativa 2");
		
		relatorioFinal.escrever(sb.toString());
	}

	/**
	 * Transicao entre a remocao do pacote e o despache
	 * @param braco
	 * @param esteira
	 * @param p
	 */
	private static void transicao(BracoMecanico braco, Esteira esteira1, Pedido p) {
		braco.removerPacote(p);
		timer.incrementaSegundo(0.5);
	}
	
	/**
	 * Cria o log contendo os pedidos atendidos até as 12h.
	 * @param p
	 */
	 private static void criarRelatorio12h(Pedido p) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("\n## " + qtdPedidosFeitos + " - ["+ timer.getHora() + "h, " + timer.getMinuto() + "min, "+ timer.getSegundo() + "seg]\n### Nome: " + p.getCliente() +", status: " + (p.isFinalizado() ? "Finalizado" : "Não finalizado!"));
		sb.append("\n### Quantidade de pacotes utilizados: " + p.getPacotes().size());
		sb.append("\n_____________________________________________\n");	
		
		log12horas.escrever(sb.toString());
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
