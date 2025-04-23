package com.tccfer.application.model.entity.orcamento;

import jakarta.persistence.*;
import lombok.Data;


import java.util.List;

@Data
@Entity
@Table(name = "tipo_orcamento")
public class TipoOrcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "tipoPai", cascade = CascadeType.ALL)
    private List<SubtipoOrcamento> subtipos;
}
