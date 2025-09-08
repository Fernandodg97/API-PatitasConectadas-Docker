package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import net.xeill.elpuig.apipatitasconectadas.models.SeguidoModel;

public class SeguidoModelDtoResponse {
    
    private Long id;
    private Long usuarioQueSigueId;
    private Long usuarioQueEsSeguidoId;
    
    public SeguidoModelDtoResponse(SeguidoModel seguido) {
        this.id = seguido.getId();
        this.usuarioQueSigueId = seguido.getUsuarioQueSigueId();
        this.usuarioQueEsSeguidoId = seguido.getUsuarioQueEsSeguidoId();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUsuarioQueSigueId() {
        return usuarioQueSigueId;
    }
    
    public void setUsuarioQueSigueId(Long usuarioQueSigueId) {
        this.usuarioQueSigueId = usuarioQueSigueId;
    }
    
    public Long getUsuarioQueEsSeguidoId() {
        return usuarioQueEsSeguidoId;
    }
    
    public void setUsuarioQueEsSeguidoId(Long usuarioQueEsSeguidoId) {
        this.usuarioQueEsSeguidoId = usuarioQueEsSeguidoId;
    }
} 