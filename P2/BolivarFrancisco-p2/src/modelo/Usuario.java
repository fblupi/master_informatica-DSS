package modelo;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;
	private String nombre;
	private String apellido;
	private String email;

	/**
	 * Constructor por defecto. Crea un usuario con cadenas vacías en sus datos
	 */
	public Usuario() {
		nombre = apellido = email = "";
	}
	
	/**
	 * Constructo con parametros. Crea un usuario con los parametros de entrada
	 * @param nombre
	 * @param apellido
	 * @param email
	 */
	public Usuario(String nombre, String apellido, String email) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
	}

	/**
	 * Constructor por copia. Copia los datos de un usuario y crea un usuario con estos
	 * @param usuario
	 */
	public Usuario(Usuario usuario) {
		nombre = usuario.getNombre();
		apellido = usuario.getApellido();
		email = usuario.getEmail();
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
