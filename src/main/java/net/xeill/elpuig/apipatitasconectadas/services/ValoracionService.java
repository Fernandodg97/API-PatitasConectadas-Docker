package net.xeill.elpuig.apipatitasconectadas.services;

import net.xeill.elpuig.apipatitasconectadas.models.ValoracionModel;
import net.xeill.elpuig.apipatitasconectadas.repositories.ValoracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con las valoraciones entre usuarios.
 * Proporciona métodos para crear, leer, actualizar y eliminar valoraciones,
 * así como para obtener valoraciones específicas por autor o receptor.
 */
@Service // Marca esta clase como un servicio de Spring para que pueda ser inyectado donde se necesite
public class ValoracionService {

    @Autowired // Inyección automática del repositorio de valoraciones
    private ValoracionRepository valoracionRepository;

    /**
     * Obtiene todas las valoraciones existentes en el sistema.
     * @return Lista con todas las valoraciones
     */
    public List<ValoracionModel> obtenerTodas() {
        return valoracionRepository.findAll();
    }

    /**
     * Busca una valoración específica por su ID.
     * @param id ID de la valoración a buscar
     * @return Optional con la valoración si existe, o vacío si no se encuentra
     */
    public Optional<ValoracionModel> obtenerPorId(Long id) {
        return valoracionRepository.findById(id);
    }

    /**
     * Guarda una nueva valoración o actualiza una existente.
     * @param valoracion Objeto ValoracionModel con los datos de la valoración a guardar
     * @return La valoración guardada con su ID asignado
     */
    public ValoracionModel guardar(ValoracionModel valoracion) {
        return valoracionRepository.save(valoracion);
    }

    /**
     * Elimina una valoración por su ID.
     * @param id ID de la valoración a eliminar
     */
    public void eliminar(Long id) {
        valoracionRepository.deleteById(id);
    }

    /**
     * Obtiene todas las valoraciones recibidas por un usuario específico.
     * @param receptorId ID del usuario que ha recibido las valoraciones
     * @return Lista de valoraciones donde el usuario especificado es el receptor
     */
    public List<ValoracionModel> obtenerPorReceptorId(Long receptorId) {
        return valoracionRepository.findByReceptorId(receptorId);
    }
    
    /**
     * Obtiene todas las valoraciones realizadas por un usuario específico.
     * @param autorId ID del usuario que ha realizado las valoraciones
     * @return Lista de valoraciones donde el usuario especificado es el autor
     */
    public List<ValoracionModel> obtenerPorAutorId(Long autorId) {
        return valoracionRepository.findByAutorId(autorId);
    }
}
