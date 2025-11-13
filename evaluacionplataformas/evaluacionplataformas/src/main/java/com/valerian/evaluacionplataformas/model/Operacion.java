package com.valerian.evaluacionplataformas.model;

import jakarta.persistence.*;

@Entity
public class Operacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String operacion;
    private Double importe;
    private String cliente;
    private String referencia;
    private String estatus;
    private String secreto;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOperacion() { return operacion; }
    public void setOperacion(String operacion) { this.operacion = operacion; }

    public Double getImporte() { return importe; }
    public void setImporte(Double importe) { this.importe = importe; }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }

    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }

    public String getSecreto() { return secreto; }
    public void setSecreto(String secreto) { this.secreto = secreto; }
}

