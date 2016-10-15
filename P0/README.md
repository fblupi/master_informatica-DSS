## Configuración del marco de trabajo de la práctica

### Versiones utilizadas

* Windows 10 o Ubuntu 16.04
* Oracle JDK 8
* Eclipse for Java EE 4.6 (Neon)
* Maven 3.3.9
* Apache Tomcat 9.0.0.M11

### Windows

#### Prerrequisitos

* Instalar el JDK de Java y comprobar la variable de entorno `JAVA_HOME` (si no se encuentra asignada, añadirla):
```
echo %JAVA_HOME%
C:\Program Files\Java\jdk1.8.0_101
```
* Instalar Eclipse con el instalador proporcionado en la web de éste

#### Instalar Maven

* Descargar y descomprimir Maven
* Crear la variable de entorno `M2_HOME` y asignarle el valor de la ruta al directorio raíz donde esté Maven
* Crear la variable de entorno `M2` y asignarle el valor `%M2_HOME%\bin`
* Crear la variable de entorno `MAVEN_OPTS` y asignarle el valor `-Xms256m -Xmx512m`
* Añadir a la variable de entorno `PATH` el directorio `%M2%`

#### Instalar Tomcat

* Instalar Tomcat con el instalador proporcionado en la web de éste
* Crear la variable de entorno `CATALINA_HOME` y asignarle el valor de la ruta al directorio raíz donde esté Tomcat

### Linux

#### Instalar JDK de Oracle

* Instalar ejecutando las siguientes órdenes:
```
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer
sudo apt-get install oracle-java8-set-default
```

#### Instalar Eclipse

