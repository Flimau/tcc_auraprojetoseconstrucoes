package com.tccfer.application.model.entity.localizacao;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Estado.class)
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String sigla;

    @Column(precision = 19, scale = 10)
    private BigDecimal latitude;

    @Column(precision = 19, scale = 10)
    private BigDecimal longitude;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pais_id")
    private Pais pais;

    @OneToMany(mappedBy = "estado", targetEntity = Municipio.class,
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Municipio> municipio;
}
