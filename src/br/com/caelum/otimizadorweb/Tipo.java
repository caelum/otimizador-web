package br.com.caelum.otimizadorweb;
import java.io.File;

public enum Tipo {

	CSS {
		@Override
		boolean aceita(File file) {
			return file.getName().endsWith(".css");
		}
	},
	JS {
		@Override
		boolean aceita(File file) {
			return file.getName().endsWith(".js");
		}
	},
	HTML {
		@Override
		boolean aceita(File file) {
			return file.getName().endsWith(".html")  || file.getName().endsWith(".htm");
		}
	};
	abstract boolean aceita(File file);

}
