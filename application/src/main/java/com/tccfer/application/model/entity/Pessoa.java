package com.tccfer.application.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.tccfer.application.model.entity.enuns.Sexo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_pessoa", discriminatorType = DiscriminatorType.STRING)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Pessoa.class)
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cpf;

    private Date dataNascimento;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Embedded
    private Endereco endereco;

    @Embedded
    private Contato contato;

    private LocalDateTime dataRegistro = LocalDateTime.now();

    private boolean ativo = true;

}
