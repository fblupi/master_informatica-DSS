package jpa.simple.principal;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpa.simple.modelo.Completo;

public class Principal {
  private static final String PERSISTENCE_UNIT_NAME = "completos";
  private static EntityManagerFactory factoria;

  public static void main(String[] args) {
    factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    EntityManager em = factoria.createEntityManager();
    // Leer las entradas existentes y escribir en la consola
    Query q = em.createQuery("select t from Completo t");

    List<Completo> completoLista = q.getResultList();
    for (Completo completo: completoLista) {
      System.out.println(completo);
    }
    System.out.println("Tamano: " + completoLista.size());

    // Ahora vamos a trabajar con una transaccion en la base de datos
    em.getTransaction().begin();
    Completo completo = new Completo();
    completo.setResumen("Esto es una prueba");
    completo.setDescripcion("Esto es una prueba");
    em.persist(completo);
    em.getTransaction().commit();

    em.close();
  }
}