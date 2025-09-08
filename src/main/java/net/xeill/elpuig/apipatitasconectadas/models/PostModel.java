package net.xeill.elpuig.apipatitasconectadas.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "post")
public class PostModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_id")
    private GrupoModel grupo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creador_id", nullable = false)
    @NotNull(message = "El creador es obligatorio")
    private UserModel creador;

    @Column
    @Size(max = 255, message = "El contenido no puede tener más de 1000 caracteres")
    private String contenido;

    @Column(nullable = false)
    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime fecha;

    @Column
    private String img;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // RELACIÓN CON COMENTARIOS
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ComentarioModel> comentarios;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GrupoModel getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoModel grupo) {
        this.grupo = grupo;
    }

    public UserModel getCreador() {
        return creador;
    }

    public void setCreador(UserModel creador) {
        this.creador = creador;
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

    public List<ComentarioModel> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioModel> comentarios) {
        this.comentarios = comentarios;
    }
}
