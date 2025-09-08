# Documentación Completa de la API Patitas Conectadas

Esta documentación contiene todas las rutas de la API con ejemplos JSON y explicaciones para cada controlador.

También puedes acceder a la documentación interactiva de la API en la ruta `/swagger-ui/index.html#/` donde podrás probar los endpoints directamente.

## Índice
1. [Introducción](#1-introducción)
2. [Autenticación (Auth)](#2-autenticación-auth)
3. [Usuarios](#3-usuarios)
4. [Perfiles](#4-perfiles)
5. [Posts](#5-posts)
6. [Comentarios](#6-comentarios)
7. [Chat](#7-chat)
8. [Eventos](#8-eventos)
9. [Grupos](#9-grupos)
10. [Mascotas](#10-mascotas)
11. [Notificaciones](#11-notificaciones)
12. [Seguidos](#12-seguidos)
13. [Valoraciones](#13-valoraciones)
14. [Relaciones](#14-relaciones)
    - [Usuario-Comentario](#141-usuario-comentario)
    - [Usuario-Evento](#142-usuario-evento)
    - [Usuario-Grupo](#143-usuario-grupo)
    - [Usuario-Post](#144-usuario-post)
    - [Usuario-Interacción](#145-usuario-interacción)

## 1. Introducción

La API de Patitas Conectadas proporciona una interfaz RESTful para gestionar una plataforma social centrada en mascotas. Esta API permite:

- Gestión de usuarios y autenticación
- Manejo de perfiles y publicaciones
- Interacción social (comentarios, chat, eventos)
- Gestión de mascotas y grupos
- Sistema de valoraciones y notificaciones

### Características principales:
- Autenticación mediante JWT
- Manejo de archivos multimedia
- Relaciones complejas entre entidades
- Sistema de notificaciones en tiempo real
- Gestión de permisos y roles

### Convenciones:
- Todas las respuestas están en formato JSON
- Los códigos de estado HTTP indican el resultado de la operación
- Las fechas se manejan en formato ISO 8601
- Los IDs son numéricos y autoincrementales

## 2. Autenticación (Auth)

### `POST /auth/login`
**Descripción:** Inicia sesión de usuario y devuelve un token JWT.

**Ejemplo Request:**
```json
{
  "email": "usuario@ejemplo.com",
  "password": "contraseña123"
}
```

**Ejemplo Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Notas:**
- Si las credenciales son incorrectas, se devolverá un error 401 Unauthorized
- El token debe ser incluido en el header Authorization para las siguientes peticiones

### `POST /auth/register`
**Descripción:** Registra un nuevo usuario en el sistema.

**Ejemplo Request:**
```json
{
  "email": "nuevo@ejemplo.com",
  "password": "contraseña123",
  "nombre": "Juan",
  "apellido": "Pérez"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "nuevo@ejemplo.com"
}
```

### `GET /auth/me`
**Descripción:** Obtiene información del usuario autenticado.

**Headers requeridos:**
- Authorization: Bearer {token}

**Ejemplo Response:**
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "nuevo@ejemplo.com",
  "perfil": {
    "id": 1,
    "usuarioId": 1,
    "descripcion": "Amante de los animales",
    "ubicacion": "Barcelona"
  },
  "mascotas": [
    {
      "id": 1,
      "usuarioId": 1,
      "nombre": "Toby",
      "genero": "Macho",
      "raza": "Labrador"
    }
  ]
}
```

## 3. Usuarios

### `GET /usuarios`
**Descripción:** Obtiene todos los usuarios registrados en el sistema.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "juan@ejemplo.com"
  },
  {
    "id": 2,
    "nombre": "María",
    "apellido": "García",
    "email": "maria@ejemplo.com"
  }
]
```

**Notas:**
- Si ocurre un error al obtener los usuarios, se devolverá un error 400 Bad Request
- La contraseña no se incluye en la respuesta por seguridad

### `GET /usuarios/buscar`
**Descripción:** Busca usuarios por nombre y/o apellido.

**Parámetros Query (opcionales):**
- nombre: Texto para buscar en el nombre del usuario
- apellido: Texto para buscar en el apellido del usuario

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "juan@ejemplo.com"
  },
  {
    "id": 3,
    "nombre": "Juan Carlos",
    "apellido": "Pérez García",
    "email": "juancarlos@ejemplo.com"
  }
]
```

**Notas:**
- La búsqueda es parcial (usando `Containing`), lo que significa que encontrará coincidencias parciales
- Si se proporcionan ambos parámetros, buscará usuarios que coincidan con ambos criterios
- Si solo se proporciona nombre, buscará solo por nombre
- Si solo se proporciona apellido, buscará solo por apellido
- Si no se proporciona ningún parámetro, devolverá una lista vacía
- La búsqueda es insensible a mayúsculas/minúsculas
- La contraseña no se incluye en la respuesta por seguridad
- Si ocurre un error en la búsqueda, se devolverá un error 400 Bad Request

### `POST /usuarios`
**Descripción:** Crea un nuevo usuario en el sistema.

**Ejemplo Request:**
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan@ejemplo.com",
  "password": "contraseña123"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan@ejemplo.com"
}
```

**Notas:**
- Todos los campos son obligatorios
- El nombre y apellido tienen un límite de 50 caracteres
- El email tiene un límite de 50 caracteres y debe ser único
- La contraseña tiene un límite de 250 caracteres
- La contraseña no se devuelve en la respuesta por seguridad
- Si ocurre un error al crear el usuario, se devolverá un error 500 Internal Server Error

### `GET /usuarios/{id}`
**Descripción:** Obtiene un usuario específico por su ID.

**Ejemplo Response:**
```json
  {
    "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan@ejemplo.com"
}
```

**Notas:**
- Si el usuario no existe, se devolverá un error 404 Not Found
- La contraseña no se incluye en la respuesta por seguridad
- Si ocurre un error al obtener el usuario, se devolverá un error 400 Bad Request

### `PUT /usuarios/{id}`
**Descripción:** Actualiza un usuario existente.

**Ejemplo Request:**
```json
{
  "nombre": "Juan Carlos",
  "apellido": "Pérez García",
  "email": "juancarlos@ejemplo.com",
  "password": "nuevaContraseña123"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "nombre": "Juan Carlos",
  "apellido": "Pérez García",
  "email": "juancarlos@ejemplo.com"
}
```

**Notas:**
- Todos los campos son obligatorios
- El nombre y apellido tienen un límite de 50 caracteres
- El email tiene un límite de 50 caracteres y debe ser único
- La contraseña tiene un límite de 250 caracteres
- La contraseña no se devuelve en la respuesta por seguridad
- Si el usuario no existe, se devolverá un error 404 Not Found
- Si ocurre un error al actualizar el usuario, se devolverá un error 500 Internal Server Error

### `DELETE /usuarios/{id}`
**Descripción:** Elimina un usuario existente.

**Ejemplo Response:**
```json
{
  "mensaje": "Usuario con ID: 1 eliminado correctamente"
}
```

**Notas:**
- Si el usuario no existe, se devolverá un error 404 Not Found
- Si ocurre un error al eliminar el usuario, se devolverá un error 400 Bad Request
- La eliminación de un usuario también eliminará todas sus relaciones con grupos y eventos

### `PATCH /usuarios/{id}`
**Descripción:** Actualiza parcialmente un usuario existente.

**Ejemplo Request:**
```json
{
  "nombre": "Juan Carlos",
  "apellido": "Pérez García",
  "email": "juancarlos@ejemplo.com"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "nombre": "Juan Carlos",
  "apellido": "Pérez García",
  "email": "juancarlos@ejemplo.com"
}
```

**Notas:**
- Solo se actualizan los campos proporcionados en la petición
- El email debe ser único si se está actualizando
- No se permite actualizar la contraseña a través de este endpoint
- Si el usuario no existe, se devolverá un error 404 Not Found
- Si el email ya está en uso, se devolverá un error 400 Bad Request

### `PATCH /usuarios/{id}/password`
**Descripción:** Actualiza la contraseña de un usuario.

**Ejemplo Request:**
```json
{
  "currentPassword": "contraseña_actual",
  "newPassword": "nueva_contraseña"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan@ejemplo.com"
}
```

**Notas:**
- Se requiere la contraseña actual para verificar la identidad del usuario
- La nueva contraseña se encripta automáticamente antes de guardarse
- Si la contraseña actual es incorrecta, se devolverá un error 400 Bad Request
- Si el usuario no existe, se devolverá un error 404 Not Found
- La contraseña no se devuelve en la respuesta por seguridad

### `POST /usuarios/restablecer-contrasena`
**Descripción:** Restablece la contraseña de un usuario.

**Ejemplo Request:**
```json
{
  "email": "usuario@ejemplo.com",
  "nuevaContrasena": "nueva_contraseña"
}
```

**Ejemplo Response:**
```json
{
  "mensaje": "Contraseña actualizada exitosamente"
}
```

**Notas:**
- El email y la nueva contraseña son obligatorios
- La nueva contraseña debe tener al menos 8 caracteres
- Si el email no existe, se devolverá un error 400 Bad Request
- Si la contraseña no cumple con los requisitos mínimos, se devolverá un error 400 Bad Request
- La nueva contraseña no puede ser igual a la contraseña actual
- Si ocurre un error interno, se devolverá un error 500 Internal Server Error

## 4. Perfiles

### `GET /perfiles`
**Descripción:** Obtiene todos los perfiles existentes en el sistema.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "usuario": {
      "id": 1,
      "nombre": "Juan",
      "apellido": "Pérez",
      "email": "juan@ejemplo.com"
    },
    "descripcion": "Amante de los animales",
    "fecha_nacimiento": "1990-01-01",
    "img": "https://ejemplo.com/imagen.jpg"
  }
]
```

**Notas:**
- Si ocurre un error al obtener los perfiles, se devolverá un error 500 Internal Server Error
- Si no hay perfiles, se devolverá una lista vacía

### `POST /perfiles`
**Descripción:** Crea un nuevo perfil en el sistema con imagen opcional.

**Content-Type:** multipart/form-data

**Parámetros:**
- descripcion: string (requerido)
- fechaNacimiento: string (requerido, formato: YYYY-MM-DD)
- usuarioId: number (requerido)
- imagen: file (opcional)

**Ejemplo Request (multipart/form-data):**
```
descripcion: "Amante de los animales"
fechaNacimiento: "1990-01-01"
usuarioId: 1
imagen: [archivo de imagen]
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "usuario_id": 1,
  "descripcion": "Amante de los animales",
  "fecha_nacimiento": "1990-01-01",
  "img": "/uploads/perfiles/2024/03/nombre-archivo.jpg"
}
```

**Notas:**
- El usuarioId es obligatorio y debe corresponder a un usuario existente
- La fechaNacimiento debe estar en formato YYYY-MM-DD
- Un usuario solo puede tener un perfil. Si intenta crear otro, se devolverá un error 400 Bad Request
- Para la imagen:
  - Tipos permitidos: jpg, jpeg, png, gif
  - Tamaño máximo: 10MB
  - Se almacena en `uploads/perfiles/YYYY/MM/`
  - Las URLs son accesibles vía `/uploads/perfiles/YYYY/MM/nombre-archivo`
- Si ocurre un error al crear el perfil, se devolverá un error 500 Internal Server Error
- La respuesta incluye el ID del perfil, el ID del usuario, la descripción, la fecha de nacimiento y la ruta de la imagen

### `GET /usuarios/{id}/perfiles`
**Descripción:** Obtiene el perfil asociado a un usuario específico.

**Ejemplo Response:**
```json
{
  "id": 1,
  "usuario_id": 1,
  "descripcion": "Amante de los animales",
  "fecha_nacimiento": "1990-01-01",
  "img": "/uploads/perfiles/2024/03/nombre-archivo.jpg"
}
```

**Notas:**
- Si el perfil no existe, se devolverá un error 404 Not Found
- Si ocurre un error al obtener el perfil, se devolverá un error 500 Internal Server Error
- La respuesta incluye el ID del perfil, el ID del usuario, la descripción, la fecha de nacimiento y la ruta de la imagen

### `PUT /usuarios/{id}/perfiles`
**Descripción:** Actualiza el perfil de un usuario específico con imagen opcional.

**Content-Type:** multipart/form-data

**Parámetros:**
- descripcion: string (requerido)
- fechaNacimiento: string (requerido, formato: YYYY-MM-DD)
- imagen: file (opcional)

**Ejemplo Request (multipart/form-data):**
```
descripcion: "Amante de los animales y la naturaleza"
fechaNacimiento: "1990-01-01"
imagen: [archivo de imagen]
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "usuario_id": 1,
  "descripcion": "Amante de los animales y la naturaleza",
  "fecha_nacimiento": "1990-01-01",
  "img": "/uploads/perfiles/2024/03/nueva-imagen.jpg"
}
```

**Notas:**
- Si el perfil no existe, se devolverá un error 404 Not Found
- La fechaNacimiento debe estar en formato YYYY-MM-DD
- Para la imagen:
  - Tipos permitidos: jpg, jpeg, png, gif
  - Tamaño máximo: 10MB
  - Al actualizar con nueva imagen, la anterior se elimina automáticamente
  - Se almacena en `uploads/perfiles/YYYY/MM/`
  - Las URLs son accesibles vía `/uploads/perfiles/YYYY/MM/nombre-archivo`
- La respuesta incluye el ID del perfil, el ID del usuario, la descripción, la fecha de nacimiento y la ruta de la imagen actualizada

### `DELETE /usuarios/{id}/perfiles`
**Descripción:** Elimina el perfil de un usuario específico y su imagen asociada.

**Ejemplo Response:**
```json
{
  "mensaje": "Se ha eliminado el perfil con id: 1"
}
```

**Notas:**
- Si el perfil no existe, se devolverá un error 404 Not Found
- La imagen asociada al perfil se elimina automáticamente
- Si ocurre un error al eliminar el perfil, se devolverá un error 500 Internal Server Error

## 5. Posts

### `GET /posts`
**Descripción:** Obtiene todas las publicaciones con posibilidad de filtrar por contenido o rango de fechas.

**Parámetros Query (opcionales):**
- contenido: Texto para filtrar por contenido
- fechaInicio: Fecha inicial para filtrar por rango (formato: YYYY-MM-DDThh:mm:ss)
- fechaFin: Fecha final para filtrar por rango (formato: YYYY-MM-DDThh:mm:ss)

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "grupoId": 1,
    "nombreGrupo": "Amantes de los Perros",
    "creadorId": 1,
    "nombreCreador": "Juan",
    "apellidoCreador": "Pérez",
    "contenido": "¡Hoy adopté un nuevo perrito!",
    "fecha": "2024-03-20T15:30:00",
    "img": "https://ejemplo.com/imagen.jpg",
    "createdAt": "2024-03-20T15:30:00",
    "updatedAt": "2024-03-20T15:30:00",
    "comentarios": [
      {
        "id": 1,
        "contenido": "¡Felicidades!",
        "fecha": "2024-03-20T15:35:00"
      }
    ]
  }
]
```

**Notas:**
- Si no se proporcionan filtros, se devuelven todas las publicaciones
- Si se proporciona contenido, se buscan publicaciones que contengan ese texto
- Si se proporcionan fechas, se filtran las publicaciones entre esas fechas
- Las fechas deben estar en formato ISO 8601

### `POST /posts`
**Descripción:** Crea una nueva publicación en el sistema.

**Content-Type:** multipart/form-data

**Parámetros:**
- contenido: Texto de la publicación (requerido)
- creadorId: ID del usuario creador (requerido)
- grupoId: ID del grupo (opcional)
- imagen: Archivo de imagen (opcional)

**Ejemplo Request (multipart/form-data):**
```
contenido: "¡Hoy adopté un nuevo perrito!"
creadorId: 1
grupoId: 1
imagen: [archivo de imagen]
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "grupoId": 1,
  "nombreGrupo": "Amantes de los Perros",
  "creadorId": 1,
  "nombreCreador": "Juan",
  "apellidoCreador": "Pérez",
  "contenido": "¡Hoy adopté un nuevo perrito!",
  "fecha": "2024-03-20T15:30:00",
  "img": "/uploads/posts/2024/03/imagen-123456.jpg",
  "createdAt": "2024-03-20T15:30:00",
  "updatedAt": "2024-03-20T15:30:00",
  "comentarios": []
}
```

**Notas:**
- La imagen debe ser un archivo de tipo imagen (jpg, jpeg, png, gif)
- El tamaño máximo de la imagen es 10MB
- La imagen se almacenará en el directorio `uploads/posts/YYYY/MM/`
- El nombre del archivo se generará automáticamente usando UUID
- La URL de la imagen será accesible a través de `/uploads/posts/YYYY/MM/nombre-archivo`

### `PUT /posts/{id}`
**Descripción:** Actualiza una publicación existente, incluyendo la imagen.

**Content-Type:** multipart/form-data

**Parámetros:**
- contenido: Texto de la publicación (requerido)
- creadorId: ID del usuario creador (requerido)
- grupoId: ID del grupo (opcional)
- imagen: Archivo de imagen (opcional)

**Ejemplo Request (multipart/form-data):**
```
contenido: "¡Actualización: Mi perrito ya está adaptado!"
creadorId: 1
grupoId: 1
imagen: [nuevo archivo de imagen]
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "grupoId": 1,
  "nombreGrupo": "Amantes de los Perros",
  "creadorId": 1,
  "nombreCreador": "Juan",
  "apellidoCreador": "Pérez",
  "contenido": "¡Actualización: Mi perrito ya está adaptado!",
  "fecha": "2024-03-20T16:30:00",
  "img": "/uploads/posts/2024/03/nueva-imagen-789012.jpg",
  "createdAt": "2024-03-20T15:30:00",
  "updatedAt": "2024-03-20T16:30:00",
  "comentarios": []
}
```

**Notas:**
- Si se proporciona una nueva imagen, la imagen anterior será eliminada
- La imagen debe ser un archivo de tipo imagen (jpg, jpeg, png, gif)
- El tamaño máximo de la imagen es 10MB
- La imagen se almacenará en el directorio `uploads/posts/YYYY/MM/`
- El nombre del archivo se generará automáticamente usando UUID
- La URL de la imagen será accesible a través de `/uploads/posts/YYYY/MM/nombre-archivo`

### `DELETE /posts/{id}`
**Descripción:** Elimina una publicación y su imagen asociada.

**Ejemplo Response:**
```json
{
  "mensaje": "Post eliminado correctamente"
}
```

**Notas:**
- Al eliminar un post, también se eliminará la imagen asociada del sistema de archivos
- Si la publicación no existe, se devolverá un error 404 Not Found
- Si ocurre un error al eliminar, se devolverá un error 500 Internal Server Error

### `GET /usuarios/{userId}/posts`
**Descripción:** Obtiene todas las publicaciones realizadas por un usuario específico.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "grupoId": 1,
    "nombreGrupo": "Amantes de los Perros",
    "creadorId": 1,
    "nombreCreador": "Juan",
    "apellidoCreador": "Pérez",
    "contenido": "¡Hoy adopté un nuevo perrito!",
    "fecha": "2024-03-20T15:30:00",
    "img": "https://ejemplo.com/imagen.jpg",
    "createdAt": "2024-03-20T15:30:00",
    "updatedAt": "2024-03-20T15:30:00",
    "comentarios": []
  }
]
```

**Notas:**
- Si el usuario no existe, se devolverá un error 404 Not Found
- Si el usuario no tiene publicaciones, se devolverá una lista vacía

### `GET /grupos/{grupoId}/posts`
**Descripción:** Obtiene todas las publicaciones realizadas en un grupo específico.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "grupoId": 1,
    "nombreGrupo": "Amantes de los Perros",
    "creadorId": 1,
    "nombreCreador": "Juan",
    "apellidoCreador": "Pérez",
    "contenido": "¡Hoy adopté un nuevo perrito!",
    "fecha": "2024-03-20T15:30:00",
    "img": "https://ejemplo.com/imagen.jpg",
    "createdAt": "2024-03-20T15:30:00",
    "updatedAt": "2024-03-20T15:30:00",
    "comentarios": []
  }
]
```

**Notas:**
- Si el grupo no existe, se devolverá un error 404 Not Found
- Si el grupo no tiene publicaciones, se devolverá una lista vacía

## 6. Comentarios

### `GET /posts/{postId}/comentarios`
**Descripción:** Obtiene todos los comentarios asociados a una publicación específica.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "postId": 1,
    "creadorId": 1,
    "nombreCreador": "Juan",
    "apellidoCreador": "Pérez",
    "contenido": "¡Qué lindo perro!",
    "fecha": "2024-03-20T15:30:00",
    "img": "https://ejemplo.com/imagen.jpg",
    "createdAt": "2024-03-20T15:30:00",
    "updatedAt": "2024-03-20T15:30:00"
  }
]
```

### `POST /posts/{postId}/comentarios`
**Descripción:** Crea un nuevo comentario en una publicación específica.

**Ejemplo Request:**
```json
{
  "creadorId": 1,
  "contenido": "¡Qué lindo perro!",
  "fecha": "2024-03-20T15:30:00",
  "img": "https://ejemplo.com/imagen.jpg"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "postId": 1,
  "creadorId": 1,
  "nombreCreador": "Juan",
  "apellidoCreador": "Pérez",
  "contenido": "¡Qué lindo perro!",
  "fecha": "2024-03-20T15:30:00",
  "img": "https://ejemplo.com/imagen.jpg",
  "createdAt": "2024-03-20T15:30:00",
  "updatedAt": "2024-03-20T15:30:00"
}
```

**Notas:**
- Si el post no existe, se devolverá un error 404 Not Found
- El contenido del comentario no puede exceder los 1000 caracteres
- La fecha es opcional, si no se proporciona se usará la fecha actual

### `GET /comentarios/{id}`
**Descripción:** Obtiene un comentario específico por su ID.

**Ejemplo Response:**
```json
{
  "id": 1,
  "postId": 1,
  "creadorId": 1,
  "nombreCreador": "Juan",
  "apellidoCreador": "Pérez",
  "contenido": "¡Qué lindo perro!",
  "fecha": "2024-03-20T15:30:00",
  "img": "https://ejemplo.com/imagen.jpg",
  "createdAt": "2024-03-20T15:30:00",
  "updatedAt": "2024-03-20T15:30:00"
}
```

### `PUT /comentarios/{id}`
**Descripción:** Actualiza un comentario existente.

**Ejemplo Request:**
```json
{
  "contenido": "¡Qué lindo perro! Me encanta",
  "fecha": "2024-03-20T15:35:00",
  "img": "https://ejemplo.com/nueva-imagen.jpg"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "postId": 1,
  "creadorId": 1,
  "nombreCreador": "Juan",
  "apellidoCreador": "Pérez",
  "contenido": "¡Qué lindo perro! Me encanta",
  "fecha": "2024-03-20T15:35:00",
  "img": "https://ejemplo.com/nueva-imagen.jpg",
  "createdAt": "2024-03-20T15:30:00",
  "updatedAt": "2024-03-20T15:35:00"
}
```

### `DELETE /comentarios/{id}`
**Descripción:** Elimina un comentario existente.

**Ejemplo Response:**
```json
{
  "mensaje": "Comentario eliminado con éxito"
}
```

## 7. Chat

### `POST /chat/enviar`
**Descripción:** Envía un nuevo mensaje de un usuario a otro.

**Ejemplo Request:**
```json
{
  "emisorId": 1,
  "receptorId": 2,
  "contenido": "¡Hola! ¿Cómo estás?"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "emisorId": 1,
  "receptorId": 2,
  "visto": false,
  "contenido": "¡Hola! ¿Cómo estás?",
  "fechaHora": "2024-03-20T15:30:00",
  "createdAt": "2024-03-20T15:30:00",
  "updatedAt": "2024-03-20T15:30:00"
}
```

**Notas:**
- Si el emisor o receptor no existen, se devolverá un error 400 Bad Request
- El contenido del mensaje no puede exceder los 255 caracteres

### `GET /chat/conversacion/{usuario1Id}/{usuario2Id}`
**Descripción:** Obtiene la conversación completa entre dos usuarios específicos.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "emisorId": 1,
    "receptorId": 2,
    "visto": true,
    "contenido": "¡Hola! ¿Cómo estás?",
    "fechaHora": "2024-03-20T15:30:00",
    "createdAt": "2024-03-20T15:30:00",
    "updatedAt": "2024-03-20T15:30:00"
  },
  {
    "id": 2,
    "emisorId": 2,
    "receptorId": 1,
    "visto": false,
    "contenido": "¡Hola! Todo bien, gracias",
    "fechaHora": "2024-03-20T15:31:00",
    "createdAt": "2024-03-20T15:31:00",
    "updatedAt": "2024-03-20T15:31:00"
  }
]
```

### `PUT /chat/marcar-vistos/{usuarioId}/{otroUsuarioId}`
**Descripción:** Marca como vistos todos los mensajes enviados por un usuario a otro.

**Ejemplo Response:**
```json
{
  "message": "Mensajes marcados como vistos"
}
```

### `GET /chat/no-vistos/{usuarioId}`
**Descripción:** Obtiene todos los mensajes no vistos por un usuario específico.

**Ejemplo Response:**
```json
[
  {
    "id": 2,
    "emisorId": 2,
    "receptorId": 1,
    "visto": false,
    "contenido": "¡Hola! Todo bien, gracias",
    "fechaHora": "2024-03-20T15:31:00",
    "createdAt": "2024-03-20T15:31:00",
    "updatedAt": "2024-03-20T15:31:00"
  }
]
```

### `GET /chat/enviados/{usuarioId}`
**Descripción:** Obtiene todos los mensajes enviados por un usuario específico.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "emisorId": 1,
    "receptorId": 2,
    "visto": true,
    "contenido": "¡Hola! ¿Cómo estás?",
    "fechaHora": "2024-03-20T15:30:00",
    "createdAt": "2024-03-20T15:30:00",
    "updatedAt": "2024-03-20T15:30:00"
  }
]
```

### `GET /chat/recibidos/{usuarioId}`
**Descripción:** Obtiene todos los mensajes recibidos por un usuario específico.

**Ejemplo Response:**
```json
[
  {
    "id": 2,
    "emisorId": 2,
    "receptorId": 1,
    "visto": false,
    "contenido": "¡Hola! Todo bien, gracias",
    "fechaHora": "2024-03-20T15:31:00",
    "createdAt": "2024-03-20T15:31:00",
    "updatedAt": "2024-03-20T15:31:00"
  }
]
```

### `DELETE /chat/eliminar/{usuario1Id}/{usuario2Id}`
**Descripción:** Elimina toda la conversación entre dos usuarios específicos.

**Ejemplo Response:**
```json
{
  "message": "Conversación eliminada"
}
```

## 8. Eventos

### `GET /eventos`
**Descripción:** Obtiene todos los eventos existentes en el sistema.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "nombre": "Adopción de mascotas",
    "descripcion": "Evento de adopción de mascotas en el parque central",
    "ubicacion": "Parque Central, Barcelona",
    "fecha": "2024-04-15",
    "creadorId": 1
  }
]
```

### `POST /eventos?usuarioId={id}`
**Descripción:** Crea un nuevo evento en el sistema.

**Parámetros URL:**
- usuarioId: ID del usuario que será creador del evento (requerido)

**Ejemplo Request:**
```json
{
  "nombre": "Adopción de mascotas",
  "descripcion": "Evento de adopción de mascotas en el parque central",
  "ubicacion": "Parque Central, Barcelona",
  "fecha": "2024-04-15"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "nombre": "Adopción de mascotas",
  "descripcion": "Evento de adopción de mascotas en el parque central",
  "ubicacion": "Parque Central, Barcelona",
  "fecha": "2024-04-15",
  "creadorId": 1
}
```

**Notas:**
- El nombre del evento es obligatorio y no puede estar vacío
- Si el usuario no existe, se devolverá un error 400 Bad Request
- Si el nombre está vacío, se devolverá un error 400 Bad Request
- El usuario especificado en usuarioId será automáticamente asignado como creador del evento en la tabla usuario_evento

### `GET /eventos/{id}`
**Descripción:** Obtiene un evento específico por su ID.

**Ejemplo Response:**
```json
{
  "id": 1,
  "nombre": "Adopción de mascotas",
  "descripcion": "Evento de adopción de mascotas en el parque central",
  "ubicacion": "Parque Central, Barcelona",
  "fecha": "2024-04-15",
  "creadorId": 1
}
```

**Notas:**
- Si el evento no existe, se devolverá un error 404 Not Found

### `PUT /eventos/{id}`
**Descripción:** Actualiza un evento existente.

**Ejemplo Request:**
```json
{
  "nombre": "Gran evento de adopción de mascotas",
  "descripcion": "Evento de adopción de mascotas en el parque central con más de 50 mascotas",
  "ubicacion": "Parque Central, Barcelona",
  "fecha": "2024-04-15"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "nombre": "Gran evento de adopción de mascotas",
  "descripcion": "Evento de adopción de mascotas en el parque central con más de 50 mascotas",
  "ubicacion": "Parque Central, Barcelona",
  "fecha": "2024-04-15",
  "creadorId": 1
}
```

**Notas:**
- Si el evento no existe, se devolverá un error 404 Not Found
- El nombre del evento es obligatorio y no puede estar vacío

### `DELETE /eventos/{id}`
**Descripción:** Elimina un evento existente.

**Ejemplo Response:**
```
Status: 204 No Content
```

**Notas:**
- Si el evento no existe, se devolverá un error 404 Not Found
- Si la eliminación es exitosa, se devolverá un status 204 sin contenido
- La eliminación del evento también eliminará todas sus relaciones con usuarios en la tabla usuario_evento

## 9. Grupos

### `GET /grupos`
**Descripción:** Obtiene todos los grupos existentes en el sistema.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "nombre": "Amantes de los Perros",
    "descripcion": "Grupo para compartir experiencias sobre perros"
  }
]
```

### `POST /grupos?usuarioId={id}`
**Descripción:** Crea un nuevo grupo y asigna al usuario como administrador.

**Parámetros URL:**
- usuarioId: ID del usuario que será administrador del grupo (requerido)

**Ejemplo Request:**
```json
{
  "nombre": "Amantes de los Perros",
  "descripcion": "Grupo para compartir experiencias sobre perros"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "nombre": "Amantes de los Perros",
  "descripcion": "Grupo para compartir experiencias sobre perros"
}
```

**Notas:**
- El usuario especificado en usuarioId será automáticamente asignado como administrador del grupo
- El nombre del grupo es obligatorio
- Si el usuario no existe, se devolverá un error

### `GET /grupos/{id}`
**Descripción:** Obtiene un grupo específico por su ID.

**Ejemplo Response:**
```json
{
  "id": 1,
  "nombre": "Amantes de los Perros",
  "descripcion": "Grupo para compartir experiencias sobre perros"
}
```

### `PUT /grupos/{id}`
**Descripción:** Actualiza un grupo existente.

**Ejemplo Request:**
```json
{
  "nombre": "Amantes de los Perros y Gatos",
  "descripcion": "Grupo para compartir experiencias sobre perros y gatos"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "nombre": "Amantes de los Perros y Gatos",
  "descripcion": "Grupo para compartir experiencias sobre perros y gatos"
}
```

### `DELETE /grupos/{id}`
**Descripción:** Elimina un grupo existente.

**Ejemplo Response:**
```json
{
  "mensaje": "Grupo eliminado correctamente"
}
```

## 10. Mascotas

### `GET /usuarios/{usuarioId}/mascotas`
**Descripción:** Obtiene todas las mascotas asociadas a un usuario específico.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "usuarioId": 1,
    "nombre": "Toby",
    "genero": "Macho",
    "especie": "Labrador",
    "foto": "/uploads/mascotas/2024/03/toby-123456.jpg"
  }
]
```

**Notas:**
- Si el usuario no existe, se devolverá un error 400 Bad Request
- Si el usuario no tiene mascotas, se devolverá una lista vacía
- La foto es opcional y puede ser null

### `POST /usuarios/{usuarioId}/mascotas`
**Descripción:** Crea una nueva mascota asociada a un usuario específico.

**Content-Type:** multipart/form-data

**Parámetros:**
- nombre: string (requerido)
- genero: string (requerido)
- especie: string (requerido)
- imagen: file (opcional)

**Ejemplo Request (multipart/form-data):**
```
nombre: "Toby"
genero: "Macho"
especie: "Labrador"
imagen: [archivo de imagen]
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "usuarioId": 1,
  "nombre": "Toby",
  "genero": "Macho",
  "especie": "Labrador",
  "foto": "/uploads/mascotas/2024/03/toby-123456.jpg"
}
```

**Notas:**
- El nombre, género y especie son campos obligatorios
- El género debe tener un máximo de 10 caracteres
- El nombre y la especie deben tener un máximo de 50 caracteres
- Si el usuario no existe, se devolverá un error 400 Bad Request
- Para la imagen:
  - Tipos permitidos: jpg, jpeg, png, gif
  - Tamaño máximo: 5MB
  - Se almacena en `uploads/mascotas/YYYY/MM/`
  - Las URLs son accesibles vía `/uploads/mascotas/YYYY/MM/nombre-archivo`
  - La imagen es opcional

### `GET /usuarios/{usuarioId}/mascotas/{mascotaId}`
**Descripción:** Obtiene una mascota específica de un usuario por su ID.

**Ejemplo Response:**
```json
{
  "id": 1,
  "usuarioId": 1,
  "nombre": "Toby",
  "genero": "Macho",
  "especie": "Labrador",
  "foto": "/uploads/mascotas/2024/03/toby-123456.jpg"
}
```

**Notas:**
- Si la mascota no existe o no pertenece al usuario, se devolverá un error 404 Not Found

### `PUT /usuarios/{usuarioId}/mascotas/{mascotaId}`
**Descripción:** Actualiza una mascota específica de un usuario.

**Content-Type:** multipart/form-data

**Parámetros:**
- nombre: string (requerido)
- genero: string (requerido)
- especie: string (requerido)
- imagen: file (opcional)

**Ejemplo Request (multipart/form-data):**
```
nombre: "Toby"
genero: "Macho"
especie: "Labrador Retriever"
imagen: [nuevo archivo de imagen]
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "usuarioId": 1,
  "nombre": "Toby",
  "genero": "Macho",
  "especie": "Labrador Retriever",
  "foto": "/uploads/mascotas/2024/03/nueva-imagen-789012.jpg"
}
```

**Notas:**
- Si la mascota no existe o no pertenece al usuario, se devolverá un error 404 Not Found
- El nombre, género y especie son campos obligatorios
- El género debe tener un máximo de 10 caracteres
- El nombre y la especie deben tener un máximo de 50 caracteres
- Para la imagen:
  - Tipos permitidos: jpg, jpeg, png, gif
  - Tamaño máximo: 5MB
  - Al actualizar con nueva imagen, la anterior se elimina automáticamente
  - Se almacena en `uploads/mascotas/YYYY/MM/`
  - Las URLs son accesibles vía `/uploads/mascotas/YYYY/MM/nombre-archivo`
  - La imagen es opcional

### `DELETE /usuarios/{usuarioId}/mascotas/{mascotaId}`
**Descripción:** Elimina una mascota específica de un usuario.

**Ejemplo Response:**
```json
{
  "mensaje": "Mascota eliminada con ID: 1"
}
```

**Notas:**
- Si la mascota no existe o no pertenece al usuario, se devolverá un error 404 Not Found
- Si la eliminación es exitosa, se devolverá un mensaje de confirmación
- La imagen asociada a la mascota se elimina automáticamente del sistema de archivos

## 11. Notificaciones

### `GET /notificaciones`
**Descripción:** Obtiene todas las notificaciones existentes en el sistema.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "fecha": "2024-03-20T15:30:00"
  }
]
```

**Notas:**
- Si ocurre un error al obtener las notificaciones, se devolverá un error 500 Internal Server Error
- Si no hay notificaciones, se devolverá una lista vacía

### `GET /notificaciones/{id}`
**Descripción:** Obtiene una notificación específica por su ID.

**Ejemplo Response:**
```json
{
  "id": 1,
  "fecha": "2024-03-20T15:30:00"
}
```

**Notas:**
- Si la notificación no existe, se devolverá un error 404 Not Found
- Si ocurre un error al obtener la notificación, se devolverá un error 500 Internal Server Error

### `POST /notificaciones`
**Descripción:** Crea una nueva notificación en el sistema.

**Ejemplo Request:**
```json
{
  "fecha": "2024-03-20T15:30:00"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "fecha": "2024-03-20T15:30:00"
}
```

**Notas:**
- La fecha es un campo obligatorio y debe ser una fecha válida
- El ID se genera automáticamente por el sistema
- La fecha debe estar en formato ISO 8601 (YYYY-MM-DDThh:mm:ss)
- Si ocurre un error al crear la notificación, se devolverá un error 500 Internal Server Error con un mensaje descriptivo
- La respuesta incluirá el ID generado automáticamente junto con la fecha proporcionada

### `PUT /notificaciones/{id}`
**Descripción:** Actualiza una notificación existente.

**Ejemplo Request:**
```json
{
  "fecha": "2024-03-20T16:30:00"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "fecha": "2024-03-20T16:30:00"
}
```

**Notas:**
- Si la notificación no existe, se devolverá un error 404 Not Found
- La fecha es un campo obligatorio
- Si ocurre un error al actualizar la notificación, se devolverá un error 500 Internal Server Error

### `DELETE /notificaciones/{id}`
**Descripción:** Elimina una notificación existente.

**Ejemplo Response:**
```json
{
  "mensaje": "Notificación eliminada correctamente"
}
```

**Notas:**
- Si la notificación no existe, se devolverá un error 404 Not Found
- Si ocurre un error al eliminar la notificación, se devolverá un error 500 Internal Server Error

## 12. Seguidos

### `GET /usuarios/{usuarioId}/seguidos`
**Descripción:** Obtiene todos los usuarios que sigue un usuario específico.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "usuarioQueSigueId": 1,
    "usuarioQueEsSeguidoId": 2
  },
  {
    "id": 2,
    "usuarioQueSigueId": 1,
    "usuarioQueEsSeguidoId": 3
  }
]
```

**Notas:**
- Si el usuario no existe, se devolverá un error 500 Internal Server Error
- Si el usuario no sigue a nadie, se devolverá una lista vacía

### `POST /usuarios/{usuarioId}/seguidos/{usuarioASeguirId}`
**Descripción:** Crea una relación de seguimiento entre dos usuarios.

**Ejemplo Response:**
```json
{
  "id": 1,
  "usuarioQueSigueId": 1,
  "usuarioQueEsSeguidoId": 2
}
```

**Notas:**
- No se puede seguir al mismo usuario (usuarioId = usuarioASeguirId)
- El usuario que sigue debe existir
- El usuario a seguir debe existir
- No se puede seguir a un usuario que ya se está siguiendo
- Si alguna de estas validaciones falla, se devolverá un error 400 Bad Request con un mensaje descriptivo

### `POST /usuarios/{usuarioId}/seguidos`
**Descripción:** Crea una relación de seguimiento utilizando un DTO.

**Ejemplo Request:**
```json
{
  "usuarioQueSigueId": 1,
  "usuarioQueEsSeguidoId": 2
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "usuarioQueSigueId": 1,
  "usuarioQueEsSeguidoId": 2
}
```

**Notas:**
- El usuarioId de la ruta tiene prioridad sobre el usuarioQueSigueId del DTO
- No se puede seguir al mismo usuario
- El usuario que sigue debe existir
- El usuario a seguir debe existir
- No se puede seguir a un usuario que ya se está siguiendo
- Si alguna de estas validaciones falla, se devolverá un error 400 Bad Request con un mensaje descriptivo

### `DELETE /usuarios/{usuarioId}/seguidos/{usuarioASeguirId}`
**Descripción:** Elimina una relación de seguimiento entre dos usuarios.

**Ejemplo Response:**
```json
{
  "mensaje": "Has dejado de seguir al usuario con ID: 2"
}
```

**Notas:**
- Si la relación de seguimiento no existe, se devolverá un error 404 Not Found
- Si ocurre un error al eliminar, se devolverá un error 500 Internal Server Error

## 13. Valoraciones

### `GET /valoraciones`
**Descripción:** Obtiene todas las valoraciones existentes en el sistema.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "autorId": 1,
    "nombreAutor": "Juan",
    "apellidoAutor": "Pérez",
    "receptorId": 2,
    "nombreReceptor": "María",
    "apellidoReceptor": "García",
    "puntuacion": "5",
    "contenido": "Excelente servicio",
    "fecha": "2024-03-20T15:30:00",
    "createdAt": "2024-03-20T15:30:00",
    "updatedAt": "2024-03-20T15:30:00"
  }
]
```

**Notas:**
- Si ocurre un error al obtener las valoraciones, se devolverá un error 400 Bad Request
- Si no hay valoraciones, se devolverá una lista vacía

### `GET /valoraciones/{id}`
**Descripción:** Obtiene una valoración específica por su ID.

**Ejemplo Response:**
```json
  {
    "id": 1,
  "autorId": 1,
  "nombreAutor": "Juan",
  "apellidoAutor": "Pérez",
  "receptorId": 2,
  "nombreReceptor": "María",
  "apellidoReceptor": "García",
  "puntuacion": "5",
  "contenido": "Excelente servicio",
  "fecha": "2024-03-20T15:30:00",
  "createdAt": "2024-03-20T15:30:00",
  "updatedAt": "2024-03-20T15:30:00"
}
```

**Notas:**
- Si la valoración no existe, se devolverá un error 404 Not Found
- Si ocurre un error al obtener la valoración, se devolverá un error 400 Bad Request

### `POST /valoraciones/usuarios/{autorId}/receptor/{receptorId}`
**Descripción:** Crea una nueva valoración de un usuario hacia otro.

**Ejemplo Request:**
```json
{
  "puntuacion": "5",
  "contenido": "Excelente servicio",
  "fecha": "2024-03-20T15:30:00"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "autorId": 1,
  "nombreAutor": "Juan",
  "apellidoAutor": "Pérez",
  "receptorId": 2,
  "nombreReceptor": "María",
  "apellidoReceptor": "García",
  "puntuacion": "5",
  "contenido": "Excelente servicio",
  "fecha": "2024-03-20T15:30:00",
  "createdAt": "2024-03-20T15:30:00",
  "updatedAt": "2024-03-20T15:30:00"
}
```

**Notas:**
- El autor y el receptor son obligatorios y deben existir
- La puntuación debe ser un valor entre 1 y 5
- El contenido es obligatorio
- La fecha es opcional, si no se proporciona se usará la fecha actual
- Si ocurre un error al crear la valoración, se devolverá un error 400 Bad Request

### `PUT /valoraciones/{id}`
**Descripción:** Actualiza una valoración existente.

**Ejemplo Request:**
```json
{
  "puntuacion": "4",
  "contenido": "Buen servicio, pero podría mejorar",
  "fecha": "2024-03-20T16:30:00"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "autorId": 1,
  "nombreAutor": "Juan",
  "apellidoAutor": "Pérez",
  "receptorId": 2,
  "nombreReceptor": "María",
  "apellidoReceptor": "García",
  "puntuacion": "4",
  "contenido": "Buen servicio, pero podría mejorar",
  "fecha": "2024-03-20T16:30:00",
  "createdAt": "2024-03-20T15:30:00",
  "updatedAt": "2024-03-20T16:30:00"
}
```

**Notas:**
- Si la valoración no existe, se devolverá un error 404 Not Found
- La puntuación debe ser un valor entre 1 y 5
- El contenido es obligatorio
- Si ocurre un error al actualizar la valoración, se devolverá un error 400 Bad Request

### `DELETE /valoraciones/{id}`
**Descripción:** Elimina una valoración existente.

**Ejemplo Response:**
```json
{
  "mensaje": "Valoración eliminada correctamente"
}
```

**Notas:**
- Si la valoración no existe, se devolverá un error 404 Not Found
- Si ocurre un error al eliminar la valoración, se devolverá un error 400 Bad Request

### `GET /valoraciones/usuarios/{receptorId}/recibidas`
**Descripción:** Obtiene todas las valoraciones recibidas por un usuario específico.

**Ejemplo Response:**
```json
[
{
  "id": 1,
    "autorId": 1,
    "nombreAutor": "Juan",
    "apellidoAutor": "Pérez",
    "receptorId": 2,
    "nombreReceptor": "María",
    "apellidoReceptor": "García",
    "puntuacion": "5",
    "contenido": "Excelente servicio",
    "fecha": "2024-03-20T15:30:00",
    "createdAt": "2024-03-20T15:30:00",
    "updatedAt": "2024-03-20T15:30:00"
  }
]
```

**Notas:**
- Si el usuario no existe, se devolverá un error 400 Bad Request
- Si el usuario no tiene valoraciones recibidas, se devolverá una lista vacía

### `GET /valoraciones/usuarios/{autorId}/enviadas`
**Descripción:** Obtiene todas las valoraciones emitidas por un usuario específico.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "autorId": 1,
    "nombreAutor": "Juan",
    "apellidoAutor": "Pérez",
    "receptorId": 2,
    "nombreReceptor": "María",
    "apellidoReceptor": "García",
    "puntuacion": "5",
    "contenido": "Excelente servicio",
    "fecha": "2024-03-20T15:30:00",
    "createdAt": "2024-03-20T15:30:00",
    "updatedAt": "2024-03-20T15:30:00"
  }
]
```

