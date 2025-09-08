package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import java.time.LocalDateTime;

public class UsuarioPostModelDtoResponse {
    
    private Long id;
    private Long postId;
    private Long usuarioId;
    private LocalDateTime fecha;

    // Constructor vacío
    public UsuarioPostModelDtoResponse() {
    }

    // Constructor con todos los campos
    public UsuarioPostModelDtoResponse(Long id, Long postId, Long usuarioId, LocalDateTime fecha) {
        this.id = id;
        this.postId = postId;
        this.usuarioId = usuarioId;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
} 