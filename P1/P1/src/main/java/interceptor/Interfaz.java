package interceptor;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Interfaz {
	private String url = "http://localhost:8080/P1/home.xhtml";
	
	public void ejecutar(double numeroVueltas) throws IOException, URISyntaxException {
		System.out.println("Ejecutando interfaz");

		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().browse(new URI(url));
		} else {
			Runtime.getRuntime().exec("/usr/bin/firefox -new-window " + url);
		}
	}
}
