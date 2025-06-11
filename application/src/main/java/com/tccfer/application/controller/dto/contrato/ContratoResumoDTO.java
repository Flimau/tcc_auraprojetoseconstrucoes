// src/main/java/com/tccfer/application/controller/dto/contrato/ContratoResumoDTO.java
package com.tccfer.application.controller.dto.contrato;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO enxuto para listar contratos (ex: na tela “Listar Contratos”).
 * Apresenta apenas os campos básicos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContratoResumoDTO {
    private Long id;           // ID do contrato
    private Long orcamentoId;  // ID do orçamento associado
    private Long clienteId;    // ID do cliente
    private String clienteNome;// Nome do cliente
}
