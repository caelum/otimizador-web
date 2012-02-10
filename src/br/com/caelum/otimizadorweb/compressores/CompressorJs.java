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

import com.yahoo.platform.yui.compressor.JavaScriptCompressor;


public class CompressorJs implements Compressor{
	
	private Diretorio pasta;
	private Tipo tipo = Tipo.JS;

	public CompressorJs(File pasta) {
		this.pasta = new Diretorio(pasta);
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	
	public void comprime(File file) throws IOException{
		
		File parent = pasta.criaPara(file);
		
		Reader in = new InputStreamReader(new FileInputStream(file));
		Writer out = new OutputStreamWriter(new FileOutputStream(new File(parent,file.getName())));
		
		JavaScriptCompressor compressor = new JavaScriptCompressor(in, null);
		compressor.compress(out, -1, true, false, false, true);
		out.flush();
	}


}
