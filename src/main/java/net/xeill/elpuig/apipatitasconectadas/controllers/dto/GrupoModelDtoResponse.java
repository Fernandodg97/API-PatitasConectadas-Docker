package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import net.xeill.elpuig.apipatitasconectadas.models.GrupoModel;

public class GrupoModelDtoResponse {

    private Long id;
    private String nombre;
    private String descripcion;

    public GrupoModelDtoResponse(GrupoModel grupo) {
        this.nombre = grupo.getNombre();
        this.descripcion = grupo.getDescripcion();
        this.id = grupo.getId();
    }

    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getDescripcion() {
        return descripcion;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
}