**Notas:**
- Si el usuario no existe, se devolverá un error 400 Bad Request
- Si el usuario no tiene valoraciones emitidas, se devolverá una lista vacía

## 14. Relaciones

### 14.1. Usuario-Comentario

### `GET /usuario-comentario`
**Descripción:** Obtiene todas las relaciones entre usuarios y comentarios.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "usuarioId": 1,
    "comentarioId": 1,
    "like": true,
    "fecha": "2024-03-20T15:30:00"
  },
  {
    "id": 2,
    "usuarioId": 2,
    "comentarioId": 1,
    "like": false,
    "fecha": "2024-03-20T15:35:00"
  }
]
```

**Notas:**
- La fecha se genera automáticamente al crear la relación
- El campo 'like' indica si el usuario dio like (true) o dislike (false) al comentario

### `GET /usuario-comentario/{id}`
**Descripción:** Obtiene una relación específica entre usuario y comentario por su ID.

**Ejemplo Response:**
```json
{
  "id": 1,
  "usuarioId": 1,
  "comentarioId": 1,
  "like": true,
  "fecha": "2024-03-20T15:30:00"
}
```

**Notas:**
- Si la relación no existe, se devolverá un error 404 Not Found

### `GET /usuario-comentario/usuario/{usuarioId}`
**Descripción:** Obtiene todas las relaciones de un usuario específico con comentarios.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "usuarioId": 1,
    "comentarioId": 1,
    "like": true,
    "fecha": "2024-03-20T15:30:00"
  },
  {
    "id": 3,
    "usuarioId": 1,
    "comentarioId": 2,
    "like": false,
    "fecha": "2024-03-20T16:00:00"
  }
]
```

