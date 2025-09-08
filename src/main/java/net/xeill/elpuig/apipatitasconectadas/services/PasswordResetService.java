package net.xeill.elpuig.apipatitasconectadas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import net.xeill.elpuig.apipatitasconectadas.repositories.UserRepository;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void resetPassword(String email, String newPassword) {
        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("El email es requerido");
        }

        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new RuntimeException("La nueva contraseña es requerida");
        }

        if (newPassword.length() < 8) {
            throw new RuntimeException("La contraseña debe tener al menos 8 caracteres");
        }

        UserModel user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("No se encontró ningún usuario con ese email"));

        // Verificar que la nueva contraseña sea diferente a la actual
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new RuntimeException("La nueva contraseña debe ser diferente a la actual");
        }

        // Actualizar la contraseña
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
} 