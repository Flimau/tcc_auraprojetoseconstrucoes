package com.tccfer.application.mapper;

import com.tccfer.application.controller.dto.PessoaCadastroDTO;
import com.tccfer.application.model.entity.enuns.TipoPessoa;
import com.tccfer.application.model.entity.pessoa.Pessoa;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {EnderecoMapper.class, ContatoMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PessoaMapper {

    Pessoa toEntity(PessoaCadastroDTO dto);
    PessoaCadastroDTO toDTO(Pessoa pessoa);

    default void preencherCamposCondicionais(PessoaCadastroDTO dto,
                                             @MappingTarget Pessoa pessoa){
        if(dto.getTipoPessoa() == TipoPessoa.FISICA){
            pessoa.setCpf(dto.getCpf());
        }else{
            pessoa.setCnpj(dto.getCnpj());
        }

        pessoa.setDataRegistro(java.time.LocalDateTime.now());
    }
}
