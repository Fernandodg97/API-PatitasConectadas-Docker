package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import java.util.Date;

import net.xeill.elpuig.apipatitasconectadas.models.NotificacionesModel;

public class NotificacionesModelDtoResponse {
    
    private Long id;
    private Date fecha;

    // Constructor vacío necesario para serialización
    public NotificacionesModelDtoResponse() {
    }

    // Constructor con todos los campos
    public NotificacionesModelDtoResponse(Long id, Date fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    // Constructor a partir del modelo
    public NotificacionesModelDtoResponse(NotificacionesModel notificacion) {
        this.id = notificacion.getId();
        this.fecha = notificacion.getFecha();
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
} 