package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import net.xeill.elpuig.apipatitasconectadas.models.UsuarioEventoModel;

public class UsuarioEventoModelDtoResponse {

    private Long id;
    private Long eventoId;
    private Long usuarioId;
    private String rol;

    public UsuarioEventoModelDtoResponse(UsuarioEventoModel usuarioEvento) {
        this.id = usuarioEvento.getId();
        this.eventoId = usuarioEvento.getEvento().getId();
        this.usuarioId = usuarioEvento.getUsuario().getId();
        this.rol = usuarioEvento.getRol();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
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