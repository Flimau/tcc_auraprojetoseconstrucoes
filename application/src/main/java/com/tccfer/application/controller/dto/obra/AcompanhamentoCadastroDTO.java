package com.tccfer.application.controller.dto.obra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AcompanhamentoCadastroDTO {
    private Long id;
    private Long obraId;
    private LocalDate dataRegistro;
    private List<String> tarefas;
    private List<String> tarefasConcluidas;
    private String observacoes;
}
