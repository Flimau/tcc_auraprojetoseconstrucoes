package com.tccfer.application.controller.dto.orcamento;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrcamentoItemDTO {

    private Long id;
    private String descricao;
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal subtotal;
}
