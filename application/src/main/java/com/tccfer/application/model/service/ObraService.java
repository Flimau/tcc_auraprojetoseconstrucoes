package com.tccfer.application.model.service;

import com.tccfer.application.controller.dto.obra.*;
import com.tccfer.application.mapper.EnderecoMapper;
import com.tccfer.application.model.entity.contrato.Contrato;
import com.tccfer.application.model.entity.enuns.ObraStatus;
import com.tccfer.application.model.entity.localizacao.Endereco;
import com.tccfer.application.model.entity.localizacao.Municipio;
import com.tccfer.application.model.entity.obra.Obra;
import com.tccfer.application.model.entity.orcamento.Orcamento;
import com.tccfer.application.model.entity.pessoa.Pessoa;
import com.tccfer.application.model.repository.contratorepository.ContratoRepository;
import com.tccfer.application.model.repository.localizacaorepository.EnderecoRepository;
import com.tccfer.application.model.repository.localizacaorepository.MunicipioRepository;
import com.tccfer.application.model.repository.obrarepository.AcompanhamentoRepository;
import com.tccfer.application.model.repository.obrarepository.DiarioDeObraRepository;
import com.tccfer.application.model.repository.obrarepository.ObraRepository;
import com.tccfer.application.model.repository.orcamentorepository.OrcamentoRepository;
import com.tccfer.application.model.repository.usuariorepository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ObraService {

    private final ObraRepository obraRepo;
    private final PessoaRepository pessoaRepo;
    private final OrcamentoRepository orcamentoRepo;
    private final EnderecoRepository enderecoRepo;
    private final EnderecoMapper enderecoMapper;
    private final ContratoRepository contratoRepo;
    private final DiarioDeObraRepository diarioRepo;
    private final MunicipioRepository municipioRepo;
    private final AcompanhamentoRepository acompanhamentoRepository;

    public void cadastrar(ObraCadastroDTO dto) {

        validarDisponibilidadeExecutor(dto.getExecutorId(), dto.getDataInicio(), dto.getDataFim(), null);

        Obra obra = new Obra();
        obra.setCliente(buscarPessoa(dto.getClienteId()));
        obra.setOrcamento(buscarOrcamento(dto.getOrcamentoId()));
        obra.setExecutor(dto.getExecutorId() != null ? buscarPessoa(dto.getExecutorId()) : null);
        obra.setContrato(dto.getContratoId() != null ? buscarContrato(dto.getContratoId()) : null);
        obra.setDataInicio(dto.getDataInicio());
        obra.setDataFim(dto.getDataFim());
        obra.setStatus(dto.getStatus());

        Endereco endereco = enderecoMapper.toEntity(dto.getEndereco());
        enderecoRepo.save(endereco);


        obra.setEndereco(endereco);

        obraRepo.save(obra);
    }

    public ObraDetalhadaDTO buscarPorId(Long id) {
        Obra obra = obraRepo.buscarPorIdComRelacionamentos(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada"));

        ObraDetalhadaDTO dto = new ObraDetalhadaDTO();
        dto.setId(obra.getId());
        dto.setNomeCliente(obra.getCliente().getNome());
        dto.setOrcamentoId(obra.getOrcamento().getId());
        dto.setEndereco(enderecoMapper.toDTO(obra.getEndereco()));
        dto.setNomeExecutor(obra.getExecutor() != null ? obra.getExecutor().getNome() : null);
        dto.setContratoId(obra.getContrato() != null ? obra.getContrato().getId() : null);
        dto.setDataInicio(obra.getDataInicio());
        dto.setDataFim(obra.getDataFim());
        dto.setStatus(obra.getStatus());

        // Listar diarios vinculados
        List<DiarioDeObraListagemDTO> diarios = diarioRepo.findByObraId(id).stream().map(diario -> {
            DiarioDeObraListagemDTO diarioDTO = new DiarioDeObraListagemDTO();
            diarioDTO.setId(diario.getId());
            diarioDTO.setDataRegistro(diario.getDataRegistro());
            return diarioDTO;
        }).collect(Collectors.toList());

        dto.setDiarios(diarios);
        return dto;
    }

    public List<ObraListagemDTO> listar() {
        return obraRepo.findAllWithClienteOrcamentoExecutor().stream().map(obra -> {
            ObraListagemDTO dto = new ObraListagemDTO();
            dto.setId(obra.getId());
            dto.setNomeCliente(obra.getCliente().getNome());
            dto.setNomeExecutor(obra.getExecutor() != null ? obra.getExecutor().getNome() : null);
            dto.setDataInicio(obra.getDataInicio());
            dto.setDataFim(obra.getDataFim());
            dto.setStatus(obra.getStatus());
            return dto;
        }).collect(Collectors.toList());
    }

    public void atualizar(Long id, ObraCadastroDTO dto) {

        Obra obra = obraRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada"));

        Long executorAtualId = obra.getExecutor() != null ? obra.getExecutor().getId() : null;
        Long executorNovoId = dto.getExecutorId();

        if (!Objects.equals(executorAtualId, executorNovoId)) {
            validarDisponibilidadeExecutor(executorNovoId, dto.getDataInicio(), dto.getDataFim(), id);
        }

        obra.setCliente(buscarPessoa(dto.getClienteId()));
        obra.setOrcamento(buscarOrcamento(dto.getOrcamentoId()));
        obra.setExecutor(dto.getExecutorId() != null ? buscarPessoa(dto.getExecutorId()) : null);
        obra.setContrato(dto.getContratoId() != null ? buscarContrato(dto.getContratoId()) : null);
        obra.setDataInicio(dto.getDataInicio());
        obra.setDataFim(dto.getDataFim());
        obra.setStatus(dto.getStatus());

        // Atualizar Endereco
        Endereco enderecoExistente = enderecoRepo.findById(obra.getEndereco().getId())
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

        Municipio municipio = buscarOuCriarMunicipio(dto.getEndereco().getCidade());
        enderecoExistente.setLogradouro(dto.getEndereco().getLogradouro());
        enderecoExistente.setNumero(dto.getEndereco().getNumero());
        enderecoExistente.setBairro(dto.getEndereco().getBairro());
        enderecoExistente.setComplemento(dto.getEndereco().getComplemento());
        enderecoExistente.setCep(dto.getEndereco().getCep());
        enderecoExistente.setLatitude(dto.getEndereco().getLatitude());
        enderecoExistente.setLongitude(dto.getEndereco().getLongitude());
        enderecoExistente.setMunicipio(municipio);

        enderecoRepo.save(enderecoExistente);
        obra.setEndereco(enderecoExistente);
        obraRepo.save(obra);
    }


    public List<ObraListagemDTO> buscarPorCliente(String nomeCliente) {
        List<Obra> obras = obraRepo.buscarPorClienteComRelacionamentos(nomeCliente);
        return obras.stream().map(obra -> {
            ObraListagemDTO dto = new ObraListagemDTO();
            dto.setId(obra.getId());
            dto.setNomeCliente(obra.getCliente().getNome());
            dto.setNomeExecutor(obra.getExecutor() != null ? obra.getExecutor().getNome() : null);
            dto.setDataInicio(obra.getDataInicio());
            dto.setDataFim(obra.getDataFim());
            dto.setStatus(obra.getStatus());
            return dto;
        }).collect(Collectors.toList());

    }

    @Transactional
    public void deletar(Long id) {
        Obra obra = obraRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada"));

        acompanhamentoRepository.deleteByObra(obra);
        obraRepo.delete(obra);
    }

    // Métodos auxiliares para carregar as entidades relacionadas:
    private Pessoa buscarPessoa(Long id) {
        return pessoaRepo.findById(id).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
    }

    private Orcamento buscarOrcamento(Long id) {
        return orcamentoRepo.findById(id).orElseThrow(() -> new RuntimeException("Orçamento não encontrado"));
    }

    private Endereco buscarEndereco(Long id) {
        return enderecoRepo.findById(id).orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
    }

    private Contrato buscarContrato(Long id) {
        return contratoRepo.findById(id).orElseThrow(() -> new RuntimeException("Contrato não encontrado"));
    }

    private void validarDisponibilidadeExecutor(Long executorId, LocalDate dataInicio, LocalDate dataFim, Long obraSendoEditada) {
        if (executorId == null || dataInicio == null || dataFim == null) {
            return;
        }

        List<Obra> obrasEmConflito = obraRepo.findObrasComConflitoDeData(executorId, dataInicio, dataFim, obraSendoEditada);

        if (!obrasEmConflito.isEmpty()) {
            throw new RuntimeException("Executor já possui obra agendada neste período.");
        }
    }

    private Municipio buscarOuCriarMunicipio(String nomeCidade) {
        // Normaliza o nome antes de buscar/criar
        String nomeNormalizado = nomeCidade.trim().toUpperCase();

        return municipioRepo.findByNomeIgnoreCase(nomeNormalizado)
                .orElseGet(() -> {
                    Municipio novoMunicipio = new Municipio();
                    novoMunicipio.setNome(nomeNormalizado);
                    return municipioRepo.save(novoMunicipio);
                });
    }


    public void iniciarObra(Long id, IniciarObraDTO dto) {
        var obra = obraRepo.findById(id).orElseThrow(() -> new RuntimeException("Obra não encontrada"));
        obra.setDataInicio(dto.getDataInicio());
        obra.setDataFim(dto.getDataFim());
        obra.setStatus(ObraStatus.EM_ANDAMENTO);

        obraRepo.save(obra);
    }

    public void finalizarObra(Long id, IniciarObraDTO dto) {
        var obra = obraRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada"));

        obra.setDataInicio(dto.getDataInicio());
        obra.setDataFim(LocalDate.now());
        obra.setStatus(ObraStatus.CONCLUIDA);

        obraRepo.save(obra);
    }

}

