package net.xeill.elpuig.apipatitasconectadas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.xeill.elpuig.apipatitasconectadas.models.NotificacionesModel;
import net.xeill.elpuig.apipatitasconectadas.repositories.NotificacionesRepository;

/**
 * Servicio que gestiona las operaciones relacionadas con las notificaciones del sistema.
 * Proporciona métodos para crear, leer, actualizar y eliminar notificaciones,
 * permitiendo gestionar los avisos y alertas para los usuarios.
 */
@Service
public class NotificacionesService {

    @Autowired
    private NotificacionesRepository notificacionesRepository;

    /**
     * Obtiene todas las notificaciones
     * @return Lista de notificaciones
     */
    public List<NotificacionesModel> getAllNotificaciones() {
        return (List<NotificacionesModel>) notificacionesRepository.findAll();
    }

    /**
     * Obtiene una notificación por su ID
     * @param id ID de la notificación
     * @return Optional con la notificación o vacío si no existe
     */
    public Optional<NotificacionesModel> getNotificacionById(Long id) {
        return notificacionesRepository.findById(id);
    }

    /**
     * Guarda una nueva notificación
     * @param notificacion Notificación a guardar
     * @return Notificación guardada
     */
    public NotificacionesModel saveNotificacion(NotificacionesModel notificacion) {
        return notificacionesRepository.save(notificacion);
    }

    /**
     * Actualiza una notificación existente
     * @param notificacion Notificación con los datos actualizados
     * @return Notificación actualizada
     */
    public NotificacionesModel updateNotificacion(NotificacionesModel notificacion) {
        return notificacionesRepository.save(notificacion);
    }

    /**
     * Elimina una notificación por su ID
     * @param id ID de la notificación a eliminar
     */
    public void deleteNotificacion(Long id) {
        notificacionesRepository.deleteById(id);
    }

    /**
     * Verifica si existe una notificación con el ID especificado
     * @param id ID de la notificación
     * @return true si existe, false en caso contrario
     */
    public boolean existsById(Long id) {
        return notificacionesRepository.existsById(id);
    }
} 