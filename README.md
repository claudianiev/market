# 🧩 Market

Market es una aplicación backend desarrollada en Spring Boot que simula un sistema de gestión para un mercado en línea. Permite la administración de productos e inventarios, facilitando operaciones CRUD básicas. Este proyecto resuelve el problema de gestionar inventarios y transacciones en un entorno e-commerce simple, sirviendo como base para aplicaciones más complejas de comercio electrónico. Está diseñado para ser escalable, modular y fácil de extender.

## 🚀 Tecnologías y dependencias principales

- **Java 17**: Lenguaje de programación principal.
- **Spring Boot 3.x**: Framework para el desarrollo de aplicaciones web y microservicios.
- **Maven**: Herramienta de gestión de dependencias y construcción del proyecto.
- **Spring Data JPA**: Para el acceso a datos y mapeo objeto-relacional.
- **H2 Database**: Base de datos en memoria para desarrollo y pruebas.
- **Lombok**: Librería para reducir código boilerplate en clases Java.
- **Spring Boot Starter Web**: Para crear APIs RESTful.
- **Spring Boot Starter Validation**: Para validación de datos de entrada.
- **JUnit 5 y Mockito**: Para pruebas unitarias e integración.

## 🏗️ Arquitectura y patrones aplicados

El proyecto Market implementa una arquitectura en capas (Layered Architecture) inspirada en la Arquitectura Limpia (Clean Architecture) de Robert C. Martin, con el objetivo de separar responsabilidades, promover la mantenibilidad y facilitar las pruebas. A continuación, detallo los componentes principales de arquitectura implementados, organizados por capas y sus roles específicos. Esta estructura permite que el código sea modular, escalable y alineado con principios SOLID (como el Principio de Responsabilidad Única - SRP y el Principio de Inversión de Dependencias - DIP).

### Capas principales y componentes

#### 1. **Capa de Presentación (Presentation Layer)**
   - **Componentes**: Controladores (Controllers) y DTOs (Data Transfer Objects).
   - **Descripción**: Esta capa maneja las interacciones con el cliente (e.g., APIs REST). Los controladores exponen endpoints HTTP y convierten requests en objetos de dominio, delegando la lógica a la capa de negocio. Los DTOs se usan para transferir datos entre el cliente y el servidor sin exponer entidades internas, evitando acoplamiento y mejorando la seguridad.
   - **Ejemplo**: `ProductController` recibe requests GET/POST para productos, valida entradas con `@Valid` y retorna DTOs como `ProductDTO`. Esto aplica el patrón DTO para encapsular datos y el patrón Controller para manejar rutas.
   - **Beneficios**: Separa la lógica de UI/API de la lógica de negocio, facilitando cambios en interfaces sin afectar el core.

#### 2. **Capa de Negocio o Servicio (Business/Service Layer)**
   - **Componentes**: Servicios (Services) y casos de uso.
   - **Descripción**: Contiene la lógica de negocio pura, orquestando operaciones sin depender de detalles de infraestructura. Los servicios implementan reglas de negocio, validaciones y transformaciones de datos. Se inyectan dependencias (e.g., repositorios) vía Spring IoC.
   - **Ejemplo**: `ProductService` maneja la creación de productos, aplicando validaciones (e.g., precio positivo) y llamando a `ProductRepository`. Usa el patrón Service Layer para centralizar lógica y el patrón Strategy para diferentes estrategias de cálculo (e.g., descuentos).
   - **Beneficios**: Promueve el SRP al aislar lógica de negocio, y facilita pruebas unitarias mockeando dependencias.

#### 3. **Capa de Datos (Data Layer)**
   - **Componentes**: Repositorios (Repositories) y entidades (Entities).
   - **Descripción**: Abstrae el acceso a datos, permitiendo cambios en la base de datos sin afectar otras capas. Los repositorios extienden `JpaRepository` de Spring Data JPA para operaciones CRUD. Las entidades representan tablas de la base de datos con anotaciones JPA (@Entity, @Table).
   - **Ejemplo**: `ProductRepository` define métodos como `findByCategoryId(Long id)` para consultas personalizadas. `Product` es una entidad con campos mapeados a columnas. Aplica el patrón Repository para encapsular queries y el patrón DAO (Data Access Object) implícitamente.
   - **Beneficios**: Separa el dominio de la persistencia, alineándose con DIP al depender de interfaces.

#### 4. **Capa de Dominio (Domain Layer)**
   - **Componentes**: Entidades, value objects y reglas de negocio.
   - **Descripción**: Representa el núcleo del negocio, libre de dependencias externas. Incluye modelos puros (sin anotaciones de framework) y lógica invariante.
   - **Ejemplo**: La entidad `Product` podría tener métodos como `isAvailable()` para verificar stock, aplicando lógica de dominio. Usa el patrón Entity para modelar objetos de negocio.
   - **Beneficios**: Hace el código independiente de frameworks, facilitando migraciones y reutilización.

#### 5. **Capa de Infraestructura y Configuración (Infrastructure Layer)**
   - **Componentes**: Configuraciones (Config), excepciones personalizadas (Exceptions) y utilidades.
   - **Descripción**: Maneja detalles técnicos como conexiones a BD, seguridad y logging. Incluye clases de configuración con `@Configuration` para beans personalizados.
   - **Ejemplo**: `SecurityConfig` configura autenticación básica; `GlobalExceptionHandler` maneja errores con `@ControllerAdvice`, retornando respuestas HTTP apropiadas. Aplica el patrón Adapter para integrar frameworks externos.
   - **Beneficios**: Centraliza configuraciones, facilitando despliegues en diferentes entornos.

### Patrones de diseño aplicados
- **Repository Pattern**: Abstrae el acceso a datos, permitiendo switches entre BD (e.g., H2 a PostgreSQL).
- **Service Layer Pattern**: Encapsula lógica de negocio, promoviendo reutilización.
- **DTO Pattern**: Transfiere datos de forma segura y eficiente.
- **Builder Pattern**: Generado por Lombok para construir objetos complejos sin setters largos.
- **Singleton**: Implícitamente en beans Spring (e.g., servicios como singletons).
- **Factory**: Para crear instancias de servicios o repositorios vía `@Bean`.

### Principios SOLID y buenas prácticas
- **SRP**: Cada clase tiene una responsabilidad única (e.g., un controller solo maneja requests).
- **OCP**: Extensiones (e.g., nuevos endpoints) sin modificar código existente.
- **LSP**: Interfaces como `ProductRepository` permiten substituciones.
- **ISP**: Interfaces pequeñas y específicas.
- **DIP**: Dependencias inyectadas (e.g., services dependen de interfaces, no implementaciones).
- **Arquitectura limpia**: El dominio no depende de frameworks; capas externas apuntan hacia el centro.

Esta arquitectura hace que Market sea robusto, testeable y preparado para crecer (e.g., agregar microservicios). Si necesitas diagramas o ejemplos de código específicos, ¡házmelo saber!

## ⚙️ Instalación y ejecución

### Prerrequisitos
- Java 17 o superior instalado.
- Maven 3.x instalado.
- Git para clonar el repositorio.
