package com.tccfer.application.model.entity.pessoa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tccfer.application.model.entity.contato.Contato;
import com.tccfer.application.model.entity.enuns.TipoPessoa;
import com.tccfer.application.model.entity.localizacao.Endereco;
import com.tccfer.application.model.entity.enuns.Sexo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cpf;

    private String cnpj;

    private Date dataNascimento;

    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @Embedded
    private Contato contato;

    private LocalDateTime dataRegistro;
}
