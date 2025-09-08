package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import net.xeill.elpuig.apipatitasconectadas.controllers.dto.PostModelDtoRequest;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.PostModelDtoResponse;
import net.xeill.elpuig.apipatitasconectadas.models.GrupoModel;
import net.xeill.elpuig.apipatitasconectadas.models.PostModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import net.xeill.elpuig.apipatitasconectadas.services.FileStorageService;
import net.xeill.elpuig.apipatitasconectadas.services.GrupoService;
import net.xeill.elpuig.apipatitasconectadas.services.PostService;
import net.xeill.elpuig.apipatitasconectadas.services.UserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador REST para gestionar operaciones relacionadas con publicaciones (posts).
 * Proporciona endpoints para crear, leer, actualizar y eliminar publicaciones,
 * así como para buscar publicaciones por usuario, grupo, contenido o rango de fechas.
 * Todas las respuestas son encapsuladas en objetos ResponseEntity para un manejo
 * consistente de la comunicación HTTP.
 */
@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private GrupoService grupoService;
    
    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Obtiene publicaciones con posibilidad de filtrar por contenido o rango de fechas
     * @param contenido Texto para filtrar por contenido (opcional)
     * @param fechaInicio Fecha inicial para filtrar por rango (opcional)
     * @param fechaFin Fecha final para filtrar por rango (opcional)
     * @return ResponseEntity con lista de publicaciones en formato DTO o mensaje de error
     */
    @GetMapping
    public ResponseEntity<?> getPosts(
            @RequestParam(required = false) String contenido,
            @RequestParam(required = false) LocalDateTime fechaInicio,
            @RequestParam(required = false) LocalDateTime fechaFin) {

        try {
            List<PostModel> posts;
            
            // Si se proporciona contenido, se hace una búsqueda por contenido
            if (contenido != null) {
                posts = postService.searchPostsByContent(contenido);
            }
            // Si se proporciona un rango de fechas, se filtran las publicaciones entre esas fechas
            else if (fechaInicio != null && fechaFin != null) {
                posts = postService.getPostsByDateRange(fechaInicio, fechaFin);
            }
            // Si no hay filtros, se devuelven todas las publicaciones
            else {
                posts = postService.getPosts();
            }
            
            // Convertir a DTOs para la respuesta
            List<PostModelDtoResponse> postsDto = posts.stream()
                .map(PostModelDtoResponse::new)
                .collect(Collectors.toList());
                
            return new ResponseEntity<>(postsDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Crea una nueva publicación en el sistema
     * @param contenido Contenido de la publicación
     * @param creadorId ID del creador de la publicación
     * @param grupoId ID del grupo asociado a la publicación (opcional)
     * @param imagen Imagen asociada a la publicación (opcional)
     * @return ResponseEntity con la publicación creada o mensaje de error
     */
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> savePost(
            @RequestParam("contenido") String contenido,
            @RequestParam("creadorId") Long creadorId,
            @RequestParam(value = "grupoId", required = false) Long grupoId,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
        try {
            // Obtener el creador y grupo si existen
            UserModel creador = null;
            GrupoModel grupo = null;
            
            Optional<UserModel> optionalCreador = userService.getById(creadorId);
            if (optionalCreador.isEmpty()) {
                throw new EntityNotFoundException("Usuario creador no encontrado con ID: " + creadorId);
            }
            creador = optionalCreador.get();
            
            if (grupoId != null) {
                grupo = grupoService.getById(grupoId)
                    .orElseThrow(() -> new EntityNotFoundException("Grupo no encontrado con ID: " + grupoId));
            }
            
            // Crear el post
            PostModel post = new PostModel();
            post.setGrupo(grupo);
            post.setCreador(creador);
            post.setContenido(contenido);
            post.setFecha(LocalDateTime.now());
            
            // Manejar la imagen si se proporciona
            if (imagen != null && !imagen.isEmpty()) {
                String imagenPath = fileStorageService.storeFile(imagen);
                post.setImg(imagenPath);
            }
            
            // Guardar el post
            PostModel savedPost = postService.savePost(post);
            
            // Convertir a DTO para la respuesta
            PostModelDtoResponse response = new PostModelDtoResponse(savedPost);
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>(Map.of("error", "Error al procesar la imagen: " + e.getMessage()), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Error al crear el post: " + e.getMessage()), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene una publicación específica por su ID
     * @param id ID de la publicación a buscar
     * @return ResponseEntity con la publicación encontrada o mensaje de error
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        try {
            PostModel post = postService.getById(id);
            PostModelDtoResponse postDto = new PostModelDtoResponse(post);
            return new ResponseEntity<>(postDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Actualiza una publicación existente
     * @param id ID de la publicación a actualizar
     * @param contenido Contenido actualizado de la publicación
     * @param grupoId ID del grupo asociado a la publicación (opcional)
     * @param imagen Imagen actualizada de la publicación (opcional)
     * @return ResponseEntity con la publicación actualizada o mensaje de error
     */
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> updatePost(
            @PathVariable Long id,
            @RequestParam("contenido") String contenido,
            @RequestParam(value = "grupoId", required = false) Long grupoId,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
        try {
            // Obtener el post existente
            PostModel existingPost = postService.getById(id);
            
            // Obtener el grupo si se proporciona
            GrupoModel grupo = existingPost.getGrupo();
            if (grupoId != null) {
                grupo = grupoService.getById(grupoId)
                    .orElseThrow(() -> new EntityNotFoundException("Grupo no encontrado con ID: " + grupoId));
            }
            
            // Preparar el post para actualizar
            PostModel postToUpdate = new PostModel();
            postToUpdate.setId(id);
            postToUpdate.setCreador(existingPost.getCreador());
            postToUpdate.setGrupo(grupo);
            postToUpdate.setContenido(contenido);
            postToUpdate.setFecha(existingPost.getFecha());
            
            // Manejar la imagen si se proporciona
            if (imagen != null && !imagen.isEmpty()) {
                // Eliminar la imagen anterior si existe
                if (existingPost.getImg() != null) {
                    fileStorageService.deleteFile(existingPost.getImg());
                }
                // Guardar la nueva imagen
                String imagenPath = fileStorageService.storeFile(imagen);
                postToUpdate.setImg(imagenPath);
            } else {
                postToUpdate.setImg(existingPost.getImg());
            }
            
            // Actualizar el post
            PostModel updatedPost = postService.updateById(postToUpdate, id);
            
            // Convertir a DTO para la respuesta
            PostModelDtoResponse response = new PostModelDtoResponse(updatedPost);
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (ValidationException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity<>(Map.of("error", "Error al procesar la imagen: " + e.getMessage()), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Error al actualizar el post: " + e.getMessage()), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina una publicación existente
     * @param id ID de la publicación a eliminar
     * @return ResponseEntity con mensaje de confirmación o error
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try {
            // Obtener el post para eliminar su imagen si existe
            PostModel post = postService.getById(id);
            if (post.getImg() != null) {
                fileStorageService.deleteFile(post.getImg());
            }
            
            boolean deleted = postService.deletePost(id);
            if (deleted) {
                return new ResponseEntity<>(Map.of("mensaje", "Post eliminado correctamente"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of("error", "No se pudo eliminar el post"), 
                    HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Error al eliminar el post: " + e.getMessage()), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene todas las publicaciones realizadas por un usuario específico
     * @param userId ID del usuario cuyas publicaciones se quieren obtener
     * @return ResponseEntity con lista de publicaciones del usuario o mensaje de error
     */
    @GetMapping("/usuarios/{userId}/posts")
    public ResponseEntity<?> getPostsByUser(@PathVariable Long userId) {
        try {
            List<PostModel> posts = postService.getPostsByUser(userId);
            
            // Convertir a DTOs para la respuesta
            List<PostModelDtoResponse> postsDto = posts.stream()
                .map(PostModelDtoResponse::new)
                .collect(Collectors.toList());
                
            return new ResponseEntity<>(postsDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene todas las publicaciones realizadas en un grupo específico
     * @param grupoId ID del grupo cuyas publicaciones se quieren obtener
     * @return ResponseEntity con lista de publicaciones del grupo o mensaje de error
     */
    @GetMapping("/grupos/{grupoId}/posts")
    public ResponseEntity<?> getPostsByGrupo(@PathVariable Long grupoId) {
        try {
            List<PostModel> posts = postService.getPostsByGrupo(grupoId);
            
            // Convertir a DTOs para la respuesta
            List<PostModelDtoResponse> postsDto = posts.stream()
                .map(PostModelDtoResponse::new)
                .collect(Collectors.toList());
                
            return new ResponseEntity<>(postsDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
