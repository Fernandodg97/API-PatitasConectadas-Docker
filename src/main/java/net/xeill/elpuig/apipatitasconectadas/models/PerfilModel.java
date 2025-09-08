package net.xeill.elpuig.apipatitasconectadas.models;
import java.sql.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "perfil", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"usuario_id"})
})
public class PerfilModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private UserModel usuario;

    @Column
    private String descripcion;

    @Column
    private Date fecha_nacimiento;

    @Column
    private String img;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UserModel usuario) {
        this.usuario = usuario;
    }

    public Long getUsuario_id() {
        return usuario != null ? usuario.getId() : null;
    }

    public void setUsuario_id(Long usuario_id) {
        if (this.usuario == null) {
            this.usuario = new UserModel();
        }
        this.usuario.setId(usuario_id);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
} 