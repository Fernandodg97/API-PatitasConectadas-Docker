# API Patitas Conectadas 🐾

[![Java](https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot_3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)

Backend de la red social para dueños de mascotas **Patitas Conectadas**. API REST desarrollada con Java 21 y Spring Boot 3, con autenticación JWT, almacenamiento de imágenes en Cloudinary y base de datos PostgreSQL en Supabase. Dockerizada y desplegada en producción en Render.

> **🔧 Demo API (Swagger)** → [api-patitasconectadas-docker.onrender.com/swagger-ui](https://api-patitasconectadas-docker.onrender.com/swagger-ui/index.html)
> Usuario de prueba: `usuario@usuario.com` · Contraseña: `usuario` · ⏱️ Los servicios pueden tardar ~30s en arrancar (plan gratuito de Render).

---

## 👋 Para recruiters

Backend completo de una red social para dueños de mascotas, construido de cero como Proyecto Final de Grado (DAW) y llevado a producción de forma autónoma tras la entrega.

**¿Qué demuestra este proyecto?**

- ✅ Diseñar e implementar una **API REST completa** con 14 controladores, 33 DTOs y seguridad JWT desde cero
- ✅ Aplicar **arquitectura en capas** real: controladores, servicios, repositorios y modelos correctamente separados
- ✅ Gestionar **relaciones complejas** entre entidades con JPA: seguidos, grupos, eventos, interacciones y mascotas
- ✅ Integrar servicios externos: **Cloudinary** para imágenes, **Supabase** para base de datos en la nube
- ✅ **Dockerizar** la aplicación con build multi-stage y llevarla a producción en Render

| | |
|---|---|
| 🔧 **API (Swagger)** | [api-patitasconectadas-docker.onrender.com/swagger-ui](https://api-patitasconectadas-docker.onrender.com/swagger-ui/index.html) |
| 🌐 **Frontend** | [front-patitas-conectadas-render-com.onrender.com](https://front-patitas-conectadas-render-com.onrender.com) |
| 💻 **Repo frontend** | [Front-Patitas-Conectadas-render.com](https://github.com/Fernandodg97/Front-Patitas-Conectadas-render.com) |
| 📁 **Proyecto completo (TFG)** | [Patitas-Conectadas](https://github.com/Fernandodg97/Patitas-Conectadas) |

---

## 🚀 Mejoras Post-Práctica

El proyecto original funcionaba en local. Tras la defensa del TFG, se retomó de forma autónoma para llevarlo a producción real:

| Mejora | Detalle |
|---|---|
| 🐳 **Dockerización** | `Dockerfile` con build multi-stage: Maven compila en la primera etapa y solo el `.jar` se copia sobre una imagen JRE ligera |
| ☁️ **Despliegue en producción** | Backend desplegado como contenedor Docker en Render, accesible públicamente |
| 🖼️ **Cloudinary** | Sustitución del almacenamiento local por Cloudinary. Subida automática y eliminación de imágenes al actualizar o borrar |
| 🗄️ **Supabase** | Migración de PostgreSQL local a Supabase con conexión SSL. Sin necesidad de instancia local para arrancar |

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

## Funcionalidades implementadas

| Módulo | Descripción |
|---|---|
| **Auth** | Registro, login y rutas protegidas con Spring Security + JWT |
| **Usuarios** | CRUD completo, búsqueda por nombre/apellido y cambio de contraseña |
| **Perfiles** | Foto, bio y fecha de nacimiento por usuario |
| **Mascotas** | Registro con foto, especie, género y fecha de nacimiento |
| **Posts** | Publicaciones con imagen, filtrado por contenido y rango de fechas |
| **Comentarios** | Comentarios en posts con imagen opcional |
| **Chat** | Mensajería directa con estado visto/no visto |
| **Grupos** | Comunidades con roles ADMINISTRADOR / MIEMBRO y feed propio |
| **Eventos** | Crear y apuntarse a eventos con ubicación y fecha (CREADOR / ASISTENTE) |
| **Seguidos** | Sistema de seguimiento entre usuarios |
| **Valoraciones** | Puntuaciones 1–5 ⭐ con comentario entre usuarios |
| **Notificaciones** | Centro de notificaciones por usuario |

---

## Retos técnicos resueltos

**Autenticación y seguridad** — JWT firmado con jjwt, filtros de Spring Security en cada request y BCrypt para contraseñas. Todos los endpoints de escritura requieren autenticación.

**Imágenes en la nube** — Subida `multipart/form-data` al backend, que delega en Cloudinary y devuelve la URL pública. Eliminación automática de la imagen anterior al actualizar o borrar un recurso.

**Relaciones complejas con JPA** — Entidades con `@OneToMany`, `@ManyToOne` y `@ManyToMany`. Tablas de unión explícitas (`usuario_grupo`, `usuario_evento`, `usuario_post`) con roles propios.

**DTOs para desacoplar capas** — 33 DTOs de entrada y salida que evitan exponer las entidades JPA directamente y permiten controlar exactamente qué datos se serializan en cada respuesta.

**Despliegue como contenedor** — Build multi-stage en Docker: la imagen de producción solo contiene el `.jar` sobre una JRE ligera, sin el toolchain de Maven.

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
| POST | `/grupos?usuarioId={id}` | Crear grupo (usuario queda como ADMINISTRADOR) |
| PUT | `/grupos/{id}` | Actualizar grupo |
| DELETE | `/grupos/{id}` | Eliminar grupo |
| GET | `/eventos` | Listar eventos |
| POST | `/eventos?usuarioId={id}` | Crear evento (usuario queda como CREADOR) |
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
| [Patitas-Conectadas](https://github.com/Fernandodg97/Patitas-Conectadas) | Proyecto completo — TFG con memoria y presentación |

---

## Licencia

[CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/deed.es)

---

<p align="center">Made with ❤️ for animals everywhere</p>
