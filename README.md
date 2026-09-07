# Sistema de Control de Asistencia

Aplicación web Full Stack para registrar empleados y gestionar su estado de asistencia.

## Tecnologías

### Backend

* Java 17+
* Spring Boot
* Spring Data JPA
* MySQL
* Maven
* Bean Validation

### Frontend

* Angular
* TypeScript
* HTML5
* CSS3

## Arquitectura

El backend utiliza una arquitectura por capas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
MySQL
```

### Controller

Expone los endpoints REST y recibe las peticiones HTTP.

### Service

Contiene la lógica de negocio de la aplicación.

### Repository

Utiliza Spring Data JPA para acceder a la base de datos.

### DTO

Se utiliza para controlar la información recibida desde el cliente y desacoplarla de la entidad de persistencia.

## Modelo de datos

La aplicación utiliza una entidad `Empleado` con los siguientes atributos:

| Campo          | Tipo    | Descripción                  |
| -------------- | ------- | ---------------------------- |
| id             | Long    | Identificador autogenerado   |
| nombreCompleto | String  | Nombre completo del empleado |
| puesto         | String  | Puesto del empleado          |
| presente       | boolean | Estado de asistencia         |

Los nuevos empleados se registran inicialmente como `Ausentes`.

## Configuración de base de datos

Crear una base de datos MySQL:

```sql
CREATE DATABASE asistencia_db;
```

Configurar las credenciales en:

```text
backend/src/main/resources/application.properties
```

Ejemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/asistencia_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD

spring.jpa.hibernate.ddl-auto=update

server.port=8080
```

## Ejecución del Backend

Desde el proyecto backend:

```bash
mvn spring-boot:run
```

La API estará disponible en:

```text
http://localhost:8080
```

## Ejecución del Frontend

Desde el proyecto Angular:

```bash
npm install
ng serve
```

La aplicación estará disponible en:

```text
http://localhost:4200
```

## API REST

### Obtener empleados

```http
GET /api/empleados
```

Retorna todos los empleados registrados.

### Registrar empleado

```http
POST /api/empleados
```

Body:

```json
{
  "nombreCompleto": "Juan Pérez",
  "puesto": "Desarrollador"
}
```

El empleado se registra inicialmente como `Ausente`.

### Cambiar asistencia

```http
PUT /api/empleados/{id}/asistencia
```

Alterna el estado del empleado:

```text
Ausente → Presente
Presente → Ausente
```

## Validaciones

El backend valida que:

* El nombre completo sea obligatorio.
* El puesto sea obligatorio.
* El empleado exista antes de modificar su asistencia.

Los errores se gestionan mediante un `GlobalExceptionHandler`.

## Pruebas

Los tests unitarios se ejecutan mediante:

```bash
mvn test
```

Se cubren, entre otros escenarios:

* Creación de empleados.
* Creación inicial como ausente.
* Cambio de estado de asistencia.
* Manejo de empleado inexistente.

## Git

El proyecto utiliza Git para el control de versiones y seguimiento de la evolución del desarrollo.
