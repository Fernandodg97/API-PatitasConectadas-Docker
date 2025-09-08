package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import java.sql.Date;
import net.xeill.elpuig.apipatitasconectadas.models.PerfilModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;

public class PerfilModelDtoResponse {
    
    private Long id;
    private Long usuario_id;
    private String descripcion;
    private Date fecha_nacimiento;
    private String img;
    
    public PerfilModelDtoResponse(PerfilModel perfil) {
        this.id = perfil.getId();
        this.usuario_id = perfil.getUsuario_id();
        this.descripcion = perfil.getDescripcion();
        this.fecha_nacimiento = perfil.getFecha_nacimiento();
        this.img = perfil.getImg();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUsuario_id() {
        return usuario_id;
    }
    
    public void setUsuario_id(Long usuario_id) {
        this.usuario_id = usuario_id;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }
    
    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }
    
    public String getImg() {
        return img;
    }
    
    public void setImg(String img) {
        this.img = img;
    }
} 