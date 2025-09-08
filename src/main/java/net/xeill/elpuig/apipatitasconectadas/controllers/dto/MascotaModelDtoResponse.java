package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import net.xeill.elpuig.apipatitasconectadas.models.MascotaModel;
import java.text.SimpleDateFormat;

public class MascotaModelDtoResponse {
    
    private Long id;
    private Long usuarioId;
    private String nombre;
    private String genero;
    private String especie;
    private String foto;
    private String fechaNacimiento;
    
    public MascotaModelDtoResponse(MascotaModel mascota) {
        this.id = mascota.getId();
        this.usuarioId = mascota.getUsuarioId();
        this.nombre = mascota.getNombre();
        this.genero = mascota.getGenero();
        this.especie = mascota.getEspecie();
        this.foto = mascota.getFoto();
        
        // Formatear la fecha en formato yyyy-MM-dd
        if (mascota.getFechaNacimiento() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            this.fechaNacimiento = formatter.format(mascota.getFechaNacimiento());
        } else {
            this.fechaNacimiento = null;
        }
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getGenero() {
        return genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public String getEspecie() {
        return especie;
    }
    
    public void setEspecie(String especie) {
        this.especie = especie;
    }
    
    public String getFoto() {
        return foto;
    }
    
    public void setFoto(String foto) {
        this.foto = foto;
    }
    
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
} 