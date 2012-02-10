package br.com.caelum.otimizadorweb.ferramentas;

import java.io.File;
import java.util.List;

import br.com.caelum.otimizadorweb.helpers.Buscador;

public class Fingerprinter {

	private final Buscador buscador;
	private String temp;

	public Fingerprinter(String temp, Buscador buscador) {
		this.temp = temp;
		this.buscador = buscador;
	}
	
	public void paraArquivos() {
		List<File> arquivos = buscador.buscaArquivosNaPastaTerminadosEm("./" + temp, ".htm", ".css", ".js");
		
		System.out.println("Gerando fingerprint dos arquivos...");
		
		for (File file : arquivos) {
			String[] nome = this.split(file.getName());
			file.renameTo(new File(file.getParent() + "/" + nome[0] + "." + file.lastModified() + nome[1]));
		}
	}
	
	private String[] split(String nomeDoArquivo) {
		int ultimoPonto = nomeDoArquivo.lastIndexOf(".");
		
		String extensao = nomeDoArquivo.substring(ultimoPonto);
		String nome = nomeDoArquivo.substring(0, ultimoPonto);
		
		String[] strings = new String[2];
		strings[0] = nome;
		strings[1] = extensao;
		
		return strings;
	}
	
	private long paraPasta() {
		List<File> arquivos = buscador.buscaArquivosNaPastaTerminadosEm("./" + temp, ".htm", ".css", ".js");
		
		System.out.println("Gerando fingerprint para o zip...");
		
		long modificacaoMaisRecente = 0;
		
		for (File file : arquivos) {
			long lastModified = file.lastModified();
			if(lastModified > modificacaoMaisRecente) {
				modificacaoMaisRecente = lastModified;
			}
		}
		return modificacaoMaisRecente;
	}
	
	public String para(String pasta) {
		String[] nome = this.split(pasta);
		return nome[0] + "." + this.paraPasta() + nome[1];
	}
}
