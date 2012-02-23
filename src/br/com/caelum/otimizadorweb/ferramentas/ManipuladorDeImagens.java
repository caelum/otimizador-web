package br.com.caelum.otimizadorweb.ferramentas;

import java.io.File;
import java.io.IOException;
import java.util.List;

import br.com.caelum.otimizadorweb.helpers.Buscador;
import br.com.caelum.otimizadorweb.helpers.Diretorio;

import com.google.common.io.Files;

public class ManipuladorDeImagens {

	private final Buscador buscador;
	private File pastaTemporaria;

	public ManipuladorDeImagens(File pastaTemporaria) {
		this.buscador = new Buscador("./img");
		this.pastaTemporaria = pastaTemporaria;
	}
	
	public void copiaImagens() {
		List<File> imagens = buscador.buscaEmSubpastasArquivosTerminadosEm(".jpg", ".jpeg", ".gif", ".png");
		Diretorio diretorio = new Diretorio(pastaTemporaria);
		for (File imagem : imagens) {
			File path = diretorio.criaPara(imagem);
			try {
				File to = new File(path.getPath() + "/" + imagem.getName());
				Files.copy(imagem, to);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
