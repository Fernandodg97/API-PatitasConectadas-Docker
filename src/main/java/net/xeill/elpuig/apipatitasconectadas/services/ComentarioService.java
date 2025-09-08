package net.xeill.elpuig.apipatitasconectadas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.xeill.elpuig.apipatitasconectadas.models.*;
import net.xeill.elpuig.apipatitasconectadas.repositories.ComentarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con los comentarios.
 * Proporciona métodos para crear, leer, actualizar y eliminar comentarios,
 * así como para realizar búsquedas específicas por usuario, contenido o fecha.
 */
@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    /**
     * Guarda un nuevo comentario o actualiza uno existente.
     * @param comentario Objeto ComentarioModel con los datos del comentario a guardar
     * @return El comentario guardado con su ID asignado
     */
    public ComentarioModel saveComentario(ComentarioModel comentario) {
        return comentarioRepository.save(comentario);
    }

    /**
     * Busca un comentario por su ID.
     * @param id ID del comentario a buscar
     * @return Optional con el comentario si existe, o vacío si no se encuentra
     */
    public Optional<ComentarioModel> getComentarioById(Long id) {
        return comentarioRepository.findById(id);
    }

    /**
     * Obtiene todos los comentarios realizados por un usuario específico.
     * @param usuario Objeto UserModel que representa al creador de los comentarios
     * @return Lista de comentarios creados por el usuario
     */
    public List<ComentarioModel> getComentariosByCreador(UserModel usuario) {
        return comentarioRepository.findByCreador(usuario);
    }

    /**
     * Busca comentarios por contenido.
     * @param searchTerm Texto a buscar en el contenido de los comentarios
     * @return Lista de comentarios que contienen el término buscado
     */
    public List<ComentarioModel> searchByContent(String searchTerm) {
        return comentarioRepository.searchByContent(searchTerm);
    }

    /**
     * Obtiene comentarios dentro de un rango de fechas.
     * @param startDate Fecha inicial del rango
     * @param endDate Fecha final del rango
     * @return Lista de comentarios realizados dentro del rango de fechas especificado
     */
    public List<ComentarioModel> getComentariosByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return comentarioRepository.findByDateRange(startDate, endDate);
    }

    /**
     * Elimina un comentario por su ID.
     * @param id ID del comentario a eliminar
     */
    public void deleteComentario(Long id) {
        comentarioRepository.deleteById(id);
    }
}
