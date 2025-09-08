package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import net.xeill.elpuig.apipatitasconectadas.models.MascotaModel;
import java.util.Date;

public class MascotaModelDtoRequest {
    
    private Long usuarioId;
    
    private String nombre;
    
    private String genero;
    
    private String especie;
    
    private String foto;
    
    private String fechaNacimiento;
    
    public MascotaModel toDomain() {
        MascotaModel mascota = new MascotaModel();
        mascota.setUsuarioId(this.usuarioId);
        mascota.setNombre(this.nombre);
        mascota.setGenero(this.genero);
        mascota.setEspecie(this.especie);
        mascota.setFoto(this.foto);
        if (this.fechaNacimiento != null && !this.fechaNacimiento.isEmpty()) {
            mascota.setFechaNacimiento(new Date(this.fechaNacimiento));
        }
        return mascota;
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