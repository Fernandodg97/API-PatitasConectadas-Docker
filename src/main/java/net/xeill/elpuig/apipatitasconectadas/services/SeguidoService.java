package net.xeill.elpuig.apipatitasconectadas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.xeill.elpuig.apipatitasconectadas.models.SeguidoModel;
import net.xeill.elpuig.apipatitasconectadas.repositories.SeguidoRepository;
import net.xeill.elpuig.apipatitasconectadas.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las relaciones de seguimiento entre usuarios.
 * Proporciona métodos para establecer, eliminar y consultar relaciones
 * donde un usuario puede seguir a otro, similar a una red social.
 */
@Service
public class SeguidoService {

    @Autowired
    private SeguidoRepository seguidoRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Establece una relación de seguimiento entre dos usuarios.
     * @param usuarioId ID del usuario que seguirá
     * @param usuarioASeguirId ID del usuario que será seguido
     * @return El objeto SeguidoModel que representa la relación creada
     * @throws RuntimeException si alguno de los usuarios no existe, si son el mismo usuario
     *         o si ya existe la relación de seguimiento
     */
    public SeguidoModel seguirUsuario(Long usuarioId, Long usuarioASeguirId) {
        if (usuarioId.equals(usuarioASeguirId)) {
            throw new RuntimeException("No puedes seguirte a ti mismo.");
        }
    
        // Verificar si el usuario que quiere seguir existe
        if (!userRepository.existsById(usuarioId)) {
            throw new RuntimeException("El usuario que sigue no existe.");
        }
    
        // Verificar si el usuario a seguir existe
        if (!userRepository.existsById(usuarioASeguirId)) {
            throw new RuntimeException("El usuario que quieres seguir no existe.");
        }
    
        // Comprobar si ya existe la relación
        if (seguidoRepository.existsByUsuarioQueSigueIdAndUsuarioQueEsSeguidoId(usuarioId, usuarioASeguirId)) {
            throw new RuntimeException("Ya estás siguiendo a este usuario.");
        }
    
        SeguidoModel seguido = new SeguidoModel();
        seguido.setUsuarioQueSigueId(usuarioId);
        seguido.setUsuarioQueEsSeguidoId(usuarioASeguirId);
        return seguidoRepository.save(seguido);
    }

    /**
     * Elimina una relación de seguimiento entre dos usuarios.
     * @param quienSigueId ID del usuario que sigue
     * @param quienEsSeguidoId ID del usuario que es seguido
     * @return true si la relación fue eliminada con éxito, false si no existía la relación
     */
    public boolean dejarDeSeguir(Long quienSigueId, Long quienEsSeguidoId) {
        Optional<SeguidoModel> seguido = seguidoRepository.findByUsuarioQueSigueIdAndUsuarioQueEsSeguidoId(quienSigueId, quienEsSeguidoId);
        if (seguido.isPresent()) {
            seguidoRepository.deleteById(seguido.get().getId());
            return true;
        }
        return false;
    }

    /**
     * Obtiene todos los usuarios a los que sigue un usuario específico.
     * @param usuarioId ID del usuario cuyas relaciones de seguimiento se quieren consultar
     * @return Lista de relaciones de seguimiento donde el usuario especificado es quien sigue
     */
    public List<SeguidoModel> obtenerSeguidos(Long usuarioId) {
        return seguidoRepository.findByUsuarioQueSigueId(usuarioId);
    }

    /**
     * Verifica si existe una relación de seguimiento entre dos usuarios.
     * @param quienSigueId ID del usuario que potencialmente sigue
     * @param quienEsSeguidoId ID del usuario que potencialmente es seguido
     * @return true si existe la relación de seguimiento, false en caso contrario
     */
    public boolean yaSigue(Long quienSigueId, Long quienEsSeguidoId) {
        return seguidoRepository.findByUsuarioQueSigueIdAndUsuarioQueEsSeguidoId(quienSigueId, quienEsSeguidoId).isPresent();
    }
}
