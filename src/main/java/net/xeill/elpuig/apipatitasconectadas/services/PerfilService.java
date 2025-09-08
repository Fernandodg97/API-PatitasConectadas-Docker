package net.xeill.elpuig.apipatitasconectadas.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.xeill.elpuig.apipatitasconectadas.models.*;
import net.xeill.elpuig.apipatitasconectadas.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;

/**
 * Servicio que gestiona las operaciones relacionadas con los perfiles de usuario.
 * Proporciona métodos para crear, leer, actualizar y eliminar perfiles,
 * permitiendo a los usuarios personalizar su información adicional en la plataforma.
 */
@Service
public class PerfilService {

    //Sirve para injectar dependencias
    @Autowired
    PerfilRepository perfilRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Obtiene todos los perfiles registrados en el sistema.
     * @return ArrayList con todos los perfiles
     */
    public ArrayList<PerfilModel> getPerfiles() {
        //findAll() es un metodo que me permite obtener todos los registros de la tabla
        return (ArrayList<PerfilModel>) perfilRepository.findAll();
    }

    /**
     * Guarda un nuevo perfil o actualiza uno existente.
     * @param perfil Objeto PerfilModel con los datos del perfil a guardar
     * @return El perfil guardado con su ID asignado
     */
    public PerfilModel savePerfil(PerfilModel perfil) {
        // Verificar que el usuario existe
        UserModel usuario = userRepository.findById(perfil.getUsuario_id())
            .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario con ID: " + perfil.getUsuario_id()));

        // Verificar que el usuario no tiene ya un perfil
        if (perfilRepository.findByUsuarioId(usuario.getId()).isPresent()) {
            throw new ValidationException("El usuario ya tiene un perfil asociado");
        }

        perfil.setUsuario(usuario);
        return perfilRepository.save(perfil);
    }

    /**
     * Busca un perfil por su ID.
     * @param id ID del perfil a buscar
     * @return Optional con el perfil si existe, o vacío si no se encuentra
     */
    public Optional<PerfilModel> getById(Long id) {
        //findById() es un metodo que me permite obtener un registro por su id
        return perfilRepository.findById(id);
    }

    /**
     * Actualiza los datos de un perfil existente.
     * @param request Objeto PerfilModel con los nuevos datos
     * @param id ID del usuario cuyo perfil se actualizará
     * @return El perfil actualizado
     */
    public PerfilModel updateByID(PerfilModel request, Long id) {
        PerfilModel perfil = perfilRepository.findByUsuarioId(id)
            .orElseThrow(() -> new EntityNotFoundException("No se encontró el perfil para el usuario con ID: " + id));

        // Verificar que el usuario existe
        UserModel usuario = userRepository.findById(request.getUsuario_id())
            .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario con ID: " + request.getUsuario_id()));
        
        // Si el usuario está cambiando, verificar que no tiene otro perfil
        if (!usuario.getId().equals(id)) {
            if (perfilRepository.findByUsuarioId(usuario.getId()).isPresent()) {
                throw new ValidationException("El usuario ya tiene un perfil asociado");
            }
        }

        perfil.setUsuario(usuario);
        perfil.setDescripcion(request.getDescripcion());
        perfil.setFecha_nacimiento(request.getFecha_nacimiento());
        perfil.setImg(request.getImg());

        return perfilRepository.save(perfil);
    }

    /**
     * Elimina un perfil por su ID.
     * @param id ID del perfil a eliminar
     * @return true si el perfil fue eliminado con éxito, false en caso contrario
     */
    public Boolean deletePerfil(Long id) {
        try {
            perfilRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Busca un perfil por el ID del usuario.
     * @param usuarioId ID del usuario cuyo perfil se busca
     * @return Optional con el perfil si existe, o vacío si no se encuentra
     */
    public Optional<PerfilModel> getByUsuarioId(Long usuarioId) {
        return perfilRepository.findByUsuarioId(usuarioId);
    }
} 