package net.xeill.elpuig.apipatitasconectadas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import net.xeill.elpuig.apipatitasconectadas.models.*;

//Clase que me permite realizar las consultas a la base de datos
@Repository
public interface PerfilRepository extends JpaRepository<PerfilModel, Long> {
    Optional<PerfilModel> findByUsuarioId(Long usuarioId);
} 