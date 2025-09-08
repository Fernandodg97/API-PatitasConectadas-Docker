package net.xeill.elpuig.apipatitasconectadas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import net.xeill.elpuig.apipatitasconectadas.models.*;

import java.util.List;
import java.util.Optional;

public interface SeguidoRepository extends JpaRepository<SeguidoModel, Long> {

    List<SeguidoModel> findByUsuarioQueSigueId(Long usuarioQueSigueId);

    Optional<SeguidoModel> findByUsuarioQueSigueIdAndUsuarioQueEsSeguidoId(Long quienSigueId, Long quienEsSeguidoId);
    
    boolean existsByUsuarioQueSigueIdAndUsuarioQueEsSeguidoId(Long usuarioQueSigueId, Long usuarioQueEsSeguidoId);

}