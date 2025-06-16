package com.tccfer.application.controller.dto.obra;

import com.tccfer.application.model.entity.enuns.ObraStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObraListagemDTO {
    private Long id;
    private String nomeCliente;
    private String nomeExecutor;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private ObraStatus status;
}
