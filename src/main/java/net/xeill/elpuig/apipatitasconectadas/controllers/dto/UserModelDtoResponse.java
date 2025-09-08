package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import net.xeill.elpuig.apipatitasconectadas.models.UserModel;

public class UserModelDtoResponse {
    
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    
    public UserModelDtoResponse(UserModel usuario) {
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.email = usuario.getEmail();
    }
    
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
} 