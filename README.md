Spring Boot for Beginners v2.0

Curso de AmigosCode.com de introducción a Spring Boot y creación de un CRUD en PostgresDB

_____________________________________________________________________________________________________________________________
Resumen de la arquitectura

           [Frontend / Cliente HTTP]
                     │
                     ▼
┌────────────────────────────────────────────┐
│         SoftwareEngineerController         │  ← @RestController: Recibe peticiones HTTP	@GetMapping, etc.
│ - Expone endpoints (GET, POST, etc.)       │
└────────────────────────────────────────────┘
                      │ llama a
                      ▼
┌────────────────────────────────────────────┐
│         SoftwareEngineerServices           │  ← @Service: Procesa lógica de negocio
│ - Contiene la lógica de negocio            │
└────────────────────────────────────────────┘
                      │ llama a
                      ▼
┌────────────────────────────────────────────┐
│       SoftwareEngineerRepository           │  ← interface JpaRepository: Acceso a datos persistentes	extends JpaRepository
│ - CRUD automático con base de datos        │
└────────────────────────────────────────────┘
                     │ opera sobre
                     ▼
┌────────────────────────────────────────────┐
│            SoftwareEngineer                │  ← @Entity: Representa una tabla en la base de datos	@Entity, @Id, etc.
│ - Representa la tabla en la BBDD           │
│ - Mapeo ORM con JPA                        │
└────────────────────────────────────────────┘


_____________________________________________________________________________________________________________________________

Errores en el curso:

1)La clase SoftwareEngineer se modifica y no comenta entre los dos videos de "Define the model". El problema viene al cambiar 
el tipo de atributo de "techStack". Empieza siendo un String y de repente es un List<SofwareEngineer>. Da error de parámetros 
del controlador si no se actualiza correctamente cuando se instancia desde el Controller. 

2)el archivo docker-compuse está mal indentado: sobran 2 espacios en networks: y volumes:

_____________________________________________________________________________________________________________________________

GUIA BREVE PARA ENTRAR EN CONTENEDOR POSTGRES:
- Crear contenedor y levantarlo desde Docker Compose a partir del archivo creado en el curso:
	docker compose up -d

- Comprobar que el contenedor está activo y saber ID o nombre:
	docker ps -a

- Iniciar sesión en el contenedor:
	docker exec -it postgres bash

- Para iniciar sesión dentro del servicio PostGres:
	psql -U amigoscode (entra como superusuario "amigoscode")

- Para listar bases de datos existentes:
	\l
	nota: no existe SHOW DATABASES; como en SQL
	
- Para crear una base de datos(sentencia SQL estandar)
	create database nombreBD;
	
- Para borrar una base datos(si no hay conexiones abiertas):
	DROP DATABASE nombreBD;

- Para conectar a una base de datos. (amigos es la base de datos del video que puede contener multiples tablas)
	amigoscode(# \c amigos
	You are now connected to database "amigos" as user "amigoscode".

- mostrar todas las tablas de la base de datos:
	\dt
						List of relations
 	Schema |       Name        | Type  |   Owner
	--------+-------------------+-------+------------
 	public | software_engineer | table | amigoscode
	(1 row)
- mostrar todos los objetos en la base de datos (tablas, secuencias, índices, etc.):

                    	List of relations
 	Schema |           Name           |   Type   |   Owner
	--------+--------------------------+----------+------------
 	public | software_engineer        | table    | amigoscode
 	public | software_engineer_id_seq | sequence | amigoscode
	(2 rows)




- Mostrar estructura de una tabla
	\d software_engineer
                    	Table "public.software_engineer"
   	  Column   |           Type           | Collation | Nullable | Default
		------------+--------------------------+-----------+----------+---------
 	id         | integer                  |           | not null |
 	name       | character varying(255)   |           |          |
 	tech_stack | character varying(255)[] |           |          |
  Indexes:
    "software_engineer_pkey" PRIMARY KEY, btree (id)
    
- Ejemplos para añadir registros por sentencia SQL:
    
    INSERT INTO software_engineer (name, tech_stack) VALUES ('Juan','{"Angular","Node"}');
    
    NOTA**: De repente se ha cambiado en el video el atributo techStack que pasa de List<String> a String
    Ahora para añadir cambia la forma de entrar techStack
    
-Sentencias SQL para entrar manualmente registros:
    INSERT into software_engineer (name, tech_stack) VALUES ('Iván','Scratch , Minecraft');
    INSERT into software_engineer (name, tech_stack) VALUES ('Pedro','Springboot , Java');
    
 - Sentencia SQL para listar los registros introducidos:
    SELECT * FROM software_engineer;

_____________________________________________________________________________________________________________________________    

BREVE GUÍA PARA MODIFICAR LA BBDD EN ENTORNO GUI CON CONTENEDOR DOCKER CON CONTENEDOR pgAdmin.(tipo PHPMyAdmin pero para Postgres)

para levantar contenedor con pgadmin:
docker run -p 8080:80 \
  -e PGADMIN_DEFAULT_EMAIL=admin@admin.com \
  -e PGADMIN_DEFAULT_PASSWORD=admin \
  dpage/pgadmin4

abrir pgadmin con localhost:8080 o el puerto que pongamos.

Para conectar con la base de datos del contenedor no funciona como podemos pensar con localhost:5332 ya que se trata de una conexión entre contenedores
(uno la base de datos y otro su administrador GUI desde web)

Desde el menú web de pgAdmin en ruta del servidor se pone:
host.docker.internal

Otros datos necesarios son el puerto, nombre de la base de datos y el usuario y contraseña de la BBDD

Para ver las columnas de la tabla desde el menú de la izquierda Object Explorer:
Servers->amigos(nombre BBDD)->Bases de Datos->Esquemas->Public->Tablas->software_engineer(nombre de la tabla)->Columnas
Si queremos crear una nueva columna botón derecho sobre Columnas y Crear     


Para ver y editar los registros de la tabla:
Servers->amigos(nombre BBDD)->Bases de Datos->Esquemas->Public->Tablas->software_engineer(nombre de la tabla)
Botón derecho sobre el nombre de la tabla y elegir Ver Editar Datos->Todas las filas

Importante al hacer cambios siempre darle al botón de guardar. Aunque en la GUI ya aparecen los cambios, hasta que no se guardan no se actualiza realmente la base de datos

_____________________________________________________________________________________________________________________________

GUIA RAPIDA PARA ADMINISTRAR CON ADMINER(OTRO GESTOR DE DDBB CON MENOS OPCIONES Y, POR CONSIGUIENTE MAS SENCILLO)

Compatible con MySQL, MariaDB, PostgreSQL, CockroachDB, SQLite, MS SQL, Oracle. 
Otras bbdd son compatibles via plugin: Elasticsearch, SimpleDB, MongoDB, ...

-Para crear contenedor:
docker run --name adminer -d -p 8089:8080 adminer

Desde navegador entrar con localhost:8089

En la opción de ruta del servidor poner 
host.docker.internal:5332

Más intuitivo y simple en su manejo. Para tareas sencillas de editar/cambiar registros o tablas es totalmente funcional y rápido de aprender


