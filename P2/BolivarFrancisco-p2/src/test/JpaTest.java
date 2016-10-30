package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modelo.BDUsuario;
import modelo.Usuario;

public class JpaTest {	
	private String nombre1 = "Fran";
	private String nombre2 = "Pepe";
	private String nombre3 = "Juan";
	private String apellido1 = "Bolivar";
	private String apellido2 = "Lopez";
	private String apellido3 = "Perez";
	private String email1 = "fblupi@correo.ugr.es";
	private String email2 = "pepelopez@correo.ugr.es";
	private String email3 = "juanperez@correo.ugr.es";
	
	@Before
	public void setUp() {
		BDUsuario.insertar(new Usuario(nombre1, apellido1, email1));
	}
	
	@Test
	public void test() {
		// Comprobar mail
		assertTrue(BDUsuario.existeEmail(email1));
		assertFalse(BDUsuario.existeEmail(email2));
		
		// Seleccionar
		Usuario usuario = BDUsuario.seleccionarUsuario(email1);
		assertEquals(usuario.getNombre(), nombre1);
		assertEquals(usuario.getApellido(), apellido1);
		assertEquals(usuario.getEmail(), email1);
		usuario = BDUsuario.seleccionarUsuario(email2);
		assertEquals(usuario, null);
		
		// Listar 
		List<Usuario> usuarios = BDUsuario.listarUsuarios();
		assertEquals(usuarios.size(), 1);
		
		// Insertar
		assertFalse(BDUsuario.existeEmail(email2));
		BDUsuario.insertar(new Usuario(nombre2, apellido2, email2));
		assertTrue(BDUsuario.existeEmail(email2));
		
		// Actualizar
		usuario = BDUsuario.seleccionarUsuario(email1);
		assertEquals(usuario.getNombre(), nombre1);
		assertEquals(usuario.getApellido(), apellido1);
		usuario.setNombre(nombre2);
		BDUsuario.actualizar(usuario);
		assertEquals(usuario.getNombre(), nombre2);
		assertEquals(usuario.getApellido(), apellido1);
		usuario.setApellido(apellido2);
		BDUsuario.actualizar(usuario);
		assertEquals(usuario.getNombre(), nombre2);
		assertEquals(usuario.getApellido(), apellido2);
		
		// Eliminar
		assertTrue(BDUsuario.existeEmail(email2));
		BDUsuario.eliminar(new Usuario(nombre2, apellido2, email2));
		assertFalse(BDUsuario.existeEmail(email2));
	}
}