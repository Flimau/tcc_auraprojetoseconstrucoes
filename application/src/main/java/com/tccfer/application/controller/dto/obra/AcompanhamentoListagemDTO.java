package com.tccfer.application.controller.dto.obra;

import com.tccfer.application.model.entity.enuns.StatusAcompanhamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AcompanhamentoListagemDTO {

    private Long id;
    private LocalDate dataRegistro;
    private StatusAcompanhamento status;
}
