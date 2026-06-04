# API Patitas Conectadas

---

### 👋 Para recruiters

API REST para una **red social de dueños de mascotas**, desarrollada de forma autónoma como proyecto personal de portafolio.

**Stack:** Java 21 · Spring Boot 3 · Spring Security · JWT · PostgreSQL · Cloudinary · Docker · Supabase

**Destacado:**
- 🔐 **Autenticación JWT** con Spring Security y BCrypt para contraseñas
- ☁️ **Almacenamiento persistente de imágenes** en Cloudinary (posts, mascotas, perfiles)
- 🐳 **Dockerizado** con build multi-stage y despliegue listo para producción
- 🗄️ **Base de datos en la nube** con PostgreSQL en Supabase + SSL
- 📄 **Swagger/OpenAPI** disponible en `/swagger-ui/index.html#/`
- 📡 **14 controladores REST** cubriendo autenticación, posts, chat, grupos, eventos, mascotas y más

---

## 🧠 ¿Qué es Patitas Conectadas?

Una plataforma social pensada para conectar a dueños de mascotas. Los usuarios pueden:

- Crear un perfil personal con foto, descripción y sus mascotas
- Publicar posts con imágenes y comentarlos
- Chatear directamente con otros usuarios
- Crear y unirse a grupos temáticos (paseos, razas, etc.)
- Organizar y apuntarse a eventos
- Seguir a otros usuarios y valorarlos con puntuación de 1 a 5 estrellas

---

## 🚀 Tecnologías

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

---

## 📡 Endpoints principales

### Auth
| Método | Ruta | Descripción |
|---|---|---|
| POST | `/auth/register` | Registro de usuario |
| POST | `/auth/login` | Login — devuelve JWT |
| GET | `/auth/me` | Usuario autenticado actual con perfil y mascotas |

### Usuarios y perfiles
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/usuarios` | Listar usuarios |
| GET | `/usuarios/{id}` | Obtener usuario por ID |
| PUT | `/usuarios/{id}` | Actualizar usuario |
| DELETE | `/usuarios/{id}` | Eliminar usuario |
| GET | `/perfiles/{id}` | Obtener perfil con imagen |
| PUT | `/perfiles/{id}` | Actualizar perfil (multipart) |

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
| GET | `/posts` | Listar todos los posts |
| POST | `/posts` | Crear post con imagen (multipart) |
| PUT | `/posts/{id}` | Actualizar post |
| DELETE | `/posts/{id}` | Eliminar post |
| GET | `/comentarios/post/{postId}` | Comentarios de un post |
| POST | `/comentarios` | Añadir comentario con imagen |

### Social
| Método | Ruta | Descripción |
|---|---|---|
| POST | `/chat/enviar` | Enviar mensaje directo |
| GET | `/chat/conversacion` | Obtener conversación entre dos usuarios |
| PATCH | `/chat/marcar-leido` | Marcar mensajes como leídos |
| POST | `/seguido` | Seguir a un usuario |
| DELETE | `/seguido` | Dejar de seguir |
| POST | `/valoracion` | Valorar a un usuario (1-5 estrellas) |

### Grupos y eventos
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/grupos` | Listar grupos |
| POST | `/grupos` | Crear grupo |
| GET | `/grupos/{id}/miembros` | Miembros de un grupo |
| GET | `/eventos` | Listar eventos |
| POST | `/eventos` | Crear evento |

> Documentación interactiva completa en `/swagger-ui/index.html#/`

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
- **usuario_grupo** — membresía en grupos (Admin / Miembro)
- **usuario_post** — interacciones con posts

---

## 🔐 Autenticación

La API usa JWT. Para acceder a los endpoints protegidos:

1. `POST /auth/login` con `email` y `password`
2. Copiar el token de la respuesta
3. Incluirlo en el header de las siguientes peticiones:

```
Authorization: Bearer <token>
```

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

## Autores

- [@Fernandodg97](https://github.com/Fernandodg97)

## Licencia

[CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/deed.es)
