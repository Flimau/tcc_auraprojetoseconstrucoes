package com.tccfer.application.controller.dto.endereco;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EnderecoDTO {//saída de informações

    private Long id;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cep;
    private String complemento;

    private String cidade;
    private String siglaEstado;
    private String nomeEstado;
    private Double latitude;
    private Double longitude;

}