**Notas:**
- Si el usuario no existe, se devolverá un error 404 Not Found
- Si el usuario no tiene relaciones con comentarios, se devolverá una lista vacía

### `GET /usuario-comentario/comentario/{comentarioId}`
**Descripción:** Obtiene todas las relaciones de un comentario específico con usuarios.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "usuarioId": 1,
    "comentarioId": 1,
    "like": true,
    "fecha": "2024-03-20T15:30:00"
  },
  {
    "id": 2,
    "usuarioId": 2,
    "comentarioId": 1,
    "like": false,
    "fecha": "2024-03-20T15:35:00"
  }
]
```

**Notas:**
- Si el comentario no existe, se devolverá un error 404 Not Found
- Si el comentario no tiene relaciones con usuarios, se devolverá una lista vacía

### `GET /usuario-comentario/post/{postId}`
**Descripción:** Obtiene todas las relaciones de un post específico con usuarios.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "postId": 1,
    "usuarioId": 1,
    "fecha": "2024-03-20T15:30:00"
  },
  {
    "id": 3,
    "postId": 1,
    "usuarioId": 2,
    "fecha": "2024-03-20T15:35:00"
  }
]
```

**Notas:**
- Si el post no existe, se devolverá un error 404 Not Found
- Si el post no tiene relaciones con usuarios, se devolverá una lista vacía

