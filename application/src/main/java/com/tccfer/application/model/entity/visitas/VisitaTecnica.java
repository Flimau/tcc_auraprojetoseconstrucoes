package com.tccfer.application.model.entity.visitas;

import com.tccfer.application.model.entity.localizacao.Endereco;
import com.tccfer.application.model.entity.pessoa.Pessoa;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> fotos;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> videos;

    private boolean usadaEmOrcamento = false;
}
