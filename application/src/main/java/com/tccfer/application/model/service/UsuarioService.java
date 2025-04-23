package com.tccfer.application.model.service;

import com.tccfer.application.controller.dto.UsuarioCadastroDTO;
import com.tccfer.application.mapper.EnderecoMapper;
import com.tccfer.application.mapper.PessoaMapper;
import com.tccfer.application.model.entity.pessoa.Pessoa;
import com.tccfer.application.model.entity.pessoa.UsuarioSistema;
import com.tccfer.application.model.repository.localizacaorepository.EstadoRepository;
import com.tccfer.application.model.repository.localizacaorepository.MunicipioRepository;
import com.tccfer.application.model.repository.localizacaorepository.PaisRepository;
import com.tccfer.application.model.repository.usuariorepository.PessoaRepository;
import com.tccfer.application.model.repository.usuariorepository.UsuarioSistemaRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioSistemaRepository usuarioSistemaRepository;
    private final PessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper;
    private final EnderecoMapper enderecoMapper;
    private final PaisRepository paisRepository;
    private final EstadoRepository estadoRepository;
    private final MunicipioRepository municipioRepository;

    public UsuarioService(
            UsuarioSistemaRepository usuarioSistemaRepository,
            PessoaRepository pessoaRepository,
            PessoaMapper pessoaMapper,
            EnderecoMapper enderecoMapper,
            PaisRepository paisRepository,
            EstadoRepository estadoRepository,
            MunicipioRepository municipioRepository
    ){
        this.usuarioSistemaRepository = usuarioSistemaRepository;
        this.pessoaRepository = pessoaRepository;
        this.pessoaMapper = pessoaMapper;
        this.enderecoMapper = enderecoMapper;
        this.paisRepository = paisRepository;
        this.estadoRepository = estadoRepository;
        this.municipioRepository = municipioRepository;
    }

    public UsuarioSistema cadastrar(UsuarioCadastroDTO usuarioCadastroDTO){
        Pessoa pessoa = pessoaMapper.toEntity(usuarioCadastroDTO.getPessoaCadastroDTO());
        pessoaMapper.preencherCamposCondicionais(usuarioCadastroDTO.getPessoaCadastroDTO(), pessoa);
        pessoa = pessoaRepository.save(pessoa);

        UsuarioSistema usuarioSistema = new UsuarioSistema();
        usuarioSistema.setLogin(usuarioCadastroDTO.getLogin());
        usuarioSistema.setSenha(usuarioCadastroDTO.getSenha());
        usuarioSistema.setPessoa(pessoa);
        usuarioSistema.setAtivo(true);

        return usuarioSistemaRepository.save(usuarioSistema);
    }

}
