package com.valerian.evaluacionplataformas.repository;

import com.valerian.evaluacionplataformas.model.Operacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OperacionRepository extends JpaRepository<Operacion, Long> {

    @Query("SELECT o FROM Operacion o WHERE o.referencia = :referencia")
    Operacion findByReferencia(String referencia);
}