### `POST /usuario-comentario`
**Descripción:** Crea una nueva relación entre un usuario y un comentario.

**Ejemplo Request:**
```json
{
  "usuarioId": 1,
  "comentarioId": 1,
  "like": true
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "usuarioId": 1,
  "comentarioId": 1,
  "like": true,
  "fecha": "2024-03-20T15:30:00"
}
```

**Notas:**
- Todos los campos son obligatorios
- No se puede crear una relación duplicada entre el mismo usuario y comentario
- El usuario y el comentario deben existir
- La fecha se genera automáticamente
- Si alguna validación falla, se devolverá un error 400 Bad Request

### `DELETE /usuario-comentario/{id}`
**Descripción:** Elimina una relación específica entre usuario y comentario.

**Ejemplo Response:**
```
Status: 204 No Content
```

**Notas:**
- Si la relación no existe, se devolverá un error 404 Not Found
- Si la eliminación es exitosa, se devolverá un status 204 sin contenido

### `DELETE /usuario-comentario/usuario/{usuarioId}`
**Descripción:** Elimina todas las relaciones de un usuario específico con comentarios.

**Ejemplo Response:**
```
Status: 204 No Content
```

**Notas:**
- Si el usuario no existe, se devolverá un error 404 Not Found
- Si la eliminación es exitosa, se devolverá un status 204 sin contenido

