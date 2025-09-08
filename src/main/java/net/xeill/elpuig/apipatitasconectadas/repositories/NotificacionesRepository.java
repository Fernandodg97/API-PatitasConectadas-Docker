package net.xeill.elpuig.apipatitasconectadas.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.xeill.elpuig.apipatitasconectadas.models.NotificacionesModel;

/**
 * Repositorio para manejar operaciones CRUD de notificaciones
 */
@Repository
public interface NotificacionesRepository extends CrudRepository<NotificacionesModel, Long> {
    
    // Aquí se pueden agregar métodos de consulta personalizados si son necesarios
    
}