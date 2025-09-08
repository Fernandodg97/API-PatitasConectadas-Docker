package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import net.xeill.elpuig.apipatitasconectadas.models.UsuarioGrupoModel;

public class UsuarioGrupoModelDtoResponse {
    
    private Long id;
    private Long grupoId;
    private String nombreGrupo;
    private Long usuarioId;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String rol;
    
    public UsuarioGrupoModelDtoResponse(UsuarioGrupoModel usuarioGrupo) {
        this.id = usuarioGrupo.getId();
        
        if (usuarioGrupo.getGrupo() != null) {
            this.grupoId = usuarioGrupo.getGrupo().getId();
            this.nombreGrupo = usuarioGrupo.getGrupo().getNombre();
        }
        
        if (usuarioGrupo.getUsuario() != null) {
            this.usuarioId = usuarioGrupo.getUsuario().getId();
            this.nombreUsuario = usuarioGrupo.getUsuario().getNombre();
            this.apellidoUsuario = usuarioGrupo.getUsuario().getApellido();
        }
        
        this.rol = usuarioGrupo.getRol();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getGrupoId() {
        return grupoId;
    }
    
    public void setGrupoId(Long grupoId) {
        this.grupoId = grupoId;
    }
    
    public String getNombreGrupo() {
        return nombreGrupo;
    }
    
    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }
    
    public Long getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    public String getApellidoUsuario() {
        return apellidoUsuario;
    }
    
    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
} 