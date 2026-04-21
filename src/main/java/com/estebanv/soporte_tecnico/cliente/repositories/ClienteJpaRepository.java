package com.estebanv.soporte_tecnico.cliente.repositories;//package com.estebanv.soporte_tecnico.repositories;

import com.estebanv.soporte_tecnico.cliente.entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, Long> {

    Optional<ClienteEntity> findById(String nombre);

    @Query(
            nativeQuery = false,
            value = "SELECT c FROM ClienteEntity c WHERE c.identificacion = :identificaion"
    )
    Optional<ClienteEntity> findByIdentificacion(@Param("identificaion") String identificacion);

}
