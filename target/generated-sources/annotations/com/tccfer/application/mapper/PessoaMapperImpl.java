package com.tccfer.application.mapper;

import com.tccfer.application.controller.dto.PessoaCadastroDTO;
import com.tccfer.application.model.entity.pessoa.Pessoa;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-23T00:11:33-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Oracle Corporation)"
)
@Component
public class PessoaMapperImpl implements PessoaMapper {

    @Override
    public Pessoa toEntity(PessoaCadastroDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Pessoa pessoa = new Pessoa();

        return pessoa;
    }

    @Override
    public PessoaCadastroDTO toDTO(Pessoa pessoa) {
        if ( pessoa == null ) {
            return null;
        }

        PessoaCadastroDTO pessoaCadastroDTO = new PessoaCadastroDTO();

        return pessoaCadastroDTO;
    }
}
