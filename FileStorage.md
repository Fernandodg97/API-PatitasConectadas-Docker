# Manejo de Archivos en Patitas Conectadas

## Descripción General

Las imágenes del sistema se almacenan en **Cloudinary**, un servicio de almacenamiento en la nube. Esto garantiza persistencia entre despliegues y elimina la dependencia del sistema de archivos local del servidor.

## Configuración

Las siguientes variables de entorno son necesarias:

```env
CLOUDINARY_CLOUD_NAME=tu_cloud_name
CLOUDINARY_API_KEY=tu_api_key
CLOUDINARY_API_SECRET=tu_api_secret
```

### Límites
- Tamaño máximo de archivo: **10MB**
- Tipos de archivo permitidos: imágenes (`image/jpeg`, `image/png`, `image/gif`, `image/webp`)

## Recursos que admiten imagen

| Recurso | Campo | Endpoint de subida |
|---|---|---|
| Posts | `img` | `POST /posts`, `PUT /posts/{id}` |
| Mascotas | `foto` | `POST /usuarios/{id}/mascotas`, `PUT /usuarios/{id}/mascotas/{mascotaId}` |
| Perfiles | `img` | `POST /perfiles`, `PUT /usuarios/{id}/perfiles` |
| Comentarios | `img` | `POST /posts/{postId}/comentarios`, `PUT /comentarios/{id}` |

## Endpoints con subida de imagen

Todos los endpoints que aceptan imagen usan `multipart/form-data`.

### Crear post con imagen
```http
POST /posts
Content-Type: multipart/form-data

contenido: string (requerido)
creadorId: number (requerido)
grupoId: number (opcional)
imagen: file (opcional)
```

**Respuesta:**
```json
{
  "id": 1,
  "contenido": "Hoy adopté un perrito",
  "img": "https://res.cloudinary.com/<cloud>/image/upload/v.../nombre.jpg",
  "creadorId": 1,
  "fecha": "2024-03-20T15:30:00"
}
```

### Actualizar post con imagen
```http
PUT /posts/{id}
Content-Type: multipart/form-data

contenido: string (requerido)
grupoId: number (opcional)
imagen: file (opcional)
```

**Notas:**
- Si se sube una nueva imagen, la anterior se elimina automáticamente de Cloudinary.
- Si no se envía imagen, la imagen anterior se conserva.

## Ejemplo de uso desde el frontend

```javascript
const formData = new FormData();
formData.append('contenido', 'Mi nuevo post');
formData.append('creadorId', 1);
formData.append('imagen', archivoImagen); // objeto File

const response = await fetch('/posts', {
  method: 'POST',
  headers: { Authorization: `Bearer ${token}` },
  body: formData
});
const data = await response.json();
// data.img contiene la URL pública de Cloudinary
```

## Manejo de Errores

| Código | Causa |
|---|---|
| `400 Bad Request` | Archivo vacío, tipo no permitido o tamaño excede el límite |
| `404 Not Found` | Usuario, grupo o recurso no encontrado |
| `500 Internal Server Error` | Error al subir o eliminar imagen en Cloudinary |

**Ejemplo de respuesta de error:**
```json
{
  "error": "Solo se permiten archivos de imagen"
}
```

## Comportamiento al eliminar

Al eliminar un post, mascota o perfil, la imagen asociada se elimina automáticamente de Cloudinary para evitar acumulación de archivos huérfanos.
