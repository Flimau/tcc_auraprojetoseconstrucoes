package com.tccfer.application.model.entity.localizacao;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estados_id")
    private Estado estado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "municipios_id")
    private Municipio municipio;

    private String logradouro;

    private String numero;

    private String bairro;

    private String cep;

    private String complemento;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

}
