package com.tccfer.application.controller.dto.obra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiarioDeObraDetalhadoDTO {
    private Long id;
    private LocalDate dataRegistro;
    private String observacoes;
}
