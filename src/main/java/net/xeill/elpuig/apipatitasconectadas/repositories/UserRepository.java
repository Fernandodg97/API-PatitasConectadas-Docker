package net.xeill.elpuig.apipatitasconectadas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.xeill.elpuig.apipatitasconectadas.models.*;
import java.util.List;
import java.util.Optional;


//Clase que me permite realizar las consultas a la base de datos

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    
    Optional<UserModel> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    List<UserModel> findByApellidoStartsWith(String apellido);

    List<UserModel> findByNombreContainingIgnoreCase(String nombre);
    
    List<UserModel> findByApellidoContainingIgnoreCase(String apellido);
    
    List<UserModel> findByNombreContainingIgnoreCaseAndApellidoContainingIgnoreCase(String nombre, String apellido);
}