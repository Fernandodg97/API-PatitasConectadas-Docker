package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import net.xeill.elpuig.apipatitasconectadas.models.PerfilModel;
import net.xeill.elpuig.apipatitasconectadas.models.MascotaModel;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.AuthDtoRequest;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.AuthDtoResponse;
import net.xeill.elpuig.apipatitasconectadas.repositories.*;
import net.xeill.elpuig.apipatitasconectadas.security.JwtUtil;
import net.xeill.elpuig.apipatitasconectadas.services.AuthService;

/**
 * Controlador REST para gestionar operaciones de autenticación y registro.
 * Proporciona endpoints para iniciar sesión, registrar nuevos usuarios
 * y obtener información del usuario autenticado.
 * Todas las respuestas son encapsuladas en objetos ResponseEntity para un manejo
 * consistente de la comunicación HTTP.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private AuthService authService;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserRepository userRepository;
    @Autowired private PerfilRepository perfilRepository;
    @Autowired private MascotaRepository mascotaRepository;

    /**
     * Autentica a un usuario mediante email y contraseña
     * @param request DTO con las credenciales del usuario
     * @return ResponseEntity con token JWT o mensaje de error
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDtoRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(Map.of("token", token));
    }

    /**
     * Registra un nuevo usuario en el sistema
     * @param request DTO con los datos del usuario
     * @return ResponseEntity con datos del usuario creado o mensaje de error
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthDtoRequest request) {
        try {
            Map<String, Object> result = authService.register(
                request.getEmail(), 
                request.getPassword(), 
                request.getNombre(), 
                request.getApellido()
            );
            
            UserModel user = (UserModel) result.get("user");
            String token = (String) result.get("token");
            
            AuthDtoResponse response = new AuthDtoResponse(
                user.getId(),
                user.getNombre(),
                user.getApellido(),
                user.getEmail()
            );
            response.setToken(token);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Obtiene información del usuario autenticado
     * @param authHeader Cabecera de autorización con el token JWT
     * @return ResponseEntity con datos del usuario, su perfil y mascotas o mensaje de error
     */
    @GetMapping("/me")
    public ResponseEntity<?> getMe(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String email = jwtUtil.extractUsername(token);
            
            UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            Optional<PerfilModel> perfil = perfilRepository.findById(user.getId());
            List<MascotaModel> mascotas = mascotaRepository.findByUsuarioId(user.getId());
            
            AuthDtoResponse response = new AuthDtoResponse(
                user.getId(),
                user.getNombre(),
                user.getApellido(),
                user.getEmail(),
                perfil.orElse(null),
                mascotas
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", e.getMessage()));
        }
    }
}