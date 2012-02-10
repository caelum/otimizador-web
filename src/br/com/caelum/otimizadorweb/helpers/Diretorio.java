package br.com.caelum.otimizadorweb.helpers;

import java.io.File;

public class Diretorio {

	private final File pasta;

	public Diretorio(File pasta) {
		this.pasta = pasta;
	}
	
	public File criaPara(File file) {
		File path = new File(pasta, file.getParent());
		path.mkdirs();
		return path;
	}
}
