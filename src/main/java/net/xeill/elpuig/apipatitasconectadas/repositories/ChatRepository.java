package net.xeill.elpuig.apipatitasconectadas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.xeill.elpuig.apipatitasconectadas.models.*;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatModel, Long> {
    
    // Obtener todos los mensajes entre dos usuarios
    @Query("SELECT c FROM ChatModel c WHERE (c.emisor.id = :usuario1Id AND c.receptor.id = :usuario2Id) OR (c.emisor.id = :usuario2Id AND c.receptor.id = :usuario1Id) ORDER BY c.fechaHora ASC")
    List<ChatModel> findConversacion(@Param("usuario1Id") Long usuario1Id, @Param("usuario2Id") Long usuario2Id);
    
    // Obtener mensajes no vistos de un usuario
    @Query("SELECT c FROM ChatModel c WHERE c.receptor.id = :usuarioId AND c.visto = false")
    List<ChatModel> findMensajesNoVistos(@Param("usuarioId") Long usuarioId);
    
    // Obtener todos los mensajes enviados por un usuario
    List<ChatModel> findByEmisorId(Long emisorId);
    
    // Obtener todos los mensajes recibidos por un usuario
    List<ChatModel> findByReceptorId(Long receptorId);
} 