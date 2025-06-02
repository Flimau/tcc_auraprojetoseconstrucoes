package com.tccfer.application.controller.dto.obra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO mínimo para respostas de Obra, só com IDs de FK em vez de
 * expor a entidade completa e sofrer com LazyInitializationException.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObraDTO {
    private Long id;

    /** ID e nome do cliente (Pessoa) associado à obra. */
    private Long clienteId;
    private String clienteNome;

    /** ID e nome do executor responsável pela obra */
    private Long executorId;
    private String executorNome;

    /** ID do orçamento (Orcamento) associado à obra. */
    private Long orcamentoId;

    /** Status da obra (PLANEJADA, EM_ANDAMENTO, etc.) */
    private String status;

    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String contratoUrl;
}
