package com.tccfer.application.controller.dto.obra;

import com.tccfer.application.controller.dto.endereco.EnderecoDTO;
import com.tccfer.application.model.entity.enuns.ObraStatus;
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
public class ObraCadastroDTO {
    private Long clienteId;      // Primeiro é selecionado
    private Long orcamentoId;    // Só depois do cliente é liberado
    private EnderecoDTO endereco;
    private Long contratoId;
    private Long executorId;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private ObraStatus status;
}
