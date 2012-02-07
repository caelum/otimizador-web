package br.com.caelum.otimizadorweb;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Buscador {
	
	private String[] exts;

	public Buscador(String... exts) {
		this.exts = exts;
	}
	
	public void setExts(String... exts) {
		this.exts = exts;
	}
	
	public List<File> buscaArquivosLocais() throws IOException {
		
		List<File> arquivos = new ArrayList<File>();
		arquivos.addAll(this.buscaRecursiva(".", new ArrayList<File>(), this.exts));
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
