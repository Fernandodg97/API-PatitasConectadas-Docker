package net.xeill.elpuig.apipatitasconectadas.models;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "valoracion")
public class ValoracionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    @NotNull(message = "El autor es obligatorio")
    private UserModel autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receptor_id", nullable = false)
    @NotNull(message = "El receptor es obligatorio")
    private UserModel receptor;

    @Column(nullable = false)
    @Min(1) @Max(5) // Si es una puntuación de 1 a 5
    private Integer puntuacion;

    @Column(nullable = false)
    @Size(message = "El contenido es obligatoria")
    private String contenido;

    @Column(nullable = false)
    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime fecha;

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

    public UserModel getAutor() {
        return autor;
    }

    public void setAutor(UserModel autor) {
        this.autor = autor;
    }

    public UserModel getReceptor() {
        return receptor;
    }

    public void setReceptor(UserModel receptor) {
        this.receptor = receptor;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
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
