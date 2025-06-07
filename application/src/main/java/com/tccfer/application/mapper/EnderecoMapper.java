package com.tccfer.application.mapper;

import com.tccfer.application.controller.dto.endereco.EnderecoDTO;
import com.tccfer.application.model.entity.localizacao.Endereco;
import com.tccfer.application.model.entity.localizacao.Estado;
import com.tccfer.application.model.entity.localizacao.Municipio;
import com.tccfer.application.model.entity.localizacao.Pais;
import com.tccfer.application.model.repository.localizacaorepository.EstadoRepository;
import com.tccfer.application.model.repository.localizacaorepository.MunicipioRepository;
import com.tccfer.application.model.repository.localizacaorepository.PaisRepository;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

    private final PaisRepository paisRepository;
    private final EstadoRepository estadoRepository;
    private final MunicipioRepository municipioRepository;

    public EnderecoMapper(
            PaisRepository paisRepository,
            EstadoRepository estadoRepository,
            MunicipioRepository municipioRepository
    ) {
        this.paisRepository = paisRepository;
        this.estadoRepository = estadoRepository;
        this.municipioRepository = municipioRepository;
    }

    public Endereco toEntity(EnderecoDTO dto) {

        Pais pais = paisRepository.findByNome("BRASIL")
                .orElseGet(() -> {
                    Pais novo = new Pais();
                    novo.setNome("BRASIL");
                    novo.setSigla("BR");
                    return paisRepository.save(novo);
                });

        Estado estado = estadoRepository.findBySigla(dto.getSiglaEstado())
                .orElseGet(() -> {
                    Estado novo = new Estado();
                    novo.setSigla(dto.getSiglaEstado());
                    novo.setNome(dto.getNomeEstado());
                    novo.setPais(pais);
                    return estadoRepository.save(novo);
                });

        Municipio municipio = municipioRepository.findByNomeAndEstado(dto.getCidade(), estado)
                .orElseGet(() -> {
                    Municipio novo = new Municipio();
                    novo.setNome(dto.getCidade());
                    novo.setEstado(estado);
                    return municipioRepository.save(novo);
                });

        Endereco entity = new Endereco();
        entity.setLogradouro(dto.getLogradouro());
        entity.setNumero(dto.getNumero());
        entity.setBairro(dto.getBairro());
        entity.setCep(dto.getCep());
        entity.setComplemento(dto.getComplemento());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setEstado(estado);
        entity.setMunicipio(municipio);
        return entity;
    }

    public EnderecoDTO toDTO(Endereco e) {
        EnderecoDTO dto = new EnderecoDTO();
        dto.setId(e.getId()); // agora com o id
        dto.setLogradouro(e.getLogradouro());
        dto.setNumero(e.getNumero());
        dto.setBairro(e.getBairro());
        dto.setCep(e.getCep());
        dto.setComplemento(e.getComplemento());
        dto.setSiglaEstado(e.getEstado().getSigla());
        dto.setNomeEstado(e.getEstado().getNome());
        dto.setCidade(e.getMunicipio().getNome());
        dto.setLatitude(e.getLatitude());
        dto.setLongitude(e.getLongitude());
        return dto;
    }

    public void atualizarEntidade(EnderecoDTO dto, Endereco entity) {

        Pais pais = paisRepository.findByNome("BRASIL")
                .orElseGet(() -> {
                    Pais novo = new Pais();
                    novo.setNome("BRASIL");
                    novo.setSigla("BR");
                    return paisRepository.save(novo);
                });

        Estado estado = estadoRepository.findBySigla(dto.getSiglaEstado().trim().toUpperCase())
                .orElseGet(() -> {
                    Estado novo = new Estado();
                    novo.setSigla(dto.getSiglaEstado().trim().toUpperCase());
                    novo.setNome(dto.getNomeEstado());
                    novo.setPais(pais);
                    return estadoRepository.save(novo);
                });

        Municipio municipio = municipioRepository
                .findByNomeAndEstado(dto.getCidade().trim().toUpperCase(), estado)
                .orElseGet(() -> {
                    Municipio novo = new Municipio();
                    novo.setNome(dto.getCidade().trim().toUpperCase());
                    novo.setEstado(estado);
                    return municipioRepository.save(novo);
                });


        entity.setLogradouro(dto.getLogradouro());
        entity.setNumero(dto.getNumero());
        entity.setBairro(dto.getBairro());
        entity.setCep(dto.getCep());
        entity.setComplemento(dto.getComplemento());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setEstado(estado);
        entity.setMunicipio(municipio);
    }
}
