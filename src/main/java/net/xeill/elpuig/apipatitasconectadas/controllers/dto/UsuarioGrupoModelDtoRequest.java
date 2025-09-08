package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import net.xeill.elpuig.apipatitasconectadas.models.GrupoModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import net.xeill.elpuig.apipatitasconectadas.models.UsuarioGrupoModel;

public class UsuarioGrupoModelDtoRequest {
    
    private Long grupoId;
    
    private Long usuarioId;
    
    private String rol;
    
    public UsuarioGrupoModel toDomain(GrupoModel grupo, UserModel usuario) {
        UsuarioGrupoModel usuarioGrupo = new UsuarioGrupoModel();
        usuarioGrupo.setGrupo(grupo);
        usuarioGrupo.setUsuario(usuario);
        usuarioGrupo.setRol(this.rol);
        return usuarioGrupo;
    }
    
    public Long getGrupoId() {
        return grupoId;
    }
    
    public void setGrupoId(Long grupoId) {
        this.grupoId = grupoId;
    }
    
    public Long getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
} 