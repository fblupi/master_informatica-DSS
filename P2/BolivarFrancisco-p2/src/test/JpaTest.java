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
	private String nombre = "Fran";
	private String otroNombre = "Pepe";
	private String apellido = "Bolivar";
	private String otroApellido = "Lopez";
	private String email = "fblupi@correo.ugr.es";
	private String otroEmail = "pepelopez@correo.ugr.es";
	
	@Before
	public void setUp() {
		List<Usuario> usuarios = BDUsuario.listarUsuarios();
		for (Usuario usuario: usuarios) {
			BDUsuario.eliminar(usuario);
		}
		Usuario usuario = new Usuario();
		usuario.setNombre(nombre);
		usuario.setApellido(apellido);
		usuario.setEmail(email);
		BDUsuario.insertar(usuario);
	}
	
	@Test
	public void existeEmail() {
		assertTrue(BDUsuario.existeEmail(email));
		assertFalse(BDUsuario.existeEmail(otroEmail));
	}
	
	@Test
	public void seleccionarUsuario() {
		Usuario usuario = BDUsuario.seleccionarUsuario(email);
		assertEquals(usuario.getNombre(), nombre);
		assertEquals(usuario.getApellido(), apellido);
		assertEquals(usuario.getEmail(), email);
		usuario = BDUsuario.seleccionarUsuario(otroEmail);
		assertEquals(usuario, null);
	}

	@Test
	public void insertar() {
		assertFalse(BDUsuario.existeEmail(otroEmail));
		Usuario usuario = new Usuario();
		usuario.setNombre(otroNombre);
		usuario.setApellido(otroApellido);
		usuario.setEmail(otroEmail);
		BDUsuario.insertar(usuario);
		assertTrue(BDUsuario.existeEmail(otroEmail));
	}
	
	@Test
	public void actualizar() {
		Usuario usuario = BDUsuario.seleccionarUsuario(email);
		assertEquals(usuario.getNombre(), nombre);
		assertEquals(usuario.getApellido(), apellido);
		usuario.setNombre(otroNombre);
		BDUsuario.actualizar(usuario);
		assertEquals(usuario.getNombre(), otroNombre);
		assertEquals(usuario.getApellido(), apellido);
		usuario.setApellido(otroApellido);
		BDUsuario.actualizar(usuario);
		assertEquals(usuario.getNombre(), otroNombre);
		assertEquals(usuario.getApellido(), otroApellido);
	}
	
	@Test
	public void eliminar() {
		assertTrue(BDUsuario.existeEmail(email));Usuario usuario = new Usuario();
		usuario.setNombre(nombre);
		usuario.setApellido(apellido);
		usuario.setEmail(email);
		BDUsuario.eliminar(usuario);
		assertFalse(BDUsuario.existeEmail(email));
	}
	
	@Test
	public void listarUsuarios() {
		List<Usuario> usuarios = BDUsuario.listarUsuarios();
		assertEquals(usuarios.size(), 1);
	}
}