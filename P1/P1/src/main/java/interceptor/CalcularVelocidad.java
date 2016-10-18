package interceptor;

public class CalcularVelocidad implements Filtro {
	private double INTERVALO;
	
	public CalcularVelocidad() {
		INTERVALO = 14.27;
	}
	
	public double ejecutar(Object o) {
		double distancia = (Double) o;
		double velocidad = distancia * 3600 / INTERVALO;
		return velocidad;
	}
}
