package net.xeill.elpuig.apipatitasconectadas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.xeill.elpuig.apipatitasconectadas.models.*;
import net.xeill.elpuig.apipatitasconectadas.repositories.*;

/**
 * Servicio que gestiona las relaciones entre usuarios y grupos.
 * Proporciona métodos para asociar usuarios a grupos con roles específicos,
 * así como consultar, actualizar y eliminar estas relaciones.
 */
@Service
public class UsuarioGrupoService {

    @Autowired
    private UsuarioGrupoRepository usuarioGrupoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    /**
     * Obtiene todas las relaciones usuario-grupo existentes en el sistema.
     * @return Lista con todas las relaciones usuario-grupo
     */
    public List<UsuarioGrupoModel> getUsuarioGrupos() {
        return usuarioGrupoRepository.findAll();
    }

    /**
     * Guarda una nueva relación usuario-grupo o actualiza una existente.
     * @param usuarioGrupo Objeto UsuarioGrupoModel con los datos de la relación a guardar
     * @return La relación guardada con su ID asignado
     */
    public UsuarioGrupoModel saveUsuarioGrupo(UsuarioGrupoModel usuarioGrupo) {
        return usuarioGrupoRepository.save(usuarioGrupo);
    }

    /**
     * Busca una relación usuario-grupo por su ID.
     * @param id ID de la relación a buscar
     * @return Optional con la relación si existe, o vacío si no se encuentra
     */
    public Optional<UsuarioGrupoModel> getById(Long id) {
        return usuarioGrupoRepository.findById(id);
    }

    /**
     * Actualiza los datos de una relación usuario-grupo existente.
     * @param request Objeto UsuarioGrupoModel con los nuevos datos
     * @param id ID de la relación a actualizar
     * @return La relación actualizada
     * @throws NoSuchElementException si no se encuentra la relación con el ID especificado
     */
    public UsuarioGrupoModel updateByID(UsuarioGrupoModel request, Long id) {
        UsuarioGrupoModel usuarioGrupo = usuarioGrupoRepository.findById(id).orElseThrow();

        usuarioGrupo.setUsuario(request.getUsuario());
        usuarioGrupo.setGrupo(request.getGrupo());
        usuarioGrupo.setRol(request.getRol());

        return usuarioGrupoRepository.save(usuarioGrupo);
    }

    /**
     * Elimina una relación usuario-grupo por su ID.
     * @param id ID de la relación a eliminar
     * @return true si la relación fue eliminada con éxito, false si no se encontró
     */
    public boolean deleteUsuarioGrupo(Long id) {
        if (usuarioGrupoRepository.existsById(id)) {
            usuarioGrupoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Obtiene todos los grupos a los que pertenece un usuario.
     * @param usuarioId ID del usuario cuyos grupos se quieren obtener
     * @return Lista de relaciones usuario-grupo para el usuario especificado
     */
    public List<UsuarioGrupoModel> getGruposByUsuarioId(Long usuarioId) {
        UserModel usuario = userRepository.findById(usuarioId).orElse(null);
        if (usuario == null) return new ArrayList<>();
        return usuarioGrupoRepository.findByUsuario(usuario);
    }

    /**
     * Obtiene todos los usuarios que pertenecen a un grupo.
     * @param grupoId ID del grupo cuyos usuarios se quieren obtener
     * @return Lista de relaciones usuario-grupo para el grupo especificado
     */
    public List<UsuarioGrupoModel> getUsuariosByGrupoId(Long grupoId) {
        GrupoModel grupo = grupoRepository.findById(grupoId).orElse(null);
        if (grupo == null) return new ArrayList<>();
        return usuarioGrupoRepository.findByGrupo(grupo);
    }

    /**
     * Obtiene la relación específica entre un usuario y un grupo.
     * @param usuarioId ID del usuario
     * @param grupoId ID del grupo
     * @return La relación usuario-grupo si existe, null en caso contrario
     */
    public UsuarioGrupoModel getUsuarioGrupoByUsuarioIdAndGrupoId(Long usuarioId, Long grupoId) {
        UserModel usuario = userRepository.findById(usuarioId).orElse(null);
        GrupoModel grupo = grupoRepository.findById(grupoId).orElse(null);
        if (usuario == null || grupo == null) return null;
        return usuarioGrupoRepository.findByUsuarioAndGrupo(usuario, grupo);
    }
}
