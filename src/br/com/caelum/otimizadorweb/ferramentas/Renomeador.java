package br.com.caelum.otimizadorweb.ferramentas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.otimizadorweb.helpers.Buscador;

public class Renomeador {

	private final Buscador buscador;
	private final List<File> fingerprints;

	public Renomeador(Buscador buscador, List<File> fingerprints) {
		this.buscador = buscador;
		this.fingerprints = fingerprints;
	}
	
	public void renomeia() {
		List<File> htmls = buscador.buscaArquivosTemporariosTerminadosEm(".html",".htm",".js", ".css");
		this.alteraReferenciasDosCss(htmls);
		this.alteraReferenciasDosJs(htmls);
	}

	private void alteraReferenciasDosCss(List<File> arquivos) {
		Pattern regex = Pattern.compile("(?:url\\(|<link.*?href=\")(.*?)\\.css");
		this.alteraReferencias(arquivos, regex, ".css");
	}
	
	private void alteraReferenciasDosJs(List<File> arquivos) {
		Pattern regex = Pattern.compile("(?:\\.script\\(|src=)\"(.*?)\\.js");
		this.alteraReferencias(arquivos, regex, ".js");
	}
	
	private void alteraReferencias(List<File> arquivos, Pattern regex, String extensao) {
		System.out.println("Alterando referencias dos arquivos " + extensao + "...");
		
		for (File original : arquivos) {
			String buffer = enviaArquivoParaBuffer(original);
			buscaPadrao(regex, original, buffer, extensao);
		}
	}

	private void buscaPadrao(Pattern regex, File original, String buffer, String extensao) {
		
		Matcher matcher = regex.matcher(buffer);
		
		while (matcher.find()) {
			String referenciaOriginal = matcher.group(1);
			buffer = atualizaBuffer(original, buffer, referenciaOriginal, extensao);
			atualizaArquivo(original, buffer);
		}
	}

	private String atualizaBuffer(File original, String buffer,	String referenciaOriginal, String extensao) {
		Pattern pattern = Pattern.compile(referenciaOriginal + "\\.");
		
		for (File fingerprint : fingerprints) {
			String nomeDoFingerprint = fingerprint.getParentFile().getName() + "/" + fingerprint.getName();
			buffer = tentaEncontrarCorrespondencia(buffer, referenciaOriginal, extensao, pattern, fingerprint, fingerprint.getName());
			buffer = tentaEncontrarCorrespondencia(buffer, referenciaOriginal, extensao, pattern, fingerprint, nomeDoFingerprint);
		}
		return buffer;
	}

	private String tentaEncontrarCorrespondencia(String buffer, String referenciaOriginal, 
			String extensao, Pattern pattern, File fingerprint, String nomeDoFingerprint) {
		Matcher matcher = pattern.matcher(nomeDoFingerprint);
		if(matcher.find()) {
			buffer = buffer.replaceAll(referenciaOriginal + extensao, nomeDoFingerprint);
		}
		return buffer;
	}

	private String enviaArquivoParaBuffer(File original) {
		String buffer = "";
		try {
			buffer += new Scanner(original).useDelimiter("$$").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	private void atualizaArquivo(File original, String builder) {
		try {
			FileWriter out = new FileWriter(original.getPath());
			BufferedWriter writer = new BufferedWriter(out);
			writer.write(builder);
			writer.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
