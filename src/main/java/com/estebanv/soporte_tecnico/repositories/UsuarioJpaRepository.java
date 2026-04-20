//package com.estebanv.soporte_tecnico.repositories;
//
//import com.estebanv.soporte_tecnico.entities.UsuarioEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {
//
//    Optional<UsuarioEntity> findByUserName(String nombre);
////    Optional<UsuarioEntity> findByUserNameAndPassword(String nombre,String password);
//
//    Boolean existsByUserName(String userName);
//
//    @Query("SELECT u FROM UsuarioEntity u WHERE u.rol = :rol")
//    List<UsuarioEntity> findByRol(@Param("rol") String rol);
//
//
////    @Query("SELECT COUNT(u) FROM UsuarioEntity u WHERE u.isEnabled = 'ACTIVO'")
////    Long countActiveUsers();
//
//}
