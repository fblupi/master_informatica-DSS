## Configuración del marco de trabajo de la práctica

### Versiones utilizadas

* Ubuntu 16.04
* Oracle JDK 8
* Eclipse for Java EE 4.6 (Neon)
* EclipseLink 2.6.4
* Apache Derby 10.13.1.1

### Linux

#### Instalar EclipseLink

* Descargar desde [este enlace](http://www.eclipse.org/downloads/download.php?file=/rt/eclipselink/releases/2.6.4/eclipselink-2.6.4.v20160829-44060b6.zip)
* Extraer en `/usr/local/`

#### Apache Derby

* Descargar desde [este enlace](http://apache.rediris.es//db/derby/db-derby-10.13.1.1/db-derby-10.13.1.1-bin.tar.gz)
* Extraer en `/usr/local/`
* (Opcional) Cambiar el nombre del directorio generado a uno más simple como: `db-derby-10.13.1.1-bin`

## Ejemplos

### Ejemplo básico

* Crear un nuevo proyecto java nombrado `jpa.simple`.
* Crear un subdirectorio `lib` donde se ubicarán los JAR (http://www.vogella.com/tutorials/Eclipse/article.html#classpath):
  * `eclipselink.jar` que se encuentra en `/usr/local/eclipselink/jlib/`
  * `javax.persistence_2.1.1.v201509150925.jar` que se encuentra en `/usr/local/eclipselink/jlib/jpa/`
  * `derby.jar` que se encuentra en `/usr/local/db-derby-10.13.1.1-bin/lib/`
* Crear la siguiente clase:

```java
package jpa.simple.modelo;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Completo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String resumen;
  private String descripcion;
  public String getResumen() {
    return resumen;
  }
  public void setResumen(String resumen) {
    this.resumen = resumen;
  }
  public String getDescripcion() {
    return descripcion;
  }
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
  @Override
  public String toString() {
    return "Completo [resumen=" + resumen + ", descripcion=" + descripcion + "]";
  }
}
```

* Crear el subdirectorio `META-INF` en el directorio `src` y crear el fichero `persistence.xml`:

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<persistence xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd"
  version="2.0" xmlns="http://java.sun.com/xml/ns/persistence">
  <persistence-unit name="completos" transaction-type="RESOURCE_LOCAL">
    <class>jpa.simple.modelo.Completo</class>
    <properties>
      <property name="javax.persistence.jdbc.driver" value="org.apache.derby.jdbc.EmbeddedDriver" />
      <property name="javax.persistence.jdbc.url" value="jdbc:derby:/home/fblupi/.databases/simpleDb;create=true" />
      <property name="javax.persistence.jdbc.user" value="test" />
      <property name="javax.persistence.jdbc.password" value="test" />

      <!-- EclipseLink debe crear este esquema de base de datos automaticamente -->
      <property name="eclipselink.ddl-generation" value="create-tables" />
      <property name="eclipselink.ddl-generation.output-mode" value="database" />
    </properties>
  </persistence-unit>
</persistence>
```

* Crear la siguiente clase:

```java
package jpa.simple.main;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpa.simple.modelo.Completo;

public class Principal {
  private static final String PERSISTENCE_UNIT_NAME = "tutorialJPA";
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
```

### Ejemplo entidad relación

* Crear un nuevo proyecto java nombrado `jpa.eclipselink`.
* Crear un subdirectorio `lib` donde se ubicarán los JAR (http://www.vogella.com/tutorials/Eclipse/article.html#classpath):
  * `eclipselink.jar` que se encuentra en `/usr/local/eclipselink/jlib/`
  * `javax.persistence_2.1.1.v201509150925.jar` que se encuentra en `/usr/local/eclipselink/jlib/jpa/`
  * `derby.jar` que se encuentra en `/usr/local/db-derby-10.13.1.1-bin/lib/`
* Crear el paquete `jpa.eclipselink.modelo` con las siguientes clases:
  * Familia

  ```java
  package jpa.eclipselink.modelo;

  import java.util.ArrayList;
  import java.util.List;

  import javax.persistence.Entity;
  import javax.persistence.GeneratedValue;
  import javax.persistence.GenerationType;
  import javax.persistence.Id;
  import javax.persistence.OneToMany;

  @Entity
  public class Familia {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;
    private String descripcion;

    @OneToMany(mappedBy = "familia")
    private final List<Persona> miembros = new ArrayList<Persona>();

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getDescripcion() {
      return descripcion;
    }

    public void setDescripcion(String descripcion) {
      this.descripcion = descripcion;
    }

    public List<Persona> getMiembros() {
      return miembros;
    }

  }
  ```

  * Persona

  ```java
  package jpa.eclipselink.modelo;

  import java.util.ArrayList;
  import java.util.List;

  import javax.persistence.Entity;
  import javax.persistence.GeneratedValue;
  import javax.persistence.GenerationType;
  import javax.persistence.Id;
  import javax.persistence.ManyToOne;
  import javax.persistence.OneToMany;
  import javax.persistence.Transient;

  @Entity
  public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private String id;
    private String nombre;
    private String apellidos;

    private Familia familia;

    private String campoSinSentido = "";

    private List<Empleo> listaEmpleos = new ArrayList<Empleo>();

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getNombre() {
      return nombre;
    }

    public void setNombre(String nombre) {
      this.nombre = nombre;
    }

    public String getApellidos() {
      return apellidos;
    }

    public void setApellidos(String apellidos) {
      this.apellidos = apellidos;
    }

    @ManyToOne
    public Familia getFamilia() {
      return familia;
    }

    public void setFamilia(Familia familia) {
      this.familia = familia;
    }

    @Transient
    public String getCampoSinSentido() {
      return campoSinSentido;
    }

    public void setCampoSinSentido(String campoSinSentido) {
      this.campoSinSentido = campoSinSentido;
    }

    @OneToMany
    public List<Empleo> getListaEmpleos() {
      return this.jobList;
    }

    public void setListaEmpleos(List<Empleo> listaEmpleos) {
      this.listaEmpleos = listaEmpleos;
    }

  }
  ```

  * Empleo

  ```java
  package jpa.eclipselink.modelo;

  import javax.persistence.Entity;
  import javax.persistence.GeneratedValue;
  import javax.persistence.GenerationType;
  import javax.persistence.Id;

  @Entity
  public class Empleo {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;
    private double salario;
    private String descripcionTrabajo;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public double getSalerio() {
      return salario;
    }

    public void setSalario(double salario) {
      this.salario = salario;
    }

    public String getDescripcionTrabajo() {
      return descripcionTrabajo;
    }

    public void setDescripcionTrabajo(String descripcionTrabajo) {
      this.descripcionTrabajo = descripcionTrabajo;
    }
  }
  ```

* Crear el subdirectorio `META-INF` en el directorio `src` y crear el fichero `persistence.xml`:

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<persistence xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd"
  version="2.0" xmlns="http://java.sun.com/xml/ns/persistence">
  <persistence-unit name="relaciones_persistentes" transaction-type="RESOURCE_LOCAL">
    <class>jpa.eclipselink.modelo.Persona</class>
    <class>jpa.eclipselink.modelo.Familia</class>
    <class>jpa.eclipselink.modelo.Empleo</class>
    <properties>
      <property name="javax.persistence.jdbc.driver" value="org.apache.derby.jdbc.EmbeddedDriver" />
      <property name="javax.persistence.jdbc.url" value="jdbc:derby:/home/fblupi/.databases/relacionDb;create=true" />
      <property name="javax.persistence.jdbc.user" value="test" />
      <property name="javax.persistence.jdbc.password" value="test" />

      <!-- EclipseLink debe crear este esquema de base de datos automaticamente -->
      <property name="eclipselink.ddl-generation" value="create-tables" />
      <property name="eclipselink.ddl-generation.output-mode" value="database" />
    </properties>
  </persistence-unit>
</persistence>
```

* Crear los test en JUnit:

```java
package jpa.eclipselink.principal;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import jpa.eclipselink.modelo.Familia;
import jpa.eclipselink.modelo.Persona;

public class JpaTest {
  private static final String PERSISTENCE_UNIT_NAME = "relaciones_persistentes";
  private EntityManagerFactory factoria;

  @Before
  public void setUp() throws Exception {
    factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    EntityManager em = factoria.createEntityManager();

    // Comenzar una nueva transaccion local de tal forma que pueda persistir como una nueva entidad
    em.getTransaction().begin();

    // Leer las entradas que ya hay en la base de datos
    // Las personas no deben tener ningun atributo asignado todavia
    Query q = em.createQuery("select m from Persona m");

    // Comprobar si necesitamos crear entradas en la base
    boolean createNewEntries = (q.getResultList().size() == 0);

    if (createNewEntries) {
      // Vamos a ello
      assertTrue(q.getResultList().size() == 0);
      Familia familia = new Familia();
      familia.setDescripcion("Familia Martinez");
      em.persist(familia);
      for (int i = 0; i < 20; i++) {
        Persona persona = new Persona();
        persona.setNombre("Juan_" + i);
        persona.setApellidos("Martinez_" + i);
        em.persist(persona);
        familia.getMiembros().add(persona);
        // Ahora hacemos que persista la relacion familia-persona
        em.persist(persona);
        em.persist(familia);
      }
    }

    // Ahora hay que hacer "commit" de la transaccion, lo que causa que la
    //entidad se salve en la base de datos.
    em.getTransaction().commit();

    // Ahora hay que cerrar el EntityManager o perderemos nuestras entradas.
    em.close();
  }

  @Test
  public void comprobarPersonas() {
    // Ahora vamos a comprobar la base de datos para ver si las entradas que hemos creado estan alli
    // Nos crearemos un gestor de entidades "fresco"
    EntityManager em = factory.createEntityManager();

    // Realizaremos una consulta simple que consistira en seleccionar a todas las personas
    Query q = em.createQuery("select m from Persona m");

    // Si todo ha ido bien, en la lista de personas hemos de tener a 20 miembros
    assertTrue(q.getResultList().size() == 20);

    em.close();
  }

  @Test
  public void comprobarFamilias() {
    // Nos crearemos un gestor de entidades "fresco"
    EntityManager em = factory.createEntityManager();
    // Recorrer cada una de las entidades y mostrar cada uno de sus campos asi como la fecha de creacion
    Query q = em.createQuery("select f from Familia f");

    // Deberiamos tener una familia con 20 personas
    assertTrue(q.getResultList().size() == 1);
    assertTrue(((Familia) q.getSingleResult()).getMembers().size() == 20);
    em.close();
  }

  @Test(expected = javax.persistence.NoResultException.class)
  public void eliminarPersona() {
    // Nos crearemos un gestor de entidades "fresco"
    EntityManager em = factory.createEntityManager();
    // Comenzar una nueva transaccion local de tal forma que pueda persistir como una nueva entidad
    em.getTransaction().begin();
    // Crear la consulta necesaria eliminar la persona de nombre y apellidos indicados despues
    Query q = em.createQuery("SELECT p FROM Persona p WHERE p.nombre = :nombre AND p.apellidos = :apellidos");
    // Ahora asigno los parametros
    q.setParameter("nombre", "Juan_1");
    q.setParameter("apellidos", "Martinez_!");
    // Ahora utilizo el metodo: "getSingleResult()" para obtener a la persona que me interesa y
    // los metodos: "remove(persona)" y "commit()" para eliminarla de la entidad y confirmar la
    // eliminacion, respectivamente.
    Persona usuario = (Persona) q.getSingleResult();
    em.remove(usuario);
    em.getTransaction().commit();
    Persona persona = (Persona) q.getSingleResult();

    em.close();
  }
}
```
