package comunicacion;

import java.io.*;
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
		String url = "/index.html";
		// Obtener la acción a partir de peticion (getParameter("action");
		
		// Realizar la accion y asignar el URL a la pagina apropiada
			// almacenar los datos en el objeto Usuario
		
			// validar los parametros utilizando los metodos BDUsuario; si existe la direccion de email en la base de datos, mostrar un mensaje y pedir otra direccion
			// Insertar los datos del usuario
			peticion.setAttribute("user", user);
			peticion.setAttribute("message", message);
			
		getServletContext()
			.getRequestDispatcher(url)
			.forward(peticion, respuesta);
	}
}
