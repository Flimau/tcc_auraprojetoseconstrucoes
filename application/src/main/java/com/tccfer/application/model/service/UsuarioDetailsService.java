package com.tccfer.application.model.service;

import com.tccfer.application.model.entity.pessoa.UsuarioSistema;
import com.tccfer.application.model.repository.usuariorepository.UsuarioSistemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioSistemaRepository usuarioSistemaRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UsuarioSistema usuario = usuarioSistemaRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return new org.springframework.security.core.userdetails.User(
                usuario.getLogin(),
                usuario.getSenha(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
