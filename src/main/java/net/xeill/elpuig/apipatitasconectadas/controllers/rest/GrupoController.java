package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.*;
import net.xeill.elpuig.apipatitasconectadas.models.*;
import net.xeill.elpuig.apipatitasconectadas.services.*;

/**
 * Controlador REST para gestionar operaciones relacionadas con grupos.
 * Proporciona endpoints para crear, leer, actualizar y eliminar grupos,
 * y gestionar la asignación de administradores a los grupos.
 * Todas las respuestas son encapsuladas en objetos ResponseEntity para un manejo
 * consistente de la comunicación HTTP.
 */
@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private UsuarioGrupoService usuarioGrupoService;

    @Autowired
    private UserService userService;

    /**
     * Obtiene todos los grupos existentes
     * @return ResponseEntity con lista de grupos en formato DTO o mensaje de error
     */
    @GetMapping
    public ResponseEntity<?> getAllGrupos() {
        try {
            List<GrupoModelDtoResponse> grupos = grupoService.getGrupos().stream()
                .map(GrupoModelDtoResponse::new)
                .toList();
            return ResponseEntity.ok(grupos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener los grupos: " + e.getMessage());
        }
    }

    /**
     * Crea un nuevo grupo y asigna al usuario como administrador
     * @param request Datos del grupo en formato DTO
     * @param usuarioId ID del usuario administrador
     * @return ResponseEntity con el grupo creado o mensaje de error
     */
    @PostMapping
    public ResponseEntity<?> createGrupo(@RequestBody GrupoModelDtoRequest request, 
                                       @RequestParam Long usuarioId) {
        try {
            // Validación básica de los datos de entrada
            if (request.getNombre() == null || request.getNombre().isEmpty()) {
                return ResponseEntity.badRequest().body("El nombre del grupo es obligatorio");
            }

            GrupoModel nuevoGrupo = request.toDomain();
            GrupoModel grupoCreado = grupoService.saveGrupo(nuevoGrupo);

            // Asignar usuario como administrador del grupo
            UserModel usuario = userService.getById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            UsuarioGrupoModel relacion = new UsuarioGrupoModel();
            relacion.setGrupo(grupoCreado);
            relacion.setUsuario(usuario);
            relacion.setRol("Admin");
            usuarioGrupoService.saveUsuarioGrupo(relacion);

            // Actualizar la lista de usuarios del grupo
            grupoCreado.getUsuarios().add(relacion);
            grupoService.saveGrupo(grupoCreado);

            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new GrupoModelDtoResponse(grupoCreado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al crear el grupo: " + e.getMessage());
        }
    }

    /**
     * Obtiene un grupo específico por su ID
     * @param id ID del grupo a buscar
     * @return ResponseEntity con el grupo encontrado o mensaje de error
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getGrupoById(@PathVariable Long id) {
        try {
            GrupoModel grupo = grupoService.getById(id)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));
            return ResponseEntity.ok(new GrupoModelDtoResponse(grupo));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener el grupo: " + e.getMessage());
        }
    }

    /**
     * Actualiza un grupo existente
     * @param id ID del grupo a actualizar
     * @param request Datos actualizados del grupo
     * @return ResponseEntity con el grupo actualizado o mensaje de error
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGrupo(@PathVariable Long id, 
                                       @RequestBody GrupoModelDtoRequest request) {
        try {
            // Verificar que el grupo existe
            GrupoModel grupoExistente = grupoService.getById(id)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

            // Actualizar datos del grupo
            grupoExistente.setNombre(request.getNombre());
            grupoExistente.setDescripcion(request.getDescripcion());

            GrupoModel grupoActualizado = grupoService.saveGrupo(grupoExistente);
            return ResponseEntity.ok(new GrupoModelDtoResponse(grupoActualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al actualizar el grupo: " + e.getMessage());
        }
    }

    /**
     * Elimina un grupo existente
     * @param id ID del grupo a eliminar
     * @return ResponseEntity con mensaje de confirmación o error
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGrupo(@PathVariable Long id) {
        try {
            // Verificar que el grupo existe antes de eliminar
            if (!grupoService.existsById(id)) {
                throw new RuntimeException("Grupo no encontrado");
            }

            grupoService.deleteGrupo(id);
            return ResponseEntity.ok("Grupo eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al eliminar el grupo: " + e.getMessage());
        }
    }
}