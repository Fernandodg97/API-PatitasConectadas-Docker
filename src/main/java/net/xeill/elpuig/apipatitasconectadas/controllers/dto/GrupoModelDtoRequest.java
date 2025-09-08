package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import net.xeill.elpuig.apipatitasconectadas.models.GrupoModel;

public class GrupoModelDtoRequest {
    

    private String nombre;


    private String descripcion;

    public GrupoModel toDomain(){
        GrupoModel grupo = new GrupoModel();
        grupo.setNombre(this.nombre);
        grupo.setDescripcion(this.descripcion);
        return grupo;
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
    
}
