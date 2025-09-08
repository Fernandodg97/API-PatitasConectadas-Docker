package net.xeill.elpuig.apipatitasconectadas.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mascota")
public class MascotaModel {

    // ID autogenerado
    @Id
    // La base de datos genera automáticamente el ID (auto-incremental)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID del usuario al que pertenece la mascota (clave foránea)
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    // Nombre de la mascota
    @Column(length = 50, nullable = false)
    private String nombre;

    // Género de la mascota (por ejemplo: "Macho" o "Hembra")
    @Column(length = 10, nullable = false)
    private String genero;

    // Especie de la mascota (cambiado de Raza)
    @Column(length = 50, nullable = false)
    private String especie;

    // Ruta de la foto de la mascota
    @Column(name = "foto")
    private String foto;

    // Fecha de nacimiento de la mascota
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
