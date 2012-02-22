package br.com.caelum.otimizadorweb.helpers;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Buscador {
	
	private final String pastaTemporaria;

	public Buscador(String pasta) {
		this.pastaTemporaria = pasta;
	}
	
	public List<File> buscaArquivosTemporariosTerminadosEm(String... exts) {
		return buscaArquivosNaPastaTerminadosEm("./" + pastaTemporaria, exts);
	}
	
	public List<File> buscaArquivosNaPastaTerminadosEm(String caminho, String... exts) {
		List<File> arquivos = new ArrayList<File>();
		
		arquivos.addAll(this.buscaRecursiva(caminho, new ArrayList<File>(), exts));
		return arquivos;
	}
	
	private List<File> buscaRecursiva(String raiz, List<File> arquivos, String... exts) {
		
		File[] files = new File(raiz).listFiles();
		
		for (File arquivo : files) {
			if(arquivo.isDirectory() && !arquivo.getName().equals(pastaTemporaria)) {
				buscaRecursiva(raiz + "/" + arquivo.getName() + "/", arquivos, exts);
			} else {
				for (String ext : exts) {
					if(arquivo.getName().endsWith(ext)) {
						arquivos.add(arquivo);
					}
				}
			}
		}
		return arquivos;
	}
}
