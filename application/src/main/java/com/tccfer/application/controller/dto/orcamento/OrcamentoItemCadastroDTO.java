package com.tccfer.application.controller.dto.orcamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrcamentoItemCadastroDTO {

    @NotBlank(message = "Descrição do item é obrigatória")
    private String descricao;

    @NotNull(message = "Quantidade do item é obrigatória")
    private Integer quantidade;

    @NotNull(message = "Valor unitário do item é obrigatório")
    private BigDecimal valorUnitario;
}
