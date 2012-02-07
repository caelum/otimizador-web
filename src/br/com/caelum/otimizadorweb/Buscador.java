package br.com.caelum.otimizadorweb;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Buscador {
	
	public List<File> buscaArquivosLocaisTerminadosEm(String... exts) throws IOException {
		
		List<File> arquivos = new ArrayList<File>();
		arquivos.addAll(this.buscaRecursiva(".", new ArrayList<File>(), exts));
		return arquivos;
	}
	
	private List<File> buscaRecursiva(String raiz, List<File> arquivos, String... exts) {
		
		File[] files = new File(raiz).listFiles();
		
		for (File arquivo : files) {
			if(arquivo.isDirectory()) {
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
