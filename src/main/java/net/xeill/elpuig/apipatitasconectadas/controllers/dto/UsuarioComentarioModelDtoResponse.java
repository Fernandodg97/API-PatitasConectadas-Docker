package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import java.time.LocalDateTime;

public class UsuarioComentarioModelDtoResponse {
    
    private Long id;
    private Long comentarioId;
    private Long postId;
    private Long usuarioId;
    private boolean like;
    private LocalDateTime fecha;

    // Constructor vacío
    public UsuarioComentarioModelDtoResponse() {
    }

    // Constructor con todos los campos
    public UsuarioComentarioModelDtoResponse(Long id, Long comentarioId, Long postId, Long usuarioId, boolean like, LocalDateTime fecha) {
        this.id = id;
        this.comentarioId = comentarioId;
        this.postId = postId;
        this.usuarioId = usuarioId;
        this.like = like;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getComentarioId() {
        return comentarioId;
    }

    public void setComentarioId(Long comentarioId) {
        this.comentarioId = comentarioId;
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

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
} 