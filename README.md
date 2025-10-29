# Spring Boot for Beginners v2.0

Curso de AmigosCode.com de introducción a Spring Boot y creación de un CRUD en PostgresDB

## Arquitectura del Sistema

```
           [Frontend / Cliente HTTP]
                     │
                     ▼
┌────────────────────────────────────────────┐
│         SoftwareEngineerController         │  ← @RestController: Recibe peticiones HTTP
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
│       SoftwareEngineerRepository           │  ← interface JpaRepository: Acceso a datos
│ - CRUD automático con base de datos        │
└────────────────────────────────────────────┘
                     │ opera sobre
                     ▼
┌────────────────────────────────────────────┐
│            SoftwareEngineer                │  ← @Entity: Representa una tabla en la BD
│ - Representa la tabla en la BBDD           │
│ - Mapeo ORM con JPA                        │
└────────────────────────────────────────────┘
```

## Errores en el curso

1. La clase `SoftwareEngineer` se modifica y no comenta entre los dos videos de "Define the model". El problema viene al cambiar el tipo de atributo de "techStack". Empieza siendo un `String` y de repente es un `List<SofwareEngineer>`. Da error de parámetros del controlador si no se actualiza correctamente cuando se instancia desde el Controller.

2. El archivo `docker-compose.yml` está mal indentado: sobran 2 espacios en `networks:` y `volumes:`.

## Guía para PostgreSQL en Docker

### Configuración inicial

```bash
# Crear contenedor y levantarlo desde Docker Compose
docker compose up -d

# Comprobar que el contenedor está activo y saber ID o nombre
docker ps -a

# Iniciar sesión en el contenedor
docker exec -it postgres bash
```

### Comandos PostgreSQL

```bash
# Iniciar sesión dentro del servicio PostgreSQL
psql -U amigoscode

# Listar bases de datos existentes
\l

# Crear una base de datos
create database nombreBD;

# Borrar una base datos (si no hay conexiones abiertas)
DROP DATABASE nombreBD;

# Conectar a una base de datos
\c amigos

# Mostrar todas las tablas de la base de datos
\dt

# Mostrar todos los objetos en la base de datos
\d

# Mostrar estructura de una tabla
\d software_engineer
```

### Ejemplos de operaciones SQL

```sql
-- Insertar registros
INSERT INTO software_engineer (name, tech_stack) VALUES ('Juan', '{"Angular","Node"}');
INSERT INTO software_engineer (name, tech_stack) VALUES ('Iván', 'Scratch, Minecraft');
INSERT INTO software_engineer (name, tech_stack) VALUES ('Pedro', 'Springboot, Java');

-- Listar registros
SELECT * FROM software_engineer;
```

## Administración con pgAdmin (GUI)

```bash
# Levantar contenedor con pgAdmin
docker run -p 8080:80 \
  -e PGADMIN_DEFAULT_EMAIL=admin@admin.com \
  -e PGADMIN_DEFAULT_PASSWORD=admin \
  dpage/pgadmin4
```

1. Abrir pgAdmin en `localhost:8080`
2. Para conectar con la base de datos usar `host.docker.internal` como host
3. Navegar por las tablas:
   - Servers → amigos → Bases de Datos → Esquemas → Public → Tablas → software_engineer → Columnas
4. Para ver y editar registros:
   - Click derecho en tabla → Ver/Editar Datos → Todas las filas
5. **Importante:** Siempre guardar los cambios con el botón correspondiente

## Administración con Adminer

Adminer es compatible con MySQL, MariaDB, PostgreSQL, CockroachDB, SQLite, MS SQL, Oracle y más bases de datos vía plugins.

```bash
# Crear contenedor Adminer
docker run --name adminer -d -p 8089:8080 adminer
```

1. Acceder desde el navegador: `localhost:8089`
2. En la ruta del servidor usar: `host.docker.internal:5332`
3. Más intuitivo y simple para tareas sencillas de edición de registros o tablas
