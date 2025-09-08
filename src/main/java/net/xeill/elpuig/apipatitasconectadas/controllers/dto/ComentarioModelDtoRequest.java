package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import java.time.LocalDateTime;
import net.xeill.elpuig.apipatitasconectadas.models.ComentarioModel;
import net.xeill.elpuig.apipatitasconectadas.models.PostModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;

public class ComentarioModelDtoRequest {
    
    private Long postId;
    
    private Long creadorId;
    
    private String contenido;
    
    private LocalDateTime fecha;
    
    private String img;
    
    public ComentarioModel toDomain(PostModel post, UserModel creador) {
        ComentarioModel comentario = new ComentarioModel();
        comentario.setPost(post);
        comentario.setCreador(creador);
        comentario.setContenido(this.contenido);
        comentario.setFecha(this.fecha != null ? this.fecha : LocalDateTime.now());
        comentario.setImg(this.img);
        return comentario;
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
} 