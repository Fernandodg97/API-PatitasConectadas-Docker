package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.xeill.elpuig.apipatitasconectadas.controllers.dto.ComentarioModelDtoRequest;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.ComentarioModelDtoResponse;
import net.xeill.elpuig.apipatitasconectadas.models.ComentarioModel;
import net.xeill.elpuig.apipatitasconectadas.models.PostModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import net.xeill.elpuig.apipatitasconectadas.repositories.ComentarioRepository;
import net.xeill.elpuig.apipatitasconectadas.services.ComentarioService;
import net.xeill.elpuig.apipatitasconectadas.services.PostService;
import net.xeill.elpuig.apipatitasconectadas.services.UserService;

import jakarta.persistence.EntityNotFoundException;

/**
 * Controlador REST para gestionar operaciones relacionadas con comentarios.
 * Proporciona endpoints para crear, leer, actualizar y eliminar comentarios
 * asociados a publicaciones (posts).
 * Todas las respuestas son encapsuladas en objetos ResponseEntity para un manejo
 * consistente de la comunicación HTTP.
 */
@RestController
@RequestMapping
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private PostService postService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ComentarioRepository comentarioRepository;

    /**
     * Obtiene todos los comentarios asociados a una publicación específica
     * @param postId ID de la publicación cuyos comentarios se quieren obtener
     * @return ResponseEntity con lista de comentarios en formato DTO o mensaje de error
     */
    @GetMapping("/posts/{postId}/comentarios")
    public ResponseEntity<?> getComentariosByPostId(@PathVariable Long postId) {
        try {
            List<ComentarioModel> comentarios = comentarioRepository.findByPostId(postId);
            List<ComentarioModelDtoResponse> comentariosDto = comentarios.stream()
                .map(ComentarioModelDtoResponse::new)
                .collect(Collectors.toList());
            return new ResponseEntity<>(comentariosDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Crea un nuevo comentario en una publicación específica
     * @param postId ID de la publicación donde se creará el comentario
     * @param comentarioDto Datos del comentario en formato DTO
     * @return ResponseEntity con el comentario creado o mensaje de error
     */
    @PostMapping("/posts/{postId}/comentarios")
    public ResponseEntity<?> crearComentario(
            @PathVariable Long postId,
            @RequestBody ComentarioModelDtoRequest comentarioDto) {
        try {
            // Obtener post y usuario
            PostModel post = postService.getById(postId);
            UserModel creador = userService.getById(comentarioDto.getCreadorId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            
            // Convertir DTO a modelo y guardar
            ComentarioModel comentario = comentarioDto.toDomain(post, creador);
            ComentarioModel guardado = comentarioService.saveComentario(comentario);
            
            return new ResponseEntity<>(new ComentarioModelDtoResponse(guardado), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene un comentario específico por su ID
     * @param id ID del comentario a buscar
     * @return ResponseEntity con el comentario encontrado o mensaje de error
     */
    @GetMapping("/comentarios/{id}")
    public ResponseEntity<?> getComentarioById(@PathVariable Long id) {
        try {
            Optional<ComentarioModel> comentario = comentarioService.getComentarioById(id);
            if (comentario.isPresent()) {
                return new ResponseEntity<>(new ComentarioModelDtoResponse(comentario.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of("error", "Comentario no encontrado"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Actualiza un comentario existente
     * @param id ID del comentario a actualizar
     * @param comentarioDto Datos actualizados del comentario
     * @return ResponseEntity con el comentario actualizado o mensaje de error
     */
    @PutMapping("/comentarios/{id}")
    public ResponseEntity<?> actualizarComentario(
            @PathVariable Long id,
            @RequestBody ComentarioModelDtoRequest comentarioDto) {
        try {
            Optional<ComentarioModel> optionalComentario = comentarioService.getComentarioById(id);
            
            if (optionalComentario.isEmpty()) {
                return new ResponseEntity<>(Map.of("error", "Comentario no encontrado"), HttpStatus.NOT_FOUND);
            }
            
            ComentarioModel comentario = optionalComentario.get();
            
            // Actualizar solo los campos proporcionados
            if (comentarioDto.getContenido() != null) {
                comentario.setContenido(comentarioDto.getContenido());
            }
            if (comentarioDto.getFecha() != null) {
                comentario.setFecha(comentarioDto.getFecha());
            }
            if (comentarioDto.getImg() != null) {
                comentario.setImg(comentarioDto.getImg());
            }
            
            ComentarioModel actualizado = comentarioService.saveComentario(comentario);
            return new ResponseEntity<>(new ComentarioModelDtoResponse(actualizado), HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Elimina un comentario existente
     * @param id ID del comentario a eliminar
     * @return ResponseEntity con mensaje de confirmación o error
     */
    @DeleteMapping("/comentarios/{id}")
    public ResponseEntity<?> eliminarComentario(@PathVariable Long id) {
        try {
            Optional<ComentarioModel> comentario = comentarioService.getComentarioById(id);
            
            if (comentario.isEmpty()) {
                return new ResponseEntity<>(Map.of("error", "Comentario no encontrado"), HttpStatus.NOT_FOUND);
            }
            
            comentarioService.deleteComentario(id);
            return new ResponseEntity<>(Map.of("mensaje", "Comentario eliminado con éxito"), HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
