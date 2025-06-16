package com.tccfer.application.model.entity.obra;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "acompanhamento_diario_obra")
public class AcompanhamentoDiarioDeObra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "obra_id", nullable = false)
    private Obra obra;

    @Column(name = "data_registro", nullable = false)
    private LocalDate dataRegistro;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "acompanhamento_tarefas", joinColumns = @JoinColumn(name = "acompanhamento_id"))
    @Column(name = "tarefa")
    private List<String> tarefas;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "acompanhamento_concluidas", joinColumns = @JoinColumn(name = "acompanhamento_id"))
    @Column(name = "tarefa_concluida")
    private List<String> tarefasConcluidas;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
