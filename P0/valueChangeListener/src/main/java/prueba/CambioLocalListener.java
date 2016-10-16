package prueba;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

public class CambioLocalListener implements ValueChangeListener { // Escuchador de eventos programado
	public void processValueChange(ValueChangeEvent evento) throws AbortProcessingException {
		// acceso al 'bean' de país directamente
		DatosUsuario datosUsuario = (DatosUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("datosUsuario");
		datosUsuario.setPaisSeleccionado(evento.getNewValue().toString());
	}
}
