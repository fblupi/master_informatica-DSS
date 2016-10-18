package interceptor;

public class CalcularDistancia implements Filtro {
	private double RADIO;
	private double revolAnt;
	
	public CalcularDistancia() {
		RADIO = 4.86;
		revolAnt = 0.0;
	}
	
	public double ejecutar(Object o) {
		double revoluciones = (Double) o;
		double distancia = (revoluciones - revolAnt) * 2 * RADIO * Math.PI;
		revolAnt = revoluciones;
		return distancia;
	}
}
