package com.tccfer.application.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@Data
@Entity
@ToString
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Municipios.class)
public class Municipios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(precision = 19, scale = 10)
    private BigDecimal latitude;

    @Column(precision = 19, scale = 10)
    private BigDecimal longitude;

    @ManyToOne
    @JoinColumn(name="estado_id")
    private Estado estado;
}
