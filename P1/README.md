## Configuración del marco de trabajo de la práctica

### Versiones utilizadas

* Windows 10
* Oracle JDK 8
* Eclipse for Java EE 4.6 (Neon)
* Maven 3.3.9
* Apache Tomcat 9.0.0.M11

### Prerrequisitos

* Instalar el JDK de Java y comprobar la variable de entorno `JAVA_HOME` (si no se encuentra asignada, añadirla):

```
echo %JAVA_HOME%
C:\Program Files\Java\jdk1.8.0_101
```

* Instalar Eclipse con el instalador proporcionado en la web de éste

### Instalar Maven

* Descargar y descomprimir Maven
* Crear la variable de entorno `M2_HOME` y asignarle el valor de la ruta al directorio raíz donde esté Maven
* Crear la variable de entorno `M2` y asignarle el valor `%M2_HOME%\bin`
* Crear la variable de entorno `MAVEN_OPTS` y asignarle el valor `-Xms256m -Xmx512m`
* Añadir a la variable de entorno `PATH` el directorio `%M2%`

### Instalar Tomcat

* Instalar Tomcat con el instalador proporcionado en la web de éste
* Crear la variable de entorno `CATALINA_HOME` y asignarle el valor de la ruta al directorio raíz donde esté Tomcat

### Configurar JSF

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
* Preparar proyecto para eclpse con la siguiente orden (desde el directorio raiz del proyecto):
```
mvn eclipse:eclipse -Dwtpversion=2.0
```
* Importar proyecto en Eclipse y cambiar en `Project Properties -> Project Faces` la versión de Java a la 1.8 y en `Runtimes` añadir Apache Tomcat.
