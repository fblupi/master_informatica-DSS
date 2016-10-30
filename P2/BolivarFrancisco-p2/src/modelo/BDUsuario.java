package modelo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class BDUsuario {
	private static final String PERSISTENCE_UNIT_NAME = "usuario";
	private static EntityManagerFactory factoria;

	/**
	 * Si el usuario no existe lo guarda en la base de datos
	 * @param usuario
	 */
	public static void insertar(Usuario usuario) {
		factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factoria.createEntityManager();

		if (!existeEmail(usuario.getEmail())) {
			em.getTransaction().begin();
			em.persist(usuario);
			em.getTransaction().commit();
			em.close();
		}
	}

	/**
	 * Si el usuario existe modifica su nombre y apellido y guarda los cambios en
	 * la base de datos
	 * @param usuario
	 */
	public static void actualizar(Usuario usuario) {
		factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factoria.createEntityManager();

		if (existeEmail(usuario.getEmail())) {
			Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
			q.setParameter("email", usuario.getEmail());
			Usuario resultado = new Usuario((Usuario) q.getSingleResult());
			em.getTransaction().begin();
			resultado.setNombre(usuario.getNombre());
			resultado.setApellido(usuario.getApellido());
			em.getTransaction().commit();
			em.close();
		}
	}

	/**
	 * Si el usuario existe lo borra de la base de datos
	 * @param usuario
	 */
	public static void eliminar(Usuario usuario) {
		factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factoria.createEntityManager();

		if (existeEmail(usuario.getEmail())) {
			Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
			q.setParameter("email", usuario.getEmail());
			Usuario resultado = new Usuario((Usuario) q.getSingleResult());
			em.getTransaction().begin();
			em.remove(resultado);
			em.getTransaction().commit();
			em.close();
		}
	}

	/**
	 * Si el usuario con el email especificado existe, lo devuelve; si no,
	 * devuelve null
	 * @param email
	 * @return usuario con el email especificado o null si no existe
	 */
	public static Usuario seleccionarUsuario(String email) {
		factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factoria.createEntityManager();
		
		Usuario resultado = null;

		if (existeEmail(email)) {
			Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
			q.setParameter("email", email);
			resultado = new Usuario((Usuario) q.getSingleResult());
			em.close();
		}

		return resultado;
	}

	/**
	 * Si el usuario con el email especificado existe, devuelve true; si no, se
	 * produce una excepcion de no resultados y devuelve false
	 * @param email
	 * @return el usuario con el email especificado existe o no
	 */
	public static boolean existeEmail(String email) {
		factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factoria.createEntityManager();
		
		Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
		q.setParameter("email", email);
		
		try {
			q.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		} finally {
			em.close();
		}
	}
	
	/**
	 * Devuelve todos los usuarios de la base de datos
	 * @return lista con todos los usuarios de la base de datos
	 */
	public static List<Usuario> listarUsuarios() {
		factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factoria.createEntityManager();
		
		Query q = em.createQuery("SELECT u FROM Usuario u");
		
		List<Usuario> resultado = q.getResultList();
		em.close();
		
		return resultado;
	}
}
