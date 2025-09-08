package net.xeill.elpuig.apipatitasconectadas.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "evento")
public class EventoModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 50, nullable = false)
    private String nombre;

    @Column(length = 250, nullable = false)
    private String descripcion;

    @Column(length = 250, nullable = false)
    private String ubicacion;

    @Column(nullable = false)
    private Date fecha;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsuarioEventoModel> usuarios = new ArrayList<>();

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<UsuarioEventoModel> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioEventoModel> usuarios) {
        this.usuarios = usuarios;
    }
}
