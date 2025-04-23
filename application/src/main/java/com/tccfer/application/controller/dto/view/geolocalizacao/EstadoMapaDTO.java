package com.tccfer.application.controller.dto.view.geolocalizacao;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EstadoMapaDTO {
    private String nome;
    private String sigla;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
