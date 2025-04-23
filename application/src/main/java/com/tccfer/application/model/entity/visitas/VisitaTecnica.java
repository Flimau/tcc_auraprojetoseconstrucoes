package com.tccfer.application.model.entity.visitas;

import com.tccfer.application.model.entity.localizacao.Endereco;
import com.tccfer.application.model.entity.pessoa.Pessoa;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

@Data
@Entity
public class VisitaTecnica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @OneToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa cliente;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    private String descricao;

    private LocalDate dataVisita;

    private LocalTime horarioVisita;

    @ElementCollection
    private List<String> fotos;

    @ElementCollection
    private List<String> videos;

    private boolean usadaEmOrcamento = false;
}
