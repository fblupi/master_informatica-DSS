package modelo;

import java.util.HashMap;
import java.util.Map;

import modelo.Todo;

public enum TodoDao {
	INSTANCE; // para implementar el patron singleton
	
	private Map<String, Todo> proveedorContenidos = new HashMap<>();
	
	private TodoDao() {
		Todo todo = new Todo("1", "Aprender REST");
		todo.setDescripcion("Leer http://lsi.ugr.es/dsbcs/Documentos/Practica/practica3.html");
		proveedorContenidos.put("1", todo);
		todo = new Todo("2", "Aprender algo sobre DSBCS");
		todo.setDescripcion("Leer todo el material de http://lsi.ugr.es/dsbcs");
		proveedorContenidos.put("2", todo);
	}
	
	public Map<String, Todo> getModel(){
		return proveedorContenidos;
	}
}