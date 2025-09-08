package net.xeill.elpuig.apipatitasconectadas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import net.xeill.elpuig.apipatitasconectadas.models.*;
import net.xeill.elpuig.apipatitasconectadas.repositories.*;

/**
 * Servicio que gestiona las operaciones relacionadas con los usuarios.
 * Proporciona métodos para crear, leer, actualizar y eliminar usuarios,
 * así como para realizar búsquedas específicas.
 */
@Service
public class UserService {

    //Sirve para injectar dependencias
    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Obtiene todos los usuarios registrados en el sistema.
     * @return ArrayList con todos los usuarios
     */
    public ArrayList<UserModel> getUsers() {
        //findAll() es un metodo que me permite obtener todos los registros de la tabla
        return (ArrayList<UserModel>) userRepository.findAll();
    }

    /**
     * Busca usuarios por apellido.
     * @param apellido Apellido o inicio del apellido para filtrar usuarios
     * @return Lista de usuarios que coinciden con el criterio de búsqueda
     */
    public List<UserModel> getUsers(String apellido){
        return userRepository.findByApellidoStartsWith(apellido);
    }

    /**
     * Guarda un nuevo usuario o actualiza uno existente.
     * @param user Objeto UserModel con los datos del usuario a guardar
     * @return El usuario guardado con su ID asignado
     */
    public UserModel saveUser(UserModel user) {
        //save() es un metodo que me permite guardar un registro en la tabla
        return userRepository.save(user);
    }

    /**
     * Busca un usuario por su ID.
     * @param id ID del usuario a buscar
     * @return Optional con el usuario si existe, o vacío si no se encuentra
     */
    public Optional<UserModel> getById(Long id) {
        //findById() es un metodo que me permite obtener un registro por su id
        return userRepository.findById(id);
    }

    /**
     * Actualiza los datos de un usuario existente.
     * @param request Objeto UserModel con los nuevos datos
     * @param id ID del usuario a actualizar
     * @return El usuario actualizado
     */
    public UserModel updateByID(UserModel request, Long id) {
        UserModel user = userRepository.findById(id).get();

        user.setNombre(request.getNombre());
        user.setApellido(request.getApellido());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return user;
    }

    /**
     * Elimina un usuario por su ID.
     * @param id ID del usuario a eliminar
     * @return true si el usuario fue eliminado con éxito, false en caso contrario
     */
    public Boolean deleteUser (Long id){
        try{
            userRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public UserModel getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Busca usuarios por nombre y apellido
     * @param nombre Nombre del usuario
     * @param apellido Apellido del usuario
     * @return Lista de usuarios que coinciden con los criterios de búsqueda
     */
    public List<UserModel> searchUsers(String nombre, String apellido) {
        System.out.println("UserService.searchUsers - nombre: [" + nombre + "], apellido: [" + apellido + "]");
        
        try {
            if (nombre != null && apellido != null) {
                System.out.println("Buscando por nombre y apellido");
                List<UserModel> results = userRepository.findByNombreContainingIgnoreCaseAndApellidoContainingIgnoreCase(nombre, apellido);
                System.out.println("Resultados encontrados: " + results.size());
                return results;
            } else if (nombre != null) {
                System.out.println("Buscando solo por nombre");
                List<UserModel> results = userRepository.findByNombreContainingIgnoreCase(nombre);
                System.out.println("Resultados encontrados: " + results.size());
                return results;
            } else if (apellido != null) {
                System.out.println("Buscando solo por apellido");
                List<UserModel> results = userRepository.findByApellidoContainingIgnoreCase(apellido);
                System.out.println("Resultados encontrados: " + results.size());
                return results;
            } else {
                System.out.println("No se proporcionaron criterios de búsqueda");
                return new ArrayList<>();
            }
        } catch (Exception e) {
            System.err.println("Error en UserService.searchUsers: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Actualiza la contraseña de un usuario
     * @param id ID del usuario
     * @param currentPassword Contraseña actual
     * @param newPassword Nueva contraseña
     * @return El usuario actualizado
     * @throws Exception si la contraseña actual es incorrecta o hay otros errores
     */
    public UserModel updatePassword(Long id, String currentPassword, String newPassword) throws Exception {
        UserModel user = userRepository.findById(id)
            .orElseThrow(() -> new Exception("Usuario no encontrado con ID: " + id));

        // Verificar que la contraseña actual sea correcta
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new Exception("La contraseña actual es incorrecta");
        }

        // Encriptar y guardar la nueva contraseña
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    /**
     * Actualiza parcialmente un usuario existente
     * @param id ID del usuario a actualizar
     * @param updates Mapa con los campos a actualizar
     * @return El usuario actualizado
     * @throws Exception si el usuario no existe o hay un error en la actualización
     */
    public UserModel patchUser(Long id, Map<String, Object> updates) throws Exception {
        UserModel user = userRepository.findById(id)
            .orElseThrow(() -> new Exception("Usuario no encontrado con ID: " + id));

        // Validar que el email no esté en uso si se está actualizando
        if (updates.containsKey("email")) {
            String newEmail = (String) updates.get("email");
            if (!newEmail.equals(user.getEmail()) && userRepository.existsByEmail(newEmail)) {
                throw new Exception("El email ya está en uso");
            }
        }

        // Actualizar solo los campos proporcionados
        updates.forEach((key, value) -> {
            switch (key) {
                case "nombre":
                    user.setNombre((String) value);
                    break;
                case "apellido":
                    user.setApellido((String) value);
                    break;
                case "email":
                    user.setEmail((String) value);
                    break;
                case "password":
                    // No permitir actualización directa de contraseña
                    throw new IllegalArgumentException("Para actualizar la contraseña, use el endpoint específico de actualización de contraseña");
                default:
                    // Ignorar campos no reconocidos
                    break;
            }
        });

        return userRepository.save(user);
    }
}