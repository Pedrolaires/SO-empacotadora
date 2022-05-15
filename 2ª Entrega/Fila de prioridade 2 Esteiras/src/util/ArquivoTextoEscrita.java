package util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ArquivoTextoEscrita {

	private OutputStreamWriter saida;
	
	public ArquivoTextoEscrita() {}
		
	public void openFile(String nomeArquivo){	
		
		try {
			saida = new OutputStreamWriter(new FileOutputStream(nomeArquivo),"UTF-8");
		}
		catch (FileNotFoundException excecao) {
			System.out.println("Arquivo n�o encontrado");
		}
		catch (IOException e) {
			System.out.println("Erro na abertura do arquivo de escrita: " + e);
		}
	}
	
	public void closeFile() {
		
		try {
			saida.close();
		}
		catch (IOException e) {
			System.out.println("Erro no fechamento do arquivo de escrita: " + e);	
		}
	}
	
	public void append(String textoEntrada) {
		try {
			saida.append(textoEntrada);
			saida.append("\n");
		}
		catch (FileNotFoundException e){
			write(textoEntrada);
		}catch (IOException e) {
			System.out.println("Erro de entrada e saída!");
		}
	}
	
	public void write(String textoEntrada) {
		try {
			saida.write(textoEntrada);
			saida.write("\n");
		}catch(IOException e){
			System.out.println("Erro de entrada e saída!");
		}
	}
}