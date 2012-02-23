package br.com.caelum.otimizadorweb.helpers;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Buscador {
	
	private final String pastaTemporaria;

	public Buscador(String pasta) {
		this.pastaTemporaria = pasta;
	}
	
	public List<File> buscaEmSubpastasArquivosTerminadosEm(String... exts) {
		List<File> arquivos = new ArrayList<File>();
		
		arquivos.addAll(this.buscaEmSubpastas("./" + pastaTemporaria, new ArrayList<File>(), exts));
		return arquivos;
	}
	
	public List<File> buscaLocalmenteArquivosTerminadosEm(String... exts) {
		List<File> arquivos = new ArrayList<File>();
		
		arquivos.addAll(this.buscaNaPasta("./" + pastaTemporaria, new ArrayList<File>(), exts));
		return arquivos;
	}
	
	private List<File> buscaNaPasta(String raiz, List<File> arquivos, String... exts) {
		for (File arquivo : new File(raiz).listFiles()) {
			if(!arquivo.isDirectory() && !arquivo.getName().equals(pastaTemporaria)) {
				for (String ext : exts) {
					if(arquivo.getName().endsWith(ext)) {
						arquivos.add(arquivo);
					}
				}
			}
		}
		return arquivos;
	}
	
	private List<File> buscaEmSubpastas(String raiz, List<File> arquivos, String... exts) {
		
		File[] files = new File(raiz).listFiles();
		
		for (File arquivo : files) {
			if(arquivo.isDirectory() && !arquivo.getName().equals(pastaTemporaria)) {
				buscaEmSubpastas(raiz + "/" + arquivo.getName() + "/", arquivos, exts);
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
