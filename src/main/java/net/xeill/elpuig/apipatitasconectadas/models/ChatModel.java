package net.xeill.elpuig.apipatitasconectadas.models;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "chat")
public class ChatModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emisor_id", nullable = false)
    @NotNull(message = "El emisor es obligatorio")
    private UserModel emisor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receptor_id", nullable = false)
    @NotNull(message = "El receptor es obligatorio")
    private UserModel receptor;

    @Column(nullable = false)
    private boolean visto;

    @Column(nullable = false)
    @Size(max = 255, message = "El contenido no puede tener más de 255 caracteres")
    @NotNull(message = "El contenido es obligatorio")
    private String contenido;

    @Column(nullable = false)
    @NotNull(message = "La fecha y hora son obligatorias")
    private LocalDateTime fechaHora;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserModel getEmisor() {
        return emisor;
    }

    public void setEmisor(UserModel emisor) {
        this.emisor = emisor;
    }

    public UserModel getReceptor() {
        return receptor;
    }

    public void setReceptor(UserModel receptor) {
        this.receptor = receptor;
    }

    public boolean isVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
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