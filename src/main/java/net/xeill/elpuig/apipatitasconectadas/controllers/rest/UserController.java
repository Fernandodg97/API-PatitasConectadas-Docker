package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.xeill.elpuig.apipatitasconectadas.controllers.dto.UserModelDtoRequest;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.UserModelDtoResponse;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import net.xeill.elpuig.apipatitasconectadas.services.UserService;

/**
 * Controlador REST para gestionar operaciones relacionadas con usuarios.
 * Proporciona endpoints para crear, leer, actualizar y eliminar usuarios
 * en el sistema.
 * Todas las respuestas son encapsuladas en objetos ResponseEntity para un manejo
 * consistente de la comunicación HTTP.
 */
@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Obtiene todos los usuarios existentes en el sistema
     * @return ResponseEntity con lista de usuarios en formato DTO o mensaje de error
     */
    @GetMapping
    public ResponseEntity<?> getUsers() {
        try {
            ArrayList<UserModel> users = this.userService.getUsers();
            List<UserModelDtoResponse> usersDto = users.stream()
                .map(UserModelDtoResponse::new)
                .collect(Collectors.toList());
            return new ResponseEntity<>(usersDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Crea un nuevo usuario en el sistema
     * @param userDto Datos del usuario en formato DTO
     * @return ResponseEntity con el usuario creado o mensaje de error
     */
    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody UserModelDtoRequest userDto) {
        try {
            // Convertir DTO a modelo
            UserModel user = userDto.toDomain();
            
            // Guardar usuario
            UserModel savedUser = this.userService.saveUser(user);
            
            // Convertir a DTO para la respuesta
            UserModelDtoResponse response = new UserModelDtoResponse(savedUser);
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Error al guardar el usuario: " + e.getMessage()), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene un usuario específico por su ID
     * @param id ID del usuario a buscar
     * @return ResponseEntity con el usuario encontrado o mensaje de error
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        try {
            Optional<UserModel> user = this.userService.getById(id);
            
            if (user.isPresent()) {
                UserModelDtoResponse userDto = new UserModelDtoResponse(user.get());
                return new ResponseEntity<>(userDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of("error", "Usuario no encontrado con ID: " + id), 
                    HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Actualiza un usuario existente
     * @param userDto Datos actualizados del usuario
     * @param id ID del usuario a actualizar
     * @return ResponseEntity con el usuario actualizado o mensaje de error
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateUserById(@RequestBody UserModelDtoRequest userDto, @PathVariable("id") Long id) {
        try {
            // Convertir DTO a modelo
            UserModel user = userDto.toDomain();
            
            // Actualizar usuario
            UserModel updatedUser = this.userService.updateByID(user, id);
            
            // Convertir a DTO para la respuesta
            UserModelDtoResponse response = new UserModelDtoResponse(updatedUser);
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Error al actualizar el usuario: " + e.getMessage()), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza parcialmente un usuario existente
     * @param updates Mapa con los campos a actualizar
     * @param id ID del usuario a actualizar
     * @return ResponseEntity con el usuario actualizado o mensaje de error
     */
    @PatchMapping(path = "/{id}")
    public ResponseEntity<?> patchUserById(@RequestBody Map<String, Object> updates, @PathVariable("id") Long id) {
        try {
            // Actualizar usuario
            UserModel updatedUser = this.userService.patchUser(id, updates);
            
            // Convertir a DTO para la respuesta
            UserModelDtoResponse response = new UserModelDtoResponse(updatedUser);
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Error al actualizar el usuario: " + e.getMessage()), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza la contraseña de un usuario
     * @param id ID del usuario
     * @param passwordData Datos de la contraseña (actual y nueva)
     * @return ResponseEntity con el usuario actualizado o mensaje de error
     */
    @PatchMapping(path = "/{id}/password")
    public ResponseEntity<?> updatePassword(
            @PathVariable("id") Long id,
            @RequestBody Map<String, String> passwordData) {
        try {
            String currentPassword = passwordData.get("currentPassword");
            String newPassword = passwordData.get("newPassword");

            if (currentPassword == null || newPassword == null) {
                return new ResponseEntity<>(
                    Map.of("error", "Se requieren tanto la contraseña actual como la nueva"),
                    HttpStatus.BAD_REQUEST);
            }

            UserModel updatedUser = this.userService.updatePassword(id, currentPassword, newPassword);
            UserModelDtoResponse response = new UserModelDtoResponse(updatedUser);
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                Map.of("error", "Error al actualizar la contraseña: " + e.getMessage()),
                HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Elimina un usuario existente
     * @param id ID del usuario a eliminar
     * @return ResponseEntity con mensaje de confirmación o error
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        try {
            boolean deleted = this.userService.deleteUser(id);
            
            if (deleted) {
                return new ResponseEntity<>(Map.of("mensaje", "Usuario con ID: " + id + " eliminado correctamente"), 
                    HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of("error", "No se ha podido eliminar el usuario con ID: " + id), 
                    HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Busca usuarios por nombre y apellido
     * @param nombre Nombre del usuario
     * @param apellido Apellido del usuario
     * @return ResponseEntity con la lista de usuarios encontrados o mensaje de error
     */
    @GetMapping("/buscar")
    public ResponseEntity<?> searchUsers(
            @RequestParam(required = false, defaultValue = "") String nombre,
            @RequestParam(required = false, defaultValue = "") String apellido) {
        try {
            System.out.println("Buscando usuarios con nombre: [" + nombre + "], apellido: [" + apellido + "]");
            
            // Si ambos parámetros están vacíos, devolver todos los usuarios
            if (nombre.isEmpty() && apellido.isEmpty()) {
                System.out.println("No se proporcionaron criterios de búsqueda, devolviendo todos los usuarios");
                ArrayList<UserModel> allUsers = this.userService.getUsers();
                List<UserModelDtoResponse> usersDto = allUsers.stream()
                    .map(UserModelDtoResponse::new)
                    .collect(Collectors.toList());
                return new ResponseEntity<>(usersDto, HttpStatus.OK);
            }

            // Si los parámetros están vacíos, establecerlos como null
            String nombreParam = nombre.isEmpty() ? null : nombre;
            String apellidoParam = apellido.isEmpty() ? null : apellido;
            
            System.out.println("Parámetros procesados - nombre: [" + nombreParam + "], apellido: [" + apellidoParam + "]");
            
            List<UserModel> users = this.userService.searchUsers(nombreParam, apellidoParam);
            System.out.println("Usuarios encontrados: " + users.size());
            
            List<UserModelDtoResponse> usersDto = users.stream()
                .map(UserModelDtoResponse::new)
                .collect(Collectors.toList());
                
            return new ResponseEntity<>(usersDto, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error al buscar usuarios: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
