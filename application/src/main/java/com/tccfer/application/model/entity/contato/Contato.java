package com.tccfer.application.model.entity.contato;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class Contato {

    private String email;

    private String celular;

    private String telefone;

}
