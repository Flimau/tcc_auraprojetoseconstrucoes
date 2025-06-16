package com.tccfer.application.controller.dto.obra;

import com.tccfer.application.controller.dto.endereco.EnderecoDTO;
import com.tccfer.application.model.entity.enuns.ObraStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObraDetalhadaDTO {

    private Long id;
    private String nomeCliente;
    private Long orcamentoId;
    private EnderecoDTO endereco;
    private String nomeExecutor;
    private Long contratoId;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private ObraStatus status;
    private List<DiarioDeObraListagemDTO> diarios;
}
