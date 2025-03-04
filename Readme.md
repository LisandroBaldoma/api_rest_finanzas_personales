# Personal Finance API

## Descripción del Proyecto
Este proyecto consiste en una API para la gestión de usuarios en una aplicación de finanzas personales. El objetivo es aplicar conceptos fundamentales de desarrollo backend con **Java** utilizando el framework **Spring Boot**, con una arquitectura organizada por capas.

### Funcionalidades Implementadas
- Gestión de usuarios con operaciones CRUD (Crear, Leer, Actualizar, Eliminar).
- Consultas personalizadas para búsqueda de usuarios por nombre y email.
- Validaciones de datos con **@Valid** para asegurar la integridad de la información.
- Manejo global de excepciones con **GlobalExceptionHandler**.
- Simulación de persistencia con una lista en memoria.

### Arquitectura
La API se organiza con las siguientes capas:

- **Entity:** Representa los objetos del modelo de dominio.
- **Repository:** Define las operaciones de persistencia (DAO).
- **Service:** Contiene la lógica de negocio dividida en dos servicios:
    - `UserServiceImpl`: CRUD y lógica de negocio básica.
    - `UserServiceQueryImpl`: Consultas personalizadas.
- **Controller:** Gestiona las solicitudes HTTP y las respuestas.
- **Config:** Configuración de excepciones globales y validaciones.

### Endpoints Disponibles
#### Usuarios
- **POST /api/v1/users/register**: Registrar usuario.
- **GET /api/v1/users**: Obtener todos los usuarios.
- **GET /api/v1/users/{id}**: Obtener usuario por ID.
- **PUT /api/v1/users/{id}**: Actualizar usuario.
- **DELETE /api/v1/users/{id}**: Eliminar usuario.
- **GET /api/v1/users/by-email?email=**: Obtener usuario por email.
- **GET /api/v1/users/email-exists?email=**: Verificar si el email ya está registrado.
- **GET /api/v1/users/count**: Contar todos los usuarios.
- **GET /api/v1/users/by-name?name=**: Buscar usuarios por nombre.

### Validaciones
- El modelo **User** aplica las siguientes restricciones:
    - `@NotBlank` para campos obligatorios.
    - `@Email` para validar formato de email.
    - `@Size` para limitar la longitud de cadenas.

### Excepciones Personalizadas
- `UserNotFoundException`: Usuario no encontrado.
- `EmailAlreadyExistsException`: Email ya registrado.

### Selección de Implementación de `UserDao`

Este proyecto soporta dos implementaciones para el acceso a los datos de los usuarios: **en memoria** (usando listas) y **en archivo JSON**. Puedes elegir cuál implementar a través del archivo de configuración `application.properties`.

#### **1. Implementaciones Disponibles**
- **`UserDaoListImpl`**: Implementación en memoria usando listas. Los datos se pierden cuando la aplicación se detiene.
- **`UserDaoJsonImpl`**: Implementación que almacena los datos en un archivo JSON. Los datos se mantienen entre ejecuciones de la aplicación.

#### **2. Selección de Implementación a través de `application.properties`**

Puedes configurar la implementación de `UserDao` modificando el archivo `application.properties`. Cambia la propiedad `app.userdao.impl` entre las opciones `list` o `json` para elegir cuál de las implementaciones usar.


### Próximas Implementaciones
- Pruebas unitarias con **JUnit 5**.
- Simulación de persistencia con base de datos H2.
- Mocking de servicios con **Mockito**.
- Documentación con **Swagger**.

### Requisitos para Ejecutar el Proyecto
- Java 17 o superior.
- Maven.
- IntelliJ IDEA o cualquier IDE compatible con Spring Boot.

### Cómo Ejecutar el Proyecto
1. Clonar el repositorio.
2. Navegar a la raíz del proyecto.
3. Ejecutar el siguiente comando:
   ```bash
   mvn spring-boot:run
   ```
4. Acceder a la API en: `http://localhost:8080/api/v1/users/test`

### Autor
Desarrollado por: **Lisandro Baldomá**

---
¡Gracias por visitar este proyecto!

