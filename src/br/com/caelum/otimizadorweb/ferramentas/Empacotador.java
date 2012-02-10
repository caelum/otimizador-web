package br.com.caelum.otimizadorweb.ferramentas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.caelum.otimizadorweb.helpers.Buscador;

import com.google.common.io.Files;

public class Empacotador {
	
	private final Buscador buscador;
	private final Minificador minificador;

	public Empacotador(Buscador buscador, Minificador minificador) {
		this.buscador = buscador;
		this.minificador = minificador;
	}
	
	public void geraPackage(String pasta) throws IOException {
		
		System.out.println("Gerando package.css e package.js...");
		
		List<File> arquivos = buscador.buscaArquivosNaPastaTerminadosEm(".", ".css.txt",".js.txt");
		List<String> temporarios = new ArrayList<String>();
		
		for (File file : arquivos) {
			List<String> nomesDosArquivos = Files.readLines(file, Charset.defaultCharset());
			
			String buffer = insereConteudoDosArquivosNoBuffer(new StringBuffer(), nomesDosArquivos);
			
			String nomeDoArquivoTemporario = file.getName().replaceAll(".txt", "");
			Writer out = new FileWriter(new File(pasta,nomeDoArquivoTemporario));
			
			temporarios.add(nomeDoArquivoTemporario);
			
			out.write(buffer);
			out.flush();
		}
		
		minificador.comprimeListaDeArquivos();
		this.removeListaDeArquivos(temporarios);
	}

	public String insereConteudoDosArquivosNoBuffer(StringBuffer buffer, List<String> nomesDosArquivos) throws IOException {
		for (String arquivo : nomesDosArquivos) {
			File temp = new File(arquivo);
			buffer.append(new Scanner(temp).useDelimiter("$$").next());
		}
		return buffer.toString();
	}
	
	private void removeListaDeArquivos(List<String> nomes) {
		for (String nome : nomes) {
			File arquivo = new File(nome);
			arquivo.delete();
		}
	}
}
