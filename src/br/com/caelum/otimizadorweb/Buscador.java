package br.com.caelum.otimizadorweb;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class Buscador {
	
	public List<File> buscaArquivosLocaisTerminadosEm(final String ext) throws FileNotFoundException {
		FileFilter filtroDeExtensao = new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(ext);
			}
		};
		File[] files = new File(".").listFiles(filtroDeExtensao);
		
		return Arrays.asList(files);
	}
	
}
