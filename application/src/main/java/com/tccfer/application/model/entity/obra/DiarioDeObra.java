package com.tccfer.application.model.entity.obra;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "diario_obra")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DiarioDeObra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JoinColumn(name = "obra_id", nullable = false)
    private long obraId;

    @Column(name = "data_registro", nullable = false)
    private LocalDate dataRegistro;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
