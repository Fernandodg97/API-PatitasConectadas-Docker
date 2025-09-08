package net.xeill.elpuig.apipatitasconectadas.repositories;

import net.xeill.elpuig.apipatitasconectadas.models.PostModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import net.xeill.elpuig.apipatitasconectadas.models.UsuarioPostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioPostRepository extends JpaRepository<UsuarioPostModel, Long> {
    
    // Buscar relaciones por ID de usuario
    List<UsuarioPostModel> findByUsuarioId(Long usuarioId);
    
    // Buscar relaciones por ID de post
    List<UsuarioPostModel> findByPostId(Long postId);
    
    // Verificar si existe una relación entre un usuario y un post
    boolean existsByUsuarioAndPost(UserModel usuario, PostModel post);
    
    // Eliminar todas las relaciones de un usuario
    void deleteByUsuarioId(Long usuarioId);
    
    // Eliminar todas las relaciones de un post
    void deleteByPostId(Long postId);
} 