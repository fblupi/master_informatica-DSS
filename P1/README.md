## Configuración del marco de trabajo de la práctica

### Prerrequisitos

* Tener instalado y configurado JDK 8
* Tener instalado Eclipse for Java EE

### Instalar Maven

* Descargar [maven](http://apache.uvigo.es/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz) y descomprimir en `/usr/local/`
* Agregar al ficher `.bashrc` las siguientes líneas:
```sh
# configure maven
export M2_HOME=/usr/local/apache-maven-3.3.9
export M2=$M2_HOME/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
export PATH=$PATH:$M2
```

### Instalar Tomcat

* Descargar [tomcat](http://apache.uvigo.es/tomcat/tomcat-9/v9.0.0.M10/bin/apache-tomcat-9.0.0.M10.tar.gz) y descomprimir en `/opt/`
* Agregar al ficher `.bashrc` las siguientes líneas:
```sh
# configure tomcat
export CATALINA_HOME=/opt/apache-tomcat-9.0.0.M10
export CATALINA_BASE=/opt/apache-tomcat-9.0.0.M10
```

### Configurar JSF

* Todo
