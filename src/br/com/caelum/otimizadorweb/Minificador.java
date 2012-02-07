package br.com.caelum.otimizadorweb;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.otimizadorweb.compressores.Compressor;
import br.com.caelum.otimizadorweb.compressores.CompressorCss;
import br.com.caelum.otimizadorweb.compressores.CompressorHtml;
import br.com.caelum.otimizadorweb.compressores.CompressorJs;
import br.com.caelum.otimizadorweb.compressores.CompressorJsGoogle;

public class Minificador {
	
	private File pasta;
	private final Buscador buscador;
	private List<Compressor> compressores;

	public Minificador(File temp, Buscador buscador) {
		this.pasta = temp;
		this.buscador = buscador;
		
		compressores = this.inicializaCompressores();
	}

	private List<Compressor> inicializaCompressores() {
		List<Compressor> compressores = new ArrayList<Compressor>();
		
		compressores.add(new CompressorCss(pasta));
		compressores.add(new CompressorHtml(pasta));
		compressores.add(new CompressorJs(pasta));
		compressores.add(new CompressorJsGoogle(pasta));
		
		return compressores;
	}
	
	public void comprimeListaDeArquivos() throws IOException {
		List<File> arquivosLocais = buscador.buscaArquivosLocaisTerminadosEm(".html", ".htm", ".css", ".js");
		
		System.out.println("Minificando arquivos...");
		
		for (File arquivo : arquivosLocais) {
			for(Compressor compressor:compressores) {
				if(compressor.getTipo().aceita(arquivo)) {
					compressor.comprime(arquivo);
				}
			}
		}
	}
}