### `DELETE /usuario-comentario/comentario/{comentarioId}`
**Descripción:** Elimina todas las relaciones de un comentario específico con usuarios.

**Ejemplo Response:**
```
Status: 204 No Content
```

**Notas:**
- Si el comentario no existe, se devolverá un error 404 Not Found
- Si la eliminación es exitosa, se devolverá un status 204 sin contenido

### `GET /usuario-comentario/post/{postId}`
**Descripción:** Obtiene todas las relaciones de un post específico con usuarios.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "postId": 1,
    "usuarioId": 1,
    "fecha": "2024-03-20T15:30:00"
  },
  {
    "id": 3,
    "postId": 1,
    "usuarioId": 2,
    "fecha": "2024-03-20T15:35:00"
  }
]
```

**Notas:**
- Si el post no existe, se devolverá un error 404 Not Found
- Si el post no tiene relaciones, se devolverá una lista vacía

### 14.2. Usuario-Evento

### `GET /usuario-evento`
**Descripción:** Obtiene todas las relaciones entre usuarios y eventos.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "usuarioId": 1,
    "eventoId": 1,
    "rol": "CREADOR"
  },
  {
    "id": 2,
    "usuarioId": 2,
    "eventoId": 1,
    "rol": "ASISTENTE"
  }
]
```

