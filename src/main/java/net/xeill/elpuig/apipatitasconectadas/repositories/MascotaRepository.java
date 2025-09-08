package net.xeill.elpuig.apipatitasconectadas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.xeill.elpuig.apipatitasconectadas.models.MascotaModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface MascotaRepository extends JpaRepository<MascotaModel, Long> {

    // Obtener todas las mascotas de un usuario
    List<MascotaModel> findByUsuarioId(Long usuarioId);

    // Buscar una mascota específica de un usuario
    Optional<MascotaModel> findByIdAndUsuarioId(Long id, Long usuarioId);
}
