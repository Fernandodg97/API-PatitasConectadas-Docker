package net.xeill.elpuig.apipatitasconectadas.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.xeill.elpuig.apipatitasconectadas.models.*;
import net.xeill.elpuig.apipatitasconectadas.repositories.*;

/**
 * Servicio que gestiona las operaciones relacionadas con los grupos.
 * Proporciona métodos para crear, leer, actualizar y eliminar grupos
 * en el sistema.
 */
@Service
public class GrupoService {

    @Autowired
    GrupoRepository grupoRepository;

    /**
     * Obtiene todos los grupos existentes en el sistema.
     * @return ArrayList con todos los grupos
     */
    public ArrayList<GrupoModel> getGrupos() {
        //findAll() es un metodo que me permite obtener todos los registros de la tabla
        return (ArrayList<GrupoModel>) grupoRepository.findAll();
    }

    /**
     * Guarda un nuevo grupo o actualiza uno existente.
     * @param grupo Objeto GrupoModel con los datos del grupo a guardar
     * @return El grupo guardado con su ID asignado
     */
    public GrupoModel saveGrupo(GrupoModel grupo) {
        //save() es un metodo que me permite guardar un registro en la tabla
        return grupoRepository.save(grupo);
    }

    /**
     * Busca un grupo por su ID.
     * @param id ID del grupo a buscar
     * @return Optional con el grupo si existe, o vacío si no se encuentra
     */
    public Optional<GrupoModel> getById(Long id) {
        //findById() es un metodo que me permite obtener un registro por su id
        return grupoRepository.findById(id);
    }

    /**
     * Actualiza los datos de un grupo existente.
     * @param request Objeto GrupoModel con los nuevos datos
     * @param id ID del grupo a actualizar
     * @return El grupo actualizado
     */
    public GrupoModel updateByID(GrupoModel request, Long id) {
        GrupoModel grupo = grupoRepository.findById(id).get();

        grupo.setNombre(request.getNombre());
        grupo.setDescripcion(request.getDescripcion());

        return grupoRepository.save(grupo);
    }

    /**
     * Elimina un grupo por su ID.
     * @param id ID del grupo a eliminar
     * @return true si el grupo fue eliminado con éxito, false en caso contrario
     */
    public Boolean deleteGrupo(Long id) {
        try {
            grupoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica si existe un grupo con el ID especificado.
     * @param id ID del grupo a verificar
     * @return true si el grupo existe, false en caso contrario
     */
    public boolean existsById(Long id) {
        return grupoRepository.existsById(id);
    }
} 