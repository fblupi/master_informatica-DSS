package prueba;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name = "datosUsuario", eager = true)
@SessionScoped
public class DatosUsuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static Map<String,String> paises;
	private String paisSeleccionado = "España"; // valor por defecto

	static {
		paises = new LinkedHashMap<String,String>();
		paises.put("es", "España"); // local, nombre del país
		paises.put("en", "United Kingdom");
		paises.put("fr", "France");
		paises.put("de", "Deutschland");	
	}
	
	public void localCambiado(ValueChangeEvent e){
		// asignar un nuevo valor al país
		setPaisSeleccionado(e.getNewValue().toString());
	}
	
	public Map<String, String> getPaises() {
		return paises;
	}

	public String getPaisSeleccionado() {
		return paisSeleccionado;
	}

	public void setPaisSeleccionado(String paisSeleccionado) {
		this.paisSeleccionado = paisSeleccionado;
	}

}