**Notas:**
- El campo 'rol' puede ser "CREADOR" o "ASISTENTE"
- Cada usuario puede tener un solo rol por evento
- La relación se crea automáticamente al crear un evento, asignando al usuario como "CREADOR"

### `GET /usuario-evento/{id}`
**Descripción:** Obtiene una relación específica entre usuario y evento por su ID.

**Ejemplo Response:**
```json
{
  "id": 1,
  "usuarioId": 1,
  "eventoId": 1,
  "rol": "CREADOR"
}
```

**Notas:**
- Si la relación no existe, se devolverá un error 404 Not Found

### `GET /usuario-evento/usuario/{usuarioId}`
**Descripción:** Obtiene todas las relaciones de un usuario específico con eventos.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "usuarioId": 1,
    "eventoId": 1,
    "rol": "CREADOR"
  },
  {
    "id": 3,
    "usuarioId": 1,
    "eventoId": 2,
    "rol": "ASISTENTE"
  }
]
```

**Notas:**
- Si el usuario no existe, se devolverá un error 404 Not Found
- Si el usuario no tiene relaciones con eventos, se devolverá una lista vacía

### `GET /usuario-evento/evento/{eventoId}`
**Descripción:** Obtiene todas las relaciones de un evento específico con usuarios.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "usuarioId": 1,
    "eventoId": 1,
    "rol": "CREADOR"
  },
  {
    "id": 2,
    "usuarioId": 2,
    "eventoId": 1,
    "rol": "ASISTENTE"
  }
]
```

**Notas:**
- Si el evento no existe, se devolverá un error 404 Not Found
- Si el evento no tiene relaciones con usuarios, se devolverá una lista vacía

### `POST /usuario-evento`
**Descripción:** Crea una nueva relación entre un usuario y un evento.

**Ejemplo Request:**
```json
{
  "usuarioId": 1,
  "eventoId": 1,
  "rol": "ASISTENTE"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "usuarioId": 1,
  "eventoId": 1,
  "rol": "ASISTENTE"
}
```

**Notas:**
- Todos los campos son obligatorios
- El rol debe ser "CREADOR" o "ASISTENTE"
- No se puede crear una relación duplicada entre el mismo usuario y evento
- El usuario y el evento deben existir
- Si alguna validación falla, se devolverá un error 400 Bad Request

