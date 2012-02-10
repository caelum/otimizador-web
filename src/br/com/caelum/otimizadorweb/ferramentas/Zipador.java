package br.com.caelum.otimizadorweb.ferramentas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.google.common.io.Files;

public class Zipador {

	private final int TAMANHO_BUFFER = 2048;
	private final File pasta;

	public Zipador(File temp) {
		this.pasta = temp;
	}
	
	public void compactarPara(String pastaDestino) {
		try {
			ZipOutputStream saida = new ZipOutputStream(new FileOutputStream(pastaDestino));
			compacta(this.pasta.getName(), saida);
			saida.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void compacta(String dir2zip, ZipOutputStream stream) {
		try {
			File zipDir = new File(dir2zip);
			String[] conteudo = zipDir.list();
			
			byte[] readBuffer = new byte[TAMANHO_BUFFER];
			int bytesIn = 0;
			
			for (String dir : conteudo) {
				File file = new File(dir2zip, dir);
				
				if(file.isDirectory()) {
					compacta(file.getPath(), stream);
					continue;
				}
				
				FileInputStream inputStream = new FileInputStream(file);
				ZipEntry entry = new ZipEntry(file.getPath());
				stream.putNextEntry(entry);
				
				while((bytesIn = inputStream.read(readBuffer)) != -1) {
					stream.write(readBuffer, 0, bytesIn);
				}
				
				inputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cria() {
		boolean mkdir = pasta.mkdirs();

		if (mkdir) {
			System.out.println("Pasta temporária criada com sucesso.");
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
		System.out.println("Pasta temporária removida com sucesso.");
	}
}
