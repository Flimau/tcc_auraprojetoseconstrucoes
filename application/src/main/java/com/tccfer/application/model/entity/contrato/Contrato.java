package com.tccfer.application.model.entity.contrato;
// src/main/java/com/tccfer/application/model/entity/ContratoEntity.java

import com.tccfer.application.model.entity.orcamento.Orcamento;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "contrato")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Relacionamento 1:1 com Orcamento.
     * Cada contrato está associado exatamente a um orçamento.
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orcamento_id", unique = true, nullable = false)
    private Orcamento orcamento;

    /** Data de início do contrato */
    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    /** Data de término do contrato */
    @Column(name = "data_fim")
    private LocalDate dataFim;

    /** Status do contrato (pode ser um enum ou String) */
    @Column(name = "status", length = 50)
    private String status;

    /** Valor total do contrato (se for diferente do orçamento) */
    @Column(name = "valor_total")
    private Double valorTotal;

    // Caso queira campos adicionais (por ex. observações), adicione aqui.
}
