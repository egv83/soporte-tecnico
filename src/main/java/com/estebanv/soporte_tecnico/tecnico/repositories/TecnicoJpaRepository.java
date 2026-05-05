package com.estebanv.soporte_tecnico.tecnico.repositories;//package com.estebanv.soporte_tecnico.repositories;

import com.estebanv.soporte_tecnico.tecnico.entities.TecnicoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TecnicoJpaRepository extends JpaRepository<TecnicoEntity, Long> {

    @Query(
            nativeQuery = false,
            value = "SELECT t FROM TecnicoEntity t WHERE t.isActivo = true AND t.id = :id"
    )
    Optional<TecnicoEntity> findByIdActivo(@Param("id") Long id);

    Optional<TecnicoEntity> findById(Long id);

    @Query(
            nativeQuery = false,
            value = "SELECT t FROM TecnicoEntity t WHERE t.isActivo = true"
    )
    Optional<TecnicoEntity> findAllByIsActivo(Long id);


    @Query(
            nativeQuery = false,
            value = "SELECT t FROM TecnicoEntity t WHERE t.identificacion = :identificaion"
    )
    Optional<TecnicoEntity> findByIdentificacion(@Param("identificaion") String identificacion);

}
