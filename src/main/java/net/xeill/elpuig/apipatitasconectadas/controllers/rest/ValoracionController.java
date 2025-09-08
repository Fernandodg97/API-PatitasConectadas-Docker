package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import net.xeill.elpuig.apipatitasconectadas.controllers.dto.ValoracionModelDtoRequest;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.ValoracionModelDtoResponse;
import net.xeill.elpuig.apipatitasconectadas.models.ValoracionModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import net.xeill.elpuig.apipatitasconectadas.services.UserService;
import net.xeill.elpuig.apipatitasconectadas.services.ValoracionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador REST para gestionar operaciones relacionadas con valoraciones entre usuarios.
 * Proporciona endpoints para crear, leer, actualizar y eliminar valoraciones,
 * así como para consultar valoraciones recibidas o enviadas por un usuario específico.
 * Todas las respuestas son encapsuladas en objetos ResponseEntity para un manejo
 * consistente de la comunicación HTTP.
 */
@RestController
@RequestMapping("/valoraciones")
public class ValoracionController {

    @Autowired
    private ValoracionService valoracionService;

    @Autowired
    private UserService userService;

    /**
     * Obtiene todas las valoraciones existentes en el sistema
     * @return ResponseEntity con lista de valoraciones en formato DTO o mensaje de error
     */
    @GetMapping
    public ResponseEntity<?> obtenerTodas() {
        try {
            List<ValoracionModel> valoraciones = valoracionService.obtenerTodas();
            List<ValoracionModelDtoResponse> valoracionesDto = valoraciones.stream()
                .map(ValoracionModelDtoResponse::new)
                .collect(Collectors.toList());
            return new ResponseEntity<>(valoracionesDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene una valoración específica por su ID
     * @param id ID de la valoración a buscar
     * @return ResponseEntity con la valoración encontrada o mensaje de error
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            Optional<ValoracionModel> valoracion = valoracionService.obtenerPorId(id);
            
            if (valoracion.isPresent()) {
                ValoracionModelDtoResponse valoracionDto = new ValoracionModelDtoResponse(valoracion.get());
                return new ResponseEntity<>(valoracionDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of("error", "Valoración no encontrada con ID: " + id), 
                    HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Crea una nueva valoración de un usuario (autor) hacia otro (receptor)
     * @param autorId ID del usuario que emite la valoración
     * @param receptorId ID del usuario que recibe la valoración
     * @param valoracionDto Datos de la valoración en formato DTO
     * @return ResponseEntity con la valoración creada o mensaje de error
     */
    @PostMapping("/usuarios/{autorId}/receptor/{receptorId}")
    public ResponseEntity<?> crearValoracion(
            @PathVariable Long autorId,
            @PathVariable Long receptorId,
            @RequestBody ValoracionModelDtoRequest valoracionDto) {
        try {
            Optional<UserModel> autor = userService.getById(autorId);
            Optional<UserModel> receptor = userService.getById(receptorId);

            if (autor.isEmpty()) {
                return new ResponseEntity<>(Map.of("error", "Autor no encontrado con ID: " + autorId), 
                    HttpStatus.NOT_FOUND);
            }
            
            if (receptor.isEmpty()) {
                return new ResponseEntity<>(Map.of("error", "Receptor no encontrado con ID: " + receptorId), 
                    HttpStatus.NOT_FOUND);
            }

            // Convertir DTO a modelo
            ValoracionModel valoracion = valoracionDto.toDomain(autor.get(), receptor.get());
            
            // Guardar valoración
            ValoracionModel guardada = valoracionService.guardar(valoracion);
            
            // Convertir a DTO para la respuesta
            ValoracionModelDtoResponse response = new ValoracionModelDtoResponse(guardada);
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Actualiza una valoración existente
     * @param id ID de la valoración a actualizar
     * @param valoracionDto Datos actualizados de la valoración
     * @return ResponseEntity con la valoración actualizada o mensaje de error
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @RequestBody ValoracionModelDtoRequest valoracionDto) {
        try {
            Optional<ValoracionModel> existente = valoracionService.obtenerPorId(id);
            
            if (existente.isEmpty()) {
                return new ResponseEntity<>(Map.of("error", "Valoración no encontrada con ID: " + id), 
                    HttpStatus.NOT_FOUND);
            }

            ValoracionModel actual = existente.get();
            
            // Actualizar solo los campos proporcionados
            if (valoracionDto.getContenido() != null) {
                actual.setContenido(valoracionDto.getContenido());
            }
            if (valoracionDto.getPuntuacion() != null) {
                actual.setPuntuacion(valoracionDto.getPuntuacion());
            }
            if (valoracionDto.getFecha() != null) {
                actual.setFecha(valoracionDto.getFecha());
            }

            // Guardar valoración actualizada
            ValoracionModel actualizada = valoracionService.guardar(actual);
            
            // Convertir a DTO para la respuesta
            ValoracionModelDtoResponse response = new ValoracionModelDtoResponse(actualizada);
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Elimina una valoración existente
     * @param id ID de la valoración a eliminar
     * @return ResponseEntity con mensaje de confirmación o error
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            Optional<ValoracionModel> existente = valoracionService.obtenerPorId(id);
            
            if (existente.isEmpty()) {
                return new ResponseEntity<>(Map.of("error", "Valoración no encontrada con ID: " + id), 
                    HttpStatus.NOT_FOUND);
            }
            
            valoracionService.eliminar(id);
            return new ResponseEntity<>(Map.of("mensaje", "Valoración eliminada correctamente"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene todas las valoraciones recibidas por un usuario específico
     * @param receptorId ID del usuario cuyas valoraciones recibidas se quieren obtener
     * @return ResponseEntity con lista de valoraciones recibidas en formato DTO o mensaje de error
     */
    @GetMapping("/usuarios/{receptorId}/recibidas")
    public ResponseEntity<?> valoracionesRecibidas(@PathVariable Long receptorId) {
        try {
            List<ValoracionModel> valoraciones = valoracionService.obtenerPorReceptorId(receptorId);
            List<ValoracionModelDtoResponse> valoracionesDto = valoraciones.stream()
                .map(ValoracionModelDtoResponse::new)
                .collect(Collectors.toList());
            return new ResponseEntity<>(valoracionesDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene todas las valoraciones emitidas por un usuario específico
     * @param autorId ID del usuario cuyas valoraciones emitidas se quieren obtener
     * @return ResponseEntity con lista de valoraciones emitidas en formato DTO o mensaje de error
     */
    @GetMapping("/usuarios/{autorId}/enviadas")
    public ResponseEntity<?> valoracionesEnviadas(@PathVariable Long autorId) {
        try {
            List<ValoracionModel> valoraciones = valoracionService.obtenerPorAutorId(autorId);
            List<ValoracionModelDtoResponse> valoracionesDto = valoraciones.stream()
                .map(ValoracionModelDtoResponse::new)
                .collect(Collectors.toList());
            return new ResponseEntity<>(valoracionesDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}