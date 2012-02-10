package br.com.caelum.otimizadorweb.helpers;
import java.io.File;

public enum Tipo {

	CSS {
		@Override
		public boolean aceita(File file) {
			return file.getName().endsWith(".css");
		}
	},
	JS {
		@Override
		public boolean aceita(File file) {
			return file.getName().endsWith(".js");
		}
	},
	HTML {
		@Override
		public boolean aceita(File file) {
			return file.getName().endsWith(".html")  || file.getName().endsWith(".htm");
		}
	};
	public abstract boolean aceita(File file);

}
