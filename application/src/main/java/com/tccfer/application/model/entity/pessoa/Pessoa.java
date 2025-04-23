package com.tccfer.application.model.entity.pessoa;

import com.tccfer.application.model.entity.contato.Contato;
import com.tccfer.application.model.entity.enuns.TipoPessoa;
import com.tccfer.application.model.entity.enuns.TipoUsuario;
import com.tccfer.application.model.entity.localizacao.Endereco;
import com.tccfer.application.model.entity.enuns.Sexo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cpf;

    private String cnpj;

    private Date dataNascimento;

    private LocalDateTime dataRegistro;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @Embedded
    private Contato contato;
}
