package br.com.caelum.otimizadorweb.helpers;

import java.util.List;

import com.beust.jcommander.Parameter;

public class VerificadorDeParametros {

	@Parameter(names= "-pack", description= "Gera os arquivos package.css e package.js com base nos arquivos css.txt e js.txt, respec.")
	private boolean geraPackage;
	
	@Parameter(names= "-fingerprint", description= "Coloca o timestamp no nome dos arquivos css, js, imagens e altera as referencias internas.")
	private boolean fingerprint;
	
	@Parameter(hidden=true, arity=1)
	private List<String> arquivo;
	
	@Parameter(names= "--help", description="Exibe a lista de parametros suportados.")
	private boolean ajuda;
	
	public boolean geraFingerprint() {
		return fingerprint;
	}
	
	public boolean geraPackage() {
		return geraPackage;
	}
	
	public List<String> getArquivos() {
		return arquivo;
	}
	
	public boolean ajuda() {
		return ajuda;
	}
}
