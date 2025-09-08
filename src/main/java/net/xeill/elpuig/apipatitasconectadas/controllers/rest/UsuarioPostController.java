package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import net.xeill.elpuig.apipatitasconectadas.controllers.dto.UsuarioPostModelDtoRequest;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.UsuarioPostModelDtoResponse;
import net.xeill.elpuig.apipatitasconectadas.services.UsuarioPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las relaciones entre usuarios y posts.
 * Proporciona endpoints para crear, consultar y eliminar las relaciones
 * entre usuarios y posts en el sistema.
 * Todas las respuestas son encapsuladas en objetos ResponseEntity para un manejo
 * consistente de la comunicación HTTP.
 */
@RestController
@RequestMapping("/usuario-post")
public class UsuarioPostController {

    @Autowired
    private UsuarioPostService usuarioPostService;

    /**
     * Obtiene todas las relaciones usuario-post existentes en el sistema.
     * @return ResponseEntity con lista de relaciones usuario-post en formato DTO
     * @see UsuarioPostModelDtoResponse
     */
    @GetMapping
    public ResponseEntity<List<UsuarioPostModelDtoResponse>> getAllUsuarioPosts() {
        List<UsuarioPostModelDtoResponse> usuarioPosts = usuarioPostService.getAllUsuarioPosts();
        return new ResponseEntity<>(usuarioPosts, HttpStatus.OK);
    }

    /**
     * Obtiene una relación usuario-post específica por su ID.
     * @param id ID de la relación usuario-post a buscar
     * @return ResponseEntity con la relación encontrada
     * @see UsuarioPostModelDtoResponse
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioPostModelDtoResponse> getUsuarioPostById(@PathVariable Long id) {
        UsuarioPostModelDtoResponse usuarioPost = usuarioPostService.getUsuarioPostById(id);
        return new ResponseEntity<>(usuarioPost, HttpStatus.OK);
    }

    /**
     * Obtiene todas las relaciones usuario-post de un usuario específico.
     * @param usuarioId ID del usuario cuyas relaciones se quieren obtener
     * @return ResponseEntity con lista de relaciones del usuario en formato DTO
     * @see UsuarioPostModelDtoResponse
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<UsuarioPostModelDtoResponse>> getUsuarioPostsByUserId(@PathVariable Long usuarioId) {
        List<UsuarioPostModelDtoResponse> usuarioPosts = usuarioPostService.getUsuarioPostsByUserId(usuarioId);
        return new ResponseEntity<>(usuarioPosts, HttpStatus.OK);
    }

    /**
     * Obtiene todas las relaciones usuario-post de un post específico.
     * @param postId ID del post cuyas relaciones se quieren obtener
     * @return ResponseEntity con lista de relaciones del post en formato DTO
     * @see UsuarioPostModelDtoResponse
     */
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<UsuarioPostModelDtoResponse>> getUsuarioPostsByPostId(@PathVariable Long postId) {
        List<UsuarioPostModelDtoResponse> usuarioPosts = usuarioPostService.getUsuarioPostsByPostId(postId);
        return new ResponseEntity<>(usuarioPosts, HttpStatus.OK);
    }

    /**
     * Crea una nueva relación usuario-post.
     * @param request DTO con los datos de la relación a crear
     * @return ResponseEntity con la relación creada (201 CREATED)
     * @see UsuarioPostModelDtoRequest
     * @see UsuarioPostModelDtoResponse
     */
    @PostMapping
    public ResponseEntity<UsuarioPostModelDtoResponse> createUsuarioPost(@RequestBody UsuarioPostModelDtoRequest request) {
        UsuarioPostModelDtoResponse newUsuarioPost = usuarioPostService.createUsuarioPost(request);
        return new ResponseEntity<>(newUsuarioPost, HttpStatus.CREATED);
    }

    /**
     * Elimina una relación usuario-post específica.
     * @param id ID de la relación a eliminar
     * @return ResponseEntity NO_CONTENT (204) si se eliminó correctamente
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuarioPost(@PathVariable Long id) {
        usuarioPostService.deleteUsuarioPost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Elimina todas las relaciones usuario-post de un usuario específico.
     * @param usuarioId ID del usuario cuyas relaciones se quieren eliminar
     * @return ResponseEntity NO_CONTENT (204) si se eliminaron correctamente
     */
    @DeleteMapping("/usuario/{usuarioId}")
    public ResponseEntity<Void> deleteAllUsuarioPostsByUserId(@PathVariable Long usuarioId) {
        usuarioPostService.deleteAllUsuarioPostsByUserId(usuarioId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Elimina todas las relaciones usuario-post de un post específico.
     * @param postId ID del post cuyas relaciones se quieren eliminar
     * @return ResponseEntity NO_CONTENT (204) si se eliminaron correctamente
     */
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Void> deleteAllUsuarioPostsByPostId(@PathVariable Long postId) {
        usuarioPostService.deleteAllUsuarioPostsByPostId(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
} 