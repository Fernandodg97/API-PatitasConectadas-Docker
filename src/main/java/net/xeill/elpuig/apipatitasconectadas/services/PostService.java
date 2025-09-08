package net.xeill.elpuig.apipatitasconectadas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.xeill.elpuig.apipatitasconectadas.models.PostModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import net.xeill.elpuig.apipatitasconectadas.models.GrupoModel;
import net.xeill.elpuig.apipatitasconectadas.repositories.*;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio que gestiona las operaciones relacionadas con las publicaciones (posts).
 * Proporciona métodos para crear, leer, actualizar y eliminar publicaciones,
 * así como búsquedas por usuario, grupo, contenido o rango de fechas.
 */
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private GrupoRepository grupoRepository;

    /**
     * Obtiene todas las publicaciones con información de sus creadores.
     * @return Lista de todas las publicaciones con datos del creador
     */
    public List<PostModel> getPosts() {
        return postRepository.findAllWithCreador();
    }

    /**
     * Guarda una nueva publicación en el sistema.
     * @param post Objeto PostModel con los datos de la publicación
     * @return La publicación guardada con su ID asignado
     * @throws ValidationException si los datos de la publicación son inválidos
     */
    @Transactional
    public PostModel savePost(PostModel post) {
        validatePost(post);
        post.setFecha(LocalDateTime.now());
        return postRepository.save(post);
    }

    /**
     * Busca una publicación por su ID.
     * @param id ID de la publicación a buscar
     * @return La publicación encontrada
     * @throws EntityNotFoundException si no se encuentra la publicación
     */
    public PostModel getById(Long id) {
        return postRepository.findByIdWithCreador(id)
            .orElseThrow(() -> new EntityNotFoundException("Post no encontrado con id: " + id));
    }

    /**
     * Actualiza los datos de una publicación existente.
     * @param request Objeto PostModel con los nuevos datos
     * @param id ID de la publicación a actualizar
     * @return La publicación actualizada
     * @throws EntityNotFoundException si no se encuentra la publicación
     */
    @Transactional
    public PostModel updateById(PostModel request, Long id) {
        PostModel post = getById(id);
        
        if (request.getContenido() != null) {
            post.setContenido(request.getContenido());
        }
        if (request.getImg() != null) {
            post.setImg(request.getImg());
        }
        if (request.getCreador() != null) {
            validateUser(request.getCreador().getId());
            post.setCreador(request.getCreador());
        }
        if (request.getGrupo() != null) {
            validateGrupo(request.getGrupo().getId());
            post.setGrupo(request.getGrupo());
        }
        
        return postRepository.save(post);
    }

    /**
     * Elimina una publicación por su ID.
     * @param id ID de la publicación a eliminar
     * @return true si la publicación fue eliminada con éxito, false en caso contrario
     */
    @Transactional
    public boolean deletePost(Long id) {
        try {
            postRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Obtiene todas las publicaciones realizadas por un usuario específico.
     * @param userId ID del usuario cuyas publicaciones se buscan
     * @return Lista de publicaciones del usuario
     * @throws EntityNotFoundException si no se encuentra el usuario
     */
    public List<PostModel> getPostsByUser(Long userId) {
        UserModel user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + userId));
        return postRepository.findByCreador(user);
    }
    
    /**
     * Obtiene todas las publicaciones realizadas en un grupo específico.
     * @param grupoId ID del grupo cuyas publicaciones se buscan
     * @return Lista de publicaciones del grupo
     * @throws EntityNotFoundException si no se encuentra el grupo
     */
    public List<PostModel> getPostsByGrupo(Long grupoId) {
        GrupoModel grupo = grupoRepository.findById(grupoId)
            .orElseThrow(() -> new EntityNotFoundException("Grupo no encontrado con id: " + grupoId));
        return postRepository.findByGrupo(grupo);
    }
    
    /**
     * Busca publicaciones por contenido.
     * @param searchTerm Texto a buscar en el contenido de las publicaciones
     * @return Lista de publicaciones que contienen el término buscado
     */
    public List<PostModel> searchPostsByContent(String searchTerm) {
        return postRepository.searchByContent(searchTerm);
    }
    
    /**
     * Obtiene publicaciones dentro de un rango de fechas.
     * @param startDate Fecha inicial del rango
     * @param endDate Fecha final del rango
     * @return Lista de publicaciones realizadas dentro del rango de fechas especificado
     */
    public List<PostModel> getPostsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return postRepository.findByDateRange(startDate, endDate);
    }
    
    /**
     * Valida los datos de una publicación.
     * @param post Publicación a validar
     * @throws ValidationException si los datos de la publicación son inválidos
     */
    private void validatePost(PostModel post) {
        if (post.getContenido() == null || post.getContenido().trim().isEmpty()) {
            throw new ValidationException("El contenido del post no puede estar vacío");
        }
        if (post.getCreador() == null || post.getCreador().getId() == null) {
            throw new ValidationException("El creador del post es obligatorio");
        }
        validateUser(post.getCreador().getId());
        if (post.getGrupo() != null && post.getGrupo().getId() != null) {
            validateGrupo(post.getGrupo().getId());
        }
    }
    
    /**
     * Valida la existencia de un usuario.
     * @param userId ID del usuario a validar
     * @throws EntityNotFoundException si no se encuentra el usuario
     */
    private void validateUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("Usuario no encontrado con id: " + userId);
        }
    }
    
    /**
     * Valida la existencia de un grupo.
     * @param grupoId ID del grupo a validar
     * @throws EntityNotFoundException si no se encuentra el grupo
     */
    private void validateGrupo(Long grupoId) {
        if (!grupoRepository.existsById(grupoId)) {
            throw new EntityNotFoundException("Grupo no encontrado con id: " + grupoId);
        }
    }
}