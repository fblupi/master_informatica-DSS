## Configuración del marco de trabajo de la práctica

### Versiones utilizadas

* Ubuntu GNOME 16.04
* Oracle JDK 8
* Eclipse for Java EE 4.6 (Neon)
* Maven 3.3.9
* Apache Tomcat 9.0.0.M10

### Instalar JDK de Oracle

```
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer
sudo apt-get install oracle-java8-set-default
```

### Instalar Eclipse

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

### Instalar Maven

* Descargar [maven](http://apache.uvigo.es/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz) y descomprimir en `/usr/local/`
* Agregar al fichero `.bashrc` las siguientes líneas:

```sh
# configure maven
export M2_HOME=/usr/local/apache-maven-3.3.9
export M2=$M2_HOME/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
export PATH=$PATH:$M2
```

### Instalar Tomcat

* Descargar [tomcat](http://apache.uvigo.es/tomcat/tomcat-9/v9.0.0.M10/bin/apache-tomcat-9.0.0.M10.tar.gz) y descomprimir en `/opt/`
* Agregar al fichero `.bashrc` las siguientes líneas:

```sh
# configure tomcat
export CATALINA_HOME=/opt/apache-tomcat-9.0.0.M10
export CATALINA_BASE=/opt/apache-tomcat-9.0.0.M10
```

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
