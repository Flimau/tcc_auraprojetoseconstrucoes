package com.tccfer.application.controller.dto.visita;

import com.tccfer.application.controller.dto.endereco.EnderecoDTO;
import lombok.Data;

import java.util.List;

@Data
public class VisitaTecnicaDTO {

    private Long id;

    private Long clienteId;
    private String clienteNome;

    private EnderecoDTO endereco;

    private String descricao;
    private String dataVisita;    // ISO yyyy-MM-dd


    private List<String> fotos;
    private List<String> videos;

    private boolean usadaEmOrcamento;
}
