package com.tccfer.application.model.entity.localizacao;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@DynamicUpdate
@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Pais.class)
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String sigla;

    @OneToMany(mappedBy = "pais", targetEntity = Estado.class,
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Estado> estado;
}
