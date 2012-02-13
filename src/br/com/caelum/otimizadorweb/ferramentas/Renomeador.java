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
		List<File> htmls = buscador.buscaArquivosTemporariosTerminadosEm(".html",".htm",".js");
		this.alteraReferenciasDosCss(htmls);
		this.alteraReferenciasDosJs(htmls);
	}

	private void alteraReferenciasDosCss(List<File> arquivos) {
		Pattern regex = Pattern.compile("<link.*?href=\"(.*?).css\".*?(/>|></link>)");
		
		this.alteraReferencias(arquivos, regex, ".css");
	}
	
	private void alteraReferenciasDosJs(List<File> arquivos) {
		Pattern regex = Pattern.compile("<script.*?src=\"(.*?).js\".*?(/>|></script>)");
		
		this.alteraReferencias(arquivos, regex, ".js");
	}
	
	private void alteraReferencias(List<File> arquivos, Pattern regex, String extensao) {
		System.out.println("Alterando referencias dos arquivos...");
		
		for (File original : arquivos) {
			String buffer = enviaArquivoParaBuffer(original);
			buscaPadrao(regex, original, buffer, extensao);
		}
	}

	private void buscaPadrao(Pattern regex, File original, String buffer, String extensao) {
		
		Matcher matcher = regex.matcher(buffer);
		
		while (matcher.find()) {
			String referenciaCssOriginal = matcher.group(1);
			buffer = atualizaBuffer(original, buffer, referenciaCssOriginal, extensao);
			atualizaArquivo(original, buffer);
		}
	}

	private String atualizaBuffer(File original, String buffer,	String referenciaCssOriginal, String extensao) {
		
		for (File fingerprint : fingerprints) {
			String nomeDoFingerprint = fingerprint.getParentFile().getName() + "/" + fingerprint.getName();
			Pattern pattern = Pattern.compile(referenciaCssOriginal + "\\.");
			
			Matcher matcher = pattern.matcher(nomeDoFingerprint);
			
			if(matcher.find()) {
				buffer = buffer.replaceAll(referenciaCssOriginal + extensao, nomeDoFingerprint);
			}
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
