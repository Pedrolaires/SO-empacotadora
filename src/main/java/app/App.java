package app;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import maquinas.BracoMecanico;
import maquinas.Esteira;
import transporte.Transporte;
import valor.Pacote;
import valor.Pedido;
import valor.Produto;

public class App {
public static void main(String[] args) {
		String enderecoArq = "./BaseDeDados/dados_tp01.txt";
		
		ArquivoTextoLeitura arquivoEntrada = new ArquivoTextoLeitura(enderecoArq); 
		
		int tam = Integer.parseInt(arquivoEntrada.lerBuffer());	

		Queue<Pedido> pedidos = new PriorityQueue<Pedido>();

		// quantidade de segundos percorridos de 8h até 12h, quando sai a primeira van
		int maxSegundos = 14400;
		int tempoCorrido = 0;
		
		for (int i = 0; i < tam; i++) {		
			
			List<Produto> listaProdutos = new ArrayList<>();	
			
			String leitura = arquivoEntrada.lerBuffer();
			String[] intermediario = leitura.split(";");
			
			for(int k = 0; k<Integer.parseInt(intermediario[1]);k++) {
				Produto p = new Produto();
				listaProdutos.add(p);
				}
			
			Pedido e = new Pedido(listaProdutos,intermediario[0], listaProdutos.size(), Integer.parseInt(intermediario[2]));
			pedidos.add(e);
		}
		arquivoEntrada.fecharArquivo();

		Transporte primeiraVan = new Transporte();

		Esteira est = new Esteira(0);
		BracoMecanico braco = new BracoMecanico();

		// começa a processar os pedidos
		for (int i = 0; i < pedidos.size() ; i++) {
			// peek retorna mas nao remove o pedido da fila
			Pedido p = pedidos.peek();		
			Pacote pac = new Pacote();

			// coloca produtos na esteira caso seja o primeiro pedido do dia
			if(est.vazia()){
				est.rolarProdutos();
				tempoCorrido += 5;
			}

			est.setQtdProdutos(p.getProdutos().size());
			for(int j = 0; j< p.getProdutos().size(); j++){
				// checa se produte cabe no pacote
				if(pac.vazio() == false){
					pac.inserirProduto(p.getProdutos().get(j));
				} else if( pac.cheio() == false && (p.getProdutos().get(j).getVolume() + pac.getVolumeAtual()) <= pac.getVolumeTotal()){
					pac.inserirProduto(p.getProdutos().get(j));
				}
			}
			
			if(tempoCorrido < maxSegundos){
				primeiraVan.addPacote(pac);
			}

			// System.out.println(p.getCliente() + " " + p.getPrazoEmpacotamento());
		}
	}
}