### `DELETE /usuario-evento/{id}`
**Descripción:** Elimina una relación específica entre usuario y evento.

**Ejemplo Response:**
```
Status: 204 No Content
```

**Notas:**
- Si la relación no existe, se devolverá un error 404 Not Found
- Si la eliminación es exitosa, se devolverá un status 204 sin contenido

### `DELETE /usuario-evento/usuario/{usuarioId}`
**Descripción:** Elimina todas las relaciones de un usuario específico con eventos.

**Ejemplo Response:**
```
Status: 204 No Content
```

**Notas:**
- Si el usuario no existe, se devolverá un error 404 Not Found
- Si la eliminación es exitosa, se devolverá un status 204 sin contenido

### `DELETE /usuario-evento/evento/{eventoId}`
**Descripción:** Elimina todas las relaciones de un evento específico con usuarios.

**Ejemplo Response:**
```
Status: 204 No Content
```

**Notas:**
- Si el evento no existe, se devolverá un error 404 Not Found
- Si la eliminación es exitosa, se devolverá un status 204 sin contenido

### `DELETE /usuario-evento/usuario/{usuarioId}/evento/{eventoId}`
**Descripción:** Elimina una relación específica entre un usuario y un evento.

**Ejemplo Response:**
```
Status: 204 No Content
```

**Notas:**
- Si la relación no existe, se devolverá un error 404 Not Found
- Si la eliminación es exitosa, se devolverá un status 204 sin contenido

### 14.3. Usuario-Grupo

### `GET /usuario-grupo`
**Descripción:** Obtiene todas las relaciones entre usuarios y grupos.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "grupoId": 1,
    "nombreGrupo": "Amantes de los Perros",
    "usuarioId": 1,
    "nombreUsuario": "Juan",
    "apellidoUsuario": "Pérez",
    "rol": "ADMINISTRADOR"
  },
  {
    "id": 2,
    "grupoId": 1,
    "nombreGrupo": "Amantes de los Perros",
    "usuarioId": 2,
    "nombreUsuario": "María",
    "apellidoUsuario": "García",
    "rol": "MIEMBRO"
  }
]
```

**Notas:**
- El campo 'rol' puede ser "ADMINISTRADOR" o "MIEMBRO"
- Cada usuario puede tener un solo rol por grupo

### `POST /usuario-grupo`
**Descripción:** Crea una nueva relación entre un usuario y un grupo.

**Ejemplo Request:**
```json
{
  "usuarioId": 1,
  "grupoId": 1,
  "rol": "ADMINISTRADOR"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "grupoId": 1,
  "nombreGrupo": "Amantes de los Perros",
  "usuarioId": 1,
  "nombreUsuario": "Juan",
  "apellidoUsuario": "Pérez",
  "rol": "ADMINISTRADOR"
}
```

**Notas:**
- Todos los campos son obligatorios
- El rol debe ser "ADMINISTRADOR" o "MIEMBRO"
- No se puede crear una relación duplicada entre el mismo usuario y grupo
- El usuario y el grupo deben existir
- Si alguna validación falla, se devolverá un error 400 Bad Request

### `GET /usuario-grupo/{id}`
**Descripción:** Obtiene una relación específica entre usuario y grupo por su ID.

**Ejemplo Response:**
```json
{
  "id": 1,
  "grupoId": 1,
  "nombreGrupo": "Amantes de los Perros",
  "usuarioId": 1,
  "nombreUsuario": "Juan",
  "apellidoUsuario": "Pérez",
  "rol": "ADMINISTRADOR"
}
```

**Notas:**
- Si la relación no existe, se devolverá un error 404 Not Found

### `PUT /usuario-grupo/{id}`
**Descripción:** Actualiza una relación existente entre usuario y grupo.

**Ejemplo Request:**
```json
{
  "usuarioId": 1,
  "grupoId": 1,
  "rol": "MIEMBRO"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "grupoId": 1,
  "nombreGrupo": "Amantes de los Perros",
  "usuarioId": 1,
  "nombreUsuario": "Juan",
  "apellidoUsuario": "Pérez",
  "rol": "MIEMBRO"
}
```

**Notas:**
- Si la relación no existe, se devolverá un error 404 Not Found
- Todos los campos son obligatorios
- El rol debe ser "ADMINISTRADOR" o "MIEMBRO"
- El usuario y el grupo deben existir

### `DELETE /usuario-grupo/{id}`
**Descripción:** Elimina una relación específica entre usuario y grupo.

**Ejemplo Response:**
```json
{
  "mensaje": "Eliminado correctamente"
}
```

**Notas:**
- Si la relación no existe, se devolverá un error 404 Not Found
- Si ocurre un error al eliminar, se devolverá un error 500 Internal Server Error

### `GET /usuario-grupo/usuario/{usuario_id}`
**Descripción:** Obtiene todos los grupos a los que pertenece un usuario específico.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "grupoId": 1,
    "nombreGrupo": "Amantes de los Perros",
    "usuarioId": 1,
    "nombreUsuario": "Juan",
    "apellidoUsuario": "Pérez",
    "rol": "ADMINISTRADOR"
  },
  {
    "id": 3,
    "grupoId": 2,
    "nombreGrupo": "Amantes de los Gatos",
    "usuarioId": 1,
    "nombreUsuario": "Juan",
    "apellidoUsuario": "Pérez",
    "rol": "MIEMBRO"
  }
]
```

**Notas:**
- Si el usuario no existe, se devolverá un error 404 Not Found
- Si el usuario no tiene grupos, se devolverá una lista vacía

### `GET /usuario-grupo/grupo/{grupo_id}`
**Descripción:** Obtiene todos los usuarios que pertenecen a un grupo específico.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "grupoId": 1,
    "nombreGrupo": "Amantes de los Perros",
    "usuarioId": 1,
    "nombreUsuario": "Juan",
    "apellidoUsuario": "Pérez",
    "rol": "ADMINISTRADOR"
  },
  {
    "id": 2,
    "grupoId": 1,
    "nombreGrupo": "Amantes de los Perros",
    "usuarioId": 2,
    "nombreUsuario": "María",
    "apellidoUsuario": "García",
    "rol": "MIEMBRO"
  }
]
```

**Notas:**
- Si el grupo no existe, se devolverá un error 404 Not Found
- Si el grupo no tiene usuarios, se devolverá una lista vacía

### `GET /usuario-grupo/relacion/{usuarioId}/{grupoId}`
**Descripción:** Obtiene una relación específica entre un usuario y un grupo.

**Ejemplo Response:**
```json
{
  "id": 1,
  "grupoId": 1,
  "nombreGrupo": "Amantes de los Perros",
  "usuarioId": 1,
  "nombreUsuario": "Juan",
  "apellidoUsuario": "Pérez",
  "rol": "ADMINISTRADOR"
}
```

**Notas:**
- Si la relación no existe, se devolverá un error 404 Not Found
- Si el usuario o el grupo no existen, se devolverá un error 404 Not Found

### 14.4. Usuario-Post

### `GET /usuario-post`
**Descripción:** Obtiene todas las relaciones entre usuarios y posts.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "postId": 1,
    "usuarioId": 1,
    "fecha": "2024-03-20T15:30:00"
  },
  {
    "id": 2,
    "postId": 2,
    "usuarioId": 1,
    "fecha": "2024-03-20T16:00:00"
  }
]
```

**Notas:**
- La fecha se genera automáticamente al crear la relación
- Si no hay relaciones, se devolverá una lista vacía

### `GET /usuario-post/{id}`
**Descripción:** Obtiene una relación específica entre usuario y post por su ID.

**Ejemplo Response:**
```json
{
  "id": 1,
  "postId": 1,
  "usuarioId": 1,
  "fecha": "2024-03-20T15:30:00"
}
```

**Notas:**
- Si la relación no existe, se devolverá un error 404 Not Found

### `GET /usuario-post/usuario/{usuarioId}`
**Descripción:** Obtiene todas las relaciones de un usuario específico con posts.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "postId": 1,
    "usuarioId": 1,
    "fecha": "2024-03-20T15:30:00"
  },
  {
    "id": 2,
    "postId": 2,
    "usuarioId": 1,
    "fecha": "2024-03-20T16:00:00"
  }
]
```

**Notas:**
- Si el usuario no existe, se devolverá un error 404 Not Found
- Si el usuario no tiene relaciones con posts, se devolverá una lista vacía

### `GET /usuario-post/post/{postId}`
**Descripción:** Obtiene todas las relaciones de un post específico con usuarios.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "postId": 1,
    "usuarioId": 1,
    "fecha": "2024-03-20T15:30:00"
  },
  {
    "id": 3,
    "postId": 1,
    "usuarioId": 2,
    "fecha": "2024-03-20T15:35:00"
  }
]
```

**Notas:**
- Si el post no existe, se devolverá un error 404 Not Found
- Si el post no tiene relaciones con usuarios, se devolverá una lista vacía

### `POST /usuario-post`
**Descripción:** Crea una nueva relación entre un usuario y un post.

