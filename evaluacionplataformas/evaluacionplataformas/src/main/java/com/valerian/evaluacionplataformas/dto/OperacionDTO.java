package com.valerian.evaluacionplataformas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperacionDTO {

    @NotBlank
    @Pattern(regexp = "[a-zA-Z]+", message = "Operación inválida")
    private String operacion;

    @NotBlank
    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "Importe inválido")
    private String importe;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z]+", message = "Cliente inválido")
    private String cliente;

    @NotBlank
    private String firma;
}

