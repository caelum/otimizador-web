package br.com.caelum.otimizadorweb;
import java.io.File;
import java.io.IOException;

import br.com.caelum.otimizadorweb.ferramentas.Empacotador;
import br.com.caelum.otimizadorweb.ferramentas.Fingerprinter;
import br.com.caelum.otimizadorweb.ferramentas.Minificador;
import br.com.caelum.otimizadorweb.ferramentas.Zipador;
import br.com.caelum.otimizadorweb.helpers.Buscador;

public class Main {
	
	private static String DESTINO = "projeto-compactado.zip";
	private static String TEMP = "projeto";

	public static void main(String[] args) throws IOException {
		
		String destino = DESTINO;
		
		File temp = new File(TEMP);
		Zipador pasta = new Zipador(temp);
		pasta.cria();

		Buscador buscador = new Buscador(TEMP);
		Minificador minificador = new Minificador(temp, buscador);
		
		if(args.length == 0) {
			destino = DESTINO;
			minificador.comprimeListaDeArquivos();
		}
		
		for (String arg : args) {
			if(arg.equals("-pack")) {
				Empacotador empacotador = new Empacotador(buscador, minificador);
				empacotador.geraPackage(".");
			} else if(arg.equals("-fingerprint")) {
				minificador.comprimeListaDeArquivos();
				Fingerprinter fingerprint = new Fingerprinter(TEMP ,buscador);
				fingerprint.paraArquivos();
				destino = fingerprint.para(destino);				
			} else if(arg.endsWith(".zip")) {
				destino = arg;
				minificador.comprimeListaDeArquivos();
			}
		}

		pasta.compactarPara(destino);
		pasta.remove();
	}
}
