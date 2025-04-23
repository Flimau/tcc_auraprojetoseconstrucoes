package com.tccfer.application.mapper;

import com.tccfer.application.controller.dto.ContatoDTO;
import com.tccfer.application.model.entity.contato.Contato;

public class ContatoMapperOld {

    public Contato toEntity(ContatoDTO dto){

        Contato contato = new Contato();
        contato.setCelular(dto.getCelular());
        contato.setTelefone(dto.getTelefone());
        contato.setEmail(dto.getEmail());

        return contato;
    }
}
