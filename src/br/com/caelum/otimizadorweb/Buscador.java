package br.com.caelum.otimizadorweb;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Buscador {
	
	private final String temp;

	public Buscador(String temp) {
		this.temp = temp;
	}

	public List<File> buscaArquivosLocaisTerminadosEm(String... exts) throws IOException {
		List<File> arquivos = new ArrayList<File>();
		
		System.out.println("Buscando arquivos locais...");
		
		arquivos.addAll(this.buscaRecursiva(".", new ArrayList<File>(), exts));
		return arquivos;
	}
	
	private List<File> buscaRecursiva(String raiz, List<File> arquivos, String... exts) {
		
		File[] files = new File(raiz).listFiles();
		
		for (File arquivo : files) {
			if(arquivo.isDirectory() && !arquivo.getName().equals(temp)) {
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
