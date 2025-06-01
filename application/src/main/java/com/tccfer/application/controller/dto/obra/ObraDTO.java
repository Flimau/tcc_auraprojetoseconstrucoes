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

    /**
     * ID e nome do cliente (Pessoa) associado à obra.
     * Se a UI precisar do nome do cliente, faz GET /api/pessoas/{clienteId} separadamente.
     */
    private Long clienteId;
    private String clienteNome;

    /**
     * ID do orçamento (Orcamento) associado à obra.
     * Se a UI precisar da descrição, faz GET /api/orcamentos/{orcamentoId} separadamente.
     */
    private Long orcamentoId;

    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String contratoUrl;
}
