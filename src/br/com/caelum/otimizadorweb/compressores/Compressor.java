package br.com.caelum.otimizadorweb.compressores;
import java.io.File;
import java.io.IOException;

import br.com.caelum.otimizadorweb.helpers.Tipo;




public interface Compressor {
	
	Tipo getTipo();
	
	public void comprime(File file) throws IOException;
	
}
