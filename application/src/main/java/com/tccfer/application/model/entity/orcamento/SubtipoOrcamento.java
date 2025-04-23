package com.tccfer.application.model.entity.orcamento;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "subtipo_orcamento")
@Data
public class SubtipoOrcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "tipo_orcamento_id")
    private TipoOrcamento tipoPai;
}
