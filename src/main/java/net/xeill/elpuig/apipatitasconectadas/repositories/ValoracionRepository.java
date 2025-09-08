package net.xeill.elpuig.apipatitasconectadas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.xeill.elpuig.apipatitasconectadas.models.*;

//Clase que me permite realizar las consultas a la base de datos

@Repository
public interface ValoracionRepository extends JpaRepository<ValoracionModel, Long> {
    List<ValoracionModel> findByReceptorId(Long receptorId);
    List<ValoracionModel> findByAutorId(Long autorId);
}