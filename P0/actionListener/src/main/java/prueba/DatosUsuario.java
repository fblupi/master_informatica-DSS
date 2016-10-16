package prueba;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "datosUsuario", eager = true)
@SessionScoped
public class DatosUsuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String datos = "datos de prueba";
	
	public String mostrarResultado() {
		return "result";
	}
	
	public void actualizarDatos(ActionEvent e) {
		datos = "Hola a todo el mundo!";
	}
	
	public String getDatos() {
		return datos;
	}
	
	public void setDatos(String datos) {
		this.datos = datos;
	}
}
