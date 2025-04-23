package com.tccfer.application.config;

import com.tccfer.application.mapper.EnderecoMapper;
import com.tccfer.application.model.repository.localizacaorepository.EstadoRepository;
import com.tccfer.application.model.repository.localizacaorepository.MunicipioRepository;
import com.tccfer.application.model.repository.localizacaorepository.PaisRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public EnderecoMapper enderecoMapper(
            PaisRepository paisRepository,
            EstadoRepository estadoRepository,
            MunicipioRepository municipioRepository
    ) {
        return new EnderecoMapper(paisRepository, estadoRepository, municipioRepository);
    }
}
