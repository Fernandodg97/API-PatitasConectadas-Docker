package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import java.time.LocalDateTime;
import net.xeill.elpuig.apipatitasconectadas.models.ValoracionModel;

public class ValoracionModelDtoResponse {
    
    private Long id;
    private Long autorId;
    private String nombreAutor;
    private String apellidoAutor;
    private Long receptorId;
    private String nombreReceptor;
    private String apellidoReceptor;
    private Integer puntuacion;
    private String contenido;
    private LocalDateTime fecha;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public ValoracionModelDtoResponse(ValoracionModel valoracion) {
        this.id = valoracion.getId();
        
        if (valoracion.getAutor() != null) {
            this.autorId = valoracion.getAutor().getId();
            this.nombreAutor = valoracion.getAutor().getNombre();
            this.apellidoAutor = valoracion.getAutor().getApellido();
        }
        
        if (valoracion.getReceptor() != null) {
            this.receptorId = valoracion.getReceptor().getId();
            this.nombreReceptor = valoracion.getReceptor().getNombre();
            this.apellidoReceptor = valoracion.getReceptor().getApellido();
        }
        
        this.puntuacion = valoracion.getPuntuacion();
        this.contenido = valoracion.getContenido();
        this.fecha = valoracion.getFecha();
        this.createdAt = valoracion.getCreatedAt();
        this.updatedAt = valoracion.getUpdatedAt();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getAutorId() {
        return autorId;
    }
    
    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }
    
    public String getNombreAutor() {
        return nombreAutor;
    }
    
    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }
    
    public String getApellidoAutor() {
        return apellidoAutor;
    }
    
    public void setApellidoAutor(String apellidoAutor) {
        this.apellidoAutor = apellidoAutor;
    }
    
    public Long getReceptorId() {
        return receptorId;
    }
    
    public void setReceptorId(Long receptorId) {
        this.receptorId = receptorId;
    }
    
    public String getNombreReceptor() {
        return nombreReceptor;
    }
    
    public void setNombreReceptor(String nombreReceptor) {
        this.nombreReceptor = nombreReceptor;
    }
    
    public String getApellidoReceptor() {
        return apellidoReceptor;
    }
    
    public void setApellidoReceptor(String apellidoReceptor) {
        this.apellidoReceptor = apellidoReceptor;
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