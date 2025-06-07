package com.tccfer.application.controller.dto.visita;


import com.tccfer.application.controller.dto.endereco.EnderecoDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class VisitaTecnicaCadastroDTO {

    @NotNull(message = "ID do cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "Endereço é obrigatório")
    private EnderecoDTO endereco;

    private String descricao;

    @NotBlank(message = "Data da visita é obrigatória")
    private String dataVisita;    // formato ISO yyyy-MM-dd


    private List<String> fotos;
    private List<String> videos;
}
