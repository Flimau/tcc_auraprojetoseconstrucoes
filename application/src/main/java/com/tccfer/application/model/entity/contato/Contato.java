package com.tccfer.application.model.entity.contato;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Contato {

    private String email;

    private String celular;

    private String telefone;

}
