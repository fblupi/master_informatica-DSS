package modelo;

import java.util.HashMap;
import java.util.Map;

import modelo.Jugador;

public enum JugadorDao {
	INSTANCE; // para implementar el patron singleton
	
	private Map<String, Jugador> proveedorContenidos = new HashMap<>();
	
	private JugadorDao() {
		Jugador jugador = new Jugador("1", "Jimmy", "Extremo derecho", 94);
		jugador.setNombreCompleto("Jimmy Natali");
		proveedorContenidos.put("1", jugador);
		jugador = new Jugador("2", "Josito", "Carrilero derecho", 72);
		jugador.setNombreCompleto("José Manuel Avilés");
		proveedorContenidos.put("2", jugador);
	}
	
	public Map<String, Jugador> getModel(){
		return proveedorContenidos;
	}
}