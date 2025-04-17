package com.tccfer.application.controller.dto.detailed;

import com.tccfer.application.controller.dto.EstadoDTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MunicipioDetailedDTO {
    private Long id;
    private String nome;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private EstadoDTO estado;
}
