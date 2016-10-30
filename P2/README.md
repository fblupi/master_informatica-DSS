# Práctica 2: Programación OO con persistencia de entidades

## Enunciado

Utilizando JPA, se pide programar una aplicación para crear Listas de Correo que utilizará un canal (DBUsuario) para escribir los datos de los usuarios de una Lista de Correo en una base de datos. La aplicación ha de utilizar un "connection pool" para permitir conectar rápidamente las hebras de usuarios a la base de datos.

![arquitectura de la aplicacion a desarrollar](https://cloud.githubusercontent.com/assets/6973564/19828471/aaea9ed4-9dc6-11e6-90e7-9324a931f163.png)

Ejecutar la aplicación como un proyecto Java en un IDE y utilizarla para añadir usuarios a la lista de correo. Utilizar Workbench o una herramienta similar para ver las tablas de la base de datos, la cual deberá incluir una tabla llamada "usuario"  con columnas que se corresponderán con los campos de la clase Usuario (leer más abajo).

Crear una aplicación de **Administración de Usuarios** que permita visualizar a todos los usuarios, actualizar los usuarios existentes y eliminar los usuarios almacenados en la tabla Usuario de la base de datos.
