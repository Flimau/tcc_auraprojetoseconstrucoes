package com.tccfer.application.mapper;

import com.tccfer.application.controller.dto.PessoaCadastroDTO;
import com.tccfer.application.model.entity.enuns.TipoPessoa;
import com.tccfer.application.model.entity.pessoa.Pessoa;
import com.tccfer.application.model.repository.localizacaorepository.EstadoRepository;
import com.tccfer.application.model.repository.localizacaorepository.MunicipioRepository;
import com.tccfer.application.model.repository.localizacaorepository.PaisRepository;

public class PessoaMapperOld {

    public static Pessoa toEntity(PessoaCadastroDTO dto,
                                  PaisRepository paisRepository,
                                  EstadoRepository estadoRepository,
                                  MunicipioRepository municipioRepository
    ){
        Pessoa entity =  new Pessoa();
        EnderecoMapper enderecoMapper = new EnderecoMapper(paisRepository, estadoRepository, municipioRepository);
        ContatoMapperOld contatoMapper = new ContatoMapperOld();

        entity.setNome(dto.getNome());
        entity.setTipoUsuario(dto.getTipoUsuario());
        entity.setTipoPessoa(dto.getTipoPessoa());

        if(dto.getTipoPessoa() == TipoPessoa.FISICA){
            entity.setCpf(dto.getCpf());
            entity.setDataNascimento(dto.getDataNascimento());
            entity.setSexo(dto.getSexo());
        } else if (dto.getTipoPessoa() == TipoPessoa.JURIDICA) {
            entity.setCnpj(dto.getCnpj());
        }

        //entity.setDataRegistro(dto.getDataRegistro());
//        entity.setEndereco(
//                enderecoMapper.toEntity(
//                        dto.getEnderecoDTO()
//                        //, estadoRepository,
//                        //municipioRepository,
//                        //paisRepository
//                )
//        );
//        entity.setContato(
//                contatoMapper.toEntity(dto.getContatoDTO())
//        );
        return entity;
    }
}
