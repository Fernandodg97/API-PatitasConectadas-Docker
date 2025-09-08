package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.xeill.elpuig.apipatitasconectadas.controllers.dto.NotificacionesModelDtoRequest;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.NotificacionesModelDtoResponse;
import net.xeill.elpuig.apipatitasconectadas.models.NotificacionesModel;
import net.xeill.elpuig.apipatitasconectadas.services.NotificacionesService;

/**
 * Controlador REST para gestionar operaciones relacionadas con notificaciones.
 * Proporciona endpoints para crear, leer, actualizar y eliminar notificaciones.
 * Todas las respuestas son encapsuladas en objetos ResponseEntity para un manejo
 * consistente de la comunicación HTTP.
 */
@RestController
@RequestMapping("/notificaciones")
public class NotificacionesController {

    @Autowired
    private NotificacionesService notificacionesService;

    /**
     * Obtiene todas las notificaciones existentes en el sistema
     * @return ResponseEntity con lista de notificaciones en formato DTO o mensaje de error
     */
    @GetMapping
    public ResponseEntity<?> getAllNotificaciones() {
        try {
            // NOTA: Esta implementación asume que el modelo NotificacionesModel tiene getters implementados
            // Si aún no los tiene, se deben implementar primero
            List<NotificacionesModelDtoResponse> notificaciones = notificacionesService.getAllNotificaciones().stream()
                .map(NotificacionesModelDtoResponse::new)
                .collect(Collectors.toList());
            return ResponseEntity.ok(notificaciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener las notificaciones: " + e.getMessage());
        }
    }

    /**
     * Obtiene una notificación específica por su ID
     * @param id ID de la notificación a buscar
     * @return ResponseEntity con la notificación encontrada o mensaje de error
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getNotificacionById(@PathVariable Long id) {
        try {
            Optional<NotificacionesModel> notificacionOptional = notificacionesService.getNotificacionById(id);
            
            if (notificacionOptional.isPresent()) {
                NotificacionesModel notificacion = notificacionOptional.get();
                return ResponseEntity.ok(new NotificacionesModelDtoResponse(notificacion));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Notificación no encontrada con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener la notificación: " + e.getMessage());
        }
    }

    /**
     * Crea una nueva notificación en el sistema
     * @param notificacionDto Datos de la notificación en formato DTO
     * @return ResponseEntity con la notificación creada o mensaje de error
     */
    @PostMapping
    public ResponseEntity<?> createNotificacion(@RequestBody NotificacionesModelDtoRequest notificacionDto) {
        try {
            // NOTA: Esta implementación asume que el modelo NotificacionesModel tiene setters implementados
            // y que el método toDomain() del DTO está completo
            NotificacionesModel notificacion = notificacionDto.toDomain();
            NotificacionesModel savedNotificacion = notificacionesService.saveNotificacion(notificacion);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new NotificacionesModelDtoResponse(savedNotificacion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al crear la notificación: " + e.getMessage());
        }
    }

    /**
     * Actualiza una notificación existente
     * @param id ID de la notificación a actualizar
     * @param notificacionDto Datos actualizados de la notificación
     * @return ResponseEntity con la notificación actualizada o mensaje de error
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNotificacion(@PathVariable Long id, 
                                             @RequestBody NotificacionesModelDtoRequest notificacionDto) {
        try {
            // NOTA: Esta implementación asume que el modelo NotificacionesModel tiene setters implementados
            // y que el método toDomain() del DTO está completo
            
            // Verificar si la notificación existe
            if (!notificacionesService.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Notificación no encontrada con ID: " + id);
            }
            
            NotificacionesModel notificacion = notificacionDto.toDomain();
            notificacion.setId(id); // Asegurar que el ID sea el correcto
            
            NotificacionesModel updatedNotificacion = notificacionesService.updateNotificacion(notificacion);
            return ResponseEntity.ok(new NotificacionesModelDtoResponse(updatedNotificacion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al actualizar la notificación: " + e.getMessage());
        }
    }

    /**
     * Elimina una notificación existente
     * @param id ID de la notificación a eliminar
     * @return ResponseEntity con mensaje de confirmación o error
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotificacion(@PathVariable Long id) {
        try {
            if (!notificacionesService.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Notificación no encontrada con ID: " + id);
            }
            
            notificacionesService.deleteNotificacion(id);
            return ResponseEntity.ok("Notificación eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al eliminar la notificación: " + e.getMessage());
        }
    }
} 