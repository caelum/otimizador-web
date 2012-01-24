package br.com.caelum.otimizadorweb.compressores;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

import br.com.caelum.otimizadorweb.Tipo;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;


public class CompressorHtml implements Compressor{
	
	private File pasta;
	private Tipo tipo = Tipo.HTML;

	public CompressorHtml(File pasta) {
		this.pasta = pasta;
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	
	public void comprime(File file) throws IOException {
		
		Writer out = new FileWriter(new File(pasta,file.getName()));
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(new Scanner(file).useDelimiter("$$").next());
		
		HtmlCompressor compressor = new HtmlCompressor();
		String compress = compressor.compress(buffer.toString());
		
		out.write(compress);
		
		out.flush();
	}

}
