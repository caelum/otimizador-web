package br.com.caelum.otimizadorweb.compressores;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.com.caelum.otimizadorweb.Diretorio;
import br.com.caelum.otimizadorweb.Tipo;

import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.JSSourceFile;

public class CompressorJsGoogle implements Compressor{

	private Diretorio pasta;
	private Tipo tipo = Tipo.JS;

	public CompressorJsGoogle(File pasta) {
		this.pasta = new Diretorio(pasta);
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	
	public void comprime(File file) throws IOException {

		File parent = pasta.criaPara(file);
		
		Compiler compiler = new Compiler();
		CompilerOptions options = new CompilerOptions();
		CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options);
		
		List<JSSourceFile> emptyList = Collections.emptyList();
		
		compiler.compile(emptyList, Arrays.asList(JSSourceFile.fromFile(file)), options);
		
		FileWriter out = new FileWriter(new File(parent,"google-compress-" + file.getName()));
		out.write(compiler.toSource());
		out.close();
	}
}
