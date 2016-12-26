# Práctica 4: Servicios Web y procesos de negocio

## Enunciados

### Ejercicio 1

Especificar utilizando BPEL 2.0 y la herramienta BPEL Designer para Eclipse el proceso de negocio (cuyo diagrama de actividad se muestra en la siguiente figura) y que se describe informalmente como sigue: "El cliente invoca al proceso de negocio, especificando el nombre del empleado, el destino de su viaje, la fecha de salida, y la fecha de regreso".

![orquestacion de vuelos](https://cloud.githubusercontent.com/assets/6973564/20930454/b04274c2-bbcd-11e6-9126-f68360b761c5.PNG)

El proceso de negocio BPEL comprueba primero la categoría del empleado que va a viajar, que se corresponden con estos tipos de pasaje de avión: (a) clase turista, (b) clase business y (c) avión privado.

Suponemos que disponemos de un SW contra el que se puede hacer la consulta, después de dicha consulta, el proceso BPEL comprobará el precio del billete con 2 líneas aéreas diferentes para encontrar mejor precio; suponemos otra vez que ambas compañías proporcionan un SW que permite realizar todas las gestiones anteriores.

Por último, el proceso BPEL seleccionará el precio más bajo y devolverá un plan de viaje al cliente, para su aprobación.

### Ejercicio 2

Orquestar, de forma simplificada, el mercadeo entre un comprador y un vendedor de un producto solicitado, de acuerdo con el diagrama de interacción que se muestra en la siguiente figura.

![orquestacion de procesos de compra](https://cloud.githubusercontent.com/assets/6973564/20930418/959315aa-bbcd-11e6-9a5c-72ab546cd102.PNG)

(a) El comprador comienza pidiendo un precio al vendedor y el vendedor responde con un precio para el producto o una excepción si no conoce el artículo que le demandan o no estuviera disponible en el almacén.
(b) El comprador continua pidiendo precio al vendedor y entra en un comportamiento repetitivo con actualizaciones (del precio del artículo) hasta que decide comprar el artículo cuando considera que se le ofrece el mejor precio.
(c) Se pide en este ejercicio desarrollar la descripción completa de la orquestación que se ha descrito anteriormente entre el comprador y el vendedor.

## Software Utilizado

* Windows 10
* Oracle JDK 8
* Eclipse for Java EE 4.6 (Neon)
* Apache Tomcat 9.0.0.M11
* BPEL Plugin 1.0.5
* Apache ODE 1.3.6

## Instrucciones de ejecución

### Ejercicio 1

* Importar el proyecto desde el archivo `BolivarFrancisco-p4_1.zip`.

#### Crear servidor *(si no existe)*

* Abrir la perspectiva *Servers* en Eclipse y seleccionar *New*.
* Seleccionar *Ode v1.x server* dentro del paquete Apache y completar con:
  * En *JRE*: `jre1.8.0_111`
  * En *ODE's home directory*: `C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\ode`
  * En *Tomcat's home directory*: `C:\Program Files\Apache Software Foundation\Tomcat 9.0`
  * En *Server address*: `localhost`
  * En *Port*: `8080`
  * En *VM Arguments*: `-Xms128m -Xmx512m -XX:MaxPermSize=256m`
* Una vez creado, hacer click dos veces en su nombre y entrar en *Open launch configuration* y en la pestaña *Classpath*, en *User Entries* hacer click en *Add External JARs...* y seleccionar `C:\Program Files\Apache Software Foundation\Tomcat 9.0\bin\tomcat-juli.jar`.

#### Lanzar

* En el servidor creado, click derecho y pulsar en *Add or remove* y añadir el proyecto `BolivarFrancisco-p4_1`.
* Lanzar servidor, click derecho *Start*.
* Una vez ejecutado hacer click derecho sobre `viajeArtifacts.wsdl` y seleccionar *Web Services* -> *Test with Web Services Explorer*.
* En *Operations* hacer click en *process* y empezar a probar introduciendo datos.

#### Datos para probar

* Como se suponía que se contaba con un servicio tanto para ver el tipo de vuelo según el empleado como el precio en las distintas aerolíneas, se han creado procesos auxiliares que lo simulan según los datos de entrada:
  * Empleado, según el nombre devuelve:
    * fran: *business*
    * enzo: *privado*
    * cualquier otro: *turista*
  * Iberia, según el destino devuelve:
    * londres: *60*
    * chicago: *200*
    * cualquier otro: *100*
  * Vueling, según el destino devuelve:
    * londres: *40*
    * chicago: *250*
    * cualquier otro: *125*
