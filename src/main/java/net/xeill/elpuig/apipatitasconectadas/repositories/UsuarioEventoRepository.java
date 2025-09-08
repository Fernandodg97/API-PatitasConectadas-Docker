package net.xeill.elpuig.apipatitasconectadas.repositories;

import net.xeill.elpuig.apipatitasconectadas.models.UsuarioEventoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioEventoRepository extends JpaRepository<UsuarioEventoModel, Long> {
    
    // Encontrar todas las relaciones de un usuario específico
    List<UsuarioEventoModel> findByUsuarioId(Long usuarioId);
    
    // Encontrar todas las relaciones de un evento específico
    List<UsuarioEventoModel> findByEventoId(Long eventoId);
    
    // Encontrar una relación específica entre usuario y evento
    Optional<UsuarioEventoModel> findByUsuarioIdAndEventoId(Long usuarioId, Long eventoId);
    
    // Verificar si existe una relación entre usuario y evento
    boolean existsByUsuarioIdAndEventoId(Long usuarioId, Long eventoId);
    
    // Eliminar todas las relaciones de un usuario específico
    void deleteByUsuarioId(Long usuarioId);
    
    // Eliminar todas las relaciones de un evento específico
    void deleteByEventoId(Long eventoId);
    
    // Eliminar una relación específica entre usuario y evento
    void deleteByUsuarioIdAndEventoId(Long usuarioId, Long eventoId);
} 