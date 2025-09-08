package net.xeill.elpuig.apipatitasconectadas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.xeill.elpuig.apipatitasconectadas.models.*;
import java.util.List;

@Repository
public interface UsuarioGrupoRepository extends JpaRepository<UsuarioGrupoModel, Long> {

    // Encuentra todos los grupos a los que pertenece un usuario
    List<UsuarioGrupoModel> findByUsuario(UserModel usuario);

    // Encuentra todos los usuarios que pertenecen a un grupo
    List<UsuarioGrupoModel> findByGrupo(GrupoModel grupo);

    // Encuentra una relación específica usuario-grupo
    UsuarioGrupoModel findByUsuarioAndGrupo(UserModel usuario, GrupoModel grupo);
}
