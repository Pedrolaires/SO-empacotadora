package app;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import maquinas.BracoMecanico;
import maquinas.Esteira;
import util.ArquivoTextoLeitura;
import util.MyTimer;
import util.Relatorio;
import valor.Pacote;
import valor.Pedido;
import valor.Produto;


public class App {
	
	static MyTimer timerEsteira1 = new MyTimer(8,0,0);
	static MyTimer timerEsteira2 = new MyTimer(8,0,0);
	static double tempoTransicao = 0.5;
	static double tempoProducaoPacote = 5;
	
	static AtomicInteger tempoMedioPedido = new AtomicInteger(0);
	static AtomicInteger totalDePacotes = new AtomicInteger(0);
	static AtomicInteger qtdPedidosFeitos = new AtomicInteger(0);
	static AtomicInteger qtdPedidosFeitosAte12h = new AtomicInteger(0);
	

	public static void main(String[] args) {
		Relatorio logPedidos = new Relatorio("./Relatorios/logs/logPedidos.md");
		Relatorio logResumidoPedidos = new Relatorio( "./Relatorios/logResumidoPedidos.md");
		Relatorio log12horas = new Relatorio("./Relatorios/logs/log12horas.md");
		Relatorio logEsteira1 = new Relatorio("./Relatorios/logs/esteiras/logEsteira1.md");
		Relatorio logEsteira2 = new Relatorio("./Relatorios/logs/esteiras/logEsteira2.md");
		Relatorio relatorioFinal = new Relatorio("./Relatorios/relatorioFinal.md");
		
		Relatorio[] relatorios = {logPedidos, logResumidoPedidos, log12horas,logEsteira1,logEsteira2,relatorioFinal};
		
		PriorityBlockingQueue<Pedido> pedidos = new PriorityBlockingQueue<Pedido>();
		BracoMecanico bracoMecanico1 = new BracoMecanico();
		Esteira esteira1 = new Esteira("Esteira 1");
		
		BracoMecanico bracoMecanico2 = new BracoMecanico();
		Esteira esteira2 = new Esteira("Esteira 2");
		
		criarPedidos(pedidos);
		

		Thread t1 = new Thread(() ->{
			try {
				iniciarProcesso(bracoMecanico1, esteira1, pedidos,relatorios, logEsteira1,timerEsteira1);
				
			} catch (InterruptedException e) {
				System.out.println("Algo deu errado!");
			}
		});
		
		Thread t2 = new Thread(() ->{
			try {
				iniciarProcesso(bracoMecanico2, esteira2, pedidos,relatorios,logEsteira2,timerEsteira2);
				
			} catch (InterruptedException e) {
				System.out.println("Algo deu errado! 2");
			}
		});
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
			
			MyTimer timer = timerEsteira2;
			if(timerEsteira1.getHora() < timerEsteira2.getMinuto())
				timer = timerEsteira1;
			
			tempoMedioPedido.getAndSet(tempoMedioPedido.get() /qtdPedidosFeitos.get());
			relatorioFinal.gerarRelatorioFinal(timer ,tempoMedioPedido.get(), qtdPedidosFeitosAte12h.get());
		} catch (InterruptedException e) {
			System.out.println("Algo deu errado! 3");
		}
		
		for(Relatorio r : relatorios) {
			r.fecharArquivo();
		}
	}
	
	/**
	 * Simula o processo de trabalho da fabrica.
	 * @param bracoMecanico
	 * @param esteira
	 * @param pedidos
	 * @throws InterruptedException 
	 */
	private static void iniciarProcesso(BracoMecanico bracoMecanico, Esteira esteira,
			PriorityBlockingQueue<Pedido> pedidos, Relatorio[] relatorios, Relatorio logEsteira, MyTimer timer) throws InterruptedException{
		Lock lock = new ReentrantLock();
		
		final int volumeComportadoNoPacote = 5000;
		int qtdProdutos = (int) (volumeComportadoNoPacote  / 250);

		while (!pedidos.isEmpty()) {
			Pedido p = pedidos.take();
			
			esteira.setprodutos(p.getProdutos());
			
			while(!p.isFinalizado()) {
				Pacote pkg = new Pacote();
				
				for(int i = 0; i < qtdProdutos; i++) {
					Produto produto = esteira.rolarProdutos();
					
					pkg.inserirProduto(bracoMecanico.empacotarProduto(produto));
					logEsteira.criarLogEsteira(timer,esteira, produto, p);
					
					if(pkg.estaCheio()) {	
						timer.incrementaSegundo(tempoProducaoPacote);
						transicao(timer, bracoMecanico, esteira, p);
						p.setPacote(pkg);
						totalDePacotes.incrementAndGet();
						i = 21;
					}
				}
			}
			lock.lock();
			tempoMedioPedido.getAndAdd((int) (p.getPacotes().size()*tempoProducaoPacote + p.getPacotes().size()*tempoTransicao));
			if(timer.getHora() < 12)
				relatorios[2].criarRelatorio12h(timer, p, qtdPedidosFeitos.get());
			
			relatorios[0].criarLogPedidos(timer, p, esteira.getNome(), qtdPedidosFeitos.get());
			relatorios[1].criarLogResumidoPedidos(timer, p, esteira.getNome(), qtdPedidosFeitos.get());
			qtdPedidosFeitos.getAndIncrement();
			qtdPedidosFeitosAte12h.getAndIncrement();
			lock.unlock();
		}
	}
	/**
	 * Transicao entre a remocao do pacote e o despache
	 * @param braco
	 * @param esteira
	 * @param p
	 */
	private static void transicao(MyTimer timer, BracoMecanico braco, Esteira esteira, Pedido p) {
		braco.removerPacote(p);
		timer.incrementaSegundo(tempoTransicao);
	}
	
	/**
	 * Cria os pedidos com base no arquivo da base de dados.
	 * @param pedidos
	 */
	public static void criarPedidos(PriorityBlockingQueue<Pedido> pedidos) {
		String enderecoArq = "./BaseDeDados/dados_tp02.txt";

		ArquivoTextoLeitura arquivoEntrada = new ArquivoTextoLeitura(enderecoArq);

		arquivoEntrada.lerBuffer();
		

		for (int i = 0; i < 319; i++) {

			List<Produto> listaProdutos = new ArrayList<Produto>();

			String leitura = arquivoEntrada.lerBuffer();
			String[] intermediario = leitura.split(";");

			for (int j = 0; j < Integer.parseInt(intermediario[1]); j++) {
				Produto p = new Produto();
				p.setIndice(j+1);
				listaProdutos.add(p);
			}

			Pedido e = new Pedido(listaProdutos, intermediario[0], listaProdutos.size(), Integer.parseInt(intermediario[3]), Integer.parseInt(intermediario[2]));
			pedidos.put(e);
		}
		arquivoEntrada.fecharArquivo();
	}

}
