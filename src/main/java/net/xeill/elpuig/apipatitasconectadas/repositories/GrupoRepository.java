package net.xeill.elpuig.apipatitasconectadas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.xeill.elpuig.apipatitasconectadas.models.*;

//Clase que me permite realizar las consultas a la base de datos
@Repository
public interface GrupoRepository extends JpaRepository<GrupoModel, Long> {

} 