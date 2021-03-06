package br.com.caelum.otimizadorweb.compressores;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import br.com.caelum.otimizadorweb.helpers.Diretorio;
import br.com.caelum.otimizadorweb.helpers.Tipo;

import com.yahoo.platform.yui.compressor.CssCompressor;


public class CompressorCss implements Compressor{
	
	private final Diretorio pasta;
	private final Tipo tipo = Tipo.CSS;

	public CompressorCss(File pasta) {
		this.pasta = new Diretorio(pasta);
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	
	public void comprime(File file) throws IOException{
		Reader in = new InputStreamReader(new FileInputStream(file));
		File parent = pasta.criaPara(file);
		Writer out = new OutputStreamWriter(new FileOutputStream(new File(parent, file.getName())));
		
		CssCompressor compressor = new CssCompressor(in);
		compressor.compress(out, 0);
		out.flush();
	}
}
