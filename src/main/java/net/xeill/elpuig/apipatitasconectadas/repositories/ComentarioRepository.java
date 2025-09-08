package net.xeill.elpuig.apipatitasconectadas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.xeill.elpuig.apipatitasconectadas.models.*;

import java.util.List;

//Clase que me permite realizar las consultas a la base de datos

@Repository
public interface ComentarioRepository extends JpaRepository<ComentarioModel, Long> {

    List<ComentarioModel> findByPostId(Long postId);

    List<ComentarioModel> findByCreador(UserModel creador);

    @Query("SELECT c FROM ComentarioModel c WHERE c.contenido LIKE %:searchTerm%")
    List<ComentarioModel> searchByContent(@Param("searchTerm") String searchTerm);

    @Query("SELECT c FROM ComentarioModel c WHERE c.fecha >= :startDate AND c.fecha <= :endDate")
    List<ComentarioModel> findByDateRange(@Param("startDate") java.time.LocalDateTime startDate,
            @Param("endDate") java.time.LocalDateTime endDate);

}