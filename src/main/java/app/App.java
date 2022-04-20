package app;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import valor.Pedido;
import valor.Produto;

public class App {
public static void main(String[] args) {
		String enderecoArq = "./BaseDeDados/dados_tp01.txt";
		
		ArquivoTextoLeitura arquivoEntrada = new ArquivoTextoLeitura(enderecoArq); 
		
		int tam = Integer.parseInt(arquivoEntrada.lerBuffer());	

		Queue<Pedido> pedidos = new PriorityQueue<Pedido>();
		
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

		for (int i = 0; i < 169 ; i++) {
			Pedido p = pedidos.remove();
			System.out.println(p.getCliente() + " " + p.getPrazoEmpacotamento());
		}
	}
}
