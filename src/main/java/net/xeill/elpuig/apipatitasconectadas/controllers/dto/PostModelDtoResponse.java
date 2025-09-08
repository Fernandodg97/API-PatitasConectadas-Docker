package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import net.xeill.elpuig.apipatitasconectadas.models.PostModel;

public class PostModelDtoResponse {
    
    private Long id;
    private Long grupoId;
    private String nombreGrupo;
    private Long creadorId;
    private String nombreCreador;
    private String apellidoCreador;
    private String contenido;
    private LocalDateTime fecha;
    private String img;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ComentarioModelDtoResponse> comentarios;
    
    public PostModelDtoResponse(PostModel post) {
        this.id = post.getId();
        
        if (post.getGrupo() != null) {
            this.grupoId = post.getGrupo().getId();
            this.nombreGrupo = post.getGrupo().getNombre();
        }
        
        if (post.getCreador() != null) {
            this.creadorId = post.getCreador().getId();
            this.nombreCreador = post.getCreador().getNombre();
            this.apellidoCreador = post.getCreador().getApellido();
        }
        
        this.contenido = post.getContenido();
        this.fecha = post.getFecha();
        this.img = post.getImg();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        
        if (post.getComentarios() != null) {
            this.comentarios = post.getComentarios().stream()
                .map(ComentarioModelDtoResponse::new)
                .collect(Collectors.toList());
        }
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getGrupoId() {
        return grupoId;
    }
    
    public void setGrupoId(Long grupoId) {
        this.grupoId = grupoId;
    }
    
    public String getNombreGrupo() {
        return nombreGrupo;
    }
    
    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
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
    
    public List<ComentarioModelDtoResponse> getComentarios() {
        return comentarios;
    }
    
    public void setComentarios(List<ComentarioModelDtoResponse> comentarios) {
        this.comentarios = comentarios;
    }
} 