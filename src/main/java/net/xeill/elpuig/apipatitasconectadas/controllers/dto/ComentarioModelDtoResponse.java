package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import java.time.LocalDateTime;
import net.xeill.elpuig.apipatitasconectadas.models.ComentarioModel;

public class ComentarioModelDtoResponse {

    private Long id;
    private Long postId;
    private Long creadorId;
    private String nombreCreador;
    private String apellidoCreador;
    private String contenido;
    private LocalDateTime fecha;
    private String img;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ComentarioModelDtoResponse(ComentarioModel comentario) {
        this.id = comentario.getId();
        if (comentario.getPost() != null) {
            this.postId = comentario.getPost().getId();
        }
        if (comentario.getCreador() != null) {
            this.creadorId = comentario.getCreador().getId();
            this.nombreCreador = comentario.getCreador().getNombre();
            this.apellidoCreador = comentario.getCreador().getApellido();
        }
        this.contenido = comentario.getContenido();
        this.fecha = comentario.getFecha();
        this.img = comentario.getImg();
        this.createdAt = comentario.getCreatedAt();
        this.updatedAt = comentario.getUpdatedAt();
    }

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

    public Long getCreadorId() {
        return creadorId;
    }

    public void setCreadorId(Long creadorId) {
        this.creadorId = creadorId;
    }

    public String getNombreCreador() {
        return nombreCreador;
    }

    public void setNombreCreador(String nombreCreador) {
        this.nombreCreador = nombreCreador;
    }

    public String getApellidoCreador() {
        return apellidoCreador;
    }

    public void setApellidoCreador(String apellidoCreador) {
        this.apellidoCreador = apellidoCreador;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
} 