package net.xeill.elpuig.apipatitasconectadas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.xeill.elpuig.apipatitasconectadas.repositories.UserRepository;
import net.xeill.elpuig.apipatitasconectadas.security.JwtUtil;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio que gestiona las operaciones de autenticación y registro de usuarios.
 * Proporciona métodos para iniciar sesión y registrar nuevos usuarios,
 * incluyendo la generación de tokens JWT para la autenticación.
 */
@Service
public class AuthService {
    @Autowired private UserRepository userRepository;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private PasswordEncoder passwordEncoder;

    /**
     * Autentica a un usuario mediante email y contraseña.
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @return Token JWT generado para el usuario autenticado
     * @throws RuntimeException si el usuario no existe o la contraseña es incorrecta
     */
    public String login(String email, String password) {
        UserModel user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        //Verificación de la contraseña
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return jwtUtil.generateToken(user);
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * @param email Email del nuevo usuario
     * @param password Contraseña del nuevo usuario
     * @param nombre Nombre del nuevo usuario
     * @param apellido Apellido del nuevo usuario
     * @return Mapa con el usuario registrado y su token JWT
     * @throws RuntimeException si el email ya está registrado en el sistema
     */
    public Map<String, Object> register(String email, String password, String nombre, String apellido) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email ya registrado");
        }
        
        // Crear y guardar el nuevo usuario
        UserModel newUser = new UserModel();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setNombre(nombre);
        newUser.setApellido(apellido);
        UserModel savedUser = userRepository.save(newUser);
        
        // Generar el token
        String token = jwtUtil.generateToken(savedUser);
        
        // Crear respuesta con usuario y token
        Map<String, Object> response = new HashMap<>();
        response.put("user", savedUser);
        response.put("token", token);
        
        return response;
    }
}

