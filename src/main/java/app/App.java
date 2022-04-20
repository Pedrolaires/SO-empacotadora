package app;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import valor.Pedido;
import valor.Produto;

public class App {
public static void main(String[] args) {
		
		int tam = 0;	
		
		String enderecoArq = "./BaseDeDados/dados_tp01.txt";
		
		//declaro novos objetos para poder ler o arquivo texto
		
		ArquivoTextoLeitura arquivoEntrada = new ArquivoTextoLeitura
				(enderecoArq); 
									//***USAR o arq certo!
		
		String leituraArquivo = arquivoEntrada.lerBuffer();
		
		//excluo a primera linha
		
		leituraArquivo = arquivoEntrada.lerBuffer();		
		while(leituraArquivo!=null) {
			tam++;
			
			//leio cada uma das linhas e vou contando o tamanho
			
			leituraArquivo = arquivoEntrada.lerBuffer();
		}
		
		arquivoEntrada.fecharArquivo();	
		arquivoEntrada = new ArquivoTextoLeitura
				(enderecoArq); 
								//***USAR o arq certo!
		
		
		//leio o cabeçalho para descartar
		
		String leitura = arquivoEntrada.lerBuffer();	

		Queue<Pedido> pedidos = new PriorityQueue<Pedido>();
		for (int i = 0; i < tam; i++) {		
			
			List<Produto> listaProdutos = new ArrayList<>();	
			
			//leio a linha e faço a separação da string em substrings
			
			leitura = arquivoEntrada.lerBuffer();
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
