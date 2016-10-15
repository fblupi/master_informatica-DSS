package prueba;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "holaMundo", eager = true)
@RequestScoped
public class HolaMundo {
	@ManagedProperty(value="#{mensaje}")
	private Mensaje mensajeBean;

	private String mensaje;

	public HolaMundo(){
		System.out.println("fblupi dice: ¡Hola Mundo!");
	}

	public String getMensaje(){		
		if (mensajeBean != null)		
			mensaje = mensajeBean.getMensaje();
		else
			System.out.println("MensajeBean está a null");

		return mensaje;
	}

	public void setMensajeBean (Mensaje mensajeBean){
		this.mensajeBean = mensajeBean;
	}
}