package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class PasswordResetRequest {
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    private String email;

    // Constructor vacío
    public PasswordResetRequest() {}

    // Constructor con email
    public PasswordResetRequest(String email) {
        this.email = email;
    }

    // Getters y Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
} 