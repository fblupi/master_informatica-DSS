package modelo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Todo {
	private String id;
	private String resumen;
	private String descripcion;
	
	public Todo() {
		
	}
	
	public Todo(String id, String resumen) {
		this.id = id;
		this.resumen = resumen;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getResumen() {
		return resumen;
	}
	
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}