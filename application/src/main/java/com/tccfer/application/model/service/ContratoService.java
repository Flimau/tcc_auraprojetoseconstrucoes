// src/main/java/com/tccfer/application/model/service/ContratoService.java
package com.tccfer.application.model.service;

import com.tccfer.application.controller.dto.contrato.ContratoDTO;
import com.tccfer.application.controller.dto.contrato.ContratoResumoDTO;
import com.tccfer.application.model.entity.contrato.Contrato;
import com.tccfer.application.model.entity.orcamento.Orcamento;
import com.tccfer.application.model.repository.contratorepository.ContratoRepository;
import com.tccfer.application.model.repository.orcamentorepository.OrcamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import java.io.ByteArrayOutputStream;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço que gerencia criação, atualização, listagem e busca de ContratoEntity.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ContratoService {

    private final ContratoRepository contratoRepo;
    private final OrcamentoRepository orcamentoRepo;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * Cria um contrato vinculado a um orçamento existente.
     * Se já existir contrato para esse orçamento, lança exceção.
     */
    public ContratoDTO criarContrato(ContratoDTO dto) {
        Orcamento orc = orcamentoRepo.findById(dto.getOrcamentoId())
                .orElseThrow(() -> new RuntimeException("Orçamento não encontrado: ID=" + dto.getOrcamentoId()));

        // Verifica se já existe um contrato para esse orçamento
        if (contratoRepo.findByOrcamentoId(orc.getId()).isPresent()) {
            throw new RuntimeException("Já existe contrato para esse orçamento: " + orc.getId());
        }

        Contrato ent = Contrato.builder()
                .orcamento(orc)
                .dataInicio(dto.getDataInicio())
                .dataFim(dto.getDataFim())
                .build();

        Contrato salvo = contratoRepo.save(ent);
        return mapToDTO(salvo);
    }

    /**
     * Atualiza um contrato existente (por ID).
     */
    public ContratoDTO atualizarContrato(Long id, ContratoDTO dto) {
        Contrato ent = contratoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado: ID=" + id));

        // Atualiza campos mutáveis
        ent.setDataInicio(dto.getDataInicio());
        ent.setDataFim(dto.getDataFim());
        Contrato atualizado = contratoRepo.save(ent);

        return mapToDTO(atualizado);
    }

    /**
     * Busca um contrato por ID.
     */
    @Transactional(readOnly = true)
    public ContratoDTO buscarPorId(Long id) {
        Contrato ent = contratoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado: ID=" + id));
        return mapToDTO(ent);
    }

    /**
     * Busca o contrato vinculado a um orçamento específico (se existir).
     */
    @Transactional(readOnly = true)
    public ContratoDTO buscarPorOrcamento(Long orcamentoId) {
        Contrato ent = contratoRepo.findByOrcamentoId(orcamentoId)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado para orçamento: " + orcamentoId));
        return mapToDTO(ent);
    }

    /**
     * Lista todos os contratos (resumos).
     */
    @Transactional(readOnly = true)
    public List<ContratoResumoDTO> listarContratosResumo() {
        return contratoRepo.findAll().stream()
                .map(this::mapToResumoDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converte entidade para DTO completo.
     */
    private ContratoDTO mapToDTO(Contrato ent) {
        ContratoDTO dto = new ContratoDTO();
        dto.setId(ent.getId());
        dto.setOrcamentoId(ent.getOrcamento().getId());
        dto.setDataInicio(ent.getDataInicio());
        dto.setDataFim(ent.getDataFim());
        return dto;
    }

    /**
     * Converte entidade para DTO de resumo (para listagem).
     */
    private ContratoResumoDTO mapToResumoDTO(Contrato ent) {
        // Aproveitamos que orcamento.getCliente() está disponível no Orcamento
        Long clienteId = ent.getOrcamento().getCliente().getId();
        String clienteNome = ent.getOrcamento().getCliente().getNome();
        return new ContratoResumoDTO(
                ent.getId(),
                ent.getOrcamento().getId(),
                clienteId,
                clienteNome
        );
    }

    public byte[] gerarPdfContrato(Long contratoId) {
        // Busca contrato e orçamento
        Contrato contrato = contratoRepo.findById(contratoId)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado: ID=" + contratoId));

        Orcamento orcamento = contrato.getOrcamento();
        var cliente = orcamento.getCliente();

        // Formatar data (dd/MM/yyyy)
        String dataFormatadaOrc = "";
        String dataFormatadaIniContrato = "";
        String dataFormatadaFinContrato = "";
        if (orcamento.getDataCriacao() != null) {
            dataFormatadaOrc = orcamento.getDataCriacao().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        if (contrato.getDataInicio() != null) {
            dataFormatadaIniContrato = contrato.getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        if (contrato.getDataFim() != null) {
            dataFormatadaFinContrato = contrato.getDataFim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        // Extrair endereço detalhado
        var endereco = cliente.getEndereco();
        String logradouro = endereco != null ? endereco.getLogradouro() : "";
        String numero = endereco != null ? endereco.getNumero() : "";
        String bairro = endereco != null ? endereco.getBairro() : "";
        String cep = endereco != null ? endereco.getCep() : "";
        String municipio = (endereco != null && endereco.getMunicipio() != null) ? endereco.getMunicipio().getNome() : "";
        String estadoSigla = (endereco != null && endereco.getMunicipio() != null
                && endereco.getMunicipio().getEstado() != null)
                ? endereco.getMunicipio().getEstado().getSigla() : "";

        // Monta o contexto Thymeleaf com os campos que o template espera
        Context context = new Context();

        // Cliente
        context.setVariable("clienteNome", cliente.getNome());
        context.setVariable("clienteDocumento", cliente.getCpf() != null ? cliente.getCpf() : cliente.getCnpj());
        context.setVariable("logradouro", logradouro);
        context.setVariable("numero", numero);
        context.setVariable("bairro", bairro);
        context.setVariable("cep", cep);
        context.setVariable("municipio", municipio);
        context.setVariable("estadoSigla", estadoSigla);

        // Orçamento
        context.setVariable("orcamentoId", orcamento.getId());
        context.setVariable("orcamentoData", dataFormatadaOrc);
        context.setVariable("orcamentoDescricao", orcamento.getDescricao());
        context.setVariable("tipo", orcamento.getTipo());
        context.setVariable("subtipo", orcamento.getSubtipo());
        context.setVariable("comMaterial", orcamento.isComMaterial());
        context.setVariable("itens", orcamento.getItens());
        context.setVariable("valorTotal", orcamento.getValorTotal());
        context.setVariable("contratoVigIni", dataFormatadaIniContrato);
        context.setVariable("contratoVigFin", dataFormatadaFinContrato);

        // Geração do HTML via Thymeleaf
        String html = templateEngine.process("contrato", context);

        // Conversão para PDF
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null);
            builder.toStream(baos);
            builder.run();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar contrato em PDF", e);
        }
    }



    public void deletarContrato(Long id) {
        if (!contratoRepo.existsById(id)) {
            throw new RuntimeException("Contrato não encontrada para deleção");
        }
        Contrato contrato = contratoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrada"));
        contratoRepo.deleteById(id);
    }
}
