<style>
  h1 {
    font-size: 24px !important;
  }

  h2 {
    font-size: 20px !important;
  }

  div {
    font-size: 10px !important;
  }

  p {
    font-size: 11px !important;
  }

  ul {
    font-size: 11px !important;
  }
</style>

# Práctica 3: Implementación de un servicio CRUD en Java

## Enunciado

Un  servicio web que llamaremos 'CRUD' (Create, Read, Update, Delete), que ha de ser RESTful y que nos permitirá mantener una lista de objetos de un determinado dominio de libre elección (reseñas bibliográficas, catálogo de coches, etc..), pero que ha de incorporar imágenes y sonidos, en nuestra aplicación Web a través de llamadas HTTP.

* Nos crearemos un modelo de datos y también una clase Singleton (no admite más de 1 instancia) que servirá como el proveedor de datos para el modelo.
* Utilizaremos una clase basada en enumeración para representar un "proveedor de contenidos":
```java
...
import java.util.HashMap;
import java.util.Map;
public enum TodoDao {
  instance;  
  private Map<String,Todo> contentProvider = new HashMap<String,Todo>();
  private TodoDao() {
    Todo todo = new Todo("1", "Aprender REST");
    todo.setDescripcion("Leer http://...");
    contentProvider.put("1", todo);
    todo = new Todo("2", "Aprender algo sobre DSBCS");
    todo.setDescripcion("Leer todo el material de http://...");
    contentProvider.put("2", todo);
  }
  public Map<String, Todo> getModel(){
    return contentProvider;
  }
}
```
* Nos crearemos los recursos
* Por último, el servicio REST que hemos programado ha de poder ser utilizado a través de formularios HTML.

El formulario permitirá hacer POST de nuevos datos en el servicio. Para lo cual nos crearemos una página (.html) en la carpeta  "WebContent" del proyecto Eclipse que se entregará.    

## Software Utilizado

* Ubuntu 16.04
* Oracle JDK 8
* Eclipse for Java EE 4.6 (Neon)
* Apache Tomcat 9.0.0.M11
* Jersey 2.24

## Configuración del proyecto

* Mover todos los archivos `.jar` que hay en los directorios `api`, `ext` y `lib` del directorio donde se ha instalado Jersey.
* Agregarlos al `build-path` (http://www.vogella.com/tutorials/Eclipse/article.html#using-jars-libraries-in-eclipse)

## Instrucciones de ejecución

* Ejecutar el proyecto en el servidor Apache Tomcat. Click derecho en el proyecto y hacer Run As... -> Run on Server
* Ejecutar el cliente. Click derecho en `Probador.java` y hacer Run As... -> Java Application y se ejecutará un programa que prueba el servicio CRUD implementado
* También se puede usar el explorador para
  * incluir jugadores desde la ruta: http://localhost:8080/BolivarFrancisco-p3/crear_jugador.html,
  * ver los jugadores actuales: http://localhost:8080/BolivarFrancisco-p3/rest/jugadores
  * y el recuento de los jugadores en la base de datos: http://localhost:8080/BolivarFrancisco-p3/rest/jugadores/count
