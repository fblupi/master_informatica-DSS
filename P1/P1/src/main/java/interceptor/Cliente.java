package interceptor;

import java.io.IOException;
import java.net.URISyntaxException;

public class Cliente {
	private GestorFiltros gestorFiltros;
	
	public void setGestorFiltros(GestorFiltros gestorFiltros) {
		this.gestorFiltros = gestorFiltros;
	}
	
	public void enviarPeticion(double numeroVueltas) throws IOException, URISyntaxException {
		gestorFiltros.ejecutar(numeroVueltas);
	}
}
