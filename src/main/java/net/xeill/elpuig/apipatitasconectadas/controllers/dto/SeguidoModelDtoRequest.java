package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import net.xeill.elpuig.apipatitasconectadas.models.SeguidoModel;

public class SeguidoModelDtoRequest {
    
    private Long usuarioQueSigueId;
    
    private Long usuarioQueEsSeguidoId;
    
    public SeguidoModel toDomain() {
        SeguidoModel seguido = new SeguidoModel();
        seguido.setUsuarioQueSigueId(this.usuarioQueSigueId);
        seguido.setUsuarioQueEsSeguidoId(this.usuarioQueEsSeguidoId);
        return seguido;
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