package com.tccfer.application.controller.dto.detailed;

import com.tccfer.application.controller.dto.MunicipioDTO;
import com.tccfer.application.controller.dto.PaisDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class EstadoDetailedDTO {
    private Long id;
    private String nome;
    private String sigla;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private PaisDTO pais;
    private List<MunicipioDTO> municipios;
}
