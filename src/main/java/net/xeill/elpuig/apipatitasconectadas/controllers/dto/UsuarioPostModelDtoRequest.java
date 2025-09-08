package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import jakarta.validation.constraints.NotNull;

public class UsuarioPostModelDtoRequest {
    
    @NotNull(message = "El ID del post es obligatorio")
    private Long postId;
    
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

    // Getters y Setters
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
} 