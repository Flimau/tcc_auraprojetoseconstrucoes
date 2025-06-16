package com.tccfer.application.controller.dto.obra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO para um registro de Diário de Obra.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiarioDeObraCadastroDTO {

    private Long id;

    private Long obraId;

    private LocalDate dataRegistro;

    private String observacoes;
}
