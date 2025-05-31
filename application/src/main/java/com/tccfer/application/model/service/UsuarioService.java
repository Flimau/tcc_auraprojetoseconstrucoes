package com.tccfer.application.model.service;

import com.tccfer.application.controller.dto.usuario.PessoaCadastroDTO;
import com.tccfer.application.controller.dto.usuario.UsuarioCadastroDTO;
import com.tccfer.application.controller.dto.usuario.UsuarioMinDTO;
import com.tccfer.application.mapper.EnderecoMapper;
import com.tccfer.application.mapper.PessoaMapper;
import com.tccfer.application.model.entity.enuns.TipoPessoa;
import com.tccfer.application.model.entity.enuns.TipoUsuario;
import com.tccfer.application.model.entity.localizacao.Endereco;
import com.tccfer.application.model.entity.pessoa.Pessoa;
import com.tccfer.application.model.entity.pessoa.UsuarioSistema;
import com.tccfer.application.model.repository.localizacaorepository.EnderecoRepository;
import com.tccfer.application.model.repository.usuariorepository.PessoaRepository;
import com.tccfer.application.model.repository.usuariorepository.UsuarioSistemaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioSistemaRepository usuarioSistemaRepository;
    private final PessoaRepository pessoaRepository;
    private final EnderecoRepository enderecoRepository;
    private final PessoaMapper pessoaMapper;
    private final EnderecoMapper enderecoMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UsuarioService(
            UsuarioSistemaRepository usuarioSistemaRepository,
            PessoaRepository pessoaRepository,
            EnderecoRepository enderecoRepository,
            PessoaMapper pessoaMapper,
            EnderecoMapper enderecoMapper,
            BCryptPasswordEncoder passwordEncoder,
            EmailService emailService
    ) {
        this.usuarioSistemaRepository = usuarioSistemaRepository;
        this.pessoaRepository = pessoaRepository;
        this.enderecoRepository = enderecoRepository;
        this.pessoaMapper = pessoaMapper;
        this.enderecoMapper = enderecoMapper;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public void cadastrar(UsuarioCadastroDTO dto) {
        PessoaCadastroDTO pDto = dto.getPessoaCadastroDTO();

        if (usuarioSistemaRepository.existsByLogin(dto.getLogin())) {
            throw new IllegalArgumentException("Já existe um usuário com este login.");
        }

        // salva endereço
        Endereco end = enderecoMapper.toEntity(pDto.getEndereco());
        end = enderecoRepository.save(end);

        // salva pessoa
        Pessoa pessoa = pessoaMapper.toEntity(pDto);
        pessoa.setEndereco(end);
        pessoaMapper.preencherCamposCondicionais(pDto, pessoa);
        pessoa = pessoaRepository.save(pessoa);

        // monta usuário
        UsuarioSistema user = new UsuarioSistema();
        user.setLogin(dto.getLogin());

        String token = null;
        if (dto.getTipoUsuario() != TipoUsuario.CLIENTE) {
            // ADM/EXECUTOR
            String raw = dto.getSenha();
            if (raw == null || raw.isBlank()) {
                throw new IllegalArgumentException("Senha obrigatória para administradores/executores.");
            }
            user.setSenha(passwordEncoder.encode(raw));
            user.setAtivo(true);
        } else {
            // CLIENTE
            token = UUID.randomUUID().toString();
            user.setTokenAtivacao(token);
            user.setAtivo(false);
            user.setSenha(passwordEncoder.encode(token)); // senha provisória
        }

        user.setTipoUsuario(dto.getTipoUsuario());
        user.setPessoa(pessoa);
        usuarioSistemaRepository.save(user);

        // envia e-mail de ativação (somente CLIENTE)
//        if (token != null) {
//            emailService.sendActivationEmail(user.getLogin(), token);
//        }
    }

    public UsuarioSistema buscarPorId(long id) {
        return usuarioSistemaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado pelo Id."));
    }

    @Transactional
    public UsuarioSistema atualizar(long id, UsuarioCadastroDTO dto) {
        UsuarioSistema usuario = usuarioSistemaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        Pessoa pessoaAtual = usuario.getPessoa();
        PessoaCadastroDTO pDto = dto.getPessoaCadastroDTO();
        if (pDto == null) {
            throw new RuntimeException("Dados da pessoa são obrigatórios para atualização.");
        }

        Endereco end = enderecoMapper.toEntity(pDto.getEndereco());
        end = enderecoRepository.save(end);
        pessoaAtual.setEndereco(end);

        pessoaMapper.atualizarEntidade(pDto, pessoaAtual);
        pessoaMapper.preencherCamposCondicionais(pDto, pessoaAtual);
        pessoaRepository.save(pessoaAtual);

        usuario.setLogin(dto.getLogin());
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }
        usuario.setTipoUsuario(dto.getTipoUsuario());

        return usuarioSistemaRepository.save(usuario);
    }

    public void desativar(Long id) {
        UsuarioSistema user = usuarioSistemaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado para desativação."));
        user.setAtivo(false);
        usuarioSistemaRepository.save(user);
    }

    @Transactional
    public String reativar(Long id) {
        UsuarioSistema user = usuarioSistemaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado para ativação."));
        if (user.getTipoUsuario() == TipoUsuario.CLIENTE) {
            String token = UUID.randomUUID().toString();
            user.setTokenAtivacao(token);
            user.setAtivo(false);
            user.setSenha(passwordEncoder.encode(token));
            usuarioSistemaRepository.save(user);
            return token;
        } else {
            user.setAtivo(true);
            usuarioSistemaRepository.save(user);
            return null;
        }
    }

    public List<UsuarioMinDTO> listarUsuarios() {
        return mapToMinDTOList(usuarioSistemaRepository.findAll());
    }

    public List<UsuarioMinDTO> listarPorId(Long id) {
        return mapToMinDTOList(List.of(buscarPorId(id)));
    }

    public List<UsuarioMinDTO> listarPorCpf(String cpf) {
        return mapToMinDTOList(usuarioSistemaRepository.findByPessoaCpf(cpf));
    }

    public List<UsuarioMinDTO> listarPorCnpj(String cnpj) {
        return mapToMinDTOList(usuarioSistemaRepository.findByPessoaCnpj(cnpj));
    }

    public List<UsuarioMinDTO> listarPorNome(String nome) {
        return mapToMinDTOList(usuarioSistemaRepository.findByPessoaNomeContainingIgnoreCase(nome));
    }

    public List<UsuarioMinDTO> listarPorTipoUsuario(String tipoStr) {
        TipoUsuario tipo = TipoUsuario.valueOf(tipoStr.toUpperCase());
        return mapToMinDTOList(usuarioSistemaRepository.findByTipoUsuario(tipo));
    }

    public List<UsuarioMinDTO> listarPorTipoEId(String tipoStr, Long id) {
        TipoUsuario tipo = TipoUsuario.valueOf(tipoStr.toUpperCase());
        return mapToMinDTOList(usuarioSistemaRepository.findByTipoUsuarioAndId(tipo, id));
    }

    public List<UsuarioMinDTO> listarPorTipoECpf(String tipoStr, String cpf) {
        TipoUsuario tipo = TipoUsuario.valueOf(tipoStr.toUpperCase());
        return mapToMinDTOList(usuarioSistemaRepository.findByTipoUsuarioAndPessoaCpf(tipo, cpf));
    }

    public List<UsuarioMinDTO> listarPorTipoECnpj(String tipoStr, String cnpj) {
        TipoUsuario tipo = TipoUsuario.valueOf(tipoStr.toUpperCase());
        return mapToMinDTOList(usuarioSistemaRepository.findByTipoUsuarioAndPessoaCnpj(tipo, cnpj));
    }

    public List<UsuarioMinDTO> listarPorTipoENome(String tipoStr, String nome) {
        TipoUsuario tipo = TipoUsuario.valueOf(tipoStr.toUpperCase());
        return mapToMinDTOList(usuarioSistemaRepository.findByTipoUsuarioAndPessoaNomeContainingIgnoreCase(tipo, nome));
    }

    public void ativarUsuario(String token, String novaSenha) {
        UsuarioSistema user = usuarioSistemaRepository.findByTokenAtivacao(token)
                .orElseThrow(() -> new RuntimeException("Token de ativação inválido"));
        user.setSenha(passwordEncoder.encode(novaSenha));
        user.setAtivo(true);
        user.setTokenAtivacao(null);
        usuarioSistemaRepository.save(user);
    }

    private List<UsuarioMinDTO> mapToMinDTOList(List<UsuarioSistema> usuarios) {
        return usuarios.stream().map(u -> {
            UsuarioMinDTO dto = new UsuarioMinDTO();
            dto.setId(u.getId());
            dto.setTipoUsuario(u.getTipoUsuario().name());
            dto.setAtivo(u.isAtivo());
            if (u.getPessoa() != null) {
                String nome = u.getPessoa().getNome();
                dto.setNome(nome != null ? nome : "Cadastro incompleto");
                if (u.getPessoa().getTipoPessoa() == TipoPessoa.FISICA) {
                    dto.setDocumento(u.getPessoa().getCpf());
                } else {
                    dto.setDocumento(u.getPessoa().getCnpj());
                }
            }
            return dto;
        }).toList();
    }
}
