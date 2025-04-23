package com.tccfer.application.model.entity.orcamento;

import com.tccfer.application.model.entity.pessoa.Pessoa;
import com.tccfer.application.model.entity.visitas.VisitaTecnica;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String descricao;
    private boolean comMaterial;

    @OneToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa cliente;

    @ManyToOne(optional = true)
    @JoinColumn(name = "visita_id")
    private VisitaTecnica visita;

    @ManyToOne
    @JoinColumn(name = "subtipo_id")
    private SubtipoOrcamento subtipo;

    @ManyToOne
    @JoinColumn(name = "tipo_id")
    private TipoOrcamento tipoOrcamento;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "orcamento_id")
    private List<ItemOrcamento> itens;
}
