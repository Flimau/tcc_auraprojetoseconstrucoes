package com.tccfer.application.mapper;

import com.tccfer.application.controller.dto.contato.ContatoDTO;
import com.tccfer.application.model.entity.contato.Contato;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContatoMapper {
    Contato toEntity(ContatoDTO contatoDTO);
    ContatoDTO toDTO(Contato contato);
}
