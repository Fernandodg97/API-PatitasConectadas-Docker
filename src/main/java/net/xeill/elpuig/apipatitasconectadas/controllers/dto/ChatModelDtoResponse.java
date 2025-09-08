package net.xeill.elpuig.apipatitasconectadas.controllers.dto;

import java.time.LocalDateTime;
import net.xeill.elpuig.apipatitasconectadas.models.ChatModel;

public class ChatModelDtoResponse {

    private Long id;
    private Long emisorId;
    private Long receptorId;
    private boolean visto;
    private String contenido;
    private LocalDateTime fechaHora;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ChatModelDtoResponse(ChatModel chat) {
        this.id = chat.getId();
        this.emisorId = chat.getEmisor().getId();
        this.receptorId = chat.getReceptor().getId();
        this.visto = chat.isVisto();
        this.contenido = chat.getContenido();
        this.fechaHora = chat.getFechaHora();
        this.createdAt = chat.getCreatedAt();
        this.updatedAt = chat.getUpdatedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
} 