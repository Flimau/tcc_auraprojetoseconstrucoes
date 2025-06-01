package com.tccfer.application.controller.dto.obra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO para um registro de Diário de Obra.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiarioDeObraDTO {
    private Long id;

    /** ID da obra a que este diário pertence */
    private Long obraId;

    /** Data do registro (ex.: "2025-06-16") */
    private LocalDate dataRegistro;

    /** Itens/tarefas do dia (cada string é um item) */
    private List<String> itens;

    /** Observações do dia */
    private String observacoes;
}
