package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import java.time.LocalDateTime;
import net.xeill.elpuig.apipatitasconectadas.models.PostModel;
import net.xeill.elpuig.apipatitasconectadas.models.GrupoModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import org.springframework.web.multipart.MultipartFile;

public class PostModelDtoRequest {
    
    private Long grupoId;
    
    private Long creadorId;
    
    private String contenido;
    
    private LocalDateTime fecha;
    
    private MultipartFile imagen;
    
    public PostModel toDomain(UserModel creador, GrupoModel grupo) {
        PostModel post = new PostModel();
        post.setGrupo(grupo);
        post.setCreador(creador);
        post.setContenido(this.contenido);
        post.setFecha(this.fecha != null ? this.fecha : LocalDateTime.now());
        return post;
    }
    
    public Long getGrupoId() {
        return grupoId;
    }
    
    public void setGrupoId(Long grupoId) {
        this.grupoId = grupoId;
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
    
    public MultipartFile getImagen() {
        return imagen;
    }
    
    public void setImagen(MultipartFile imagen) {
        this.imagen = imagen;
    }
} 