# 🛒 Caprish - Plataforma de Comercio Electrónico

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-3.8+-red.svg)](https://maven.apache.org/)

## 📋 Descripción

**Caprish** es una plataforma completa de comercio electrónico desarrollada con Spring Boot que proporciona una solución robusta para la gestión de tiendas online. La aplicación incluye funcionalidades avanzadas como integración con Gmail API, sistema de autenticación JWT, gestión de productos, carritos de compra, mensajería en tiempo real.
## ✨ Características Principales

### 🔐 Autenticación y Seguridad
- **Autenticación JWT**: Sistema seguro de autenticación con tokens JWT
- **Spring Security**: Configuración robusta de seguridad
- **Gestión de roles**: Diferentes tipos de usuarios (Clientes, Staff)

### 🛍️ Gestión de Comercio Electrónico
- **Catálogo de productos**: Gestión completa de productos con imágenes
- **Carritos de compra**: Sistema de carritos con diferentes estados
- **Gestión de ventas**: Control de transacciones y pedidos
- **Categorización**: Organización de productos por categorías

### 💬 Sistema de Mensajería
- **Chat en tiempo real**: Comunicación entre clientes y vendedores
- **Integración Gmail**: Envio de token automatizado por GMAIL.


### 📊 Reportes y Documentación
- **API Documentation**: Documentación con OpenAPI/Swagger


### 🖼️ Gestión de Archivos
- **Subida de imágenes**: Sistema de gestión de imágenes de productos
- **Almacenamiento local**: Gestión eficiente de archivos

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java 21**: Lenguaje de programación principal
- **Spring Boot 3.5.0**: Framework de desarrollo
- **Spring Security**: Seguridad y autenticación
- **Spring Data JPA**: Persistencia de datos
- **Spring Web**: API REST
- **Spring Scheduling**: Tareas programadas

### Base de Datos
- **MySQL 8.0**: Base de datos relacional

### Autenticación y API
- **JWT (JSON Web Tokens)**: Autenticación stateless
- **Gmail API**: Integración con servicios de Google
- **OAuth 2.0**: Autenticación con Google

### Documentación y Testing
- **SpringDoc OpenAPI**: Documentación automática de API

### Utilidades
- **Thymeleaf**: Motor de plantillas
- **Lombok**: Reducción de código boilerplate

## 📦 Instalación y Configuración

### Prerrequisitos
- Java 21 o superior
- MySQL 8.0 o superior
- Maven 3.8+
- Cuenta de Google Cloud Platform (para Gmail API)

### 1. Clonar el Repositorio
```bash
git clone <url-del-repositorio>
cd Caprish
```

### 2. Configurar Base de Datos
1. Crear una base de datos MySQL llamada `CaprishDB`
2. Configurar las credenciales en `src/main/resources/application.properties`:
```properties
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

### 3. Configurar Gmail API
1. Crear un proyecto en [Google Cloud Console](https://console.cloud.google.com/)
2. Habilitar Gmail API
3. Crear credenciales OAuth 2.0
4. Descargar el archivo de credenciales y colocarlo en `src/main/resources/credentials/credentials.json`
5. Configurar la API key en `application.properties`:
```properties
google.api.key=tu_api_key
```

### 4. Compilar y Ejecutar
```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar la aplicación
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## 🚀 Uso

### Acceso a la Documentación de la API
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`


## 📁 Estructura del Proyecto

```
Caprish/
├── src/
│   ├── main/
│   │   ├── java/Caprish/
│   │   │   ├── Controllers/          # Controladores REST
│   │   │   ├── Service/              # Lógica de negocio
│   │   │   ├── Repository/           # Acceso a datos
│   │   │   ├── Model/                # Entidades y DTOs
│   │   │   ├── config/               # Configuraciones
│   │   │   ├── Exception/            # Manejo de excepciones
│   │   │   └── Scheduler/            # Tareas programadas
│   │   └── resources/
│   │       ├── application.properties # Configuración
│   │       ├── credentials/          # Credenciales Gmail API
│   │       └── templates/            # Plantillas Thymeleaf
│   └── test/                         
├── uploads/                          # Archivos subidos
├── pom.xml                           # Dependencias Maven
└── README.md                         # Este archivo
```

## 🤝 Contribución

### Flujo de Trabajo
1. **Rama Principal (main)**: Versión de producción estable
2. **Rama Develop**: Desarrollo activo
3. **Ramas Feature**: Nuevas funcionalidades

### Reglas de Contribución
- ✅ **ANTES** de hacer commit a la rama principal, discutir cambios con el equipo
- ✅ Verificar 10 veces la rama develop antes de mergear
- ✅ La rama principal debe funcionar en todo momento
- ✅ Nunca romper la funcionalidad principal!

### Proceso de Desarrollo
1. Crear rama hija desde `develop`
2. Desarrollar funcionalidad
3. Revisión de código
4. Merge a `develop`
5. Testing exhaustivo
6. Merge a `main` (solo cuando esté completamente probado)


## 👥 Equipo

- **Desarrolladores**: Erbin Ignacio, Oyhamburu Matias, Ocampo Emilia, Paez Juan Francisco, Páez Pastrello Ignasi Joan.
- **Contacto**: caprishcommerce@gmail.com
