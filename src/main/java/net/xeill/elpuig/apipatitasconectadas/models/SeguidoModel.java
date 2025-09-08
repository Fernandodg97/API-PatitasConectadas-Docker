package net.xeill.elpuig.apipatitasconectadas.models;

import jakarta.persistence.*;

@Entity
@Table(name = "seguido")
public class SeguidoModel {

    // ID autogenerado
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID del usuario que sigue (clave foránea)
    @Column(name = "usuario_que_sigue_id", nullable = false)
    private Long usuarioQueSigueId;

    // ID del usuario que es seguido (clave foránea)
    @Column(name = "usuario_que_es_seguido_id", nullable = false)
    private Long usuarioQueEsSeguidoId;

    // Getters y Setters

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
