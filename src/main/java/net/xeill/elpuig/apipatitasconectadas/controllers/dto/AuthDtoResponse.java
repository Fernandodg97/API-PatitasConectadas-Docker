package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import net.xeill.elpuig.apipatitasconectadas.models.PerfilModel;
import net.xeill.elpuig.apipatitasconectadas.models.MascotaModel;
import java.util.List;

/**
 * DTO para las respuestas de autenticación
 */
public class AuthDtoResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String token;
    private PerfilModel perfil;
    private List<MascotaModel> mascotas;

    // Constructor vacío
    public AuthDtoResponse() {}

    // Constructor para login
    public AuthDtoResponse(String token) {
        this.token = token;
    }

    // Constructor para registro
    public AuthDtoResponse(Long id, String nombre, String apellido, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
    }

    // Constructor para getMe
    public AuthDtoResponse(Long id, String nombre, String apellido, String email, 
                          PerfilModel perfil, List<MascotaModel> mascotas) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.perfil = perfil;
        this.mascotas = mascotas;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public PerfilModel getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilModel perfil) {
        this.perfil = perfil;
    }

    public List<MascotaModel> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<MascotaModel> mascotas) {
        this.mascotas = mascotas;
    }
} 