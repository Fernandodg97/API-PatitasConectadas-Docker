package net.xeill.elpuig.apipatitasconectadas.models;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
//@JsonManagedReference
//@JsonBackReference
@Entity
@Table(name = "grupo")
public class GrupoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsuarioGrupoModel> usuarios = new ArrayList<>();

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

    public List<UsuarioGrupoModel> getUsuarios() {
        return usuarios;
    }
    
    public void setUsuarios(List<UsuarioGrupoModel> usuarios) {
        this.usuarios = usuarios;
    }
} 