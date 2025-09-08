package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.xeill.elpuig.apipatitasconectadas.controllers.dto.UsuarioGrupoModelDtoRequest;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.UsuarioGrupoModelDtoResponse;
import net.xeill.elpuig.apipatitasconectadas.models.*;
import net.xeill.elpuig.apipatitasconectadas.services.*;

/**
 * Controlador REST para gestionar la relación entre usuarios y grupos.
 * Proporciona endpoints para crear, leer, actualizar y eliminar asociaciones entre usuarios y grupos,
 * así como para consultar los grupos de un usuario o los usuarios de un grupo.
 * Todas las respuestas son encapsuladas en objetos ResponseEntity para un manejo
 * consistente de la comunicación HTTP.
 */
@RestController
@RequestMapping("/usuario-grupo")
public class UsuarioGrupoController {

    @Autowired
    private UsuarioGrupoService usuarioGrupoService;

    @Autowired
    private UserService userService;

    @Autowired
    private GrupoService grupoService;

    /**
     * Obtiene todas las relaciones entre usuarios y grupos
     * @return ResponseEntity con lista de relaciones en formato DTO o mensaje de error
     */
    @GetMapping
    public ResponseEntity<?> getUsuarioGrupos() {
        try {
            List<UsuarioGrupoModelDtoResponse> relaciones = usuarioGrupoService.getUsuarioGrupos().stream()
                .map(UsuarioGrupoModelDtoResponse::new)
                .collect(Collectors.toList());
            return ResponseEntity.ok(relaciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener las relaciones: " + e.getMessage());
        }
    }

    /**
     * Crea una nueva relación entre un usuario y un grupo
     * @param usuarioGrupoDto Datos de la relación en formato DTO
     * @return ResponseEntity con la relación creada o mensaje de error
     */
    @PostMapping
    public ResponseEntity<?> saveUsuarioGrupo(@RequestBody UsuarioGrupoModelDtoRequest usuarioGrupoDto) {
        try {
            // Obtener los objetos completos de usuario y grupo
            Long usuarioId = usuarioGrupoDto.getUsuarioId();
            Long grupoId = usuarioGrupoDto.getGrupoId();

            Optional<UserModel> usuario = userService.getById(usuarioId);
            Optional<GrupoModel> grupo = grupoService.getById(grupoId);

            if (usuario.isEmpty() || grupo.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario o Grupo no encontrado");
            }

            // Verificar si ya existe una relación entre el usuario y el grupo
            UsuarioGrupoModel existingRelation = usuarioGrupoService.getUsuarioGrupoByUsuarioIdAndGrupoId(usuarioId, grupoId);
            if (existingRelation != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El usuario ya tiene un rol asignado en este grupo. Use el endpoint PUT para actualizar el rol.");
            }

            // Crear el modelo a partir del DTO
            UsuarioGrupoModel usuarioGrupo = new UsuarioGrupoModel();
            usuarioGrupo.setUsuario(usuario.get());
            usuarioGrupo.setGrupo(grupo.get());
            usuarioGrupo.setRol(usuarioGrupoDto.getRol());

            UsuarioGrupoModel saved = usuarioGrupoService.saveUsuarioGrupo(usuarioGrupo);
            return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioGrupoModelDtoResponse(saved));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al guardar: " + e.getMessage());
        }
    }