**Ejemplo Request:**
```json
{
  "usuarioId": 1,
  "postId": 1
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "postId": 1,
  "usuarioId": 1,
  "fecha": "2024-03-20T15:30:00"
}
```

**Notas:**
- Todos los campos son obligatorios
- No se puede crear una relación duplicada entre el mismo usuario y post
- El usuario y el post deben existir
- La fecha se genera automáticamente
- Si alguna validación falla, se devolverá un error 400 Bad Request

### `DELETE /usuario-post/{id}`
**Descripción:** Elimina una relación específica entre usuario y post.

**Ejemplo Response:**
```
Status: 204 No Content
```

**Notas:**
- Si la relación no existe, se devolverá un error 404 Not Found
- Si la eliminación es exitosa, se devolverá un status 204 sin contenido

### `DELETE /usuario-post/usuario/{usuarioId}`
**Descripción:** Elimina todas las relaciones de un usuario específico con posts.

**Ejemplo Response:**
```
Status: 204 No Content
```

**Notas:**
- Si el usuario no existe, se devolverá un error 404 Not Found
- Si la eliminación es exitosa, se devolverá un status 204 sin contenido

### `DELETE /usuario-post/post/{postId}`
**Descripción:** Elimina todas las relaciones de un post específico con usuarios.

**Ejemplo Response:**
```
Status: 204 No Content
```

**Notas:**
- Si el post no existe, se devolverá un error 404 Not Found
- Si la eliminación es exitosa, se devolverá un status 204 sin contenido

### 14.5. Usuario-Interacción

### `GET /usuario-interaccion`
**Descripción:** Obtiene todas las relaciones entre usuarios y comentarios/posts.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "usuarioId": 1,
    "comentarioId": 1,
    "postId": null,
    "like": true,
    "fecha": "2024-03-20T15:30:00"
  },
  {
    "id": 2,
    "usuarioId": 2,
    "comentarioId": null,
    "postId": 1,
    "like": false,
    "fecha": "2024-03-20T15:35:00"
  }
]
```

**Notas:**
- La fecha se genera automáticamente al crear la relación
- El campo 'like' indica si el usuario dio like (true) o dislike (false)
- Cada interacción debe estar asociada a un comentario O un post, nunca a ambos

### `GET /usuario-interaccion/{id}`
**Descripción:** Obtiene una relación específica entre usuario y comentario/post por su ID.

**Ejemplo Response:**
```json
{
  "id": 1,
  "usuarioId": 1,
  "comentarioId": 1,
  "postId": null,
  "like": true,
  "fecha": "2024-03-20T15:30:00"
}
```

**Notas:**
- Si la relación no existe, se devolverá un error 404 Not Found

### `GET /usuario-interaccion/usuario/{usuarioId}`
**Descripción:** Obtiene todas las relaciones de un usuario específico con comentarios y posts.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "usuarioId": 1,
    "comentarioId": 1,
    "postId": null,
    "like": true,
    "fecha": "2024-03-20T15:30:00"
  },
  {
    "id": 3,
    "usuarioId": 1,
    "comentarioId": null,
    "postId": 2,
    "like": false,
    "fecha": "2024-03-20T16:00:00"
  }
]
```

**Notas:**
- Si el usuario no existe, se devolverá un error 404 Not Found
- Si el usuario no tiene relaciones, se devolverá una lista vacía

### `GET /usuario-interaccion/comentario/{comentarioId}`
**Descripción:** Obtiene todas las relaciones de un comentario específico con usuarios.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "usuarioId": 1,
    "comentarioId": 1,
    "postId": null,
    "like": true,
    "fecha": "2024-03-20T15:30:00"
  },
  {
    "id": 2,
    "usuarioId": 2,
    "comentarioId": 1,
    "postId": null,
    "like": false,
    "fecha": "2024-03-20T15:35:00"
  }
]
```

**Notas:**
- Si el comentario no existe, se devolverá un error 404 Not Found
- Si el comentario no tiene relaciones, se devolverá una lista vacía

### `GET /usuario-interaccion/post/{postId}`
**Descripción:** Obtiene todas las relaciones de un post específico con usuarios.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "usuarioId": 1,
    "comentarioId": null,
    "postId": 1,
    "like": true,
    "fecha": "2024-03-20T15:30:00"
  },
  {
    "id": 2,
    "usuarioId": 2,
    "comentarioId": null,
    "postId": 1,
    "like": false,
    "fecha": "2024-03-20T15:35:00"
  }
]
```

**Notas:**
- Si el post no existe, se devolverá un error 404 Not Found
- Si el post no tiene relaciones, se devolverá una lista vacía

### `POST /usuario-interaccion`
**Descripción:** Crea una nueva relación entre un usuario y un comentario o post.

**Ejemplo Request:**
```json
{
  "usuarioId": 1,
  "comentarioId": 1,
  "postId": null,
  "like": true
}
```
o
```json
{
  "usuarioId": 1,
  "comentarioId": null,
  "postId": 1,
  "like": true
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "usuarioId": 1,
  "comentarioId": 1,
  "postId": null,
  "like": true,
  "fecha": "2024-03-20T15:30:00"
}
```

**Notas:**
- El usuarioId es obligatorio
- Debe especificarse comentarioId O postId, pero no ambos
- El campo like es obligatorio
- No se puede crear una relación duplicada entre el mismo usuario y comentario/post
- El usuario, comentario (si se especifica) y post (si se especifica) deben existir
- La fecha se genera automáticamente
- Si alguna validación falla, se devolverá un error 400 Bad Request

### `DELETE /usuario-interaccion/{id}`
**Descripción:** Elimina una relación específica entre usuario y comentario/post.

**Ejemplo Response:**
```
Status: 204 No Content
```

**Notas:**
- Si la relación no existe, se devolverá un error 404 Not Found
- Si la eliminación es exitosa, se devolverá un status 204 sin contenido

### `DELETE /usuario-interaccion/usuario/{usuarioId}`
**Descripción:** Elimina todas las relaciones de un usuario específico con comentarios y posts.

**Ejemplo Response:**
```
Status: 204 No Content
```

**Notas:**
- Si el usuario no existe, se devolverá un error 404 Not Found
- Si la eliminación es exitosa, se devolverá un status 204 sin contenido

### `DELETE /usuario-interaccion/comentario/{comentarioId}`
**Descripción:** Elimina todas las relaciones de un comentario específico con usuarios.

**Ejemplo Response:**
```
Status: 204 No Content
```

**Notas:**
- Si el comentario no existe, se devolverá un error 404 Not Found
- Si la eliminación es exitosa, se devolverá un status 204 sin contenido

### `DELETE /usuario-interaccion/post/{postId}`
**Descripción:** Elimina todas las relaciones de un post específico con usuarios.

**Ejemplo Response:**
```
Status: 204 No Content
```

**Notas:**
- Si el post no existe, se devolverá un error 404 Not Found
- Si la eliminación es exitosa, se devolverá un status 204 sin contenido

### Mascotas

#### POST /mascotas
Crea una nueva mascota.

**Parámetros:**
- `nombre` (string, requerido): Nombre de la mascota
- `genero` (string, requerido): Género de la mascota (Macho/Hembra)
- `especie` (string, requerido): Especie de la mascota
- `usuarioId` (number, requerido): ID del usuario propietario
- `fechaNacimiento` (string, opcional): Fecha de nacimiento de la mascota en formato YYYY-MM-DD
- `foto` (file, opcional): Foto de la mascota

**Respuesta:**
```json
{
  "id": 1,
  "nombre": "Nombre de la mascota",
  "genero": "Macho",
  "especie": "Perro",
  "usuarioId": 1,
  "fechaNacimiento": "2020-01-01",
  "foto": "/uploads/mascotas/nombre-archivo.jpg"
}
```

#### PUT /mascotas/{id}
Actualiza una mascota existente.

**Parámetros:**
- `nombre` (string, requerido): Nombre de la mascota
- `genero` (string, requerido): Género de la mascota (Macho/Hembra)
- `especie` (string, requerido): Especie de la mascota
- `fechaNacimiento` (string, opcional): Fecha de nacimiento de la mascota en formato YYYY-MM-DD
- `foto` (file, opcional): Foto de la mascota

**Respuesta:**
```json
{
  "id": 1,
  "nombre": "Nombre actualizado",
  "genero": "Hembra",
  "especie": "Gato",
  "usuarioId": 1,
  "fechaNacimiento": "2021-02-15",
  "foto": "/uploads/mascotas/nuevo-archivo.jpg"
}
```

#### GET /mascotas/{id}
Obtiene una mascota específica.

**Respuesta:**
```json
{
  "id": 1,
  "nombre": "Nombre de la mascota",
  "genero": "Macho",
  "especie": "Perro",
  "usuarioId": 1,
  "fechaNacimiento": "2020-01-01",
  "foto": "/uploads/mascotas/nombre-archivo.jpg"
}
```

#### DELETE /mascotas/{id}
Elimina una mascota específica.

**Respuesta:**
- 204 No Content si la eliminación fue exitosa
- 404 Not Found si la mascota no existe
