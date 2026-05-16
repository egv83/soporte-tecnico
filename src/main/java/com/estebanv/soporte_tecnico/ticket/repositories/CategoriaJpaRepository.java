package com.estebanv.soporte_tecnico.ticket.repositories;//package com.estebanv.soporte_tecnico.repositories;

import com.estebanv.soporte_tecnico.ticket.entities.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaJpaRepository extends JpaRepository<CategoriaEntity, Long> {

    Optional<CategoriaEntity> findByNombre(String nombre);
//    Optional<UsuarioEntity> findByUserNameAndPassword(String nombre,String password);

//    Boolean existsByUserName(String userName);

//    @Query("SELECT u FROM UsuarioEntity u WHERE u.rol = :rol")
//    List<CategoriaEntity> findByRol(@Param("rol") String rol);


//    @Query("SELECT COUNT(u) FROM UsuarioEntity u WHERE u.isEnabled = 'ACTIVO'")
//    Long countActiveUsers();

}
