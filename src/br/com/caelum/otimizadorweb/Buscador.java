package br.com.caelum.otimizadorweb;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Buscador {
	
	public List<File> buscaArquivosLocais() throws IOException {
		
		List<File> arquivos = new ArrayList<File>();
		
		arquivos.addAll(this.buscaArquivosLocaisTerminadosEm(".html", ".htm", ".css", ".js"));
		
		return arquivos;
	}
	
	public List<File> buscaArquivosLocaisTerminadosEm(final String... exts) throws FileNotFoundException {
		
		ArrayList<File> files = new ArrayList<File>();
		
		for (String ext : exts) {
			files.addAll(Arrays.asList(new File(".").listFiles(terminaEm(ext))));
		
		}
		return files;
	}

	private FileFilter terminaEm(final String ext) {
		FileFilter filtroDeExtensao = new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(ext);
			}
		};
		return filtroDeExtensao;
	}
	
}
