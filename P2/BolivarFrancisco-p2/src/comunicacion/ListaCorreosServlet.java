package comunicacion;

import java.io.*;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import modelo.Usuario;
import modelo.BDUsuario;

public class ListaCorreosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws ServletException, IOException {
		doPost(peticion, respuesta);
	}
	
	@Override
	protected void doPost(HttpServletRequest peticion, HttpServletResponse respuesta) throws ServletException, IOException {
		String accion = peticion.getParameter("action");
		String nombre = peticion.getParameter("nombre");
		String apellido = peticion.getParameter("apellido");
		String email = peticion.getParameter("email");
		
		ObjectOutputStream oos = new ObjectOutputStream(respuesta.getOutputStream());
		
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
				Usuario usuario = new Usuario();
				usuario.setNombre(nombre);
				usuario.setApellido(apellido);
				usuario.setEmail(email);
				BDUsuario.insertar(usuario);
				oos.writeInt(0);
				oos.writeObject("Usuario actualizado correctamente.");
			} else {
				oos.writeInt(1);
				oos.writeObject("Ya existe un usuario con el email " + email + ".");
			}
			break;
		case "eliminarUsuario":
			if (BDUsuario.existeEmail(email)) {
				Usuario usuario = new Usuario();
				usuario.setNombre(nombre);
				usuario.setApellido(apellido);
				usuario.setEmail(email);
				BDUsuario.insertar(usuario);
				oos.writeInt(0);
				oos.writeObject("Usuario eliminado correctamente.");
			} else {
				oos.writeInt(1);
				oos.writeObject("Ya existe un usuario con el email " + email + ".");
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
