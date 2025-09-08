package net.xeill.elpuig.apipatitasconectadas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.xeill.elpuig.apipatitasconectadas.models.*;
import net.xeill.elpuig.apipatitasconectadas.repositories.*;

/**
 * Servicio que gestiona las operaciones relacionadas con los eventos.
 * Proporciona métodos para crear, leer, actualizar y eliminar eventos
 * en el sistema.
 */
@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    /**
     * Obtiene todos los eventos existentes en el sistema.
     * @return ArrayList con todos los eventos
     */
    public List<EventoModel> getAllEventos() {
        // findAll() es un método que me permite obtener todos los registros de la tabla
        return eventoRepository.findAll();
    }

    /**
     * Busca un evento por su ID.
     * @param id ID del evento a buscar
     * @return Optional con el evento si existe, o vacío si no se encuentra
     */
    public EventoModel getEventoById(Long id) {
        // findById() es un método que me permite obtener un registro por su id
        return eventoRepository.findById(id).orElse(null);
    }

    /**
     * Guarda un nuevo evento o actualiza uno existente.
     * @param evento Objeto EventoModel con los datos del evento a guardar
     * @return El evento guardado con su ID asignado
     */
    public EventoModel saveEvento(EventoModel evento) {
        // save() es un método que me permite guardar un registro en la tabla
        return eventoRepository.save(evento);
    }

    /**
     * Actualiza los datos de un evento existente.
     * @param request Objeto EventoModel con los nuevos datos
     * @param id ID del evento a actualizar
     * @return El evento actualizado
     * @throws RuntimeException si no se encuentra el evento con el ID especificado
     */
    public EventoModel updateByID(EventoModel request, Long id) {
        Optional<EventoModel> optionalEvento = eventoRepository.findById(id);

        if (optionalEvento.isPresent()) {
            EventoModel evento = optionalEvento.get();
            evento.setNombre(request.getNombre());
            evento.setDescripcion(request.getDescripcion());
            evento.setUbicacion(request.getUbicacion());
            evento.setFecha(request.getFecha());
            return eventoRepository.save(evento);
        } else {
            throw new RuntimeException("Evento no encontrado con ID: " + id);
        }
    }

    /**
     * Elimina un evento por su ID.
     * @param id ID del evento a eliminar
     * @return true si el evento fue eliminado con éxito, false en caso contrario
     */
    public void deleteEvento(Long id) {
        eventoRepository.deleteById(id);
    }
}
