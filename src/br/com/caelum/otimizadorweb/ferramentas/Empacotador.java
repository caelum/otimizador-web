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

	public Empacotador(Minificador minificador, String pasta) {
		this.buscador = new Buscador(pasta);
		this.minificador = minificador;
	}
	
	public void gera() throws IOException {
		
		List<File> arquivos = buscador.buscaEmSubpastasArquivosTerminadosEm(".css.txt",".js.txt");
		List<String> temporarios = new ArrayList<String>();
		List<File> packages = new ArrayList<File>();
		
		for (File file : arquivos) {
			List<String> nomesDosArquivos = Files.readLines(file, Charset.defaultCharset());
			
			String buffer = insereConteudoDosArquivosNoBuffer(new StringBuffer(), nomesDosArquivos);
			
			String nomeDoArquivoTemporario = file.getName().replaceAll(".txt", "");
			File pack = new File(".", nomeDoArquivoTemporario);
			Writer out = new FileWriter(pack);
			
			temporarios.add(nomeDoArquivoTemporario);
			
			out.write(buffer);
			out.flush();
			
			packages.add(pack);
		}
		
		minificador.minificaLista(buscador.buscaLocalmenteArquivosTerminadosEm(".html", ".htm", ".css", ".js"));
		this.removeListaDeArquivos(temporarios);
	}

	private String insereConteudoDosArquivosNoBuffer(StringBuffer buffer, List<String> nomesDosArquivos) throws IOException {
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
