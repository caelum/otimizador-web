package br.com.caelum.otimizadorweb.ferramentas;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.otimizadorweb.compressores.Compressor;
import br.com.caelum.otimizadorweb.compressores.CompressorCss;
import br.com.caelum.otimizadorweb.compressores.CompressorHtml;
import br.com.caelum.otimizadorweb.compressores.CompressorJs;
import br.com.caelum.otimizadorweb.helpers.Buscador;

public class Minificador {
	
	private File pasta;
	private final Buscador buscador;
	private List<Compressor> compressores;

	public Minificador(File temp) {
		this.pasta = temp;
		this.buscador = new Buscador(".");
		
		this.compressores = this.inicializaCompressores();
	}

	private List<Compressor> inicializaCompressores() {
		List<Compressor> compressores = new ArrayList<Compressor>();
		
		compressores.add(new CompressorCss(pasta));
		compressores.add(new CompressorHtml(pasta));
		compressores.add(new CompressorJs(pasta));
//		compressores.add(new CompressorJsGoogle(pasta));
		
		return compressores;
	}
	
	public void minifica() {
		List<File> arquivos = buscador.buscaEmSubpastasArquivosTerminadosEm(".html", ".htm", ".css", ".js");
		minificaLista(arquivos);
	}
	
	public void minificaLista(List<File> arquivos) {
		for (File arquivo : arquivos) {
			for(Compressor compressor:compressores) {
				if(compressor.getTipo().aceita(arquivo)) {
					try {
						compressor.comprime(arquivo);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
