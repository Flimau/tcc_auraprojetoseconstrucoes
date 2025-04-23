package com.tccfer.application.controller.dto.view.geolocalizacao;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MunicipioMapaDTO {
    private String nome;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
