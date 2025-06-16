package com.tccfer.application.model.entity.obra;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tccfer.application.model.entity.contrato.Contrato;
import com.tccfer.application.model.entity.enuns.ObraStatus;
import com.tccfer.application.model.entity.localizacao.Endereco;
import com.tccfer.application.model.entity.orcamento.Orcamento;
import com.tccfer.application.model.entity.pessoa.Pessoa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "obra")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Obra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Pessoa cliente;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orcamento_id", nullable = false)
    private Orcamento orcamento;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id", nullable = false)
    private Endereco endereco;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @OneToMany(
            mappedBy = "obraId",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<DiarioDeObra> diariosObra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id")
    private Pessoa executor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private ObraStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contrato_id")
    private Contrato contrato;

}
