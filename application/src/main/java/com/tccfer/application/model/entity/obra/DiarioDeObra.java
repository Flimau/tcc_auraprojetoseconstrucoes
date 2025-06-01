package com.tccfer.application.model.entity.obra;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Entidade que representa o registro diário (diário de bordo) de uma Obra.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "diario_obra")
public class DiarioDeObra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Relacionamento ManyToOne com Obra.
     * Cada registro de diário está associado a exatamente uma Obra.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "obra_id", nullable = false)
    private Obra obra;

    /**
     * Data em que este registro foi feito.
     */
    @Column(name = "data_registro", nullable = false)
    private LocalDate dataRegistro;

    /**
     * Lista de itens/tarefas do dia, que deverão ser cumpridas conforme o cronograma/orçamento.
     * Usamos @ElementCollection para armazenar uma lista simples de strings em tabela auxiliar.
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "diario_obra_itens",
            joinColumns = @JoinColumn(name = "diario_id")
    )
    @Column(name = "item")
    private List<String> itens;

    /**
     * Campo de observações livre para anotações do dia (por exemplo, notas de campo, problemas encontrados).
     */
    @Column(name = "observacoes", length = 2000)
    private String observacoes;
}
