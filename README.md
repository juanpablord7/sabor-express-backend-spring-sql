# Sabor Express (Backend - Spring Boot)

*Saborexpress Backend* is the RESTful API built with Spring Boot, designed using a microservices architecture.

Where each core business logic (orders, products, categories, users and roles) is implemented as an independent and functional service.

The backend handles product management, order processing, user authentication with JWT, and role-based access, all connected to a SQL database.

Originally created for fast food ordering, its modular and abstracted design allows it to be reused to any other product or business.

This repository contains only the *Backend* code from the services, and was developed with  **Spring Boot + Java + Maven**

## Documentation
- [Español](#español)
- [English](#english)

# Español

## Tabla de Contenidos

- [💻 Tecnologías](#tecnologías)
- [🧩 Características](#características)
- [🧠 Lógica de Negocio](#lógica-de-negocio)
- [🎯 Propósito del Proyecto](#propósito-del-proyecto)
- [💾 Instalación](#installation)
- [🌐 API / Conexiones](#api--connections)
- [👤 Autor](#author)

## Tecnologías

- **Java 21** – Lenguaje de programacion
- **Spring Boot** – Framework
- **Spring Security** – Gestión de autenticación y autorización.
- **JWT (JSON Web Tokens)** – Autenticación basada en tokens seguros.
- **Spring Data JPA** – Abstracción para operaciones de persistencia en bases de datos.
- **Hibernate** – ORM (Object-Relational Mapping) para el mapeo de entidades a tablas de la base de datos.
- **MySQL**– Sistema de gestión de bases de datos relacionales.
- **Lombok** – Eliminación de código repetitivo (getters, setters, constructores, etc.).
- **Maven** – Gestión de dependencias y construcción del proyecto.


## Características

### Caracteristicas Principales:
- 🛍️ Generacion de Ordenes
- 🧾 Seguimiento y Gestion del estado de las ordenes
- 🍔 Gestión de productos, categorias
- 👨‍🍳 Gestión de ordenes basada en roles (Chef o Repartidor)
- 👥 Gestión de usuarios y roles con permisos personalizados
- 💻 Arquitectura basada en microservicios por dominio
  - Productos
  - Categorías
  - Órdenes
  - Usuarios
  - Roles
  - Imágenes (Almacenamiento y Gestion)

### Caracteristicas Secundarias:
- 🔐 Autenticación con JWT y control de acceso basado en roles
- 🔄 CRUD completo de productos, categorías, usuarios y roles
- 📦 Conexion a base de datos SQL

### ⚙️ Características Futuras:
- 🧠 Guardar el JWT en cookies para mayor seguridad
- 🏠 Registro de direcciones de entrega por parte de los clientes
- 📍 Rastrear en tiempo real la ubicación del repartidor y generar la ruta para entregar las ordenes
- 🔄 Aumentar la gestion del estado de las órdenes (crear nuevos estados, editar o eliminar)
- 🧂 Gestión de ingredientes por producto (agregar/quitar al gusto)
- ⚡ Actualización en tiempo real de los estados de las órdenes”
- 💳 Integración de métodos de pago digitales
- 🧾 Agregado y validación de métodos de pago (Nequi, extracto bancario, etc.)
- 🔐 Autenticación en dos pasos (2FA)
- 🤖 Sugerencias inteligentes de comidas según preferencias y restricciones (ej. vegano, diabetico, sin gluten, bajo en grasa, etc.), basada en inteligencia artificial

## Lógica de Negocio

### Usuarios finales
- 💻 Registro e inicio de sesión.
- 🛍️ Obtencion de productos y filtrarlos por categorías.
- 🛒 Realizar pedidos
- 📄 Acceso a sus órdenes y su perfil de forma segura, identificado automáticamente mediante el JWT (sin necesidad de enviar su usuario o ID manualmente).
- ❌ Cancelación de pedidos posible solo mientras estén en estado “Registrado” (una vez en preparación, ya no pueden ser eliminados).
- ✏️ Edición de información personal: Nombre completo, Usuario, Contraseña y Correo electrónico.
- 🗑️ Eliminación permanente de la cuenta (las órdenes permanecen registradas en el sistema).

### Empleados y administración
Los empleados consumen los servicios del backend según sus permisos, definidos por rol y validados con JWT.

  - 📌 Todos los accesos seguros a datos personales y operaciones basadas en roles se gestionan automáticamente mediante JWT, evitando el envío manual de identificadores.

  - 🧩 Cada dominio de negocio (Usuarios, Productos, Órdenes, etc.) está desacoplado en su propio microservicio, promoviendo la escalabilidad y mantenibilidad.

  - **🔐 Permiso: Gestionar Productos**:
    - Crear, editar y eliminar categorias de productos.
    - Crear, editar y eliminar productos

  - **🔐 Permiso: Gestionar Usuarios**:
    - Visualizar la informacion de los Usuarios (Nombre, Usuario, Correo y Rol)
    - Ascender Usuarios a su mismo Rol:
    Al registrarse, un nuevo empleado recibe el rol predeterminado de Usuario. Otro empleado puede ascenderlo a su mismo rol <br/><br/>
    Por ejemplo, un Chef puede ascender a un usuario al rol de Chef, o devolverlo a Usuario en caso de despido. <br/><br/>
    Sin embargo, ⚠️ No puede modificar el rol de otros empleados ya existentes.

  - **🔐 Permiso: Ascender a Cualquier Rol**:
    - Permite asignar cualquier rol a cualquier empleado, sin restricciones.

  - **🔐 Permiso: Cambiar Contraseñas**:
    - Permite restablecer la contraseña de un usuario (En caso de que el usuario ha olvidado su contraseña y no puede acceder a su cuenta)

  - **🔐 Permiso: Gestionar Roles**:
    - Crear nuevo roles y definir sus permisos.
    - Editar roles existentes: cambiar el nombre, modificar permisos, y establecerlo como Rol predeterminado para nuevos usuarios.
    - Eliminar roles

  - **🔐 Permiso: Gestionar Ordenes**:
    - Visualización completa de todas las órdenes y posibilidad de filtrarlas por estado.

  - **🔐 Permiso: Cocinero (Chef)**: 
    - Permite avanzar las Ordenes entre estos estados:
    <br/><br/>
    Registrado → Cocinando → Entregando.
    <br/><br/>
    Retroceder o avanzar entre esos mismos estados según sea necesario:

  - **🔐 Permiso: Repartidor (Delivery)**: 
    - Permite avanzar las Ordenes entre estos estados:
    <br/><br/>
    Entregando → Entregada.
    <br/><br/>
    Retroceder o avanzar entre esos mismos estados según sea necesario:


## Propósito del Proyecto
Este backend fue desarrollado con el objetivo de aprender e implementar conceptos clave del desarrollo web moderno desde el lado del servidor, incluyendo:

- ⚙️ Arquitectura de Microservicios: Separación por dominios independientes (usuarios, roles, pedidos, productos, etc.), que facilita la escalabilidad, el mantenimiento y la distribución de cargas.

- 🔐 Autenticación y Seguridad: Implementación de autenticación con JWT y control de acceso basado en permisos personalizados por rol, garantizando protección en cada punto de entrada.

- 🛠️ Gestión escalable de categorias, productos, ordenes, usuarios y roles.

- 🧩 Gestión completa de recursos: CRUD robusto y seguro para usuarios, productos, categorías, roles y ordenes, con validaciones y manejo de errores.

- 🧠 Lógica de negocio desacoplada: Cada servicio contiene lógica de negocio independiente, manteniendo la coherencia y claridad del dominio correspondiente.

- 🛠️ Persistencia con SQL y ORM (Hibernate): Integración con base de datos relacional utilizando JPA/Hibernate para el mapeo de entidades y consultas eficientes.

- 🔄 Comunicación eficiente entre servicios: Preparado para escalar en una arquitectura distribuida, minimizando dependencias innecesarias entre servicios.

- 📈 Buenas practicas de Desarrollo: Estructura modular, código limpio, organización por capas (controlador, servicio, repositorio), e integración con frontend.

<br>

# English

## Table of Contents

- [💻 Technologies](#technologies)
- [🧩 Features](#features)
- [🧠 Business Logic](#business-logic)
- [🎯 Project Purpose](#project-purpose)
- [💾 Installation](#installation)
- [🌐 API / Connections](#api--connections)
- [👤 Author](#author)

## Technologies
- **Java 21** – Programming language  
- **Spring Boot** – Framework  
- **Spring Security** – Authentication and authorization management  
- **JWT (JSON Web Tokens)** – Secure token-based authentication  
- **Spring Data JPA** – Abstraction for database persistence operations  
- **Hibernate** – ORM (Object-Relational Mapping) for mapping entities to database tables  
- **MySQL** – Relational database management system  
- **Lombok** – Eliminates boilerplate code (getters, setters, constructors, etc.)  
- **Maven** – Dependency management and project build tool  

## Features

### Main Features:
- 🛍️ Order generation  
- 🧾 Order state tracking and management  
- 🍔 Product and category management  
- 👨‍🍳 Role-based order handling (Chef or Delivery)  
- 👥 User and role management with customizable permissions  
- 💻 Domain-based microservice architecture:
  - Products  
  - Categories  
  - Orders  
  - Users  
  - Roles  
  - Images (Storage & Management)  

### Secondary Features:
- 🔐 JWT-based authentication and role-based access control  
- 🔄 Full CRUD for products, categories, users, and roles  
- 📦 SQL database connection  

### ⚙️ Future Features:
- 🧠 Store JWT in cookies for added security  
- 🏠 Address registration by clients  
- 📍 Real-time delivery tracking and route generation  
- 🔄 Enhanced order state management (create, edit, delete states)  
- 🧂 Ingredient customization per product (add/remove as desired)  
- ⚡ Real-time updates for order statuses  
- 💳 Integration of digital payment methods  
- 🧾 Adding and validating payment methods (Nequi, bank statement, etc.)  
- 🔐 Two-factor authentication (2FA)  
- 🤖 Smart meal recommendations based on preferences and restrictions (e.g., vegan, diabetic, gluten-free, low-fat, etc.), powered by AI  

## Business Logic

### End Users:
- 💻 Registration and login  
- 🛍️ View products and filter by categories  
- 🛒 Place orders  
- 📄 Access their orders and profile securely, auto-identified using JWT (no need to send user ID manually)  
- ❌ Order cancellation is only possible while in “Registered” state (once in preparation, cancellation is disabled)  
- ✏️ Edit personal information: full name, username, password, and email  
- 🗑️ Permanently delete their account (orders remain in the system)  

### Employees and Admin:
Employees consume backend services according to their permissions, defined by role and verified via JWT.

- 📌 All secure access to personal data and role-based operations is managed automatically via JWT, avoiding manual identifier transmission.  
- 🧩 Each business domain (Users, Products, Orders, etc.) is decoupled into its own microservice, promoting scalability and maintainability.  

#### 🔐 Permission: Manage Products
- Create, edit, and delete product categories  
- Create, edit, and delete products  

#### 🔐 Permission: Manage Users
- View user info (Name, Username, Email, and Role)  
- Promote users to their own role:  
  When a new employee registers, they receive the default "User" role. Another employee can promote them to their same role.  
  For example, a Chef can promote a User to Chef, or demote them back to User if dismissed.  
  ⚠️ However, they **cannot** modify the role of other existing employees.  

#### 🔐 Permission: Promote to Any Role
- Allows assigning **any** role to **any** employee, with no restrictions  

#### 🔐 Permission: Change Passwords
- Allows resetting a user's password (in case the user forgot it and cannot log in)  

#### 🔐 Permission: Manage Roles
- Create new roles and define their permissions  
- Edit existing roles: change name, update permissions, set as default for new users  
- Delete roles  

#### 🔐 Permission: Manage Orders
- Full visibility of all orders and ability to filter by status  

#### 🔐 Permission: Chef
- Move orders through these states:  
  Registered → Cooking → Delivering  
- Can move back and forth between those states as needed  

#### 🔐 Permission: Delivery
- Move orders through these states:  
  Delivering → Delivered  
- Can move back and forth between those states as needed  

## Project Purpose

This backend was built with the goal of learning and applying key concepts of modern server-side web development, including:

- ⚙️ Microservice Architecture: Domain-based separation (users, roles, orders, products, etc.), improving scalability, maintenance, and load distribution  
- 🔐 Authentication & Security: JWT-based authentication and permission-based access control to secure every entry point  
- 🛠️ Scalable management of categories, products, orders, users, and roles  
- 🧩 Complete resource management: robust and secure CRUD for users, products, categories, roles, and orders, with validations and error handling  
- 🧠 Decoupled business logic: each service handles its own logic to maintain consistency and domain clarity  
- 🛠️ SQL Persistence with ORM (Hibernate): relational database integration using JPA/Hibernate for efficient entity mapping and queries  
- 🔄 Efficient service communication: designed to scale in distributed architectures while minimizing service coupling  
- 📈 Development Best Practices: modular structure, clean code, layered organization (controller, service, repository), and frontend integration  

## Installation

```bash
# ==================================================
# 🔥 Clone and Enter Project
# ==================================================

# Clone the repository
git clone https://github.com/your-username/project-name.git

# Enter the project directory
cd project-name


# ==================================================
# 🚧 Development
# ==================================================

#✅ Option 1: Run via IntelliJ IDEA (Recommended)
# 1. Open the project root in IntelliJ
# 2. Go to: Run > Edit Configurations > ➕ > Application
# 3. For each microservice, set:
#    - Module: Java 21
#    - Classpath: (Select the microservice module)
#    - Main class: The main Spring Boot class
# 4. Then, create a Compound configuration:
#    - Go to ➕ > Compound
#    - Add all the created Application configs
# 5. Run the Compound to start all microservices

# ✅ Option 2: Run with Maven Wrapper (from terminal)

# In the terminal, from the root directory, run the following commands:

# Windows (on Linux use "./mvnw" instead of "mvnw"):
mvnw -pl api-gateway spring-boot:run
mvnw -pl eureka-server spring-boot:run
mvnw -pl service-category spring-boot:run
mvnw -pl service-image spring-boot:run
mvnw -pl service-order spring-boot:run
mvnw -pl service-product spring-boot:run
mvnw -pl service-role spring-boot:run
mvnw -pl service-user spring-boot:run

# ==================================================
# 🚀 Production
# ==================================================

# 🧱 Step 1: Build the Frontend

# Go into the frontend directory
cd frontend-directory

# Install dependencies
npm install

# Build the frontend
npm run build

# After building, copy everything inside `dist/`
# into the backend directory: → /api-gateway/src/main/resources/static

cp -r dist/* ../api-gateway/src/main/resources/static/


#🧪 Step 2: Package All Backend Services

# Run this from the root backend directory (parent POM)
./mvnw clean package -DskipTests

# Note: JARs will be generated in:
# - api-gateway/target/
# - eureka-server/target/
# - service-*/target/

#📦 Step 3: Collect All JARs into One Directory

# Create a folder for all compiled JARs
mkdir deploy

# Copy the JARs into the deploy folder
cp api-gateway/target/*.jar deploy/
cp eureka-server/target/*.jar deploy/
cp service-category/target/*.jar deploy/
cp service-image/target/*.jar deploy/
cp service-order/target/*.jar deploy/
cp service-product/target/*.jar deploy/
cp service-role/target/*.jar deploy/
cp service-user/target/*.jar deploy/

#🚀 Step 4: Run All Services from the Deploy Folder

cd deploy

# Start each service manually (or via script/monitor)
java -jar eureka-server.jar
java -jar api-gateway.jar
java -jar service-category.jar
java -jar service-image.jar
java -jar service-order.jar
java -jar service-product.jar
java -jar service-role.jar
java -jar service-user.jar
```

## API / Connections

### 🛠️ Configuration
- Puerto por defecto: 8080 - Sirve el frontend y gestiona todas las solicitudes de la aplicación.
- Base URL de la API: http://localhost:8080/api
- Autenticación: Basada en JWT (token enviado en cada solicitud protegida)
- ORM: Hibernate + Spring Data JPA
- Base de datos: MySQL (administrado con SQL Server Management)

### 🔑 Common Headers

```json
Authorization: Bearer <token>
Content-Type: application/json
```

### 💻 Endpoints
- 💻 Category

  CategoryRequest:
  ```json 
  {
    "name": "String",
    "image": "String"
  }
  ```

  CategoryResponse:
  ```json
  {
    "id": 123,
    "name": "String",
    "image": "String"
  }
  ```
    
  | Method | Route             | Description                    | Auth | Permission      | Request                  | Response           |
  |--------|------------------|--------------------------------|------|------------------|--------------------------|--------------------|
  | GET    | `/category`      | Get all categories             | ❌   | -                | -                        | `CategoryResponse[]` |
  | GET    | `/category/{id}` | Get category by ID             | ❌   | -                | -                        | `CategoryResponse`   |
  | POST   | `/category`      | Create a new category          | ✅   | `manageProduct`  | `CategoryRequest`        | `CategoryResponse`   |
  | PUT    | `/category/{id}` | Replace category by ID         | ✅   | `manageProduct`  | `CategoryRequest`        | `CategoryResponse`   |
  | PATCH  | `/category/{id}` | Update category fields by ID   | ✅   | `manageProduct`  | `Partial<CategoryRequest>` | `CategoryResponse` |
  | DELETE | `/category/{id}` | Delete category by ID          | ✅   | `manageProduct`  | -                        | `Message`           |


- 💻 Product

  ProductRequest:
  ```json 
  {
    "name": "String",
    "price": 123,
    "category": 123,
    "image": "String"
  }
  ```

  ProductResponse:
  ```json
  {
    "id": 123,
    "name": "String",
    "price": 123,
    "category": 123,
    "image": "String"
  }
  ```

  | Method | Route                                | Description                     | Auth | Permission      | Request          | Response                             |
  |--------|--------------------------------------|---------------------------------|------|------------------|------------------|--------------------------------------|
  | GET    | `/product?page=&limit=`              | Get paginated products          | ❌   | -                | -                | `PaginatedResponse<ProductResponse>` |
  | GET    | `/product?page=&limit=&categoryId=`  | Get products by category        | ❌   | -                | -                | `PaginatedResponse<ProductResponse>` |
  | POST   | `/product`                           | Create a new product            | ✅   | `manageProduct`  | `ProductRequest` | `ProductResponse`                    |
  | PUT    | `/product/{id}`                      | Replace product by ID           | ✅   | `manageProduct`  | `ProductRequest` | `ProductResponse`                    |
  | PATCH  | `/product/{id}`                      | Update product fields by ID     | ✅   | `manageProduct`  | `ProductRequest` | `ProductResponse`                    |
  | DELETE | `/product/{id}`                      | Delete product by ID            | ✅   | `manageProduct`  | -                | `Message`                            |




- 💻 Order

  OrderRequest:
  ```json 
  {
    "product": [1, 2, 4, 6],
    "quantity": [3, 8, 2, 1]
  }
  ```

  OrderViewResponse (Without Items):
  ```json
  {
    "id": 123,
    "state": 2,
    "userId": 7,
    "totalPrice": 9.99,
    "createdAt": "2025-01-20T12:34:56.000Z",
    "updatedAt": "2025-01-21T09:15:30.000Z"
  }
  ```

  OrderResponse (With Items):
  ```json
  {
    "id": 123,
    "state": 2,
    "userId": 7,
    "totalPrice": 9.99,
    "items": [
      {
        "id": 123,
        "productId": 1,
        "productName": "String",
        "quantity": 2,
        "price": 9.99
      },
      {
        "id": 1234,
        "productId": 3,
        "productName": "String",
        "quantity": 1,
        "price": 12.99
      }
      // ...
    ],
    "createdAt": "2025-01-20T12:34:56.000Z",
    "updatedAt": "2025-01-21T09:15:30.000Z"
  }
  ```

  | Method | Route                           | Description                     | Auth | Permission | Request                 | Response                               |
  | ------ | ------------------------------- | ------------------------------- | ---- | ---------- | ----------------------- | -------------------------------------- |
  | GET    | `/order/me?page=&limit=`        | Get paginated my orders         | ✅    | -          | -                       | `PaginatedResponse<OrderViewResponse>` |
  | GET    | `/order/me?page=&limit=&state=` | Filter my orders by status      | ✅    | -          | -                       | `PaginatedResponse<OrderViewResponse>` |
  | GET    | `/order/me/{id}`                | Get full details of one order   | ✅    | -          | -                       | `OrderResponse`                        |
  | POST   | `/order/me`                     | Create a new order              | ✅    | -          | `OrderRequest`          | `OrderResponse`                        |
  | PUT    | `/order/me/{id}`                | Replace entire order by ID      | ✅    | -          | `OrderRequest`          | `OrderResponse`                        |
  | PATCH  | `/order/me/{id}`                | Update some fields of the order | ✅    | -          | `Partial<OrderRequest>` | `OrderResponse`                        |
  | DELETE | `/order/me/{id}`                | Cancel/delete order by ID       | ✅    | -          | -                       | `Message`                              |


- 💻 Order Managment

  StateRequest:
  ```json 
  {
    "state": 2
  }
  ```

  OrderViewResponse (Without Items):
  ```json
  {
    "id": 123,
    "state": 2,
    "userId": 7,
    "totalPrice": 9.99,
    "createdAt": "2025-01-20T12:34:56.000Z",
    "updatedAt": "2025-01-21T09:15:30.000Z"
  }
  ```

  OrderResponse (With Items):
  ```json
  {
    "id": 123,
    "state": 2,
    "userId": 7,
    "totalPrice": 9.99,
    "items": [
      {
        "id": 123,
        "productId": 1,
        "productName": "String",
        "quantity": 2,
        "price": 9.99
      },
      {
        "id": 1234,
        "productId": 3,
        "productName": "String",
        "quantity": 1,
        "price": 12.99
      }
      // ...
    ],
    "createdAt": "2025-01-20T12:34:56.000Z",
    "updatedAt": "2025-01-21T09:15:30.000Z"
  }
  ```

  | Method | Route                        | Description                            | Auth | Permission    | Request        | Response                               |
  | ------ | ---------------------------- | -------------------------------------- | ---- | ------------- | -------------- | -------------------------------------- |
  | GET    | `/order?page=&limit=&state=` | Get all orders (optional state filter) | ✅    | `manageOrder` | -              | `PaginatedResponse<OrderViewResponse>` |
  | GET    | `/order/{id}`                | Get full order details by ID           | ✅    | `manageOrder` | -              | `OrderResponse`                        |
  | POST   | `/order/{id}/state`          | Move order to the **next** state       | ✅    | `manageOrder` and (`chef` or `delivery`) | -              | `OrderViewResponse`                    |
  | POST   | `/order/{id}/state`          | Set order to a **specific** state      | ✅    | `manageOrder` and (`chef` or `delivery`)` | `StateRequest` | `OrderViewResponse`                    |



- 💻 Authentication

  UserRequest:
  ```json
  {
    "username": "String",
    "fullname": "String",
    "email": "String",
    "password": "String"
  }
  ```

  UserResponse:
  ```json
  {
    "id": 123,
    "username": "String",
    "fullname": "String",
    "email": "String", 
    "createdAt": "2025-01-20T12:34:56.000Z",
    "updatedAt": "2025-01-21T09:15:30.000Z"
  }
  ```

  LoginRequest:
  ```json
  {
    "username": "String",
    "email": "String",
    "password": "String"
  }
  ```

  LoginResponse (Without Items):
  ```json
  {
    "jwt": "String"
  }
  ```

  | Method | Route                 | Description           | Auth | Permission | Request        | Response        |
  | ------ | --------------------- | --------------------- | ---- | ---------- | -------------- | --------------- |
  | POST   | `/user/auth/register` | Register new user     | ❌    | -          | `UserRequest`  | `UserResponse`  |
  | POST   | `/user/auth/login`    | Login and receive JWT | ❌    | -          | `LoginRequest` | `LoginResponse` |


- 💻 User

  UserRequest:
  ```json
  {
    "username": "String",
    "fullname": "String",
    "email": "String",
    "password": "String"
  }
  ```

  UserResponse:
  ```json
  {
    "id": 123,
    "username": "String",
    "fullname": "String",
    "email": "String", 
    "createdAt": "2025-01-20T12:34:56.000Z",
    "updatedAt": "2025-01-21T09:15:30.000Z"
  }
  ```

  | Method | Route      | Description         | Auth | Permission | Request       | Response       |
  | ------ | ---------- | ------------------- | ---- | ---------- | ------------- | -------------- |
  | GET    | `/user/me` | Get my profile info | ✅    | -          | -             | `UserResponse` |
  | PATCH  | `/user/me` | Update my profile   | ✅    | -          | `UserRequest` | `UserResponse` |
  | DELETE | `/user/me` | Delete my account   | ✅    | -          | -             | `Message`      |

- 💻 User Managment

  UserManagmentRequest:
  ```json
  {
    "password": "String",
    "role": 2
  }
  ```

  UserResponse:
  ```json
  {
    "id": 123,
    "username": "String",
    "fullname": "String",
    "email": "String", 
    "createdAt": "2025-01-20T12:34:56.000Z",
    "updatedAt": "2025-01-21T09:15:30.000Z"
  }
  ```

  | Method | Route                | Description                      | Auth | Permission   | Request                | Response                          |
  | ------ | -------------------- | -------------------------------- | ---- | ------------ | ---------------------- | --------------------------------- |
  | GET    | `/user?page=&limit=` | Get paginated users | ✅    | `manageUser` | -                      | `PaginatedResponse<UserResponse>` |
  | GET    | `/user/{id}`         | Get user by ID                   | ✅    | `manageUser` | -                      | `UserResponse`                    |
  | PATCH  | `/user/{id}`         | Update user role or password     | ✅    | `manageUser` and (`editPassword` or `promoteAll`)  | `UserManagmentRequest` | `UserResponse`                    |


- 💻 Role

  RoleRequest:
  ```json
  {
    "name": "String",
    "default": true,
    "chef": false,
    "delivery": false,
    "editPassword": false,
    "manageOrder": false,
    "manageProduct": false,
    "manageRole": false,
    "manageUser": false,
    "promoteAll": false,
  }
  ```

  RoleResponse:
  ```json
  {
    "id": 123,
    "name": "String",
    "default": true,
    "chef": false,
    "delivery": false,
    "editPassword": false,
    "manageOrder": false,
    "manageProduct": false,
    "manageRole": false,
    "manageUser": false,
    "promoteAll": false,
  }
  ```

  | Method | Route        | Description             | Auth | Permission   | Request       | Response         |
  | ------ | ------------ | ----------------------- | ---- | ------------ | ------------- | ---------------- |
  | GET    | `/role`      | Get all roles           | ❌    | -            | -             | `RoleResponse[]` |
  | GET    | `/role/{id}` | Get role by ID          | ❌    | -            | -             | `RoleResponse`   |
  | POST   | `/role`      | Create a new role       | ✅    | `manageRole` | `RoleRequest` | `RoleResponse`   |
  | PUT    | `/role/{id}` | Replace a role by ID    | ✅    | `manageRole` | `RoleRequest` | `RoleResponse`   |
  | PATCH  | `/role/{id}` | Update fields of a role | ✅    | `manageRole` | `RoleRequest` | `RoleResponse`   |
  | DELETE | `/role/{id}` | Delete a role by ID     | ✅    | `manageRole` | -             | `Message`        |

- 💻 Image

  **Note**: The "/**" in the path allows accessing, uploading, replacing, patching, or deleting any image based on a dynamic path structure (e.g., /image/product/Hamburguer.jpg).

  | Method | Route       | Description                  | Auth | Permission | Request                | Response                        |
  | ------ | ----------- | ---------------------------- | ---- | ---------- | ---------------------- | ------------------------------- |
  | GET    | `/image/**` | Get image by dynamic path    | ❌    | -          | -                      | `byte[]`                       |
  | POST   | `/image/**` | Upload image (new)           | ✅    | -          | `MultipartFile (file)` | `Message` |
  | PUT    | `/image/**` | Replace image by path        | ✅    | -          | `MultipartFile (file)` | `Message` |
  | PATCH  | `/image/**` | Patch (partial update) image | ✅    | -          | `MultipartFile (file)` | `Message`  |
  | DELETE | `/image/**` | Delete image by path         | ✅    | -          | -                      | `Message`  |


## Author
Juan Pablo Roman (Juanpablord)