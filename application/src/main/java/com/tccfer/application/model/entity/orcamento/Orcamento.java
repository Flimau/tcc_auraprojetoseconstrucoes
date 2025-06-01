package com.tccfer.application.model.entity.orcamento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tccfer.application.model.entity.enuns.SubtipoOrcamento;
import com.tccfer.application.model.entity.enuns.TipoOrcamento;
import com.tccfer.application.model.entity.pessoa.Pessoa;
import com.tccfer.application.model.entity.visitas.VisitaTecnica;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orcamento")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Referência ao cliente
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id")
    private Pessoa cliente;

    // Pode ou não ter visita técnica associada
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visita_id")
    private VisitaTecnica visita;

    @Column(length = 2000)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoOrcamento tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubtipoOrcamento subtipo;

    @Column(name = "com_material", nullable = false)
    private boolean comMaterial;

    @OneToMany(mappedBy = "orcamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrcamentoItem> itens;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();
}
