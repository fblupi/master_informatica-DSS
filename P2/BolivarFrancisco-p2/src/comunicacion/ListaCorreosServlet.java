package comunicacion;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import modelo.Usuario;
import modelo.BDUsuario;

public class ListaCorreosServlet extends HttpServlet {	
	@Override
	protected void doPost(HttpServletRequest peticion, HttpServletResponse respuesta) throws ServletException, IOException {
		// Obtener la acción a partir de peticion (getParameter("action");
		
		// Realizar la accion y asignar el URL a la pagina apropiada
			// almacenar los datos en el objeto Usuario
		
			// validar los parametros utilizando los metodos BDUsuario; si existe la direccion de email en la base de datos, mostrar un mensaje y pedir otra direccion
			// Insertar los datos del usuario
			request.setAttribute("user", user);
			request.setAttribute("message", message);
			
		getServletContext()
			.getRequestDispatcher(url)
			.forward(peticion, respuesta);
	}
}
