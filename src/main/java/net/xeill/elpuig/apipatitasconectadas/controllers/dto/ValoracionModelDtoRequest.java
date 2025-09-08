package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import java.time.LocalDateTime;
import net.xeill.elpuig.apipatitasconectadas.models.ValoracionModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;

public class ValoracionModelDtoRequest {
    
    private Long autorId;
    
    private Long receptorId;
    
    private Integer puntuacion;
    
    private String contenido;
    
    private LocalDateTime fecha;
    
    public ValoracionModel toDomain(UserModel autor, UserModel receptor) {
        ValoracionModel valoracion = new ValoracionModel();
        valoracion.setAutor(autor);
        valoracion.setReceptor(receptor);
        valoracion.setPuntuacion(this.puntuacion);
        valoracion.setContenido(this.contenido);
        valoracion.setFecha(this.fecha != null ? this.fecha : LocalDateTime.now());
        return valoracion;
    }
    
    public Long getAutorId() {
        return autorId;
    }
    
    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }
    
    public Long getReceptorId() {
        return receptorId;
    }
    
    public void setReceptorId(Long receptorId) {
        this.receptorId = receptorId;
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
} 