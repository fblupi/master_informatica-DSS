package prueba;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

public class EscuchadorAccionUsuario implements ActionListener{
   public void processAction(ActionEvent evento) throws AbortProcessingException {
      // acceder al bean datosUsuario directamente
      DatosUsuario datosUsuario = (DatosUsuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("datosUsuario");
      datosUsuario.setDatos("Hola a todo el mundo por interfaz!");
   }
}