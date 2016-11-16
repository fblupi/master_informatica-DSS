## Configuración del marco de trabajo de la práctica

### Versiones utilizadas

* Ubuntu 16.04 o Windows 10
* Oracle JDK 8
* Eclipse for Java EE 4.6 (Neon)
* Apache Tomcat 9.0.0.M11
* Jersey 2.24

### Linux

#### Instalar Jersey

* Descargar desde [este enlace](http://repo1.maven.org/maven2/org/glassfish/jersey/bundles/jaxrs-ri/2.24/jaxrs-ri-2.24.zip)
* Extraer en `/usr/local/`

### Windows

#### Instalar Jersey

* Descargar desde [este enlace](http://repo1.maven.org/maven2/org/glassfish/jersey/bundles/jaxrs-ri/2.24/jaxrs-ri-2.24.zip)
* Extraer en `C:\Program Files\Java`

## Primer servicio web RESTful

* Crear un nuevo proyecto *Dynamic Web Project* nombrado `jersey.first`.
* Usar Apache Tomcat v9.0 como *Target runtime* y en *Configuration* usar la configuración por defecto para éste.
* Marcar *Generate web.xml deployment descriptor* en el último paso de la creación del proyecto.
* Mover todos los archivos `.jar` que hay en los directorios `api`, `ext` y `lib` del directorio donde se ha instalado Jersey.
* Agregarlos al `build-path` (http://www.vogella.com/tutorials/Eclipse/article.html#using-jars-libraries-in-eclipse)
* Crear la siguiente clase:

```java
package jersey.first;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// Plain old Java Object it does not extend as class or implements
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation.
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML.

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/hello")
public class Hello {

	// This method is called if TEXT_PLAIN is request
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "Hello Jersey";
	}

	// This method is called if XML is request
	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayXMLHello() {
		return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
	}

	// This method is called if HTML is request
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		return "<html> " + "<title>" + "Hello Jersey" + "</title>" + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
	}

}
```

* Abrir `web.xml` y modificarlo como a continuación:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://xmlns.jcp.org/xml/ns/javaee" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd" id="WebApp_ID" version="3.1">
	<display-name>jersey.first</display-name>
	<servlet>
		<servlet-name>Jersey REST Service</servlet-name>
		<servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class>
		<!-- Register resources and providers under com.vogella.jersey.first package. -->
		<init-param>
			<param-name>jersey.config.server.provider.packages</param-name>
			<param-value>jersey.first</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
	</servlet>
	<servlet-mapping>
		<servlet-name>Jersey REST Service</servlet-name>
		<url-pattern>/rest/*</url-pattern>
	</servlet-mapping>
</web-app>
```

* Ejecutar la aplicación y se podrá acceder a la siguiente url desde el navegador http://localhost:8080/jersey.first/rest/hello
* Crear la siguiente clase:

```java
package jersey.first;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class Test {

	public static void main(String[] args) {
		ClientConfig config = new ClientConfig();

		Client client = ClientBuilder.newClient(config);

		WebTarget target = client.target(getBaseURI());

		String response = target.path("rest").path("hello").request().accept(MediaType.TEXT_PLAIN).get(Response.class).toString();
		String plainAnswer = target.path("rest").path("hello").request().accept(MediaType.TEXT_PLAIN).get(String.class);
		String xmlAnswer = target.path("rest").path("hello").request().accept(MediaType.TEXT_XML).get(String.class);
		String htmlAnswer = target.path("rest").path("hello").request().accept(MediaType.TEXT_HTML).get(String.class);

		System.out.println(response);
		System.out.println(plainAnswer);
		System.out.println(xmlAnswer);
		System.out.println(htmlAnswer);
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/jersey.first").build();
	}
}
```

* Ejecutar y comprobar que devuelve el resultado esperado:

```
InboundJaxrsResponse{context=ClientResponse{method=GET, uri=http://localhost:8080/jersey.first/rest/hello, status=200, reason=OK}}
Hello Jersey
<?xml version="1.0"?><hello> Hello Jersey</hello>
<html> <title>Hello Jersey</title><body><h1>Hello Jersey</body></h1></html>
```
