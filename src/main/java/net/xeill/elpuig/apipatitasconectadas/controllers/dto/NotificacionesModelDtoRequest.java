package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import java.util.Date;
import net.xeill.elpuig.apipatitasconectadas.models.NotificacionesModel;

public class NotificacionesModelDtoRequest {
    
    private Date fecha;
    
    // Constructor vacío necesario para deserialización JSON
    public NotificacionesModelDtoRequest() {
    }

    // Constructor con todos los campos
    public NotificacionesModelDtoRequest(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Convierte el DTO a un objeto de dominio NotificacionesModel
     * @return Objeto NotificacionesModel con los datos del DTO
     */
    public NotificacionesModel toDomain() {
        NotificacionesModel notificacion = new NotificacionesModel();
        notificacion.setFecha(this.fecha);
        return notificacion;
    }
    
    public Date getFecha() {
        return fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
} 