package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.xeill.elpuig.apipatitasconectadas.controllers.dto.SeguidoModelDtoRequest;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.SeguidoModelDtoResponse;
import net.xeill.elpuig.apipatitasconectadas.models.SeguidoModel;
import net.xeill.elpuig.apipatitasconectadas.services.SeguidoService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para gestionar las relaciones de seguimiento entre usuarios.
 * Proporciona endpoints para ver, crear y eliminar relaciones de seguimiento.
 * Todas las respuestas son encapsuladas en objetos ResponseEntity para un manejo
 * consistente de la comunicación HTTP.
 */
@RestController
@RequestMapping("/usuarios/{usuarioId}/seguidos")
public class SeguidoController {

    @Autowired
    private SeguidoService seguidoService;

    /**
     * Obtiene todos los usuarios que sigue un usuario específico
     * @param usuarioId ID del usuario cuyas relaciones de seguimiento se buscan
     * @return ResponseEntity con la lista de relaciones de seguimiento o mensaje de error
     */
    @GetMapping
    public ResponseEntity<?> obtenerSeguidos(@PathVariable Long usuarioId) {
        try {
            List<SeguidoModelDtoResponse> seguidos = seguidoService.obtenerSeguidos(usuarioId).stream()
                .map(SeguidoModelDtoResponse::new)
                .collect(Collectors.toList());
            return ResponseEntity.ok(seguidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener los seguidos: " + e.getMessage());
        }
    }

    /**
     * Crea una relación de seguimiento entre dos usuarios basada en sus IDs
     * @param usuarioId ID del usuario que realiza el seguimiento
     * @param usuarioASeguirId ID del usuario que será seguido
     * @return ResponseEntity con la relación creada o mensaje de error
     */
    @PostMapping("/{usuarioASeguirId}")
    public ResponseEntity<?> seguirUsuario(@PathVariable Long usuarioId, @PathVariable Long usuarioASeguirId) {
        try {
            SeguidoModel nuevo = seguidoService.seguirUsuario(usuarioId, usuarioASeguirId);
            return ResponseEntity.ok(new SeguidoModelDtoResponse(nuevo));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al seguir usuario: " + e.getMessage());
        }
    }

    /**
     * Método alternativo para crear una relación de seguimiento utilizando un DTO
     * @param usuarioId ID del usuario que realiza el seguimiento
     * @param request DTO con los datos de la relación de seguimiento
     * @return ResponseEntity con la relación creada o mensaje de error
     */
    @PostMapping
    public ResponseEntity<?> seguirUsuarioConDto(@PathVariable Long usuarioId, 
                                              @RequestBody SeguidoModelDtoRequest request) {
        try {
            // Convertir DTO a modelo
            SeguidoModel seguido = request.toDomain();
            
            // El usuarioId de la ruta tiene prioridad sobre el del DTO
            seguido.setUsuarioQueSigueId(usuarioId);
            
            SeguidoModel nuevo = seguidoService.seguirUsuario(
                seguido.getUsuarioQueSigueId(), 
                seguido.getUsuarioQueEsSeguidoId());
                
            return ResponseEntity.ok(new SeguidoModelDtoResponse(nuevo));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al seguir usuario: " + e.getMessage());
        }
    }

    /**
     * Elimina una relación de seguimiento entre dos usuarios
     * @param usuarioId ID del usuario que deja de seguir
     * @param usuarioASeguirId ID del usuario que dejará de ser seguido
     * @return ResponseEntity con mensaje de confirmación o error
     */
    @DeleteMapping("/{usuarioASeguirId}")
    public ResponseEntity<?> dejarDeSeguir(@PathVariable Long usuarioId, @PathVariable Long usuarioASeguirId) {
        try {
            boolean eliminado = seguidoService.dejarDeSeguir(usuarioId, usuarioASeguirId);
            if (eliminado) {
                return ResponseEntity.ok("Has dejado de seguir al usuario con ID: " + usuarioASeguirId);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró una relación de seguimiento.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al dejar de seguir: " + e.getMessage());
        }
    }
}
