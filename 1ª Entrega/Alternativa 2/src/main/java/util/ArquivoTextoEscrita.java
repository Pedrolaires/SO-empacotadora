package util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ArquivoTextoEscrita {

	private OutputStreamWriter saida;
	
	public ArquivoTextoEscrita() {}
		
	public void abrirArquivo(String nomeArquivo){	
		
		try {
			saida = new OutputStreamWriter(new FileOutputStream(nomeArquivo),"UTF-8");
		}
		catch (FileNotFoundException excecao) {
			System.out.println("Arquivo não encontrado");
		}
		catch (IOException excecao) {
			System.out.println("Erro na abertura do arquivo de escrita: " + excecao);
		}
	}
	
	public void fecharArquivo() {
		
		try {
			saida.close();
		}
		catch (IOException excecao) {
			System.out.println("Erro no fechamento do arquivo de escrita: " + excecao);	
		}
	}
	
	public void escrever(String textoEntrada) {
		try {
			saida.append(textoEntrada);
			saida.append("\n");
		}
		catch (IOException excecao){
			System.out.println("Erro de entrada/saída " + excecao);
		}
	}
}