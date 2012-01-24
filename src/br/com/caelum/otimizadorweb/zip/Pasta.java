package br.com.caelum.otimizadorweb.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.google.common.io.Files;

public class Pasta {

	static final int TAMANHO_BUFFER = 2048;
	private final File pasta;

	public Pasta(File temp) {
		this.pasta = temp;
	}

	public void compactarPara(String pastaDestino) {

		int cont;

		byte[] dados = new byte[TAMANHO_BUFFER];

		try {
			FileOutputStream destino = new FileOutputStream(pastaDestino);
			ZipOutputStream saida = new ZipOutputStream(new BufferedOutputStream(destino));
			
			File diretorio = this.pasta;

			for (String nome : diretorio.list()) {
				File arquivo = new File(nome);

				System.out.println("Compactando: " + arquivo);
				
				FileInputStream streamDeEntrada = new FileInputStream(this.pasta + "/" + arquivo);
				BufferedInputStream origem = new BufferedInputStream(streamDeEntrada, TAMANHO_BUFFER);
				ZipEntry entry = new ZipEntry(arquivo.getName());
				saida.putNextEntry(entry);
				
				while ((cont = origem.read(dados, 0, TAMANHO_BUFFER)) != -1) {
					saida.write(dados, 0, cont);
				}
				origem.close();
			}
			saida.close();
			System.out.println("Arquivos compactados com sucesso na pasta " + pastaDestino);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cria() {
		boolean mkdir = pasta.mkdirs();

		if (mkdir) {
			System.out.println("Pasta " + pasta + " criada com sucesso.");
		} else {
			System.out.println("Ja existe uma pasta chamada " + pasta + ".");
		}
	
	}

	public void remove() {
		try {
			Files.deleteRecursively(pasta);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
