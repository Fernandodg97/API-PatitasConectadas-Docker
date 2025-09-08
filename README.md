## Installation

Installation PostgreSQL

```bash
  sudo apt update

  sudo apt install -y postgresql

  systemctl status postgresql
```
New user

```bash
sudo -iu postgres createuser -P --interactive fernando
```
View:
```bash
Enter password for new role: *******
Enter it again: *******
Shall the new role be a superuser? (y/n) y
```

New database
```bash
createdb fernando -O fernando
```

Conexion

```bash
psql -U fernando -d postgres
```

External Conexion
```bash
psql -U fernando -d fernando -h localhost -p 5432
```

Installation pgAdmin

Install the public key for the repository (if not done previously):

```bash
curl -fsS https://www.pgadmin.org/static/packages_pgadmin_org.pub | sudo gpg --dearmor -o /usr/share/keyrings/packages-pgadmin-org.gpg
```

Create the repository configuration file:

```bash
sudo sh -c 'echo "deb [signed-by=/usr/share/keyrings/packages-pgadmin-org.gpg] https://ftp.postgresql.org/pub/pgadmin/pgadmin4/apt/$(lsb_release -cs) pgadmin4 main" > /etc/apt/sources.list.d/pgadmin4.list && apt update'
```
Install for both desktop and web modes:
```bash
sudo apt install pgadmin4
```
Install for desktop mode only:
```bash
sudo apt install pgadmin4-desktop
```
Install for web mode only: 
```bash
sudo apt install pgadmin4-web 
```
Configure the webserver, if you installed pgadmin4-web:
```bash
sudo /usr/pgadmin4/bin/setup-web.sh
```

# Documentación de la API Patitas Conectadas

Para la documentación completa de todas las rutas de la API con ejemplos JSON y explicaciones detalladas, consulte el archivo [API-DOCS.md](API-DOCS.md).

Para información sobre el manejo de archivos e imágenes en el sistema, incluyendo configuración, endpoints y ejemplos de uso, consulte el archivo [FileStorage.md](FileStorage.md).

# Documentación API - Módulo de Perfiles

## Descripción General
Este módulo permite gestionar los perfiles de usuarios en el sistema. Cada perfil está asociado a un usuario específico y contiene información personal como descripción, fecha de nacimiento e imagen.

## Endpoints

### 1. Obtener todos los perfiles
- **Método**: GET
- **URL**: `/perfiles`
- **Descripción**: Retorna una lista de todos los perfiles existentes en el sistema
- **Respuesta Exitosa**:
  ```json
  [
    {
      "id": 1,
      "usuario_id": 123,
      "descripcion": "Descripción del perfil",
      "fecha_nacimiento": "1990-01-01",
      "img": "url_imagen"
    }
  ]
  ```

### 2. Crear un nuevo perfil
- **Método**: POST
- **URL**: `/perfiles`
- **Descripción**: Crea un nuevo perfil en el sistema
- **Cuerpo de la Petición**:
  ```json
  {
    "usuario_id": 123,
    "descripcion": "Descripción del perfil",
    "fecha_nacimiento": "1990-01-01",
    "img": "url_imagen"
  }
  ```
- **Respuesta Exitosa** (201 Created):
  ```json
  {
    "id": 1,
    "usuario_id": 123,
    "descripcion": "Descripción del perfil",
    "fecha_nacimiento": "1990-01-01",
    "img": "url_imagen"
  }
  ```

### 3. Obtener perfil por ID de usuario
- **Método**: GET
- **URL**: `/usuarios/{id}/perfiles`
- **Descripción**: Obtiene el perfil asociado a un usuario específico

### 4. Actualizar perfil
- **Método**: POST
- **URL**: `/usuarios/{id}/perfiles`
- **Descripción**: Actualiza la información de un perfil existente

### 5. Eliminar perfil
- **Método**: DELETE
- **URL**: `/usuarios/{id}/perfiles`
- **Descripción**: Elimina un perfil del sistema

## Manejo de Imágenes

### 1. Crear Post con Imagen
- **Método**: POST
- **URL**: `/posts`
- **Content-Type**: multipart/form-data
- **Parámetros**:
  - contenido: string (requerido)
  - creadorId: number (requerido)
  - grupoId: number (opcional)
  - imagen: file (opcional)
- **Notas**:
  - Tipos de archivo permitidos: jpg, jpeg, png, gif
  - Tamaño máximo: 10MB
  - Las imágenes se almacenan en `uploads/posts/YYYY/MM/`
  - Las URLs son accesibles vía `/uploads/posts/YYYY/MM/nombre-archivo`

### 2. Actualizar Post con Imagen
- **Método**: PUT
- **URL**: `/posts/{id}`
- **Content-Type**: multipart/form-data
- **Parámetros**:
  - contenido: string (requerido)
  - grupoId: number (opcional)
  - imagen: file (opcional)
- **Notas**:
  - Al actualizar con nueva imagen, la anterior se elimina automáticamente
  - Mismos requisitos de formato y tamaño que en la creación

### 3. Eliminar Post con Imagen
- **Método**: DELETE
- **URL**: `/posts/{id}`
- **Descripción**: Elimina el post y su imagen asociada del sistema

## Modelo de Datos

### PerfilModel
```json
{
  "id": "Long",
  "usuario_id": "Long",
  "descripcion": "String",
  "fecha_nacimiento": "Date (formato: YYYY-MM-DD)",
  "img": "String (URL de la imagen)"
}
```

## Consideraciones Importantes
1. El campo `fecha_nacimiento` debe ser enviado en formato YYYY-MM-DD
2. El campo `img` debe contener la URL completa de la imagen
3. El `usuario_id` debe corresponder a un usuario existente en el sistema
4. Todos los endpoints requieren autenticación
5. Para el manejo de imágenes:
   - Se validan los tipos MIME de los archivos
   - Se generan nombres únicos para evitar colisiones
   - Se organizan los archivos por año/mes
   - Se eliminan las imágenes antiguas al actualizar o eliminar posts

## Manejo de Errores
- Los errores 500 indican problemas del servidor
- Los errores 404 indican que el recurso no fue encontrado
- Los errores 400 indican que la petición es inválida
- Para imágenes:
  - Error 400 si el archivo está vacío
  - Error 400 si el tipo de archivo no está permitido
  - Error 400 si el tamaño excede el límite