* Descargar [eclipse](http://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/neon/1a/eclipse-jee-neon-1a-linux-gtk-x86_64.tar.gz) y descomprimir en `/opt/`
* (Opcional) Crear un acceso directo añadiendo un archivo con extension `.desktop` a `/usr/share/applications/` con la siguiente información:
```
[Desktop Entry]
Name=Eclipse Java EE
Type=Application
Exec=/opt/eclipse/4.6_neon/java_ee/eclipse
Terminal=false
Icon=/opt/eclipse/4.6_neon/java_ee/icon.xpm
Comment=Integrated Development Environment
NoDisplay=false
Categories=Development;IDE;
```

#### Instalar Maven

* Descargar [maven](http://apache.uvigo.es/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz) y descomprimir en `/usr/local/`
* Agregar al fichero `.bashrc` las siguientes líneas:
```sh
# configure maven
export M2_HOME=/usr/local/apache-maven-3.3.9
export M2=$M2_HOME/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
export PATH=$PATH:$M2
```

#### Instalar Tomcat

* Descargar [tomcat](http://apache.uvigo.es/tomcat/tomcat-9/v9.0.0.M11/bin/apache-tomcat-9.0.0.M11.tar.gz) y descomprimir en `/opt/`
* Agregar al fichero `.bashrc` las siguientes líneas:
```sh
# configure tomcat
export CATALINA_HOME=/opt/apache-tomcat-9.0.0.M11
```

## Proyecto HolaMundo

### Crear proyecto Maven

* Crear un proyecto Maven:
```
mvn archetype:generate -DgroupId=prueba -DartifactId=holamundo -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false
```
* Agregar las siguientes dependencias en el fichero `pom.xml` del directorio raiz del proyecto generado:
```xml
<dependency>
  <groupId>com.sun.faces</groupId>
  <artifactId>jsf-api</artifactId>
  <version>2.1.7</version>
</dependency>
<dependency>
  <groupId>com.sun.faces</groupId>
  <artifactId>jsf-impl</artifactId>
  <version>2.1.7</version>
</dependency>
```

### Importar proyecto en Eclipse

* Preparar proyecto para eclipse con la siguiente orden (desde el directorio raiz del proyecto):
```
mvn eclipse:eclipse -Dwtpversion=2.0
```
* Importar proyecto en Eclipse y cambiar en `Project Properties -> Project Faces` la versión de Java a la 1.8 y en `Runtimes` añadir Apache Tomcat.

* Actualizar el fichero `webapp/WEB-INF/web.xml` con el siguiente contenido:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xmlns="http://java.sun.com/xml/ns/javaee"
   xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
   xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
   http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
   id="WebApp_ID" version="2.5">

   <welcome-file-list>
      <welcome-file>faces/home.xhtml</welcome-file>
   </welcome-file-list>

   <!--
      FacesServlet is main servlet responsible to handle all request.
      It acts as central controller.
      This servlet initializes the JSF components before the JSP is displayed.
   -->

   <servlet>
      <servlet-name>Faces Servlet</servlet-name>
      <servlet-class>javax.faces.webapp.FacesServlet</servlet-class>
      <load-on-startup>1</load-on-startup>
   </servlet>

   <servlet-mapping>
      <servlet-name>Faces Servlet</servlet-name>
      <url-pattern>/faces/*</url-pattern>
   </servlet-mapping>

   <servlet-mapping>
      <servlet-name>Faces Servlet</servlet-name>
      <url-pattern>*.jsf</url-pattern>
   </servlet-mapping>

   <servlet-mapping>
      <servlet-name>Faces Servlet</servlet-name>
      <url-pattern>*.faces</url-pattern>
   </servlet-mapping>

   <servlet-mapping>
      <servlet-name>Faces Servlet</servlet-name>
      <url-pattern>*.xhtml</url-pattern>
   </servlet-mapping>

</web-app>
```

### Editar proyecto

* Crear un paquete `prueba` con la estructura de subdirectorio `src/main/java` que contenga las siguientes clases:
  * `Mensaje.java`

  ```java
  package prueba;

  import javax.faces.bean.ManagedBean;
  import javax.faces.bean.RequestScoped;

  @ManagedBean(name = "mensaje", eager = true)
  @RequestScoped
  public class Mensaje {
  	private String mensaje = "¡Hola Mundo!";

  	public String getMensaje(){
  		return mensaje;
  	}

  	public void setMensaje (String mensaje){
  		this.mensaje = mensaje;
  	}
  }
  ```

  * `HolaMundo.java`

  ```java
  package prueba;

  import javax.faces.bean.ManagedBean;
  import javax.faces.bean.ManagedProperty;
  import javax.faces.bean.RequestScoped;

  @ManagedBean(name = "holaMundo", eager = true)
  @RequestScoped
  public class HolaMundo {
  	@ManagedProperty(value="#{mensaje}")
  	private Mensaje mensajeBean;

  	private String mensaje;

  	public HolaMundo(){
  		System.out.println("¡Hola Mundo empezado!");
  	}

  	public String getMensaje(){		
  		if (mensajeBean != null)		
  			mensaje = mensajeBean.getMensaje();
  		else
  			System.out.println("MensajeBean está a null");

  		return mensaje;
  	}

  	public void setMensajeBean (Mensaje mensajeBean){
  		this.mensajeBean = mensajeBean;
  	}
  }
  ```

* Crear un archivo XHTML llamado `home.xhtml` dentro del subdirectorio `webapp` con el siguiente contenido:
```html
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html">
	<head>
		<title>Ejemplo de prueba con JSF!</title>
	</head>
	<body>
		#{holaMundo.mensaje}
	</body>
</html>
```

### Desplegar

* Hacer click derecho sobre el proyecto y seleccionar `Configure -> Convert to Maven Project`
* Hacer click derecho sobre el proyecto y seleccionar `Run as -> Maven Test`
  * Si salta el error *No compiler is provided in this environment. Perhaps you are running on a JRE rather than a JDK?*. Ir a `Window -> Preferences -> Java -> Installed JREs` y seleccionar el JDK apropiado. Volver a realizar el `Run as -> Maven Test`.
* Hacer click derecho sobre el proyecto y seleccionar `Run as -> Maven Install`
* Copiar el archivo `holamundo.war` generado en el subdirectorio `target` al directorio `webapps` de Tomcat y ejecutar `startup.sh` o `startup.bat` (dependiendo del S.O. utilizado).
* Comprobar entrando en la siguiente dirección: http://localhost:8080/holamundo/home.jsf 
