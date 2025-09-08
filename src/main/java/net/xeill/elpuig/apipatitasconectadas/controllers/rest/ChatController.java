package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.xeill.elpuig.apipatitasconectadas.controllers.dto.ChatModelDtoRequest;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.ChatModelDtoResponse;
import net.xeill.elpuig.apipatitasconectadas.models.ChatModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import net.xeill.elpuig.apipatitasconectadas.services.ChatService;
import net.xeill.elpuig.apipatitasconectadas.services.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controlador REST para gestionar operaciones relacionadas con el chat entre usuarios.
 * Proporciona endpoints para enviar, recibir y gestionar mensajes,
 * así como para obtener conversaciones entre usuarios y marcar mensajes como vistos.
 * Todas las respuestas son encapsuladas en objetos ResponseEntity para un manejo
 * consistente de la comunicación HTTP.
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;
    
    @Autowired
    private UserService userService;

    /**
     * Envía un nuevo mensaje de un usuario a otro
     * @param chatDto Datos del mensaje en formato DTO
     * @return ResponseEntity con el mensaje enviado o mensaje de error
     */
    @PostMapping("/enviar")
    public ResponseEntity<?> enviarMensaje(@RequestBody ChatModelDtoRequest chatDto) {
        try {
            UserModel emisor = userService.getById(chatDto.getEmisorId())
                .orElseThrow(() -> new RuntimeException("Usuario emisor no encontrado"));
            UserModel receptor = userService.getById(chatDto.getReceptorId())
                .orElseThrow(() -> new RuntimeException("Usuario receptor no encontrado"));
            
            ChatModel mensaje = chatService.enviarMensaje(
                chatDto.getEmisorId(), 
                chatDto.getReceptorId(), 
                chatDto.getContenido()
            );
            
            return new ResponseEntity<>(new ChatModelDtoResponse(mensaje), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene la conversación completa entre dos usuarios específicos
     * @param usuario1Id ID del primer usuario en la conversación
     * @param usuario2Id ID del segundo usuario en la conversación
     * @return ResponseEntity con lista de mensajes en formato DTO o mensaje de error
     */
    @GetMapping("/conversacion/{usuario1Id}/{usuario2Id}")
    public ResponseEntity<?> obtenerConversacion(
            @PathVariable Long usuario1Id,
            @PathVariable Long usuario2Id) {
        try {
            List<ChatModel> conversacion = chatService.obtenerConversacion(usuario1Id, usuario2Id);
            List<ChatModelDtoResponse> conversacionDto = conversacion.stream()
                .map(ChatModelDtoResponse::new)
                .collect(Collectors.toList());
                
            return new ResponseEntity<>(conversacionDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Marca como vistos todos los mensajes enviados por un usuario a otro
     * @param usuarioId ID del usuario receptor de los mensajes
     * @param otroUsuarioId ID del usuario emisor de los mensajes a marcar
     * @return ResponseEntity con mensaje de confirmación o error
     */
    @PutMapping("/marcar-vistos/{usuarioId}/{otroUsuarioId}")
    public ResponseEntity<?> marcarComoVistos(
            @PathVariable Long usuarioId,
            @PathVariable Long otroUsuarioId) {
        try {
            chatService.marcarComoVistos(usuarioId, otroUsuarioId);
            return new ResponseEntity<>(Map.of("message", "Mensajes marcados como vistos"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene todos los mensajes no vistos por un usuario específico
     * @param usuarioId ID del usuario cuyos mensajes no vistos se quieren obtener
     * @return ResponseEntity con lista de mensajes no vistos en formato DTO o mensaje de error
     */
    @GetMapping("/no-vistos/{usuarioId}")
    public ResponseEntity<?> obtenerMensajesNoVistos(@PathVariable Long usuarioId) {
        try {
            List<ChatModel> mensajes = chatService.obtenerMensajesNoVistos(usuarioId);
            List<ChatModelDtoResponse> mensajesDto = mensajes.stream()
                .map(ChatModelDtoResponse::new)
                .collect(Collectors.toList());
                
            return new ResponseEntity<>(mensajesDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene todos los mensajes enviados por un usuario específico
     * @param usuarioId ID del usuario cuyos mensajes enviados se quieren obtener
     * @return ResponseEntity con lista de mensajes enviados en formato DTO o mensaje de error
     */
    @GetMapping("/enviados/{usuarioId}")
    public ResponseEntity<?> obtenerMensajesEnviados(@PathVariable Long usuarioId) {
        try {
            List<ChatModel> mensajes = chatService.obtenerMensajesEnviados(usuarioId);
            List<ChatModelDtoResponse> mensajesDto = mensajes.stream()
                .map(ChatModelDtoResponse::new)
                .collect(Collectors.toList());
                
            return new ResponseEntity<>(mensajesDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene todos los mensajes recibidos por un usuario específico
     * @param usuarioId ID del usuario cuyos mensajes recibidos se quieren obtener
     * @return ResponseEntity con lista de mensajes recibidos en formato DTO o mensaje de error
     */
    @GetMapping("/recibidos/{usuarioId}")
    public ResponseEntity<?> obtenerMensajesRecibidos(@PathVariable Long usuarioId) {
        try {
            List<ChatModel> mensajes = chatService.obtenerMensajesRecibidos(usuarioId);
            List<ChatModelDtoResponse> mensajesDto = mensajes.stream()
                .map(ChatModelDtoResponse::new)
                .collect(Collectors.toList());
                
            return new ResponseEntity<>(mensajesDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Elimina toda la conversación entre dos usuarios específicos
     * @param usuario1Id ID del primer usuario en la conversación
     * @param usuario2Id ID del segundo usuario en la conversación
     * @return ResponseEntity con mensaje de confirmación o error
     */
    @DeleteMapping("/eliminar/{usuario1Id}/{usuario2Id}")
    public ResponseEntity<?> eliminarConversacion(
            @PathVariable Long usuario1Id,
            @PathVariable Long usuario2Id) {
        try {
            chatService.eliminarConversacion(usuario1Id, usuario2Id);
            return new ResponseEntity<>(Map.of("message", "Conversación eliminada"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
} 