// src/main/java/com/tccfer/application/controller/dto/contrato/ContratoDTO.java
package com.tccfer.application.controller.dto.contrato;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

/**
 * DTO usado para criar ou atualizar um contrato.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContratoDTO {
    private Long id;              // null ao criar; preenchido ao retornar do DB
    private Long orcamentoId;     // ID do orçamento ao qual pertence
    private LocalDate dataInicio; // data de início
    private LocalDate dataFim;    // data de término
    private Double valorTotal;    // valor total do contrato
}

