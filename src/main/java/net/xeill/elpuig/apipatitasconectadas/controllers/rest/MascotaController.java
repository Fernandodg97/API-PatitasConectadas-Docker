package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import net.xeill.elpuig.apipatitasconectadas.controllers.dto.MascotaModelDtoRequest;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.MascotaModelDtoResponse;
import net.xeill.elpuig.apipatitasconectadas.models.MascotaModel;
import net.xeill.elpuig.apipatitasconectadas.services.MascotaService;
import net.xeill.elpuig.apipatitasconectadas.services.FileStorageService;

/**
 * Controlador REST para gestionar operaciones relacionadas con mascotas de usuarios.
 * Proporciona endpoints para crear, leer, actualizar y eliminar mascotas
 * asociadas a un usuario específico.
 * Todas las respuestas son encapsuladas en objetos ResponseEntity para un manejo
 * consistente de la comunicación HTTP.
 */
@RestController
@RequestMapping("/usuarios/{usuarioId}/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Obtiene todas las mascotas asociadas a un usuario específico
     * @param usuarioId ID del usuario cuyas mascotas se quieren obtener
     * @return ResponseEntity con lista de mascotas en formato DTO o mensaje de error
     */
    @GetMapping
    public ResponseEntity<?> getMascotasByUsuario(@PathVariable("usuarioId") Long usuarioId) {
        try {
            List<MascotaModel> mascotas = mascotaService.getMascotasByUsuario(usuarioId);
            List<MascotaModelDtoResponse> mascotasDto = mascotas.stream()
                .map(MascotaModelDtoResponse::new)
                .collect(Collectors.toList());
            return new ResponseEntity<>(mascotasDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Crea una nueva mascota asociada a un usuario específico
     * @param usuarioId ID del usuario al que se asociará la mascota
     * @param nombre Nombre de la mascota
     * @param genero Género de la mascota
     * @param especie Especie de la mascota
     * @param fechaNacimiento Nueva fecha de nacimiento de la mascota
     * @param imagen Nueva imagen de la mascota
     * @return ResponseEntity con la mascota creada o mensaje de error
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveMascota(
            @PathVariable("usuarioId") Long usuarioId, 
            @RequestParam("nombre") String nombre,
            @RequestParam("genero") String genero,
            @RequestParam("especie") String especie,
            @RequestParam(value = "fechaNacimiento", required = false) String fechaNacimiento,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
        try {
            // Crear el modelo de mascota
            MascotaModel mascota = new MascotaModel();
            mascota.setUsuarioId(usuarioId);
            mascota.setNombre(nombre);
            mascota.setGenero(genero);
            mascota.setEspecie(especie);

            // Establecer fecha de nacimiento si se proporciona
            if (fechaNacimiento != null && !fechaNacimiento.isEmpty()) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date fecha = formatter.parse(fechaNacimiento);
                mascota.setFechaNacimiento(fecha);
            }

            // Si hay imagen, guardarla
            if (imagen != null && !imagen.isEmpty()) {
                String fotoPath = fileStorageService.storeFile(imagen, "mascotas");
                mascota.setFoto(fotoPath);
            }
            
            // Guardar mascota
            MascotaModel saved = mascotaService.saveMascota(mascota);
            
            // Convertir a DTO para la respuesta
            MascotaModelDtoResponse response = new MascotaModelDtoResponse(saved);
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Error al guardar la mascota: " + e.getMessage()), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene una mascota específica de un usuario por su ID
     * @param usuarioId ID del usuario propietario de la mascota
     * @param mascotaId ID de la mascota a buscar
     * @return ResponseEntity con la mascota encontrada o mensaje de error
     */
    @GetMapping("/{mascotaId}")
    public ResponseEntity<?> getMascotaById(
            @PathVariable("usuarioId") Long usuarioId,
            @PathVariable("mascotaId") Long mascotaId) {
        try {
            Optional<MascotaModel> mascota = mascotaService.getByIdAndUsuarioId(mascotaId, usuarioId);
            
            if (mascota.isPresent()) {
                MascotaModelDtoResponse mascotaDto = new MascotaModelDtoResponse(mascota.get());
                return new ResponseEntity<>(mascotaDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of("error", "Mascota no encontrada con ID: " + mascotaId), 
                    HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Actualiza una mascota específica de un usuario
     * @param usuarioId ID del usuario propietario de la mascota
     * @param mascotaId ID de la mascota a actualizar
     * @param nombre Nombre actualizado de la mascota
     * @param genero Género actualizado de la mascota
     * @param especie Especie actualizada de la mascota
     * @param fechaNacimiento Nueva fecha de nacimiento de la mascota
     * @param imagen Nueva imagen de la mascota
     * @return ResponseEntity con la mascota actualizada o mensaje de error
     */
    @PutMapping(value = "/{mascotaId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateMascota(
            @PathVariable("usuarioId") Long usuarioId,
            @PathVariable("mascotaId") Long mascotaId,
            @RequestParam("nombre") String nombre,
            @RequestParam("genero") String genero,
            @RequestParam("especie") String especie,
            @RequestParam(value = "fechaNacimiento", required = false) String fechaNacimiento,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
        try {
            // Obtener la mascota existente
            Optional<MascotaModel> optionalMascota = mascotaService.getByIdAndUsuarioId(mascotaId, usuarioId);
            
            if (optionalMascota.isPresent()) {
                MascotaModel mascota = optionalMascota.get();
                mascota.setNombre(nombre);
                mascota.setGenero(genero);
                mascota.setEspecie(especie);

                // Actualizar fecha de nacimiento si se proporciona
                if (fechaNacimiento != null && !fechaNacimiento.isEmpty()) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date fecha = formatter.parse(fechaNacimiento);
                    mascota.setFechaNacimiento(fecha);
                }

                // Si hay nueva imagen, guardarla
                if (imagen != null && !imagen.isEmpty()) {
                    // Eliminar la imagen anterior si existe
                    if (mascota.getFoto() != null) {
                        try {
                            fileStorageService.deleteFile(mascota.getFoto());
                        } catch (Exception e) {
                            // Solo loguear el error, no interrumpir el proceso
                            System.err.println("Error al eliminar la imagen anterior: " + e.getMessage());
                        }
                    }
                    String fotoPath = fileStorageService.storeFile(imagen, "mascotas");
                    mascota.setFoto(fotoPath);
                }
            
            // Actualizar mascota
                MascotaModel updated = mascotaService.saveMascota(mascota);
            
            // Convertir a DTO para la respuesta
            MascotaModelDtoResponse response = new MascotaModelDtoResponse(updated);
            
            return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of("error", "Mascota no encontrada con ID: " + mascotaId), 
                    HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Elimina una mascota específica de un usuario
     * @param usuarioId ID del usuario propietario de la mascota
     * @param mascotaId ID de la mascota a eliminar
     * @return ResponseEntity con mensaje de confirmación o error
     */
    @DeleteMapping("/{mascotaId}")
    public ResponseEntity<?> deleteMascota(
            @PathVariable("usuarioId") Long usuarioId,
            @PathVariable("mascotaId") Long mascotaId) {
        try {
            boolean deleted = mascotaService.deleteMascotaByUsuario(mascotaId, usuarioId);
            
            if (deleted) {
                return new ResponseEntity<>(Map.of("mensaje", "Mascota eliminada con ID: " + mascotaId), 
                    HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of("error", "Mascota no encontrada o no pertenece al usuario"), 
                    HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
