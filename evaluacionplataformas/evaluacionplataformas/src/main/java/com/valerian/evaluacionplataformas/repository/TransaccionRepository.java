package com.valerian.evaluacionplataformas.repository;

import com.valerian.evaluacionplataformas.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

    Optional<Transaccion> findByReferencia(String referencia);

    @Query("SELECT t FROM Transaccion t WHERE t.referencia = :referencia")
    Optional<Transaccion> buscarPorReferencia(@Param("referencia") String referencia);

    @Modifying
    @Transactional
    @Query("UPDATE Transaccion t SET t.estatus = :estatus WHERE t.id = :id AND t.referencia = :referencia")
    int actualizarEstatus(@Param("id") Long id, @Param("referencia") String referencia, @Param("estatus") String estatus);

    @Query("SELECT t FROM Transaccion t")
    List<Transaccion> obtenerTodas();
}






