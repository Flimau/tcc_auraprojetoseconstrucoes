package com.tccfer.application.controller.dto.orcamento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrcamentoDTO {

    private Long id;

    private Long clienteId;
    private String clienteNome;

    /**
     * Pode ser null se não houver visita associada
     */
    private Long visitaId;

    private String descricao;
    private String tipo;
    private String subtipo;

    private boolean comMaterial;

    private List<OrcamentoItemDTO> itens;

    /**
     * ISO 8601: yyyy-MM-dd'T'HH:mm:ss
     */
    private String dataCriacao;
}