    /**
     * Obtiene una relación específica entre usuario y grupo por su ID
     * @param id ID de la relación a buscar
     * @return ResponseEntity con la relación encontrada o mensaje de error
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioGrupoById(@PathVariable Long id) {
        try {
            Optional<UsuarioGrupoModel> relacion = usuarioGrupoService.getById(id);
            
            if (relacion.isPresent()) {
                return ResponseEntity.ok(new UsuarioGrupoModelDtoResponse(relacion.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró la relación usuario-grupo");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener la relación: " + e.getMessage());
        }
    }

    /**
     * Actualiza una relación existente entre usuario y grupo
     * @param id ID de la relación a actualizar
     * @param usuarioGrupoDto Datos actualizados de la relación
     * @return ResponseEntity con la relación actualizada o mensaje de error
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuarioGrupoById(@PathVariable Long id,
            @RequestBody UsuarioGrupoModelDtoRequest usuarioGrupoDto) {
        try {
            // Obtener los objetos completos de usuario y grupo
            Long usuarioId = usuarioGrupoDto.getUsuarioId();
            Long grupoId = usuarioGrupoDto.getGrupoId();

            Optional<UserModel> usuario = userService.getById(usuarioId);
            Optional<GrupoModel> grupo = grupoService.getById(grupoId);

            if (usuario.isEmpty() || grupo.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario o Grupo no encontrado");
            }

            // Crear el modelo a partir del DTO
            UsuarioGrupoModel usuarioGrupo = new UsuarioGrupoModel();
            usuarioGrupo.setUsuario(usuario.get());
            usuarioGrupo.setGrupo(grupo.get());
            usuarioGrupo.setRol(usuarioGrupoDto.getRol());

            UsuarioGrupoModel updated = usuarioGrupoService.updateByID(usuarioGrupo, id);
            return ResponseEntity.ok(new UsuarioGrupoModelDtoResponse(updated));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al actualizar: " + e.getMessage());
        }
    }

    /**
     * Elimina una relación existente entre usuario y grupo
     * @param id ID de la relación a eliminar
     * @return ResponseEntity con mensaje de confirmación o error
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            boolean ok = usuarioGrupoService.deleteUsuarioGrupo(id);
            if (ok) {
                return ResponseEntity.ok("Eliminado correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado para eliminar");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al eliminar: " + e.getMessage());
        }
    }

    /**
     * Obtiene todos los grupos a los que pertenece un usuario
     * @param usuarioId ID del usuario cuyos grupos se quieren obtener
     * @return ResponseEntity con lista de relaciones del usuario con sus grupos o mensaje de error
     */
    @GetMapping("/usuario/{usuario_id}")
    public ResponseEntity<?> getGruposByUsuarioId(@PathVariable("usuario_id") Long usuarioId) {
        try {
            List<UsuarioGrupoModelDtoResponse> relaciones = usuarioGrupoService.getGruposByUsuarioId(usuarioId).stream()
                .map(UsuarioGrupoModelDtoResponse::new)
                .collect(Collectors.toList());
            return ResponseEntity.ok(relaciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener los grupos del usuario: " + e.getMessage());
        }
    }

    /**
     * Obtiene todos los usuarios que pertenecen a un grupo
     * @param grupoId ID del grupo cuyos usuarios se quieren obtener
     * @return ResponseEntity con lista de relaciones del grupo con sus usuarios o mensaje de error
     */
    @GetMapping("/grupo/{grupo_id}")
    public ResponseEntity<?> getUsuariosByGrupoId(@PathVariable("grupo_id") Long grupoId) {
        try {
            List<UsuarioGrupoModelDtoResponse> relaciones = usuarioGrupoService.getUsuariosByGrupoId(grupoId).stream()
                .map(UsuarioGrupoModelDtoResponse::new)
                .collect(Collectors.toList());
            return ResponseEntity.ok(relaciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener los usuarios del grupo: " + e.getMessage());
        }
    }

    /**
     * Obtiene una relación específica entre un usuario y un grupo basada en sus IDs
     * @param usuarioId ID del usuario en la relación
     * @param grupoId ID del grupo en la relación
     * @return ResponseEntity con la relación encontrada o mensaje de error
     */
    @GetMapping("/relacion/{usuarioId}/{grupoId}")
    public ResponseEntity<?> getUsuarioGrupoByUsuarioIdAndGrupoId(
            @PathVariable("usuarioId") Long usuarioId,
            @PathVariable("grupoId") Long grupoId) {
        try {
            UsuarioGrupoModel relation = usuarioGrupoService.getUsuarioGrupoByUsuarioIdAndGrupoId(usuarioId, grupoId);
            if (relation != null) {
                return ResponseEntity.ok(new UsuarioGrupoModelDtoResponse(relation));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Relación no encontrada");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener la relación: " + e.getMessage());
        }
    }
}
