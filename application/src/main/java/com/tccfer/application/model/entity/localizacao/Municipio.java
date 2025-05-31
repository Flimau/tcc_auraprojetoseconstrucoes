package com.tccfer.application.model.entity.localizacao;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@DynamicUpdate
@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Municipio.class)
public class Municipio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(precision = 19, scale = 10)
    private BigDecimal latitude;

    @Column(precision = 19, scale = 10)
    private BigDecimal longitude;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="estado_id")
    private Estado estado;
}
