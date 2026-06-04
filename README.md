# API Patitas Conectadas 🐾

Backend de la red social para dueños de mascotas **Patitas Conectadas**. API REST construida con Spring Boot 3 y Java 21, con autenticación JWT, almacenamiento de imágenes en Cloudinary y base de datos PostgreSQL en Supabase. Desplegada en Render.

> **🔧 Demo API (Swagger)** → [api-patitasconectadas-docker.onrender.com/swagger-ui](https://api-patitasconectadas-docker.onrender.com/swagger-ui/index.html)
> Usuario de prueba: `usuario@usuario.com` · Contraseña: `usuario`

---

### 👋 Para recruiters

API REST desarrollada de forma autónoma como proyecto personal de portafolio, consumida por un [frontend en React + TypeScript](https://github.com/Fernandodg97/Front-Patitas-Conectadas-render.com).

**Stack:** Java 21 · Spring Boot 3 · Spring Security · JWT · PostgreSQL · Cloudinary · Docker · Supabase

**Destacado:**
- 🔐 **Autenticación JWT** con Spring Security y BCrypt para contraseñas
- ☁️ **Almacenamiento persistente de imágenes** en Cloudinary (posts, mascotas, perfiles, comentarios)
- 🐳 **Dockerizado** con build multi-stage y despliegue listo para producción
- 🗄️ **Base de datos en la nube** con PostgreSQL en Supabase + SSL
- 📄 **Swagger/OpenAPI** con documentación interactiva de todos los endpoints
- 📡 **14 controladores REST** cubriendo autenticación, posts, chat, grupos, eventos, mascotas y más

### 🔑 Acceso rápido a la demo

| | |
|---|---|
| **Swagger UI** | [api-patitasconectadas-docker.onrender.com/swagger-ui](https://api-patitasconectadas-docker.onrender.com/swagger-ui/index.html) |
| **Frontend** | [front-patitas-conectadas-render-com.onrender.com](https://front-patitas-conectadas-render-com.onrender.com) |
| **Usuario de prueba** | `usuario@usuario.com` |
| **Contraseña** | `usuario` |

> Usa `POST /auth/login` con esas credenciales para obtener el token JWT, luego pulsa **Authorize** en Swagger e introduce `Bearer <token>` para probar el resto de endpoints.

---

## Características

- **🔒 Autenticación JWT** — Registro, login y rutas protegidas con Spring Security
- **🏠 Posts** — Publicaciones con imagen, filtrado por contenido y rango de fechas
- **💬 Comentarios** — Comentarios en posts con imagen opcional
- **👥 Red Social** — Seguir usuarios, valoraciones con puntuación (1-5 estrellas)
- **🐾 Mascotas** — Perfiles de mascotas con foto, especie, género y fecha de nacimiento
- **📅 Eventos** — Crear eventos con ubicación, fecha y sistema de asistencia (CREADOR / ASISTENTE)
- **👥 Grupos** — Comunidades con roles (ADMINISTRADOR / MIEMBRO) y posts propios
- **💬 Chat** — Mensajería directa con estado visto/no visto
- **🔔 Notificaciones** — Sistema de notificaciones por usuario
- **🖼️ Imágenes** — Subida y eliminación automática en Cloudinary para posts, mascotas, perfiles y comentarios

---

## Stack Tecnológico

| Categoría | Tecnología |
|---|---|
| Lenguaje | Java 21 |
| Framework | Spring Boot 3.4.4 |
| Seguridad | Spring Security + JWT (jjwt 0.9.1) |
| ORM | Spring Data JPA + Hibernate |
| Base de datos | PostgreSQL (Supabase) |
| Almacenamiento | Cloudinary |
| Documentación | SpringDoc OpenAPI (Swagger) |
| Contenedores | Docker (multi-stage, eclipse-temurin:21) |
| Pool de conexiones | HikariCP |

**Frontend asociado:** React 19 · TypeScript · Vite 6 · Tailwind CSS · React Router v6 · Axios

---

## 📡 Endpoints principales

### Auth
| Método | Ruta | Descripción |
|---|---|---|
| POST | `/auth/register` | Registro de usuario |
| POST | `/auth/login` | Login — devuelve JWT |
| GET | `/auth/me` | Usuario autenticado con perfil y mascotas |

### Usuarios y perfiles
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/usuarios` | Listar usuarios |
| GET | `/usuarios/buscar` | Buscar por nombre y/o apellido |
| GET | `/usuarios/{id}` | Obtener usuario por ID |
| PUT | `/usuarios/{id}` | Actualizar usuario |
| PATCH | `/usuarios/{id}` | Actualización parcial |
| PATCH | `/usuarios/{id}/password` | Cambiar contraseña |
| DELETE | `/usuarios/{id}` | Eliminar usuario |
| GET | `/usuarios/{id}/perfiles` | Obtener perfil |
| PUT | `/usuarios/{id}/perfiles` | Actualizar perfil (multipart) |

### Mascotas
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/usuarios/{id}/mascotas` | Listar mascotas del usuario |
| POST | `/usuarios/{id}/mascotas` | Añadir mascota con foto |
| PUT | `/usuarios/{id}/mascotas/{mascotaId}` | Actualizar mascota |
| DELETE | `/usuarios/{id}/mascotas/{mascotaId}` | Eliminar mascota |

### Posts y comentarios
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/posts` | Listar posts (filtros: contenido, fechaInicio, fechaFin) |
| POST | `/posts` | Crear post con imagen (multipart) |
| PUT | `/posts/{id}` | Actualizar post |
| DELETE | `/posts/{id}` | Eliminar post |
| GET | `/usuarios/{id}/posts` | Posts de un usuario |
| GET | `/grupos/{id}/posts` | Posts de un grupo |
| GET | `/posts/{id}/comentarios` | Comentarios de un post |
| POST | `/posts/{id}/comentarios` | Añadir comentario |
| DELETE | `/comentarios/{id}` | Eliminar comentario |

### Social
| Método | Ruta | Descripción |
|---|---|---|
| POST | `/chat/enviar` | Enviar mensaje directo |
| GET | `/chat/conversacion/{u1}/{u2}` | Conversación entre dos usuarios |
| PUT | `/chat/marcar-vistos/{u1}/{u2}` | Marcar mensajes como leídos |
| GET | `/chat/no-vistos/{id}` | Mensajes no leídos |
| GET | `/usuarios/{id}/seguidos` | Usuarios que sigue |
| POST | `/usuarios/{id}/seguidos/{seguidoId}` | Seguir usuario |
| DELETE | `/usuarios/{id}/seguidos/{seguidoId}` | Dejar de seguir |
| POST | `/valoraciones/usuarios/{autorId}/receptor/{receptorId}` | Valorar usuario |
| GET | `/valoraciones/usuarios/{id}/recibidas` | Valoraciones recibidas |

### Grupos y eventos
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/grupos` | Listar grupos |
| POST | `/grupos?usuarioId={id}` | Crear grupo (asigna al usuario como Admin) |
| PUT | `/grupos/{id}` | Actualizar grupo |
| DELETE | `/grupos/{id}` | Eliminar grupo |
| GET | `/eventos` | Listar eventos |
| POST | `/eventos?usuarioId={id}` | Crear evento (asigna al usuario como CREADOR) |
| PUT | `/eventos/{id}` | Actualizar evento |
| DELETE | `/eventos/{id}` | Eliminar evento |

> Documentación interactiva completa en [Swagger UI](https://api-patitasconectadas-docker.onrender.com/swagger-ui/index.html).
> Para ejemplos JSON detallados consulta [API-DOCS.md](API-DOCS.md). Para información sobre imágenes consulta [FileStorage.md](FileStorage.md).

---

## 🗄️ Modelo de datos

14 entidades principales:

- **usuario** — cuenta de usuario (email, password BCrypt)
- **perfil** — perfil público (descripción, fecha nacimiento, imagen Cloudinary)
- **mascota** — mascotas del usuario (especie, género, foto, fecha nacimiento)
- **post** — publicaciones con imagen y timestamp
- **comentario** — comentarios sobre posts con imagen opcional
- **chat** — mensajes directos entre usuarios (con estado visto/no visto)
- **evento** — eventos con ubicación y fecha
- **grupo** — comunidades temáticas
- **seguido** — relación seguidor/seguido
- **valoracion** — puntuaciones entre usuarios (1-5 + comentario)
- **notificaciones** — sistema de notificaciones
- **usuario_evento** — asistencia a eventos (CREADOR / ASISTENTE)
- **usuario_grupo** — membresía en grupos (ADMINISTRADOR / MIEMBRO)
- **usuario_post** — interacciones con posts

---

## 🔐 Autenticación

1. `POST /auth/register` — Registro de nuevo usuario
2. `POST /auth/login` — Devuelve un **token JWT**
3. Incluir el token en cada petición protegida:

```
Authorization: Bearer <token>
```

4. `GET /auth/me` — Devuelve el usuario autenticado con su perfil y mascotas

---

## 🐳 Instalación con Docker

```bash
git clone https://github.com/Fernandodg97/API-PatitasConectadas-Docker
cd API-PatitasConectadas-Docker
```

Crear un archivo `.env` con las variables necesarias:

```env
DATABASE_URL=jdbc:postgresql://<host>/<db>?sslmode=require
DATABASE_USERNAME=tu_usuario
DATABASE_PASSWORD=tu_password
CLOUDINARY_CLOUD_NAME=tu_cloud_name
CLOUDINARY_API_KEY=tu_api_key
CLOUDINARY_API_SECRET=tu_api_secret
```

```bash
docker build -t api-patitas .
docker run -p 8080:8080 --env-file .env api-patitas
```

La API queda disponible en `http://localhost:8080`

---

## ⚙️ Instalación sin Docker

Requisitos: **Java 21** y **Maven**

```bash
./mvnw spring-boot:run
```

O compilar y ejecutar el JAR:

```bash
./mvnw clean package
java -jar target/ApiPatitasConectadas-*.jar
```

---

## 📂 Estructura del proyecto

```
src/main/java/net/xeill/elpuig/apipatitasconectadas/
├── controllers/rest/     # 14 controladores REST
├── controllers/dto/      # 33 DTOs de entrada/salida
├── models/               # 14 entidades JPA
├── repositories/         # Repositorios Spring Data
├── services/             # Lógica de negocio
├── config/               # CORS y configuración MVC
└── security/             # JWT + Spring Security
```

---

## Repositorios del Proyecto

| Repositorio | Descripción |
|---|---|
| [API-PatitasConectadas-Docker](https://github.com/Fernandodg97/API-PatitasConectadas-Docker) | Este repositorio — Spring Boot 3 + Docker |
| [Front-Patitas-Conectadas-render.com](https://github.com/Fernandodg97/Front-Patitas-Conectadas-render.com) | Frontend — React 19 + TypeScript |

---

## Licencia

[CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/deed.es)

---

<p align="center">Made with ❤️ for animals everywhere</p>
