package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import net.xeill.elpuig.apipatitasconectadas.controllers.dto.UsuarioEventoModelDtoRequest;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.UsuarioEventoModelDtoResponse;
import net.xeill.elpuig.apipatitasconectadas.models.EventoModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import net.xeill.elpuig.apipatitasconectadas.models.UsuarioEventoModel;
import net.xeill.elpuig.apipatitasconectadas.services.EventoService;
import net.xeill.elpuig.apipatitasconectadas.services.UserService;
import net.xeill.elpuig.apipatitasconectadas.services.UsuarioEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para gestionar las relaciones entre usuarios y eventos.
 * Proporciona endpoints para crear, consultar y eliminar las relaciones
 * entre usuarios y eventos en el sistema.
 * Las relaciones pueden ser de dos tipos: CREADOR o ASISTENTE.
 * Todas las respuestas son encapsuladas en objetos ResponseEntity para un manejo
 * consistente de la comunicación HTTP.
 */
@RestController
@RequestMapping("/usuario-evento")
public class UsuarioEventoController {

    @Autowired
    private UsuarioEventoService usuarioEventoService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventoService eventoService;

    /**
     * Obtiene todas las relaciones usuario-evento existentes en el sistema.
     * @return ResponseEntity con lista de relaciones usuario-evento en formato DTO
     * @see UsuarioEventoModelDtoResponse
     */
    @GetMapping
    public ResponseEntity<List<UsuarioEventoModelDtoResponse>> getAllUsuarioEventos() {
        List<UsuarioEventoModel> usuarioEventos = usuarioEventoService.getAllUsuarioEventos();
        List<UsuarioEventoModelDtoResponse> response = usuarioEventos.stream()
                .map(UsuarioEventoModelDtoResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene una relación usuario-evento específica por su ID.
     * @param id ID de la relación usuario-evento a buscar
     * @return ResponseEntity con la relación encontrada o NOT_FOUND si no existe
     * @see UsuarioEventoModelDtoResponse
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEventoModelDtoResponse> getUsuarioEventoById(@PathVariable Long id) {
        UsuarioEventoModel usuarioEvento = usuarioEventoService.getUsuarioEventoById(id);
        if (usuarioEvento != null) {
            return ResponseEntity.ok(new UsuarioEventoModelDtoResponse(usuarioEvento));
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Obtiene todas las relaciones usuario-evento para un usuario específico.
     * @param usuarioId ID del usuario cuyas relaciones se quieren obtener
     * @return ResponseEntity con lista de relaciones del usuario en formato DTO
     * @see UsuarioEventoModelDtoResponse
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<UsuarioEventoModelDtoResponse>> getUsuarioEventosByUsuarioId(@PathVariable Long usuarioId) {
        List<UsuarioEventoModel> usuarioEventos = usuarioEventoService.getUsuarioEventosByUsuarioId(usuarioId);
        List<UsuarioEventoModelDtoResponse> response = usuarioEventos.stream()
                .map(UsuarioEventoModelDtoResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene todas las relaciones usuario-evento para un evento específico.
     * @param eventoId ID del evento cuyas relaciones se quieren obtener
     * @return ResponseEntity con lista de relaciones del evento en formato DTO
     * @see UsuarioEventoModelDtoResponse
     */
    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<UsuarioEventoModelDtoResponse>> getUsuarioEventosByEventoId(@PathVariable Long eventoId) {
        List<UsuarioEventoModel> usuarioEventos = usuarioEventoService.getUsuarioEventosByEventoId(eventoId);
        List<UsuarioEventoModelDtoResponse> response = usuarioEventos.stream()
                .map(UsuarioEventoModelDtoResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Crea una nueva relación usuario-evento.
     * Valida la existencia del usuario y el evento antes de crear la relación.
     * @param request DTO con los datos de la relación a crear
     * @return ResponseEntity con la relación creada (201 CREATED) o BAD_REQUEST si hay error
     * @see UsuarioEventoModelDtoRequest
     * @see UsuarioEventoModelDtoResponse
     */
    @PostMapping
    public ResponseEntity<?> createUsuarioEvento(@RequestBody UsuarioEventoModelDtoRequest request) {
        UserModel usuario = userService.getUserById(request.getUsuarioId());
        EventoModel evento = eventoService.getEventoById(request.getEventoId());

        if (usuario == null || evento == null) {
            return ResponseEntity.badRequest().body("Usuario o Evento no encontrado");
        }

        // Verificar si ya existe una relación entre el usuario y el evento
        List<UsuarioEventoModel> existingRelations = usuarioEventoService.getUsuarioEventosByUsuarioId(request.getUsuarioId());
        boolean alreadyHasRole = existingRelations.stream()
                .anyMatch(rel -> rel.getEvento().getId().equals(request.getEventoId()));

        if (alreadyHasRole) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El usuario ya tiene un rol asignado en este evento. Use el endpoint PUT para actualizar el rol.");
        }

        UsuarioEventoModel usuarioEvento = request.toDomain(evento, usuario);
        UsuarioEventoModel savedUsuarioEvento = usuarioEventoService.saveUsuarioEvento(usuarioEvento);

        if (savedUsuarioEvento != null) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new UsuarioEventoModelDtoResponse(savedUsuarioEvento));
        }
        return ResponseEntity.badRequest().body("Error al crear la relación usuario-evento");
    }

    /**
     * Elimina una relación usuario-evento específica por su ID.
     * @param id ID de la relación a eliminar
     * @return ResponseEntity NO_CONTENT (204) si se eliminó correctamente, o NOT_FOUND si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuarioEvento(@PathVariable Long id) {
        UsuarioEventoModel usuarioEvento = usuarioEventoService.getUsuarioEventoById(id);
        if (usuarioEvento != null) {
            usuarioEventoService.deleteUsuarioEvento(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Elimina todas las relaciones usuario-evento de un usuario específico.
     * @param usuarioId ID del usuario cuyas relaciones se quieren eliminar
     * @return ResponseEntity NO_CONTENT (204) si se eliminaron correctamente
     */
    @DeleteMapping("/usuario/{usuarioId}")
    public ResponseEntity<Void> deleteUsuarioEventosByUsuarioId(@PathVariable Long usuarioId) {
        usuarioEventoService.deleteUsuarioEventosByUsuarioId(usuarioId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Elimina todas las relaciones usuario-evento de un evento específico.
     * @param eventoId ID del evento cuyas relaciones se quieren eliminar
     * @return ResponseEntity NO_CONTENT (204) si se eliminaron correctamente
     */
    @DeleteMapping("/evento/{eventoId}")
    public ResponseEntity<Void> deleteUsuarioEventosByEventoId(@PathVariable Long eventoId) {
        usuarioEventoService.deleteUsuarioEventosByEventoId(eventoId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Elimina una relación usuario-evento específica entre un usuario y un evento.
     * @param usuarioId ID del usuario de la relación
     * @param eventoId ID del evento de la relación
     * @return ResponseEntity NO_CONTENT (204) si se eliminó correctamente
     */
    @DeleteMapping("/usuario/{usuarioId}/evento/{eventoId}")
    public ResponseEntity<Void> deleteUsuarioEventoByUsuarioIdAndEventoId(
            @PathVariable Long usuarioId,
            @PathVariable Long eventoId) {
        usuarioEventoService.deleteUsuarioEventoByUsuarioIdAndEventoId(usuarioId, eventoId);
        return ResponseEntity.noContent().build();
    }
} 