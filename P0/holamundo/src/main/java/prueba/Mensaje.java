package prueba;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "mensaje", eager = true)
@RequestScoped
public class Mensaje {
	private String mensaje = "¡Hola Mundo!";

	public String getMensaje(){
		return mensaje;
	}

	public void setMensaje (String mensaje){
		this.mensaje = mensaje;
	}
}