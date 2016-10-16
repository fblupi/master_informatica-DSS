package prueba;

import javax.faces.application.Application;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

public class EscuchadorEventosSistemaCliente implements SystemEventListener {
	public boolean isListenerForSource(Object valor) {
		// solo para Application
		return (valor instanceof Application);
	}
	
	public void processEvent(SystemEvent evento) throws AbortProcessingException {
		if (evento instanceof PostConstructApplicationEvent) {
			System.out.println("Ha comenzado Application. Ha ocurrido el evento    PostConstructApplicationEvent!");
		}
		if (evento  instanceof PreDestroyApplicationEvent) {
			System.out.println("Ha ocurrido el evento PreDestroyApplicationEvent. Application esta en parada.");
		}
	}
}
