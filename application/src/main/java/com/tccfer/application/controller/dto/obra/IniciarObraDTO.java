package com.tccfer.application.controller.dto.obra;

import lombok.Data;

import java.time.LocalDate;

@Data
public class IniciarObraDTO {

    private LocalDate dataInicio;
    private LocalDate dataFim;

}
