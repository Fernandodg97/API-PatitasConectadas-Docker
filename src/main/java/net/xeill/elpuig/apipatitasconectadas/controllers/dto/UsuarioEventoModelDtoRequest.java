package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import jakarta.validation.constraints.NotNull;
import net.xeill.elpuig.apipatitasconectadas.models.EventoModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import net.xeill.elpuig.apipatitasconectadas.models.UsuarioEventoModel;

public class UsuarioEventoModelDtoRequest {

    @NotNull(message = "El ID del evento es obligatorio")
    private Long eventoId;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El rol es obligatorio")
    private String rol;

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

    public UsuarioEventoModel toDomain(EventoModel evento, UserModel usuario) {
        UsuarioEventoModel usuarioEvento = new UsuarioEventoModel();
        usuarioEvento.setEvento(evento);
        usuarioEvento.setUsuario(usuario);
        usuarioEvento.setRol(this.rol);
        return usuarioEvento;
    }
} 