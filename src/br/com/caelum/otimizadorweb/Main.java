package br.com.caelum.otimizadorweb;
import java.io.File;
import java.io.IOException;

import br.com.caelum.otimizadorweb.empacotadores.Empacotador;
import br.com.caelum.otimizadorweb.zip.Zipador;

public class Main {
	
	private static String DESTINO = "projeto-compactado.zip";
	private static String TEMP = "arquivos_temporarios";

	public static void main(String[] args) throws IOException {
		
		String destino = DESTINO;
		
		File temp = new File(TEMP);
		Zipador pasta = new Zipador(temp);
		pasta.cria();

		Buscador buscador = new Buscador();
		Minificador minificador = new Minificador(temp, buscador);
		
		if(args.length == 0) {
			destino = DESTINO;
			minificador.comprimeListaDeArquivos();
		}
		
		for (String arg : args) {
			if(arg.equals("-pack")) {
				Empacotador empacotador = new Empacotador(buscador, minificador);
				empacotador.geraPackage(".");
			} else {
				destino = arg;
				minificador.comprimeListaDeArquivos();
			}
		}
		
		pasta.compactarPara(destino);
		pasta.remove();
	}
}
