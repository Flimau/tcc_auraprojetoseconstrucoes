package com.tccfer.application.mapper;

import com.tccfer.application.controller.dto.usuario.PessoaCadastroDTO;
import com.tccfer.application.model.entity.enuns.TipoPessoa;
import com.tccfer.application.model.entity.pessoa.Pessoa;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {EnderecoMapper.class, ContatoMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PessoaMapper {

    @Mappings({
            @Mapping(source = "nome", target = "nome"),
            @Mapping(source = "sexo", target = "sexo"),
            @Mapping(source = "tipoPessoa", target = "tipoPessoa"),
            @Mapping(source = "dataNascimento", target = "dataNascimento"),
            @Mapping(source = "endereco", target = "endereco"),
            @Mapping(source = "contato", target = "contato")
    })

    Pessoa toEntity(PessoaCadastroDTO dto);

    PessoaCadastroDTO toDTO(Pessoa pessoa);

    @AfterMapping
    default void preencherCamposCondicionais(PessoaCadastroDTO dto,
                                             @MappingTarget Pessoa pessoa){
        if(dto.getTipoPessoa() == TipoPessoa.FISICA){
            pessoa.setCpf(dto.getCpf());
        }else{
            pessoa.setCnpj(dto.getCnpj());
        }

        pessoa.setDataRegistro(java.time.LocalDateTime.now());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void atualizarEntidade(PessoaCadastroDTO dto, @MappingTarget Pessoa entidade);
}
