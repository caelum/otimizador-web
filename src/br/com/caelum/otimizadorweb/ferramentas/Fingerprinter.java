package br.com.caelum.otimizadorweb.ferramentas;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.otimizadorweb.helpers.Buscador;

public class Fingerprinter {

	private final String pasta;
	private final Buscador buscador;

	public Fingerprinter(String pasta) {
		this.pasta = pasta;
		this.buscador = new Buscador(pasta);
	}
	
	public List<File> paraArquivos() {
		List<File> arquivosOriginais = buscador.buscaEmSubpastasArquivosTerminadosEm(".css", ".js");
		List<File> fingerprints = new ArrayList<File>();
		
		System.out.println("Gerando fingerprint dos arquivos...");
		
		for (File arquivo : arquivosOriginais) {
			String[] nome = this.split(arquivo.getName());
			File arquivoComFingerprint = new File(arquivo.getParent() + "/" + nome[0] + "." + arquivo.lastModified() + nome[1]);
			arquivo.renameTo(arquivoComFingerprint);
			fingerprints.add(arquivoComFingerprint);
		}
		
		new Renomeador(pasta, fingerprints).renomeia();
		return fingerprints;
	}
	
	public String para(String nomeDaPasta) {
		String[] nome = this.split(nomeDaPasta);
		return nome[0] + "." + this.paraPasta() + nome[1];
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
		List<File> arquivos = buscador.buscaEmSubpastasArquivosTerminadosEm(".css", ".js");
		
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
}
