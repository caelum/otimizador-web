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
	
	public Renomeador(String pasta) {
		this.buscador = new Buscador(pasta);
		this.fingerprints = null;
	}

	public Renomeador(String pasta, List<File> fingerprints) {
		this.buscador = new Buscador(pasta);
		this.fingerprints = fingerprints;
	}
	
	public void renomeia() {
		List<File> arquivos = buscador.buscaEmSubpastasArquivosTerminadosEm(".html",".htm",".js", ".css");
		
		String regexCss = "(?:url\\(|<link.*?href=\")(.*?)\\.css";
		String regexJs = "(?:\\.script\\(|src=)\"(.*?)\\.js";
		
		this.alteraReferencias(arquivos,regexCss,".css");
		this.alteraReferencias(arquivos,regexJs,".js");
	}
	
	public void renomeiaPackage() {
		List<File> arquivos = buscador.buscaEmSubpastasArquivosTerminadosEm(".html",".htm",".js", ".css");
		
		String regexCss = "(?:url\\(|<link.*?href=\")(.*)\\.css";
		String regexJs = "src=\"(.*)\\.js";
		
		Pattern js = Pattern.compile(regexJs);
		Pattern css = Pattern.compile(regexCss);
		
		for (File file : arquivos) {
			String buffer = enviaArquivoParaBuffer(file);
			buffer = renomeiaOcorrencia(buffer, js.matcher(buffer), ".js");
			buffer = renomeiaOcorrencia(buffer, css.matcher(buffer), ".css");
			reescreveArquivo(file, buffer);
		}
	}
	
	private String renomeiaOcorrencia(String buffer, Matcher matcher, String extensao) {
		while(matcher.find()) {
			for (File fingerprint : fingerprints) {
				String nome = fingerprint.getName();
				if(nome.endsWith(extensao)) {
					nome = nome.replace(extensao, "");
					buffer = buffer.replaceAll(matcher.group(1), nome);
				}
			}
		}
		return buffer;
	}
	
	private void alteraReferencias(List<File> arquivos, String regex, String extensao) {
		Pattern pattern = Pattern.compile(regex);
		System.out.println("Alterando referencias dos arquivos " + extensao + "...");
		
		for (File original : arquivos) {
			String buffer = enviaArquivoParaBuffer(original);
			buscaPadrao(pattern, original, buffer, extensao);
		}
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

	private void buscaPadrao(Pattern regex, File original, String buffer, String extensao) {
		
		Matcher matcher = regex.matcher(buffer);
		
		while (matcher.find()) {
			String referenciaOriginal = matcher.group(1);
			buffer = atualizaBuffer(original, buffer, referenciaOriginal, extensao);
			reescreveArquivo(original, buffer);
		}
	}

	private String atualizaBuffer(File original, String buffer,	String referenciaOriginal, String extensao) {
		Pattern pattern = Pattern.compile(referenciaOriginal + "\\.");
		
		for (File fingerprint : fingerprints) {
			String nomeDoFingerprint = fingerprint.getParentFile().getName() + "/" + fingerprint.getName();
			buffer = procuraCorrespondencia(buffer, referenciaOriginal, extensao, pattern, fingerprint, fingerprint.getName());
			buffer = procuraCorrespondencia(buffer, referenciaOriginal, extensao, pattern, fingerprint, nomeDoFingerprint);
		}
		return buffer;
	}

	private String procuraCorrespondencia(String buffer, String referenciaOriginal, 
			String extensao, Pattern pattern, File fingerprint, String nomeDoFingerprint) {
		Matcher matcher = pattern.matcher(nomeDoFingerprint);
		if(matcher.find()) {
			buffer = buffer.replaceAll(referenciaOriginal + extensao, nomeDoFingerprint);
		}
		return buffer;
	}

	private void reescreveArquivo(File original, String buffer) {
		try {
			FileWriter out = new FileWriter(original.getPath());
			BufferedWriter writer = new BufferedWriter(out);
			writer.write(buffer);
			writer.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
