package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import java.time.LocalDateTime;
import net.xeill.elpuig.apipatitasconectadas.models.ChatModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;

public class ChatModelDtoRequest {
    
    private Long emisorId;
    
    private Long receptorId;
    
    private boolean visto;
    
    private String contenido;
    
    private LocalDateTime fechaHora;
    
    public ChatModel toDomain(UserModel emisor, UserModel receptor) {
        ChatModel chat = new ChatModel();
        chat.setEmisor(emisor);
        chat.setReceptor(receptor);
        chat.setVisto(this.visto);
        chat.setContenido(this.contenido);
        chat.setFechaHora(this.fechaHora);
        return chat;
    }
    
    public Long getEmisorId() {
        return emisorId;
    }
    
    public void setEmisorId(Long emisorId) {
        this.emisorId = emisorId;
    }
    
    public Long getReceptorId() {
        return receptorId;
    }
    
    public void setReceptorId(Long receptorId) {
        this.receptorId = receptorId;
    }
    
    public boolean isVisto() {
        return visto;
    }
    
    public void setVisto(boolean visto) {
        this.visto = visto;
    }
    
    public String getContenido() {
        return contenido;
    }
    
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
} 