package net.xeill.elpuig.apipatitasconectadas.repositories;

import net.xeill.elpuig.apipatitasconectadas.models.UsuarioComentarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioComentarioRepository extends JpaRepository<UsuarioComentarioModel, Long> {
    
    // Encontrar todas las relaciones de un usuario específico
    List<UsuarioComentarioModel> findByUsuarioId(Long usuarioId);
    
    // Encontrar todas las relaciones de un comentario específico
    List<UsuarioComentarioModel> findByComentarioId(Long comentarioId);
    
    // Encontrar todas las relaciones de un post específico
    List<UsuarioComentarioModel> findByPostId(Long postId);
    
    // Encontrar una relación específica por usuario y comentario
    UsuarioComentarioModel findByUsuarioIdAndComentarioId(Long usuarioId, Long comentarioId);
    
    // Encontrar una relación específica por usuario y post
    UsuarioComentarioModel findByUsuarioIdAndPostId(Long usuarioId, Long postId);
    
    // Verificar si existe una relación entre un usuario y un comentario
    boolean existsByUsuarioIdAndComentarioId(Long usuarioId, Long comentarioId);
    
    // Verificar si existe una relación entre un usuario y un post
    boolean existsByUsuarioIdAndPostId(Long usuarioId, Long postId);
    
    // Eliminar todas las relaciones de un usuario específico
    void deleteByUsuarioId(Long usuarioId);
    
    // Eliminar todas las relaciones de un comentario específico
    void deleteByComentarioId(Long comentarioId);
    
    // Eliminar todas las relaciones de un post específico
    void deleteByPostId(Long postId);
    
    // Eliminar una relación específica por usuario y comentario
    void deleteByUsuarioIdAndComentarioId(Long usuarioId, Long comentarioId);
    
    // Eliminar una relación específica por usuario y post
    void deleteByUsuarioIdAndPostId(Long usuarioId, Long postId);
} 