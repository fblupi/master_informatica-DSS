package modelo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Jugador {
	private String id;
	private String nombre;
	private String nombreCompleto;
	private String posicion;
	private int calidad;
	
	public Jugador() {
		
	}
	
	public Jugador(String id, String nombre, String posicion, int calidad) {
		this.id = id;
		this.nombre = nombre;
		this.posicion = posicion;
		this.calidad = calidad;
	}

	public String getId() {
		return nombre;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getPosicion() {
		return posicion;
	}

	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}

	public int getCalidad() {
		return calidad;
	}

	public void setCalidad(int calidad) {
		this.calidad = calidad;
	}
}
