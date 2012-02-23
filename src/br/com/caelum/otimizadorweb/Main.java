package br.com.caelum.otimizadorweb;
import java.io.File;
import java.io.IOException;
import java.util.List;

import br.com.caelum.otimizadorweb.ferramentas.Empacotador;
import br.com.caelum.otimizadorweb.ferramentas.Fingerprinter;
import br.com.caelum.otimizadorweb.ferramentas.ManipuladorDeImagens;
import br.com.caelum.otimizadorweb.ferramentas.Minificador;
import br.com.caelum.otimizadorweb.ferramentas.Renomeador;
import br.com.caelum.otimizadorweb.ferramentas.Zipador;
import br.com.caelum.otimizadorweb.helpers.Buscador;
import br.com.caelum.otimizadorweb.helpers.VerificadorDeParametros;

import com.beust.jcommander.JCommander;

public class Main {
	
	private static String DESTINO = "projeto-compactado.zip";
	private static String TEMP = "projeto";

	public static void main(String[] args) throws IOException {
		
		String destino = DESTINO;
		
		File temp = new File(TEMP);
		Zipador pasta = new Zipador(temp);
		pasta.cria();

		Buscador buscadorTemp = new Buscador(TEMP);
		Minificador minificador = new Minificador(temp);
		
		if(args.length == 0) {
			destino = DESTINO;
			minificador.minifica();
		}
		
		VerificadorDeParametros parser = new VerificadorDeParametros();
		JCommander commander = new JCommander(parser, args);
		
		if(parser.ajuda()) {
			commander.usage();
			return;
		}
		
		geraPackage(minificador, parser);
		destino = checaNomeDaPastaDeDestino(destino, minificador, parser);
		destino = geraFingerprint(destino, TEMP, minificador, parser);
		
		ManipuladorDeImagens manipuladorDeImagens = new ManipuladorDeImagens(temp);
		manipuladorDeImagens.copiaImagens();
		
		pasta.compactarPara(destino);
		pasta.remove();
	}

	private static void geraPackage(Minificador minificador, VerificadorDeParametros parser) throws IOException {
		if(parser.geraPackage()) {
			System.out.println("Gerando package.css e package.js...");
			new Empacotador(minificador, ".").gera();
			List<File> arquivos = new Buscador(TEMP).buscaLocalmenteArquivosTerminadosEm(".css", ".js");
			new Renomeador(TEMP, arquivos).renomeiaPackage();
		}
	}
	
	private static String geraFingerprint(String destino, String pasta,
			Minificador minificador, VerificadorDeParametros parser) throws IOException {
		if(parser.geraFingerprint()) {
			if(parser.geraPackage()) {
				List<File> arquivos = new Buscador(".").buscaLocalmenteArquivosTerminadosEm(".html", ".htm");
				minificador.minificaLista(arquivos);
			} else {
				minificador.minifica();
			}
			Fingerprinter fingerprint = new Fingerprinter(pasta);
			List<File> arquivos = fingerprint.paraArquivos();
			destino = fingerprint.para(destino);
			if(parser.geraPackage()) {
				new Renomeador(pasta, arquivos).renomeiaPackage();
			}
		}
		return destino;
	}

	private static String checaNomeDaPastaDeDestino(String destino, Minificador minificador,
			VerificadorDeParametros parser) throws IOException {
		
		List<String> arquivos = parser.getArquivos();
		
		if(arquivos != null) {
			String arquivo = arquivos.get(0);
			if(!arquivo.isEmpty() && arquivo.endsWith(".zip")) {
				destino = arquivo;
				
				System.out.println("Comprimindo para a pasta "+ destino + "...");
				
				minificador.minifica();
			}
		}
		return destino;
	}
}
