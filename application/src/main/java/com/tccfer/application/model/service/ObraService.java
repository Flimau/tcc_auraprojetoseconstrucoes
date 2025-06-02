package com.tccfer.application.model.service;

import com.tccfer.application.controller.dto.obra.ObraDTO;
import com.tccfer.application.model.entity.enuns.ObraStatus;
import com.tccfer.application.model.entity.obra.Obra;

import com.tccfer.application.model.entity.pessoa.Pessoa;
import com.tccfer.application.model.repository.obrarepository.ObraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ObraService {

    private final ObraRepository obraRepository;

    public ObraService(ObraRepository obraRepository) {
        this.obraRepository = obraRepository;
    }

    /**
     * Lista todas as obras e converte cada Entidade para ObraDTO,
     * já carregando cliente, orçamento e executor.
     */
    public List<ObraDTO> listarTodasDTO() {
        List<Obra> todas = obraRepository.findAllWithClienteOrcamentoExecutor();
        return todas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca a obra pelo ID e retorna um ObraDTO.
     * Carrega cliente, orçamento e executor.
     */
    public ObraDTO buscarPorIdDTO(Long id) {
        Obra obra = obraRepository.findByIdWithClienteOrcamentoExecutor(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada com id: " + id));
        return toDTO(obra);
    }

    /**
     * Cria nova obra no banco e retorna o DTO correspondente.
     * Sempre define status = PLANEJADA antes de salvar.
     */
    public ObraDTO criarObraDTO(Obra obra) {
        obra.setStatus(ObraStatus.PLANEJADA);
        Obra obraSalva = obraRepository.save(obra);

        // Recarrega com JOIN FETCH de cliente, orçamento e executor (se houver)
        Obra obraComTodos = obraRepository.findByIdWithClienteOrcamentoExecutor(obraSalva.getId())
                .orElseThrow(() -> new RuntimeException("Erro ao buscar obra criada"));
        return toDTO(obraComTodos);
    }

    /**
     * Atualiza uma obra já existente e retorna o DTO atualizado,
     * garantindo que cliente, orçamento e executor sejam carregados.
     */
    public ObraDTO atualizarObraDTO(Long id, Obra obraAtualizada) {
        Obra obraExistente = obraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada com id: " + id));

        obraExistente.setCliente(obraAtualizada.getCliente());
        obraExistente.setOrcamento(obraAtualizada.getOrcamento());
        obraExistente.setExecutor(obraAtualizada.getExecutor());
        obraExistente.setDataInicio(obraAtualizada.getDataInicio());
        obraExistente.setDataFim(obraAtualizada.getDataFim());
        obraExistente.setContratoUrl(obraAtualizada.getContratoUrl());

        Obra obraSalva = obraRepository.save(obraExistente);

        // Recarrega com JOIN FETCH de cliente, orçamento e executor
        Obra obraComTodos = obraRepository.findByIdWithClienteOrcamentoExecutor(obraSalva.getId())
                .orElseThrow(() -> new RuntimeException("Erro ao buscar obra atualizada"));
        return toDTO(obraComTodos);
    }

    /**
     * Deleta uma obra pelo ID.
     */
    public void deletarObra(Long id) {
        Obra obraExistente = obraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada com id: " + id));
        obraRepository.delete(obraExistente);
    }

    /**
     * Atribui um executor a uma obra existente, validando disponibilidade de datas.
     * Se já houver conflito de cronograma para o executor, lança RuntimeException.
     */
    @Transactional
    public ObraDTO atribuirExecutor(Long obraId, Long executorId) {
        Obra obra = obraRepository.findById(obraId)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada com id: " + obraId));

        Pessoa executor = new Pessoa();
        executor.setId(executorId);

        LocalDate novoInicio = obra.getDataInicio();
        LocalDate novoFim    = obra.getDataFim();
        Long qtdConflitos = obraRepository.countConflitos(executorId, novoInicio, novoFim);
        if (qtdConflitos != null && qtdConflitos > 0) {
            throw new RuntimeException("Executor não está disponível neste período");
        }

        obra.setExecutor(executor);
        Obra obraSalva = obraRepository.save(obra);

        // Recarrega com JOIN FETCH de cliente, orçamento e executor
        Obra obraComTodos = obraRepository.findByIdWithClienteOrcamentoExecutor(obraSalva.getId())
                .orElseThrow(() -> new RuntimeException("Erro ao buscar obra atualizada com executor"));
        return toDTO(obraComTodos);
    }

    /**
     * Altera apenas o status de uma obra existente.
     * Retorna o ObraDTO atualizado com o novo status.
     */
    public ObraDTO alterarStatus(Long obraId, String novoStatusStr) {
        Obra obra = obraRepository.findByIdWithClienteOrcamentoExecutor(obraId)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada com id: " + obraId));

        ObraStatus novoStatus;
        try {
            novoStatus = ObraStatus.valueOf(novoStatusStr);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Status inválido: " + novoStatusStr);
        }

        obra.setStatus(novoStatus);
        Obra obraSalva = obraRepository.save(obra);

        // Recarrega com JOIN FETCH de cliente, orçamento e executor
        Obra obraComTodos = obraRepository.findByIdWithClienteOrcamentoExecutor(obraSalva.getId())
                .orElseThrow(() -> new RuntimeException("Erro ao buscar obra atualizada com status"));
        return toDTO(obraComTodos);
    }

    /**
     * Retorna um mapa que agrupa as obras por status,
     * pronto para ser consumido como Kanban no front.
     */
    public Map<String, List<ObraDTO>> obterPorKanban() {
        Map<String, List<ObraDTO>> mapa = new LinkedHashMap<>();

        for (ObraStatus status : ObraStatus.values()) {
            String statusStr = status.name();
            List<Obra> obrasDoStatus = obraRepository.findByStatusWithCliente(statusStr);
            List<ObraDTO> dtos = obrasDoStatus.stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
            mapa.put(statusStr, dtos);
        }

        return mapa;
    }

    /**
     * Retorna lista de ObraDTO cujas datas se sobrepõem ao intervalo fornecido.
     */
    public List<ObraDTO> obterPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        List<Obra> obras = obraRepository.findByPeriodoWithCliente(dataInicio, dataFim);
        return obras.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converte a entidade JPA Obra em ObraDTO “achatado”.
     */
    private ObraDTO toDTO(Obra obra) {
        Long clienteId = obra.getCliente() != null
                ? obra.getCliente().getId() : null;
        String clienteNome = obra.getCliente() != null
                ? obra.getCliente().getNome() : null;

        Long orcamentoId = obra.getOrcamento() != null
                ? obra.getOrcamento().getId() : null;

        Long executorId = obra.getExecutor() != null
                ? obra.getExecutor().getId() : null;
        String executorNome = obra.getExecutor() != null
                ? obra.getExecutor().getNome() : null;

        String status = obra.getStatus() != null
                ? obra.getStatus().name() : null;

        return new ObraDTO(
                obra.getId(),
                clienteId,
                clienteNome,
                executorId,
                executorNome,
                orcamentoId,
                status,
                obra.getDataInicio(),
                obra.getDataFim(),
                obra.getContratoUrl()
        );
    }
}
