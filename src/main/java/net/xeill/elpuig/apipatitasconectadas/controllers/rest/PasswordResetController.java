package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.PasswordResetRequest;
import net.xeill.elpuig.apipatitasconectadas.services.PasswordResetService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}, allowedHeaders = "*")
public class PasswordResetController {

    private static final Logger logger = LoggerFactory.getLogger(PasswordResetController.class);

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/restablecer-contrasena")
    public ResponseEntity<?> restablecerContrasena(@RequestBody Map<String, String> request) {
        logger.info("Solicitud de restablecimiento de contraseña recibida");
        
        try {
            String email = request.get("email");
            String nuevaContrasena = request.get("nuevaContrasena");

            if (email == null || nuevaContrasena == null) {
                logger.warn("Intento de restablecimiento con datos incompletos");
                Map<String, String> error = new HashMap<>();
                error.put("error", "Email y nueva contraseña son requeridos");
                return ResponseEntity.badRequest().body(error);
            }

            passwordResetService.resetPassword(email, nuevaContrasena);
            logger.info("Contraseña restablecida exitosamente para email: {}", email);
            
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Contraseña actualizada exitosamente");
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            logger.error("Error al restablecer contraseña: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            logger.error("Error interno del servidor al restablecer contraseña", e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error interno del servidor. Por favor, intente más tarde.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
} 