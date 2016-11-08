package comunicacion;

import java.io.*;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import modelo.Usuario;
import modelo.BDUsuario;

public class ListaCorreosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Peticiones GET: Redirige a POST
	 * @param peticion
	 * @param respuesta	
	 */
	@Override
	protected void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws ServletException, IOException {
		doPost(peticion, respuesta);
	}
	
	/**
	 * Petición POST: Si no hay acción devuelve un HTML con la tabla de usuarios
	 * que hay en la base de datos. Si hay acción:
	 * - aniadirUsuario: Añade un usuario con los datos proporcionados. Si el 
	 *   email existe, no lo añade e informa sobre ello.
	 * - actualizarUsuario: Actualiza un usuario con los datos proporcionados. Si
	 *   el email no existe informa sobre ello.
	 * - eliminarUsuaio: Elimina un usuario con los datos proporcionados. Si el
	 *   email no existe informa sobre ello.
	 * @param peticion
	 * @param respuesta
	 */
	@Override
	protected void doPost(HttpServletRequest peticion, HttpServletResponse respuesta) throws ServletException, IOException {
		String accion = peticion.getParameter("action");
		
		if (accion == null) {	// no hay acción -> renderiza usuarios en HTML
			respuesta.setContentType("text/html");		// repuesta tipo HTML
			respuesta.setCharacterEncoding("UTF-8");	// codificada en UTF-8
			
			PrintWriter writer = respuesta.getWriter();	// obtiene PrintWriter donde escribir la respuesta
			
			writer.println("<h1>Usuarios</h1>");
			writer.println("<table>");
			writer.println("<thead>");
			writer.println("<tr>");
			writer.println("<th>Nombre</th>");
			writer.println("<th>Apellido</th>");
			writer.println("<th>Email</th>");
			writer.println("</tr>");
			writer.println("</thead>");
			writer.println("<tbody>");
			for (Usuario usuario: BDUsuario.listarUsuarios()) {
				writer.println("<tr>");
				writer.println("<td>" + usuario.getNombre() + "</td>");
				writer.println("<td>" + usuario.getApellido() + "</td>");
				writer.println("<td>" + usuario.getEmail() + "</td>");
				writer.println("</tr>");
			}
			writer.println("</tbody>");
			writer.println("</table>");
		} else {	// hay acción -> la detecta y la realiza
			String nombre = peticion.getParameter("nombre");
			String apellido = peticion.getParameter("apellido");
			String email = peticion.getParameter("email");
			
			ObjectOutputStream oos = new ObjectOutputStream(respuesta.getOutputStream()); // obtiene ObjectOutputStream donde se informará del resultado
			
			switch (accion) {
			case "aniadirUsuario":
				if (!BDUsuario.existeEmail(email)) {
					Usuario usuario = new Usuario();
					usuario.setNombre(nombre);
					usuario.setApellido(apellido);
					usuario.setEmail(email);
					BDUsuario.insertar(usuario);
					oos.writeInt(0);
					oos.writeObject("Usuario aniadido correctamente.");
				} else {
					oos.writeInt(1);
					oos.writeObject("Ya existe un usuario con el email " + email + ".");
				}
				break;
			case "actualizarUsuario":
				if (BDUsuario.existeEmail(email)) {
					Usuario usuario = BDUsuario.seleccionarUsuario(email);
					usuario.setNombre(nombre);
					usuario.setApellido(apellido);
					BDUsuario.actualizar(usuario);
					oos.writeInt(0);
					oos.writeObject("Usuario actualizado correctamente.");
				} else {
					oos.writeInt(1);
					oos.writeObject("No existe un usuario con el email " + email + ".");
				}
				break;
			case "eliminarUsuario":
				if (BDUsuario.existeEmail(email)) {
					Usuario usuario = BDUsuario.seleccionarUsuario(email);
					BDUsuario.eliminar(usuario);
					oos.writeInt(0);
					oos.writeObject("Usuario eliminado correctamente.");
				} else {
					oos.writeInt(1);
					oos.writeObject("No existe un usuario con el email " + email + ".");
				}
				break;
	  		default: // por defecto lista usuarios
	  			List<Usuario> usuarios = BDUsuario.listarUsuarios();
	  			oos.writeObject(usuarios);
			}
			
			oos.flush();
			oos.close();
		}		
	}
}
