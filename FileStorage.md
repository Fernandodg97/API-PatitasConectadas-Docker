# Manejo de Archivos en Patitas Conectadas

## Descripción General
El sistema permite la carga y gestión de imágenes para las publicaciones (posts). Las imágenes se almacenan en el servidor y se sirven a través de URLs públicas.

## Configuración
- Tamaño máximo de archivo: 10MB
- Tipos de archivo permitidos: Imágenes (image/*)
- Directorio de almacenamiento: `uploads/posts/YYYY/MM/`
- URL base para acceder a las imágenes: `/uploads/posts/YYYY/MM/`

## Estructura de Directorios
```
uploads/
  └── posts/
      └── YYYY/
          └── MM/
              └── [nombre-archivo-unico].[extension]
```

## Endpoints

### Crear Post con Imagen
```http
POST /posts
Content-Type: multipart/form-data

Parámetros:
- contenido: string (requerido)
- creadorId: number (requerido)
- grupoId: number (opcional)
- imagen: file (opcional)

Respuesta exitosa (201 Created):
{
  "id": number,
  "contenido": string,
  "fecha": string,
  "img": string,  // Ruta relativa de la imagen
  "creador": {
    "id": number,
    "nombre": string
  },
  "grupo": {
    "id": number,
    "nombre": string
  }
}
```

### Actualizar Post con Imagen
```http
PUT /posts/{id}
Content-Type: multipart/form-data

Parámetros:
- contenido: string (requerido)
- grupoId: number (opcional)
- imagen: file (opcional)

Respuesta exitosa (200 OK):
{
  "id": number,
  "contenido": string,
  "fecha": string,
  "img": string,  // Ruta relativa de la imagen
  "creador": {
    "id": number,
    "nombre": string
  },
  "grupo": {
    "id": number,
    "nombre": string
  }
}
```

## Ejemplos de Uso

### Frontend (JavaScript)
```javascript
// Crear un nuevo post con imagen
const formData = new FormData();
formData.append('contenido', 'Contenido del post');
formData.append('creadorId', 1);
formData.append('grupoId', 2);
formData.append('imagen', archivoImagen); // archivoImagen es un objeto File

fetch('/posts', {
  method: 'POST',
  body: formData
})
.then(response => response.json())
.then(data => console.log(data));

// Actualizar un post con nueva imagen
const formData = new FormData();
formData.append('contenido', 'Contenido actualizado');
formData.append('imagen', nuevaImagen);

fetch('/posts/1', {
  method: 'PUT',
  body: formData
})
.then(response => response.json())
.then(data => console.log(data));
```

### Frontend (HTML)
```html
<form id="postForm">
  <textarea name="contenido" required></textarea>
  <input type="file" name="imagen" accept="image/*">
  <button type="submit">Publicar</button>
</form>

<script>
document.getElementById('postForm').onsubmit = async (e) => {
  e.preventDefault();
  const formData = new FormData(e.target);
  formData.append('creadorId', 1); // ID del usuario actual
  
  const response = await fetch('/posts', {
    method: 'POST',
    body: formData
  });
  const data = await response.json();
  console.log(data);
};
</script>
```

## Manejo de Errores

### Errores Comunes
- `400 Bad Request`: 
  - Archivo vacío
  - Tipo de archivo no permitido
  - Tamaño de archivo excede el límite
- `404 Not Found`: 
  - Usuario o grupo no encontrado
- `500 Internal Server Error`: 
  - Error al procesar la imagen
  - Error al guardar el archivo

### Ejemplo de Respuesta de Error
```json
{
  "error": "Solo se permiten archivos de imagen"
}
```

## Consideraciones de Seguridad
1. Se validan los tipos MIME de los archivos
2. Se generan nombres únicos para evitar colisiones
3. Se organizan los archivos por año/mes para mejor gestión
4. Se eliminan las imágenes antiguas al actualizar o eliminar posts

## Buenas Prácticas
1. Comprimir las imágenes antes de subirlas
2. Usar formatos web optimizados (JPEG, PNG, WebP)
3. Implementar validación de tamaño en el frontend
4. Mostrar previsualización de la imagen antes de subir
5. Manejar errores y mostrar mensajes al usuario
