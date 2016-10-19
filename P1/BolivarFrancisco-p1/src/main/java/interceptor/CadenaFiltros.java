package interceptor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class CadenaFiltros {
	private Interfaz objetivo;
	private List<Filtro> filtros;
	
	public CadenaFiltros() {
		filtros = new ArrayList<Filtro>();
	}
	
	public void addFiltro(Filtro filtro) {
		filtros.add(filtro);
	}
	
	public void ejecutar(double peticion) throws IOException, URISyntaxException {
		for (Filtro filtro: filtros) {
			System.out.println("Nueva velocidad (m/s) " + filtro.ejecutar(peticion));
		}
		objetivo.ejecutar(peticion);
	}
	
	public void setObjetivo(Interfaz objetivo) {
		this.objetivo = objetivo;
	}
}
