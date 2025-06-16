package com.tccfer.application.controller.dto.endereco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDetalhadoDTO {
    private Long id;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cep;
    private String complemento;
    private Double latitude;
    private Double longitude;
    private String cidade;
    private String estado;
    private Long municipioId; //
}
