package recurso;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import modelo.Jugador;
import modelo.JugadorDao;

public class JugadorRecurso {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;
	public JugadorRecurso(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Jugador getJugador() {
		Jugador jugador = JugadorDao.INSTANCE.getModel().get(id);
		if (jugador == null) {
			throw new RuntimeException("Get: Jugador con " + id + " no encontrado");
		}
		return jugador;
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public Jugador getJugadorHTML() {
		Jugador jugador = JugadorDao.INSTANCE.getModel().get(id);
		if (jugador == null) {
			throw new RuntimeException("Get: Jugador con " + id + " no encontrado");
		}
		return jugador;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putTodo(JAXBElement<Jugador> jugador) {
		Jugador c = jugador.getValue();
		return putAndGetResponse(c);
	}
	
	@DELETE
	public void deleteJugador() {
		Jugador c = JugadorDao.INSTANCE.getModel().remove(id);
		if (c == null) {
			throw new RuntimeException("Delete: Jugador con " + id + " no encontrado");
		}
	}
	
	private Response putAndGetResponse(Jugador jugador) {
		Response res;
		if (JugadorDao.INSTANCE.getModel().containsKey(jugador.getId())) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		JugadorDao.INSTANCE.getModel().put(jugador.getId(), jugador);
		return res;
	}
}
