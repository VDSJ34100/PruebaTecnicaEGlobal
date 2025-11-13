package com.valerian.evaluacionplataformas.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String operacion;
    private Double importe;
    private String cliente;
    private String referencia;
    private String estatus;
    private String secreto;
}









