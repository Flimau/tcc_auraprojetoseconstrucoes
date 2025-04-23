package com.tccfer.application.mapper;

import com.tccfer.application.controller.dto.ContatoDTO;
import com.tccfer.application.model.entity.contato.Contato;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-22T02:09:06-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Oracle Corporation)"
)
@Component
public class ContatoMapperImpl implements ContatoMapper {

    @Override
    public Contato toEntity(ContatoDTO contatoDTO) {
        if ( contatoDTO == null ) {
            return null;
        }

        Contato contato = new Contato();

        return contato;
    }

    @Override
    public ContatoDTO toDTO(Contato contato) {
        if ( contato == null ) {
            return null;
        }

        ContatoDTO contatoDTO = new ContatoDTO();

        return contatoDTO;
    }
}
