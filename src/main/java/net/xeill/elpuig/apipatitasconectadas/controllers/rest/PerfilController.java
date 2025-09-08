package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import java.util.List;
import java.util.stream.Collectors;
import java.io.IOException;
import java.util.Map;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import net.xeill.elpuig.apipatitasconectadas.controllers.dto.PerfilModelDtoRequest;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.PerfilModelDtoResponse;
import net.xeill.elpuig.apipatitasconectadas.models.PerfilModel;
import net.xeill.elpuig.apipatitasconectadas.services.FileStorageService;
import net.xeill.elpuig.apipatitasconectadas.services.PerfilService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;

/**
 * Controlador REST para gestionar operaciones relacionadas con perfiles de usuarios.
 * Proporciona endpoints para crear, leer, actualizar y eliminar perfiles.
 * Todas las respuestas son encapsuladas en objetos ResponseEntity para un manejo
 * consistente de la comunicación HTTP.
 */
@RestController
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Obtiene todos los perfiles existentes en el sistema
     * @return ResponseEntity con lista de perfiles en formato DTO o mensaje de error
     */
    @GetMapping(path = "/perfiles")
    public ResponseEntity<?> getPerfiles() {
        try {
            // Llama al servicio para obtener la lista de perfiles y la retorna
            List<PerfilModelDtoResponse> perfiles = perfilService.getPerfiles().stream()
                .map(PerfilModelDtoResponse::new)
                .collect(Collectors.toList());
            return ResponseEntity.ok(perfiles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener los perfiles: " + e.getMessage());
        }
    }

    /**
     * Crea un nuevo perfil en el sistema con imagen opcional
     * @param descripcion Descripción del perfil
     * @param fechaNacimiento Fecha de nacimiento del usuario
     * @param usuarioId ID del usuario al que pertenece el perfil
     * @param imagen Imagen del perfil (opcional)
     * @return ResponseEntity con el perfil creado o mensaje de error
     */
    @PostMapping(path = "/perfiles", consumes = "multipart/form-data")
    public ResponseEntity<?> savePerfil(
            @RequestParam("descripcion") String descripcion,
            @RequestParam("fechaNacimiento") String fechaNacimiento,
            @RequestParam("usuarioId") Long usuarioId,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
        try {
            // Crear el perfil
            PerfilModel perfil = new PerfilModel();
            perfil.setDescripcion(descripcion);
            perfil.setFecha_nacimiento(Date.valueOf(fechaNacimiento));
            perfil.setUsuario_id(usuarioId);
            
            // Manejar la imagen si se proporciona
            if (imagen != null && !imagen.isEmpty()) {
                String imagenPath = fileStorageService.storeFile(imagen);
                perfil.setImg(imagenPath);
            }
            
            // Guardar el perfil
            PerfilModel savedPerfil = perfilService.savePerfil(perfil);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new PerfilModelDtoResponse(savedPerfil));
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al procesar la imagen: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al guardar el perfil: " + e.getMessage()));
        }
    }

    /**
     * Obtiene el perfil asociado a un usuario específico
     * @param id ID del usuario cuyo perfil se busca
     * @return ResponseEntity con el perfil encontrado o mensaje de error
     */
    @GetMapping(path = "/usuarios/{id}/perfiles")
    public ResponseEntity<?> getPerfilById(@PathVariable("id") Long id) {
        try {
            // Llama al servicio para buscar el perfil por ID de usuario
            var perfilOptional = this.perfilService.getByUsuarioId(id);
            
            if (perfilOptional.isPresent()) {
                return ResponseEntity.ok(new PerfilModelDtoResponse(perfilOptional.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró el perfil para el usuario con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener el perfil: " + e.getMessage());
        }
    }

    /**
     * Actualiza el perfil de un usuario específico con imagen opcional
     * @param descripcion Descripción actualizada del perfil
     * @param fechaNacimiento Fecha de nacimiento actualizada
     * @param id ID del usuario cuyo perfil se actualizará
     * @param imagen Nueva imagen del perfil (opcional)
     * @return ResponseEntity con el perfil actualizado o mensaje de error
     */
    @PutMapping(path = "/usuarios/{id}/perfiles", consumes = "multipart/form-data")
    public ResponseEntity<?> updatePerfilById(
            @RequestParam("descripcion") String descripcion,
            @RequestParam("fechaNacimiento") String fechaNacimiento,
            @PathVariable("id") Long id,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
        try {
            // Obtener el perfil existente
            var perfilOptional = perfilService.getByUsuarioId(id);
            if (perfilOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "No se encontró el perfil para el usuario con ID: " + id));
            }
            
            PerfilModel existingPerfil = perfilOptional.get();
            
            // Preparar el perfil para actualizar
            PerfilModel perfilToUpdate = new PerfilModel();
            perfilToUpdate.setId(existingPerfil.getId());
            perfilToUpdate.setUsuario_id(id);
            perfilToUpdate.setDescripcion(descripcion);
            perfilToUpdate.setFecha_nacimiento(Date.valueOf(fechaNacimiento));
            
            // Manejar la imagen si se proporciona
            if (imagen != null && !imagen.isEmpty()) {
                // Eliminar la imagen anterior si existe
                if (existingPerfil.getImg() != null) {
                    fileStorageService.deleteFile(existingPerfil.getImg());
                }
                // Guardar la nueva imagen
                String imagenPath = fileStorageService.storeFile(imagen);
                perfilToUpdate.setImg(imagenPath);
            } else {
                perfilToUpdate.setImg(existingPerfil.getImg());
            }
            
            // Actualizar el perfil
            PerfilModel updatedPerfil = perfilService.updateByID(perfilToUpdate, id);
            
            return ResponseEntity.ok(new PerfilModelDtoResponse(updatedPerfil));
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al procesar la imagen: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al actualizar el perfil: " + e.getMessage()));
        }
    }

    /**
     * Elimina el perfil de un usuario específico
     * @param id ID del usuario cuyo perfil se eliminará
     * @return ResponseEntity con mensaje de confirmación o error
     */
    @DeleteMapping(path = "/usuarios/{id}/perfiles")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        try {
            // Obtener el perfil para eliminar su imagen si existe
            var perfilOptional = perfilService.getById(id);
            if (perfilOptional.isPresent() && perfilOptional.get().getImg() != null) {
                fileStorageService.deleteFile(perfilOptional.get().getImg());
            }
            
            // Eliminar el perfil
            boolean ok = perfilService.deletePerfil(id);
    
            if (ok) {
                return ResponseEntity.ok("Se ha eliminado el perfil con id: " + id);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se ha podido eliminar el perfil con id: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al eliminar el perfil: " + e.getMessage()));
        }
    }
}
