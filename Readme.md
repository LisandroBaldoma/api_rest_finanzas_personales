# FinanzasPersonales

## Descripción

API para administrar y gestionar gastos personales.

## Propósito, Lenguajes, Frameworks y Herramientas

Este proyecto forma parte del proceso de upskilling en desarrollo Backend con Java, con los siguientes objetivos:

- Desarrollar la lógica y funcionalidad de aplicaciones web.
- Programar en Java utilizando el enfoque orientado a objetos.
- Crear y gestionar APIs RESTful con Spring Boot.
- Aplicar arquitectura REST y el patrón MVC.
- Implementar programación funcional en el backend.
- Construir backends eficientes y escalables.

### Tecnologías utilizadas:

- Java con programación orientada a objetos.
- Collections y Generics.
- JDBC, H2 Database y patrón DAO.
- JUnit5 Jupiter y Mockito para pruebas.
- Spring Framework y Spring Boot.
- API RESTful con Spring Boot.
- Maven para la gestión de dependencias.
- Spring Data JPA y ORM.
- Logging y manejo de excepciones personalizadas.

---

## Arquitectura

### Capas y Responsabilidades

- Se sigue el patrón MVC con las capas:
  - **Controller**: Expone los endpoints REST.
  - **Service**: Contiene la lógica de negocio.
  - **Repository**: Interactúa con la base de datos usando Spring Data JPA.
  - **DTOs**: Maneja las solicitudes y respuestas.
- `ExpenseMapper` se encarga de convertir entre entidades y DTOs.
- Se implementaron excepciones personalizadas.
- Se manejan tres entidades principales: `User`, `Expense` y `Category`.

### Flujo de la Aplicación

Los usuarios pueden registrar y gestionar sus gastos personales mediante la API. La aplicación permite CRUD de usuarios, gastos y categorías, así como consultas específicas.

---

## Requisitos previos

- En el `application.properties` se puede definir si trabajar con H2 en memoria o en archivo.
  ```properties
  # Opciones: memory o file
  app.datasource.mode=file
  ```

---

## Clonación y configuración del proyecto

1. Clonar el repositorio:
   ```sh
   git clone https://github.com/LisandroBaldoma/api_rest_finanzas_personales.git
   ```
2. Importar el proyecto en **IntelliJ IDEA**.
3. Configurar la base de datos en `application.properties` si es necesario.
4. Ejecutar la aplicación desde IntelliJ IDEA con `Run → Spring Boot Application`.

---

## Base de datos

- Se utiliza **H2 Database** en modo en memoria.
- Se puede acceder a la consola de H2 en `http://localhost:8080/h2-console`.
- Los datos iniciales se cargan automáticamente desde `data.sql`.

---

## Endpoints principales

### **Usuarios (**``**)**

Ruta base: `/api/v1/users`

#### **Operaciones CRUD**

| Método | Endpoint    | Descripción                |
| ------ | ----------- | -------------------------- |
| GET    | `/`         | Obtiene todos los usuarios |
| GET    | `/{id}`     | Obtiene un usuario por ID  |
| POST   | `/register` | Registra un nuevo usuario  |
| PUT    | `/{id}`     | Actualiza un usuario       |
| DELETE | `/{id}`     | Elimina un usuario         |

#### **Consultas específicas**

| Método | Endpoint        | Descripción                 |
| ------ | --------------- | --------------------------- |
| GET    | `/by-email`     | Busca usuario por email     |
| GET    | `/email-exists` | Verifica si un email existe |
| GET    | `/count`        | Cuenta el total de usuarios |
| GET    | `/by-name`      | Busca usuarios por nombre   |

### **Categorías (**``**)**

Ruta base: `/api/v1/categories`

#### **Operaciones CRUD**

| Método | Endpoint | Descripción                  |
| ------ | -------- | ---------------------------- |
| POST   | `/`      | Crea una nueva categoría     |
| GET    | `/{id}`  | Obtiene una categoría por ID |
| GET    | `/`      | Obtiene todas las categorías |
| PUT    | `/{id}`  | Actualiza una categoría      |
| DELETE | `/{id}`  | Elimina una categoría        |

#### **Consultas específicas**

| Método | Endpoint  | Descripción                         |
| ------ | --------- | ----------------------------------- |
| GET    | `/active` | Obtiene solo las categorías activas |

### **Gastos (**``**)**

Ruta base: `/api/v1/expenses`

#### **Operaciones CRUD**

| Método | Endpoint       | Descripción              |
| ------ | -------------- | ------------------------ |
| GET    | `/`            | Obtiene todos los gastos |
| GET    | `/{expenseId}` | Obtiene un gasto por ID  |
| POST   | `/`            | Crea un nuevo gasto      |
| PUT    | `/{expenseId}` | Actualiza un gasto       |
| DELETE | `/{expenseId}` | Elimina un gasto         |

#### **Consultas especializadas**

| Método | Endpoint                | Descripción                             |
| ------ | ----------------------- | --------------------------------------- |
| GET    | `/user/{userId}`        | Obtiene gastos por usuario              |
| GET    | `/total-spent/{userId}` | Calcula el total gastado por un usuario |

---

## Manejo de respuestas y errores

- Se usa `ResponseEntity` para manejar respuestas HTTP.
- Códigos de estado utilizados:
  - `200 OK`: Operación exitosa.
  - `201 Created`: Recurso creado exitosamente.
  - `204 No Content`: Eliminación exitosa.
  - `404 Not Found`: Recurso no encontrado.
  - `400 Bad Request`: Datos incorrectos en la solicitud.
- Se implementaron excepciones personalizadas para errores específicos.

---

## Pruebas

- Se implementaron **tests unitarios** para los servicios utilizando **JUnit 5** y **Mockito**.
- Se realizaron **pruebas de integración** para validar el correcto funcionamiento de la API y su interacción con la base de datos.
- Las pruebas verifican:
  - Operaciones CRUD en los servicios.
  - Comportamiento esperado en las consultas específicas.
  - Manejo de excepciones y validaciones.

---

## Ejecución de la aplicación

1. Asegurar que el entorno tenga **Java 17+** y **Maven**.
2. Ejecutar la aplicación desde IntelliJ IDEA.
3. Acceder a los endpoints a través de Postman o cURL.

---

Este README proporciona una visión general del proyecto y sus funcionalidades. ¡Listo para desplegar y probar! 🚀

