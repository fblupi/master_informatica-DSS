package interceptor;

public class Cliente {
	private GestorFiltros gestorFiltros;
	
	public void setGestorFiltros(GestorFiltros gestorFiltros) {
		this.gestorFiltros = gestorFiltros;
	}
	
	public void enviarPeticion(double numeroVueltas) {
		gestorFiltros.ejecutar(numeroVueltas);
	}
}
