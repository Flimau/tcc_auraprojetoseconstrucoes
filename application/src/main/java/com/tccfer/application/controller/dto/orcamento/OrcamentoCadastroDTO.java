package com.tccfer.application.controller.dto.orcamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrcamentoCadastroDTO {

    @NotNull(message = "ID do cliente é obrigatório")
    private Long clienteId;

    /**
     * Opcional: se for null, significa que não houve visita técnica
     */
    private Long visitaId;

    @NotBlank(message = "Descrição do orçamento é obrigatória")
    private String descricao;

    @NotBlank(message = "Tipo de orçamento é obrigatório")
    private String tipo;    // deve receber um valor igual ao nome em TipoOrcamento

    @NotBlank(message = "Subtipo de orçamento é obrigatório")
    private String subtipo; // deve receber um valor igual ao nome em SubtipoOrcamento

    /**
     * Se true, itens deverá ser preenchido.
     * Se false, pode vir null ou lista vazia.
     */
    private boolean comMaterial;

    private List<OrcamentoItemCadastroDTO> itens;
}
