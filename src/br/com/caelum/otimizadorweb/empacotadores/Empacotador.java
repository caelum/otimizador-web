package br.com.caelum.otimizadorweb.empacotadores;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.caelum.otimizadorweb.Buscador;
import br.com.caelum.otimizadorweb.Minificador;

import com.google.common.io.Files;


public class Empacotador {
	
	private final Buscador buscador;
	private final Minificador minificador;

	public Empacotador(Buscador buscador, Minificador minificador) {
		this.buscador = buscador;
		this.minificador = minificador;
	}
	
	public void geraPackage(String pasta) throws IOException {
		List<File> arquivos = buscador.buscaArquivosLocaisTerminadosEm(".css.txt",".js.txt");
		List<String> temporarios = new ArrayList<String>();
		
		for (File file : arquivos) {
			StringBuffer buffer = new StringBuffer();
			List<String> lines = Files.readLines(file, Charset.defaultCharset());
			
			for (String line : lines) {
				File temp = new File(line);
				buffer.append(new Scanner(temp).useDelimiter("$$").next());
			}
			
			String nomeDoArquivoTemporario = file.getName().replaceAll(".txt", "");
			Writer out = new FileWriter(new File(pasta,nomeDoArquivoTemporario));
			
			temporarios.add(nomeDoArquivoTemporario);
			
			out.write(buffer.toString());
			out.flush();
		}
		
		minificador.comprimeListaDeArquivos();
		this.removeListaDeArquivos(temporarios);
	}
	
	private void removeListaDeArquivos(List<String> nomes) {
		for (String nome : nomes) {
			File arquivo = new File(nome);
			arquivo.delete();
		}
	}
}
