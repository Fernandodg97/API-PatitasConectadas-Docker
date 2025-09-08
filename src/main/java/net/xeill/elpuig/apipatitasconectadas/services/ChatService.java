package net.xeill.elpuig.apipatitasconectadas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.xeill.elpuig.apipatitasconectadas.models.ChatModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import net.xeill.elpuig.apipatitasconectadas.repositories.ChatRepository;
import net.xeill.elpuig.apipatitasconectadas.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con el chat entre usuarios.
 * Proporciona métodos para enviar, recibir y gestionar mensajes,
 * así como obtener conversaciones completas y marcar mensajes como vistos.
 */
@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;
    
    @Autowired
    private UserRepository userRepository;

    /**
     * Envía un nuevo mensaje de un usuario a otro.
     * @param emisorId ID del usuario que envía el mensaje
     * @param receptorId ID del usuario que recibe el mensaje
     * @param contenido Texto del mensaje a enviar
     * @return El mensaje creado con su ID asignado
     * @throws RuntimeException si no se encuentra alguno de los usuarios
     */
    @Transactional
    public ChatModel enviarMensaje(Long emisorId, Long receptorId, String contenido) {
        Optional<UserModel> emisor = userRepository.findById(emisorId);
        Optional<UserModel> receptor = userRepository.findById(receptorId);
        
        if (emisor.isEmpty() || receptor.isEmpty()) {
            throw new RuntimeException("Usuario emisor o receptor no encontrado");
        }
        
        ChatModel mensaje = new ChatModel();
        mensaje.setEmisor(emisor.get());
        mensaje.setReceptor(receptor.get());
        mensaje.setContenido(contenido);
        mensaje.setVisto(false);
        mensaje.setFechaHora(LocalDateTime.now());
        
        return chatRepository.save(mensaje);
    }

    /**
     * Obtiene todos los mensajes intercambiados entre dos usuarios.
     * @param usuario1Id ID del primer usuario en la conversación
     * @param usuario2Id ID del segundo usuario en la conversación
     * @return Lista de mensajes intercambiados entre los usuarios
     */
    public List<ChatModel> obtenerConversacion(Long usuario1Id, Long usuario2Id) {
        return chatRepository.findConversacion(usuario1Id, usuario2Id);
    }

    /**
     * Marca como vistos todos los mensajes enviados por un usuario a otro.
     * @param usuarioId ID del usuario receptor de los mensajes
     * @param otroUsuarioId ID del usuario emisor de los mensajes a marcar como vistos
     */
    @Transactional
    public void marcarComoVistos(Long usuarioId, Long otroUsuarioId) {
        List<ChatModel> mensajesNoVistos = chatRepository.findMensajesNoVistos(usuarioId);
        for (ChatModel mensaje : mensajesNoVistos) {
            if (mensaje.getEmisor().getId().equals(otroUsuarioId)) {
                mensaje.setVisto(true);
                chatRepository.save(mensaje);
            }
        }
    }

    /**
     * Obtiene todos los mensajes no vistos por un usuario específico.
     * @param usuarioId ID del usuario cuyos mensajes no vistos se quieren obtener
     * @return Lista de mensajes no vistos por el usuario
     */
    public List<ChatModel> obtenerMensajesNoVistos(Long usuarioId) {
        return chatRepository.findMensajesNoVistos(usuarioId);
    }

    /**
     * Obtiene todos los mensajes enviados por un usuario específico.
     * @param usuarioId ID del usuario cuyos mensajes enviados se quieren obtener
     * @return Lista de mensajes enviados por el usuario
     */
    public List<ChatModel> obtenerMensajesEnviados(Long usuarioId) {
        return chatRepository.findByEmisorId(usuarioId);
    }

    /**
     * Obtiene todos los mensajes recibidos por un usuario específico.
     * @param usuarioId ID del usuario cuyos mensajes recibidos se quieren obtener
     * @return Lista de mensajes recibidos por el usuario
     */
    public List<ChatModel> obtenerMensajesRecibidos(Long usuarioId) {
        return chatRepository.findByReceptorId(usuarioId);
    }

    /**
     * Elimina todos los mensajes intercambiados entre dos usuarios.
     * @param usuario1Id ID del primer usuario en la conversación
     * @param usuario2Id ID del segundo usuario en la conversación
     * @throws RuntimeException si no se encuentra conversación entre los usuarios
     */
    public void eliminarConversacion(Long usuario1Id, Long usuario2Id) {
        List<ChatModel> conversacion = chatRepository.findConversacion(usuario1Id, usuario2Id);
        
        if (conversacion != null && !conversacion.isEmpty()) {
            chatRepository.deleteAll(conversacion);
        } else {
            throw new RuntimeException("No se encontró una conversación entre los usuarios.");
        }
    }
} 