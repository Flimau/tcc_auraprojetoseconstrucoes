package com.tccfer.application.model.entity;
//TODO: Verificar a questão da documentação com swagger

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contato {

    //@ApiModelProperty(value = "E-mail válido para contato.")
    private String email;

    //@ApiModelProperty(value = "Número de celular.")
    private String celular;

    //@ApiModelProperty(value = "Número de telefone fixo.")
    private String telefone;

}
