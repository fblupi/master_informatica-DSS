package recurso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import modelo.Jugador;
import modelo.JugadorDao;

@Path("/jugadores")
public class JugadoresRecurso {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Jugador> getJugadoresBrowser() {
		List<Jugador> jugadores = new ArrayList<Jugador>();
		jugadores.addAll(JugadorDao.INSTANCE.getModel().values());
		return jugadores;
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Jugador> getJugadores() {
		List<Jugador> jugadores = new ArrayList<Jugador>();
		jugadores.addAll(JugadorDao.INSTANCE.getModel().values());
		return jugadores;
	}
	
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int count = JugadorDao.INSTANCE.getModel().size();
		return String.valueOf(count);
	}
	
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newJugador(
			@FormParam("id") String id, 
			@FormParam("nombre") String nombre, 
			@FormParam("nombreCompleto") String nombreCompleto, 
			@FormParam("posicion") String posicion, 
			@FormParam("calidad") int calidad, 
			@Context HttpServletResponse servletResponse) throws IOException {
		Jugador jugador = new Jugador(id, nombre, posicion, calidad);
		if (nombreCompleto != null) {
			jugador.setNombreCompleto(nombreCompleto);
		}
		JugadorDao.INSTANCE.getModel().put(id, jugador);
		
		servletResponse.sendRedirect("../crear_jugador.html");
	}

	@Path("{jugador}")
	public JugadorRecurso getJugador(@PathParam("jugador") String id) {
		return new JugadorRecurso(uriInfo, request, id);
	}
}