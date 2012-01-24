package br.com.caelum.otimizadorweb;
import java.io.File;
import java.io.IOException;

import br.com.caelum.otimizadorweb.zip.Pasta;

public class Main {
	
	private static String DESTINO = "projeto-compactado.zip";
	private static String TEMP = "arquivos_temporarios";

	public static void main(String[] args) throws IOException {
		
		String destino = DESTINO;
		if(args.length > 0) {
			destino = args[0];
		}
		
		File temp = new File(TEMP + System.currentTimeMillis());
		Pasta pasta = new Pasta(temp);
		pasta.cria();

		Buscador buscador = new Buscador();
		Minificador minificador = new Minificador(temp, buscador);
		minificador.comprimeListaDeArquivos();
		
		pasta.compactarPara(destino);
		pasta.remove();
	}
	
}
